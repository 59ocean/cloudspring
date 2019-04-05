package com.ocean.cloudcms.excel.excel.util;

import java.util.List;

public class ExcelTemplateConfig {
	private List<ExcelHeadItem> headers;//表头
	private List<ExcelHeadItem> properties;//复杂表中表头上面的属性
	private String target;//表头对应的Model类路径
	private Integer headRow;//用来记录表头在第几行
	private String propertyTarget;//属性对应的model类路径

	public List<ExcelHeadItem> getHeaders() {
		return headers;
	}

	public void setHeaders(List<ExcelHeadItem> headers) {
		this.headers = headers;
	}

	public List<ExcelHeadItem> getProperties() {
		return properties;
	}

	public void setProperties(List<ExcelHeadItem> properties) {
		this.properties = properties;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	

	public Integer getHeadRow() {
		return headRow;
	}

	public void setHeadRow(Integer headRow) {
		this.headRow = headRow;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExcelTemplateConfig [headers=");
		builder.append(headers);
		builder.append(", properties=");
		builder.append(properties);
		builder.append(", target=");
		builder.append(target);
		builder.append("]");
		return builder.toString();
	}

	public String getPropertyTarget() {
		return propertyTarget;
	}

	public void setPropertyTarget(String propertyTarget) {
		this.propertyTarget = propertyTarget;
	}

	
}
