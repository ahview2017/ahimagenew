package com.deepai.photo.common.util;

public class ShieldUtil {

	public static String showBankId(String string){
		if (string == null || "".equals(string)) {
			return "";
		}
		int length = string.length();
		if (length >= 2) {
			String begin = string.substring(0, 3);
			String subString = string.substring(3, string.length() - 4);
			String center = replaceSub(subString);
			String end = string.substring(string.length() - 4, string.length());
			String str = begin + center + end;
			return str;
		}
		return string;
	}
	
	public static String showRealName(String string){
		if (string == null || "".equals(string)) {
			return "";
		}
		int length = string.length();
		if (length >= 2) {
			String subString = string.substring(1, string.length());
			String end = replaceSub(subString);
			String begin = string.substring(0, 1);
			String str = begin + end;
			return str;
		}
		return string;
	}
	
	public static String replaceSub(String str) {
		String string = "";
		for (int i = 0; i < str.length(); i++) {
			string += "*";
		}
		return string;
	}
	
	public static void main(String[] args) {
		System.out.println(showRealName("1234567890"));
	}
}
