package com.timetracker.auto.tests;

import com.timetracker.auto.constans.EndPoints;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.timetracker.auto.constans.ErrorMsg.idNotFound;
import static com.timetracker.auto.constans.JsonPath.errorMsg;
import static com.timetracker.auto.services.CategoryService.*;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class DeleteCategoryTests {
    private int createdCategory;
    private final String TITLE = "title " + Utils.getCurrentMS();
    private final int INCORRECT_ID = 123546;

    @BeforeClass
    public void dataSetUp() {
        createdCategory = convertCreateCategoryToId(createCategory(Category.builder().title(TITLE).build()));
    }

    @Test(description = "[Positive] Delete category")
    public void deleteCategoryTest() {
        int startCategoriesCount = getCategoriesList().size();

        deleteCategory(createdCategory)
                .then()
                .assertThat()
                .statusCode(SC_OK);

        int categoriesCountAfterDelete = getCategoriesList().size();

        Assert.assertTrue(startCategoriesCount > categoriesCountAfterDelete);
    }

    @Test(description = "[Negative]Create category with incorrect data(set id)")
    public void verifyDeleteCategoryWithIncorrectDataTest() {
        int startCategoriesCount = getCategoriesList().size();

        deleteCategory(INCORRECT_ID)
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idNotFound));

        int categoriesCountAfterDelete = getCategoriesList().size();

        Assert.assertTrue(startCategoriesCount == categoriesCountAfterDelete);
    }
}