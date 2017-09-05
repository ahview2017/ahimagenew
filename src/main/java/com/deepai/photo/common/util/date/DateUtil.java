package com.deepai.photo.common.util.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.util.Assert;

import com.deepai.photo.common.util.validator.Checker;


public class DateUtil
{
  private static String defaultDatePattern = "yyyy-MM-dd";

  public static java.util.Date now()
  {
    return new java.util.Date();
  }
  private static final DateFormat DATE_FORMAT_ZH = new SimpleDateFormat("yyyy年MM月dd日");
  public static String getDateZhToday()
  {
      return DATE_FORMAT_ZH.format(new Date());
  }
  public static Calendar getCalendar(java.util.Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar;
  }

  public static Calendar getCurrentCalendar()
  {
    java.util.Date date = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar;
  }

  public static String getTimeByDefaultPattern(java.util.Date date)
  {
    return new SimpleDateFormat(defaultDatePattern).format(date);
  }

  public static String getCurrentTimeByDefaultPattern()
  {
    return new SimpleDateFormat(defaultDatePattern).format(new java.util.Date());
  }

  public static String getTimeByCustomPattern(java.util.Date date, String pattern)
  {
    return new SimpleDateFormat(pattern).format(date);
  }

  public static String getCurrentTimeByCustomPattern(String pattern)
  {
    return new SimpleDateFormat(pattern).format(new java.util.Date());
  }

  public static String getCurrentTimeByCustomPattern(java.util.Date date, String pattern)
  {
    return new SimpleDateFormat(pattern).format(date);
  }

  public static java.util.Date parseByDefaultPattern(String str)
    throws ParseException
  {
    return parseByCustomPattern(str, defaultDatePattern);
  }

  public static java.util.Date parseByCustomPattern(String str, String pattern)
  {
    try
    {
      return new SimpleDateFormat(pattern).parse(str);
    } catch (ParseException e) {
      throw new RuntimeException("���ڸ�ʽת������", e);
    }
  }

  public static int getYear(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);

