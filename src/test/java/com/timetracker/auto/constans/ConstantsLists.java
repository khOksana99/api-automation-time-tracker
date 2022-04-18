package com.timetracker.auto.constans;

import io.qameta.allure.Step;

import java.util.Arrays;
import java.util.List;

public class ConstantsLists {
    @Step("Get priorities name")
    public static List<String> getNamesListForPriorities() {
        return Arrays.asList("Low", "Medium", "High", "Urgent");
    }

    @Step("Get categories name")
    public static List<String> getNamesListForCategories() {
        return Arrays.asList("Relax", "Work", "Hobby", "Home", "Friends");
    }

    @Step("Get tasks name")
    public static List<String> getNamesListForTasks() {
        return Arrays.asList("Go to the gym", "Make some cake", "Send report", "Read a book", "Go to the cinema");
    }

    @Step("Get colors")
    public static List<String> getColorsList() {
        return Arrays.asList("#f05f5f", "#8bf05f", "#7e5ff0", "#cdf05f", "#f0e75f");
    }
}
