package com.ocean.cloudorder.redis;

public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
