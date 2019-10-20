package com.sample.droider.legacyrecipeapp.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    @SerializedName("cooking_ingredients")
    private List<CookingIngredients> cookingIngredients;
    @SerializedName("cooking_method")
    private List<CookingMethod> cookingMethods;
    @SerializedName("genre_cd")
    private String genreCd;
    @SerializedName("genre_name")
    private String genreName;
    @SerializedName("recipe_id")
    private String recipeId;
    @SerializedName("recipe_name")
    private String recipeName;
    private String introduction;
    @SerializedName("main_gazo")
    private String mainImageUrl;
    @SerializedName("recommended_flg")
    private String reccomendFlg;

    public List<CookingIngredients> getCookingIngredients() {
        return cookingIngredients;
    }

    public void setCookingIngredients(List<CookingIngredients> cookingIngredients) {
        this.cookingIngredients = cookingIngredients;
    }

    public List<CookingMethod> getCookingMethods() {
        return cookingMethods;
    }

    public void setCookingMethods(List<CookingMethod> cookingMethods) {
        this.cookingMethods = cookingMethods;
    }

    public String getGenreCd() {
        return genreCd;
    }

    public void setGenreCd(String genreCd) {
        this.genreCd = genreCd;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getReccomendFlg() {
        return reccomendFlg;
    }

    public void setReccomendFlg(String reccomendFlg) {
        this.reccomendFlg = reccomendFlg;
    }
}
