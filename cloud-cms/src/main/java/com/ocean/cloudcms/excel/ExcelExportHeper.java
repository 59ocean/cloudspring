package com.ocean.cloudcms.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

/**
 * @Author: chenhy
 * @Date: 2019/2/15 9:19
 * @Version 1.0
 */
public class ExcelExportHeper {

    private ExcelExport excelExport;

    public ExcelExportHeper(ExcelExport excelExport) {
        this.excelExport = excelExport;
    }

    public HSSFWorkbook export(Map<String,Object> map){
        return excelExport.excute(map);
    }

}
