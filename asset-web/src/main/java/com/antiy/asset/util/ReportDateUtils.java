package com.antiy.asset.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

import com.antiy.common.utils.ParamterExceptionUtils;

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

            // help gc
            localDateTemp = null;
        }
        return resultMaps;
    }

    /**
     * 获取当月有多少周
     * @return
     */
    public static Map<String, String> getWeekOfMonth() {
        LocalDate today = LocalDate.now();

        // 获取本月第一天
        LocalDate firstday = LocalDate.of(today.getYear(), today.getMonth(), 1);

        // 获取本月最后一天
        LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());

        // 获取当前月一共多少天
        int days = lastDay.getDayOfMonth() - firstday.getDayOfMonth() + 1;
        int weeks = (int) Math.ceil(days / 7.0);
        Map<String, String> resultMap = new HashMap<>();

        Map<Integer, String> weeksMap = new HashMap<>();
        weeksMap.put(1, "第一周");
        weeksMap.put(2, "第二周");
        weeksMap.put(3, "第三周");
        weeksMap.put(4, "第四周");
        weeksMap.put(5, "第五周");
        for (int i = 1; i <= weeks; i++) {
            resultMap.put(weeksMap.get(i), weeksMap.get(i));
        }
        return resultMap;
    }

    /**
     * 获取本年的月份信息,如果当月直接返回当前时间
     * @return
     */
    public static Map<String, String> getCurrentMonthOfYear() {
        Map<String, String> resultMaps = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate localDate = LocalDate.now();
        Map<Integer, String> weeksMap = new HashMap<>();
        weeksMap.put(1, "一月");
        weeksMap.put(2, "二月");
        weeksMap.put(3, "三月");
        weeksMap.put(4, "四月");
        weeksMap.put(5, "五月");
        weeksMap.put(6, "六月");
        weeksMap.put(7, "七月");
        weeksMap.put(8, "八月");
        weeksMap.put(9, "九月");
        weeksMap.put(10, "十月");
        weeksMap.put(11, "十一月");
        weeksMap.put(12, "十二月");
        for (int i = 1; i <= localDate.getMonthValue(); i++) {
            LocalDate localDateTemp = localDate.withMonth(i);
            resultMaps.put(localDateTemp.format(dateTimeFormatter), weeksMap.get(i));

            // help gc
            localDateTemp = null;
        }
        return resultMaps;
    }

    /**
     * 获取当前时间区间内的月份信息
     * @param startTime
     * @param endTime
     * @return
     */
    public static Map<String, String> getMonthWithDate(Long startTime, Long endTime) {

        ParamterExceptionUtils.isTrue(endTime > startTime, "结束时间必须大于开始时间");
        LocalDate startDate = getDateTimeOfTimestamp(startTime).toLocalDate();
        LocalDate endDate = getDateTimeOfTimestamp(endTime).toLocalDate();

        // 获取时间差月份
        Long months = startDate.until(endDate, ChronoUnit.MONTHS);

        Map<String, String> resultMaps = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        resultMaps.put(startDate.format(dateTimeFormatter), startDate.format(dateTimeFormatter));
        for (int i = 1; i <= months; i++) {
            LocalDate localDateTemp = startDate.withMonth(i);
            resultMaps.put(localDateTemp.format(dateTimeFormatter), localDateTemp.format(dateTimeFormatter));

            // help gc
            localDateTemp = null;
        }
        return resultMaps;
    }

    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 获取季度信息
     * @return
     */
    public static Map<String, String> getSeason() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getMonth().getValue());
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentMonthOfYear());
    }
}
