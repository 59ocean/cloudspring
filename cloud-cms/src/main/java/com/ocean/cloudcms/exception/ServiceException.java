package com.ocean.cloudcms.exception;

/**
 * 调用服务逻辑异常
 * <p/>
 * @author chenhy
 */
public class ServiceException extends RuntimeException implements	BaseException, RetCodeSupport {

	private static final long serialVersionUID = -4619099372788244617L;
	


	/**
	 * 数据库操作,findUnique返回null
	 */
	public static final ServiceException DB_QUERYONE_IS_NULL = new ServiceException(RetCode.DB_QUERYONE_IS_NULL, "数据库操作,findUnique 返回null");


	/**
	 * 错误码
	 */
	protected String retCode = RetCode.FAIL_LOGIC;
	/**
	 * 异常信息
	 */
	protected String msg;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String retCode, String message) {
		this(message);
		this.retCode = retCode;
	}

	public ServiceException(String retCode, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.retCode = retCode;
		this.msg = String.format(msgFormat, args);
	}

	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ServiceException(String retCode, String message, Throwable throwable) {
		this(message, throwable);
		this.retCode = retCode;
	}
	@Override
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
