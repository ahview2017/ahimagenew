package com.deepai.photo.common.util.TrsSearch;


import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author xiayunan
 * @date   2017年9月8日
 *
 */
public class TrsSearchUtil {
	  private static final String LIKE_QUOTE_STR = String.valueOf('\\');
	  private static final char[] LIKE_CHARS = { '(', ')', '[', ']', '-', '+', '*', '/' };
	  
	  public static String handleSqlWhere(String keyword){
		  if ((StringUtils.isEmpty(keyword)) || (keyword.trim().length() < 1)){
			  return "";
		  }
	      String[] keywordsArray = keyword.split(" ");
	      String tempWhere = "";
	      System.out.println("<<<<<<<<<<<<<<keywordsArray.length"+keywordsArray.length);
	      if(keywordsArray.length>0){
	    	  StringBuilder sb = new StringBuilder();
	    	  sb.append("(AUTHOR_NAME,KEYWORDS,MEMO,TITLE,gtitle,gkeywords) += ")
	    		.append("(");
	    	  for (int i = 0; i < keywordsArray.length; i++){
	    		  sb.append(keywordsArray[i]);
	    		  if(i<(keywordsArray.length-1)){
	    			  sb.append(" and "); 
	    		  }
	    	  }
	    	  sb.append(")");
	    	  tempWhere = sb.toString();
	      }
	    return tempWhere;
	  }
	  
	  public static String unescapeLike(String str) {
	    if (StringUtils.containsAny(str, LIKE_CHARS)) {
	      for (char _char : LIKE_CHARS) {
	        str = StringUtils.replace(str, String.valueOf(_char), LIKE_QUOTE_STR + _char);
	      }
	      
	      
	    }
	    return str;
	  }
}
