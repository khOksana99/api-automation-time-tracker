package com.timetracker.auto.tests.priorities;

import com.timetracker.auto.constans.ConstantsLists;
import com.timetracker.auto.pojo.Priority;
import com.timetracker.auto.utils.Utils;
import org.testng.annotations.Test;

import static com.timetracker.auto.constans.ErrorMsg.*;
import static com.timetracker.auto.constans.JsonPath.errorMsg;
import static com.timetracker.auto.constans.JsonPath.title;
import static com.timetracker.auto.services.PriorityService.createPriority;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class CreatePriorityTests {
    private final String PRIORITY_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForPriorities());
    private final String COLOR = Utils.getRandomElementFromList(ConstantsLists.getColorsList());
    private final int INCORRECT_ID = Utils.getRandomInteger();

    @Test(description = "[Positive]Create priority with correct data")
    public void verifyCreatePriorityWithCorrectDataTest() {
        createPriority(Priority.builder().title(PRIORITY_TITLE).color(COLOR).build())
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(title, equalTo(PRIORITY_TITLE));
    }

    @Test(description = "[Negative]Create priority with incorrect data(set id)")
    public void verifyCreatePriorityWithIncorrectDataTest() {
        createPriority(Priority.builder().title(PRIORITY_TITLE).color(COLOR).id(INCORRECT_ID).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idIsForbidden));
    }

    @Test(description = "[Negative]Create priority with incorrect data(without color)")
    public void verifyCreatePriorityWithoutColorTest() {
        createPriority(Priority.builder().title(PRIORITY_TITLE).color("").build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(colorIsRequiredField));
    }

    @Test(description = "[Negative]Create priority with incorrect data(set id)")
    public void verifyCreatePriorityWithEmptyTitleTest() {
        createPriority(Priority.builder().title("").color(COLOR).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(titleIsRequiredField));
    }

}
