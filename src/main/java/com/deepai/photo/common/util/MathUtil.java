package com.deepai.photo.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * cn.org.niec.irmis.kernel.util.MathUtil, 数学方法的工具类，提供常用数学公式的实现方法。
 *
 */
public class MathUtil {
	private static final String pattern = "##,##0.00";
	private static final String numberpattern = "####0.00";
	private static final int DEF_DIV_SCALE = 10;
	
	public static String formatDecimal(BigDecimal n){
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(pattern);
		return df.format(n);
	}
	
	public static String formatNumber(BigDecimal n){
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(numberpattern);
		return df.format(n);
	}
	
	/**
	 * 公式 2的n次方 的结果。
	 * @param n
	 * @return
	 */
	public static long base2Pow(int n) {
		if (n == 0) {
			return 1;
		}
		else {
			return 2L << (n - 1);
		}
	}
	/**
	 * 公式 1*2*3*...*n 的结果
	 * @param n
	 * @return
	 */
	public static int factorial(int n) {
		if (n < 0) {
			return 0;
		}

		int factorial = 1;

		while (n > 1) {
			factorial = factorial * n;
			n = n - 1;
		}

		return factorial;
	}
	/**
	 * 判断参数是否为偶数
	 * @param x
	 * @return
	 */
	public static boolean isEven(int x) {
		if ((x % 2) == 0) {
			return true;
		}

		return false;
	}
	/**
	 * 判断参数是否为基数，与isEven(int)相反
	 * @param x
	 * @return
	 */
	public static boolean isOdd(int x) {
		return !isEven(x);
	}
	/**
	 * 找到参数max以内的所有素数。
	 * @param max
	 * @return
	 */
	public static int[] generatePrimes(int max) {
		if (max < 2) {
			return new int[0];
		}
		else {
			boolean[] crossedOut = new boolean[max + 1];

			for (int i = 2; i < crossedOut.length; i++) {
				crossedOut[i] = false;
			}

			int limit = (int)Math.sqrt(crossedOut.length);

			for (int i = 2; i <= limit; i++) {
				if (!crossedOut[i]) {
					for (int multiple = 2 * i; multiple < crossedOut.length;
							multiple += i) {

						crossedOut[multiple] = true;
					}
				}
			}

			int uncrossedCount = 0;

			for (int i = 2; i < crossedOut.length; i++) {
				if (!crossedOut[i]) {
					uncrossedCount++;
				}
			}

			int[] result = new int[uncrossedCount];

			for (int i = 2, j = 0; i < crossedOut.length; i++) {
				if (!crossedOut[i]) {
					result[j++] = i;
				}
			}

			return result;
		}
	}

	/**
     * 提供精确的加法运算。

     * @param v1 被加数

     * @param v2 加数

     * @return 两个参数的和

     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的加法运算。

     * @param v1 被加数

     * @param v2 加数

     * @return 两个参数的和

     */

    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            v1 = BigDecimal.valueOf(0);
        }
        if (v2 == null) {
            v2 = BigDecimal.valueOf(0);
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2);
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
 
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();

    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */

    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2);
    }


    /**
     * 提供精确的乘法运算。

     * @param v1 被乘数

     * @param v2 乘数

     * @return 两个参数的积

     */

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。

     * @param v1 被乘数

     * @param v2 乘数

     * @return 两个参数的积

     */

    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到

     * 小数点以后10位，以后的数字四舍五入。

     * @param v1 被除数

     * @param v2 除数

     * @return 两个参数的商

     */

    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到

     * 小数点以后10位，以后的数字四舍五入。

     * @param v1 被除数

     * @param v2 除数

     * @return 两个参数的商

     */

    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指

     * 定精度，以后的数字四舍五入。

     * @param v1 被除数

     * @param v2 除数

     * @param scale 表示表示需要精确到小数点以后几位。

     * @return 两个参数的商

     */

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指

     * 定精度，以后的数字四舍五入。

     * @param v1 被除数

     * @param v2 除数

     * @param scale 表示表示需要精确到小数点以后几位。

     * @return 两个参数的商

     */

    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 提供精确的小数位四舍五入处理。

     * @param v 需要四舍五入的数字

     * @param scale 小数点后保留几位

     * @return 四舍五入后的结果

     */

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。

     * @param v 需要四舍五入的数字

     * @param scale 小数点后保留几位

     * @return 四舍五入后的结果

     */
    public static BigDecimal round(BigDecimal v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v.toString());
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 去尾法，data小数点2位后的内容直接舍去
     * @param data
     * @return
     */
	public static double roundOff(double data) {
    	return Math.floor(data * 100) / 100;
    }
    
    public static void main(String[] args) {
		System.out.println(MathUtil.formatNumber(new BigDecimal("1111111111.33333")));
	}

}