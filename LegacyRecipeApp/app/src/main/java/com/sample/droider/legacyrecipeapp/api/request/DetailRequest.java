package com.sample.droider.legacyrecipeapp.api.request;

import com.sample.droider.legacyrecipeapp.api.HttpMethod;
import com.sample.droider.legacyrecipeapp.api.parser.RecipeParser;
import com.sample.droider.legacyrecipeapp.dto.Recipe;

import java.util.HashMap;
import java.util.Map;

public class DetailRequest extends Request<Recipe> {
    public static Request createRequest(String recipeId) {

        Map<String, String> params = new HashMap<>();
        params.put("recipe_id", recipeId);

        Request.Builder<Recipe> builder = new Request.Builder<>();
        builder.path("recipe")
                .httpMethod(HttpMethod.GET)
                .parser(new RecipeParser())
                .params(params);
        return builder.build();
    }
}
