package com.ocean.cloudcms.controller;

import com.ocean.cloudcms.Dto.FastDFSFileEntity;
import com.ocean.cloudcms.exception.ErrorCode;
import com.ocean.cloudcms.exception.FastDFSException;
import com.ocean.cloudcms.utils.FastDFSClient;
import com.ocean.cloudcommon.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/v1/cms/file")
@Api(tags = "文件上传接口")
public class FileController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
    /**
     * @Author maoqitian
     * @Description  上传文件
     * @Date 2018/10/30 0030 15:07
     * @Param [file]
     * @return com.gxxmt.common.utils.ResultApi
     **/
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new FastDFSException(ErrorCode.FILE_ISNULL.CODE,"上传文件不能为空");
        }
        String url;
        //此处域名获取可以根据自需求编写
       // String domainUrl = OSSFactory.build().getDomainPath();
        String domainUrl = "http://106.14.116.177/";
        logger.info("配置的域名为"+domainUrl);
        if (StringUtils.isNotBlank(domainUrl)){
            url = uploadFile(file,domainUrl);
            return R.ok().put("url",url);
        }else {
            return R.error("域名配置为空,请先配置对象存储域名");
        }
    }

    /**
     * @Author chenhy
     * @Description 上传文件到 FastDFS
     * @Date 2019/2/1 11:11
     * @Param [file]
     * @Param [domainName] 域名
     * @return path 文件访问路径
     **/
    public String uploadFile(MultipartFile file,String domainName) throws IOException {

        String[] fileAbsolutePath={};
        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf(".")+1);
        byte[] file_buff=null;
        InputStream inputStream = file.getInputStream();
        if(inputStream!=null){
            int available = inputStream.available();
            file_buff=new byte[available];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFileEntity fastDFSFileEntity=new FastDFSFileEntity(fileName,file_buff,ext);
        try {
            fileAbsolutePath=FastDFSClient.getInstance().upload(fastDFSFileEntity);
            logger.info(fileAbsolutePath.toString());
        }catch (Exception e){
            logger.error("upload file Exception!",e);
            throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE,ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
        }
        if(fileAbsolutePath == null){
            logger.error("upload file failed,please upload again!");
            throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE,"文件上传失败，请重新上传");
        }
        String path=domainName+fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
        return path;
    }
    @GetMapping("/test")
    @ApiOperation(value = "测试接口",notes = "ceshijiek")
    public void tees(){
        System.out.println("dddd");
    }

    @RequestMapping(value="/downLoad",method=RequestMethod.GET)
    public void downLoad(@RequestParam(name = "groupName", required = true) String groupName,
                         @RequestParam(name = "remoteFileName", required = true) String remoteFileName, HttpServletResponse response) {
        OutputStream out = null;
        InputStream is = null;
        try {
            out = response.getOutputStream();
            byte[] bytes = null;
                //bytes = FastDFSClient.getInstance().downFile2("group1/M00/00/00/rBMwl1xUBViACpsfAAKZSVBf_dc075.jpg","rBMwl1xUBViACpsfAAKZSVBf_dc075.jpg");
                bytes = FastDFSClient.getInstance().downFile2(groupName,remoteFileName);
            response.setContentType("application/octet-stream;charset=UTF-8");
            // Content-Disposition
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(remoteFileName.getBytes(), "ISO-8859-1"));
            is = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024 * 5];
            int len = 0;
            while ((len = is.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();

        } catch (Exception e) {
            logger.info("下载失败",e);
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (is != null) {
                    is.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
