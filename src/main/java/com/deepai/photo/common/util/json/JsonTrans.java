package com.deepai.photo.common.util.json;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.deepai.photo.common.util.date.JsonDateValueProcessor;

/**
 * Util for converting Java object to JSON string.
 * 
 */
public final class JsonTrans {

    /**
     * Convert a Java object to JSON string.
     */
    @SuppressWarnings("unchecked")
    public static String toJson(Object o) {
        if (o==null)
            return "null";
        if (o instanceof String)
            return string2Json((String)o);
        if (o instanceof Boolean)
            return boolean2Json((Boolean)o);
        if (o instanceof Number)
            return number2Json((Number)o);
        if (o instanceof Map)
            return map2Json((Map<String, Object>)o);
        if (o instanceof Object[])
            return array2Json((Object[])o);
        if (o instanceof int[])
            return intArray2Json((int[])o);
        if (o instanceof boolean[])
            return booleanArray2Json((boolean[])o);
        if (o instanceof long[])
            return longArray2Json((long[])o);
        if (o instanceof float[])
            return floatArray2Json((float[])o);
        if (o instanceof double[])
            return doubleArray2Json((double[])o);
        if (o instanceof short[])
            return shortArray2Json((short[])o);
        if (o instanceof byte[])
            return byteArray2Json((byte[])o);
        if (o instanceof ArrayList)
            return ArrayList2Json((ArrayList)o);
        if (o instanceof Timestamp)
            return string2Json(o.toString());
        if (o instanceof char[])
            return string2Json(String.valueOf((char[])o));
        if (o instanceof Serializable){
        	JsonConfig jsonConfig = new JsonConfig(); 
        	jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());  
        	jsonConfig.registerJsonValueProcessor(java.sql.Date.class , new JsonDateValueProcessor());
        	jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
        	jsonConfig.registerJsonValueProcessor(Long.class , new JsonNumberValueProcessor());
        	return JSONObject.fromObject(o,jsonConfig).toString();
        }
        if (o instanceof JSONArray){
        	return o.toString();
        }
        throw new RuntimeException("Unsupported type: " + o.getClass().getName());
    }
    
    static String ArrayList2Json(ArrayList o){
    	
    	JsonConfig jsonConfig = new JsonConfig(); 
    	jsonConfig.registerJsonValueProcessor(Timestamp.class , new JsonDateValueProcessor());  
    	jsonConfig.registerJsonValueProcessor(java.sql.Date.class , new JsonDateValueProcessor());
    	jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
    	jsonConfig.registerJsonValueProcessor(Long.class , new JsonNumberValueProcessor());
    	JSONArray obj=JSONArray.fromObject(o,jsonConfig);
    	return obj.toString();
    }
    
    public static void main(String[] args) {
    	ArrayList  list = new ArrayList();
		Date d = new java.util.Date();
		list.add(d);
		//java.sql.Date d1 = new java.sql.Date(d.getTime());
		//list.add(d1);
		String a = ArrayList2Json(list);
		System.out.println(a);
	}
    
    
    static String array2Json(Object[] array) {
        if (array.length==0)
            return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (Object o : array) {
            sb.append(toJson(o));
            sb.append(',');
        }
        // set last ',' to ']':
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    static String intArray2Json(int[] array) {
        if (array.length==0)
            return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (int o : array) {
            sb.append(Integer.toString(o));
            sb.append(',');
        }
        // set last ',' to ']':
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    static String longArray2Json(long[] array) {
        if (array.length==0)
            return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (long o : array) {
            sb.append(Long.toString(o));
            sb.append(',');
        }
        // set last ',' to ']':
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    static String booleanArray2Json(boolean[] array) {
        if (array.length==0)
            return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (boolean o : array) {
            sb.append(Boolean.toString(o));
            sb.append(',');
        }
        // set last ',' to ']':
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    static String floatArray2Json(float[] array) {
        if (array.length==0)
            return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (float o : array) {
            sb.append(Float.toString(o));
            sb.append(',');
        }
        // set last ',' to ']':
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    static String doubleArray2Json(double[] array) {
        if (array.length==0)
            return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (double o : array) {
            sb.append(Double.toString(o));
            sb.append(',');
        }
        // set last ',' to ']':
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    static String shortArray2Json(short[] array) {
        if (array.length==0)
            return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (short o : array) {
            sb.append(Short.toString(o));
            sb.append(',');
        }
        // set last ',' to ']':
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    static String byteArray2Json(byte[] array) {
        if (array.length==0)
            return "[]";
        StringBuilder sb = new StringBuilder(array.length << 4);
        sb.append('[');
        for (byte o : array) {
            sb.append(Byte.toString(o));
            sb.append(',');
        }
        // set last ',' to ']':
        sb.setCharAt(sb.length()-1, ']');
        return sb.toString();
    }

    static String map2Json(Map<String, Object> map) {
        if (map.isEmpty())
            return "{}";
        StringBuilder sb = new StringBuilder(map.size() << 4);
        sb.append('{');
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Object value = map.get(key);
            sb.append('\"');
            sb.append(key);
            sb.append('\"');
            sb.append(':');
            sb.append(toJson(value));
            sb.append(',');
        }
        // set last ',' to '}':
        sb.setCharAt(sb.length()-1, '}');
        return sb.toString();
    }

    static String boolean2Json(Boolean bool) {
        return bool.toString();
    }

    static String number2Json(Number number) {
    	if (number instanceof Long) {
    		StringBuilder sb = new StringBuilder();
            sb.append('\"').append(number).append('\"');
        	return sb.toString();
    	} else {
    		return number.toString();
    	}
    }

    static String string2Json(String s) {
        StringBuilder sb = new StringBuilder(s.length()+20);
        sb.append('\"');
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '\"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '/':
                sb.append("\\/");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            default:
                sb.append(c);
            }
        }
        sb.append('\"');
        return sb.toString();
    }
     public  static String Message2Json(String success,String message){
    	StringBuffer sb =new  StringBuffer();
    	sb.append("{\"success\":");
    	sb.append(success);
    	sb.append(",\"message\": \"");
    	sb.append(message);
    	sb.append("\"}");
       return sb.toString();
    }
     
     public static void main1(String[] args) {
		String str = "{\"errorCode\":0,\"translation\":[{\"translated\":[{\"src-tokenized\":\"this is test\",\"score\":-12.841996192932,\"rank\":0,\"text\":\"\\u8fd9\\u662f\\u8003\\u9a8c\"}],\"translationId\":\"db4fb3f1939f46399cde47f9f14017cf\"}],\"errorMessage\":\"OK\"}";
		JSONObject obj = JSONObject.fromObject(str);
		JSONArray arr = (JSONArray)obj.get("translation");
		for (int i = 0; i < arr.size(); i++) {
			JSONObject info = arr.getJSONObject(i);
			JSONArray transArr = (JSONArray) info.get("translated");
			for (int j = 0; j < transArr.size(); j++) {
				JSONObject transBean = transArr.getJSONObject(i);
				System.out.println(transBean.get("text"));
			}
		}
		
	}
     
     
     public  static  <T> T parseObject(String value, Class<T> clazz) {
         return com.alibaba.fastjson.JSONObject.parseObject(value, clazz);
     }
     
}
