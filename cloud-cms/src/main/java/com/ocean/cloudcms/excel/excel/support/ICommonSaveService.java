package com.ocean.cloudcms.excel.excel.support;

import java.util.List;


public interface ICommonSaveService{
	
	public List<String> commonSave(List<Object> list, Class clazz) throws Exception;
}
