package com.iebm.pfds.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 日期工具类
 *         字母 日期或时间元素 表示 示例 <br>
 *         G Era 标志符 Text AD <br>
 *         y 年 Year 1996; 96 M 年中的月份 Month July; Jul; 07 <br>
 *         w 年中的周数 Number 27 <br>
 *         W 月份中的周数 Number 2 <br>
 *         D 年中的天数 Number 189 <br>
 *         d 月份中的天数 Number 10 <br>
 *         F 月份中的星期 Number 2 E 星期中的天数 Text Tuesday; Tue <br>
 *         a Am/pm 标记 Text PM <br>
 *         H 一天中的小时数（0-23） Number 0 <br>
 *         k 一天中的小时数（1-24） Number 24 <br>
 *         K am/pm 中的小时数（0-11） Number 0 <br>
 *         h am/pm 中的小时数（1-12） Number 12 <br>
 *         m 小时中的分钟数 Number 30 <br>
 *         s 分钟中的秒数 Number 55 <br>
 *         S 毫秒数 Number 978 <br>
 *         z 时区 General time zone Pacific Standard Time; PST; GMT-08:00 <br>
 *         Z 时区 RFC 822 time zone -0800 <br>
 */
public class DateUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * 当月的第几天
	 */
	public static final int TIME_DAYOFMONTH = Calendar.DAY_OF_MONTH;
	/**
	 * 当周所的第几天
	 */
	public static final int TIME_DAYOFWEEK = Calendar.DAY_OF_WEEK;
	/**
	 * 当年的第几天
	 */
	public static final int TIME_DAYOFYEAR = Calendar.DAY_OF_YEAR;
	/**
	 * 当天的第几个小时
	 */
	public static final int TIME_HOUROFDAY = Calendar.HOUR_OF_DAY;
	/**
	 * 分
	 */
	public static final int TIME_MINUTE = Calendar.MINUTE;
	/**
	 * 月
	 */
	public static final int TIME_MONTH = Calendar.MONTH;
	/**
	 * 秒
	 */
	public static final int TIME_SECOND = Calendar.SECOND;
	/**
	 * 年
	 */
	public static final int TIME_YEAR = Calendar.YEAR;
	/**
	 * 一天的毫秒
	 */
	public static final int DAYMIS=1000*60*60*24;


	/**
	 * 日期正则，支持yyyy-MM-dd;yyyy-M-d;支持"-","","/","."分隔
	 */
	private static final Pattern datePattern =Pattern.compile("^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))$");
	
	/**
	 * 日期时间正则，支持yyyy-MM-dd HH:ms:ss;yyyy-M-d HH:ms:ss;支持"-","","/","."分隔
	 */
	private static final Pattern dateTimePattern =Pattern.compile("^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))[\\s]{1}([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
	
	
	

	private DateUtils() {}
	
	/**
	 * 日期运算
	 *
	 * @param date
	 *            源
	 * @param part
	 *            操作部份
	 * @param value
	 *            改变值
	 * @return 计算后的日期
	 */
	public static Date add(Date date, int part, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(part, value);
		return calendar.getTime();
	}

	/**
	 * 取两个日期的差值
	 *
	 * @param from
	 *            开始时间
	 * @param to
	 *            结束时间
	 * @param part
	 *            Time_Minute--相关多少分,Time_HourOfDay-时，other-天
	 * @return 差值
	 */
	public static long diff(Date from, Date to, int part) {
		if (to == null || from == null)
			return 0;
		long d = to.getTime() - from.getTime();
		switch (part) {
			case TIME_MINUTE:
				return d / 1000 / 60;
			case TIME_HOUROFDAY:
				return d / 1000 / 60 / 60;
			default:
				return d / 1000 / 60 / 60 / 24;
		}

	}

	/**
	 * 返回两个时间/日期之间的年份差或月份差
	 * @param from 开始时间
	 * @param to 结束时间
	 * @param month
	 * @return
	 */
	public static int diff(Date from, Date to, boolean month) {

		if (from == null || to == null)
			return 0;
		int y0 = getPartOfDate(from, TIME_YEAR, "");
		int y1 = getPartOfDate(to, TIME_YEAR, "");
		int m0 = getPartOfDate(from, TIME_MONTH, "");
		int m1 = getPartOfDate(to, TIME_MONTH, "");
		int ret = y1 - y0;
		if (month) {
			ret = ret * 12 + m1 - m0;
		}
		return ret;
	}


	/**
	 * 时间/日期格式化函数,缺省时区
	 * @param date 时间
	 * @param format 格式字符串
	 * @return
	 */
	public static String format(Date date, String format) {
		return format(date, format, null);
	}

	/**
	 * 时间/日期格式化函数
	 *
	 * @param date 时间/日期
	 * @param format 格式
	 * @param timeZone
	 *            时区如东八区GMT+8
	 * @return
	 */
	public static String format(Date date, String format, String timeZone) {

		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (timeZone != null && !"".equals(timeZone.trim()))
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(date);
	}

	/**
	 * 获取指定时区当前月份的天数
	 * @param date	日期
	 * @param timeZone	 时区，如GMT+8
	 * @return
	 */
	public static int getDayOfMonth(String timeZone) {
		Calendar cal = new GregorianCalendar(getYearOfDate(new Date(), timeZone), getMonthOfYear(new Date(), timeZone),
				1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 取出时间中的小时
	 * @param date 时间
	 * @return
	 */
	public static int getHourOfDay(Date date) {

		return getTimePart(date, Calendar.HOUR_OF_DAY, null);

	}

	/**
	 * 取出时间中的分钟
	 *
	 * @param date 时间
	 * @return
	 */
	public static int getMinuteOfHour(Date date) {

		return getTimePart(date, Calendar.MINUTE, null);

	}

	/**
	 * 取出时间中的月份
	 * @param date 时间
	 * @return
	 */
	public static int getMonthOfYear(Date date, String timeZone) {

		return getTimePart(date, Calendar.MONTH, timeZone);

	}

	/**
	 * 获取日期的部份数据
	 * @param date 时间
	 * @param part 部分,如年(YEAR)、月(MONTH)、日(DAY)、时(HOUR_OF_DAY)、分(MINUTE)、秒(SECOND)
	 * @param timeZone 时区如GMT+8
	 * @return
	 */
	public static int getPartOfDate(Date date, int part, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !"".equals(timeZone.trim()))
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		int ret = calendar.get(part);
		if (part == TIME_MONTH)
			ret++;
		return ret;

	}

	/**
	 * 获取日期的部份数据
	 *
	 * @param date 时间
	 * @param part 部分,如年(YEAR)、月(MONTH)、日(DAY)、时(HOUR_OF_DAY)、分(MINUTE)、秒(SECOND)
	 * @param timeZone 时区如GMT+8
	 * @return
	 */
	public static int getTimePart(Date date, int part, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !"".equals(timeZone.trim()))
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		return calendar.get(part);

	}

	/**
	 * 取当前日期
	 * @param includeHours
	 *            是否包括时分秒
	 * @param zone
	 *            时区
	 * @return Date
	 */
	public static Date getToday(boolean includeHours, String zone) {
		if (includeHours)
			return toTimeZone(new Date(), zone);
		else
			return valueOf(format(new Date(), "yyyy-MM-dd", zone), "yyyy-MM-dd");
	}

	/**
	 * 取出时间中的年份数据
	 * @param date 时间
	 * @param timeZone 时区
	 * @return
	 */
	public static int getYearOfDate(Date date, String timeZone) {

		return getTimePart(date, Calendar.YEAR, timeZone);

	}

	/**
	 * 时区转换
	 *
	 * @param date 时间
	 * @param locate 时区
	 * @return
	 */
	public static Date toTimeZone(Date date, String locate) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (locate != null && !"".equals(locate.trim()))
			calendar.setTimeZone(TimeZone.getTimeZone(locate));
		return calendar.getTime();
	}


	/**
	 * 字符转换为日期。
	 *
	 * @param source 时间字符串
	 * @param pattern 时间格式,如yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date valueOf(String source, String pattern) {
		return valueOf(source, pattern, null);
	}
	
	/**
	 * 时间对象转字符串
	 * @param source 时间对象
	 * @param pattern 格式
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String parse(Object source, String pattern){
		if(source == null)
			return "";
		Date date_ = new Date(source.toString());
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateStr = sdf.format(date_);
		return dateStr;
	}
	
	/**
	 * 时间字符串转时间对象
	 * @param value 时间字符串
	 * @param pattern 格式
	 * @param timeZone 时区
	 * @param locale 语言和区域
	 * @return
	 */
	public static Date valueOf(String value, String pattern, String timeZone, Locale locale) {
		SimpleDateFormat format;
		if (locale == null)
			format = new SimpleDateFormat(pattern);
		else
			format = new SimpleDateFormat(pattern, locale);
		Date date = null;
		if (value == null)
			return date;
		if (timeZone != null && !"".equals(timeZone.trim()))
			format.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			date = format.parse(value);
		} catch (ParseException e) {
			LOGGER.error("Date convert failure!", e);
		}
		return date;
	}

	/**
	 * 字符串转换为指定时区时间
	 *
	 * @param value 字符串时间
	 * @param pattern 格式，如yyyy-MM-dd HH:mm:ss
	 * @param timeZone 时区，如东八区：GMT+8
	 * @return true:是<br>false:否
	 */
	public static Date valueOf(String value, String pattern, String timeZone) {

		return valueOf(value, pattern, timeZone, null);
	}
	
	/**
	 * 检查是否日期格式串。
	 * <br>支持yyyy-MM-dd;yyyy-M-d;支持"-","","/","."分隔
	 * @param dateStr 日期字符串
	 * @return
	 */
	public static boolean checkDate(String dateStr){
		return datePattern.matcher(dateStr).matches();
	}
	
	/**
	 * 检查是否日期时间格式串
	 * <br>支持yyyy-MM-dd HH:ms:ss;yyyy-M-d HH:ms:ss;支持"-","","/","."分隔
	 * @param dateStr 日期字符串
	 * @return true:是<br>false:否
	 */
	public static boolean checkDateTime(String dateStr){
		return dateTimePattern.matcher(dateStr).matches();
	}
	
	/**
	 * 获取日期的最后一毫秒  如Date为 Mon Mar 13 00:00:00 CST 2017  则返回结果  Mon Mar 13 23:59:59 CST 2017
	 * @param date 目标时间
	 * @return 当天最后一毫秒的时间对象
	 * @throws ParseException 
	 */
	public static Date getDateLastMillisecond(Date date){
		int year = getYearOfDate(date,null);
		int month = getMonthOfYear(date,null)+1;
		int day = getPartOfDate(date,Calendar.DATE,null);
		
	    // 指定一个日期  
	     Date parse = valueOf(year+"-"+month+"-"+day, "yyyy-MM-dd");  
		//返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
	     if (parse != null) {
	    	 long curMillisecond=parse.getTime();//当天的毫秒
	 		long resultMis=curMillisecond+(DAYMIS-1); //当天最后一秒
	 		
			//得到我需要的时间    当天最后一秒
			return new Date(resultMis);
		}
		 return null;
		
	}

	
	

	/**
	 * 获得传入日期所在月的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return valueOf(parse(cal.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");//去掉时分秒
	}
	
	/**
	 * 获得某年某月最后一天，返回日期格式：yyyy-MM-dd
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		String lastDayOfMonth =parse(cal.getTime(), "yyyy-MM-dd");
		return lastDayOfMonth;
	}

	/**
	 * 获得某月的最后一个工作日
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastWorkDayOfMonth(int year, int month) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year); 
		cal.set(Calendar.MONTH, month - 1); 
		cal.set(Calendar.DATE, 1); 
		while(cal.get(Calendar.MONTH) < month){ 
			int day = cal.get(Calendar.DAY_OF_WEEK); 
			if(!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)){ 
				date = (Date)cal.getTime().clone(); 
			} 
			cal.add(Calendar.DATE, 1);
		} 
		return date;
	}
	
	/**
	 * 获得当前月的上一个月的最后一个工作日，返回日期格式：yyyy-MM-dd
	 * @return
	 */
	public static String getLastWorkDayOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		Date date = getLastWorkDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
		return parse(date, "yyyy-MM-dd");
	}
	

	/**
	 * 获得传入日期所在自然季度的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfQuarter (Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int curMonth = cal.get(Calendar.MONTH);//从0开始
		int lastMonth = curMonth;
		switch (curMonth) {
		 	case 0:
		 	case 1:
		 	case 2: {
		 		lastMonth = 2;
		 		break;
		 	}
		 	case 3:
		 	case 4:
		 	case 5: {
		 		lastMonth = 5;
		 		break;
		 	}
		 	case 6:
		 	case 7:
		 	case 8: {
		 		lastMonth = 8;
		 		break;
		 	}
		 	case 9:
		 	case 10:
		 	case 11: {
		 		lastMonth = 11;
		 		break;
		 	}
		}
		cal.set(Calendar.MONTH, lastMonth);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return valueOf(parse(cal.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}
	
	/**
	 * 获得传入日期所在自然季度的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfQuarter (Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int curMonth = cal.get(Calendar.MONTH);
		int lastMonth = curMonth;
		switch (curMonth) {
		 	case 0:
		 	case 1:
		 	case 2: {
		 		lastMonth = 0;
		 		break;
		 	}
		 	case 3:
		 	case 4:
		 	case 5: {
		 		lastMonth = 3;
		 		break;
		 	}
		 	case 6:
		 	case 7:
		 	case 8: {
		 		lastMonth = 6;
		 		break;
		 	}
		 	case 9:
		 	case 10:
		 	case 11: {
		 		lastMonth = 9;
		 		break;
		 	}
		}
		cal.set(Calendar.MONTH, lastMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return valueOf(parse(cal.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}


	/**
	 * 获取某月的最后一天
	 */
	public static Date getLastDayOfMonth(int year, int month, boolean trimTime) {
		Calendar cal = Calendar.getInstance();

		//设置年份
		cal.set(Calendar.YEAR,year);

		//设置月份
		cal.set(Calendar.MONTH, month-1);

		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);

		if (trimTime) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}

		return cal.getTime();
	}

	public static Integer getAccountPeriod(Date date) {
		return Integer.valueOf(format(date, "yyyyMM"));
	}

	public static Integer getNextAccountPeriod(Date date) {
		return Integer.valueOf(format(add(date, Calendar.MONTH, 1), "yyyyMM"));
	}

	public static Integer getNextAccountPeriod(Integer accountPeriod) {
		return Integer.valueOf(format(add(valueOf(accountPeriod + "01", "yyyyMMdd"), Calendar.MONTH, 1), "yyyyMM"));
	}
}
