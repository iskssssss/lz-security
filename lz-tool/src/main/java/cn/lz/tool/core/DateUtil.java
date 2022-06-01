package cn.lz.tool.core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Version 版权 Copyright(c)2021 LZ
 * @ClassName:
 * @Descripton:
 * @Author: 孔胜
 * @Date: 2021/6/24 10:50
 */
public final class DateUtil {

    /**
     * 日期转换
     */
    public static LocalDateTime parse(
            String dateTimeStr
    ) {
        dateTimeStr = dateTimeStr.replace(".000000", "");
        LocalDateTime localDateTime = cn.hutool.core.date.DateUtil.parse(dateTimeStr).toTimestamp().toLocalDateTime();
        if (localDateTime == null) {
            localDateTime = DateUtil.parse(dateTimeStr, "yyyy年MM月dd日");
        }
        if (localDateTime == null) {
            localDateTime = DateUtil.parse(dateTimeStr, "yyyy-MM-dd HH:mm:ss");
        }
        if (localDateTime == null) {
            localDateTime = DateUtil.parse(dateTimeStr, "yyyy/MM/dd HH:mm:ss");
        }
        return localDateTime;
    }

    /**
     * 日期转换
     */
    public static LocalDateTime parse(
            String dateTimeStr,
            String pattern
    ) {
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算时间差 毫秒
     *
     * @param oldTime
     * @param newTime
     * @return
     */
    public static long betweenByMillis(
            LocalDateTime oldTime,
            LocalDateTime newTime
    ) {
        Duration duration = Duration.between(newTime, oldTime);
        return duration.toMillis();
    }

    public static Date now() {
        return new Date();
    }

}
