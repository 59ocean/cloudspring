package com.ocean.cloudcommon.utils;

import java.util.Map;

/**
 * @author chenhy
 * @date @time 2019/5/17 9:34
 */
public class ApiResponse {

	private static final long serialVersionUID = 1L;

	private int code;
	private String msg;
	private Object data;

	public ApiResponse () {
	}

	public ApiResponse (int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ApiResponse (int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static ApiResponse error() {
		return error(500, "操作失败");
	}

	public static ApiResponse operate(boolean b){
		if(b){
			return ApiResponse.ok();
		}
		return ApiResponse.error();
	}

	public static ApiResponse error(String msg) {
		return error(500, msg);
	}

	public static ApiResponse error(int code, String msg) {
		return new ApiResponse(code,msg);
	}

	public static ApiResponse ok(String msg) {
;
		return new ApiResponse(0,msg);
	}

	public static ApiResponse ok(Map<String, Object> map) {
		ApiResponse r = ok();
		r.setData(map);
		return r;
	}
	public static ApiResponse ok(Object data) {
		ApiResponse r = ok();
		r.setData(data);
		return r;
	}

	public static ApiResponse ok() {
		return new ApiResponse(0,"操作成功");
	}

	public static ApiResponse error401() {
		return error(401, "你还没有登录");
	}

	public static ApiResponse error403() {
		return error(403, "你没有访问权限");
	}



//	public static ApiResponse page(Object page){
//		return R.ok().put("page",page);
//	}

	public int getCode () {
		return code;
	}

	public void setCode (int code) {
		this.code = code;
	}

	public String getMsg () {
		return msg;
	}

	public void setMsg (String msg) {
		this.msg = msg;
	}

	public Object getData () {
		return data;
	}

	public void setData (Object data) {
		this.data = data;
	}
}