    return calendar.get(1);
  }

  public static int getMonth(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);

    return calendar.get(2) + 1;
  }

  public static int getDay(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);

    return calendar.get(5) + 1;
  }

  public static int getHour(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);

    return calendar.get(11);
  }

  public static int getMinute(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);

    return calendar.get(12);
  }

  public static int getSecond(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);
    return calendar.get(13);
  }

  public static int getMonthLength(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);

    return calendar.getActualMaximum(5);
  }

  public static java.util.Date getActualMinimumDate(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);
    int actualMininumDay = calendar.getActualMinimum(5);
    calendar.set(5, actualMininumDay);

    return calendar.getTime();
  }

  public static java.util.Date getActualMaximumDate(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);
    int actualMaximumDay = calendar.getActualMaximum(5);
    calendar.set(5, actualMaximumDay);

    return calendar.getTime();
  }

  public static java.util.Date getPreviousMonth(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);
    int month = calendar.get(2);
    calendar.set(2, month - 1);
    calendar.set(5, 1);

    return getActualMaximumDate(calendar.getTime());
  }

  public static java.util.Date getNextMonth(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);
    int month = calendar.get(2);
    calendar.set(2, month + 1);
    calendar.set(5, 1);

    return calendar.getTime();
  }

  public static java.util.Date getPreviousDay(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);
    int day = calendar.get(5);
    calendar.set(5, day - 1);

    return calendar.getTime();
  }

  public static java.util.Date getNextDay(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);
    int day = calendar.get(5);
    calendar.set(5, day + 1);

    return calendar.getTime();
  }

  public static java.util.Date getSimpleDate(java.util.Date date)
  {
    Calendar calendar = getCalendar(date);
    calendar.set(11, 0);
    calendar.set(12, 0);
    calendar.set(13, 0);
    calendar.set(14, 0);

    return calendar.getTime();
  }

  public static java.sql.Date getSqlDate(java.util.Date date) {
    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    return sqlDate;
  }

  public static XMLGregorianCalendar convertToXMLGregorianCalendar(java.util.Date date)
  {
    if (date == null) {
      Assert.notNull(date);
    }
    try
    {
      DatatypeFactory factory = DatatypeFactory.newInstance();
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(date);

      return factory.newXMLGregorianCalendar(calendar);
    } catch (DatatypeConfigurationException e) {
      throw new RuntimeException("�������ʹ���");
    }
  }

  public static java.util.Date convertToDate(XMLGregorianCalendar gregorianCalendar)
  {
    if (gregorianCalendar == null) {
      Assert.notNull(gregorianCalendar);
    }

    Calendar calendar = gregorianCalendar.toGregorianCalendar();

    return calendar.getTime();
  }

  public static Timestamp convertToTimestamp(XMLGregorianCalendar gregorianCalendar)
  {
    java.util.Date date = convertToDate(gregorianCalendar);

    if (date == null) {
      Assert.notNull(gregorianCalendar);
    }

    return new Timestamp(date.getTime());
  }

  public static final java.util.Date convertStringToDate(String aMask, String strDate)
    throws ParseException
  {
    SimpleDateFormat df = null;
    java.util.Date date = null;
    df = new SimpleDateFormat(aMask);
    try
    {
      date = df.parse(strDate);
    }
    catch (ParseException pe) {
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }

    return date;
  }

  public static java.util.Date convertStringToDate(String strDate)
    throws ParseException
  {
    java.util.Date aDate = null;
    try
    {
      aDate = convertStringToDate(defaultDatePattern, strDate);
    } catch (ParseException pe) {
      pe.printStackTrace();
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }

    return aDate;
  }

  public static boolean isDateBetween(java.util.Date compareDate, java.util.Date startDate, java.util.Date endDate)
  {
    if (null == compareDate) {
      Assert.notNull(compareDate);
    } else {
      if ((null != endDate) && (compareDate.before(endDate)) && (null == startDate))
        return true;
      if ((null != startDate) && (((compareDate.after(startDate)) || (compareDate.equals(startDate)))) && (null == endDate))
        return true;
      if ((startDate != null) && (endDate != null) && ((
        ((compareDate.before(endDate)) && (compareDate.after(startDate))) || (compareDate.getTime() == startDate.getTime()) || (compareDate.getTime() == endDate.getTime())))) {
        return true;
      }

    }

    return false;
  }

  public static final java.util.Date getRightNow()
  {
    Calendar cal = Calendar.getInstance();
    cal.set(11, 0);
    cal.set(12, 0);
    cal.set(13, 0);

    return cal.getTime();
  }

  public static String getWeekOfDate(java.util.Date dt)
  {
    String[] weekDays = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
    Calendar cal = Calendar.getInstance();
    cal.setTime(dt);

    int w = cal.get(7) - 1;

    if (w < 0) {
      w = 0;
    }

    return weekDays[w];
  }

  boolean isSameWeekDates(java.util.Date date1, java.util.Date date2)
  {
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);

    int subYear = cal1.get(1) - cal2.get(1);

    if (0 == subYear) {
      if (cal1.get(3) == cal2.get(3))
        return true;
    }
    else if ((1 == subYear) && (11 == cal2.get(2)))
    {
      if (cal1.get(3) == cal2.get(3))
        return true;
    }
    else if ((-1 == subYear) && (11 == cal1.get(2)) && 
      (cal1.get(3) == cal2.get(3))) {
      return true;
    }

    return false;
  }

  public static String getSeqWeek()
  {
    return getSeqWeek(new java.util.Date());
  }

  public static String getSeqWeek(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);

    String week = Integer.toString(c.get(3));

    if (week.length() == 1) {
      week = "0" + week;
    }

    String year = Integer.toString(c.get(1));

    return year + week;
  }

  public static String getWeek(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);

    String week = Integer.toString(c.get(3));

    if (week.length() == 1) {
      week = "0" + week;
    }

    return week;
  }

  public static String getWeekOfMonth(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);

    String week = Integer.toString(c.get(4) + 1);

    if (week.length() == 1) {
      week = "0" + week;
    }

    return week;
  }

  public static String getSeqMonth(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);

    String month = Integer.toString(c.get(2) + 1);

    if (month.length() == 1) {
      month = "0" + month;
    }

    String year = Integer.toString(c.get(1));

    return year + month;
  }

  public static String getSunday(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(7, 1);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

  public static String getMonday(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(7, 2);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

  public static String getTuesday(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(7, 3);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

  public static String getWednesday(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(7, 4);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

  public static String getThursday(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(7, 5);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

  public static String getFriday(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(7, 6);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

  public static String getSaturday(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(7, 7);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

  public static String afterNDay(int n)
  {
    return afterNDay(new java.util.Date(), n);
  }

  public static String afterNDay(java.util.Date date, int n)
  {
    Calendar c = Calendar.getInstance();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    c.setTime(date);
    c.add(5, n);

    java.util.Date d2 = c.getTime();
    String s = df.format(d2);

    return s;
  }

  public static String getLastDayOfMonth(java.util.Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(2, 1);
    c.add(5, -1);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }

  public static String getLastWeekOfMonth(java.util.Date date)
  {
    String week = "";
    try
    {
      week = getWeekOfMonth(convertStringToDate(getLastDayOfMonth(date)));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return week;
  }

  public static String getStartDate(String year, String month, String week)
    throws ParseException
  {
    java.util.Date firsdayOfMonth = convertStringToDate(year + "-" + month + "-01");
    Calendar c = Calendar.getInstance();
    c.setTime(firsdayOfMonth);
    c.set(4, Integer.valueOf(week).intValue());
    c.set(7, 1);

    return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  }
  
  public DateUtil()
  {
  }

  public static DateFormat getDateFormat()
  {
      return DATE_FORMAT;
  }

  public static DateFormat getTimeFormat()
  {
      return TIME_FORMAT;
  }

  public static DateFormat getDatetimeFormat()
  {
      return DATETIME_FORMAT;
  }

  public static DateFormat getTimestampFormat()
  {
      return TIMESTAMP_FORMAT;
  }

  public static String addDateByYear(String date, int years)
  {
      if(!Checker.isDate(date))
      {
          return null;
      } else
      {
          GregorianCalendar calendar = new GregorianCalendar();
          calendar.setTime(getDate(date));
          calendar.add(1, years);
          return getDate(calendar.getTime());
      }
  }

  public static String addDateByDay(String date, int day)
  {
      if(!Checker.isDate(date))
      {
          return null;
      } else
      {
          GregorianCalendar calendar = new GregorianCalendar();
          calendar.setTime(getDate(date));
          calendar.add(6, day);
          return getDate(calendar.getTime());
      }
  }
  
  /**
   * 返回sourceDate增加days天后的日期
   * @param sourceDate
   * @param days
   * @return
   */
  public static Date addDateByDay(Date sourceDate, int days) {
	  GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(sourceDate);
      calendar.add(6, days);
      return calendar.getTime();
  }

  public static String addDatetimeByDay(String datetime, int days)
  {
      if(!Checker.isTimestamp(datetime) && !Checker.isDate(datetime))
          return null;
      if(Checker.isDate(datetime))
          datetime = (new StringBuilder(String.valueOf(datetime))).append(" 00:00:00.000").toString();
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(getDate(datetime));
      calendar.add(6, days);
      return getTimestamp(calendar.getTime());
  }

  public static String addDatetimeByMonth(String datetime, int months)
  {
      if(!Checker.isTimestamp(datetime) && !Checker.isDate(datetime))
          return null;
      if(Checker.isDate(datetime) && !Checker.isTimestamp(datetime))
          datetime = (new StringBuilder(String.valueOf(datetime))).append(" 00:00:00.000").toString();
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(getDate(datetime));
      calendar.add(2, months);
      return getTimestamp(calendar.getTime());
  }

  public static String addDatetimeBySecond(String datetime, int seconds)
  {
      if(!Checker.isTimestamp(datetime) && !Checker.isDate(datetime))
          return null;
      if(Checker.isDate(datetime))
          datetime = (new StringBuilder(String.valueOf(datetime))).append(" 00:00:00.000").toString();
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(getDate(datetime));
      calendar.add(13, seconds);
      return getTimestamp(calendar.getTime());
  }

  public static String getCurrentDate()
  {
      return getDate(new Date());
  }

  public static String getFirstDateOfMonth()
  {
      String date = getDate(new Date());
      return (new StringBuilder(String.valueOf(date.substring(0, 8)))).append("01").toString();
  }

  public static String getDate(Date date)
  {
      return DATE_FORMAT.format(date);
  }

  public static Date getDate(String dateStr)
  {
      if(Checker.isDate(dateStr))
          try
          {
              return DATE_FORMAT.parse(dateStr);
          }
          catch(Exception e)
          {
              return null;
          }
      if(Checker.isTime(dateStr))
          try
          {
              return TIME_FORMAT.parse(dateStr);
          }
          catch(Exception e)
          {
              return null;
          }
      if(Checker.isTimestamp(dateStr))
          try
          {
              if(dateStr.indexOf(".") < 0)
                  dateStr = (new StringBuilder(String.valueOf(dateStr))).append(".000").toString();
              return TIMESTAMP_FORMAT.parse(dateStr);
          }
          catch(Exception e)
          {
              return null;
          }
      else
          return null;
  }

  public static String getCurrentDatetime()
  {
      return getDatetime(new Date());
  }

  public static String getCurrentTimestamp()
  {
      return getTimestamp(new Date());
  }

  public static String getCurrentTime()
  {
      return getTime(new Date());
  }

  public static String getTime(Date date)
  {
      return TIME_FORMAT.format(date);
  }

  public static String getDatetime(Date date)
  {
      return DATETIME_FORMAT.format(date);
  }

  public static String getTimestamp(Date date)
  {
      return TIMESTAMP_FORMAT.format(date);
  }

  private static String getFormatDatetime(String datetime)
  {
      if(Checker.isTimestamp(datetime))
          return datetime;
      StringBuffer retStr;
      StringTokenizer st;
      String s;
      int count;
      try
      {
          retStr = new StringBuffer();
          st = new StringTokenizer(datetime.trim());
          retStr.append(st.nextToken());
          if(retStr.substring(6, 7).equals("-"))
              retStr = retStr.insert(5, "0");
          if(retStr.length() < 10)
              retStr = retStr.insert(8, "0");
          if(st.countTokens() < 1)
              return retStr.append(" 00:00:00.000").toString();
      }
      catch(Exception e)
      {
          return null;
      }
      s = new String();
      s = st.nextToken();
      st = new StringTokenizer(s, ":");
      count = st.countTokens();
      if(count == 1)
          retStr.append(" ").append(s).append(":00:00.000");
      if(count == 2)
          retStr.append(" ").append(s).append(":00.000");
      if(count == 3)
          retStr.append(" ").append(s);
      return retStr.toString();
  }

  public static String getFormatDate(String date)
  {
      if(date.substring(6, 7).equals("-"))
          date = (new StringBuilder(String.valueOf(date.substring(0, 5)))).append("0").append(date.substring(5)).toString();
      if(date.length() < 10)
          date = (new StringBuilder(String.valueOf(date.substring(0, 8)))).append("0").append(date.substring(8)).toString();
      return date;
  }

  public static int getMonth(String datetime)
  {
      String s = getFormatDatetime(datetime);
      if(!Checker.isTimestamp(s))
          return 0;
      else
          return Integer.parseInt(s.substring(5, 7));
  }

  public static int getYear(String datetime)
  {
      String s = getFormatDatetime(datetime);
      if(!Checker.isTimestamp(s))
          return 0;
      else
          return Integer.parseInt(s.substring(0, 4));
  }

  public static int getDay(String datetime)
  {
      String s = getFormatDatetime(datetime);
      if(!Checker.isTimestamp(s))
          return 0;
      else
          return Integer.parseInt(s.substring(8, 10));
  }

  public static int getHour(String datetime)
  {
      String s = getFormatDatetime(datetime);
      if(!Checker.isTimestamp(s))
          return 0;
      else
          return Integer.parseInt(s.substring(11, 13));
  }

  public static int getMinute(String datetime)
  {
      String s = getFormatDatetime(datetime);
      if(!Checker.isTimestamp(s))
          return 0;
      else
          return Integer.parseInt(s.substring(14, 16));
  }

  public static int getSecond(String datetime)
  {
      String s = getFormatDatetime(datetime);
      if(!Checker.isTimestamp(s))
          return 0;
      else
          return Integer.parseInt(s.substring(17, 19));
  }

  public static String get400Datetime(String datetime)
  {
      StringBuffer s = new StringBuffer(getFormatDatetime(datetime));
      return s.replace(10, 11, "-").replace(13, 14, ".").replace(16, 17, ".").toString();
  }

  public static String getFormatDateStr(String datetime, int Type)
  {
      if(Checker.isEmpty(datetime))
          return "";
      datetime = datetime.trim();
      StringBuffer dtStr = new StringBuffer();
      if(datetime.indexOf(".") > 0)
          datetime = datetime.substring(0, datetime.indexOf("."));
      int dtArry[] = new int[6];
      dtArry[0] = Integer.parseInt(datetime.substring(0, 4));
      dtArry[1] = Integer.parseInt(datetime.substring(5, 7));
      dtArry[2] = Integer.parseInt(datetime.substring(8, 10));
      dtArry[3] = Integer.parseInt(datetime.substring(11, 13));
      dtArry[4] = Integer.parseInt(datetime.substring(14, 16));
      dtArry[5] = Integer.parseInt(datetime.substring(17));
      switch(Type)
      {
      case 1: // '\001'
          return dtStr.append(dtArry[0]).append("\u5E74").append(dtArry[1]).append("\u6708").append(dtArry[2]).append("\u65E5").toString();

      case 2: // '\002'
          return dtStr.append(dtArry[3]).append("\u65F6").append(dtArry[4]).append("\u5206").append(dtArry[5]).append("\u79D2").toString();

      case 3: // '\003'
          return dtStr.append(dtArry[0]).append("\u5E74").append(dtArry[1]).append("\u6708").append(dtArry[2]).append("\u65E5").append(" ").append(dtArry[3]).append("\u65F6").append(dtArry[4]).append("\u5206").append(dtArry[5]).append("\u79D2").toString();

      case 4: // '\004'
          return dtStr.append(dtArry[1]).append("/").append(dtArry[2]).append(" ").append(dtArry[3]).append(":").append(dtArry[4]).toString();

      case 5: // '\005'
          return dtStr.append(dtArry[2]).append("\u65E5").append(dtArry[3]).append(":").append(dtArry[4]).toString();
      }
      return datetime;
  }

  public static boolean isLeapYear(int year)
  {
      if(year % 400 == 0)
          return true;
      return year % 4 == 0 && year % 100 != 0;
  }

  public static boolean isLeapYear(String year)
  {
      if(!Checker.isInteger(year))
          return false;
      else
          return isLeapYear(Integer.parseInt(year));
  }

  public static String getEndDateOfMonth(int year, int month)
  {
      if(year < 1000 || year > 9999 || month > 12 || month < 1)
          return "";
      StringBuffer temp = new StringBuffer();
      temp.append(year).append("-");
      if(month < 10)
          temp.append("0");
      temp.append(month).append("-");
      GregorianCalendar gc = new GregorianCalendar(year, --month, 1, 0, 0, 0);
      long first = gc.getTimeInMillis();
      gc.set(year, ++month, 1, 0, 0, 0);
      long second = gc.getTimeInMillis();
      return temp.append((second - first) / 0x5265c00L).toString();
  }

  public static long getDays(String date1, String date2)
  {
      return getMilliseconds(date1, date2) / 0x5265c00L;
  }
  
  public static long getDays(Date date1, Date date2)
  {
      return (date2.getTime() - date1.getTime()) / 0x5265c00L;
  }
  
  /**
   * 注:本方法为获取大概的天数差,忽略时分秒的影响.
   * 若要精确计算请使用方法getDays
   * 例如:2015-7-1 23:59:59到2015-7-2 00:00:01的天数差为1.
   * @param startDate
   * @param endDate
   * @return
   */
  public static long getApproximateDays(Date startDate, Date endDate) {
	  return getDays(getDate(startDate), getDate(endDate));
  }
  
  /**
   * 日期数组比较，如果两数组长度相等，且排序后每个相同下标的日期也相等，则返回true
   * 注意：本方法在比较前，会对数组进行排序。
   * @param array1
   * @param array2
   * @return
   */
  public static boolean dateArrayEquals(Date[] array1, Date[] array2) {
	  if (array1 == null || array2 == null) {
		  return false;
	  }
	  if (array1.length != array2.length) {
		  return false;
	  }
	  
	  Arrays.sort(array1);
	  Arrays.sort(array2);
	  int times = array1.length;
	  for (int i = 0; i < times; i++) {
		  if (!array1[i].equals(array2[i]))
			  return false;
	  }
	  return true;
  }

  public static long getHours(String date1, String date2)
  {
      return getMilliseconds(date1, date2) / 0x36ee80L;
  }

  public static long getSeconds(String date1, String date2)
  {
      return getMilliseconds(date1, date2) / 1000L;
  }

  public static long getMilliseconds(String date1, String date2)
  {
      return getDate(date2).getTime() - getDate(date1).getTime();
  }

  public static void main(String args[])
  {
      System.out.println(getEndDateOfMonth(2011, 1));
  }

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
  private static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

}