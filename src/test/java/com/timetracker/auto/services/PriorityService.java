package com.timetracker.auto.services;

import com.timetracker.auto.constans.EndPoints;
import com.timetracker.auto.pojo.Priority;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class PriorityService extends BaseService {
    @Step("Create priority")
    public static Response createPriority(Priority priority) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .body(priority)
                .post(EndPoints.ADD_PRIORITY.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Delete priority")
    public static Response deletePriority(int id) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .delete(EndPoints.DELETE_PRIORITY.getEndpoint() + id)
                .then()
                .extract()
                .response();
    }

    @Step("Get priority by id")
    public static Response getPriorityById(int id) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .get(EndPoints.GET_PRIORITY.getEndpoint() + id)
                .then()
                .extract()
                .response();
    }

    @Step("Get all priorities")
    public static Response getAllPriorities() {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .get(EndPoints.GET_ALL_PRIORITIES.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Search priority by title")
    public static Response searchPriorityByTitle(String title) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .body("{\"title\":\"" + title +"\"}")
                .post(EndPoints.SEARCH_PRIORITY.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Edit priority")
    public static Response updatePriority(Priority priority) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .body(priority)
                .put(EndPoints.UPDATE_PRIORITY.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Convert priority to id")
    public static int convertCreatePriorityToId(Response response) {
        response.then().statusCode(SC_OK);
        return response.jsonPath().getInt("id");
    }

    @Step("Create priorities list")
    public static List<Priority> getPrioritiesList() {
        Response response = getAllPriorities();
        response.then().assertThat().statusCode(SC_OK);
        return response.jsonPath().getList("$");
    }

    @Step("Get priority object")
    public static Priority getPriorityAsObject(Response response) {
        response.then().assertThat().statusCode(SC_OK);
        return Priority.builder()
                .id(response.jsonPath().getInt("id"))
                .title(response.jsonPath().getString("title"))
                .color(response.jsonPath().getString("color"))
                .build();
    }
}
