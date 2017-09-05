package com.deepai.photo.common.util.validator;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.deepai.photo.common.util.date.DateUtil;
import com.deepai.photo.common.util.date.DateUtils;


/**
 * 
 * 检验是否符合某一个规则。
 * 
 * @author a
 *
 */

public class Checker
{
	
 public Checker()
 {
 }
 
 /**
  * 是否为手机
  * @param phone
  * @return
  */
 public static final boolean isPhoneNo(String phone){
	Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(177))\\d{8}$");
	Matcher m = p.matcher(phone);
    return m.matches();
 }
 
 /**
  * 是否为email
  * @param email
  * @return
  */
 public static final boolean isEmail(String email){
		Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");;
		Matcher m = p.matcher(email);
	    return m.matches();
 }
 
 
 public static final boolean isEmpty(String str)
 {
     return str == null || str.length() == 0;
 }

 public static final boolean isEmpty(StringBuffer str)
 {
     return str == null || str.length() == 0;
 }

 public static boolean isEmpty(String array[])
 {
     return array == null || array.length == 0;
 }

 public static boolean isEmpty(String array[][])
 {
     return array == null || array.length == 0;
 }

 public static boolean isEmpty(List list)
 {
     return list == null || list.size() < 1;
 }



 public static boolean isEmpty(Map map)
 {
     return map == null || map.size() < 1;
 }



 public static boolean isInteger(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         Integer.parseInt(str);
     }
     catch(Exception ex)
     {
         return false;
     }
     return true;
 }

 public static boolean isPositiveInteger(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         int i = Integer.parseInt(str);
         return i > 0;
     }
     catch(Exception ex)
     {
         return false;
     }
 }

 public static boolean isNonnegativeInteger(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         int i = Integer.parseInt(str);
         return i >= 0;
     }
     catch(Exception ex)
     {
         return false;
     }
 }

 public static boolean isLong(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         Long.parseLong(str);
     }
     catch(Exception ex)
     {
         return false;
     }
     return true;
 }

 public static boolean isPositiveLong(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         long l = Long.parseLong(str);
         return l > 0L;
     }
     catch(Exception ex)
     {
         return false;
     }
 }

 public static boolean isNonnegativeLong(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         long l = Long.parseLong(str);
         return l >= 0L;
     }
     catch(Exception ex)
     {
         return false;
     }
 }

 public static boolean isDecimal(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         Double.parseDouble(str);
     }
     catch(Exception e)
     {
         return false;
     }
     return true;
 }

 public static boolean isPositiveDecimal(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         double d = Double.parseDouble(str);
         return d > 0.0D;
     }
     catch(Exception e)
     {
         return false;
     }
 }

 public static boolean isNonnegativeDecimal(String str)
 {
     if(isEmpty(str))
         return false;
     try
     {
         double d = Double.parseDouble(str);
         return d >= 0.0D;
     }
     catch(Exception e)
     {
         return false;
     }
 }

 public static boolean isDate(String str)
 {
     try
     {
         java.util.Date date = DateUtil.getDateFormat().parse(str);
         String formatStr = DateUtil.getDateFormat().format(date);
         return formatStr.equals(str);
     }
     catch(Exception ex)
     {
         return false;
     }
 }

 public static boolean isTime(String str)
 {
     String formatStr;
     try
     {
     java.util.Date t = DateUtil.getTimeFormat().parse(str);
     formatStr = DateUtil.getTimeFormat().format(t);
     return formatStr.equals(str);
     }
     catch(Exception e)
     {
     return false;
     }
 }

 public static boolean isTimestamp(String str)
 {
 	try
 	{
     if(str == null || str.length() < 19)
         return false;
     if(str.length() == 19 && !str.endsWith("."))
         str = (new StringBuilder(String.valueOf(str))).append(".").toString();
     for(; str.length() < 23; str = (new StringBuilder(String.valueOf(str))).append("0").toString());
     String formatStr;
     java.util.Date date = DateUtil.getTimestampFormat().parse(str);
     formatStr = DateUtil.getTimestampFormat().format(date);
     return formatStr.equals(str);
 	}
 	catch(ParseException ex)
 	{
     return false;
 	}
 }
 
}
