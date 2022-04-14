package com.timetracker.auto.tests.priorities;

import com.timetracker.auto.pojo.Priority;
import com.timetracker.auto.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.timetracker.auto.constans.ErrorMsg.idNotFound;
import static com.timetracker.auto.constans.JsonPath.errorMsg;
import static com.timetracker.auto.services.CategoryService.*;
import static com.timetracker.auto.services.PriorityService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class DeletePriorityTests {
    private int createdPriority;
    private final String TITLE = "title " + Utils.getCurrentMS();
    private final int INCORRECT_ID = 123546;
    private final String color = "#f05f5f";

    @BeforeClass
    public void dataSetUp() {
        createdPriority = convertCreatePriorityToId(createPriority(Priority.builder().title(TITLE).color(color).build()));
    }

    @Test(description = "[Positive] Delete priority")
    public void deletePriorityTest() {
        int startPrioritiesCount = getPrioritiesList().size();

        deletePriority(createdPriority)
                .then()
                .assertThat()
                .statusCode(SC_OK);

        int prioritiesCountAfterDelete = getPrioritiesList().size();

        Assert.assertTrue(startPrioritiesCount > prioritiesCountAfterDelete);
    }

    @Test(description = "[Negative]Create priority with incorrect data(set id)")
    public void verifyDeletePriorityWithIncorrectDataTest() {
        int startPrioritiesCount = getPrioritiesList().size();

        deleteCategory(INCORRECT_ID)
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idNotFound));

        int prioritiesCountAfterDelete = getPrioritiesList().size();

        Assert.assertTrue(startPrioritiesCount == prioritiesCountAfterDelete);
    }
}
