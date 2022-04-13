package com.timetracker.auto.utils;

import java.util.Calendar;

public class Utils {
    public static Long getCurrentMS() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
