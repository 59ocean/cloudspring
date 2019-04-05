package com.ocean.cloudcms.excel.excel.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


 /**
  * 
  * @author Administrator
  *
  */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelField {


    /**
     *  Excel 头名称.
     *
     * @return excel header column
     */
    String columnName() ;

}
