package com.timetracker.auto.tests.priorities;

import com.timetracker.auto.pojo.Priority;
import com.timetracker.auto.utils.Utils;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.timetracker.auto.constans.ErrorMsg.idNotFound;
import static com.timetracker.auto.constans.JsonPath.*;
import static com.timetracker.auto.services.PriorityService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class GetPriorityTests {
    private int createdPriority;
    private final String TITLE = "title " + Utils.getCurrentMS();
    private final int INCORRECT_ID = 123546;
    private final String color = "#f05f5f";

    @BeforeClass
    public void dataSetUp() {
        createdPriority = convertCreatePriorityToId(createPriority(Priority.builder().title(TITLE).color(color).build()));
    }

    @Test(description = "[Positive] Get all priorities")
    public void verifyGetAllPrioritiesTest() {
        getAllPriorities()
                .then()
                .statusCode(SC_OK)
                .body("$", not(hasKey(errorMsg)));

        Assert.assertFalse(getPrioritiesList().isEmpty());
    }

    @Test(description = "[Positive] Get priority by id")
    public void verifyGetPriorityByIdTest() {
        getPriorityById(createdPriority)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(createdPriority));
    }

    @Test(description = "[Negative] Get priority by incorect id")
    public void verifyGetCategoryWithIncorrectIdTest() {
        getPriorityById(INCORRECT_ID)
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idNotFound));
    }

    @Test(description = "[Positive] Search priority by title")
    public void verifySearchCategoryTest() {
        searchPriorityByTitle(TITLE)
                .then()
                .statusCode(SC_OK)
                .body(title, equalTo(TITLE));
    }
}
