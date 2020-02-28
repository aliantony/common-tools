package com.antiy.asset.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author zhangyajun
 * @create 2020-02-28 15:21
 **/
public class TwelveTimeUtil {
    private final static String ZERO                   = "00";
    private final static String TWO                    = "02";
    private final static String FOUR                   = "04";
    private final static String SIX                    = "06";
    private final static String EIGHT                  = "08";
    private final static String TEN                    = "10";
    private final static String TWELVE                 = "12";
    private final static String FOURTEEN               = "14";
    private final static String SIXTEEN                = "16";
    private final static String EIGHTEEN               = "18";
    private final static String TWENTY                 = "20";
    private final static String TWENTY_TWO             = "22";
    private final static String TWENTY_FOUR            = "24";
    private final static String ZERO_TWO               = "ZERO_TWO";
    private final static String TWENTY_TWO_YESTERDAY   = "TWENTY_TWO_YESTERDAY";
    private final static String TWO_FOUR               = "TWO_FOUR";
    private final static String FOUR_SIX               = "FOUR_SIX";
    private final static String SIX_EIGHT              = "SIX_EIGHT";
    private final static String EIGHT_TEN              = "EIGHT_TEN";
    private final static String TEN_TWELVE             = "TEN_TWELVE";
    private final static String TWELVE_FOURTEEN        = "TWELVE_FOURTEEN";
    private final static String FOURTEEN_SIXTEEN       = "FOURTEEN_SIXTEEN";
    private final static String SIXTEEN_EIGHTEEN       = "SIXTEEN_EIGHTEEN";
    private final static String EIGHTEEN_TWENTY        = "EIGHTEEN_TWENTY";
    private final static String TWENTY_TWENTY_TWO      = "TWENTY_TWENTY_TWO";
    private final static String TWENTY_TWO_TWENTY_FOUR = "TWENTY_TWO_TWENTY_FOUR";

    /**
     * 横坐标时间数据
     * @return
     */
    public static List<String> getAbscissa() {
        List<String> abscissaList = new ArrayList<>();
        abscissaList.add(ZERO);
        abscissaList.add(TWO);
        abscissaList.add(FOUR);
        abscissaList.add(SIX);
        abscissaList.add(EIGHT);
        abscissaList.add(TEN);
        abscissaList.add(TWELVE);
        abscissaList.add(FOURTEEN);
        abscissaList.add(SIXTEEN);
        abscissaList.add(EIGHTEEN);
        abscissaList.add(TWENTY);
        abscissaList.add(TWENTY_TWO);
        abscissaList.add(TWENTY_FOUR);
        return abscissaList;
    }

    public static List<List<Long>> getTwelveTime() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        Calendar calendar = Calendar.getInstance();
        // 获取当前时间的前一天
        calendar.set(year, month, day, 0, 0, 0);
        Long zerozero = calendar.getTimeInMillis();

        Calendar yesterday = Calendar.getInstance();
        yesterday.set(year, month, day - 1, 22, 0, 0);
        Long twentyYesterday = yesterday.getTimeInMillis();

        List<List<Long>> startEnd = new ArrayList<>();
        // 昨天22点-今天00点
        List<Long> timeList0 = new ArrayList<>(1);
        timeList0.add(twentyYesterday);
        timeList0.add(zerozero);
        startEnd.add(timeList0);

        // 明天
        calendar.set(year, month, day, 24, 0, 0);
        Long twentyFour = calendar.getTimeInMillis();

        // 获取当天内十二时辰
        calendar.set(year, month, day, 2, 0, 0);
        Long two = calendar.getTimeInMillis();

        // 0-2点
        List<Long> timeList1 = new ArrayList<>(1);
        timeList1.add(zerozero);
        timeList1.add(two);
        startEnd.add(timeList1);

        // 2-4点
        List<Long> timeList2 = new ArrayList<>(1);
        calendar.set(year, month, day, 4, 0, 0);
        Long four = calendar.getTimeInMillis();
        timeList2.add(two);
        timeList2.add(four);
        startEnd.add(timeList2);

        // 4-6点
        List<Long> timeList3 = new ArrayList<>(1);
        calendar.set(year, month, day, 6, 0, 0);
        Long six = calendar.getTimeInMillis();
        timeList3.add(four);
        timeList3.add(six);
        startEnd.add(timeList3);

