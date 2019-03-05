package com.ocean.cloudcms.excel.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;

public class ExcelUtil {
	public static final String TEMPLATE_ROOT_PATH = "classpath:excel-template";
	public static final int HEAD_LAST_ROW = 10;//允许表头的最后行数
	public static final int ERROR_NUM = 3;  //错误数量
	public static final int BLANK_ROW_NUM = 5; //连续空白行数量
	/**
	 * 根据模板code读取header
	 * @param templateCode  模板code
	 * @return
	 * @throws Exception
	 */
	public static ExcelTemplateConfig parseConfig(String templateCode) throws Exception {
		String configPath = TEMPLATE_ROOT_PATH + "/" + templateCode + ".json";
		String error = null;
		try {
			File jsonFile = ResourceUtils.getFile(configPath);
			String jsonStr = FileUtils.readFileToString(jsonFile, "UTF-8");
			if (StringUtils.isEmpty(jsonStr)) {
				error = "模版文件数据为空";
				throw new Exception(error);
			}
			ExcelTemplateConfig config = JSONObject.parseObject(jsonStr, ExcelTemplateConfig.class);
			return config;
		} catch (FileNotFoundException e) {
			error = "模版编码可能错误，模版文件未找到，" + templateCode;
			throw new Exception(error);
		} catch (IOException e) {
			error = "模版文件读取失败";
			throw new Exception(error);
		}
	}
	/**
	 * 匹配实体类
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public static ExcelTemplateConfig mappedHeadFeild(ExcelTemplateConfig config) throws Exception {
		try {
			Class<?> target = Class.forName(config.getTarget());
			List<ExcelHeadItem> items = config.getHeaders();
			if (items == null || items.isEmpty()) {
				throw new Exception("映射类文件中的列表头没有定义");
			}
			//得到model类的属性
			Field[] fields = target.getDeclaredFields();
			Map<String, Field> fieldMap = new HashMap<String, Field>();
			for (Field f : fields) {
				ExcelField excelField = f.getAnnotation(ExcelField.class);
				if (excelField == null || StringUtils.isEmpty(excelField.columnName()))
					continue;
				fieldMap.put(excelField.columnName(), f);
			}
			//匹配属性
			for (ExcelHeadItem item : items) {
				if (StringUtils.isEmpty(item.getName())) {
					throw new Exception("映射类文件header中丢失name属性");
				}
				Field f = fieldMap.get(item.getName());
				if (f == null) {
					throw new Exception("映射类文件header中的" + item.getName() + "没有找到对应实体中映射名");
				}
				item.setField(f);
			}
			return config;
		} catch (ClassNotFoundException e) {
			throw new Exception("映射类文件名没找到对应的类");
		}
	}
	
	/**
	 * 匹配实体类
	 * @param config
	 * @return
	 * 
	 * @throws Exception
	 */
	public static ExcelTemplateConfig mappedPropertyFeild(ExcelTemplateConfig config) throws Exception {
		try {
			Class<?> target = Class.forName(config.getPropertyTarget());
			List<ExcelHeadItem> items = config.getProperties();
			if (items == null || items.isEmpty()) {
				throw new Exception("映射类文件中的列表头没有定义");
			}
			Field[] fields = target.getDeclaredFields();

			Map<String, Field> fieldMap = new HashMap<String, Field>();
			
			for (Field f : fields) {
				ExcelField excelField = f.getAnnotation(ExcelField.class);
				if (excelField == null || StringUtils.isEmpty(excelField.columnName()))
					continue;
				fieldMap.put(excelField.columnName(), f);
			}
			for (ExcelHeadItem item : items) {
				if (StringUtils.isEmpty(item.getName())) {
					throw new Exception("映射类文件property中丢失name属性");
				}
				Field f = fieldMap.get(item.getName());
				if (f == null) {
					throw new Exception("映射类文件property中的" + item.getName() + "没有找到对应实体中映射名");
				}
				item.setField(f);
			}

			return config;

		} catch (ClassNotFoundException e) {
			throw new Exception("映射类文件名没找到对应的类");
		}
	}
	public static ExcelHeadItem mappedProperties(List<ExcelHeadItem> list,String value) throws Exception{
		for(ExcelHeadItem item : list){
			if(value.equals(item.getName()) || item.getByName().contains(value)){
				return item;
			}
		}
		return null;
	}
	/**
	 * 得到property
	 * @param config
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	public static Object getProperty(ExcelTemplateConfig config,Sheet sheet) throws Exception{
		int headRow = config.getHeadRow();
		Class clazz = Class.forName(config.getPropertyTarget());
		List<ExcelHeadItem> list = config.getProperties();
		List<Object> objs = null;
		int propertySize = 0;
		Object obj = clazz.newInstance();
		boolean success = false;
		for(int i = 0; i< headRow-1; i++){
			Row row = sheet.getRow(i);
			for(int j=row.getFirstCellNum();j <= row.getLastCellNum();j++){
				Cell cell = row.getCell(j);
				String value = getCellValue(cell).replaceAll("：", "").replaceAll(":", "");
				ExcelHeadItem item = mappedProperties(list, value);
				if(item != null){
					Cell cell2 = row.getCell(j+1);
					String cellValue = getCellValue(cell2);
					Field field = item.getField();
					if(item.isRequired()&&StringUtils.isEmpty(cellValue)){
						throw new Exception(value +"不能为空！");
					}
					if(!isRightDataType(item, cellValue)){
						throw new Exception(value +"数据类型不对");
					}
					if(!isRightTextType(item, cellValue)){
						throw new Exception(value +"文本类型不对");
					}
					if(!isRightLength(item, cellValue)){
						throw new Exception(value +"长度过长");
					}
					field.setAccessible(true);
					field.set(obj, parseObject(field, cellValue));
					propertySize++ ;
				}
			}
			if(propertySize == list.size()){
				success = true;
				return obj;
			}
		}
		if(!success){
			throw new Exception("匹配property失败！");
		}
		return obj;
	}
	
	/**
	 * 比较Excel的header和模板中的header
	 * @return
	 */
	public static boolean mapped(ExcelHeadItem item,Row row){
		for(Cell cell : row){
			String value = getCellValue(cell);
			value = getStringNoBlank(value);
			if(item.getName().equals(value) || item.getByName().contains(value)){
				item.setColIndex(cell.getColumnIndex());
				return true;
			}
		}
		return false;
	}
	/**
	 * 对模板和Excel进行表头匹配
	 * @param config
	 * @param xssfSheet
	 * @return
	 * @throws Exception
	 */
	public static ExcelTemplateConfig mappedExcelHead(ExcelTemplateConfig config, Sheet xssfSheet) throws Exception {
		List<ExcelHeadItem> list = config.getHeaders();
		boolean success = false;
		//匹配到的表头
		List<String> haveHead = new ArrayList<String>();
		//所有表头
		List<String> sumHead = new ArrayList<String>();
		for(ExcelHeadItem item : list){
			sumHead.add(item.getName());
		}
		for (int rowNum = 0; rowNum < HEAD_LAST_ROW; rowNum++) {
			Row row = xssfSheet.getRow(rowNum);
			int headSize = 0;
			if(row !=null){
				for(ExcelHeadItem item : list) {
					if(mapped(item, row)){
						headSize ++;
						haveHead.add(item.getName());
						continue;
					}
				}
			}
			if (list.size() == headSize) {
				config.setHeadRow(rowNum);
				success = true;
				break;
			}
			
		}
		if(!success){
			sumHead.removeAll(haveHead);
			throw new Exception("模板不对，缺少"+sumHead.toString()+"表头,请下载模板，对应好表头！");
		}
		return config;
	}
	
