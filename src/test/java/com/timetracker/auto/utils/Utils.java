package com.timetracker.auto.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Utils {
    private static Random r = new Random();

    public static Long getCurrentMS() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static Date getFutureDate() {
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        return  new Date(timeInSecs + (10 * 60 * 1000));
    }

    public static String getRandomElementFromList(List<String> list) {
        return list.get(r.nextInt(list.size()));
    }

    public static int getRandomInteger() {
        return 50 + (int)(Math.random() * ((1000 - 50) + 1));
    }
}
