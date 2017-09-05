package com.deepai.photo.common.util.html;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.deepai.photo.common.util.MyStringUtil;

/**
 * 用于远程获取文件的url初始化
 * 
 * @author lizongjie
 *
 */
public class UrlUtil {
	private static URL url;
	private static HttpURLConnection con;
	private static int state = -1;

	/**
	 * 功能：检测当前URL是否可连接或是否有效, 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用
	 * 
	 * @param urlStr
	 *            指定URL网络地址
	 * @return URL
	 */
	public static synchronized URL getUrl(String urlStr) {
		int counts = 0;
		if (urlStr == null || urlStr.length() <= 0) {
			return null;
		}
		while (counts < 5) {
			try {
				url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				state = con.getResponseCode();
				if (state == 200) {
					return url;
				}
			} catch (Exception ex) {
				counts++;
				continue;
			}
		}
		return null;
	}

	/**
	 * 从字符串中得到有效的url列表
	 * 
	 * @param urlStr
	 * @return
	 */
	public static List<String> getUrlsByString(String urlStr) {
		List<String> urlList = new ArrayList<String>();
		if (MyStringUtil.isEmpty(urlStr)) {
			return urlList;
		}
		Pattern pattern = Pattern.compile("http://[\\w\\.\\-/:]+");
		Matcher matcher = pattern.matcher(urlStr);
		while (matcher.find()) {
			urlList.add(matcher.group());
		}
		return urlList;
	}

	public static String getKeyFromUrl(String url) {
		if (MyStringUtil.isEmpty(url)) {
			return null;
		} else {
			int p = url.lastIndexOf("/");
			if (p > 0) {
				return url.substring(p + 1);
			} else {
				return null;
			}
		}
	}

}
