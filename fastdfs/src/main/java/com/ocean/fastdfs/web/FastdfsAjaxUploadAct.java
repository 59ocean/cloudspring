package com.ocean.fastdfs.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocean.fastdfs.config.RequestImageMarkConfig;
import com.ocean.fastdfs.config.RequestImageScaleConfig;
import com.ocean.fastdfs.exception.FastDFSException;
import com.ocean.fastdfs.service.IFastdfsClient;
import com.ocean.fastdfs.util.FileUploadRet;
import com.ocean.fastdfs.util.StrUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@RequestMapping("/fileUpload")
@Api(tags = "文件上传")
@RestController
public class FastdfsAjaxUploadAct {

	private IFastdfsClient fastdfsClient;
	
	

	private static final Logger log = Logger.getLogger(FastdfsAjaxUploadAct.class);

	// 文件保存目录URL

	/**
	 * 异步上传文件 zx
	 * 
	 * @return
	 */
	@ApiOperation(value = "异步上传文件")
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadFile(HttpServletRequest request, HttpServletResponse response, String uploadRuleId) {
		try {
			FileUploadRet ret = new FileUploadRet(); // 返回值
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;// 获取multipart请求
			MultipartFile files = mRequest.getFile("doc"); // 获取其中MultipartFile
															// ？能否获取多文件，如何多文件分割
			String uploadFileFileName = files.getOriginalFilename(); // 获取文件名称

			/*if (rule == null) {
				ret.setStatus(FileUploadRet.ERROR_STATUS);
				ret.setMsg("上传失败！未找到上传规则！");
				log.error(ret.getMsg());
				return JSONObject.toJSONString(ret);
			}*/

			/*if (!rule.acceptTypeUpload(FilenameUtils.getExtension(uploadFileFileName))) {
				ret.setStatus(FileUploadRet.ERROR_STATUS);
				ret.setMsg("上传失败！该类型文件不允许上传!");
				log.error(ret.getMsg());
				return JSONObject.toJSONString(ret);
			}*/

			String uploadPath = fastdfsClient.upload(files.getBytes(), files.getOriginalFilename(), null);
			String saveName = FilenameUtils.getBaseName(uploadPath);
			// 判断rule是否需要清理
			/*if (rule.isNeedClear()) {
				// 往rule中添加已上传的文件
				rule.addUploadFile(StrUtils.sanitizeFileName(uploadFileFileName), saveName, uploadPath,
						files.getSize());
			}*/
			ret.setStatus(FileUploadRet.SUCCESS_STATUS);
			ret.setFileSaveName(saveName);
			ret.setFileName(uploadFileFileName);
			ret.setFilePath(uploadPath);
			ret.setFileSize(Long.toString(files.getSize()));
			ret.setMsg("上传成功!");
			return JSONObject.toJSONString(ret);
		} catch (Exception e) {
			FileUploadRet ret = new FileUploadRet();
			e.printStackTrace();
			ret.setStatus(FileUploadRet.ERROR_STATUS);
			ret.setMsg("上传失败！");
			log.error(ret.getMsg());
			return JSONObject.toJSONString(ret);
		}
	}

