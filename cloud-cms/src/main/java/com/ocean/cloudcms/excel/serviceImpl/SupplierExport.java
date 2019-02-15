package com.ocean.cloudcms.excel.serviceImpl;

import com.ocean.cloudcms.excel.ExcelExport;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * @Author: chenhy
 * @Date: 2019/2/14 17:07
 * @Version 1.0
 */
public class SupplierExport implements ExcelExport {

    private static String[] headerName={"名称","所在省"};
    @Override
    public void excute() {


        // 1.创建一个workbook，对应一个Excel文件
         HSSFWorkbook wb = new HSSFWorkbook();
       // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("供应商评价等级");
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        String headerNames[] = {"企业名称(账号)","申请人(手机)","考评时间","供应商等级"};
        // 设置表头
        for(int i = 0; i<headerNames.length; i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headerNames[i]);
            HSSFFont font = wb.createFont();
            //设置字体大小
            font.setFontHeightInPoints((short)11);
            //字体加粗
            font.setBold(true);
            //设置字体名字
            font.setFontName("Courier New");
            HSSFCellStyle style1 = wb.createCellStyle();
            //设置水平对齐的样式为居中对齐;
            style1.setAlignment(HorizontalAlignment.CENTER);
            //设置垂直对齐的样式为居中对齐;
            style1.setVerticalAlignment(VerticalAlignment.CENTER);
            style1.setFont(font);
            cell.setCellStyle(style1);
        }
    }
}
