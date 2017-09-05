package com.deepai.photo.common.util.date;

import java.text.SimpleDateFormat;  
import net.sf.json.JsonConfig;  
import net.sf.json.processors.JsonValueProcessor;  
  
public class JsonDateValueProcessor implements JsonValueProcessor {  
  
    private String format ="yyyy-MM-dd HH:mm:ss";  
    private String format1 = "yyyy-MM-dd";
      
    public Object processArrayValue(Object value, JsonConfig config) {  
        return process(value);  
   }  
  
   public Object processObjectValue(String key, Object value, JsonConfig config) {  
        return process(value);  
    }  
      
   private Object process(Object value){ 
       if(value instanceof java.sql.Timestamp){  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return sdf.format(value);  
       }  
       if (value instanceof java.sql.Date) {
    	   SimpleDateFormat sdf = new SimpleDateFormat(format1);  
           return sdf.format(value);
       }
       if(value instanceof java.util.Date){  
           SimpleDateFormat sdf = new SimpleDateFormat(format1);  
           return sdf.format(value);  
       }  
       return value == null ? "" : value.toString();  
   }  
}  
