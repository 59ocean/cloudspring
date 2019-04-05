package com.ocean.fastdfs.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import com.ocean.fastdfs.config.*;
import com.ocean.fastdfs.exception.ErrorCode;
import com.ocean.fastdfs.exception.FastDFSException;
import com.ocean.fastdfs.util.FastDFSClientConstants;
import com.ocean.fastdfs.util.FastDFSClientUtil;
import org.springframework.util.StringUtils;


import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * 文件上传下载主类.
 *
 */
public class FastdfsClientImpl extends GenerateClientImpl implements IFastdfsClient {

	private ImageConfig imageConfig;

	public void setImageConfig(ImageConfig imageConfig) {
		this.imageConfig = imageConfig;
	}

	@Override
	public String uploadImage(InputStream inputStream, String filename,
							  Map<String, String> descriptions, RequestImageMarkConfig markConfig, RequestImageScaleConfig scaleConfig) {
		
		String fileExtName= FastDFSClientUtil.getFilenameSuffix(filename);
		// 检查是否能处理此类图片
		if (!FastDFSClientUtil.isSupportImage(fileExtName)) {
			throw new FastDFSException(ErrorCode.FILE_ERROR_IMAGE_SCALE.CODE,
					ErrorCode.FILE_ERROR_IMAGE_SCALE.MESSAGE + fileExtName);
		}

		byte[] bytes=null;
		try {
			bytes = FastDFSClientUtil.readInputStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE,
					ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
		}
		
		String path=null;
		byte[] markbytes=null;
		//如果没有加水印则主文件为原图,如果加了水印则主文件为水印图，原图作为从文件存放，后缀默认.orgin
		if (markConfig!=null&&markConfig.enable&&imageConfig.getMark().enable){
			markbytes=doImageMark(markConfig, new ByteArrayInputStream(bytes));
		}
		
		if(markbytes==null||markbytes.length==0){
			path=upload(bytes, filename, descriptions);
			markbytes=bytes;
		}else{
			path=upload(markbytes, filename, descriptions);
			if(imageConfig.getMark().saveOrigin){
				uploadSlaveFile(path, new ByteArrayInputStream(bytes), FastDFSClientConstants.ORIGIN_PREFIX_NAME , fileExtName);
			}
			bytes=null;
		}
		
		
		//是否上传大图
		if(imageConfig.getLarge().enable){
			byte[] largebytes=doLargeScale(new ByteArrayInputStream(markbytes), imageConfig.getLarge());
			if(largebytes!=null&&largebytes.length>0){
				uploadSlaveFile(path, new ByteArrayInputStream(largebytes), imageConfig.getLarge().getPrefixName(), fileExtName);
			}
			largebytes=null;
		}
		
		//是否开启压缩，压缩大小优先级别当前的高于系统
		if (scaleConfig!=null&&scaleConfig.enable&&imageConfig.getScale().enable){
			byte[] scalebytes= doImageScale(scaleConfig, new ByteArrayInputStream(markbytes));
			if(scalebytes!=null&&scalebytes.length>0){
				uploadSlaveFile(path, new ByteArrayInputStream(scalebytes), imageConfig.getScale().getPrefixName(), fileExtName);
			}
			scalebytes=null;
		}
		markbytes = null;
		return path;
	}

	/***
	 * 水印处理
	 * @param markConfig
	 * @param inputStream
	 * @return
	 */
	private byte[] doImageMark(RequestImageMarkConfig markConfig, InputStream inputStream) {
			ImageMarkConfig mark=imageConfig.getMark();
			//水印图片如果当前指定了路径使用指定路径否则使用全局水印路径
			String iconPath=StringUtils.isEmpty(markConfig.getIconPath())?mark.iconPath:markConfig.getIconPath();
			//如果没找到水印图片返回
			if (StringUtils.isEmpty(iconPath))
				return null;
			File iconFile = new File(iconPath);
			//如果没找到水印图片返回
			if(!iconFile.exists()) return null;
			// 在内存当中加水印
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			//@formatter:off
	        try {
				Thumbnails
				  .of(inputStream)
				  .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(iconFile), mark.alpha)
				  .toOutputStream(out);
				return out.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	        //@formatter:on
	}
	
	
	private byte[] doImageScale(RequestImageScaleConfig scaleConfig,InputStream inputStream){
		ImageScaleConfig scale=imageConfig.getScale();
		if(scaleConfig==null) return null;
		//必须同时开启压缩
		if (scaleConfig.enable||scale.enable) {
			int width=scale.getWidth();
			int height=scale.getHeight();
			//如果请求中带有压缩系数替换全局的
			if(scaleConfig.getHeight()>0&&scaleConfig.getWidth()>0){
				width=scaleConfig.getWidth();
				height=scaleConfig.getHeight();
			}
			//如果长或者宽未赋值返回
			if(width==0||height==0) return null;
			
			// 在内存当中加水印
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			//@formatter:off
	        try {
				Thumbnails
				  .of(inputStream)
				  .size(width, height)
				  .toOutputStream(out);
				return out.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	        //@formatter:on

		}
		return null;
		
	}
	
	/***
	 * 系统大图保存
	 * @param inputStream
	 * @param largeConfig
	 * @return
	 */
	private byte[] doLargeScale(InputStream inputStream,ImageLargeConfig largeConfig){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			//@formatter:off
	        try {
				Thumbnails
				  .of(inputStream)
				  .size(largeConfig.getWidth(), largeConfig.getHeight())
				  .outputQuality(largeConfig.alpha)
				  .toOutputStream(out);
				return out.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	        //@formatter:on
	}

}
