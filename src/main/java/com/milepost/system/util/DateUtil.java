package com.milepost.system.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类(这个类是自己写，里面可能有写方法有问题，如发现有问题的，逐步的改成使用
 * org.apache.commons.lang.time包下面的两个类，
 * org.apache.commons.lang.time.DateFormatUtils;
 * org.apache.commons.lang.time.DateUtils;
 * @author HRF
 */
public class DateUtil {

	public static final String[] DEFAULT_PARSERS = new String[] {	
			"yyyy-MM-dd HH:mm:ss.S",
			"yyyy年MM月dd日  HH时mm分ss秒SSS毫秒", 
			"yyyy-MM-dd HH:mm:ss", 
			"yyyy年MM月dd日  HH时mm分ss秒", 
			"yyyy-MM-dd",
			"yyyy年MM月dd日"
		};
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	// 最大的 Date 值，
	public static final String MAX = "9999-01-01 08:00:00";

	// 最小值 Date 值
	public static final String MIN = "1970-01-01 08:00:00";
	
	public static final String DEFAULT_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

	public static final String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

	public static final String FORMAT_SHORT = "yyyy-MM-dd";

	public static final String FORMAT_SHORT_CN = "yyyy年MM月dd";

	/**
	 * 将一个 Date 转换成指定格式的字符串，如果不传入 Date 则默认返回的当前时间
	 * 
	 * @param currTime：默认为当前时间
	 * @param formatSymbols：默认为“yyyy-MM-dd
	 *            HH:mm:ss”，即 “2014-04-25 14:05:06”
	 * @return
	 * 
	 * 		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
	 *         ,Locale.US);//2014-04-25 14:05:06 new
	 *         SimpleDateFormat("yyyy-M-d",Locale.US);//2014-4-25 new
	 *         SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",Locale.US);//2014-04-25-14
	 *         -05-06
	 * 
	 */
	public static String date2Str(Date date, String formatSymbols) {

		if (date == null) {
			date = new Date();
		}

		if (!ValidateUtil.isValid(formatSymbols)) {
			formatSymbols = "yyyy-MM-dd HH:mm:ss";
		}

		SimpleDateFormat formatter = new SimpleDateFormat(formatSymbols, Locale.US);
		String strTimeString = "";
		try {
			strTimeString = formatter.format(date);
		} catch (Exception e) {
			logger.error("DateUtil#date2Str{}..." + "date:" + date + ";formatSymbols:" + formatSymbols, e);
		}
		return strTimeString;
	}

	/**
	 * 接受字符串形式的时间格式和时间值，返回时间的 Date，如果发生异常则返回 null
	 * 
	 * 
	 * @param timeFormat
	 *            : 时间格式，如：“yyyy-MM-dd HH:mm:ss”
	 * @param timeValue
	 *            ：时间值 2014-04-25 14-05-06
	 * @return
	 * 
	 * 已经过期，有时候会不准确
	 */
	@Deprecated
	public static Date str2Date(String timeFormat, String timeValue) {

		Long result = 0L;
		if (timeFormat == null || timeValue == null) {
			return null;
		}

		SimpleDateFormat parseSdf = new SimpleDateFormat(timeFormat, Locale.US);

		try {
			result = parseSdf.parse(timeValue).getTime();
		} catch (Exception e) {
			logger.error("DateUtil#str2Date{}..." +  "timeFormat:" + timeFormat + ";timeValue:" + timeValue, e);
			return null;
		}
		return new Date(result);
	}
	
	
	public static Date str2Date(String[] parsers, String timeValue) {
		Date result = null;
		if(!ValidateUtil.isValid(parsers)){
			parsers = DEFAULT_PARSERS;
		}
		try {
			result = DateUtils.parseDate(timeValue, parsers);
		} catch (Exception e) {
			logger.error("DateUtil#str2Date{}..." +  "parsers:" + parsers + ";timeValue:" + timeValue, e);
			return null;
		}
		return result;
	}
	
	public static Date str2Date(String timeValue) {
		Date result = null;
		try {
			result = DateUtils.parseDate(timeValue, DEFAULT_PARSERS);
		} catch (Exception e) {
			logger.error("DateUtil#str2Date{}..." +  "parsers:" + DEFAULT_PARSERS + ";timeValue:" + timeValue, e);
			return null;
		}
		return result;
	}

	
	/*
	 * 以下方法都是从点都中拿过来的，见 “com.saas.ddkj.zz.property.common.util.DateUtils”
	 * */
	
	/**
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(5, n);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(2, n);
		return cal.getTime();
	}

	/**
	 * 当前月份中的第一天
	 * @param currentDate
	 * @return
	 */
	public static Date getBeginDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		//result.set(currentDate.get(1), currentDate.get(2), result.getActualMinimum(5));
		result.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), result.getActualMinimum(Calendar.DAY_OF_MONTH));
		return result.getTime();
	}

	/**
	 * @param currentDate
	 * @return
	 */
	public static Date getMaxDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		//result.set(currentDate.get(1), currentDate.get(2), currentDate.getActualMaximum(5));
		//currentDate.getActualMaximum(5)，给定此 Calendar 的时间值，返回指定日历字段可能拥有的最大值。
		result.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.getActualMaximum(5));

		return result.getTime();
	}

	/**
	 * 当前时间与给定时间(字符串类型之间相隔的天数，字符串参数的格式为“FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";”,精确到毫秒
	 * @param date
	 * @return
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000L - t1 / 1000L) / 3600 / 24;
	}

	/**
	 * 两个时间的小时差
	 * @param start
	 * @param end
	 * @return
	 */
	public static int countHours(Date start, Date end) {
		long s = start.getTime();
		long e = end.getTime();
		return (int) (e / 1000L - s / 1000L) / 3600;
	}

	public static int countDays(Date start, Date end) {
		long s = start.getTime();
		long e = end.getTime();
		return (int) (e / 1000L - s / 1000L) / 3600 / 24;
	}

	public static int countDay(String date1, String date2, String format) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(parse(date1, format));
		long t1 = c1.getTime().getTime();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(parse(date2, format));
		long t2 = c2.getTime().getTime();
		return (int) (t2 / 1000L - t1 / 1000L) / 3600 / 24;
	}

	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000L - t1 / 1000L) / 3600 / 24;
	}

	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return returnValue;
	}

	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	public static String getNow() {
		return format(new Date());
	}

	public static String getNow(String format) {
		return format(new Date(), format);
	}

	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
		}
		return null;
	}
}
