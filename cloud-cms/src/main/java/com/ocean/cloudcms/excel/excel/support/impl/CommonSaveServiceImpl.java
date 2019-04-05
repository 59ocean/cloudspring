package com.ocean.cloudcms.excel.excel.support.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ocean.cloudcms.excel.excel.support.ICommonSaveService;
import org.apache.commons.pool2.BaseObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonSaveServiceImpl implements ICommonSaveService {

	public List<String> commonSave(List<Object> list,Class clazz) throws Exception{
		List<String> failData = new ArrayList<String>();
		try {
			for(int i = 0; i < list.size(); i++){
				Object model = list.get(i);
				if(model != null && !(model instanceof String)){
					Object obj = clazz.newInstance();
					BeanUtils.copyProperties(model, obj);
					//为id赋值
					Field field = clazz.getDeclaredField("id");
					field.setAccessible(true);
					field.set(obj, UUID.randomUUID().toString().replace("-", ""));
					//判断该实体类是否继承BaseObject
					if(obj instanceof BaseObject){
						 Field[] fields =  clazz.getSuperclass().getDeclaredFields();
						 for(Field f : fields){
							 if(f.getName().equals("deleteFlag")){
								 f.setAccessible(true);
								 f.set(obj, "N");
							 }
							 if(f.getName().equals("createdDt")){
								 f.setAccessible(true);
								 f.set(obj, new Date());
							 }
						 }
						
					 }
					boolean success = save(obj);
					if(!success){
						failData.add("第"+i+"行导入失败!");
					}
				}else{
					failData.add(model.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return failData;
	}
	
	@Transactional
	public boolean save(Object obj){
		try{
			//activityItemDao.saveOrUpdate(obj);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
