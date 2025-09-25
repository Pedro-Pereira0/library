package com.project.psoft.lendingmanagement.utils;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthConverter {
    public static String getMonthName(int month) {

        Month monthEnum = Month.of(month);
        return monthEnum.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
