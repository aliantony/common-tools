package com.antiy.asset.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * @author: zhangbing
 * @date: 2019/3/25 16:43
 * @description:
 */
public class ReportDateUtils {

    static Map<Integer, String> monthsMap = new HashMap<>();

    static {
        monthsMap.put(1, "一月");
        monthsMap.put(2, "二月");
        monthsMap.put(3, "三月");
        monthsMap.put(4, "四月");
        monthsMap.put(5, "五月");
        monthsMap.put(6, "六月");
        monthsMap.put(7, "七月");
        monthsMap.put(8, "八月");
        monthsMap.put(9, "九月");
        monthsMap.put(10, "十月");
        monthsMap.put(11, "十一月");
        monthsMap.put(12, "十二月");
    }

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
        TreeMap<String, String> resultWeek = new TreeMap<>();

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
        TreeMap<String, String> resultMaps = new TreeMap<>();
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
        LocalDate firstday = today.with(TemporalAdjusters.firstDayOfMonth());

        // 获取本月最后一天
        LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());

        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
        int firstWeek = firstday.get(weekFields.weekOfYear());
        int lastWeek = lastDay.get(weekFields.weekOfYear());
        TreeMap<String, String> resultMap = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return DataTypeUtils.stringToInteger(a) - DataTypeUtils.stringToInteger(b);
            }
        });

        Map<Integer, String> weeksMap = new HashMap<>();
        weeksMap.put(1, "第一周");
        weeksMap.put(2, "第二周");
        weeksMap.put(3, "第三周");
        weeksMap.put(4, "第四周");
        weeksMap.put(5, "第五周");
        weeksMap.put(6, "第六周");
        int weekCount = lastWeek - firstWeek + 1;
        for (int i = 1; i <= weekCount; i++) {
            // 由于java周是1到53，mysql是0到52，所以此处-2
            resultMap.put((firstWeek + i - 2) + "", weeksMap.get(i));
        }
        return resultMap;
    }

    /**
     * 获取本年的月份信息,如果当月直接返回当前时间
     * @return
     */
    public static Map<String, String> getCurrentMonthOfYear() {
        TreeMap<String, String> resultMaps = new TreeMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate localDate = LocalDate.now();
        for (int i = 1; i <= localDate.getMonthValue(); i++) {
            LocalDate localDateTemp = localDate.withMonth(i);
            resultMaps.put(localDateTemp.format(dateTimeFormatter), monthsMap.get(i));

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

        ParamterExceptionUtils.isTrue(months < 12, "月份不能超过12个月");

        TreeMap<String, String> resultMaps = new TreeMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        resultMaps.put(startDate.format(dateTimeFormatter), startDate.format(dateTimeFormatter));
        for (int i = 1; i <= months; i++) {
            startDate = startDate.plusMonths(1);
            resultMaps.put(startDate.format(dateTimeFormatter), startDate.format(dateTimeFormatter));
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
        // 获取当前的月份
        int month = localDate.getMonth().getValue();
        TreeMap<String, String> resultMaps = new TreeMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        if (month <= 3) {
            for (int i = 1; i <= 3; i++) {
                LocalDate localDateTemp = localDate.withMonth(i);
                resultMaps.put(localDateTemp.format(dateTimeFormatter), monthsMap.get(i));

                // help gc
                localDateTemp = null;
            }
        } else if (month > 3 && month <= 6) {
            for (int i = 4; i <= 6; i++) {
                LocalDate localDateTemp = localDate.withMonth(i);
                resultMaps.put(localDateTemp.format(dateTimeFormatter), monthsMap.get(i));

                // help gc
                localDateTemp = null;
            }
        } else if (month > 6 && month <= 9) {
            for (int i = 7; i <= 9; i++) {
                LocalDate localDateTemp = localDate.withMonth(i);
                resultMaps.put(localDateTemp.format(dateTimeFormatter), monthsMap.get(i));

                // help gc
                localDateTemp = null;
            }
        } else if (month > 9 && month <= 12) {
            for (int i = 10; i <= 12; i++) {
                LocalDate localDateTemp = localDate.withMonth(i);
                resultMaps.put(localDateTemp.format(dateTimeFormatter), monthsMap.get(i));

                // help gc
                localDateTemp = null;
            }
        }

        return resultMaps;
    }

    public static Map<String, String> getDate(Integer type, Long startTime, Long endTime) {
        switch (type) {
            case 1:
                return getDayOfWeek();
            case 2:
                return getWeekOfMonth();
            case 3:
                return getSeason();
            case 4:
                return getCurrentMonthOfYear();
            case 5:
                return getMonthWithDate(startTime, endTime);
        }
        return null;
    }

    /**
     * 获取数据库时间格式
     * @return
     */
    public static String getTimeType(Integer type) {
        switch (type) {
            // 本周
            case 1:
                return "%w";
            // 本月
            case 2:
                return "%u";
            // 本季度
            case 3:
                return "%Y-%m";
            // 本年
            case 4:
                return "%Y-%m";
            // 时间范围
            case 5:
                return "%Y-%m";
        }
        return "";
    }

    public static void main(String[] args) {
        // System.out.println(getMonthWithDate(1542468106000L,1558468106000L));

        // System.out.println(getDayOfWeek());
        // for (Map.Entry<String, String> entry : getDayOfWeek().entrySet()) {
        // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        // }
        System.out.println(getWeekOfMonth());
        // for (Map.Entry<String, String> entry : getWeekOfMonth().entrySet()) {
        // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        // }
        // System.out.println(getSeason());
        // for (Map.Entry<String, String> entry : getSeason().entrySet()) {
        // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        // }
        // System.out.println(getCurrentMonthOfYear());
        // for (Map.Entry<String, String> entry : getCurrentMonthOfYear().entrySet()) {
        // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        // }
        // System.out.println(getMonthWithDate(1527177600000L,1553443200000L));
        // for (Map.Entry<String, String> entry : getMonthWithDate(1527177600000L,1553443200000L).entrySet()) {
        // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        // }
        // System.out.println(getWeekOfMonth());
    }
}