	/**
	 * 判断是不是空白行
	 * @param row
	 * @return
	 */
	public static boolean isBlankRow(Row row){		
		 if(row == null) return true;
	        for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
	        	Cell cell = row.getCell(i);
	        	String value = getCellValue(cell);
	        	if(!value.trim().isEmpty()){
	        		return false;
	        	}
	        }
		return true;
	}
	
	/**
	 * 读取Excel里的数据，封装成Lit<Model>
	 * @param config
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	public static List<Object> parseExcel(ExcelTemplateConfig config, Sheet sheet)
			throws Exception {
		Class clazz = Class.forName(config.getTarget());
		int headRow = config.getHeadRow();
		int errorNum = 0;
		List<String> error = new ArrayList<String>();
		List<Object> list = new ArrayList<Object>();
		Iterator<Row> rows = sheet.rowIterator();
		Object obj = null;
		int blankRowNum = 0;
		first:for(int i = 0; i<= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			obj = clazz.newInstance();
			String errorStr = "";
			//判断这个行是否为空
			if(isBlankRow(row)){
				blankRowNum++;
				//空行如果连续有BLANK_ROW_NUM个就不继续读了
				if(blankRowNum >= BLANK_ROW_NUM){
					return list;
				}
				continue first;
			}
			blankRowNum = 0;
			if(row.getRowNum() > headRow){
			for (ExcelHeadItem item : config.getHeaders()) {
				String cellValue=getCellValue(row.getCell(item.getColIndex()));
				cellValue = getStringNoBlank(cellValue);
				if(item.isRequired()&&StringUtils.isEmpty(cellValue)){
					
					errorStr = errorStr + item.getName()+"为空！";
					
				}
				if(!isRightDataType(item, cellValue)&&(cellValue!=null&&cellValue!="")){
					
					errorStr = errorStr + item.getName()+"数据类型不对！";
					
				}
				if(!isRightTextType(item, cellValue)&&(cellValue!=null&&cellValue!="")){
					
					errorStr = errorStr + item.getName()+"文本类型不对！";
					
				}
				if(!isRightLength(item, cellValue)&&(cellValue!=null&&cellValue!="")){
					
					errorStr = errorStr + item.getName()+"长度过长！";
					
				}
				if(errorStr == ""){
					Field f = item.getField();
					f.setAccessible(true);
					f.set(obj, parseObject(f,cellValue ));
				}
			}
			if(errorStr != ""){
				errorNum++;
				error.add("第"+(row.getRowNum())+"行"+errorStr);
				if(errorNum == ERROR_NUM){
					throw new Exception("导入失败：请检查如下行数"+error.toString());
				}
				list.add("第"+(list.size()+1)+"行"+errorStr);
			}else{
				//判断当前行数据是否跟之前的数据重复
				if(!list.contains(obj)){
					list.add(obj);
				}
			}
			}
		}
		
		return list;
	}
	
	public static Workbook getWorkbook(String path,FileInputStream inputStream) throws Exception{
		Workbook xWorkbook = null;
		String extString = path.substring(path.lastIndexOf("."));
		if(extString.equals(".xlsx")){
			//xWorkbook = new XSSFWorkbook(OPCPackage.open((path)));
			xWorkbook = new XSSFWorkbook(inputStream);
		}else if(extString.equals(".xls")){
			xWorkbook = new HSSFWorkbook(inputStream);
		}else{
			throw new Exception("文件不是Excel格式");
		}
		return xWorkbook;
	}
	/**
	 * 复杂Excel 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> processComplex(HttpServletRequest request) throws Exception {
		String template = request.getParameter("template");
		String path = (String) request.getAttribute("realPath");
		FileInputStream inputStream = null;
		Map<String,Object> map = new HashMap<String, Object>();
		List<Object> list = null;
		Object property = null;
		try {
			inputStream = new FileInputStream(path);
			//根据template读取json模板文件封装成ExcelTemplateConfig
			ExcelTemplateConfig config = parseConfig(template);
			//匹配模板里的property和实体里的属性
			mappedPropertyFeild(config);
			//匹配模板里的head和实体里的属性
			mappedHeadFeild(config);
			Workbook xWorkbook = getWorkbook(path, inputStream);
			if(xWorkbook.getNumberOfSheets() >= 2){
				throw new Exception("导入文件只能存在一个工作表！");
			}
			//得到headRow
			mappedExcelHead(config, xWorkbook.getSheetAt(0));
			property = getProperty(config, xWorkbook.getSheetAt(0));
			list= parseExcel(config, xWorkbook.getSheetAt(0));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			inputStream.close();
		}
		map.put("data", list);
		map.put("property", property);
		return map;
	}
	/**
	 * 简单Excel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<Object> processSimple(HttpServletRequest request) throws Exception{
		String template = (String) request.getParameter("template");
		String path = (String) request.getAttribute("realPath");
		FileInputStream inputStream = new FileInputStream(path);
		List<Object> list = null;
		try {
			//根据template读取json模板文件封装成ExcelTemplateConfig
			ExcelTemplateConfig config = parseConfig(template);
			//匹配模板里的head和实体里的属性
			mappedHeadFeild(config);
			Workbook xWorkbook = getWorkbook(path, inputStream);
			if(xWorkbook.getNumberOfSheets() >= 2){
				throw new Exception("导入文件只能存在一个工作表！");
			}
			
			//得到headRow
			mappedExcelHead(config, xWorkbook.getSheetAt(0));	
			list= parseExcel(config, xWorkbook.getSheetAt(0));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			inputStream.close();
		}
		return list;
	}
	
	/**
	 * 得到每列的值
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				try {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
				} catch (Exception e) {
					return cell.getDateCellValue().getTime() + "";
				}
			}
			cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
			if (cellValue.contains("E")) {
				cellValue = new DecimalFormat("#").format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				cellValue = String.valueOf(cell.getStringCellValue());
			} catch (Exception e) {
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR:
			cellValue = "illegal character";
			break;
		default:
			cellValue = "Unknown type";
			break;
		}
		return cellValue;
	}
	
	/**
	 * 判断实体类的属性是什么数据类型
	 * @param field
	 * @param value
	 * @return
	 * @throws ParseException 
	 */
	private static Object parseObject(Field field, String value) throws ParseException {
		if (field.getType().equals(String.class)) {
			return value;
		}
		if (field.getType().equals(BigDecimal.class)) {
			return new BigDecimal(value);
		}
		if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
			return new BigDecimal(value).longValue();
		}
		if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
			return new BigDecimal(value).intValue();
		}
		if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
			return Double.valueOf(value);
		}
		if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
			return Float.valueOf(value);
		}
		if (field.getType().equals(Short.class) || field.getType().equals(short.class)) {
			return Short.valueOf(value);
		}
		if (field.getType().equals(Byte.class) || field.getType().equals(byte.class)) {
			return Byte.valueOf(value);
		}
		if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
			return Boolean.valueOf(value);
		}
		if (field.getType().equals(Date.class)) {
			if(DataTypeUtil.isContainChinese(value.toString())){
				value=value.replace("年", "-");
				value=value.replace("月", "-");
				value=value.replace("日", "");
			}
			SimpleDateFormat sdf= null;
			if(value.contains("-")){
				sdf= new SimpleDateFormat("yyyy-MM-dd");
			}else{
				sdf= new SimpleDateFormat("yyyy/MM/dd");
				
			}
			return sdf.parse(value);
		}
		return value;
	}
	/**
	 * 判断数据类型是否正确
	 * @param item
	 * @param cellValue
	 * @return
	 */
	public static boolean isRightDataType(ExcelHeadItem item,String cellValue){
		String dataType = item.getDataType();
		if(dataType != null){
			if(dataType.equals("Date")){
				if(DataTypeUtil.isDate(cellValue)||DataTypeUtil.isValidDate(cellValue)){
					return true;
				}else{
					return false;
				}
			}else if(dataType.equals("Integer")||dataType.equals("int")){
				if(DataTypeUtil.isInt(cellValue)){
					return true;
				}else{
					return false;
				}
			}else if(dataType.equals("double")||dataType.equals("Double")){
				if(DataTypeUtil.isDouble(cellValue)){
					return true;
				}else{
					return false;
				}
			}
		}
		
		
		return true;
	}
	/**
	 * 判断数据长度是否正确
	 * @param item
	 * @param cellValue
	 * @return
	 */
	public static boolean isRightLength(ExcelHeadItem item,String cellValue){
		Integer length = item.getLength();
		if(length != null){
			if(cellValue.length() > length){
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断文本类型是否正确
	 */
	public static boolean isRightTextType(ExcelHeadItem item,String cellValue){
		String textType = item.getTextType();
		if(textType != null){
			if(textType.equals("phone")){
				if(DataTypeUtil.isPhone(cellValue)){
					return true;
				}else{
					return false;
				}
			}else if(textType.equals("email")){
				if(DataTypeUtil.isPhone(cellValue)){
					return true;
				}else{
					return false;
				}
			}else if(textType.equals("mobilePhone")){
				if(DataTypeUtil.isMobilePhone(cellValue)){
					return true;
				}else{
					return false;
				}
			}
			else if(textType.equals("telePhone")){
				if(DataTypeUtil.istelePhone(cellValue)){
					return true;
				}else{
					return false;
				}
			}
			else if(textType.equals("rate")){
				if(DataTypeUtil.isRate(cellValue)){
					return true;
				}else{
					return false;
				}
			}
		}
		return true;
		
	}
	/**
	 * 去掉前后空格
	 * @param str
	 * @return
	 */
	public static String getStringNoBlank(String str) {
        if(str!=null && !"".equals(str)) {
            str.replaceAll("\n", "");
            Pattern p = Pattern.compile("(^\\s*)|(\\s*$)");
            Matcher m = p.matcher(str);
            String strNoBlank = m.replaceAll("");
            return strNoBlank;
        }else {
            return  "";
        }

 }
	public static void main(String[] args) throws Exception {
		String path = "F:/template.xlsx";
		String extString = path.substring(path.lastIndexOf("."));
		InputStream instream = new FileInputStream(path);
		ExcelTemplateConfig config = parseConfig("target-price-template");
		long start  = System.currentTimeMillis();
		mappedHeadFeild(config);
		Workbook xWorkbook = null;
		if(extString.equals(".xlsx")){
			//xWorkbook = new XSSFWorkbook(OPCPackage.open((path)));
			xWorkbook = new XSSFWorkbook(instream);
		}else if(extString.equals(".xls")){
			xWorkbook = new HSSFWorkbook(instream);
		}else{
			throw new Exception("文件不是Excel格式");
		}
		System.out.println(xWorkbook.getNumberOfSheets());
		//mappedPropertyFeild(config);
		mappedExcelHead(config, xWorkbook.getSheetAt(0));
//		Object obj = getProperty(config, xWorkbook.getSheetAt(0));
//		if(obj instanceof Property){
//			Property property = (Property)obj;
//			System.out.println(property);
//		}
		long a = System.currentTimeMillis();
		List<Object> list=	parseExcel(config, xWorkbook.getSheetAt(0));
		for(Object obj: list){
		//	PrjMatModel model = (PrjMatModel)obj;
			System.out.println(obj);
		}
		instream.close();
		System.out.println("end:"+(System.currentTimeMillis()-start));
		System.out.println(list.size());
		System.out.println(config);
	}
	
}
