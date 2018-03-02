package com.wethis.library.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zzc on 2017/11/29/029.
 */

public class DateTimeUtils {

    /**
     * 获取yyyy-MM-dd格式的日期字符串
     *
     * @return
     */

    public static String getTime(String time){
        long l = Long.parseLong(time)*1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(l);
        return simpleDateFormat.format(date);
    }

    public static String getFormatDateStr(Date date) {
        return getFormatDateStr(date, "yyyy-MM-dd");
    }

    /**
     * 获取指定格式的日期字符串
     *
     * @param date
     * @param format 带转换的格式如（yyyy-MM-dd）
     * @return
     */
    public static String getFormatDateStr(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 获取指定格式的日期字符串
     *
     * @param formatStr
     * @param dateStr
     * @return
     */
    public static String getFormatDateStr(String formatStr, String dateStr) {
        String dates;
        try {
            DateFormat dateFormat = new SimpleDateFormat(formatStr);// 可以方便地修改日期格式
            Date date = dateFormat.parse(dateStr);
            dates = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            dates = "";
        }
        return dates;
    }

    /**
     * 获取指定格式的当前时间字符串
     *
     * @param dateFormatStr
     * @return
     */
    public static String getNowTime(String dateFormatStr) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);// 可以方便地修改日期格式
        String nowTime = dateFormat.format(now);
        return nowTime;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 把 yyyy-MM-dd格式字符串转为日期
     *
     * @param strDate
     * @return
     * @throws Exception
     */
    public static Date string2Date(String strDate) {
        return string2Date("yyyy-MM-dd", strDate);
    }

    /**
     * 将指定格式的字符串转换为Date类型
     *
     * @param format
     * @param strDate
     * @return
     */
    public static Date string2Date(String format, String strDate) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串格式化为yyyy-MM-dd类型的字符串
     *
     * @param strDate
     * @return
     */
    public static String string2DateString(String strDate) {
        return stringToDateString("yyyy-MM-dd", strDate);
    }

    /**
     * 将字符串格式化为指定类型的字符串
     */
    public static String stringToDateString(String format, String strDate) {
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        Date d1 = new Date(strDate);
        String t1 = format1.format(d1);
        return t1;
    }

