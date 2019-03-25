package com.antiy.asset.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangbing
 * @date: 2019/3/25 16:43
 * @description:
 */
public class ReportDateUtils {

    /**
     * 获取当前时间的周时间
     * @return Map.key 为数据库返回的星期几，value返回前端数据格式
     */
    public static Map<String, String> getDayOfWeek() {
        Map<Integer, String> days = new HashMap<>();
        days.put(1, "星期一");
        days.put(2, "星期二");
        days.put(3, "星期三");
        days.put(4, "星期四");
        days.put(5, "星期五");
        days.put(6, "星期六");
        days.put(7, "星期日");
        LocalDate currentDate = LocalDate.now();
        int weekDay = currentDate.getDayOfWeek().getValue();
        Map<String, String> resultWeek = new HashMap<>();
        for (int i = 1; i <= weekDay; i++) {
            if (null != days.get(i)) {
                resultWeek.put(i + "", days.get(i));
            }
        }
        return resultWeek;
    }

    /**
     * 获取本月天数信息
     * @return
     */
    public static Map<String, String> getCurrentDayOfMonth() {
        Map<String, String> resultMaps = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        resultMaps.put(localDate.toString(), localDate.toString());
        for (int i = 1; i < localDate.getDayOfMonth(); i++) {
            LocalDate localDateTemp = localDate.withDayOfMonth(i);
            resultMaps.put(localDateTemp.toString(), localDateTemp.toString());
            localDateTemp = null;
        }
        return resultMaps;
    }

    /**
     * 获取本年的月份信息,如果当月直接返回当前时间
     * @return
     */
    public static Map<String, String> getCurrentMonthOfYear() {
        Map<String, String> resultMaps = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate localDate = LocalDate.now();
        resultMaps.put(localDate.format(dateTimeFormatter), localDate.format(dateTimeFormatter));
        for (int i = 1; i < localDate.getMonthValue(); i++) {
            LocalDate localDateTemp = localDate.withMonth(i);
            resultMaps.put(localDateTemp.format(dateTimeFormatter), localDateTemp.format(dateTimeFormatter));
            localDateTemp = null;
        }
        return resultMaps;
    }

    /**
     * 获取季度信息
     * @return
     */
    public static Map<String, String> getSeason() {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentMonthOfYear());
    }
}
