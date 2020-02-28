package com.antiy.asset.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.antiy.common.utils.DateUtils;

/**
 * @author zhangyajun
 * @create 2019-03-08 22:21
 **/
public class TimeUtil {
    public static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    /**
     * 获取当前时间之前或之后几小时 hour
     * @param hour
     * @return
     */
    public static Long getTimeByHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        int month = calendar.get(Calendar.MONTH) + 1;
        String yearMonDay = calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.get(Calendar.DATE);
        System.out.println(yearMonDay);
        String hourStr = calendar.get(Calendar.HOUR_OF_DAY) + ":" + "00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.WHOLE_FORMAT);
        try {
            return simpleDateFormat.parse(yearMonDay + " " + hourStr).getTime();
        } catch (ParseException e) {
            logger.error("getTimeByHour方法调用失败");
            return calendar.getTime().getTime();
        }

    }

    /**
     * 获取当前时间之前或之后几分钟 minute
     *
     * @param minute
     * @return
     */
    public static Long getTimeByMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -minute);
        int month = calendar.get(Calendar.MONTH) + 1;
        String yearMonDay = calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.get(Calendar.DATE);
        String hourMinute = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.WHOLE_FORMAT);
        try {
            return simpleDateFormat.parse(yearMonDay + " " + hourMinute).getTime();
        } catch (ParseException e) {
            logger.error("getTimeByMinute方法调用失败");
            return calendar.getTime().getTime();
        }
    }

    /**
     * 得到几天后/几周后的时间
     * @param day
     * @return
     */
    public static Long getTimeByDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
        int month = calendar.get(Calendar.MONTH) + 1;
        String yearMonDay = calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.get(Calendar.DATE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.NO_TIME_FORMAT);
        try {
            return simpleDateFormat.parse(yearMonDay).getTime();
        } catch (ParseException e) {
            logger.error("getTimeByDate方法调用失败");
            return calendar.getTime().getTime();
        }
    }

    /**
     * 获取前/后半年的开始时间
     * @return
     */
    public static Long getHalfYearAgo() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.toString());
        c.add(Calendar.MONTH, -5);
        int oldMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.NO_TIME_FORMAT);
        try {
            return simpleDateFormat.parse(c.get(Calendar.YEAR) + "-" + oldMonth + "-00").getTime();
        } catch (ParseException e) {
            logger.error("getHalfYearAgo方法调用失败");
            return c.getTime().getTime();
        }
    }

    // https://blog.csdn.net/zhangzhilai8/article/details/47324141
    public static String timeStampToDate(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    public static int getYearByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String year = date.substring(0, 4);
        return Integer.parseInt(year);
    }

    public static int getMonthByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String month = date.substring(5, 7);
        return Integer.parseInt(month);
    }

    public static int getDayByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String day = date.substring(8, 10);
        return Integer.parseInt(day);
    }

    public static int getHourByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String hour = date.substring(11, 13);
        return Integer.parseInt(hour);
    }

    public static int getMinuteByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String minute = date.substring(14, 16);
        return Integer.parseInt(minute);
    }

    public static int getSecondByTimeStamp(long timeStamp) {
        String date = timeStampToDate(timeStamp);
        String second = date.substring(17, 19);
        return Integer.parseInt(second);
    }

    public static Long getNoSecondTime(Long timeStamp) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String year = String.valueOf(TimeUtil.getYearByTimeStamp(timeStamp));
        String month = String.valueOf(TimeUtil.getMonthByTimeStamp(timeStamp));
        String day = String.valueOf(TimeUtil.getDayByTimeStamp(timeStamp));
        String hour = String.valueOf(TimeUtil.getHourByTimeStamp(timeStamp));
        String minute = String.valueOf(TimeUtil.getMinuteByTimeStamp(timeStamp));
        System.out.println(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00");
        String noSecondTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
        return simpleDateFormat.parse(noSecondTime).getTime();
    }

    public static Long getUnixTime(Long timeStamp) throws Exception {
        return TimeUtil.getNoSecondTime(timeStamp) / 1000;
    }

    // public static void main(String[] args) throws Exception {
    // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    // Long currentTime = System.currentTimeMillis();
    // String year = String.valueOf(TimeUtil.getYearByTimeStamp(currentTime));
    // String month = String.valueOf(TimeUtil.getMonthByTimeStamp(currentTime));
    // String day = String.valueOf(TimeUtil.getDayByTimeStamp(currentTime));
    // String hour = String.valueOf(TimeUtil.getHourByTimeStamp(currentTime));
    // String minute = String.valueOf(TimeUtil.getMinuteByTimeStamp(currentTime));
    // System.out.println(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00");
    // String noSecondTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
    // try {
    // System.out.println(simpleDateFormat.parse(noSecondTime).getTime());
    // } catch (ParseException e) {
    // e.printStackTrace();
    // }
    // List<Long> timeScaleList = new ArrayList<>();
    // 当前时间
    // timeScaleList.add(TimeUtil.getNoSecondTime());
    // for (int i = 1; i < 60; i++) {
    // timeScaleList.add(TimeUtil.getTimeByMinute(i) / 1000);
    // }
    // System.out.println(StringUtils.trim(timeScaleList.toString(), "[", "]"));
    // }
}
