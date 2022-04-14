package com.timetracker.auto.tests.priorities;

import com.timetracker.auto.pojo.Priority;
import org.testng.annotations.Test;

import static com.timetracker.auto.constans.ErrorMsg.*;
import static com.timetracker.auto.constans.JsonPath.errorMsg;
import static com.timetracker.auto.constans.JsonPath.title;
import static com.timetracker.auto.services.PriorityService.createPriority;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class CreatePriorityTests {
    private final String priorityTitle = "New priority";
    private final String color = "#f05f5f";
    private int incorrect_id = 123456;

    @Test(description = "[Positive]Create priority with correct data")
    public void verifyCreatePriorityWithCorrectDataTest() {
        createPriority(Priority.builder().title(priorityTitle).color(color).build())
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(title, equalTo(priorityTitle));
    }

    @Test(description = "[Negative]Create priority with incorrect data(set id)")
    public void verifyCreatePriorityWithIncorrectDataTest() {
        createPriority(Priority.builder().title(priorityTitle).color(color).id(incorrect_id).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idIsForbidden));
    }

    @Test(description = "[Negative]Create priority with incorrect data(without color)")
    public void verifyCreatePriorityWithoutColorTest() {
        createPriority(Priority.builder().title(priorityTitle).color("").build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(colorIsRequiredField));
    }

    @Test(description = "[Negative]Create priority with incorrect data(set id)")
    public void verifyCreatePriorityWithEmptyTitleTest() {
        createPriority(Priority.builder().title("").color(color).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(titleIsRequiredField));
    }

}
