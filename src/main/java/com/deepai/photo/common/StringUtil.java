package com.deepai.photo.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具类
 */
public class StringUtil extends StringUtils {
	private static final String TIME_FORMAT_SHORT = "yyyyMMddHHmmss";
	private static final String TIME_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
	private static final String TIME_FORMAT_ENGLISH = "MM/dd/yyyy HH:mm:ss";
	private static final String TIME_FORMAT_CHINA = "yyyy年MM月dd日 HH时mm分ss秒";

	private static final String DATE_FORMAT_SHORT = "yyyyMMdd";
	private static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
	private static final String DATE_FORMAT_ENGLISH = "MM/dd/yyyy";
	private static final String DATE_FORMAT_CHINA = "yyyy年MM月dd日";

	/**
	 * 判断给定的字符串是否为空,以及空字符串
	 * 
	 * @param input
	 *            输入字符串
	 * @return 是否为空,空则返回true，反之亦反
	 * @since 0.1
	 */
	public static boolean blank(String input) {
		return input == null || "".equals(input) || input.length() == 0
				|| input.trim().length() == 0 || "null".equals(input)|| "NULL".equals(input);
	}

	/**
	 * 判断给定的字符串不为空
	 * 
	 * @param input
	 *            输入字符串
	 * @return 是否不为空，不为空返回true，反之亦反.
	 * @since 0.1
	 */
	public static boolean notBlank(String input) {
		return !blank(input);
	}

	/**
	 * 求字符串中某个子串的位置（自字符串orig的i位开始将orig按组长度len分隔为若干段，求第一个indexOf(段,sub)=0
	 * 的段首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @param len
	 *            每组长度
	 * @param i
	 *            开始查找位置
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub, int len, int i) {
		if (orig == null)
			return -1;
		int idx = orig.indexOf(sub, i);
		if (idx == -1)
			return idx;
		if (idx % len == 0)
			return idx;
		i = (idx / len + 1) * len;
		int tmp = -1;
		if ((tmp = indexOf(orig, sub, len, i)) > -1) {
			return tmp;
		} else {
			return -1;
		}
	}

	/**
	 * 求字符串中某个子串的位置（将字符串按组长度len分隔为若干段，求第一个indexOf(段,sub)=0的段首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @param len
	 *            每组长度
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub, int len) {
		return indexOf(orig, sub, len, 0);
	}

	/**
	 * 求字符串中某个子串的位置（将字符串按子串长度分隔为若干段，求第一个同子串相同的段 首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub) {
		return indexOf(orig, sub, sub.length(), 0);
	}

	/**
	 * 截取字符串
	 * 
	 * @param orig
	 *            源字符串
	 * @param length
	 *            字符串长度
	 * @return
	 * @since 0.1
	 */
	public static String substr(String orig, int length) {
		if (orig == null)
			return "";
		if (orig.length() <= length)
			return orig;
		return orig.substring(0, length - 1) + "...";
	}

	/**
	 * 首字母大写
	 * 
	 * @param input
	 *            输入字符串
	 * @return
	 * @since 0.1
	 */
	public static String toFirstUpperCase(String input) {
		return StringUtil.blank(input) ? input : input.substring(0, 1)
				.toUpperCase()
				+ input.substring(1);
	}

	/**
	 * 填充字符串到固定长度
	 * 
	 * @param orig
	 *            源字符串
	 * @param num
	 *            填充后字符串长度几位
	 * @param fillCharacter
	 *            要填充的字符
	 * @param location
	 *            位置 true:后 false:前
	 * @return
	 */
	public static String fillCharacter(String orig, int num,
			String fillCharacter, boolean location) {
		if (orig == null)
			return null;
		if (orig.length() >= num)
			return orig;
		StringBuffer sb = new StringBuffer("");
		for (int i = 0;; i++) {
			if (orig.length() + i * fillCharacter.length() >= num) {
				break;
			}
			sb.append(fillCharacter);
		}
		if (location) {
			orig = orig + sb.substring(0, num - orig.length());
		} else {
			orig = sb.substring(0, num - orig.length()) + orig;
		}
		return orig;
	}

