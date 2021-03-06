package com.timetracker.auto.services;

import com.timetracker.auto.constans.EndPoints;
import com.timetracker.auto.pojo.Category;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CategoryService extends BaseService {
    @Step("Create category")
    public static Response createCategory(Category category) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .body(category)
                .post(EndPoints.ADD_CATEGORY.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Delete category")
    public static Response deleteCategory(int id) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .delete(EndPoints.DELETE_CATEGORY.getEndpoint() + id)
                .then()
                .extract()
                .response();
    }

    @Step("Get category by id")
    public static Response getCategoryById(int id) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when().log().all()
                .get(EndPoints.GET_CATEGORY.getEndpoint() + id)
                .then().log().all()
                .extract()
                .response();
    }

    @Step("Get all categories")
    public static Response getAllCategories() {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .get(EndPoints.GET_ALL_CATEGORIES.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Search category by title")
    public static Response searchCategoryByTitle(String title) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when().log().all()
                .body("{\"title\":\"" + title +"\"}")
                .post(EndPoints.SEARCH_CATEGORY.getEndpoint())
                .then().log().all()
                .extract()
                .response();
    }

    @Step("Edit category")
    public static Response updateCategory(Category category) {
        return given()
                .spec(getRequestSpec(BASE_URI))
                .when()
                .body(category)
                .put(EndPoints.UPDATE_CATEGORY.getEndpoint())
                .then()
                .extract()
                .response();
    }

    @Step("Convert category to id")
    public static int convertCreateCategoryToId(Response response) {
        response.then().statusCode(SC_OK);
        return response.jsonPath().getInt("id");
    }

    @Step("Get list of categories")
    public static List<Category> getCategoriesList() {
        Response response = getAllCategories();
        response.then().assertThat().statusCode(SC_OK);
        return response.jsonPath().getList("$");
    }

    @Step("Get category object")
    public static Category getCategoryAsObject(Response response) {
        response.then().assertThat().statusCode(SC_OK);
        return Category.builder()
                .id(response.jsonPath().getInt("id"))
                .title(response.jsonPath().getString("title"))
                .build();
    }
}
