package com.ocean.cloudorder.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {

	@Autowired
	private RedisUtil redisUtil;
	@Resource(name="redisTemplate")
	private RedisTemplate<Serializable, Object> redisTemplate;

	public void setRedisTemplate(
			RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	/**
	 * 获取当个对象
	 * */
	public <T> T get(KeyPrefix prefix, String key,  Class<T> clazz) {
		try {
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;

			Object str = redisUtil.get(realKey);
			T t =  stringToBean(str.toString(), clazz);
			return t;
		}finally {
		}
	}

	/**
	 * 设置对象
	 * */
	public <T> boolean set(KeyPrefix prefix, String key,  T value) {
		try {
			String str = beanToString(value);
			if(str == null || str.length() <= 0) {
				return false;
			}
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			int seconds =  prefix.expireSeconds();
			if(seconds <= 0) {
				redisUtil.set(realKey, str);
			}else {
				redisUtil.set(realKey, str, Long.valueOf(seconds));
			}
			return true;
		}finally {
		}
	}

	/**
	 * 判断key是否存在
	 * */
	public <T> boolean exists(KeyPrefix prefix, String key) {
		try {
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			return  redisUtil.exists(realKey);
		}finally {
		}
	}

	/**
	 * 删除
	 * */
	public boolean delete(KeyPrefix prefix, String key) {
		try {
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			//long ret =  redisTemplate.(realKey);
			return redisTemplate.delete(realKey);
		}finally {
		}
	}

	/**
	 * 增加值
	 * */
	public <T> Long incr(KeyPrefix prefix, String key) {
		try {
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			return  redisUtil.incr(realKey);
		}finally {
		}
	}

	/**
	 * 减少值
	 * */
	public <T> Long decr(KeyPrefix prefix, String key) {
		
		try {
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			return  redisUtil.decr(realKey);
		}finally {
			
		}
	}




	public boolean delete(KeyPrefix prefix) {
		if(prefix == null) {
			return false;
		}
		List<String> keys =null;
				// keys = scanKeys(prefix.getPrefix());
		if(keys==null || keys.size() <= 0) {
			return true;
		}
		try {
			redisUtil.remove(keys.toArray(new String[0]));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		} finally {

		}
	}
//
//	public List<String> scanKeys(String key) {
//		try {
//			List<String> keys = new ArrayList<String>();
//			String cursor = "0";
//			ScanParams sp = new ScanParams();
//			sp.match("*"+key+"*");
//			sp.count(100);
//			do{
//				ScanResult<String> ret = redisUtil.scan(cursor, sp);
//				List<String> result = ret.getResult();
//				if(result!=null && result.size() > 0){
//					keys.addAll(result);
//				}
//				//再处理cursor
//				cursor = ret.getStringCursor();
//			}while(!cursor.equals("0"));
//			return keys;
//		} finally {
//			if (redisUtil != null) {
//				redisUtil.close();
//			}
//		}
//	}

	public static <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class) {
			return ""+value;
		}else if(clazz == String.class) {
			return (String)value;
		}else if(clazz == long.class || clazz == Long.class) {
			return ""+value;
		}else {
			return JSON.toJSONString(value);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T stringToBean(String str, Class<T> clazz) {
		if(str == null || str.length() <= 0 || clazz == null) {
			return null;
		}
		if(clazz == int.class || clazz == Integer.class) {
			return (T)Integer.valueOf(str);
		}else if(clazz == String.class) {
			return (T)str;
		}else if(clazz == long.class || clazz == Long.class) {
			return  (T)Long.valueOf(str);
		}else {
			return JSON.toJavaObject(JSON.parseObject(str), clazz);
		}
	}



}

