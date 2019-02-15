package com.ocean.cloudcms.controller;

import com.ocean.cloudcms.dto.FastDFSFileEntity;
import com.ocean.cloudcms.exception.ErrorCode;
import com.ocean.cloudcms.exception.FastDFSException;
import com.ocean.cloudcms.utils.CheckFileUtil;
import com.ocean.cloudcms.utils.FastDFSClient;
import com.ocean.cloudcms.utils.FileUtils;
import com.ocean.cloudcms.utils.ImageCompressUtil;
import com.ocean.cloudcommon.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/v1/cms/file")
@Api(tags = "文件上传接口")
public class FileController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
    /**
     * @Description  上传文件
     * @Date 2018/10/30 0030 15:07
     * @Param [file]
     **/
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public R upload(@RequestParam("file") MultipartFile file,@RequestParam(value = "isWatermark",required = false) boolean isWatermark) throws Exception {
        if (file.isEmpty()) {
            throw new FastDFSException(ErrorCode.FILE_ISNULL.CODE,"上传文件不能为空");
        }
        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf(".")+1);
        String url;
        //此处域名获取可以根据自需求编写
       // String domainUrl = OSSFactory.build().getDomainPath();
        String domainUrl = "http://106.14.116.177/";
        logger.info("配置的域名为"+domainUrl);
        if (StringUtils.isNotBlank(domainUrl)){
            if(CheckFileUtil.isImage(ext)){
                url = uploadImage(file,domainUrl,isWatermark);
            }else{
                url = uploadFile(file,domainUrl);
            }
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

    public String uploadFile(MultipartFile file,String domainName){
        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf(".")+1);
        byte[] file_buff=null;
        String[] fileAbsolutePath={};
        String path = "";
        try {
            //InputStream inputStream = file.getInputStream();
            //inputStream.close();
            file_buff = file.getBytes();
            FastDFSFileEntity fastDFSFileEntity=new FastDFSFileEntity(fileName,file_buff,ext);
            fileAbsolutePath = getStrings(fastDFSFileEntity);
            if(fileAbsolutePath == null){
                logger.error("upload file failed,please upload again!");
                throw new  FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE,"文件上传失败，请重新上传");
            }
            path=domainName+fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private String[] getStrings(FastDFSFileEntity fastDFSFileEntity) {
        String[] fileAbsolutePath;
        try {

            fileAbsolutePath= FastDFSClient.getInstance().upload(fastDFSFileEntity);
            logger.info(fileAbsolutePath.toString());
        }catch (Exception e){
            logger.error("upload file Exception!",e);
            throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE,ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
        }
        return fileAbsolutePath;
    }


    /**
     * @Author chenhy
     * @Description 上传文件到 FastDFS
     * @Date 2019/2/1 11:11
     * @Param [file]
     * @Param [domainName] 域名
     * @return path 文件访问路径
     **/
    public String uploadImage(MultipartFile file,String domainName,boolean isWatermark) throws IOException {

        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf(".")+1);
        byte[] file_buff=null;
        InputStream inputStream = file.getInputStream();
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream); //通过转出bufferImage来获得图片的宽度
            int imageWidth = bufferedImage.getWidth();
            int imageHeight = bufferedImage.getHeight();
            if(isWatermark){
                file_buff = ImageCompressUtil.setWatermark(file.getInputStream(),true,imageWidth,imageHeight);
            }else{
                file_buff = file.getBytes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
            //file1.delete();
        }
        /*if(inputStream!=null){
            int available = inputStream.available();
            file_buff=new byte[available];
            inputStream.read(file_buff);
        }*/
        FastDFSFileEntity fastDFSFileEntity=new FastDFSFileEntity(fileName,file_buff,ext);
        String[] fileAbsolutePath = getStrings(fastDFSFileEntity);
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
    public void downLoad(@RequestParam("url") String url, @RequestParam("fileName") String fileName, HttpServletResponse response, HttpServletRequest request) {
        OutputStream out = null;
        InputStream is = null;
        try {
            out = response.getOutputStream();
            byte[] bytes = null;
            is = FastDFSClient.getInstance().downFile(url);
            response.setContentType("multipart/form-data");
            String userAgent = request.getHeader("User-Agent");
            if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }else{
                fileName = URLEncoder.encode(fileName,"UTF-8");//其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            //is = new ByteArrayInputStream(bytes);
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
