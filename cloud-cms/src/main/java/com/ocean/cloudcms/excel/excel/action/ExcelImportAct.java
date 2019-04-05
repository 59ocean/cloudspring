package com.ocean.cloudcms.excel.excel.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.ocean.cloudcms.excel.excel.ExcelProcess;
import com.ocean.cloudcms.excel.excel.util.ExcelUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;





@Controller
@RequestMapping("excel")
public class ExcelImportAct {
	/*@Autowired
	private ExcelProcess excelProcess;
	
	//直接导入数据库方式的执行action
	@RequestMapping("import")
	public ModelAndView importExcel(HttpServletRequest request,@RequestParam(value="excelType" ,defaultValue="1") String excelType) throws Exception{
		String url = request.getParameter("urlAdr");
		ModelAndView mav = new ModelAndView(url);
		String info = "";
		try {
			Map<String,String> map = uploadFile(request);
			String realPath = map.get("realPath");
			//1:简单Excel的导入 2：复杂Excel的导入
			if(excelType.equals("1")){
				info = importSimpleExcel(realPath,request);
			}else{
				info = importComplexExcel(realPath, request);
			}
			mav.addObject("info",info);
		} catch (Exception e) {
			info = "上传失败！信息:" + e.getMessage();
			mav.addObject("info", info);
			return mav;
		}
		return mav;
	}
	
	//不直接导入数据库  自定义返回数据
	@RequestMapping("importTwo")
	public ModelAndView importExcelTwo(HttpServletRequest request) throws Exception{
		String url = request.getParameter("urlAdr");
		ModelAndView mav = new ModelAndView(url);
		String info = "";
		String template = request.getParameter("template");
		try {
			Map<String,String> map2 = uploadFile(request);
			request.setAttribute("realPath", map2.get("realPath"));
			//excel 表数据
			List<Object> list = ExcelUtil.processSimple(request);
			Map<String,Object> map = excelProcess.getData(list, template,request);
			//根据业务自定义要返回到页面的数据
			for(Entry<String, Object> entry : map.entrySet()){
	            mav.addObject(entry.getKey(), entry.getValue());
	        }
			mav.addObject("uploadPath",map2.get("uploadPath"));
			mav.addObject("uploadRuleId", map2.get("uploadRuleId"));
		} catch (Exception e) {
			e.printStackTrace();
			info = "上传失败！信息:" + e.getMessage();
			mav.addObject("error", info);
			return mav;
		}
		return mav;
	}

	//不直接导入数据库 返回固定的数据
	@RequestMapping("importFix")
	public ModelAndView importFix(HttpServletRequest request) throws Exception{
		String url = request.getParameter("urlAdr");
		ModelAndView mav = new ModelAndView(url);
		String info = "";
		try {
			Map<String,String> map2 = uploadFile(request);
			request.setAttribute("realPath", map2.get("realPath"));
			//excel 表数据
			List<Object> list = ExcelUtil.processSimple(request);
			Map<String,Object> map = new HashMap<String, Object>();
			//得到正确数据和错误信息
			Map<String,Object> map3 = getData(list);
			map.putAll(map3);
			map.putAll(map2);
			for(Entry<String, Object> entry : map.entrySet()){
	            mav.addObject(entry.getKey(), entry.getValue());
	        }
		} catch (Exception e) {
			e.printStackTrace();
			info = "上传失败！信息:" + e.getMessage();
			mav.addObject("error", info);
			return mav;
		}
		return mav;
	}
	*//**
	 * 可以自定义返回数据的ajax上传
	 * @param request
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("ajaxImport")
	@ResponseBody
	public Json ajaxImportExcel(HttpServletRequest request) throws Exception{
		Json j = new Json();
		String template = request.getParameter("template");
		try{
			Map<String,String> map2 = uploadFile(request);
			request.setAttribute("realPath", map2.get("realPath"));
			//excel 表数据
			List<Object> list = ExcelUtil.processSimple(request);
			Map<String,Object> map = excelProcess.getData(list, template,request);
			map.put("uploadPath", map2.get("uploadPath"));
			j.setData(map);
			j.setRetCode(RetCode.OK);
		}catch(Exception e){
			e.printStackTrace();
			j.setRetCode(RetCode.FAIL_LOGIC);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	*//**
	 * 返回固定数据的 ajax上传
	 * @param request
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("fixAjaxImport")
	@ResponseBody
	public Json ajaxImportExcel2(HttpServletRequest request) throws Exception{
		Json j = new Json();
		String template = request.getParameter("template");
		try{
			Map<String,String> map2 = uploadFile(request);
			request.setAttribute("realPath", map2.get("realPath"));
			//excel 表数据
			List<Object> list = ExcelUtil.processSimple(request);
			Map<String,Object> map = new HashMap<String, Object>();
			//得到正确数据和错误信息
			Map<String,Object> map3 = getData(list);
			map.putAll(map3);
			map.put("uploadPath", map2.get("uploadPath"));
			j.setData(map);
			j.setRetCode(RetCode.OK);
		}catch(Exception e){
			e.printStackTrace();
			j.setRetCode(RetCode.FAIL_LOGIC);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	//得到正确数据跟错误信息
	public Map<String,Object> getData(List<Object> list){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Object> data = new ArrayList<Object>();
		String error = "";
		for(Object obj : list){
			//判断是错误信息还是数据
			if(!(obj instanceof String)){
				data.add(obj);
			}else{
				error = error +""+ obj.toString();
			}
		}
		//成功的数据
		map.put("data", JSONObject.toJSON(data));
		//错误信息
		if(error != ""){
			map.put("error", "导入失败，信息：请检查以下数据行 【"+error+"】并重新导入！");
		}
		map.put("info", "成功"+data.size()+"条，失败"+(list.size()-data.size())+"条");
		//错误数量
		map.put("errorNum", list.size()-data.size());
		//成功数量
		map.put("excelSize", data.size());
		return map;
	}
	
	
	//上传文件
	public Map<String,String> uploadFile(HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		//根据文件名加载文件
		MultipartFile uploadFile = mRequest.getFile("uploadFile");
		//获取文件名  如 基础数据.txt
		String uploadFileFileName = uploadFile.getOriginalFilename();
		String uploadRuleId= request.getParameter("uploadRuleId");
		*//*
		 * 上传规矩
		 * uploadRuleId  不知道这个参数从哪里来的
		 * UploadRule 上传规矩前缀、后缀、路径等信息
		 *//*
		UploadRule rule = (UploadRule) request.getSession().getAttribute(UploadRule.KEY + uploadRuleId);
		String error = UploadCheckUtil.checkUpload(uploadFileFileName, rule);
		if(error != null){
			throw new Exception(error);
		}
		int suffixIndex = uploadFileFileName.indexOf('.');
		String name = uploadFileFileName.substring(0, suffixIndex);
		String suffix = uploadFileFileName.substring(suffixIndex + 1).toLowerCase();
		//磁盘路径
		String uploadPath = rule.getPathName(name, suffix, Constants.UPLOAD_FILE);
		//磁盘真实路径
		String realPath = rule.getRootPath() + uploadPath;
		String uploadNum = request.getParameter("uploadNum");
		File file = new File(realPath);
		//写数据到磁盘上
		FileUtils.writeByteArrayToFile(file, uploadFile.getBytes());
		log.info("上传文件成功：{}" + realPath);
		*//*if (rule.isNeedClear()) {
			rule.addUploadFile(StringUtils.sanitizeFileName(uploadFileFileName), file.getName(),
					file.getAbsolutePath(), file.length());
		}*//*
		Map<String,String> map = new HashMap<String, String>();
		map.put("realPath", realPath);
		map.put("uploadPath", uploadPath);
		map.put("uploadRuleId", uploadRuleId);
		return map;
	}
	//导入简单Excel
	public String importSimpleExcel(String realPath,HttpServletRequest request) throws Exception{
		Map<String,Object> params = new HashMap<String, Object>();
		request.setAttribute("realPath", realPath);
		String template = request.getParameter("template");
		List<Object> list = ExcelUtil.processSimple(request);
		String info = null;
		//错误集合 
		List<String> failList = new ArrayList<String>();
		if(list != null && list.size()>0){
			failList = excelProcess.save(list,template,null,request);
		}else{
			throw new Exception("数据为空，请重新选择文件！");
		}
		info = getInfo(failList,list.size());
		return info;
	}
	//导入复杂Excel
	public String importComplexExcel(String realPath,HttpServletRequest request) throws Exception{
		request.setAttribute("realPath", realPath);
		String template = request.getParameter("template");
		Map<String, Object> map = ExcelUtil.processComplex(request);
		List<Object> list = (List<Object>)map.get("data");
		Object property = map.get("property");
		String info = null;
		List<String> failList = new ArrayList<String>();
		if(list != null && list.size()>0 && property != null){
			failList = excelProcess.save(list,template,property,request);
		}else{
			throw new Exception("数据为空，请重新选择文件！");
		}
		info = getInfo(failList,list.size());
		return info;
	}
	public String getInfo(List<String> failList,Integer sum){
		String info = null;
		if(failList !=null && failList.size() > 0){
			info = "成功"+(sum-failList.size()) + "条，失败"+failList.size()+"条，请检查以下数据行"+failList.toString()+"，并重新导入这些数据";
		}else{
			info = "全部导入成功"+sum+"条！";
		}
		return info;
	}
	public String getError(List<Integer> failList){
		String info = null;
		if(failList !=null && failList.size() > 0){
			info = "导入失败,信息：请检查以下数据行"+failList.toString()+"的数据格式，如长度等，并重新导入！";
		}
		return info;
	}*/
}