        // 6-8点
        List<Long> timeList4 = new ArrayList<>(1);
        calendar.set(year, month, day, 8, 0, 0);
        Long eight = calendar.getTimeInMillis();
        timeList4.add(six);
        timeList4.add(eight);
        startEnd.add(timeList4);

        // 8-10点
        List<Long> timeList5 = new ArrayList<>(1);
        calendar.set(year, month, day, 10, 0, 0);
        Long ten = calendar.getTimeInMillis();
        timeList5.add(eight);
        timeList5.add(ten);
        startEnd.add(timeList5);

        // 10-12点
        List<Long> timeList6 = new ArrayList<>(1);
        calendar.set(year, month, day, 12, 0, 0);
        Long twelve = calendar.getTimeInMillis();
        timeList6.add(ten);
        timeList6.add(twelve);
        startEnd.add(timeList6);

        // 12-14点
        List<Long> timeList7 = new ArrayList<>(1);
        calendar.set(year, month, day, 14, 0, 0);
        Long fourteen = calendar.getTimeInMillis();
        timeList7.add(twelve);
        timeList7.add(fourteen);
        startEnd.add(timeList7);

        // 14-16点
        List<Long> timeList8 = new ArrayList<>(1);
        calendar.set(year, month, day, 16, 0, 0);
        Long sixteen = calendar.getTimeInMillis();
        timeList8.add(fourteen);
        timeList8.add(sixteen);
        startEnd.add(timeList8);

        // 16-18点
        List<Long> timeList9 = new ArrayList<>(1);
        calendar.set(year, month, day, 18, 0, 0);
        Long eighteen = calendar.getTimeInMillis();
        timeList9.add(sixteen);
        timeList9.add(eighteen);
        startEnd.add(timeList9);

        // 18-20点
        List<Long> timeList10 = new ArrayList<>(1);
        calendar.set(year, month, day, 20, 0, 0);
        Long twenty = calendar.getTimeInMillis();
        timeList10.add(eighteen);
        timeList10.add(twenty);
        startEnd.add(timeList10);

        // 20-22点
        List<Long> timeList11 = new ArrayList<>(1);
        calendar.set(year, month, day, 22, 0, 0);
        Long twentytwo = calendar.getTimeInMillis();
        timeList11.add(twenty);
        timeList11.add(twentytwo);
        startEnd.add(timeList11);

        // 22-24点
        List<Long> timeList12 = new ArrayList<>(1);
        timeList12.add(twentytwo);
        timeList12.add(twentyFour);
        startEnd.add(timeList12);

        return startEnd;

    }

    public static String getZERO() {
        return ZERO;
    }

    public static String getTWO() {
        return TWO;
    }

    public static String getFOUR() {
        return FOUR;
    }

    public static String getSIX() {
        return SIX;
    }

    public static String getEIGHT() {
        return EIGHT;
    }

    public static String getTEN() {
        return TEN;
    }

    public static String getTWELVE() {
        return TWELVE;
    }

    public static String getFOURTEEN() {
        return FOURTEEN;
    }

    public static String getSIXTEEN() {
        return SIXTEEN;
    }

    public static String getEIGHTEEN() {
        return EIGHTEEN;
    }

    public static String getTWENTY() {
        return TWENTY;
    }

    public static String getTwentyTwo() {
        return TWENTY_TWO;
    }

    public static String getTwentyFour() {
        return TWENTY_FOUR;
    }

    public static String getZeroTwo() {
        return ZERO_TWO;
    }

    public static String getTwoFour() {
        return TWO_FOUR;
    }

    public static String getFourSix() {
        return FOUR_SIX;
    }

    public static String getSixEight() {
        return SIX_EIGHT;
    }

    public static String getEightTen() {
        return EIGHT_TEN;
    }

    public static String getTenTwelve() {
        return TEN_TWELVE;
    }

    public static String getTwelveFourteen() {
        return TWELVE_FOURTEEN;
    }

    public static String getFourteenSixteen() {
        return FOURTEEN_SIXTEEN;
    }

    public static String getSixteenEighteen() {
        return SIXTEEN_EIGHTEEN;
    }

    public static String getEighteenTwenty() {
        return EIGHTEEN_TWENTY;
    }

    public static String getTwentyTwentyTwo() {
        return TWENTY_TWENTY_TWO;
    }

    public static String getTwentyTwoTwentyFour() {
        return TWENTY_TWO_TWENTY_FOUR;
    }
}
