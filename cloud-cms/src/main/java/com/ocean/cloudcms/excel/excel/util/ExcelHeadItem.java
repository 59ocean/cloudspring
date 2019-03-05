package com.ocean.cloudcms.excel.excel.util;

import java.lang.reflect.Field;
import java.util.List;

public class ExcelHeadItem {
	private String name;   //表头名称
	private String colCode; 
	private boolean required=false; //是否必须的
	private Integer length;         //数据长度要求
	private String dataType;        //数据类型
	private String textType;        //文本类型  如手机号码、邮箱等
	private Integer colIndex;       //数据在第几列
	private Field field;            //field属性
	private List<String> byName;    //别名

	public List<String> getByName() {
		return byName;
	}
	public void setByName(List<String> byName) {
		this.byName = byName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColCode() {
		return colCode;
	}
	public void setColCode(String colCode) {
		this.colCode = colCode;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getColIndex() {
		return colIndex;
	}
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getTextType() {
		return textType;
	}
	public void setTextType(String textType) {
		this.textType = textType;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	
	
}
