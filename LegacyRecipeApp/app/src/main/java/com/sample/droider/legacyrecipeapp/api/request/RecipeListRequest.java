package com.sample.droider.legacyrecipeapp.api.request;

import com.sample.droider.legacyrecipeapp.api.HttpMethod;
import com.sample.droider.legacyrecipeapp.api.parser.RecipeListParser;
import com.sample.droider.legacyrecipeapp.dto.Recipe;

import java.util.List;

public class RecipeListRequest extends Request<List<Recipe>> {
    public static Request createRequest() {
        Request.Builder<List<Recipe>> builder = new Request.Builder<>();
        builder.path("recipe")
                .httpMethod(HttpMethod.GET)
                .parser(new RecipeListParser());
        return builder.build();
    }
}