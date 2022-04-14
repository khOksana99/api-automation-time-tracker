package com.timetracker.auto.tests.categories;

import com.timetracker.auto.constans.EndPoints;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.utils.Utils;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.timetracker.auto.constans.ErrorMsg.idNotFound;
import static com.timetracker.auto.constans.JsonPath.*;
import static com.timetracker.auto.services.CategoryService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class GetCategoryTests {
    private int createdCategory;
    private final String TITLE = "title " + Utils.getCurrentMS();
    private final int INCORRECT_ID = 123546;

    @BeforeClass
    public void dataSetUp() {
        createdCategory = convertCreateCategoryToId(createCategory(Category.builder().title(TITLE).build()));
    }

    @Test(description = "[Positive] Get all categories")
    public void verifyGetAllCategoriesTest() {
        getAllCategories()
                .then()
                .statusCode(SC_OK)
                .body("$", not(hasKey(errorMsg)));

        Assert.assertFalse(getCategoriesList().isEmpty());
    }

    @Test(description = "[Positive] Get category by id")
    public void verifyGetCategoryByIdTest() {
        getCategoryById(createdCategory)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(id, is(createdCategory));
    }

    @Test(description = "[Negative] Get category by incorect id")
    public void verifyGetCategoryWithIncorrectIdTest() {
        getCategoryById(INCORRECT_ID)
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idNotFound));
    }

    @Test(description = "[Positive] Search category by title")
    public void verifySearchCategoryTest() {
        searchCategoryByTitle(TITLE)
                .then()
                .statusCode(SC_OK)
                .body(title, equalTo(TITLE));
    }
}
