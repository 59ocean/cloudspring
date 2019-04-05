package com.ocean.cloudcms.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

/**
 * @Author: chenhy
 * @Date: 2019/2/14 16:50
 * @Version 1.0
 */
public interface ExcelExport {


    HSSFWorkbook excute(Map<String, Object> map);
}
