//package com.deepai.photo.common.util.TrsSearch;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import org.apache.commons.lang.StringUtils;
//
///**
// * 
// * @author xiayunan
// * @date   2017年9月8日
// *
// */
//public class TrsSearchUtil {
//	  private int _dividedWordLength = 5;//分割检索词长度
//	  private Calendar cal = null;
//	  private Calendar calToday = null;
//	  SimpleDateFormat formater = new SimpleDateFormat("yyyy.MM.dd");
//	  private static final String LIKE_QUOTE_STR = String.valueOf('\\');
//	  private static final char[] LIKE_CHARS = { '(', ')', '[', ']', '-', '+', '*', '/' };
//	  
//	  public String handleSqlWhere(String keyword, String preKeyword, String colum, Boolean research){
//		  if ((StringUtils.isEmpty(keyword)) || (keyword.trim().length() < 1)){
//			  return "";
//		  }
//	      String[] keywordsArray = keyword.split(" ");
//	      String tempWhere = "";
//	      for (int i = 0; i < keywordsArray.length; i++){
//	    	  if(keywordsArray[i].length() > this._dividedWordLength ){
//	    		  tempWhere = new StringBuilder().append(tempWhere).append(keywordsArray[i].length() > this._dividedWordLength ? new StringBuilder()
//	    			  		.append(" (DOCTITLE,DOCCONTENT OR like(")
//	    			  		.append(unescapeLike(keywordsArray[i]));
////	    			  		.append("))").toString()
//	    	  }
//	    	  
//	    	  
//	    	  
//	    	  tempWhere = new StringBuilder().append(tempWhere)
//	    			  		.append(keywordsArray[i].length() > this._dividedWordLength ? new StringBuilder()
//	    			  		.append(" (DOCTITLE,DOCCONTENT OR like(")
//	    			  		.append(unescapeLike(keywordsArray[i]))
//	    			  		.append("))").toString() : !StringUtils.isEmpty(colum) ? new StringBuilder()
//	    			  		.append(colum)
//	    			  		.append("=")
//	    			  		.append(keywordsArray[i]).toString():new StringBuilder()
//	    			  		.append("(DOCTITLE/100, DOCCONTENT/1+=")
//	    			  		.append(keywordsArray[i]).append(")").toString()).toString();
//	    	  tempWhere = new StringBuilder().append(tempWhere).append(i + 1 == keywordsArray.length ? " AND " : " or ").toString();
//	    }
//	    tempWhere = (!research.booleanValue()) || (StringUtils.isEmpty(preKeyword)) ? tempWhere : new StringBuilder().append(tempWhere).append(" (DOCTITLE=").append(preKeyword).append(" or DOCCONTENT=").append(preKeyword).append(") and ").toString();
//
//	    return tempWhere;
//	  }
//	  
//	  public static String unescapeLike(String str) {
//	    if (StringUtils.containsAny(str, LIKE_CHARS)) {
//	      for (char _char : LIKE_CHARS) {
//	        str = StringUtils.replace(str, String.valueOf(_char), LIKE_QUOTE_STR + _char);
//	      }
//	    }
//	    return str;
//	  }
//}
