package com.sample.droider.legacyrecipeapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sample.droider.legacyrecipeapp.R;
import com.sample.droider.legacyrecipeapp.api.ApiRequestTask;
import com.sample.droider.legacyrecipeapp.api.ErrorCode;
import com.sample.droider.legacyrecipeapp.api.request.RecipeRequest;
import com.sample.droider.legacyrecipeapp.api.request.Request;
import com.sample.droider.legacyrecipeapp.dto.Recipe;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        fetchRecipeList();
    }

    private void fetchRecipeList() {
        Request request = RecipeRequest.createRequest();
        ApiRequestTask<List<Recipe>> task = new ApiRequestTask<>(request, new ApiRequestTask.CallbackToMainThread<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                Log.d("APITest", "onSuccess");
            }

            @Override
            public void onError(ErrorCode errorCode, String errorMessage, Exception e) {
                Log.d("APITest", errorMessage);
            }

            @Override
            public void onCancel() {

            }
        });
        task.execute(this);
    }
}
