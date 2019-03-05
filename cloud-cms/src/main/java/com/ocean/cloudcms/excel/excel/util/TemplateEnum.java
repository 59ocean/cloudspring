package com.ocean.cloudcms.excel.excel.util;

public enum TemplateEnum {
	SUPPLIER_TEMPLATE("supplier-template","供应商模板"),
	MAT_TEMPLATE("mat-template","采购材料模板"),
	LITTLE_MAT_TEMPLATE("little-mat-template","采购材料模板"),
	PRICE_TEMPLATE("price-template","价格模板");
	TemplateEnum(String value,String desc) {
		this.desc = desc;
		this.value = value;
	}
	private String desc;
	private String value;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static TemplateEnum getEnum(String value) {
		TemplateEnum resultEnum = null;
		TemplateEnum[] enumAry = TemplateEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue().equals(value)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
}
