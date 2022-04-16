package com.timetracker.auto.tests.categories;

import com.timetracker.auto.constans.ConstantsLists;
import com.timetracker.auto.pojo.Category;
import com.timetracker.auto.utils.Utils;
import org.testng.annotations.Test;

import static com.timetracker.auto.constans.ErrorMsg.idIsForbidden;
import static com.timetracker.auto.constans.ErrorMsg.titleIsRequiredField;
import static com.timetracker.auto.constans.JsonPath.errorMsg;
import static com.timetracker.auto.constans.JsonPath.title;
import static com.timetracker.auto.services.CategoryService.createCategory;
import static org.apache.http.HttpStatus.SC_NOT_ACCEPTABLE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class CreateCategoryTests {
    private final String categoryTitle = Utils.getRandomElementFromList(ConstantsLists.getNamesListForCategories());

    @Test(description = "[Positive]Create category with correct data")
    public void verifyCreateCategoryWithCorrectDataTest() {
        createCategory(Category.builder().title(categoryTitle).build())
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(title, equalTo(categoryTitle));
    }

    @Test(description = "[Negative]Create category with incorrect data(set id)")
    public void verifyCreateCategoryWithIncorrectDataTest() {
        createCategory(Category.builder().title(categoryTitle).id(15).build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(idIsForbidden));
    }

    @Test(description = "[Negative]Create category with incorrect data(set id)")
    public void verifyCreateCategoryWithEmptyTitleTest() {
        createCategory(Category.builder().title("").build())
                .then()
                .assertThat()
                .statusCode(SC_NOT_ACCEPTABLE)
                .body(errorMsg, equalTo(titleIsRequiredField));
    }

}