    /**
     * 获取当前时间，格式为："yyyy年MM月dd日   HH:mm:ss"
     *
     * @return
     */
    public static String getNowDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取格式化时间字符串 xx年xx月xx日星期x
     *
     * @return
     */
    public static String getFormatDate() {
        String mYear;
        String mMonth;
        String mDay;
        String mWay;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mYear + "年" + mMonth + "月" + mDay + "日" + "/星期" + mWay;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回月日短格式。 format:05月09日
     *
     * @param paramCalendar
     * @return
     */
    public static String calendarToXyueXri(Calendar paramCalendar) {
        String str = "";
        if (paramCalendar != null) {

            int i = 1 + paramCalendar.get(Calendar.MONTH);
            if (i >= 10) str = i + "月";
            else str = "0" + i + "月";
            int j = paramCalendar.get(Calendar.DAY_OF_MONTH);
            if (j >= 10) str = str + j + "日";
            else str = str + "0" + j + "日";
        } else {
            str = null;
        }
        return str;
    }

    public static String changeDataTimeStringToSC(String paramString) {
        return formatCalToSCString(formatStringToCalendar(paramString));
    }

    public static String changeDateStringToSC(String paramString) {
        String str;
        if ((paramString != null) && (paramString != "")) {
            String[] arrayOfString = trim(paramString.split("-"));
            for (int i = 0; i < 3; i++) {
                if (arrayOfString[i].length() != 1) continue;
                arrayOfString[i] = ("0" + arrayOfString[i]);
            }
            str = arrayOfString[0] + "年" + arrayOfString[1] + "月" + arrayOfString[2] + "日";
        } else {
            str = paramString;
        }
        return str;
    }

    private static void checkArrayNullOrEmpty(Object[] arr) {
        if (arr != null) {
            if (arr.length != 0) return;
            throw new IllegalArgumentException("array must have some datas");
        }
        throw new NullPointerException();
    }

    public static String fixDateString(String paramString) {
        String[] strs;
        String result;
        if ((paramString != null) && (paramString != "")) {
            strs = trim(paramString.split("[年月日]"));
            for (int i = 0; i < 3; i++) {
                if (strs[i].length() != 1) continue;
                strs[i] = ("0" + strs[i]);
            }
            result = strs[0] + "-" + strs[1] + "-" + strs[2];
        } else {
            result = paramString;
        }
        return result;
    }

    public static String fixDateStringWithoutYear(String paramString) {
        String str;
        if ((paramString != null) && (paramString != "")) {
            String[] arrayOfString = trim(paramString.split("[年月日]"));
            for (int i = 0; i < 3; i++) {
                if (arrayOfString[i].length() != 1) continue;
                arrayOfString[i] = ("0" + arrayOfString[i]);
            }
            str = arrayOfString[1] + "-" + arrayOfString[2];
        } else {
            str = "";
        }
        return str;
    }

    public static String formatCalToSCString(Calendar car) {
        String str;
        if (car != null) {
            str = String.valueOf(car.get(Calendar.YEAR)) + "年";
            if (1 + car.get(Calendar.MONTH) >= 10)
                str = str + String.valueOf(1 + car.get(Calendar.MONTH)) + "月";
            else str = str + "0" + String.valueOf(1 + car.get(Calendar.MONTH)) + "月";
            if (car.get(Calendar.DAY_OF_MONTH) >= 10)
                str = str + String.valueOf(car.get(Calendar.DAY_OF_MONTH)) + "日";
            else str = str + "0" + String.valueOf(car.get(Calendar.DAY_OF_MONTH)) + "日";
        } else {
            str = null;
        }
        return str;
    }

    /**
     * 格式化日历。 2012-09-10 03:10 yyyy-MM-dd HH:mi
     *
     * @param paramCalendar
     * @return
     */
    public static String formatCalendarToMiddleString(Calendar paramCalendar) {
        // 年
        String str = String.valueOf(paramCalendar.get(Calendar.YEAR)) + "-";
        // 月
        if (1 + paramCalendar.get(Calendar.MONTH) >= 10)
            str = str + String.valueOf(1 + paramCalendar.get(Calendar.MONTH)) + "-";
        else str = str + "0" + String.valueOf(1 + paramCalendar.get(Calendar.MONTH)) + "-";
        // 日
        if (paramCalendar.get(Calendar.DAY_OF_MONTH) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.DAY_OF_MONTH));
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.DAY_OF_MONTH));
        str = str + " ";
        // 时

        if (paramCalendar.get(Calendar.HOUR_OF_DAY) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.HOUR_OF_DAY)) + ":";
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.HOUR_OF_DAY)) + ":";
        // 分

        if (paramCalendar.get(Calendar.MINUTE) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.MINUTE));
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.MINUTE));
        // 秒
        /*
		 * if (paramCalendar.get(13) >= 10) str = str + String.valueOf(paramCalendar.get(13)); else str = str + "0" +
		 * String.valueOf(paramCalendar.get(13));
		 */
        return str;
    }

    /**
     * 格式化日历。 03:10
     *
     * @param paramCalendar
     * @return
     */
    public static String formatCalendarHourMinute(Calendar paramCalendar) {
        String str = "";
        // 时
        if (paramCalendar.get(Calendar.HOUR_OF_DAY) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.HOUR_OF_DAY)) + ":";
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.HOUR_OF_DAY)) + ":";
        // 分
        if (paramCalendar.get(Calendar.MINUTE) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.MINUTE));
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.MINUTE));
        return str;
    }

    public static String formatCalendarToString(Calendar paramCalendar) {

        String str = String.valueOf(paramCalendar.get(Calendar.YEAR)) + "-";
        if (1 + paramCalendar.get(Calendar.MONTH) >= 10)
            str = str + String.valueOf(1 + paramCalendar.get(Calendar.MONTH)) + "-";
        else str = str + "0" + String.valueOf(1 + paramCalendar.get(Calendar.MONTH)) + "-";
        if (paramCalendar.get(Calendar.DAY_OF_MONTH) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.DAY_OF_MONTH));
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.DAY_OF_MONTH));
        str = str + " ";
        if (paramCalendar.get(Calendar.HOUR_OF_DAY) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.HOUR_OF_DAY)) + ":";
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.HOUR_OF_DAY)) + ":";
        if (paramCalendar.get(Calendar.MINUTE) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.MINUTE)) + ":";
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.MINUTE)) + ":";

        if (paramCalendar.get(Calendar.SECOND) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.SECOND));
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.SECOND));
        return str;
    }

    /**
     * 返回标准日期格式 format: 2011-05-09
     *
     * @param paramCalendar
     * @return
     */
    public static String formatCalendarToShortString(Calendar paramCalendar) {
        String str = String.valueOf(paramCalendar.get(Calendar.YEAR)) + "-";
        if (1 + paramCalendar.get(Calendar.MONTH) >= 10)
            str = str + String.valueOf(1 + paramCalendar.get(Calendar.MONTH)) + "-";
        else str = str + "0" + String.valueOf(1 + paramCalendar.get(Calendar.MONTH)) + "-";
        if (paramCalendar.get(Calendar.DAY_OF_MONTH) >= 10)
            str = str + String.valueOf(paramCalendar.get(Calendar.DAY_OF_MONTH));
        else str = str + "0" + String.valueOf(paramCalendar.get(Calendar.DAY_OF_MONTH));
        return str;
    }

    /**
     * 返回指定日期格式的字符串
     *
     * @param paramDate
     * @param fmt
     * @return
     */
    public static String formatDateToStringByFMT(Date paramDate, String fmt) {
        return new SimpleDateFormat(fmt).format(paramDate);
    }

    public static Calendar formatSCStringToCalendar(String paramString) {
        Calendar localCalendar;
        if ((paramString != null) && (paramString != ""))
            localCalendar = formatStringToCalendar(fixDateString(paramString));
        else localCalendar = null;
        return localCalendar;
    }

    /**
     * 格式化日期字符串为Calender类型。
     *
     * @param dateStr 2013-02-11
     * @return
     */
    public static Calendar formatStringToCalendar(String dateStr) {
        Calendar car = Calendar.getInstance();
        if ((dateStr != null) && (dateStr != "")) {
            car.setLenient(true);
            List<String> dateArr = new ArrayList<String>();
            // 匹配1-4位整数
            Matcher matcher = Pattern.compile("\\d{1,4}").matcher(dateStr);
            while (matcher.find())
                dateArr.add(matcher.group());
            // 非yyyy-MM-dd格式
            if (dateArr.size() != 3) {
                if (dateArr.size() == 6 || dateArr.size() == 7)
                    car.set(Integer.parseInt(dateArr.get(0)),
                            Integer.parseInt(dateArr.get(1)) - 1, Integer.parseInt(dateArr.get(2)),
                            Integer.parseInt(dateArr.get(3)), Integer.parseInt(dateArr.get(4)),
                            Integer.parseInt(dateArr.get(5)));
            } else
                car.set(Integer.parseInt(dateArr.get(0)), Integer.parseInt(dateArr.get(1)) - 1,
                        Integer.parseInt(dateArr.get(2)), 0, 0, 0);
        }
        return car;
    }

    /**
     * 出生到现在的天数
     *
     * @param paramCalendar1
     * @param paramCalendar2
     * @return
     */
    public static int getBirthIntervalDays(Calendar paramCalendar1, Calendar paramCalendar2) {
        int i;
        if ((paramCalendar1 != null) && (paramCalendar2 != null)) {
            if (paramCalendar1.after(paramCalendar2)) {
                Calendar localCalendar = paramCalendar1;
                paramCalendar1 = paramCalendar2;
                paramCalendar2 = localCalendar;
            }
            i = (int) ((3600000L + (paramCalendar2.getTime().getTime() - paramCalendar1.getTime().getTime())) / 86400000L);
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * 获取两个日期相差多少天
     *
     * @param paramString1
     * @param paramString2
     * @return
     */
    public static long getDaysBetween(String paramString1, String paramString2) {
        Calendar localCalendar = formatStringToCalendar(paramString1);
        return Math.abs(formatStringToCalendar(paramString2).getTime().getTime() - localCalendar.getTime().getTime()) / 86400000L;
    }

    /**
     * 返回两Calendar间隔时间
     *
     * @param c1
     * @param c2
     * @return 毫秒
     */
    public static long getMillBetween(Calendar c1, Calendar c2) {
        return (c1.getTime().getTime() - c2.getTime().getTime());
    }

    /**
     * 返回两个日期相差天数。 后面的日期-前面的日期
     *
     * @param d1
     * @param d2
     * @return
     * @throws ParseException
     */
    public static Long getDaysBetween(Date d1, Date d2) throws ParseException {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        c1.set(Calendar.HOUR_OF_DAY, 0);

        c1.set(Calendar.MINUTE, 0);


        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        return Long.valueOf(Math.abs(c2.getTime().getTime() - c1.getTime().getTime()) / 86400000L);
    }

    /**
     * 判断指定时间是否在指定时间区间内 SimpleDateFormat format = new SimpleDateFormat("HH:mm"); String[] time = new String[] { "17:50",
     * "18:00" }; System.out.println(isShift(Calendar.getInstance().getTimeInMillis(), time));
     *
     * @param currTime
     * @param timeSlot
     * @return
     */
    public static boolean isShift(final long currTime, String[] timeSlot) {

        try {

            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTimeInMillis(currTime);
            String[] tmpArray = timeSlot[0].split(":");
            long startTime, stopTime;
            tempCalendar.clear(Calendar.HOUR_OF_DAY);
            tempCalendar.clear(Calendar.MINUTE);
            tempCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArray[0]));
            tempCalendar.set(Calendar.MINUTE, Integer.parseInt(tmpArray[1]));
            startTime = tempCalendar.getTimeInMillis();
            tmpArray = timeSlot[1].split(":");
            int stopHour = Integer.parseInt(tmpArray[0]), stopMinute = Integer.parseInt(tmpArray[1]);
            if (stopHour == 0) {
                tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            tempCalendar.clear(Calendar.HOUR_OF_DAY);
            tempCalendar.clear(Calendar.MINUTE);
            tempCalendar.set(Calendar.HOUR_OF_DAY, stopHour);
            tempCalendar.set(Calendar.MINUTE, stopMinute);
            stopTime = tempCalendar.getTimeInMillis();

            if (stopTime < startTime) {
                stopTime = 24 * 60 * 60 * 1000 + stopTime;
            }

            return ((startTime < currTime && currTime <= stopTime) ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

    }

    public static Calendar getDefaultDateTimeFromDateTime(String paramString1, String paramString2, int paramInt) {
        Object localObject;
        if ((paramString1 != null) && (paramString1.length() != 0) && (paramString2 != null)
                && (paramString2.length() != 0)) {
            localObject = formatStringToCalendar(paramString1);
            Calendar localCalendar = formatStringToCalendar(paramString2);
            if (!localCalendar.before(localObject)) {
                localObject = localCalendar;
            } else {
                ((Calendar) localObject).add(Calendar.DAY_OF_MONTH, paramInt);
            }
        } else {
            localObject = null;
        }
        return (Calendar) localObject;
    }

    public static Calendar getDefaultDateTimeFromDateTime(String paramString, Calendar paramCalendar, int paramInt) {
        Calendar localCalendar1;
        if ((paramString != null) && (paramString.length() != 0) && (paramCalendar != null)) {
            Calendar localCalendar2 = formatStringToCalendar(paramString);
            localCalendar1 = (Calendar) paramCalendar.clone();
            if (!localCalendar1.before(localCalendar2)) {
            } else {
                localCalendar2.add(Calendar.DAY_OF_MONTH, paramInt);
                localCalendar1 = localCalendar2;
            }
        } else {
            localCalendar1 = null;
        }
        return localCalendar1;
    }

    public static Calendar getDefaultDateTimeFromDateTime(Calendar paramCalendar, String paramString, int paramInt) {
        Object localObject;
        if ((paramCalendar != null) && (paramString != null) && (paramString.length() != 0)) {
            localObject = paramCalendar.clone();
            Calendar localCalendar = formatStringToCalendar(paramString);
            if (!localCalendar.before(localObject)) {
                localObject = localCalendar;
            } else {
                ((Calendar) localObject).add(Calendar.DAY_OF_MONTH, paramInt);
            }
        } else {
            localObject = null;
        }
        return (Calendar) localObject;
    }

    public static Calendar getDefaultDateTimeFromDateTime(Calendar paramCalendar1, Calendar paramCalendar2, int paramInt) {
        Calendar localCalendar;
        if ((paramCalendar1 != null) && (paramCalendar2 != null)) {
            localCalendar = (Calendar) paramCalendar1.clone();
            if (!paramCalendar2.before(paramCalendar1)) {
                localCalendar = paramCalendar2;
            } else {
                localCalendar.add(Calendar.DAY_OF_MONTH, paramInt);
            }
        } else {
            localCalendar = null;
        }
        return localCalendar;
    }

    public static String getFieldStringFromCalendar(Calendar paramCalendar, int paramInt) {
        String str = "";
        switch (paramInt) {
            case 0:
                str = formatCalendarToString(paramCalendar).substring(0, 10);
                break;
            case 1:
                str = formatCalendarToString(paramCalendar).substring(5, 10);
                break;
            case 2:
                str = formatCalendarToString(paramCalendar).substring(0, 4);
                break;
            case 3:
                str = formatCalendarToString(paramCalendar).substring(5, 7);
                break;
            case 4:
                str = formatCalendarToString(paramCalendar).substring(8, 10);
                break;
            case 5:
                str = formatCalendarToString(paramCalendar).substring(11, 19);
                break;
            case 6:
                str = formatCalendarToString(paramCalendar).substring(11, 16);
                break;
            case 7:
                str = formatCalendarToString(paramCalendar).substring(11, 13);
                break;
            case 8:
                str = formatCalendarToString(paramCalendar).substring(14, 16);
                break;
            case 9:
                str = formatCalendarToString(paramCalendar).substring(17, 19);
                break;
            case 10:
                str = formatCalendarToString(paramCalendar).substring(0, 16);
                break;
            case 11:
                str = formatCalendarToString(paramCalendar).substring(0, 7);
                break;
            case 12:
                str = formatCalendarToString(paramCalendar).substring(2, 4);
        }
        return str;
    }

    public static String getFieldStringFromDateString(String paramString, int paramInt) {
        String str;
        if (paramString != null)
            str = getFieldStringFromCalendar(formatStringToCalendar(paramString), paramInt);
        else str = "";
        return str;
    }

    public static Calendar getIntervalDateTime(String paramString, int paramInt) {
        Calendar localCalendar;
        if ((paramString != null) && (paramString.length() != 0))
            localCalendar = getIntervalDateTime(
                    formatStringToCalendar(paramString), paramInt);
        else localCalendar = null;
        return localCalendar;
    }

    public static Calendar getIntervalDateTime(Calendar paramCalendar, int paramInt) {
        Calendar localCalendar;
        if (paramCalendar != null) {
            localCalendar = (Calendar) paramCalendar.clone();
            localCalendar.add(Calendar.DAY_OF_MONTH, paramInt);
        } else {
            localCalendar = null;
        }
        return localCalendar;
    }

    /**
     * 获取两个日期距离的天数
     *
     * @param paramString1
     * @param paramString2
     * @return
     */
    public static int getIntervalDays(String paramString1, String paramString2) {
        int i;
        if ((paramString1 != null) && (paramString2 != null))
            i = getIntervalDays(formatStringToCalendar(paramString1),
                    formatStringToCalendar(paramString2));
        else i = 0;
        return i;
    }

    /**
     * 获取两个日期距离的天数
     *
     * @param paramCalendar1
     * @param paramCalendar2
     * @return
     */
    public static int getIntervalDays(Calendar paramCalendar1, Calendar paramCalendar2) {
        Calendar localCalendar1 = Calendar.getInstance();


        localCalendar1.set(paramCalendar1.get(Calendar.YEAR), paramCalendar1.get(Calendar.MONTH), paramCalendar1.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.set(paramCalendar2.get(Calendar.YEAR), paramCalendar2.get(Calendar.MONTH), paramCalendar2.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return (int) (Math.abs(localCalendar1.getTimeInMillis() - localCalendar2.getTimeInMillis()) / 86400000L);
    }

    /**
     * 将Calender格式化为周... 如周一、周二。。。
     *
     * @param paramCalendar
     * @return
     */
    public static String getWeekDayFromCalendar(Calendar paramCalendar) {
        String str = "";
        if (paramCalendar != null) {
            switch (paramCalendar.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    str = str + "周日";
                    break;
                case 2:
                    str = str + "周一";
                    break;
                case 3:
                    str = str + "周二";
                    break;
                case 4:
                    str = str + "周三";
                    break;
                case 5:
                    str = str + "周四";
                    break;
                case 6:
                    str = str + "周五";
                    break;
                case 7:
                    str = str + "周六";
            }
        }
        return str;
    }

    /**
     * 将Calendar转化为字符串的星期 如星期一、星期二......
     *
     * @param paramCalendar
     * @return
     */
    public static String getWeekDayFromCalendar1(Calendar paramCalendar) {
        String str = "";
        if (paramCalendar != null) {

            switch (paramCalendar.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    str = str + "星期天";
                    break;
                case 2:
                    str = str + "星期一";
                    break;
                case 3:
                    str = str + "星期二";
                    break;
                case 4:
                    str = str + "星期三";
                    break;
                case 5:
                    str = str + "星期四";
                    break;
                case 6:
                    str = str + "星期五";
                    break;
                case 7:
                    str = str + "星期六";
            }
        }
        return str;
    }

    /**
     * 是否为闰年
     *
     * @param paramString
     * @return
     */
    public static boolean isLeapyear(String paramString) {
        boolean i = false;
        if ((!paramString.contains("-")) && ((!paramString.contains("年")) || (!paramString.contains("月")))) {
            i = false;
        } else {
            int year = Integer.parseInt(paramString.substring(0, 3));
            i = !(((year % 100 != 0) || (year % 400 != 0)) && ((year % 100 == 0) || (year % 4 != 0)));
        }
        return i;
    }

    private static String[] trim(String[] strArr) {
        checkArrayNullOrEmpty(strArr);
        int i = strArr.length;
        for (int j = 0; j < i; j++)
            strArr[j] = strArr[j].trim();
        return strArr;
    }

    /**
     * 比较两个日期，当第一个参数大于第二个参数，返回false
     *
     * @param lastDate
     * @param addDate
     * @return
     */
    public static boolean isToUpdate(String lastDate, String addDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(lastDate);
            Date dt2 = df.parse(addDate);
            if (dt1.getTime() > dt2.getTime()) {
                return false;
            } else return dt1.getTime() < dt2.getTime();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 添加月
     *
     * @param oldDate
     * @param num     添加几个月
     * @return
     */
    public final static String getAddDate(String oldDate, int num) {
        Calendar cal = Calendar.getInstance();
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        try {
            date = dateFormat.parse(oldDate);
            // date = DateFormat.getDateInstance().parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, num);
        String newDate = dateFormat.format(cal.getTime());
        return newDate;
    }

    public static final int MORNING = 0;

    public static final int AFTERNOON = 1;

    public static final int NIGHT = -1;

    /**
     * 获取每天的时间段
     *
     * @param timeFlag
     * @return
     */
    public static String getTimeInDay(int timeFlag) {
        String timeStr;
        switch (timeFlag) {
            case MORNING:
                timeStr = "上午";
                break;
            case AFTERNOON:
                timeStr = "下午";
                break;
            case NIGHT:
                timeStr = "晚上";
                break;
            default:
                timeStr = "无";
                break;
        }
        return timeStr;
    }

    /**
     * 当前时间比较 获取早中晚标识
     *
     * @param time
     * @return
     */
    public static int compareShortTime(String time) {
        String morning = "06:00";
        String noon = "12:00";
        String night = "18:00";
        if (morning.compareTo(time) <= 0 && noon.compareTo(time) > 0) {
            // 早上
            return MORNING;
        } else if (noon.compareTo(time) <= 0 && night.compareTo(time) > 0) {
            // 中午
            return AFTERNOON;
        } else if (night.compareTo(time) < 0 || morning.compareTo(time) >= 0) {
            // 晚上
            return NIGHT;
        }
        return -999;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

}
