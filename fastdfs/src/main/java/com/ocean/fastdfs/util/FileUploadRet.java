package com.ocean.fastdfs.util;

import java.util.Date;

public class FileUploadRet {
	public static final String ERROR_STATUS = "-1";
	public static final String SUCCESS_STATUS = "1";
	private String msg; //返回提示
	private String status;//返回状态  上传成功 1 上传失败 -1
	private String filePath;//文件路径
	private String fileSize;//文件大小
	private String fileSaveName;//文件保存名称
	private String fileName;//文件原始名称
	private Date createTime;//文件创建时间
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileSaveName() {
		return fileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
