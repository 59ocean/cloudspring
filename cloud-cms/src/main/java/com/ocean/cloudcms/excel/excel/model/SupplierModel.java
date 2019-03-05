package com.ocean.cloudcms.excel.excel.model;


import com.ocean.cloudcms.excel.excel.util.ExcelField;

public class SupplierModel {
	@ExcelField(columnName="公司名称（*）")
	private String companyName;	//供应商名称
	@ExcelField(columnName="主营业务")
	private String majorIndustry;//供应商主营材料分类名称   
	@ExcelField(columnName="企业负责人")
	private String companyBoss;	//企业负责人
	@ExcelField(columnName="注册资金(万元)")
	private String companyMoney;	//注册资金
	@ExcelField(columnName="地址")
	private String companyAddr;		//公司地址
	@ExcelField(columnName="联系人（*）")
	private String contacter;	//联系人
	@ExcelField(columnName="电话号码（*）")
	private String mobilePhone;	//联系手机号码
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getMajorIndustry() {
		return majorIndustry;
	}
	public void setMajorIndustry(String majorIndustry) {
		this.majorIndustry = majorIndustry;
	}
	public String getCompanyBoss() {
		return companyBoss;
	}
	public void setCompanyBoss(String companyBoss) {
		this.companyBoss = companyBoss;
	}
	public String getCompanyMoney() {
		return companyMoney;
	}
	public void setCompanyMoney(String companyMoney) {
		this.companyMoney = companyMoney;
	}
	public String getCompanyAddr() {
		return companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public String getContacter() {
		return contacter;
	}
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
 @Override
	public String toString() {
		return "SupplierModel [companyName=" + companyName + ", majorIndustry="
				+ majorIndustry + ", companyBoss=" + companyBoss
				+ ", companyMoney=" + companyMoney + ", companyAddr="
				+ companyAddr + ", contacter=" + contacter + ", mobilePhone="
				+ mobilePhone + "]";
	}
 
 @Override
public int hashCode() {
	// TODO Auto-generated method stub
	return companyName.hashCode();
}
@Override
public boolean equals(Object obj) {
	 if(obj instanceof SupplierModel){
		 SupplierModel model = (SupplierModel)obj;
		 int num = 0;
		 if(getCompanyName().equalsIgnoreCase(model.getCompanyName())){
			 num++;
		 }	
		 
		 if(model.getMobilePhone().equals(getMobilePhone())){
			 num++;
		 }
		 if(num >= 2){
			 return true;
		 }
	 }
	return false;
}
}
