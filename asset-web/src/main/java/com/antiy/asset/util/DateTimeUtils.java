package com.antiy.asset.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * @Author: lvliang
 * @Date: 2019/11/9 15:04
 */
public class DateTimeUtils {
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past + 1);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
    public static String getPastHour(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - past + 1);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        String result = format.format(today);
        return result;
    }
    public static Long getPastTimeStamp(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past + 1);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format1.parse(result + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static Long getPastDay(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past + 1);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        return Long.parseLong(result);
    }
    public static Long getPastDayOfHour(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past + 1);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        String result = format.format(today);
        return Long.parseLong(result);
    }
    public static Long getNowfHour() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        String result = format.format(today);
        return Long.parseLong(result);
    }

    /**
     * 日期字符串转时间戳
     * @param day
     * @return
     */
    public static Long getTimeStampByStr(String day) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format1.parse(day + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取过去几天的日期
     *
     * @param days
     * @return
     */
    public static ArrayList<String> getPastNumDate(int days) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            pastDaysList.add(getPastDate(i));
        }
        Collections.reverse(pastDaysList);
        return pastDaysList;
    }

    public static ArrayList<String> getFullHours() {
        ArrayList<String> hours = new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            hours.add(String.format("%02d", i));
        }
        return hours;
    }

    public static String getCurrentTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(timeFormatter);
    }

    /**
     * 毫秒转时分秒
     * @param mills
     * @return
     */
    public static String convertTimeToStr(long mills) {
        StringBuilder sb = new StringBuilder();
        long h = 60 * 60 * 1000;
        long m = 60 * 1000;
        long s = 1000;
        if (mills / h > 0) {
            sb.append(mills / h).append("小时");
        }
        mills %= h;

        if (mills / m > 0) {
            sb.append(mills / m).append("分");
        }
        mills %= m;
        if (mills / s > 0) {
            sb.append(mills / s).append("秒");
        }
        mills %= s;
        sb.append(mills).append("毫秒");
        return sb.toString();
    }

    public static long getZeroTimeByTimeStamp(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(time));
        return getTimeStampByStr(sd);
    }

    public static boolean isMillisecond(long timeStamp) {
        return (timeStamp + "").length() > 10;
    }

    public static Long getLastSeconds(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format1.parse(sdf.format(time) + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
    public static void main(String[] args) {
        System.out.println(getLastSeconds(1590910323544L));
    }
}
