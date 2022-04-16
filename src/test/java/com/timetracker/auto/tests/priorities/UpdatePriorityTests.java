package com.timetracker.auto.tests.priorities;

import com.timetracker.auto.constans.ConstantsLists;
import com.timetracker.auto.pojo.Priority;
import com.timetracker.auto.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.timetracker.auto.constans.ErrorMsg.*;
import static com.timetracker.auto.constans.JsonPath.*;
import static com.timetracker.auto.constans.JsonPath.title;
import static com.timetracker.auto.services.PriorityService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UpdatePriorityTests {
    private int firstCreatedPriority;
    private int secondCreatedPriority;
    private int thirdCreatedPriority;
    private final String START_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForPriorities());
    private final String EDITED_TITLE = "edited " + START_TITLE;
    private final String COLOR = Utils.getRandomElementFromList(ConstantsLists.getColorsList());
    private final String EDITED_COLOR = Utils.getRandomElementFromList(ConstantsLists.getColorsList());

    @BeforeClass
    public void dataSetUp() {
        firstCreatedPriority = convertCreatePriorityToId(createPriority(Priority.builder().title(START_TITLE).color(COLOR).build()));
        secondCreatedPriority = convertCreatePriorityToId(createPriority(Priority.builder().title(START_TITLE).color(COLOR).build()));
        thirdCreatedPriority = convertCreatePriorityToId(createPriority(Priority.builder().title(START_TITLE).color(COLOR).build()));
    }

    @Test(description = "[Positive] Update priority title")
    public void verifyUpdatePriorityTitleTest() {
        updatePriority(Priority.builder().id(firstCreatedPriority).title(EDITED_TITLE).color(COLOR).build())
                .then()
                .assertThat()
                .statusCode(SC_OK);

        getPriorityById(firstCreatedPriority)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(firstCreatedPriority))
                .body(title, equalTo(EDITED_TITLE));

    }

    @Test(description = "[Positive] Update priority title")
    public void verifyUpdatePriorityColorTest() {
        updatePriority(Priority.builder().id(firstCreatedPriority).title(START_TITLE).color(EDITED_COLOR).build())
                .then()
                .assertThat()
                .statusCode(SC_OK);

        getPriorityById(firstCreatedPriority)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(firstCreatedPriority));

    }

    @Test(description = "[Negative] Update priority without id provided")
    public void verifyUpdatePriorityWithoutIdTest() {
        updatePriority(Priority.builder().title(START_TITLE).color(EDITED_COLOR).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idIsRequiredField));

        getPriorityById(secondCreatedPriority)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(secondCreatedPriority))
                .body(title, equalTo(START_TITLE));
    }

    @Test(description = "[Negative] Update priority without title provided")
    public void verifyUpdatePriorityWithoutTitleTest() {
        updatePriority(Priority.builder().id(thirdCreatedPriority).color(COLOR).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(titleIsRequiredField));

        getPriorityById(thirdCreatedPriority)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(thirdCreatedPriority))
                .body(title, equalTo(START_TITLE));

    }

    @Test(description = "[Negative] Update priority without color provided")
    public void verifyUpdatePriorityWithoutColorTest() {
        updatePriority(Priority.builder().id(thirdCreatedPriority).title(EDITED_TITLE).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(colorIsRequiredField));

        getPriorityById(thirdCreatedPriority)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(thirdCreatedPriority))
                .body(title, equalTo(START_TITLE));

    }
}
