/*
 * Title: TRS 身份服务器 Copyright: Copyright (c) 2004-2005, TRS信息技术有限公司. All rights reserved. License: see the license file.
 * Company: TRS信息技术有限公司(www.trs.com.cn)
 * 
 * Created on 2006-2-17 15:35:27, by liushen
 */
package com.deepai.photo.common.util;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.util.StringHelper;

/**
 * IP工具类. <BR>
 * 
 * @author TRS信息技术有限公司
 */
public class IPUtil {

	public static final String IP_SEGMENT_SPLITTER = "-";
	private static final String REG_IP_V4 = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
	private static final String REG_IP_V4_SEG = REG_IP_V4 + "\\-" + REG_IP_V4;

	/**
	 * 验证IP是否有效
	 * 
	 * @param ip
	 *            IP地址，如192.9.200.10，不支持通配符
	 * @return true if valid or empty, false otherwise.
	 */
	public static boolean isValidV4IP(String ip) {
		if (ip == null) {
			return false;
		}
		ip = ip.trim();
		if (ip.length() == 0) {
			return false;
		}
		String[] part_IP = ip.split("\\.");
		if (part_IP.length != 4) {
			return false;
		}
		int int_IP;
		for (int i = 0; i < 4; i++) {
			try {
				int_IP = Integer.parseInt(part_IP[i]);
				if (int_IP < 0 || int_IP > 255) {
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	

	

	/**
	 * 处理代理模式下获取原始客户端IP，优先获取Header里面X-Forwarded-For属性
	 * 
	 * @param request
	 * @return
	 * @author xiayunan
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String xForwardedFor = request.getHeader("X-Forwarded-For");
		String remoteAddr = request.getRemoteAddr();
		if (xForwardedFor != null && xForwardedFor.length() > 4) {
			int index = xForwardedFor.indexOf(",");
			if (index != -1) {
				remoteAddr = xForwardedFor.substring(0, index);
			} else {
				remoteAddr = xForwardedFor;
			}
		}
		return remoteAddr;
	}

	/**
	 * IP或者IP段是否包含IP地址
	 * 
	 * @param ipOrIpSegment
	 *            IP或者IP段 ， 如192.9.200.10， 或者192.9.200.0-192.9.200.255，不支持通配符
	 * @param ipAddress
	 *            IP地址，如192.9.200.10，不支持通配符
	 * @return
	 * @since v3.5
	 * @creator shixin @ 2010-6-24
	 */
	public static boolean isIpOrSegmentContainsIpAddr(String ipOrIpSegment, String ipAddress) {
		if (ipOrIpSegment == null || ipOrIpSegment.length() == 0) {
			return false;
		}

		if (ipAddress == null || ipAddress.length() == 0) {
			return false;
		}
		// 如果是IP值，且相等
		if (-1 == ipOrIpSegment.indexOf(IP_SEGMENT_SPLITTER) && ipOrIpSegment.equals(ipAddress)) {
			return true;
		}
		// 如果都是IP段
		if (-1 != ipOrIpSegment.indexOf(IP_SEGMENT_SPLITTER) && -1 != ipAddress.indexOf(IP_SEGMENT_SPLITTER)) {
			return ipOrIpSegment.equals(ipAddress);
		}

		// 如果是IP段
		if (-1 != ipOrIpSegment.indexOf(IP_SEGMENT_SPLITTER)) {
			return isIpInSegment(ipOrIpSegment, ipAddress);
		}
		return false;
	}

	

	/**
	 * 判断IP是否在指定IP段范围内
	 * 
	 * @param ipSegment
	 *            IP段，如192.9.200.0-192.9.200.255，不支持通配符
	 * @param ipAddr
	 *            IP地址，如192.9.200.10，不支持通配符
	 * @return IP地址在IP段范围内，返回true，否则返回false
	 * @since v3.5
	 * @creator shixin @ 2010-6-24
	 */
	public static boolean isIpInSegment(String ipSegment, String ipAddr) {
		if (ipSegment == null) {
			return false;
		}
		if (ipAddr == null) {
			return false;
		}
		ipSegment = ipSegment.trim();
		ipAddr = ipAddr.trim();
		if (!ipSegment.matches(REG_IP_V4_SEG) || !ipAddr.matches(REG_IP_V4)) {
			return false;
		}
		int splitterIndex = ipSegment.indexOf('-');
		String[] ipSegBeforeArray = ipSegment.substring(0, splitterIndex).split("\\.");
		String[] ipSegEndArray = ipSegment.substring(splitterIndex + 1).split("\\.");
		String[] ipAddrArray = ipAddr.split("\\.");
		long ipSegBeforeLongValue = 0L, ipSegEndLongValue = 0L, ipAddrLongValue = 0L;
		for (int i = 0; i < 4; ++i) {
			ipSegBeforeLongValue = ipSegBeforeLongValue << 8 | Integer.parseInt(ipSegBeforeArray[i]);
			ipSegEndLongValue = ipSegEndLongValue << 8 | Integer.parseInt(ipSegEndArray[i]);
			ipAddrLongValue = ipAddrLongValue << 8 | Integer.parseInt(ipAddrArray[i]);
		}
		if (ipSegBeforeLongValue > ipSegEndLongValue) {
			long t = ipSegBeforeLongValue;
			ipSegBeforeLongValue = ipSegEndLongValue;
			ipSegEndLongValue = t;
		}
		return ipSegBeforeLongValue <= ipAddrLongValue && ipAddrLongValue <= ipSegEndLongValue;
	}

	/**
	 * IP集合是否包含当前IP
	 * 
	 * @param ipSet
	 *            IP或者IP段集合
	 * @param ipAddress
	 *            IP地址
	 * @return 包含返回true，否则返回false
	 */
	public static boolean containIpAddr(Set ipSet, String ipAddress) {
		if (ipSet == null || ipSet.size() == 0) {
			return false;
		}
		Object[] ipArray = ipSet.toArray();
		for (int i = 0; i < ipArray.length; i++) {
			String ipOrSegment = (String) ipArray[i];
			if (isIpOrSegmentContainsIpAddr(ipOrSegment, ipAddress)) {
				return true;
			}
		}
		return false;
	}

	// /**
	// * 把IP地址转化为int<br>
	// * 1) 把IP地址转化为字节数组；<br>
	// * 2）通过左移位（<<）、与（&）、或（|）这些操作转为int
	// *
	// * @param ipAddr
	// * @return int
	// */
	// public static int ipToInt(String ipAddr) {
	// if (false == ipAddr.matches(REG_IP_V4)) {
	// throw new IllegalArgumentException(ipAddr + " is invalid IP");
	// }
	//
	// try {
	// return bytesToInt(ipToBytesByInet(ipAddr));
	// } catch (Exception e) {
	// throw new IllegalArgumentException(ipAddr + " is invalid IP");
	// }
	// }

	// /**
	// * 把IP地址转化为字节数组
	// *
	// * @param ipAddr
	// * @return byte[]
	// */
	// public static byte[] ipToBytesByInet(String ipAddr) {
	// try {
	// return InetAddress.getByName(ipAddr).getAddress();
	// } catch (Exception e) {
	// throw new IllegalArgumentException(ipAddr + " is invalid IP");
	// }
	// }

	// /**
	// * 根据位运算把 byte[] -> int
	// *
	// * @param bytes
	// * @return int
	// */
	// public static int bytesToInt(byte[] bytes) {
	// int addr = bytes[3] & 0xFF;
	// addr |= ((bytes[2] << 8) & 0xFF00);
	// addr |= ((bytes[1] << 16) & 0xFF0000);
	// addr |= ((bytes[0] << 24) & 0xFF000000);
	// return addr;
	// }

	// /**
	// * 把int转化为ip地址<br>
	// * 1)将整数值进行右移位操作（>>>），右移24位，再进行与操作符（&）0xFF，得到的数字即为第一段IP；<br>
	// * 2)将整数值进行右移位操作（>>>），右移16位，再进行与操作符（&）0xFF，得到的数字即为第二段IP；<br>
	// * 3)将整数值进行右移位操作（>>>），右移8位，再进行与操作符（&）0xFF，得到的数字即为第三段IP；<br>
	// * 4)将整数值进行与操作符（&）0xFF，得到的数字即为第四段IP；<br>
	// *
	// * @param ipInt
	// * @return String
	// */
	// public static String intToIp(int ipInt) {
	// return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.').append((ipInt >> 16) & 0xff).append('.')
	// .append((ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff)).toString();
	// }

	

	/**
	 * 整数值转换回IP地址
	 * 
	 * @param ipLongValue
	 *            整数值
	 * @return IP地址
	 * @since v3.5
	 * @creator SHIXIN-dev4 @ 2013-3-1
	 */
	public static String long2Ip(long ipLongValue) {
		// StringBuilder sb = new StringBuilder();
		// sb.append(ipLongValue & 0xFF).append(".");
		// sb.append((ipLongValue >> 8) & 0xFF).append(".");
		// sb.append((ipLongValue >> 16) & 0xFF).append(".");
		// sb.append((ipLongValue >> 24) & 0xFF);
		// return sb.toString();

		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf((ipLongValue >>> 24)));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((ipLongValue & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// 将高16位置0，然后右移8位
		sb.append(String.valueOf((ipLongValue & 0x0000FFFF) >>> 8));
		sb.append(".");
		// 将高24位置0
		sb.append(String.valueOf((ipLongValue & 0x000000FF)));
		return sb.toString();
	}

	
}