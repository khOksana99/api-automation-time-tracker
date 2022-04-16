package com.timetracker.auto.tests.tasks;

import com.timetracker.auto.constans.ConstantsLists;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.pojo.Priority;
import com.timetracker.auto.pojo.Task;
import com.timetracker.auto.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static com.timetracker.auto.constans.ErrorMsg.*;
import static com.timetracker.auto.constans.JsonPath.errorMsg;
import static com.timetracker.auto.constans.JsonPath.title;
import static com.timetracker.auto.services.CategoryService.*;
import static com.timetracker.auto.services.PriorityService.*;
import static com.timetracker.auto.services.TaskService.createTask;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class CreateTaskTests {
    private Priority createdPriority;
    private Category createdCategory;
    private final String TASK_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForTasks());
    private final String PRIORITY_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForPriorities());
    private final String CATEGORY_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForCategories());
    private final int INCORRECT_ID = Utils.getRandomInteger();
    private final String COLOR = Utils.getRandomElementFromList(ConstantsLists.getColorsList());
    private final Date deadLine = new Date();

    @BeforeClass
    public void dataSetUp() {
        createdPriority = getPriorityAsObject(createPriority(Priority.builder().title(PRIORITY_TITLE).color(COLOR).build()));
        createdCategory = getCategoryAsObject(createCategory(Category.builder().title(CATEGORY_TITLE).build()));
    }

    @Test(description = "[Positive]Create task with correct data")
    public void verifyCreateTaskWithCorrectDataTest() {
        createTask(Task.builder().category(createdCategory).priority(createdPriority).title(TASK_TITLE).date(deadLine).build())
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(title, equalTo(TASK_TITLE));
    }

    @Test(description = "[Negative]Create priority with incorrect data(set id)")
    public void verifyCreateTaskWithIncorrectDataTest() {
        createTask(Task.builder().id(INCORRECT_ID).category(createdCategory).priority(createdPriority).title(TASK_TITLE).date(deadLine).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idIsForbidden));
    }

    @Test(description = "[Negative]Create priority with incorrect data(empty title)")
    public void verifyCreatePriorityWithEmptyTitleTest() {
        createTask(Task.builder().category(createdCategory).priority(createdPriority).title("").date(deadLine).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(titleIsRequiredField));
    }
}
