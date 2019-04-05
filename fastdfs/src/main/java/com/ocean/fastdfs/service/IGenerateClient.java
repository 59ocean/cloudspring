package com.ocean.fastdfs.service;

import java.io.InputStream;
import java.util.Map;

import com.ocean.fastdfs.exception.FastDFSException;
import org.csource.fastdfs.FileInfo;


public interface IGenerateClient {
    /**
     * 上传从文件,返回路径
     * 
     * @param masterFilename
     * @param inputStream
     * @param prefixName
     * @param fileExtName
     * @return
     */
    String uploadSlaveFile(String masterFilename, InputStream inputStream,
                           String prefixName, String fileExtName) throws FastDFSException;
    
    
    /**
     * 上传通用方法
     *
     * @param filename 文件名
     * @param descriptions 文件描述信息
     * @return 组名+文件路径，如：group1/M00/00/00/wKgz6lnduTeAMdrcAAEoRmXZPp870.jpeg
     * @throws FastDFSException
     */
    public String upload(byte[] bytes, String filename, Map<String, String> descriptions) throws FastDFSException;

    /**
     * 删除文件
     *
     * @param filepath 文件路径
     * @return 删除成功返回 0, 失败返回其它
     */
    public int deleteFile(String filepath) throws FastDFSException;
    
    
    /**
     * 下载文件
     *
     * @param filepath 文件路径
     * @return 返回文件字节
     * @throws FastDFSException
     */
    public byte[] download(String filepath) throws FastDFSException;
    
    /**
     * 获取文件信息
     * 
     * @param filepath 文件路径
     * @return 文件信息
     * 
     * <pre>
     *  {<br>
     *      "SourceIpAddr": 源IP <br>
     *      "FileSize": 文件大小 <br>
     *      "CreateTime": 创建时间 <br>
     *      "CRC32": 签名 <br>
     *  }  <br>
     * </pre>
     */
    public FileInfo getFileInfo(String filepath) throws FastDFSException;
}
