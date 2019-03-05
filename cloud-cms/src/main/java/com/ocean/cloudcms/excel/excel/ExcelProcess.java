package com.ocean.cloudcms.excel.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelProcess {
	/*@Autowired
	private ISupplerExcelImportService supplerExcelImportService;
	@Autowired
	private IMatExcelImportService matExcelImportService;
	@Autowired
	private IPriceImportService priceImportService;
	@Autowired
	private ICommonSaveService commonSaveService;
	//直接导入数据库的
	public List<String> save(List<Object> list,String template,Object property, HttpServletRequest request) throws Exception{
		TemplateEnum templateEnum = TemplateEnum.getEnum(template);
		List<String> failList = new ArrayList<String>();
		switch (templateEnum) {
		case SUPPLIER_TEMPLATE:
			failList =  supplerExcelImportService.saveSupplier(list);
			//failList = commonSaveService.commonSave(list, Supplier.class);
			break;
		default:
			break;
		}
		return failList;
	}
	//不直接导入数据库
	public Map<String, Object> getData(List<Object> list,String template,HttpServletRequest request){
		TemplateEnum templateEnum = TemplateEnum.getEnum(template);
		Map<String,Object> map = new HashMap<String, Object>();
		switch (templateEnum) {
		case MAT_TEMPLATE:
			map = matExcelImportService.getItems(list);
			break;
		case PRICE_TEMPLATE:
			map = priceImportService.get(list, request);
			break;
		case LITTLE_MAT_TEMPLATE:
			map = matExcelImportService.getLittleItems(list,request);
			break;
		default:
			break;
		}
		return map;
	}*/
}
