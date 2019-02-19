package com.ocean.cloudcms.controller;

import com.ocean.cloudcms.excel.ExcelExportHeper;
import com.ocean.cloudcms.excel.serviceImpl.SupplierExport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenhy
 * @Date: 2019/2/15 9:12
 * @Version 1.0
 */

@RestController
@RequestMapping("/v1/cms/excel")
@Api(tags = "Excel导入导出API")
public class ExcelController {


    @GetMapping("/export")
    @ApiOperation(value = "Excel导出")
    public void excelExport(HttpServletResponse response, HttpServletRequest request){
        HSSFWorkbook wb = null;
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            String fileName = "供应商评价等级信息.xls";
            response.setContentType("application/msexcel");
            String userAgent = request.getHeader("User-Agent");
            if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }else{
                fileName = URLEncoder.encode(fileName,"UTF-8");//其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            ExcelExportHeper excelExportHeper = new ExcelExportHeper(new SupplierExport());
            Map<String,Object> map = new HashMap<>();
            wb = excelExportHeper.export(map);
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
