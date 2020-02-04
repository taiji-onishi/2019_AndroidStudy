package com.sample.droider.legacyrecipeapp.dto;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class RecipeDataValidationTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void isEmptyMainImageUrlValidattion() {
        RecipeDataValidation recipeDataValidation = new RecipeDataValidation();
        Assertions.assertThat(recipeDataValidation.isMainImageUrlValidate(""))
                .isFalse();
    }

    @Test
    public void isNullMainImageUrlValidattion() {
        RecipeDataValidation recipeDataValidation = new RecipeDataValidation();
        Assertions.assertThat(recipeDataValidation.isMainImageUrlValidate(null))
                .isFalse();
    }

    @Test
    public void isNotHttpSchemeMainImageUrlValidation() {
        RecipeDataValidation recipeDataValidation = new RecipeDataValidation();
        Assertions.assertThat(
                recipeDataValidation.isMainImageUrlValidate("123")
                        && recipeDataValidation.isMainImageUrlValidate("http://www.co.jp")
                        && recipeDataValidation.isMainImageUrlValidate("ht://")
        ).isFalse();
    }


    @Test
    public void isValidateMainImageUrlValidation() {
        RecipeDataValidation recipeDataValidation = new RecipeDataValidation();
        Assertions.assertThat(
                recipeDataValidation.isMainImageUrlValidate("https://developer.android.com/training/testing/fundamentals?hl=ja")
        ).isTrue();
    }
}
