package com.hab.blog.common.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtil {

    private static final String[] WEEK_DAYS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static String getDayOfWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return WEEK_DAYS[dayOfWeek.getValue() % 7];
    }
}
