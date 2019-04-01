package com.ocean.fastdfs.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 上传规则定义类。
 * 
 * 在上传之前将上传规则对象保存在session中，之后编辑器或其他上传对象将根据上传规则上传文件。
 * 
 * 编辑器浏览服务器的根路径：rootPath。模板制作时需要指定根路径，以便上传图片。
 * 
 * 定义上传路径
 * 
 * @author liufang
 * 
 */
public class UploadRule implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// 由于log没有实现序列化，会导致session对象不能序列化，所以注释掉
	// private Logger log = Logger.getLogger(getClass());

	public static final String ACCESS_SCOPE_PUBLIC = "public"; // 公共访问
	public static final String ACCESS_SCOPE_PRIVATE = "private"; // 仅限于文件所属者访问
	public static final String ACCESS_SCOPE_PROTECTED = "protected";// 受限访问，需验证是否有访问授权
	/**
	 * 在session中的key
	 */
	public static final String KEY = "_upload_rule";

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 * @param pathPrefix
	 *            路径前缀
	 * @param isGenName
	 *            是否创建随机文件名
	 * @param hasType
	 *            是否区分文件类别。模板制作时不需要，其他情况下需要
	 * @param needClear
	 *            是否需要清除未使用的上传文件
	 */
	public UploadRule(String rootPath, String pathPrefix, boolean isGenName, boolean hasType, boolean needClear) {
		this.rootPath = rootPath;
		this.pathPrefix = pathPrefix;
		this.isGenName = isGenName;
		this.hasType = hasType;
		this.needClear = needClear;
	}

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 * @param isGenName
	 *            是否创建随机文件名
	 * @param hasType
	 *            是否区分文件类别。模板制作时不需要，其他情况下需要
	 */

	public UploadRule(String rootPath, String pathPrefix, boolean isGenName, boolean hasType) {
		this(rootPath, pathPrefix, isGenName, hasType, true);
	}

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 * @param isGenName
	 *            是否创建随机文件名
	 */

	public UploadRule(String rootPath, String pathPrefix, boolean isGenName) {
		this(rootPath, pathPrefix, isGenName, true, true);
	}

	/**
	 * 构造器
	 * 
	 * @param rootPath
	 *            根路径。浏览服务器的根路径。上传时可能需要再加上类别路径
	 */

	public UploadRule(String rootPath, String pathPrefix) {
		this(rootPath, pathPrefix, true, true, true);
	}

	/**
	 * 获得文件全名
	 * 
	 * 目录前缀/年+季度/月+日/文件名.suffix
	 * 
	 * @return
	 */
	public String getPathName(String fileName, String suffix, String type) {
		StringBuilder sb = new StringBuilder(getPathPrefix()).append(type).append(genFilePath());
		if (isGenName) {
			sb.append(genFileName());
		} else {
			sb.append(fileName);
		}
		sb.append('.').append(suffix);
		return sb.toString();
	}

	/**
	 * 按当前日期生产路径：/2008_2/5_20/，/年_季/月_日/
	 * 
	 * @return
	 */
	public static String genFilePath() {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		sb.append(FastDFSClientConstants.SEPARATOR).append(cal.get(Calendar.YEAR)).append('_')
				.append(cal.get((Calendar.MONTH)) / 3 + 1).append(FastDFSClientConstants.SEPARATOR)
				.append(cal.get(Calendar.MONTH) + 1).append('_').append(cal.get(Calendar.DAY_OF_MONTH))
				.append(FastDFSClientConstants.SEPARATOR);
		return sb.toString();
	}

	/**
	 * 获得文件名
	 * 
	 * 4位随机数加上当前时间
	 * 
	 * @return
	 */
	public static String genFileName() {
		String name = StrUtils.longToN36(System.currentTimeMillis());
		return RandomStringUtils.random(4, StrUtils.N36_CHARS) + name;
	}

	public void setUploadFiles(Map<String, UploadFile> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	/**
	 * 获得可图片的后缀，如没有指定，则使用默认的后缀集合。
	 * 
	 * @return
	 */
	public Set<String> getAcceptImg() {
		if (acceptImg == null) {
			acceptImg = new HashSet<String>();
			for (String s : DEF_IMG_ACCEPT) {
				acceptImg.add(s);
			}
		}
		return acceptImg;
	}

	public boolean acceptTypeUpload(String suffix) {
		if(StringUtils.isNotBlank(suffix)){
			suffix = suffix.toLowerCase();
		}
		boolean re = true;
		if (this.fileFilter && !this.getAcceptAttach().contains(suffix)) {
			re = false;
		}
		return re;
	}

	public void setAcceptAttach(Set<String> acceptAttach) {
		this.acceptAttach = acceptAttach;
	}

	/**
	 * 获得可附件的后缀，如没有指定，则使用默认的后缀集合。
	 * 
	 * @return
	 */
	public Set<String> getAcceptAttach() {
		if (acceptAttach == null) {
			acceptAttach = new HashSet<String>();
			for (String s : DEF_ATTACH_ACCEPT) {
				acceptAttach.add(s);
			}
		}
		return acceptAttach;
	}

	public void addUploadFile(String origName, String fileName, String realPath, long size) {
		if (uploadFiles == null) {
			uploadFiles = new HashMap<String, UploadFile>();
		}
		uploadFiles.put(fileName, new UploadFile(origName, fileName, realPath, size));
	}

	public void addUploadFile(String origName, String fileName, String realPath, long size, Date createTime) {
		if (uploadFiles == null) {
			uploadFiles = new HashMap<String, UploadFile>();
		}
		uploadFiles.put(fileName, new UploadFile(origName, fileName, realPath, size, createTime));
	}

	public void addUploadFile(String origName, String fileName, String realPath, long size, int uploadNum) {
		if (uploadFiles == null) {
			uploadFiles = new HashMap<String, UploadFile>();
		}
		uploadFiles.put(fileName, new UploadFile(origName, fileName, realPath, size, uploadNum));
	}

	public void addUploadFile(String origName, String fileName, String realPath, long size, int uploadNum,
			Date createTime) {
		if (uploadFiles == null) {
			uploadFiles = new HashMap<String, UploadFile>();
		}
		uploadFiles.put(fileName, new UploadFile(origName, fileName, realPath, size, uploadNum, createTime));
	}

	public void removeUploadFile(String fileName) {
		if (uploadFiles != null) {
			uploadFiles.remove(fileName);
		}
	}

	public Map<String, UploadFile> getUploadFiles() {
		return uploadFiles;
	}

	public void clearUploadFile() {
		if (uploadFiles != null && needClear) {
			for (UploadFile uf : uploadFiles.values()) {
				File file = new File(uf.getRealPath());
				// if (file.delete()) {
				// log.debug("删除未被使用的文件：{}"+ file.getName());
				// } else {
				// log.warn("删除文件失败：{}"+ file.getName());
				// }
			}
			uploadFiles.clear();
		}
	}

	/**
	 * 已经上传到图片
	 */
	private Map<String, UploadFile> uploadFiles;

	/**
	 * 可以上传的文件后缀
	 */
	private Set<String> acceptImg;

	private Set<String> acceptAttach;
	/**
	 * 编辑器浏览服务器的根路径，也是上传的根路径
	 */
	private String rootPath;

	private String pathPrefix;
	/**
	 * 是否生成文件名
	 */
	private boolean isGenName = true;
	/**
	 * 是否区分文件类型（用于编辑器浏览服务器时使用）
	 */
	private boolean hasType = true;
	/**
	 * 是否需要清理
	 */
	private boolean needClear = true;
	private boolean fileFilter = true;

	public boolean isFileFilter() {
		return fileFilter;
	}

	public void setFileFilter(boolean fileFilter) {
		this.fileFilter = fileFilter;
	}

	/**
	 * 是否允许浏览文件
	 */
	private boolean allowFileBrowsing = true;
	/**
	 * 是否允许上传文件
	 */
	private boolean allowUpload = true;
	/**
	 * 允许上传的大小。0不允许上传，-1不受限制
	 */
	private int allowSize = -1;
	/**
	 * 已上传大小
	 */
	private int uploadSize = 0;

	/**
	 * 设置是否打开上传v
	 */
	private boolean uploadTypeLimit = true;
	/**
	 * 默认的可上传文件后缀
	 */
	// 先去掉png ，png会导致一个问题。
	public static final String[] DEF_IMG_ACCEPT = { "jpg", "gif", "jpeg", "bmp", "xml", "png" };
	/**
	 * 默认的可上传附件文件后缀
	 */
	public static final String[] DEF_ATTACH_ACCEPT = { "ppt", "pptx", "doc", "rar", "zip", "docx", "xls", "xlsx", "pdf",
			"txt", "dxf", "dwg", "hyd", "pcb", "jpg", "jpeg", "gif", "xml","png" };

	public boolean isGenName() {
		return isGenName;
	}

	public void setGenName(boolean isGenName) {
		this.isGenName = isGenName;
	}

	public void setAcceptImg(Set<String> acceptImg) {
		this.acceptImg = acceptImg;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public boolean isHasType() {
		return hasType;
	}

	public void setHasType(boolean hasType) {
		this.hasType = hasType;
	}

	public boolean isNeedClear() {
		return needClear;
	}

	public void setNeedClear(boolean needClear) {
		this.needClear = needClear;
	}

	public static class UploadFile implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		public UploadFile() {
		}

		public UploadFile(String origName, String fileName, String realPath, long size) {
			this.origName = origName;
			this.fileName = fileName;
			this.realPath = realPath;
			this.size = size;
		}

		public UploadFile(String origName, String fileName, String realPath, long size, Date createTime) {
			this.origName = origName;
			this.fileName = fileName;
			this.realPath = realPath;
			this.size = size;
			this.createTime = createTime;
		}

		public UploadFile(String origName, String fileName, String realPath, long size, int uploadNum) {
			this.origName = origName;
			this.fileName = fileName;
			this.realPath = realPath;
			this.size = size;
			this.uploadNum = uploadNum;
		}

		public UploadFile(String origName, String fileName, String realPath, long size, int uploadNum,
				Date createTime) {
			this.origName = origName;
			this.fileName = fileName;
			this.realPath = realPath;
			this.size = size;
			this.uploadNum = uploadNum;
			this.createTime = createTime;
		}

		public String getRelPath(String pathRoot) {
			String real = getRealPath();
			real = StringUtils.replace(real, File.separator, "/");
			real = StringUtils.replace(real, pathRoot, "");
			return real;
		}

		private String origName;
		private String fileName;
		private String realPath;
		private Date createTime;
		/**
		 * 上传编号
		 */
		private int uploadNum;
		/** 文件状态，1表示正常的 -1表示逻辑删除 **/
		private int fileStatus = 1;

		private long size;

		public String getOrigName() {
			return origName;
		}

		public void setOrigName(String origName) {
			this.origName = origName;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getRealPath() {
			return realPath;
		}

		public void setRealPath(String realPath) {
			this.realPath = realPath;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public int getUploadNum() {
			return uploadNum;
		}

		public void setUploadNum(int uploadNum) {
			this.uploadNum = uploadNum;
		}

		public int getFileStatus() {
			return fileStatus;
		}

		public void setFileStatus(int fileStatus) {
			this.fileStatus = fileStatus;
		}

	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	public boolean isAllowFileBrowsing() {
		return allowFileBrowsing;
	}

	public void setAllowFileBrowsing(boolean allowFileBrowsing) {
		this.allowFileBrowsing = allowFileBrowsing;
	}

	public int getUploadSize() {
		return uploadSize;
	}

	public void setUploadSize(int uploadSize) {
		this.uploadSize = uploadSize;
	}

	public boolean isAllowUpload() {
		return allowUpload;
	}

	public void setAllowUpload(boolean allowUpload) {
		this.allowUpload = allowUpload;
	}

	public int getAllowSize() {
		return allowSize;
	}

	public void setAllowSize(int allowSize) {
		this.allowSize = allowSize;
	}
}
