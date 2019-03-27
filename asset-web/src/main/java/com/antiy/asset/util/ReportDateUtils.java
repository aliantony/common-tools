package com.antiy.asset.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Map;

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
        days.put(0, "星期日");
        LocalDate currentDate = LocalDate.now();
        int weekDay = currentDate.getDayOfWeek().getValue();
        Map<String, String> resultWeek = new HashMap<>();

        // 转换mysql 0 为周天
        if (weekDay == 7) {
            resultWeek.put("0", days.get(0));
        }
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

        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
        int firstWeek = firstday.get(weekFields.weekOfWeekBasedYear());
        int lastWeek = lastDay.get(weekFields.weekOfWeekBasedYear());

        Map<String, String> resultMap = new HashMap<>();

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
        Map<String, String> resultMaps = new HashMap<>();
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

        Map<String, String> resultMaps = new HashMap<>();
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
        Map<String, String> resultMaps = new HashMap<>();
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

    public static void main(String[] args) {
        // System.out.println(getMonthWithDate(1542468106000L,1558468106000L));

        System.out.println(getWeekOfMonth());
    }
}
