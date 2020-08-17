package com.zte.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jodd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    enum FormatEnum {
        /**
         * 返回 DateTimeFormatter "yyyy-MM-dd HH:mm:ss" 时间格式
         */
        FORMAT_DATA_TIME(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),

        /**
         * 返回 DateTimeFormatter "yyyyMMddHHmmss" 时间格式
         */
        FORMAT_DB(DateTimeFormatter.ofPattern(DB_FORMAT)),

        /**
         * 返回 DateTimeFormatter "yyyyMMdd" 时间格式
         */
        FORMAT_DB_DATE(DateTimeFormatter.ofPattern(DB_DATE_FORMAT)),

        /**
         * 返回 DateTimeFormatter "HHmmss" 时间格式
         */
        FORMAT_DB_TIME(DateTimeFormatter.ofPattern(DB_TIME_FORMAT)),

        /**
         * 返回 DateTimeFormatter "yyyy-MM-dd" 时间格式
         */
        FORMAT_DATE(DateTimeFormatter.ofPattern(DATE_FORMAT)),
        /**
         * 返回 DateTimeFormatter "yyyy年MM月dd日" 时间格式
         */
        FORMAT_CN_DATE(DateTimeFormatter.ofPattern(CN_DATE_FORMAT)),

        /**
         * 返回 DateTimeFormatter "HH:mm:ss" 时间格式
         */
        FORMAT_TIME(DateTimeFormatter.ofPattern(TIME_FORMAT));

        private DateTimeFormatter value;

        FormatEnum(DateTimeFormatter format) {
            this.value = format;
        }
    }

    /**
     * 数据库日期
     **/
    public static final String DB_DATE_FORMAT = "yyyyMMdd";
    /**
     * 数据库时间
     **/
    public static final String DB_TIME_FORMAT = "HHmmss";
    /**
     * 数据库时间
     **/
    public static final String DB_FORMAT = "yyyyMMddHHmmss";
    /**
     * 数据库时间带时区
     **/
    public static final String DB_ZONED_FORMAT = "yyyyMMddHHmmssX";
    /**
     * 返回 yyyy-MM-dd HH:mm:ss
     **/
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 返回 yyyy-MM-dd
     **/
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 返回 yyyy年MM月dd日
     **/
    public static final String CN_DATE_FORMAT = "yyyy年MM月dd日";
    /**
     * 返回 HH:mm:ss
     **/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 获取秒级时间戳
     */
    public static Long epochSecond() {
        return epochSecond("+8");
    }

    public static Long epochSecond(String zoned) {
        return localDateTime().toEpochSecond(ZoneOffset.of(StringUtil.isNotBlank(zoned) ? "+8" : zoned));
    }

    /**
     * 获取毫秒级时间戳
     */
    public static Long epochMilli() {
        return epochMilli("+8");
    }

    public static Long epochMilli(String zoned) {
        return localDateTime().toInstant(ZoneOffset.of(StringUtil.isNotBlank(zoned) ? "+8" : zoned)).toEpochMilli();
    }

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 时间转换相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * LocalDateTime转String
     *
     * @param date
     * @param formatter
     * @return java.lang.String
     * @author zhongyong 2019/11/29 14:53
     */
    public static String toString(LocalDateTime date, DateTimeFormatter formatter) {
        return formatter.format(date);
    }

    public static String toString(LocalDateTime date) {
        return toString(date, FormatEnum.FORMAT_DB.value);
    }

    /**
     * 数据库时间转中文日期
     * @param date yyyyMMddHHmmss
     * @return yyyy年MM月dd日
     * @author zhongyong 2020/1/7 10:58
     */
    public static String dbTimeToCNString(String date) {
        return toString(toLocalDateTime(date, FormatEnum.FORMAT_DB.value), FormatEnum.FORMAT_CN_DATE.value);
    }

    /**
     * Date类型转LocalDateTime
     *
     * @param date Date类型时间
     * @return LocalDateTime
     * @author zhongyong 2019/11/29 11:40
     */
    public static LocalDateTime toLocalDateTime(Date date, ZoneId zone) {
        return LocalDateTime.ofInstant(date.toInstant(), zone);
    }

    public static LocalDateTime toLocalDateTime(Date date, String zone) {
        return toLocalDateTime(date, ZoneOffset.of(zone));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date, "+8");
    }

    /**
     * String类型转LocalDateTime
     *
     * @param date String类型时间
     * @return LocalDateTime
     * @author zhongyong 2019/11/29 10:43
     */
    public static LocalDateTime toLocalDateTime(String date, DateTimeFormatter formatter) {
        return LocalDateTime.from(formatter.parse(date));
    }

    public static LocalDateTime toLocalDateTime(String date, String formatter) {
        return toLocalDateTime(date, DateTimeFormatter.ofPattern(formatter));
    }

    public static LocalDateTime toLocalDateTime(String date) {
        return toLocalDateTime(date, FormatEnum.FORMAT_DB.value);
    }

    /**
     * Long类型转LocalDateTime
     *
     * @param timestamp Long类型时间
     * @return LocalDateTime
     * @author zhongyong 2019/11/29 10:43
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return localDateTime();
        }
        Instant instant = Instant.ofEpochMilli(timestamp > 1000000000000L ? timestamp : timestamp * 1000L);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转为Long类型的timestamp
     *
     * @author zhongyong 2019/11/29 10:24
     */
    public static Long getTimestampOfDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LocalDateTime 类型转 Date
     *
     * @return 转换后的Date类型日期
     * @author zhongyong 2019/11/29 10:25
     */
    public static Date toDate(String datetime, DateTimeFormatter formatter, ZoneId zone) {
        return Date.from(toLocalDateTime(datetime, formatter).atZone(zone).toInstant());
    }

    public static Date toDate(String datetime, DateTimeFormatter formatter) {
        return toDate(datetime, formatter, ZoneId.systemDefault());
    }

    public static Date toDate(String datetime) {
        return toDate(datetime, FormatEnum.FORMAT_DB.value);
    }

    /**
     * LocalDateTime 类型转 Date
     *
     * @param localDateTime localDateTime
     * @return 转换后的Date类型日期
     * @author zhongyong 2019/11/29 10:26
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalTime类型转Date，日期为今天
     *
     * @param localTime localTime
     * @return 转换后的Date类型日期
     * @author zhongyong 2019/11/29 10:27
     */
    public static Date toDate(LocalTime localTime) {
        return toDate(LocalDateTime.of(localDate(), localTime));
    }

    /**
     * 将指定格式日期转换成目标格式
     *
     * @param date   日期
     * @param before 转换前格式
     * @param after  转换后格式
     * @return java.lang.String
     * @author zhongyong 2019/11/29 14:59
     */
    public static String conversion(String date, DateTimeFormatter before, DateTimeFormatter after) {
        return toString(toLocalDateTime(date, before), after);
    }

    /**
     * 这是满足特殊情况，在object类型下转换为String时，转换时间类型会出现异常错误，可以使用这种方式进行转换
     *
     * @author zhongyong 2019/11/29 10:22
     */
    public static LocalDateTime conversionStringToDateTime(String time) {
        return Timestamp.valueOf(time).toLocalDateTime();
    }

    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 时间转换相关 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/


    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 时间间隔相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 获取 endDate-startDate 日期间隔天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 日期间隔天数
     * @author zhongyong 2019/11/29 10:29
     */
    public static Long daysInterval(LocalDate startDate, LocalDate endDate) {
        return endDate.toEpochDay() - startDate.toEpochDay();
    }

    public static Long daysInterval(LocalDateTime startDate, LocalDateTime endDate) {
        return daysInterval(startDate.toLocalDate(), endDate.toLocalDate());
    }

    public static Long daysInterval(String startDate, String endDate, DateTimeFormatter formatter) {
        return daysInterval(LocalDateTime.parse(endDate, formatter), LocalDateTime.parse(startDate, formatter));
    }

    public static Long daysInterval(String startDate, String endDate) {
        return daysInterval(startDate, endDate, FormatEnum.FORMAT_DB.value);
    }

    /**
     * 距离现在多少分钟
     *
     * @param date
     * @return java.lang.Long 正数：将来时间，负数：过去时间
     * @author zhongyong 2019/11/29 16:14
     */
    public static Long minutesInterVal(String date) {
        return minutesInterVal(toLocalDateTime(date));
    }

    public static Long minutesInterVal(LocalDateTime date) {
        return minutesInterVal(date, localDateTime());
    }

    public static Long minutesInterVal(LocalDateTime time1, LocalDateTime time2) {
        ZoneOffset zone = ZoneOffset.of("+8");
        return time1.toEpochSecond(zone) - time2.toEpochSecond(zone);
    }

    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 时间间隔相关 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 日期操作相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 获取前几年/后几年的日期
     *
     * @param date 日期
     * @param year 年份（正加负减）
     * @author zhongyong 2019/11/29 10:32
     */
    public static String plusYears(String date, Long year) {
        return toLocalDateTime(date).plusYears(year).format(FormatEnum.FORMAT_DB.value);
    }

    public static String plusYears(String date) {
        return plusYears(date, 1L);
    }

    /**
     * 获取前几月/后几月的日期
     *
     * @param date   日期
     * @param months 月份（正加负减）
     * @author zhongyong 2019/11/29 10:33
     */
    public static String plusMonths(String date, Long months) {
        return toLocalDateTime(date).plusMonths(months).format(FormatEnum.FORMAT_DB.value);
    }

    public static String plusMonths(String date) {
        return plusMonths(date, 1L);
    }

    /**
     * 获取前几日/后几日的日期
     *
     * @param date 日期
     * @param days 天数（正加负减）
     * @author zhongyong 2019/11/29 10:34
     */
    public static String plusDays(String date, Long days) {
        return toLocalDateTime(date).plusDays(days).format(FormatEnum.FORMAT_DB.value);
    }

    public static String plusDays(String date) {
        return plusDays(date, 1L);
    }

    /**
     * 获取前几小时/后几小时的时间
     *
     * @param date  日期
     * @param hours 小时数（正加负减）
     * @author zhongyong 2019/11/29 10:34
     */
    public static String plusHours(String date, Long hours) {
        return toLocalDateTime(date).plusHours(hours).format(FormatEnum.FORMAT_DB.value);
    }

    /**
     * 获取前几分/后几分的时间
     *
     * @param date    日期
     * @param minutes 分钟数（正加负减）
     * @author zhongyong 2019/11/29 10:35
     */
    public static String plusMinutes(String date, Long minutes) {
        return toLocalDateTime(date).plusMinutes(minutes).format(FormatEnum.FORMAT_DB.value);
    }

    /**
     * 获取前几秒/后几秒的时间
     *
     * @param date    日期
     * @param seconds 秒数（正加负减）
     * @author zhongyong 2019/11/29 10:35
     */
    public static String plusSeconds(String date, Long seconds) {
        return toLocalDateTime(date).plusSeconds(seconds).format(FormatEnum.FORMAT_DB.value);
    }

    /**
     * 获取前几周/后几周的日期
     *
     * @param date  日期
     * @param weeks 周数（正加负减）
     * @author zhongyong 2019/11/29 10:35
     */
    public static String plusWeeks(String date, Long weeks) {
        return toLocalDateTime(date).plusWeeks(weeks).format(FormatEnum.FORMAT_DB.value);
    }

    public static String plusTime(String date, Long hours, Long minutes, Long seconds) {
        return toLocalDateTime(date).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds).format(FormatEnum.FORMAT_DB.value);
    }

