package com.ocean.fastdfs.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ocean.fastdfs.conn.TrackerServerPool;
import com.ocean.fastdfs.exception.ErrorCode;
import com.ocean.fastdfs.exception.FastDFSException;
import com.ocean.fastdfs.util.FastDFSClientConstants;
import com.ocean.fastdfs.util.FastDFSClientUtil;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.springframework.util.StringUtils;


/***
 * 基础上传方法
 * 
 * @author Administrator
 *
 */
public class GenerateClientImpl implements IGenerateClient {

	private TrackerServerPool trackerServerPool;

	public String uploadSlaveFile(String masterFilename, InputStream inputStream, String prefixName, String fileExtName)
			throws FastDFSException {

		TrackerServer trackerServer = this.getTrackerServer();
		if (trackerServer == null) {
			throw new FastDFSException(ErrorCode.FILE_SERVER_CONNECTION_FAILED.CODE,
					ErrorCode.FILE_SERVER_CONNECTION_FAILED.MESSAGE);
		}

		StorageClient1 storageClient = new StorageClient1(trackerServer, null);
		String path = null;
		try {
			byte[] file_buff = FastDFSClientUtil.readInputStream(inputStream);
			path = storageClient.upload_file1(masterFilename, prefixName, file_buff, fileExtName, null);
			// 如果返回结果为空或者结果里面的路径为空代表上传失败
			if (StringUtils.isEmpty(path)) {
				throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE, ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
			}

		} catch (MyException e) {
			e.printStackTrace();
			throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE, ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE, ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
		}
		trackerServerPool.returnObject(trackerServer);
		return path;

	}

	/**
	 * 上传通用方法
	 *
	 * @param is
	 *            文件输入流
	 * @param filename
	 *            文件名
	 * @param descriptions
	 *            文件描述信息
	 * @return 组名+文件路径，如：group1/M00/00/00/wKgz6lnduTeAMdrcAAEoRmXZPp870.jpeg
	 * @throws FastDFSException
	 */
	public String upload(byte[] bytes, String filename, Map<String, String> descriptions) throws FastDFSException {
		if (bytes == null) {
			throw new FastDFSException(ErrorCode.FILE_ISNULL.CODE, ErrorCode.FILE_ISNULL.MESSAGE);
		}
		filename = FastDFSClientUtil.toLocal(filename);
		String suffix=FastDFSClientUtil.getFilenameSuffix(filename);
		
		if(StringUtils.isEmpty(suffix)){
			throw new FastDFSException(ErrorCode.FILE_SUFFIX_FAILED.CODE, ErrorCode.FILE_SUFFIX_FAILED.MESSAGE);
		}
		NameValuePair[] nvps=getNameValuePairs(filename, descriptions);
		TrackerServer trackerServer = this.getTrackerServer();
		StorageClient1 storageClient = new StorageClient1(trackerServer, null);
		String path=null;
		try {
			// 上传
			path = storageClient.upload_file1(bytes, suffix, nvps);
			if (StringUtils.isEmpty(path)) {
				throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE, ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE, ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
		} catch (MyException e) {
			e.printStackTrace();
			throw new FastDFSException(ErrorCode.FILE_UPLOAD_FAILED.CODE, ErrorCode.FILE_UPLOAD_FAILED.MESSAGE);
		}
		// 返还对象
		trackerServerPool.returnObject(trackerServer);

		return path;
	}

	

	/**
	 * 下载文件
	 *
	 * @param filepath
	 *            文件路径
	 * @return 返回文件字节
	 * @throws FastDFSException
	 */
	public byte[] download(String filepath) throws FastDFSException {
		if (StringUtils.isEmpty(filepath)) {
			throw new FastDFSException(ErrorCode.FILE_PATH_ISNULL.CODE, ErrorCode.FILE_PATH_ISNULL.MESSAGE);
		}

		TrackerServer trackerServer = this.getTrackerServer();
		StorageClient1 storageClient = new StorageClient1(trackerServer, null);
		byte[] fileByte = null;
		try {
			fileByte = storageClient.download_file1(filepath);
			if (fileByte == null) {
				throw new FastDFSException(ErrorCode.FILE_NOT_EXIST.CODE, ErrorCode.FILE_NOT_EXIST.MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
			throw new FastDFSException(ErrorCode.FILE_DOWNLOAD_FAILED.CODE, ErrorCode.FILE_DOWNLOAD_FAILED.MESSAGE);
		}
		// 返还对象
		trackerServerPool.returnObject(trackerServer);
		return fileByte;
	}

	/**
	 * 删除文件
	 *
	 * @param filepath
	 *            文件路径
	 * @return 删除成功返回 0, 失败返回其它
	 */
	public int deleteFile(String filepath) throws FastDFSException {
		if (StringUtils.isEmpty(filepath)) {
			throw new FastDFSException(ErrorCode.FILE_PATH_ISNULL.CODE, ErrorCode.FILE_PATH_ISNULL.MESSAGE);
		}

		TrackerServer trackerServer = this.getTrackerServer();
		StorageClient1 storageClient = new StorageClient1(trackerServer, null);
		int success = 0;
		try {
			success = storageClient.delete_file1(filepath);
			if (success != 0) {
				throw new FastDFSException(ErrorCode.FILE_DELETE_FAILED.CODE, ErrorCode.FILE_DELETE_FAILED.MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
			throw new FastDFSException(ErrorCode.FILE_DELETE_FAILED.CODE, ErrorCode.FILE_DELETE_FAILED.MESSAGE);
		}
		// 返还对象
		trackerServerPool.returnObject(trackerServer);

		return success;
	}

	/**
	 * 获取文件信息
	 * 
	 * @param filepath
	 *            文件路径
	 * @return 文件信息
	 * 
	 *         <pre>
	 *  {<br>
	 *      "SourceIpAddr": 源IP <br>
	 *      "FileSize": 文件大小 <br>
	 *      "CreateTime": 创建时间 <br>
	 *      "CRC32": 签名 <br>
	 *  }  <br>
	 *         </pre>
	 */
	public FileInfo getFileInfo(String filepath) throws FastDFSException {
		TrackerServer trackerServer = this.getTrackerServer();
		StorageClient1 storageClient = new StorageClient1(trackerServer, null);
		FileInfo fileInfo = null;
		try {
			fileInfo = storageClient.get_file_info1(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		// 返还对象
		trackerServerPool.returnObject(trackerServer);
		return fileInfo;
	}

	/***
	 * 获取TrackerServer
	 * 
	 * @return
	 */
	protected TrackerServer getTrackerServer() {
		try {
			return trackerServerPool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//将map转成NameValuePair[]数组
	protected NameValuePair[] getNameValuePairs(String filename, Map<String, String> descriptions) {
		// 文件描述
		NameValuePair[] nvps = null;
		List<NameValuePair> nvpsList = new ArrayList<NameValuePair>();
		// 文件名
		if (!StringUtils.isEmpty(filename)) {
			nvpsList.add(new NameValuePair(FastDFSClientConstants.FILENAME, filename));
		}

		// 描述信息
		if (descriptions != null && descriptions.size() > 0) {
			for (String key : descriptions.keySet()) {
				nvpsList.add(new NameValuePair(key, descriptions.get(key)));
			}
		}
		if (nvpsList.size() > 0) {
			nvps = new NameValuePair[nvpsList.size()];
			nvpsList.toArray(nvps);
		}
		
		return nvps;
	}
	
	public void setTrackerServerPool(TrackerServerPool trackerServerPool) {
		this.trackerServerPool = trackerServerPool;
	}

}
