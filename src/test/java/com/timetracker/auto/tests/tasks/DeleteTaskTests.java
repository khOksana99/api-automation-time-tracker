package com.timetracker.auto.tests.tasks;

import com.timetracker.auto.constans.ConstantsLists;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.pojo.Priority;
import com.timetracker.auto.pojo.Task;
import com.timetracker.auto.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static com.timetracker.auto.constans.ErrorMsg.idNotFound;
import static com.timetracker.auto.constans.JsonPath.errorMsg;
import static com.timetracker.auto.services.CategoryService.*;
import static com.timetracker.auto.services.PriorityService.*;
import static com.timetracker.auto.services.TaskService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class DeleteTaskTests {
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

    @Test(description = "[Positive] Delete task")
    public void deleteTaskTest() {
        int startTasksCount = getTasksList().size();

        deleteTask(createdTask)
                .then()
                .assertThat()
                .statusCode(SC_OK);

        int tasksCountAfterDelete = getTasksList().size();

        Assert.assertTrue(startTasksCount > tasksCountAfterDelete);
    }

    @Test(description = "[Negative]Create task with incorrect data(set id)")
    public void verifyDeleteTaskWithIncorrectDataTest() {
        int startTasksCount = getTasksList().size();

        deleteTask(INCORRECT_ID)
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idNotFound));

        int tasksCountAfterDelete = getTasksList().size();

        Assert.assertTrue(startTasksCount == tasksCountAfterDelete);
    }
}