//  public static void main(String[] args) {
//      System.out.println(plusTime(localDateTime().format(FormatEnum.FORMAT_DB.value), 3L, 36L, 10L));
//      System.out.println(plusTime(localDateTime().format(FormatEnum.FORMAT_DB.value), 0L, 0L, 0L));
//      System.out.println(localDateTime().format(FormatEnum.FORMAT_DB.value));
//      System.out.println(getDBDatetime);
//  }
    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 日期操作相关 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 当前时间相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * @author yinsiwei
     * @date 2020-08-13 12:03
     * 获取当前时间到当天0点的秒数
    */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    /**
     * @author yinsiwei
     * @date 2020-08-13 12:21
     * 获取当前时间到指定日期0点的秒数
    */
    public static Integer getRemainSecondsManyDay(Date currentDate,Date bespeakDay){
        Integer days = bespeakDay.getDate() - currentDate.getDate();
        return days*86400+getRemainSecondsOneDay(currentDate);
    }

    /**
     * 获取 LocalDate
     */
    public static LocalDate localDate() {
        return localDateTime().toLocalDate();
    }

    /**
     * 获取 LocalTime
     */
    public static LocalTime localTime() {
        return localDateTime().toLocalTime();
    }

    /**
     * 获取 LocalDateTime
     */
    public static LocalDateTime localDateTime() {
        return LocalDateTime.now();
    }
    /**
     * 获取 LocalDateTime
     */
    public static String localDateTime(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.now());
    }


    /**
     * 阅读数据库入库采用日期和时间（yyyyMMddHHmmss）
     */
    public static String getDBDatetime() {
        return FormatEnum.FORMAT_DB.value.format(localDateTime());
    }

    public static boolean isToday(Date time) {
        return LocalDate.from(time.toInstant()).equals(LocalDate.now());
    }

    public static void main(String[] args) {
        System.out.println(getDBDatetime());
    }
    /**
     * 超过10000数转成1.*万
     *
     * @return
     */
    public static String replcetime(int time) {
        String result = null;
        try {
            double d = time * 1.0 / 10000;
            result = String.format("%.2f", d);
        } catch (Exception e) {

        }
        return result;
    }


    /**
     * 时间格式化：使用相对时间，即刚刚、一分钟前、一小时前、一天前、一周前、一个月前、一年前
     *
     * @param date 原时间
     * @return
     * @author zhouqi
     */
    public static String formatrRelativetime(LocalDateTime date) {
        final long ONE_MINUTE = 1L;
        final long ONE_HOUR = 60L;
        final long ONE_DAY = 24L * ONE_HOUR;
        final long ONE_WEEK = 7L * ONE_DAY;
        final long ONE_MONTH = 30L * ONE_DAY;
        final long ONE_YEARS = 365L * ONE_DAY;

        final String ONE_SECOND_AGO = "刚刚";
        final String ONE_MINUTE_AGO = "分钟前";
        final String ONE_HOUR_AGO = "小时前";
        final String ONE_DAY_AGO = "天前";
        final String ONE_WEEK_AGO = "周前";
        final String ONE_MONTH_AGO = "个月前";
        final String ONE_YEAR_AGO = "一年前";

        long minutes = minutesInterVal(date);
        if (minutes < ONE_MINUTE) {
            return ONE_SECOND_AGO;
        } else if (minutes < 45L * ONE_MINUTE) {
            return minutes + ONE_MINUTE_AGO;
        } else if (minutes < ONE_DAY) {
            long hours = toHours(minutes);
            return (hours == 0L ? 1L : hours) + ONE_HOUR_AGO;
        } else if (minutes < ONE_WEEK) {
            return toDays(minutes) + ONE_DAY_AGO;
        } else if (minutes < ONE_MONTH) {
            return toWeeks(minutes) + ONE_WEEK_AGO;
        } else if (minutes < ONE_YEARS) {
            return toMonths(minutes) + ONE_MONTH_AGO;
        } else {
            return ONE_YEAR_AGO;
        }
    }


    private static long toHours(long minutes) {
        return BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 0, BigDecimal.ROUND_DOWN).longValue();
    }

    private static long toDays(long minutes) {
        return BigDecimal.valueOf(toHours(minutes)).divide(BigDecimal.valueOf(24), 0, BigDecimal.ROUND_DOWN).longValue();
    }

    private static long toWeeks(long minutes) {
        return BigDecimal.valueOf(toDays(minutes)).divide(BigDecimal.valueOf(7), 0, BigDecimal.ROUND_DOWN).longValue();
    }

    private static long toMonths(long minutes) {
        return BigDecimal.valueOf(toDays(minutes)).divide(BigDecimal.valueOf(30), 0, BigDecimal.ROUND_DOWN).longValue();
    }

    public static String transform(String datetimeStr, String originalFormatStr, String targetFormatStr) {
        if (null == datetimeStr) {
            return "";
        }
        if (datetimeStr.length() < originalFormatStr.length()) {
            datetimeStr = localDateTime(originalFormatStr);
        }
        int tmpStart = 0;
        Map<String, int[]> norms = new HashMap<>();
        if (targetFormatStr.contains("yyyy")) {
            tmpStart = originalFormatStr.indexOf("yyyy");
            norms.put("yyyy", new int[]{tmpStart, tmpStart + 4});
        }
        if (targetFormatStr.contains("MM")) {
            tmpStart = originalFormatStr.indexOf("MM");
            norms.put("MM", new int[]{tmpStart, tmpStart + 2});
        }
        if (targetFormatStr.contains("dd")) {
            tmpStart = originalFormatStr.indexOf("dd");
            norms.put("dd", new int[]{tmpStart, tmpStart + 2});
        }
        if (targetFormatStr.contains("HH")) {
            tmpStart = originalFormatStr.indexOf("HH");
            norms.put("HH", new int[]{tmpStart, tmpStart + 2});
        }
        if (targetFormatStr.contains("mm")) {
            tmpStart = originalFormatStr.indexOf("mm");
            norms.put("mm", new int[]{tmpStart, tmpStart + 2});
        }
        if (targetFormatStr.contains("ss")) {
            tmpStart = originalFormatStr.indexOf("ss");
            norms.put("ss", new int[]{tmpStart, tmpStart + 2});
        }
        for (String key : norms.keySet()) {
            targetFormatStr = targetFormatStr.replace(key, datetimeStr.substring(norms.get(key)[0], norms.get(key)[1]));
        }
        return targetFormatStr;
    }

    /**
     * 格式化日期时间字符串
     *
     * @param date
     * @param isBegin 是否起始,起始则补充000000,否则补充235959
     * @return
     * @author gavenbeyond
     * 2017年12月25日上午11:25:35
     */
    public static String format2Standard(String date, boolean isBegin) {
        if (StringUtils.isNotBlank(date) && date.length() == 8) {
            String suffix = "000000";
            if (!isBegin) {
                suffix = "235959";
            }
            date = FormatEnum.FORMAT_DB.value.format(toLocalDateTime(date+suffix));
        }
        return date;
    }

    /**
     * 是否在现在以前
     *
     * @param date 生效时间，时间为日期则补上000000
     * @return java.lang.Boolean
     * @author zhongyong 2019/11/29 16:54
     */
    public static Boolean isBefore(String date) {
        return isBefore(null, date);
    }

    /**
     * 是否在某个时间以前
     *
     * @param date 生效时间，时间为日期则补上000000
     * @return java.lang.Boolean
     * @author zhongyong 2020/3/24 16:59
     */
    public static Boolean isBefore(LocalDateTime dateMode, String date) {
        LocalDateTime localDateTime;
        if (StringUtil.isNotBlank(date)) {
            if (date.length() == 14) {
                localDateTime = toLocalDateTime(date);
            } else if (date.length() == 8) {
                localDateTime = toLocalDateTime(format2Standard(date, true));
            } else {
                return false;
            }
            if (dateMode!=null) {
                return minutesInterVal(localDateTime, dateMode) <= 0;
            } else {
                return minutesInterVal(localDateTime) <= 0;
            }
        }
        return false;
    }

    /**
     * 是否在现在之后
     *
     * @param date 失效时间，时间为日期则补上235959
     * @return java.lang.Boolean
     * @author zhongyong 2019/11/29 16:56
     */
    public static Boolean isAfter(String date) {
        return isAfter(null, date);
    }

    /**
     * 是否在某个时间之后
     *
     * @param date 失效时间，时间为日期则补上235959
     * @return java.lang.Boolean
     * @author zhongyong 2020/3/24 17:32
     */
    public static Boolean isAfter(LocalDateTime dateMode, String date) {
        LocalDateTime localDateTime;
        if (StringUtil.isNotBlank(date)) {
            if (date.length() == 14) {
                localDateTime = toLocalDateTime(date);
            } else if (date.length() == 8) {
                localDateTime = toLocalDateTime(format2Standard(date, false));
            } else {
                return false;
            }
            if (dateMode!=null) {
                return minutesInterVal(localDateTime, dateMode) >= 0;
            } else {
                return minutesInterVal(localDateTime) >= 0;
            }
        }
        return false;
    }

    /**
     * 判断是否为DB时间，yyyyMMddHHmmss or yyyyMMdd
     *
     * @param date
     * @return java.lang.Boolean
     * @author zhongyong 2020/3/24 16:29
     */
    public static Boolean isDBDatetime(String date) {
        try{
            if (StringUtil.isNotBlank(date)) {
                if (date.length() == 14) {
                    LocalDateTime.parse(date, FormatEnum.FORMAT_DB.value);
                } else if (date.length() == 8) {
                    LocalDateTime.parse(date, FormatEnum.FORMAT_DB_DATE.value);
                } else {
                    return false;
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.debug("isDBDatetime check error: ["+date+"] " + e.getMessage());
            return false;
        }
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd");
//        DateTimeFormatter formatterDatetime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//	    String updater = "20190917162249";
//	    String effecttime = "20190610";
//	    String expiretime = "20990610";
//
//        System.out.println(LocalDateTime.parse(updater, formatterDatetime).plusDays(10).format(formatterDatetime));
//
//        Duration timeDuration = Duration.between(LocalDateTime.now(),
//                LocalDateTime.of(2019, 11,13,14,3));
//		System.out.println(timeDuration.getNano());
//		System.out.println(timeDuration.getSeconds());
//		System.out.println(timeDuration.getUnits());
//
//		System.out.println(DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now()));
//		System.out.println(DateTimeFormatter.ISO_DATE.format(LocalDateTime.now()));
//		System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
//		System.out.println(DateTimeFormatter.BASIC_ISO_DATE.format(LocalDateTime.now()));
//    }




}