	/**
	 * 异步上传文件 zx 启用水印的参数为imgIcon， true为启用
	 * 启用压缩的参数zoom，true为启用，zoomWidth，zoomHeight为压缩的参数
	 * 
	 * @return
	 */
    @ApiOperation(value = "异步上传文件 zx 启用水印的参数为imgIcon， true为启用")
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadImg(HttpServletRequest request, HttpServletResponse response, String uploadRuleId) {
		try {
			FileUploadRet ret = new FileUploadRet(); // 返回值
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;// 获取multipart请求
			MultipartFile files = mRequest.getFile("doc"); // 获取其中MultipartFile
															// ？能否获取多文件，如何多文件分割
			String uploadFileFileName = files.getOriginalFilename(); // 获取文件名称

			/*if (rule == null) {
				ret.setStatus(FileUploadRet.ERROR_STATUS);
				ret.setMsg("上传失败！未找到上传规则！");
				log.error(ret.getMsg());
				return JSONObject.toJSONString(ret);
			}*/

			if (((int) files.getSize() / 1024 + 1) > 1 * 1024) {
				ret.setStatus(FileUploadRet.ERROR_STATUS);
				ret.setMsg("上传失败！上传图片大小不能超过1M!");
				log.error(ret.getMsg());
				return JSONObject.toJSONString(ret);
			}

			RequestImageMarkConfig markConfig = StringUtils.equals(request.getParameter("imgIcon"), "true")
					? new RequestImageMarkConfig(true) : null;
			RequestImageScaleConfig scaleConfig = StringUtils.equals(request.getParameter("zoom"), "true")
					? new RequestImageScaleConfig(true, Integer.parseInt(request.getParameter("zoomWidth")),
							Integer.parseInt(request.getParameter("zoomHeight")))
					: null;

			String path = fastdfsClient.uploadImage(files.getInputStream(), uploadFileFileName, null, markConfig,
					scaleConfig);
			String saveName = FilenameUtils.getBaseName(path);
			// 判断rule是否需要清理
			/*if (rule.isNeedClear()) {
				// 往rule中添加已上传的文件
				rule.addUploadFile(StrUtils.sanitizeFileName(uploadFileFileName), saveName, path, files.getSize());
			}*/
			ret.setStatus(FileUploadRet.SUCCESS_STATUS);
			ret.setFileSaveName(saveName);
			ret.setFileName(uploadFileFileName);
			ret.setFilePath(path);
			ret.setFileSize(Long.toString(files.getSize()));
			ret.setMsg("上传成功!");
			return JSONObject.toJSONString(ret);
		} catch (Exception e) {
			FileUploadRet ret = new FileUploadRet();
			e.printStackTrace();
			ret.setStatus(FileUploadRet.ERROR_STATUS);
			ret.setMsg("上传失败！");
			log.error(ret.getMsg());
			return JSONObject.toJSONString(ret);
		}
	}

	/**
	 * 异步批量上传文件 zx
	 * 
	 * @return
	 */
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadFiles(HttpServletRequest request, HttpServletResponse response, String uploadRuleId) {
		JSONArray jsonArray = new JSONArray();
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;// 获取multipart请求
		MultiValueMap<String, MultipartFile> fileMap = mRequest.getMultiFileMap();
		Iterator<String> it = fileMap.keySet().iterator();
		while (it.hasNext()) {
			LinkedList<MultipartFile> fileList = (LinkedList<MultipartFile>) fileMap.get(it.next());
			for (MultipartFile files : fileList) {
				FileUploadRet ret = new FileUploadRet();
				String uploadFileFileName = files.getOriginalFilename(); // 获取文件名称

				/*if (rule == null) {
					ret.setStatus(FileUploadRet.ERROR_STATUS);
					ret.setMsg("上传失败！未找到上传规则！");
					log.error(ret.getMsg());
					jsonArray.add(ret);
					continue;
				}*/

				/*if (!rule.acceptTypeUpload(FilenameUtils.getExtension(uploadFileFileName))) {
					ret.setStatus(FileUploadRet.ERROR_STATUS);
					ret.setMsg("上传失败！该类型文件不允许上传!");
					log.error(ret.getMsg());
					jsonArray.add(ret);
					continue;
				}*/

				try {
					String uploadPath = fastdfsClient.upload(files.getBytes(), files.getOriginalFilename(), null);
					String saveName = FilenameUtils.getBaseName(uploadPath);
					/*// 判断rule是否需要清理
					if (rule.isNeedClear()) {
						// 往rule中添加已上传的文件
						rule.addUploadFile(StrUtils.sanitizeFileName(uploadFileFileName), saveName, uploadPath,
								files.getSize());
					}*/

					ret.setStatus(FileUploadRet.SUCCESS_STATUS);
					ret.setFileSaveName(saveName);
					ret.setFileName(uploadFileFileName);
					ret.setFilePath(uploadPath);
					ret.setFileSize(Long.toString(files.getSize()));
					ret.setMsg("上传成功!");
					jsonArray.add(ret);
				} catch (FastDFSException e) {
					ret.setStatus(FileUploadRet.ERROR_STATUS);
					ret.setMsg("上传失败！");
					jsonArray.add(ret);
					e.printStackTrace();
				} catch (IOException e) {
					ret.setStatus(FileUploadRet.ERROR_STATUS);
					ret.setMsg("上传失败！");
					jsonArray.add(ret);
					e.printStackTrace();
				}
			}
		}
		return JSONArray.toJSONString(jsonArray);
	}
	


	public IFastdfsClient getFastdfsClient() {
		return fastdfsClient;
	}

	public void setFastdfsClient(IFastdfsClient fastdfsClient) {
		this.fastdfsClient = fastdfsClient;
	}

	
	
}
