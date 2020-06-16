package com.whyrkj.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明: 日期时间工具类
 * @Author zhangsanfeng
 */
public class DateUtil {

    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static final String PATTERN_YM = "yyyy-MM"; // pattern_ym
    public static final int PATTERN_YM_LENGTH = 7;

    public static final String PATTERN_YMD = "yyyy-MM-dd"; // pattern_ymd
    public static final int PATTERN_YMD_LENGTH = 10;

    public static final String PATTERN_YMD_HM = "yyyy-MM-dd HH:mm"; // pattern_ymd hm
    public static final int PATTERN_YMD_HM_LENGTH = 16;

    public static final String PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss"; // pattern_ymd time
    public static final int PATTERN_YMD_HMS_LENGTH = 19;

    public static final String PATTERN_YMD_HMS_S = "yyyy-MM-dd HH:mm:ss:SSS"; // pattern_ymd timeMillisecond
    public static final int PATTERN_YMD_HMS_S_LENGTH = 23;

    /**
     * 返回年份
     *
     * @param date 日期
     *
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 返回 年 月  日
     */
    public static Map<String, Object> getDateMsg(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Map<String, Object> map = new HashMap<>();
        map.put("year", calendar.get(Calendar.YEAR));
        map.put("month", calendar.get(Calendar.MONTH));
        map.put("day", calendar.get(Calendar.DAY_OF_MONTH));
        return map;
    }

    /**
     * 格式化
     *
     * @param date
     * @param pattern
     *
     * @return
     */
    public static String format(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 格式化
     *
     * @param date
     * @param pattern
     * @param timeZone
     *
     * @return
     */
    public static String format(Date date, String pattern, TimeZone timeZone) {
        DateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(timeZone);
        return format.format(date);
    }

    /**
     * 格式化
     *
     * @param date
     * @param parsePattern
     * @param returnPattern
     *
     * @return
     */
    public static String format(String date, String parsePattern, String returnPattern) {
        return format(parse(date, parsePattern), returnPattern);
    }

    /**
     * 格式化
     *
     * @param date
     * @param parsePattern
     * @param returnPattern
     * @param timeZone
     *
     * @return
     */
    public static String format(String date, String parsePattern, String returnPattern, TimeZone timeZone) {
        return format(parse(date, parsePattern), returnPattern, timeZone);
    }

    /**
     * 时间戳转时间
     */
    public static Date timeToDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date.getTime());
        return sdf.parse(str, new ParsePosition(1));
    }

    /**
     * 解析
     *
     * @param date
     * @param pattern
     *
     * @return
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.error("ToolDateTime.parse异常：date值" + date + "，pattern值" + pattern);
            return null;
        }
    }

    /**
     * 解析
     *
     * @param date
     *
     * @return
     */
    public static Date parse(String date) {
        return parse(date, PATTERN_YMD_HMS);
    }

    /**
     * 解析
     *
     * @param date
     *
     * @return
     */
    public static Date parseYearAndDay(String date) {
        return parse(date, PATTERN_YMD);
    }

    /**
     * 两个日期的时间差，返回"X天X小时X分X秒"
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static String getBetween(Date start, Date end) {
        long between = (end.getTime() - start.getTime()) / 1000;// 除以1000是为了转换成秒
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minute = between % 3600 / 60;
        long second = between % 60 / 60;
        String sb = String.valueOf(day) + "天" + hour + "小时" + minute + "分";//second + "秒";
        return sb;
    }

    /**
     * 返回两个日期之间隔了多少秒
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static int getDateSecondSpace(Date start, Date end) {
        return (int) ((end.getTime() - start.getTime()) / (1000));
    }

    /**
     * 返回两个日期之间隔了多少分钟
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static int getDateMinuteSpace(Date start, Date end) {
        return (int) ((end.getTime() - start.getTime()) / (60 * 1000));
    }

    /**
     * 返回两个日期之间隔了多少小时
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static int getDateHourSpace(Date start, Date end) {
        return (int) ((end.getTime() - start.getTime()) / (60 * 60 * 1000));
    }

    /**
     * 返回两个日期之间隔了多少天
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static int getDateDaySpace(Date start, Date end) {
        return (int) ((end.getTime() - start.getTime()) / (60 * 60 * 24 * 1000));
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    //根据日期取得星期几
    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        return weeks[weekIndex];
    }

    /**
     * 返回两个日期之间隔了多少天，包含开始、结束时间
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static List<String> getDaySpaceDate(Date start, Date end) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        List<String> dateList = new LinkedList<>();

        long dayCount = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
        if (dayCount < 0) {
            return dateList;
        }

        dateList.add(format(fromCalendar.getTime(), PATTERN_YMD));

        for (int i = 0; i < dayCount; i++) {
            fromCalendar.add(Calendar.DATE, 1);// 增加一天
            dateList.add(format(fromCalendar.getTime(), PATTERN_YMD));
        }

        return dateList;
    }

    /**
     * 时间按年相加减
     * 正值相加
     * 负值相减
     */
    public static Date calcDateByYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 时间按年天相加减
     * 正值相加
     * 负值相减
     */
    public static Date calcDateByDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 时间按小时相加减
     * 正值相加
     * 负值相减
     */
    public static Date calcDateByHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 时间按分相加减
     * 正值相加
     * 负值相减
     */
    public static Date calcDateByMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 判断时间是否在时间段内
     */
    public static boolean compareDate(Date start, Date end, Date date) {
        return date.after(start) && date.before(end);
    }

    /**
     * 获取开始时间
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static Date startDateByDay(Date start, int end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, end);// 明天1，昨天-1
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取结束时间
     *
     * @param start
     *
     * @return
     */
    public static Date endDateByDay(Date start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取date
     *
     * @param date
     * @return
     */
    public static Date getDateByMonth(String date) {
        return parse(date, PATTERN_YM);
    }


    /**
     * 获取本月月末时间
     *
     * @param start
     * @return
     */
    public static String endDateByMonth(Date start, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyyMMdd";
        }
        SimpleDateFormat dft = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }

    /**
     * 获取开始时间
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static Date startDateByHour(Date start, int end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.MINUTE, end);
        return calendar.getTime();
    }

    /**
     * 获取结束时间
     *
     * @param end
     *
     * @return
     */
    public static Date endDateByHour(Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

//    public static void main(String[] args) {
//        System.out.println(DateUtil.format(DateUtil.calcDateByYear(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateUtil.format(DateUtil.calcDateByDay(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateUtil.format(DateUtil.calcDateByHour(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
//
//        System.out.println(DateUtil.format(DateUtil.calcDateByYear(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateUtil.format(DateUtil.calcDateByDay(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateUtil.format(DateUtil.calcDateByHour(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
//
//        System.out.println(DateUtil.format(DateUtil.startDateByDay(new Date(), 0), "yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateUtil.format(DateUtil.endDateByDay(new Date()), "yyyy-MM-dd HH:mm:ss"));
//
//        System.out.println(DateUtil.getDateDaySpace(DateUtil.parse("2015-01-01", "yyyy-MM-dd"), new Date()));
//
//        System.out.println(DateUtil.getDaySpaceDate(DateUtil.parse("2016-04-01 8:00:00", "yyyy-MM-dd HH:mm:ss"), new Date()));
//
//        System.out.println(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//
//        System.out.println(DateUtil.parse("2015-11-21 12:34:56", "yyyy-MM-dd HH:mm:ss"));
//    }
}
