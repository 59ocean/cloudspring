package com.ocean.cloudcms.excel.serviceImpl;

import com.ocean.cloudcms.excel.ExcelExport;
import com.ocean.cloudcms.utils.HSSFCellStyleUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.util.Map;

/**
 * @Author: chenhy
 * @Date: 2019/2/14 17:07
 * @Version 1.0
 */
public class SupplierExport implements ExcelExport {

    private static String[] headerNames={"名称","所在省"};
    @Override
    public HSSFWorkbook excute(Map<String, Object> map) {
        // 1.创建一个workbook，对应一个Excel文件
         HSSFWorkbook wb = new HSSFWorkbook();
       // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("供应商评价等级");
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow rowHeader = sheet.createRow((int) 0);
        // 设置表头
        for(int i = 0; i<headerNames.length; i++){
            HSSFCell cell = rowHeader.createCell(i);
            cell.setCellValue(headerNames[i]);
            cell.setCellStyle(HSSFCellStyleUtil.getStyleTitle(wb));
        }

        for (int i=0; i<10; i++){
            HSSFRow row = sheet.createRow(i+1);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("材巴巴");
            cell.setCellStyle(HSSFCellStyleUtil.getStyle(wb));

            HSSFCell cell2 = row.createCell(1);
            cell2.setCellValue("广东省");
            cell2.setCellStyle(HSSFCellStyleUtil.getStyle(wb));

        }
        return wb;
    }
}