	/**
	 * 字符转换方法
	 * 
	 * @param source
	 *            原字符串
	 * @param clazz
	 *            转换类型
	 * @return
	 * @throws ParseException
	 */
	public static Object convert(String orig, Class<?> clazz) {
		if (orig == null) {
			return null;
		}
		if (clazz == String.class) {
			return orig;
		}
		if (clazz == short.class) {
			return Short.parseShort(orig);
		}
		if (clazz == Short.class) {
			return new Short(orig);
		}
		if (clazz == int.class) {
			return Integer.parseInt(orig);
		}
		if (clazz == Integer.class) {
			return new Integer(orig);
		}
		if (clazz == long.class) {
			return Long.parseLong(orig);
		}
		if (clazz == Long.class) {
			return new Long(orig);
		}
		if (clazz == float.class) {
			return Float.parseFloat(orig);
		}
		if (clazz == Float.class) {
			return new Float(orig);
		}
		if (clazz == double.class) {
			return Double.parseDouble(orig);
		}
		if (clazz == Double.class) {
			return new Double(orig);
		}

		if (orig.equalsIgnoreCase("t") || orig.equalsIgnoreCase("ture")
				|| orig.equalsIgnoreCase("y") || orig.equalsIgnoreCase("yes")) {
			if (clazz == boolean.class) {
				return true;
			}
			if (clazz == Boolean.class) {
				return new Boolean(true);
			}
		} else {
			if (clazz == boolean.class) {
				return false;
			}
			if (clazz == Boolean.class) {
				return new Boolean(false);
			}
		}

		try {
			if (clazz == java.util.Date.class) {
				DateFormat fmt = null;
				if (orig.matches("\\d{14}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
				} else if (orig
						.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
				} else if (orig
						.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
				} else if (orig
						.matches("\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}时\\d{1,2}分\\d{1,2}秒")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_CHINA);
				} else if (orig.matches("\\d{8}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
				} else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
				} else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
				} else if (orig.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_CHINA);
				}
				return fmt.parse(orig);
			}
			if (clazz == java.util.Calendar.class) {
				Calendar cal = Calendar.getInstance();
				DateFormat fmt = null;
				if (orig.matches("\\d{14}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
				} else if (orig
						.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
				} else if (orig
						.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
				} else if (orig
						.matches("\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}时\\d{1,2}分\\d{1,2}秒")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_CHINA);
				} else if (orig.matches("\\d{8}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
				} else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
				} else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
				} else if (orig.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_CHINA);
				}
				cal.setTime(fmt.parse(orig));
				return cal;
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException("字符串不能转换为" + clazz.getName()
					+ "类型.");
		}
		throw new IllegalArgumentException("字符串不能转换为" + clazz.getName() + "类型.");
	}

	/**
	 * 字符转换方法
	 * 
	 * @param source
	 *            原字符串
	 * @param clazz
	 *            转换类型
	 * @return
	 * @throws ParseException
	 */
	public static String convert(Object orig) {
		if (orig == null) {
			return null;
		}
		if (orig instanceof String) {
			return (String) orig;
		}
		if (orig instanceof Short) {
			return Short.toString((Short) orig);
		}
		if (orig instanceof Integer) {
			return Integer.toString((Integer) orig);
		}
		if (orig instanceof Long) {
			return Long.toString((Long) orig);
		}
		if (orig instanceof Float) {
			return Float.toString((Float) orig);
		}
		if (orig instanceof Double) {
			return Double.toString((Double) orig);
		}
		if (orig instanceof Boolean) {
			return Boolean.toString((Boolean) orig);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		if (orig instanceof java.util.Date) {
			return format.format((java.util.Date) orig);
		}
		if (orig instanceof java.sql.Date) {
			return format.format((java.sql.Date) orig);
		}
		if (orig instanceof java.util.Calendar) {
			return format.format(((java.util.Calendar) orig).getTime());
		}
		throw new java.lang.IllegalArgumentException("参数类型不支持.");
	}

	/**
	 * 从map字符串获取value值
	 * 
	 * @param orig
	 *            字符串 例:a=1&b=2&c=3
	 * @param param
	 *            key 例:a
	 * @param split
	 *            例:&
	 * @return 例:1
	 * @since 0.1
	 */
	public static String[] getValueFromString(String orig, String param,
			String split) {
		String[] result = new String[] {};
		if (StringUtils.isBlank(orig)) {
			return result;
		}
		List<String> list = new ArrayList<String>();
		String[] values = orig.split(split);
		for (String tmp : values) {
			String key = StringUtils.substringBefore(tmp, "=");
			String value = StringUtils.substringAfter(tmp, "=");
			if (param.equals(key)) {
				list.add(value);
			}
		}
		return list.toArray(result);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String null2String(String s) {
		return s == null ? "" : s;
	}

	/**
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	public static String null2String(String s, String s1) {
		return s == null ? s1 : s;
	}

	/**
	 * 反射根据Method获得类名、方法名
	 * 
	 * @param method
	 * @return
	 */
	public static String methodToClass(Method method) {
		String methodName = method.getName();
		String className = method.getDeclaringClass().getName();
		Class<?>[] typeNames = method.getParameterTypes();
		StringBuffer buffer = new StringBuffer();
		for (Class<?> type : typeNames) {
			buffer.append(type.getName()).append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return className + "." + methodName + "(" + buffer.toString() + ")";
	}

	/**
	 * 根据起始时间、类型、间隔算出结束时间
	 * 
	 * @param startDate
	 * @param type
	 * @param interval
	 * @return
	 */
	public static String getEndDate(String startDate, String type, int interval) {
		if (StringUtil.isBlank(startDate) || StringUtil.isBlank(type))
			return null;
		Calendar c1 = Calendar.getInstance(Locale.CHINA);
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_NORMAL);
		try {
			Date d1 = format.parse(startDate);
			c1.setTime(d1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if ("1".equals(type)) {// 间隔为天
			c1.add(Calendar.DAY_OF_MONTH, interval);
		} else if ("2".equals(type)) {// 间隔为整月
			c1.add(Calendar.MONTH, interval);
		} else if ("3".equals(type)) {// 间隔为自然月
			c1.set(Calendar.DAY_OF_MONTH, 1);
			c1.add(Calendar.MONTH, interval);
			c1.add(Calendar.DAY_OF_MONTH, -1);
		}
		return format.format(c1.getTime());
	}

	public static String cnToNumber(String normal) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("一", 1);
		map.put("二", 2);
		map.put("三", 3);
		map.put("四", 4);
		map.put("五", 5);
		map.put("六", 6);
		map.put("七", 7);
		map.put("八", 8);
		map.put("九", 9);
		map.put("十", 10);
		char[] charArray = normal.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charArray.length; i++) {
			char t = normal.charAt(i);
			if(map.containsKey(String.valueOf(t))){
				sb.append(map.get(String.valueOf(t)));
			}else{
				sb.append(String.valueOf(t));
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * Description: 过滤js标签，防止js注入漏洞
	 *
	 * @param str
	 * @return
	 * @Author yubin
	 * Create Date: Dec 21, 2011 5:55:54 PM
	 */
	public static String replaceJsTag(String str) {
		str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll(
				"\\(", "（").replaceAll("\\)", "）").replaceAll("eval\\((.*)\\)",
				"").replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		return str;
	}
	/**
	 * 
	 * Description: BigDecimal转为字符串
	 *
	 * @param dec
	 * @return
	 * @Author yubin
	 * Create Date: Jan 11, 2012 1:44:46 PM
	 */
	public static String bigDecimalToString(BigDecimal dec){
		String str = "";
		if(dec != null){
			str = dec.toString();
		}
		return str;
	}
	
	
	public static boolean chechEmail(String emailStr){
		/*邮箱验证*/  
		 String check = "^([a-zA-Z0-9]+[-|\\-|_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-|\\-|_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
		 java.util.regex.Pattern regex = java.util.regex.Pattern.compile(check );
		 java.util.regex.Matcher matcher = regex.matcher(emailStr);
		  return  matcher.matches();

	}
	
	
	public static boolean chechPhoneNumber(String phoneStr){
		/*手机验证*/
		   java.util.regex.Pattern p = java.util.regex.Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		   java.util.regex.Matcher m = p.matcher(phoneStr);
		   return m.matches();

	}
	
	/**
	 * 生成在min~max之间的数字
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandom(int min,int max)
	{
		Random rand = new Random(); 
	    int tmp = Math.abs(rand.nextInt());
	    return tmp % (max - min + 1) + min;
	}
	
	/**
	 * 生成6位随机数字
	 * @return
	 */
	public static String getSixRandom(){
		return getRandom(100000, 999999)+"";
	}
	
	/**
	 * 将浮点数保留2位小数，并用逗号分隔
	 * @param data
	 * @return
	 */
	public static String decimalFormatComma(double data) {
		DecimalFormat format = new DecimalFormat("###,##0.##");
		return format.format(data);
	}
	
	/**
	 * 将浮点数保留2位小数，但不用逗号分隔
	 * @param data
	 * @return
	 */
	public static String decimalFormat(double data) {
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(data);
	}
	
	/**
	 * 将double转为两位小数的百分数
	 * @param data
	 * @return
	 */
	public static String transDouble2Percent(double data) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(2); 
		return nf.format(data);
	}
	
	/**
	 * 显示double有效位，最多两位。
	 * @param data
	 * @return
	 */
	public static String formatDoubleValidBit(double data) {
	    NumberFormat nf=NumberFormat.getNumberInstance();
	    nf.setMaximumFractionDigits(2);
	    return nf.format(data);
	}
	
	/**
	 * 将日期格式化成yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String formatDateShort(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_SHORT);
		return format.format(date);
	}
	
	/**
	 * 将日期格式化成yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static String formatTimeShort(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT_SHORT);
		return format.format(date);
	}
	
	/**
	 * 将日期格式化成yyyy年MM月dd日
	 * @param date
	 * @return
	 */
	public static String formatDateChina(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_CHINA);
		return format.format(date);
	}
	
	/**
	 * 将日期格式化成yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatTimeNormal(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT_NORMAL);
		return format.format(date);
	}
	
	/**
	 * 隐藏敏感信息。
	 * 将字符串转化成首尾字母加三个星号。songyh->s***h
	 * @param name
	 * @return
	 */
	public static String getAnonymous(String name) {
		if (blank(name) || name.length() < 2) {
			return "";
		}
		
		char first = name.charAt(0);
		char tail = name.charAt(name.length() - 1);
		return first + "***" + tail;
	}
	
	/**
	 * 还原HTML代码方法
	 * 
	 * @param htmlString
	 * @return
	 */
	public static String restoreHTMLForShow(String htmlString) {
		if (blank(htmlString)) {
			return "";
		}
		
		Map<String, String> html = new HashMap<String, String>();
		html.put("<", "&lt;");
		html.put("'", "&acute;");
		html.put(">", "&gt;");
		html.put("&", "&amp;");
		html.put("\"", "&quot;");
		html.put("'", "&quot;");
		
		for (Map.Entry<String, String> entry : html.entrySet()) {
			htmlString = htmlString.replaceAll(entry.getValue(), entry.getKey());
		}
		return htmlString;
	}
	
	/**
	 * 将HTML进行编码，用于富文本编辑器显示已存到数据库中的内容
	 * @param htmlString
	 * @return
	 */
	public static String transcodeHTMLForEdit(String htmlString) {
		if (blank(htmlString)) {
			return "";
		}
		
		Map<String, String> html = new HashMap<String, String>();
		html.put("&amp;", "&");
		html.put("&lt;", "<");
		html.put("&gt;", ">");
		
		for (Map.Entry<String, String> entry : html.entrySet()) {
			htmlString = htmlString.replaceAll(entry.getValue(), entry.getKey());
		}
		return htmlString;
	}
	
	/**
	 * 把map的值都转换成String类型如果为空，转换成""
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map getStringValMap(Map map) throws Exception {
		for (Object key : map.keySet()) {
			Object val=map.get(key);
			if (val==null||"".equals(val)||"null".equals(val)) {
				val="";
			}
			String type = val.getClass().toString();
			if (type.contains("math")||type.contains("Double")) {
				val=val.toString();
			}
			if(type.contains("Map")){
				Map valMap=(Map) val;
				val=getStringValMap(valMap);
			}
			map.put(key, val);
		}
		return map;
	}
	/**
	 * 把所有的map的值都转换成String类型如果为空，转换成""
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> getStringValMapList(List<Map<String,Object>> list) throws Exception {
		if (list==null||list.size()==0) {
			return list;
		}
		for (int i = 0; i < list.size(); i++) {
			Map map=list.get(i);
			map=getStringValMap(map);
		}
		return list;
	}
	
	/**
	 * 获取异常的堆栈信息字符串
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
	
	/**
	 * 获取指定字符串出现的次数
	 * 
	 * @param srcText 源字符串
	 * @param findText 要查找的字符串
	 * @return
	 */
	public static int appearNumber(String srcText, String findText) {
	    int count = 0;
	    Pattern p = Pattern.compile(findText);
	    Matcher m = p.matcher(srcText);
	    while (m.find()) {
	        count++;
	    }
	    return count;
	}
	
	public static void main(String[] args) {
//		System.out.println(getAnonymous("syh"));
//		System.out.println(decimalFormatComma(11));
//		System.out.println(decimalFormatComma(1));
//		
//		List<String> a = new ArrayList<String>();
//		a.toArray(new String[a.size()]);
		
		System.out.println(appearNumber("管理员,值班编辑,签约摄影师,销售,订户,",","));
		
	}
}
