package com.ocean.fastdfs.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

/***
 * fastdfs工具类
 * 
 * @author Administrator
 *
 */
public class FastDFSClientUtil {
	
    /** 支持的图片类型 */
    private static final String[] SUPPORT_IMAGE_TYPE = { "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP" };
    private static final List<String> SUPPORT_IMAGE_LIST = Arrays.asList(SUPPORT_IMAGE_TYPE);
    
	/***
	 * 读取inputstream流到byte[]数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	
	   /**
     * 获取文件名称的后缀
     *
     * @param filename 文件名 或 文件路径
     * @return 文件后缀
     */
    public static String getFilenameSuffix(String filename) {
        String suffix = null;
        if (!StringUtils.isEmpty(filename)) {
            if (filename.contains(FastDFSClientConstants.SEPARATOR)) {
                filename = filename.substring(filename.lastIndexOf(FastDFSClientConstants.SEPARATOR) + 1);
            }
            if (filename.contains(FastDFSClientConstants.POINT)) {
                suffix = filename.substring(filename.lastIndexOf(FastDFSClientConstants.POINT) + 1);
            }
        }
        return suffix;
    }


	/**
	 * 转换路径中的 '\' 为 '/' <br>
	 * 并把文件后缀转为小写
	 *
	 * @param path
	 *            路径
	 * @return
	 */
	public static String toLocal(String path) {
		if (!StringUtils.isEmpty(path)) {
			path = path.replaceAll("\\\\", FastDFSClientConstants.SEPARATOR);

			if (path.contains(FastDFSClientConstants.POINT)) {
				String pre = path.substring(0, path.lastIndexOf(FastDFSClientConstants.POINT) + 1);
				String suffix = path.substring(path.lastIndexOf(FastDFSClientConstants.POINT) + 1).toLowerCase();
				path = pre + suffix;
			}
		}
		return path;
	}
	
	 /**
     * 是否是支持的图片文件
     * 
     * @param fileExtName
     * @return
     */
    public static boolean isSupportImage(String fileExtName) {
        return SUPPORT_IMAGE_LIST.contains(fileExtName.toUpperCase());
    }
   
}
