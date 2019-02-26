package com.ocean.cloudcms.exception;

/**
 * 通用错误码
 *
 * @Author leaf
 * 2015/8/28 0028 1:08.
 */
public abstract class RetCode {

    /**
     * 成功
     */
    public final static String OK = "000000";

    /**
     * 未知异常
     */
    public final static String FAIL_UNKNOWN = "999999";

    /**
     * 逻辑错误
     */
    public final static String FAIL_LOGIC = "999998";

    /**
     * 参数异常
     */
    public final static String FAIL_PARAM = "999997";

    /**
     * 缓存操作异常
     */
    public final static String FAIL_CACHE = "999996";

    /**
     * 数据库操作异常
     */
    public final static String FAIL_DB = "999995";
    
    /**
     * 数据库查询单个对象为空
     */
    public final static String DB_QUERYONE_IS_NULL = "905001";


    /**
     * 远程访问异常
     */
    public final static String FAIL_REMOTE = "999994";
    
    
    /** 参数为空*/
	public static final String PARAM_NULL_POINT="100000";
	
	/** 参数验证失败*/
	public static final String PARAM_CHECK_FAIL="100001";
	
	/**业务逻辑执行失败**/
	public static final String BUSINESS_EXCUTE_FAIL="200000";
	
	 /**
     * 数据库操作异常
     */
    public final static String DB_EXCUTE_FAIL = "200001";
    
    /**
     * 其它未知异常
     */
    public final static String UNKNOW_EXCEPTION = "200002";
    
    /**
     * url connection连接异常  9xxxxx代表网络异常
     */
    public final static String URL_CONNECTION_FAIL = "900000";
    
    /**
     * socket timeout连接异常  9xxxxx代表网络异常
     */
    public final static String SOCKET_TIMEOUT = "900001";
    
    /**
     * ribin 负载均衡调用微服务异常  9xxxxx代表网络异常
     */
    public final static String LOADBALANCE_FAIL = "900002";
    
    /**
     * zuul filter 未知异常  9xxxxx代表网络异常
     */
    public final static String ZUUL_FILTER_EXCEPTION = "900003";
    
    /**
     * 访问接口4xx异常  9xxxxx代表网络异常
     */
    public final static String HTTP_4XX_EXCEPTION = "900004";
    

}
