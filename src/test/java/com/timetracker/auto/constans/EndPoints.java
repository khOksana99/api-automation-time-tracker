package com.timetracker.auto.constans;

public enum EndPoints {
    ///region category
    ADD_CATEGORY("/category/add"),
    UPDATE_CATEGORY("/category/update"),
    GET_CATEGORY("/category/id"),
    GET_ALL_CATEGORIES("/category/all"),
    DELETE_CATEGORY("/category/delete"),
    SEARCH_CATEGORY("/category/search"),
    ///endregion

    ///region task
    ADD_TASK("/task/add"),
    UPDATE_TASK("/task/update"),
    GET_TASK("/task/id"),
    GET_ALL_TASKS("/task/all"),
    DELETE_TASK("/task/delete"),
    SEARCH_TASK("/task/search"),
    ///endregion

    ///region priority
    ADD_PRIORITY("/priority/add"),
    UPDATE_PRIORITY("/priority/update"),
    GET_PRIORITY("/priority/id"),
    GET_ALL_PRIORITIES("/priority/all"),
    DELETE_PRIORITY("/priority/delete"),
    SEARCH_PRIORITY("/priority/search");
    ///endregion

    private final String endpoint;

    EndPoints(String endpoint){this.endpoint = endpoint;}

    public String getEndpoint() {return endpoint;}
}
