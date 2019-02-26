package com.ocean.cloudcms.exception;

public class ExceptionInfo {
	/**其它异常**/
	public static final String BIZ_OTHER_EXCEPTION="0";
	/**业务处理异常**/
	public static final String BIZ_SERVICE_EXCEPTION="1";
	/**SQL异常**/
	public static final String BIZ_SQL_EXCEPTION="2";
	/**参数校验异常**/
	public static final String BIZ_PARAM_VLD_EXCEPTION="3";

	
	/***
	 * 错误编码
	 */
	private String retCode;
	/***
	 * 错误信息
	 */
	private String msg;
	/***
	 * 异常类型
	 */
	private String exceptionType;
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
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	
	
	
	
}
