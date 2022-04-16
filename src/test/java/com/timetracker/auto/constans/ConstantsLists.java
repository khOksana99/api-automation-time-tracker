package com.timetracker.auto.constans;

import java.util.Arrays;
import java.util.List;

public class ConstantsLists {
    public static List<String> getNamesListForPriorities() {
        return Arrays.asList("Low", "Medium", "High", "Urgent");
    }

    public static List<String> getNamesListForCategories() {
        return Arrays.asList("Relax", "Work", "Hobby", "Home", "Friends");
    }

    public static List<String> getNamesListForTasks() {
        return Arrays.asList("Go to the gym", "Make some cake", "Send report", "Read a book", "Go to the cinema");
    }

    public static List<String> getColorsList() {
        return Arrays.asList("#f05f5f", "#8bf05f", "#7e5ff0", "#cdf05f", "#f0e75f");
    }
}
