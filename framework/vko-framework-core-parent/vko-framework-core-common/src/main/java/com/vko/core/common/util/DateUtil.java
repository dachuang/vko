package com.vko.core.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
	private static String DATE_FORMAT = "yyyy/MM/dd";
	private static String LONG_DATE_FORMAT = "yyyyMMddHHmmss";

	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

	public static final String[] constellationArr = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
			"天蝎座", "射手座", "魔羯座" };

	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };

	/**
	 * Get the previous time, from how many days to now.
	 * 
	 * @param days
	 *            How many days.
	 * @return The new previous time.
	 */
	public static Date previous(int days) {
		return new Date(System.currentTimeMillis() - days * 3600000L * 24L);
	}

	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm:ss".
	 */
	public static String formatDateLong(Date d) {
		if (d == null) {
			return "";
		}
		return new SimpleDateFormat(LONG_DATE_FORMAT).format(d);
	}

	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm".
	 */
	public static String formatDateTime(Date d) {
		if (d == null) {
			return "";
		}
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}

	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm".
	 */
	public static String formatDateTime(long d) {
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}

	/**
	 * Convert date to String like "yyyy-MM-dd".
	 */
	public static String formatDate(Date d) {
		if (d == null) {
			return "";
		}
		return new SimpleDateFormat(DATE_FORMAT).format(d);
	}

	/**
	 * Parse date like "yyyy-MM-dd".
	 */
	public static Date parseDate(String d) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(d);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Parse date and time like "yyyy-MM-dd hh:mm".
	 */
	public static Date parseDateTime(String dt) {
		try {
			return new SimpleDateFormat(DATETIME_FORMAT).parse(dt);
		} catch (Exception e) {
		}
		return null;
	}

	//-----------------获取指定日期的年份，月份，日份，小时，分，秒，毫秒----------------------------

	/**
	 * 获取指定日期的年份
	 * 
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date:   2012-03-07
	 */
	public static int getYearOfDate(java.util.Date p_date) {
		if (null != p_date) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(p_date);
			return c.get(java.util.Calendar.YEAR);
		}
		return -1;
	}

	/**
	 * 获取指定日期的月份
	 * 
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date:   2012-03-07
	 */
	public static int getMonthOfDate(java.util.Date p_date) {
		if (null != p_date) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(p_date);
			return c.get(java.util.Calendar.MONTH) + 1;
		}
		return -1;
	}

	/**
	 * 获取指定日期的日份
	 * 
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date:   2012-03-07
	 */
	public static int getDayOfDate(java.util.Date p_date) {
		if (null != p_date) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(p_date);
			return c.get(java.util.Calendar.DAY_OF_MONTH);
		}
		return -1;
	}

	/**
	 * 获取指定日期的小时
	 * 
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date:   2012-03-07
	 */
	public static int getHourOfDate(java.util.Date p_date) {
		if (null != p_date) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(p_date);
			return c.get(java.util.Calendar.HOUR_OF_DAY);
		}
		return -1;
	}

	/**
	 * 获取指定日期的分钟
	 * 
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date:   2012-03-07
	 */
	public static int getMinuteOfDate(java.util.Date p_date) {
		if (null != p_date) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(p_date);
			return c.get(java.util.Calendar.MINUTE);
		}
		return -1;
	}

	/**
	 * 获取指定日期的秒钟
	 * 
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date:   2012-03-07
	 */
	public static int getSecondOfDate(java.util.Date p_date) {
		if (null != p_date) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(p_date);
			return c.get(java.util.Calendar.SECOND);
		}
		return -1;
	}

	/**
	 * 获取指定日期的毫秒
	 * 
	 * @param p_date
	 * @return
	 * @author longyin
	 * @Date:   2012-03-07
	 */
	public static long getMillisOfDate(java.util.Date p_date) {
		if (null != p_date) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(p_date);
			return c.getTimeInMillis();
		}
		return -1;
	}

	/**
	 * 根据日期获取年龄
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}

		return age;
	}

	/**
	 * Java 毫秒转换为（天：时）方法
	 * 
	 * @param ms
	 * @return
	 */
	public static String formatDayHour(long ms, int validDay) {
		int temp = (int) (ms / (60 * 60 * 1000));
		int day = 0;
		int hour = 0;
		if (temp % 24 == 0) {
			day = (validDay * 24 - temp) / 24;
			hour = 0;
		} else {
			day = (validDay * 24 - temp) / 24;
			if (temp < 24) {
				hour = 24 - temp;
			} else {
				hour = 24 - temp % 24;
			}
		}
		return new StringBuffer().append(day).append("天").append(hour).append("小时").toString();
	}

	/**
	 * 根据日期获取生肖
	 * 
	 * @return
	 */
	public static String date2Zodica(Calendar time) {
		return zodiacArr[time.get(Calendar.YEAR) % 12];
	}

	/**
	 * 根据日期获取星座
	 * 
	 * @param time
	 * @return
	 */
	public static String date2Constellation(Calendar time) {
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}

	/**
	 * 获取当前java.sql.Timestamp
	 * @return 当前java.sql.Timestamp
	 * @see java.util.Date
	 */
	public final static Timestamp nowDateTime() {
		return new Timestamp((new java.util.Date()).getTime());
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.formatDateLong(new Date()));
	}
}
