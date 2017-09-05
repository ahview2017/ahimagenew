package com.deepai.photo.common.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title 数字相关常用操作类
 * @Author ZhouQiang
 * @CrTime 2010-4-16 下午01:06:38
 * @Version 1.0
 */
public class NumberUtils {
	public static void main(String[] args) {
		randomInteger();
		// anotherRandomInteger();
		for(int i=0;i<100;i++)
		System.out.println(randomInteger(2));
	}

	/**
	 * 根据基数产生一定范围之内的随机正整数，区间[0,nbase)
	 */
	public static int randomInteger(int nbase) {
		int nResult = 0;
		if (nbase > 0) {
			Random rd = new Random();
			nResult = rd.nextInt(nbase);
		}
		return nResult;
	}

	/**
	 * 生成900~1000之间的随机数
	 */
	private static void randomInteger() {
		Random rd = new Random(); // 一种方式 java.util.Random
		System.out.println(900 + rd.nextInt(100));

		int i = (int) (Math.random() * 100); // 另一种方式 java.lang.Math
		System.out.println(900 + i);
	}

	/**
	 * 下面这个程序给出在一定范围内的随机数生成方法，随机数生成对每一种编程语言来说都是十分重要的。
	 */
	private static void anotherRandomInteger() {
		Random rdm = new Random();

		// 产生-160到160之间的随机数
		System.out.println("Range from -160 to + 160");
		for (int i = 0; i < 5; i++)
			System.out.println(rdm.nextInt() % 160);

		// 产生0到160之间的随机数
		System.out.println("Range from 0 to 160");
		for (int i = 0; i < 5; i++)
			System.out.println((rdm.nextInt() >>> 1) % 160); // >>>是无符号右移位操作符，在高位插入0

		// 产生0到160之间的随机数的另一种方法
		System.out.println("Range from 0 to 160");
		for (int i = 0; i < 5; i++)
			System.out.println((rdm.nextInt() & 0x7fffffff) % 160); // 将int数的最高位置为0，也就是排除掉负数

		// 产生－160到0之间的随机数
		System.out.println("Range from －160 to 0");
		for (int i = 0; i < 5; i++)
			System.out.println(-(rdm.nextInt() >>> 1) % 160);

		// 产生－160到0之间的随机数的另一种方法
		System.out.println("Range from －160 to 0");
		for (int i = 0; i < 5; i++)
			System.out.println((rdm.nextInt() | 0x80000000) % 160); // 将int数的最高位置为1，也就是排除掉正数
	}
	
	/**
	 * 正在表达式判断字符串是否是整数
	 * @author PuTao
	 * @date 2011-1-13
	 */
	public static Boolean isNumeric(String str){
		   Pattern pattern = Pattern.compile("^-?[0-9]+");
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false;
		   }
		   return true;
	} 
}
