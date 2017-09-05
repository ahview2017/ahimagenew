package com.deepai.photo.common.util.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @ClassName: OperateTime
 * @Description: 对时间、日期的操作类
 * @author deepai
 * @date 2015年1月15日 下午3:40:18
 */
public class OperateTime {
	/**
	 * 
	 * @Title: getExpire
	 * @Description: 获取大于date时间afterDays天的时间戳
	 * @param date
	 * @return Date
	 * @throws
	 */
	public static Date getExpire(Date date, int afterDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, afterDays);
		// System.out.println((new
		// SimpleDateFormat("yyyy-MM-dd hh-mm-ss")).format(cal.getTime()));
		return cal.getTime();
	}

	public static void main(String[] args) {
		System.out.println(OperateTime.getExpire(new Date(), 2).getTime());
	}
}
