package com.sample.droider.legacyrecipeapp.api.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sample.droider.legacyrecipeapp.dto.Recipe;

import java.lang.reflect.Type;
import java.util.List;

public class RecipeParser implements ParseCallback<Recipe> {
    @Override
    public Recipe parse(String responseBody) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipe>>(){}.getType();
        List<Recipe> result = gson.fromJson(responseBody, type);
        return result.get(0);
    }
}
