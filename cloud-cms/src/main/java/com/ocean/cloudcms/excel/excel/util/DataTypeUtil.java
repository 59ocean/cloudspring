package com.ocean.cloudcms.excel.excel.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTypeUtil {
	
	/**
	 * 判断是不是电话号码
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone){
		 String regExp ="^0\\d{2,3}-?\\d{7,8}$|^(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])\\d{8}$";
		 Pattern p = Pattern.compile(regExp);
		 Matcher m = p.matcher(phone);
		 if(m.matches()){ //注意：m.find只能用一次，第二次调用后都为false
		  return true;

		 }else{
			 return false;

		 }
	}
	/**
	 * 判断是不是手机号码
	 * @param phone
	 * @return
	 */
	public static boolean isMobilePhone(String phone){
		 String regExp ="^(\\+\\d{2}-)?(\\d{2,3}-)?([1][3,4,5,7,8][0-9]\\d{8})$";
		 Pattern p = Pattern.compile(regExp);
		 Matcher m = p.matcher(phone);
		 if(m.matches()){ //注意：m.find只能用一次，第二次调用后都为false
		  return true;

		 }else{
			 return false;

		 }
	}
	/**
	 * 判断是不是固定电话号码
	 * @param phone
	 * @return
	 */
	public static boolean istelePhone(String phone){
		 String regExp ="^(\\+\\d{2}-)?0\\d{2,3}-\\d{7,8}$";
		 Pattern p = Pattern.compile(regExp);
		 Matcher m = p.matcher(phone);
		 if(m.matches()){ //注意：m.find只能用一次，第二次调用后都为false
		  return true;

		 }else{
			 return false;

		 }
	}
	/**
	 * 判断是不是整数
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str){
		if(str.matches("[0-9]+")){
			return true;
		}
		return false;
	}
	
	/**
     * 功能：判断字符串是否为日期格式
     * 
     * @param str
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";

        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }
	/**
	 * 判断是不是double类型
	 * @param str
	 * @return
	 */
    public static boolean isDouble(String str)
    {
       try
       {
          Double.parseDouble(str);
          return true;
       }
       catch(NumberFormatException ex){}
       return false;
    }
    /**
     * 判断是不是中文日期
     * @param str
     * @return
     */
    public static boolean isValidDate(String str){
	      boolean convertSuccess=true;
	       SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
	       try {
	          format.setLenient(false);
	          format.parse(str);
	       } catch (java.text.ParseException e) {
	    	   convertSuccess=false;
		} 
	       return convertSuccess;
	}
    
    /**
     * 根据正则表达式判断字符是否为汉字
     */
    public static boolean isContainChinese(String str) {
        String regex = "[\u4e00-\u9fa5]";   //汉字的Unicode取值范围
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(str);
        return match.find();
    }
    /**
	  * 判断是不是税率
	  * @param str
	  * @return
	  */
	 public static boolean isRate(String str){
		 if(isDouble(str)){
			 double num = Double.valueOf(str);
			 if(num > 0){
				 return true;
			 }
		 }
		 int n =str.length() - str.replaceAll("%", "").length();
		 if(n==1){
			 str = str.replaceAll("%", "");
			 if(isDouble(str)){
				 double num = Double.valueOf(str);
				 if(num > 0){
					 return true;
				 }
			 }else{
				 return false;
			 }
			 
		 }
		return false;
	 }
	 /**
	  * doubl转integer
	  * @param d
	  * @return
	  */
	 public static Integer doubleToInteger(Double d){
		 DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
		 return Integer.parseInt(df.format(d));
	 }
	 /**
	  * 判断是否正数
	  * @param cellValue
	  * @return
	  */
	 public static boolean isPositiveNumber(String cellValue){
		 if(cellValue.trim().matches("^\\d+(\\.\\d+)?$")){
			 return true;
		 }
		return false;
	 }
    
}
