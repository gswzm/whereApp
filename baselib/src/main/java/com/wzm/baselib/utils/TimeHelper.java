package com.wzm.baselib.utils;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * 描述：日期帮助类
 * 类名： TimeHelper
 * 创作者： wangzm
 * 时间：2019/2/29
 */

@SuppressLint({ "SimpleDateFormat", "UseValueOf" })
@SuppressWarnings("unused")
public class TimeHelper {
	private static String CurrentTime;
	private static String CurrentDate;
	private static String hHmm ="HH:mm";
	private static String yyMon = "yyyy-MM";
	private static String yyMonDay = "yyyy-MM-dd";
	private static String yyMonDayHHmm = "yyyy-MM-dd HH:mm:ss";

	
	public static String getCurrentDateTimeForFormat(String format) {
		Date NowDate = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(NowDate);
	}
	
	/**
	 * 得到当前的年份
	 * 返回格式:yyyy
	 * @return String
	 */
	public static String getCurrentYear() {
		Date NowDate = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(NowDate);
	}

    /**
     * 返回当前时间的日期格式
     * @return
     */
    public static String NowDateFormatToDate() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(yyMonDay);
        return formatter.format(NowDate);
    }

    /**
     * 返回当前时间的时间格式
     * @return
     */
    public static String NowDateFormatToTime() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(NowDate);
    }

	public static String formatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	public static String getWeekDay(String currentdate){
		GregorianCalendar gc=null;
		Calendar calendar= Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(yyMonDay);
		int wday;
		try {
			Date date = formatter.parse(currentdate);
			calendar.setTime(date);
			wday = calendar.get(DAY_OF_WEEK)-1;
			if(wday==0){
				wday=7;
			}
			return Integer.toString(wday);//addDay(currentdate,weekDay-wday);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 得到当前的月份
	 * 返回格式:MM
	 * @return String
	 */
	public static String getCurrentMonth() {
		Date NowDate = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return formatter.format(NowDate);
	}
	/**
	 * 得到当前的日期
	 * 返回格式:dd
	 * @return String
	 */
	public static String getCurrentDay() {
		Date NowDate = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		return formatter.format(NowDate);
	}
	
	/**
	 * 得到当前的时间，精确到毫秒,共19位
	 * 返回格式:yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getCurrentTime() {
		Date NowDate = new Date();
		SimpleDateFormat formatter =new SimpleDateFormat(yyMonDayHHmm);
		CurrentTime = formatter.format(NowDate);
		return CurrentTime;
	}

	/**
	 * 得到当前的日期,共10位
	 * 返回格式：yyyy-MM-dd
	 * @return String
	 */
	public static String getCurrentDate() {
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(yyMonDay);
		CurrentDate = formatter.format(NowDate);
		return CurrentDate;
	}
	/**
	 * 得到当前日期加上某一个整数的日期，整数代表天数
	 * 输入参数：currentdate : String 格式 yyyy-MM-dd
	 * 			add_day		:  int
	 * 返回格式：yyyy-MM-dd
	 */
	public static String addDay(String currentdate, int add_day){
		GregorianCalendar gc=null;
		SimpleDateFormat formatter = new SimpleDateFormat(yyMonDay);
		int year,month,day;
		
		try {
			year= Integer.parseInt(currentdate.substring(0,4));
			month= Integer.parseInt(currentdate.substring(5,7))-1;
			day= Integer.parseInt(currentdate.substring(8,10));
			
			gc=new GregorianCalendar(year,month,day);
			gc.add(DATE,add_day);
		
			return formatter.format(gc.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * getCurrentCompactTimeToMillisecond:(获取当前时间到毫秒数，格式：yyyyMMddHHmmssSSS). <br/> 
	 *
	 * @author Administrator 
	 * @return
	 */
   public static String getCurrentCompactTimeToMillisecond() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        CurrentTime = formatter.format(NowDate);
        return CurrentTime;
    }
	/**
	 * @description 将格式为 yyyy-MM-dd 的日期字符串转换为一个 java.util.Date 对象
	 * @param sDate 格式为 yyyy-MM-dd 的日期字符串
	 * @return java.util.Date 对象
	 * @return Date
	 * @throws
	 */
	public static Date parseDate(String sDate) {
        SimpleDateFormat bartDateFormat =
        new SimpleDateFormat(yyMonDay);

        try {
            Date date = bartDateFormat.parse(sDate);
            return date;
        }
        catch (Exception ex) {
        }
        return null;
    }
	
	/**
	 * @description 将格式为 yyyy-MM-dd hh:mm:ss 的日期字符串转换为一个 java.util.Date 对象
	 * @param sDate 格式为 yyyy-MM-dd hh:mm:ss 的日期字符串
	 * @return java.util.Date 对象
	 * @return Date
	 * @throws
	 */
	public static Date parseDateTime(String sDate) {
        SimpleDateFormat bartDateFormat =
        new SimpleDateFormat(yyMonDayHHmm);

        try {
            Date date = bartDateFormat.parse(sDate);
            return date;
        }
        catch (Exception ex) {
        }
        return null;
    }
	
	/**
	 * @description 将分别为整形的年月日转换为一个Date对象
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return Date对象
	 * @return Date
	 * @throws
	 */
	public static Date parseDate(Integer year, Integer month, Integer day)
	{
		return parseDate(new StringBuffer(10).append(year).append("-").append(month).append("-").append(day).toString());
	}
	
	public static String getWeekDate(String currentdate, int weekDay){
		Calendar calendar= Calendar.getInstance();
		int wday;
		try {
			wday = calendar.get(DAY_OF_WEEK)-1;
			if(wday==0){
				wday = 7;
			}
			return addDay(currentdate,weekDay-wday);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 转换日期格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(String date, String format){
		Date d = TimeHelper.convertToDate(date);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(d);
	}
	
	public static Date convertToDate(String date) {
		SimpleDateFormat formatter =new SimpleDateFormat(yyMonDay);
		try{
			return formatter.parse(date);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date convertDateTime(String format , String dateTimeStr)
	{
		SimpleDateFormat df = new SimpleDateFormat(format);
		try
		{
			Date date = df.parse(dateTimeStr);
			return date;
		}
		catch (Exception e)
		{
			return null;
		}  
	}
	
	public static Date convertToTime(String date) {
		SimpleDateFormat formatter =new SimpleDateFormat(yyMonDayHHmm);
		try{
			return formatter.parse(date);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date convertToTime2(String date) {
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			return formatter.parse(date);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}	
	
	/**
	 * 给定字符串和格式，返回加一个月的串
	 * 输入："1998-11-10"，yyMonDay
	 * 输出："1998-12-10"
	 * @param d
	 * @param format
	 * @return
	 * @author yuzx
	 */
	public static String getStrByaddMonth(String d, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(d);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);
			gc.add(MONTH, 1);
			return sdf.format(gc.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 给定字符串和格式，返回加一个星期的串
	 * 输入："1998-11-10"，yyMonDay
	 * 输出："1998-12-10"
	 * @param d
	 * @param format
	 * @return
	 * @author yuzx
	 */
	public static String getStrByaddWeek(String d, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(d);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);
			gc.add(Calendar.WEEK_OF_YEAR, 1);
			return sdf.format(gc.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//"yy-MM-dd"
	public static int compareToDate(String src, String obj, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		GregorianCalendar srcStartGc = new GregorianCalendar();
		GregorianCalendar srcEndGc = new GregorianCalendar();
		srcStartGc.setTime(sdf.parse(src));
		srcEndGc.setTime(sdf.parse(obj));
		return srcStartGc.compareTo(srcEndGc);
	}
	
	/**
	 * 计算2个日期相差的月数
	 * @param data1
	 * @param data2
	 * @return
	 * zby
	 */
	public static int getMonth(String data1, String data2){
		int result=0;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(yyMonDay);
			Date startdate = sdf.parse(data1);
			Date enddate = sdf.parse(data2);
			
			Calendar start = Calendar.getInstance();
			start.setTime(startdate); 
			Calendar end = Calendar.getInstance();
			end.setTime(enddate); 
			Calendar temp= Calendar.getInstance();
			temp.setTime(enddate); 
			temp.add(DATE,1);

			int y=end.get(YEAR)-start.get(YEAR);
			int m=end.get(MONTH)-start.get(MONTH);
			
			result= y*12+m+1; 
			System.err.println("sssss="+start.get(DATE));//
//			if((start.get(Calendar.DATE)==1) && (temp.get(Calendar.DATE)==1)){//前后都不破月 
//				result= y*12+m+1; 
//			} 
//			else if((start.get(Calendar.DATE)!=1) && (temp.get(Calendar.DATE)==1)){//前破月后不破月 
//				result= y*12+m; 
//			} 
//			else if((start.get(Calendar.DATE)==1) && (temp.get(Calendar.DATE)!=1)){//前不破月后破月 
//				result= y*12+m; 
//			} 
//			else {//前破月后破月 	
//		     result= (y*12+m-1)<0?0:(y*12+m-1); 
//			}
			
			SimpleDateFormat sdf1 = new SimpleDateFormat(yyMon);
			Date date = sdf.parse(data1);
			
			String[] res = new String[result];
			res[0] = sdf1.format(date);
			int i=1;
			while(i<result){
				res[i] = TimeHelper.getStrByaddMonth(res[i-1],yyMon);
				i++;
			}
			for(int j=0;j<res.length;j++){//具体月
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return result;
		} 
	
	/**
	 * @description 获取昨天的日期 日期格式 yyyy-MM-dd
	 * @return yyyy-MM-dd
	 * @return String
	 * 郭磊
	 */
	public static String getYesterdayDate()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(DATE, -1);
		String begin;
		begin = new Date(calendar.getTime().getTime()).toString();
		return begin;
	}
	/**
	 * 获取本周周一日期
	 * @return yyyy-MM-dd
	 * @return
	 * @author zhaowt
	 */
	public static String getMondayOFWeek(){
		int mondayPlus = getMondayPlus();    
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(DATE, mondayPlus);
		Date monday = currentDate.getTime();
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat(yyMonDay);
		return simpleDateFormat.format(monday);   
		
	}
	/**
	 * 获取本周日日期
	 * @return yyyy-MM-dd
	 * @return
	 * @author zhaowt
	 */
	public static String getSundayOFWeek(){
		int mondayPlus = getMondayPlus(); 
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(DATE, mondayPlus+6);
		Date Sunday = currentDate.getTime();
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat(yyMonDay);
		return simpleDateFormat.format(Sunday);  
	}
   /**
    *  获得当前日期与本周日相差的天数   
    * 描述这个方法的作用)
    * @author zhaowt
    * @return
    */
    private static int getMondayPlus() {   
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......   
        int dayOfWeek = cd.get(DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1
         if (dayOfWeek == 1) {   
            return 0;   
        } else {   
            return 1 - dayOfWeek;   
        }   
    }  
	/**
	 * @description 获取前N周的起始日期 日期格式 yyyy-MM-dd
	 * @param lastNum 前N周 ,0为当前周,1为上周,2为上2周,依次类推
	 * @return yyyy-MM-dd
	 * 郭磊
	 */
	public static String getLastWeekStartDateByNum(int lastNum)
	{
		int num = 7 * ((lastNum < 0 ? 0 : lastNum) - 1);
		GregorianCalendar calendar = new GregorianCalendar();
		int minus = calendar.get(DAY_OF_WEEK) + 1;
		calendar.add(DATE, -minus - num);
		calendar.add(DATE, -4);
		String begin;
		begin = new java.sql.Date(calendar.getTime().getTime()).toString();
		return begin;
	}
	
	/**
	 * @description 获取本月的起始日期 日期格式 yyyy-MM-dd 
	 * @return yyyy-MM-dd
	 * 郭磊
	 */
	public static String getThisMonthStartDate()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		int dayOfMonth = calendar.get(DATE);
		calendar.add(DATE, -dayOfMonth + 1);
		String begin;
		begin = new java.sql.Date(calendar.getTime().getTime()).toString();
		return begin;
	}
	
	/**
	 * @description 获取上月的起始日期 日期格式 yyyy-MM-dd 
	 * @return yyyy-MM-dd
	 * 郭磊
	 */
	public static String getLastMonthStartDate()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(calendar.get(YEAR), calendar
				.get(MONTH), 1);
		calendar.add(DATE, -1);
		int month = calendar.get(MONTH) + 1;
		String begin;
		begin = calendar.get(YEAR) + "-" + (month < 10 ? "0"+month : month) + "-01";
		return begin;
	}
	
	/**
	 * @description 判断某日期是否在其它两个日期之间
	 * @param objDate 目标日期
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return 是否在2个日期之间
	 * @return boolean
	 * @throws
	 */	
	public static boolean isBetweenDate(Date objDate, Date startDate, Date endDate)
	{
		return TimeHelper.isBetweenDate(objDate, startDate.getTime(), endDate.getTime());
	}
	
	/**
	 * @description 判断某日期是否在其它两个日期之间
	 * @param objDate 目标日期
	 * @param startDateTime 起始日期到今天毫秒数
	 * @param endDateTime 结束日期到今天毫秒数
	 * @return 是否在2个日期之间
	 * @return boolean
	 * @throws
	 */
	public static boolean isBetweenDate(Date objDate, long startDateTime, long endDateTime)
	{
		long objDateTime = objDate.getTime();
		return objDateTime >= startDateTime && objDateTime <= endDateTime ;
	}	
	
	public static long getCurrentLongTime(){
		return new Date().getTime();
		
	}
	
	public static String getDateFormatForString(long time){
		SimpleDateFormat sdf = new SimpleDateFormat(yyMonDayHHmm);
		Date date = new Date(time);
		return sdf.format(date);
	}
	
	public static String getDateFormatForString(long time, String formate){
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		Date date = new Date(time);
		return sdf.format(date);
	} 
	
	
	/**
	 * 返回指定年、月、日、小时、分钟、秒所代表的时间值，以毫秒为单位
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hour 小时
	 * @param minute 分钟
	 * @param second 妙
	 * @return long 自1970年1月1日 00:00:00 GMT 以来此日期表示的毫秒数
	 * 苗康
	 */
	public static long getTime(int year, int month, int day, int hour, int minute, int second) {
		Calendar cd = Calendar.getInstance();
		cd.clear();
		cd.set(YEAR, year);
		cd.set(MONTH, month);
		cd.set(Calendar.DAY_OF_MONTH, day);
		cd.set(Calendar.HOUR_OF_DAY, hour);
		cd.set(Calendar.MINUTE, minute);
		cd.set(Calendar.SECOND, second);
		return cd.getTime().getTime();
	}
	
	/**
	 * 返回指定日期的开始时间，通常为 时间段为： 00:00:00
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 指定时间，以从历元至现在所经过的 UTC 毫秒数形式。
	 * 苗康
	 */
	public static long getDayBeginTime(int year, int month, int day) {
		return TimeHelper.getTime(year, month > 0 ? month - 1 : 0, day, 0, 0, 0);
	}

	/**
	 * 返回指定日期的最后时间，通常为 时间段为： 23:59:59
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 指定时间，以从历元至现在所经过的 UTC 毫秒数形式。
	 * 苗康
	 */
	public static long getDayEndTime(int year, int month, int day) {
		return TimeHelper.getTime(year, month > 0 ? month - 1 : 0, day, 23, 59, 59);
	}
	
	/**
	 * @description 获取某年某月的天数
	 * @param year 年
	 * @param month 月
	 * @return void
	 * @throws 获取失败
	 */
	public static Integer getMaxDayByYearMonth(Integer year, Integer month)
	{
		GregorianCalendar date = new GregorianCalendar(year,month - 1, 1);
        int number;
		number = date.getActualMaximum(DATE);
		return number;
	}
	
	/**
	 * @description 根据传入的日期，获取当天是该星期的第几天
	 * @param pTime 日期
	 * @return
	 * @throws Exception 获取失败
	 */
	public  static  int  getDayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(yyMonDay );
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));   
		int  dayForWeek = 0 ;   
		if (c.get(DAY_OF_WEEK) == 1 ){
		dayForWeek = 7 ;   
		}else {   
		dayForWeek = c.get(DAY_OF_WEEK) - 1 ;
		}   
		return  dayForWeek;   
	}
	/**
	 * 判断时间是昨天的方法
	 * @param sdate
	 * @return
	 */
	public static String getDTFormat(String sdate){
		String format = "";
		try {
			Date current = new Date();
			long microCurrent = current.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = sdf.parse(sdate);
			long microDate = date.getTime();
			long secPre = 60*60*1000L;//X分钟前
			long hourPre = 6*60*60*1000L;//6小时前
			String currentYear = new SimpleDateFormat("yyyy").format(current);//取得当前年
			String juageYear = new SimpleDateFormat("yyyy").format(date);
			String currentDay = getPreDay(0);//取得当前天
			String yesterdayPre = getPreDay(-1);//获取昨天
			String beforeYesterdayPre = getPreDay(-2);//获取前天
			long difference = microCurrent - microDate;
			if(difference<secPre){
				long sec = difference/(60*1000);
				return sec==0L?"刚刚":sec+"分钟前";
			}
			else if(difference<hourPre){
				long sec = difference/(60*60*1000);
				return sec == 0L?"1":sec+"小时前";
			}
			else if(sdate.substring(0,10).equals(currentDay)){
				return new SimpleDateFormat(hHmm).format(date);
			}
			else if(sdate.substring(0, 10).equals(yesterdayPre)){
				return "昨天 "+new SimpleDateFormat(hHmm).format(date);
			}
			else if(sdate.substring(0, 10).equals(beforeYesterdayPre)){
				return "前天"+new SimpleDateFormat(hHmm).format(date);
			}
			else if(currentYear.equals(juageYear)){
				return new SimpleDateFormat("M月dd日 HH:mm").format(date);
			}
			else{
				return new SimpleDateFormat("yyyy-M-dd").format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format;
	}
	/**取到前一天的数据*/
	public static String getPreDay(int day){
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(DATE, day);    //得到前一天
        String yestedayDate  = new SimpleDateFormat(yyMonDay).format(calendar.getTime());
        return yestedayDate;
	}


    /**
     * 计算两个时间相差的天数
     * @param dateStr1      ----开始时间
     * @param dateStr2      ----结束时间
     * @return
     */
    public static int getDay(String dateStr1, String dateStr2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat(yyMonDay);
        try {
            dateStr1 = sdf.format(sdf2.parse(dateStr1));
            dateStr2 = sdf.format(sdf2.parse(dateStr2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year1 = Integer.parseInt(dateStr1.substring(0, 4));
        int month1 = Integer.parseInt(dateStr1.substring(4, 6));
        int day1 = Integer.parseInt(dateStr1.substring(6, 8));
        int year2 = Integer.parseInt(dateStr2.substring(0, 4));
        int month2 = Integer.parseInt(dateStr2.substring(4, 6));
        int day2 = Integer.parseInt(dateStr2.substring(6, 8));
        Calendar c1 = Calendar.getInstance();
        c1.set(YEAR, year1);
        c1.set(MONTH, month1 - 1);
        c1.set(Calendar.DAY_OF_MONTH, day1);
        Calendar c2 = Calendar.getInstance();
        c2.set(YEAR, year2);
        c2.set(MONTH, month2 - 1);
        c2.set(Calendar.DAY_OF_MONTH, day2);
        long mills =
                c1.getTimeInMillis() > c2.getTimeInMillis()
                        ? c1.getTimeInMillis() - c2.getTimeInMillis()
                        : c2.getTimeInMillis() - c1.getTimeInMillis();
        return (int) (mills / 1000 / 3600 / 24);
    }

	/**
	 * 比较两个时间的大小
	 * @param dateStr1      ----开始时间
	 * @param dateStr2      ----结束时间
	 * @return
	 */
	public static int compareDay(String dateStr1, String dateStr2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat(yyMonDay);
		try {
			dateStr1 = sdf.format(sdf2.parse(dateStr1));
			dateStr2 = sdf.format(sdf2.parse(dateStr2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int year1 = Integer.parseInt(dateStr1.substring(0, 4));
		int month1 = Integer.parseInt(dateStr1.substring(4, 6));
		int day1 = Integer.parseInt(dateStr1.substring(6, 8));
		int year2 = Integer.parseInt(dateStr2.substring(0, 4));
		int month2 = Integer.parseInt(dateStr2.substring(4, 6));
		int day2 = Integer.parseInt(dateStr2.substring(6, 8));
		Calendar c1 = Calendar.getInstance();
		c1.set(YEAR, year1);
		c1.set(MONTH, month1 - 1);
		c1.set(Calendar.DAY_OF_MONTH, day1);
		Calendar c2 = Calendar.getInstance();
		c2.set(YEAR, year2);
		c2.set(MONTH, month2 - 1);
		c2.set(Calendar.DAY_OF_MONTH, day2);
		long mills = c1.getTimeInMillis() - c2.getTimeInMillis();
		return (int) (mills / 1000 / 3600 / 24);
	}
	/**
	 * 计算两个日期间相差天数
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 * @author zhongxl
	 */
	public static int getDaysBetween(String startTime, String endTime) throws Exception {
		long dateBetween;

		SimpleDateFormat sdf = new SimpleDateFormat(yyMonDay);

		Date date1 = sdf.parse(startTime);
		Date date2 = sdf.parse(endTime);

		GregorianCalendar cal1 = new GregorianCalendar();
		GregorianCalendar cal2 = new GregorianCalendar();

		cal1.setTime(date1);
		cal2.setTime(date2);

		dateBetween = (cal2.getTimeInMillis() - cal1.getTimeInMillis())
				/ (1000 * 3600 * 24);// 从间隔毫秒变成间隔天数</strong>
		return (int)dateBetween+1;
	}
}
