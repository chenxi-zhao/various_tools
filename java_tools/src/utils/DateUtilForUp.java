package utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtilForUp {
//	public static void main(String[] args) {
//		System.out.println("当前时间：" + getNextMonthEnd().toLocaleString());
//		System.out.println("当天0点时间：" + getCurDateStart().toLocaleString());
//		System.out.println("当天24点时间：" + getCurDateEnd().toLocaleString());
//		System.out.println("昨天0点时间：" + getYesterdayStart().toLocaleString());
//		System.out.println("近7天时间：" + getWeekFromNow().toLocaleString());
//		System.out.println("本周周一0点时间：" + getCurWeekStart().toLocaleString());
//		System.out.println("本周周日24点时间：" + getCurWeekEnd().toLocaleString());
//		System.out.println("本月初0点时间：" + getCurMonthStart().toLocaleString());
//		System.out.println("本月未24点时间：" + getCurMonthEnd().toLocaleString());
//		System.out.println("上月初0点时间：" + getLastMonthStartMorning().toLocaleString());
//		System.out.println("本季度开始点时间：" + getCurrentQuarterStartTime().toLocaleString());
//		System.out.println("本季度结束点时间：" + getCurrentQuarterEndTime().toLocaleString());
//		System.out.println("本年开始点时间：" + getCurrentYearStartTime().toLocaleString());
//		System.out.println("本年结束点时间：" + getCurrentYearEndTime().toLocaleString());
//		System.out.println("上年开始点时间：" + getLastYearStartTime().toLocaleString());
//	}

	// 获得当天0点时间
	public static Date getCurDateStart() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();

	}

	// // 获得当天0点时间
	// public static String getTimesmorningString() {
	// Calendar calendar = Calendar.getInstance();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// String dateStr = sdf.format(calendar.getTime());
	// return dateStr;
	// }

	// 获得昨天0点时间
	public static Date getYesterdayStart() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getCurDateStart().getTime() - 3600 * 24 * 1000);
		return cal.getTime();
	}

	// 获得当天近7天时间
	public static Date getWeekFromNow() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getCurDateStart().getTime() - 3600 * 24 * 1000 * 7);
		return cal.getTime();
	}

	// 获得当天24点时间
	public static Date getCurDateEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// // 获得当天24点时间
	// public static String getTimesnightString() {
	// Calendar cal = Calendar.getInstance();
	// cal.set(Calendar.HOUR_OF_DAY, 24);
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// String dateStr = sdf.format(cal.getTime());
	// return dateStr;
	// }

	// 获得本周一0点时间
	public static Date getCurWeekStart() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	// 获得本周日24点时间
	public static Date getCurWeekEnd() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurWeekStart());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}

	// 获得本月第一天0点时间
	public static Date getCurMonthStart() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// 获得本月最后一天24点时间
	public static Date getCurMonthEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
	}

	// 获得下个月最后一天24点时间
	public static Date getNextMonthEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}

	// "上月初0点时间："
	public static Date getLastMonthStartMorning() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurMonthStart());
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	// 本季度开始点时间
	public static Date getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 *
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentQuarterStartTime());
		cal.add(Calendar.MONTH, 3);
		return cal.getTime();
	}

	// 本年开始点时间
	public static Date getCurrentYearStartTime() {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		cal.clear();
		cal.set(Calendar.YEAR, currentYear);
		return cal.getTime();
	}

	// 本年结束点时间
	public static Date getCurrentYearEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentYearStartTime());
		cal.add(Calendar.YEAR, 1);
		return cal.getTime();
	}

	// 上年开始点时间
	public static Date getLastYearStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentYearStartTime());
		cal.add(Calendar.YEAR, -1);
		return cal.getTime();
	}

	/**
	 * 获取今天所在周的星期一
	 * 
	 * @method name: getMondyOfToday
	 * @return type: String
	 *
	 * @return
	 */
	public static String getMondyOfToday(String format) {
		Calendar c = Calendar.getInstance();
		int step = c.get(Calendar.DAY_OF_WEEK);
		// 星期天
		if (step == 1)
			step = 6;
		else
			step -= 2;

		c.add(Calendar.DAY_OF_YEAR, -step);

		return toString(c.getTime(), format);
	}

	/**
	 * 根据时间类型，返回第一天的日期
	 */
	public static String getStringByTimeType(String timeType) {
		if (null == timeType)
			return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		switch (timeType) {
		case "WEEK":// 周
			return getMondyOfToday("yyyy-MM-dd") + " 00:00:00";
		case "MONTH":// 月
			c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
			return format.format(c.getTime()) + " 00:00:00";
		case "QUARTER":// 季度
			int currentMonth = c.get(Calendar.MONTH) + 1;
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 6);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			return format.format(c.getTime()) + " 00:00:00";
		case "YEAR":// 年
			c.set(Calendar.DAY_OF_YEAR, 1);// 设置为1号,当前日期既为本月第一天
			return format.format(c.getTime()) + " 00:00:00";
		default:
			break;
		}
		return null;
	}

	/**
	 * 格式化时间
	 * 
	 * @method name: toString
	 * @return type: String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toString(Date date, String format) {
		String strTime = null;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
			strTime = simpledateformat.format(date);
		} catch (Exception ex) {
			System.err.println("格式化日期错误 : " + ex.getMessage());
		}
		return strTime;
	}

	/**
	 * <p>
	 * 以现在的时间为基准,获取前num天的日期,返回字符格式yyyy-MM-dd HH:mm:ss
	 * </p>
	 * 
	 * @param num
	 * @return
	 */
	public static String getPreviousDate(int num) {
		String date = null;
		try {
			Date nowTime = DateKit.getNow();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowTime);
			calendar.add(Calendar.DATE, -num); // 日期向前减去num天
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			System.err.println("获取前" + num + "天日期异常!  " + e.getMessage());
		}
		return date;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 *
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 判断是否润年
	 *
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static Date getNextTime(Date date, int delay) {
		try {
			Date d = (Date) date.clone();
			long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断二个时间是否在同一个周
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 *
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num) {
		// 再转换为时间
		Date dd = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 两个时间之间的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 检查是否是日期格式
	 * 
	 * @param args
	 */
	public static boolean RightDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if (date == null)
			return false;
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			sdf.parse(date);
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	/**
	 * 获取现在时间
	 *
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	/**
	 * 获取简单的时间格式 yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String getSimpleDate(Date date) {
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			return formatter.format(date);
		} else {
			return null;
		}
	}

	/**
	 * 获取现在时间
	 *
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 *
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 *
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getStringDateShort(String params) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(params);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 *
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate, String params) {
		SimpleDateFormat formatter = new SimpleDateFormat(params);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}


	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 *
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 *
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		if (strDate == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static Date strToDate(String strDate, String format) {
		if (strDate == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
}
