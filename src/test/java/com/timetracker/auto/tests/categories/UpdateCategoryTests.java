package com.timetracker.auto.tests.categories;

import com.timetracker.auto.constans.ConstantsLists;
import com.timetracker.auto.constans.EndPoints;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.utils.Utils;
import org.testng.annotations.*;

import static com.timetracker.auto.constans.ErrorMsg.idIsRequiredField;
import static com.timetracker.auto.constans.ErrorMsg.titleIsRequiredField;
import static com.timetracker.auto.constans.JsonPath.*;
import static com.timetracker.auto.services.CategoryService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UpdateCategoryTests {
    private int firstCreatedCategory;
    private int secondCreatedCategory;
    private int thirdCreatedCategory;
    private final String START_TITLE = Utils.getRandomElementFromList(ConstantsLists.getNamesListForCategories());
    private final String EDITED_TITLE = "edited " + START_TITLE;

    @BeforeClass
    public void dataSetUp() {
        firstCreatedCategory = convertCreateCategoryToId(createCategory(Category.builder().title(START_TITLE).build()));
        secondCreatedCategory = convertCreateCategoryToId(createCategory(Category.builder().title(START_TITLE).build()));
        thirdCreatedCategory = convertCreateCategoryToId(createCategory(Category.builder().title(START_TITLE).build()));
    }

    @Test(description = "[Positive] Update category title")
    public void verifyUpdateCategoryTest() {
        updateCategory(Category.builder().id(firstCreatedCategory).title(EDITED_TITLE).build())
                .then()
                .assertThat()
                .statusCode(SC_OK);

        getCategoryById(firstCreatedCategory)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(firstCreatedCategory))
                .body(title, equalTo(EDITED_TITLE));

    }

    @Test(description = "[Negative] Update category without id provided")
    public void verifyUpdateCategoryWithoutIdTest() {
        updateCategory(Category.builder().title(EDITED_TITLE).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                        .body(errorMsg, equalTo(idIsRequiredField));

        getCategoryById(secondCreatedCategory)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(secondCreatedCategory))
                .body(title, equalTo(START_TITLE));

    }

    @Test(description = "[Negative] Update category without title provided")
    public void verifyUpdateCategoryWithoutTitleTest() {
        updateCategory(Category.builder().id(thirdCreatedCategory).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(titleIsRequiredField));

        getCategoryById(thirdCreatedCategory)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(thirdCreatedCategory))
                .body(title, equalTo(START_TITLE));

    }

    @AfterClass(alwaysRun = true)
    public void dataCleanUp() {
        deleteCategory(firstCreatedCategory);
    }
}
