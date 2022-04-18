package com.timetracker.auto.services;

import com.timetracker.auto.constans.EndPoints;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.pojo.Task;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class TaskService extends BaseService {
    @Step("Create task")
    public static Response createTask(Task task) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when().log().all()
                .body(task)
                .post(EndPoints.ADD_TASK.getEndpoint())
                .then().log().all()
                .extract()
                .response();
    }

    @Step("Delete task")
    public static Response deleteTask(int id) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .delete(EndPoints.DELETE_TASK.getEndpoint() + id)
                .then()
                .extract()
                .response();
    }

    @Step("Get task by id")
    public static Response getTaskById(int id) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when().log().all()
                .get(EndPoints.GET_TASK.getEndpoint() + id)
                .then().log().all()
                .extract()
                .response();
    }

    @Step("Get all tasks")
    public static Response getAllTasks() {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .get(EndPoints.GET_ALL_TASKS.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Search task by title")
    public static Response searchTaskByTitle(String title) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when().log().all()
                .body("{\"title\":\"" + title +"\"}")
                .post(EndPoints.SEARCH_TASK.getEndpoint())
                .then().log().all()
                .extract()
                .response();
    }

    @Step("Edit task")
    public static Response updateTask(Task task) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .body(task)
                .put(EndPoints.UPDATE_TASK.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Convert task to id")
    public static int convertCreateTaskToId(Response response) {
        response.then().statusCode(SC_OK);
        return response.jsonPath().getInt("id");
    }

    @Step("Get tasks list")
    public static List<Category> getTasksList() {
        Response response = getAllTasks();
        response.then().assertThat().statusCode(SC_OK);
        return response.jsonPath().getList("$");
    }
}
