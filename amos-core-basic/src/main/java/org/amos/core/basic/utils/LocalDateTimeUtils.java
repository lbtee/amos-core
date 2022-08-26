package org.amos.core.basic.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
/**
 * @desc: LocalDateTime 工具类 封装
 * @author: liubt
 * @date: 2020-12-31 13:21
 **/
public class LocalDateTimeUtils {
    /**
     * Date转换为 LocalDateTime
     *
     * @param date   date
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    /**
     * LocalDateTime转换为 Date
     *
     * @param time   time
     * @return {@link Date}
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取指定日期的毫秒
     *
     * @param time   time
     * @return {@link Long}
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    /**
     * 获取指定日期的秒
     *
     * @param time   time
     * @return {@link Long}
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }


    /**
     * 获取指定时间的指定格式
     *
     * @param time   time
     * @param pattern   pattern
     * @return {@link String}
     */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 获取当前时间的指定格式
     *
     * @param pattern   pattern
     * @return {@link String}
     */
    public static String formatNow(String pattern) {
        return formatTime(LocalDateTime.now(), pattern);
    }


    /**
     * 日期加上一个数,根据 field不同加不同值,field为 ChronoUnit.*
     *
     * @param time   time
     * @param number  number
     * @param field   field
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }


    /**
     * 日期减去一个数,根据 field不同减不同值,field参数为 ChronoUnit.*
     *
     * @param time   time
     * @param number   number
     * @param field   field
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }


    /**
     * 获取两个日期的差  field参数为 ChronoUnit.*
     *
     * @param startTime   startTime
     * @param endTime   endTime
     * @param field   field
     * @return {@link long}
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }


    /**
     * 获取一天的开始时间，2020,5,11 00:00
     *
     * @param time   time
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }


    /**
     * 获取一天的结束时间，2020,5,11 23:59:59.999999999
     *
     * @param time   time
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        return time.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }
}
