package com.timetracker.auto.tests.tasks;

import com.timetracker.auto.constans.ConstantsLists;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.pojo.Priority;
import com.timetracker.auto.pojo.Task;
import com.timetracker.auto.utils.Utils;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static com.timetracker.auto.constans.ErrorMsg.idNotFound;
import static com.timetracker.auto.constans.JsonPath.*;
import static com.timetracker.auto.services.CategoryService.createCategory;
import static com.timetracker.auto.services.CategoryService.getCategoryAsObject;
import static com.timetracker.auto.services.PriorityService.*;
import static com.timetracker.auto.services.TaskService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class GetTaskTests {
    private Priority createdPriority;
    private Category createdCategory;
    private int createdTask;
    private final String TASK_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForTasks());
    private final String PRIORITY_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForPriorities());
    private final String CATEGORY_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForCategories());
    private final int INCORRECT_ID = Utils.getRandomInteger();
    private final String COLOR = Utils.getRandomElementFromList(ConstantsLists.getColorsList());
    private final Date DEADLINE = new Date();

    @BeforeClass
    public void dataSetUp() {
        createdPriority = getPriorityAsObject(createPriority(Priority.builder().title(PRIORITY_TITLE).color(COLOR).build()));
        createdCategory = getCategoryAsObject(createCategory(Category.builder().title(CATEGORY_TITLE).build()));
        createdTask = convertCreateTaskToId(createTask(Task.builder().category(createdCategory).priority(createdPriority).title(TASK_TITLE).date(DEADLINE).build()));
    }

    @Test(description = "[Positive] Get all tasks")
    public void verifyGetAllTasksTest() {
        getAllTasks()
                .then()
                .statusCode(SC_OK)
                .body("$", not(hasKey(errorMsg)));

        Assert.assertFalse(getTasksList().isEmpty());
    }

    @Test(description = "[Positive] Get task by id")
    public void verifyGetTaskByIdTest() {
        getTaskById(createdTask)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(createdTask));
    }

    @Test(description = "[Negative] Get task by incorect id")
    public void verifyGetCTaskWithIncorrectIdTest() {
        getTaskById(INCORRECT_ID)
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idNotFound));
    }

    @Test(description = "[Positive] Search task by title")
    public void verifySearchTaskTest() {
        searchTaskByTitle(TASK_TITLE)
                .then()
                .statusCode(SC_OK)
                .body(title, equalTo(TASK_TITLE));
    }
}
