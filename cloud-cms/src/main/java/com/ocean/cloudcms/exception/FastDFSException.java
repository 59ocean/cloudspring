package com.ocean.cloudcms.exception;

/**
 * FastDFS 上传下载时可能出现的一些异常信息
 * <p>
 *
 * @version 1.0
 * @name FastDFSException
 * @date 2017-10-13 15:12
 */
public class FastDFSException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    public FastDFSException(){}

    public FastDFSException(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
