package com.sample.droider.legacyrecipeapp.api.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sample.droider.legacyrecipeapp.dto.Recipe;

import java.lang.reflect.Type;
import java.util.List;

public class RecipeListParser implements ParseCallback<List<Recipe>> {

    @Override
    public List<Recipe> parse(String responseBody) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>(){}.getType();
        return gson.fromJson(responseBody, type);
    }
}
