package com.ocean.cloudcms.excel.excel.model;


import com.ocean.cloudcms.excel.excel.util.ExcelField;

public class XjdItemModel {
	@ExcelField(columnName="材料名称")
	private String matName;//材料名称
	@ExcelField(columnName="规格型号")
	private String standard;//规格型号
	@ExcelField(columnName="单位")	
	private String units;//单位
	@ExcelField(columnName="工程用量")
	private String matNum;//工程用量
	@ExcelField(columnName="品牌")
	private String brand;//品牌
	@ExcelField(columnName="详细参数")
	private String param;//详细参数
	@ExcelField(columnName="备注")
	private String remark;//备注
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getMatNum() {
		return matNum;
	}
	public void setMatNum(String matNum) {
		this.matNum = matNum;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "XjdItemModel [matName=" + matName + ", standard=" + standard
				+ ", units=" + units + ", matNum=" + matNum + ", brand="
				+ brand + ", param=" + param + ", remark=" + remark + "]";
	}
	
}
