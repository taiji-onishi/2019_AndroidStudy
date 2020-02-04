package com.sample.droider.legacyrecipeapp.dto;



public class RecipeDataValidation {


    public boolean isMainImageUrlValidate(String str){
        return !isEmpty(str) && str.startsWith("https://") ;
    }

    private boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }
}
