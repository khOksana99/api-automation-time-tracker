package com.timetracker.auto.tests.tasks;

import com.timetracker.auto.constans.ConstantsLists;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.pojo.Priority;
import com.timetracker.auto.pojo.Task;
import com.timetracker.auto.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;

import static com.timetracker.auto.constans.ErrorMsg.idIsRequiredField;
import static com.timetracker.auto.constans.ErrorMsg.titleIsRequiredField;
import static com.timetracker.auto.constans.JsonPath.*;
import static com.timetracker.auto.services.CategoryService.createCategory;
import static com.timetracker.auto.services.CategoryService.getCategoryAsObject;
import static com.timetracker.auto.services.PriorityService.*;
import static com.timetracker.auto.services.TaskService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class EditTask {
    private Priority createdPriority;
    private Category createdCategory;
    private Priority editedPriority;
    private Category editedCategory;
    private int createdTask;
    private final String TASK_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForTasks());
    private final String PRIORITY_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForPriorities());
    private final String CATEGORY_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForCategories());
    private final String EDITED_TITLE = "edited " + TASK_TITLE;
    private final String COLOR = Utils.getRandomElementFromList(ConstantsLists.getColorsList());
    private final Date EDITED_DATE = Utils.getFutureDate();
    private final Date DEADLINE = new Date();

    @BeforeClass
    public void dataSetUp() {
        createdPriority = getPriorityAsObject(createPriority(Priority.builder().title(PRIORITY_TITLE).color(COLOR).build()));
        createdCategory = getCategoryAsObject(createCategory(Category.builder().title(CATEGORY_TITLE).build()));
        editedPriority = getPriorityAsObject(createPriority(Priority.builder().title(EDITED_TITLE).color(COLOR).build()));
        editedCategory = getCategoryAsObject(createCategory(Category.builder().title(EDITED_TITLE).build()));
        createdTask = convertCreateTaskToId(createTask(Task.builder().category(createdCategory).priority(createdPriority).title(TASK_TITLE).date(DEADLINE).build()));
    }

    @Test(description = "Change task priority")
    public void verifyChangeTaskPriorityTest() {
        updateTask(Task.builder().title(TASK_TITLE).id(createdTask).priority(editedPriority).build())
                .then()
                .assertThat()
                .statusCode(SC_OK);

        getTaskById(createdTask)
                .then()
                .body(priorityId, is(editedPriority.getId()))
                .body(priorityTitle, equalTo(editedPriority.getTitle()));
    }

    @Test(description = "Change task category")
    public void verifyChangeTaskCategoryTest() {
        updateTask(Task.builder().title(TASK_TITLE).id(createdTask).category(editedCategory).build())
                .then()
                .assertThat()
                .statusCode(SC_OK);

        getTaskById(createdTask)
                .then()
                .body(categoryId, is(editedCategory.getId()))
                .body(categoryTitle, equalTo(editedCategory.getTitle()));
    }

    @Test(description = "Change task date")
    public void verifyChangeTaskDateTest() {
        updateTask(Task.builder().title(TASK_TITLE).id(createdTask).date(EDITED_DATE).build())
                .then()
                .assertThat()
                .statusCode(SC_OK);

        getTaskById(createdTask)
                .then()
                .body("date", equalTo(EDITED_DATE));

    }

    @Test(description = "Change task category")
    public void verifyChangeTaskTitleTest() {
        updateTask(Task.builder().title(EDITED_TITLE).id(createdTask).build())
                .then()
                .assertThat()
                .statusCode(SC_OK);

        getTaskById(createdTask)
                .then()
                .body(title, equalTo(EDITED_TITLE));
    }

    @Test(description = "[Negative] Update task without id provided")
    public void verifyUpdateTaskWithoutIdTest() {
        updateTask(Task.builder().title(EDITED_TITLE).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idIsRequiredField));
    }

    @Test(description = "[Negative] Update task without title provided")
    public void verifyUpdateTaskWithoutTitleTest() {
        updatePriority(Priority.builder().id(createdTask).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(titleIsRequiredField));

    }
}
