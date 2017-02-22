package com.milepost.system.util;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

/**
 * 测试
 * org.apache.commons.lang.time.DateFormatUtils;
 * org.apache.commons.lang.time.DateUtils;
 * 包中的两个关于时间处理的类
 * @author HRF
 *
 */
public class DateUtilTest {

	public static final String[] parsers = new String[] {	"yyyy-MM-dd HH:mm:ss.S",
															"yyyy年MM月dd日  HH时mm分ss秒SSS毫秒", 
															"yyyy-MM-dd HH:mm:ss", 
															"yyyy年MM月dd日  HH时mm分ss秒", 
															"yyyy-MM-dd",
															"yyyy年MM月dd日"
														};
	@Test
	public void testParseDate() throws Exception {
		String dateStr1 = "1972-12-01 12:22:59.999";
		Date date1 = DateUtils.parseDate(dateStr1, parsers);
		System.out.println(date1);
		
		String dateStr2 = "1972年01月12日  12时12分12秒999毫秒";
		Date date2 = DateUtils.parseDate(dateStr2, parsers);
		System.out.println(date2);
		
		String dateStr3 = "1972-12-22 12:12:21";
		Date date3 = DateUtils.parseDate(dateStr3, parsers);
		System.out.println(date3);
	}
}
