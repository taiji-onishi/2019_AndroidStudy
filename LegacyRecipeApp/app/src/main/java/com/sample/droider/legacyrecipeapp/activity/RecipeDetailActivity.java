package com.sample.droider.legacyrecipeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.droider.legacyrecipeapp.R;
import com.sample.droider.legacyrecipeapp.adapter.RecipeDetailRecyclerAdapter;
import com.sample.droider.legacyrecipeapp.adapter.RecipeDetailRecyclerAdapter.DetailViewItem;
import com.sample.droider.legacyrecipeapp.adapter.RecipeDetailRecyclerAdapter.ImageViewItem;
import com.sample.droider.legacyrecipeapp.adapter.RecipeDetailRecyclerAdapter.IngredientViewItem;
import com.sample.droider.legacyrecipeapp.adapter.RecipeDetailRecyclerAdapter.IntroductionViewItem;
import com.sample.droider.legacyrecipeapp.adapter.RecipeDetailRecyclerAdapter.MethodViewItem;
import com.sample.droider.legacyrecipeapp.adapter.RecipeDetailRecyclerAdapter.SectionViewItem;
import com.sample.droider.legacyrecipeapp.api.ApiRequestTask;
import com.sample.droider.legacyrecipeapp.api.ErrorCode;
import com.sample.droider.legacyrecipeapp.api.request.DetailRequest;
import com.sample.droider.legacyrecipeapp.dto.CookingIngredients;
import com.sample.droider.legacyrecipeapp.dto.CookingMethod;
import com.sample.droider.legacyrecipeapp.dto.Recipe;
import com.sample.droider.legacyrecipeapp.dto.RecipeDataValidation;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements ApiRequestTask.CallbackToMainThread<Recipe> {

    private ApiRequestTask<Recipe> apiRequestTask;
    private RecipeDetailRecyclerAdapter adapter;
    private String recipeId;
    private RecyclerView recyclerView;
    public static final String INTENT_KEY = RecipeDetailActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null){
            recipeId = intent.getStringExtra(INTENT_KEY);
        }

        recyclerView = findViewById(R.id.recycler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch(recipeId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (apiRequestTask != null) {
            apiRequestTask.cancel();
        }
    }

    @Override
    public void onSuccess(Recipe result) {
        showList(result);
    }

    @Override
    public void onError(ErrorCode errorCode, String errorMessage, Exception e) {
        // TODO:エラー表示
    }

    @Override
    public void onCancel() {
        // nothing to do
    }

    private void fetch(String recipeId) {
        if (TextUtils.isEmpty(recipeId)) {
            return;
        }
        apiRequestTask = new ApiRequestTask<>(DetailRequest.createRequest(recipeId), this).execute(this);
    }

    private List<DetailViewItem> createList(Recipe result) {
        List<DetailViewItem> list = new ArrayList<>();
        RecipeDataValidation recipeDataValidation = new RecipeDataValidation();

        if (recipeDataValidation.isMainImageUrlValidate(result.getMainImageUrl())) {
            ImageViewItem imageViewItem = new ImageViewItem(result.getMainImageUrl());
            list.add(imageViewItem);
        }

        if (!TextUtils.isEmpty(result.getIntroduction())) {
            IntroductionViewItem introductionViewItem = new IntroductionViewItem(result.getIntroduction());
            list.add(introductionViewItem);
        }

        if (!result.getCookingIngredients().isEmpty()) {
            SectionViewItem sectionViewItem = new SectionViewItem(this.getString(R.string.label_ingredient));
            list.add(sectionViewItem);
            for (CookingIngredients ingredients : result.getCookingIngredients()) {
                IngredientViewItem ingredientViewItem = new IngredientViewItem(ingredients.getMaterial(), ingredients.getQuantity());
                list.add(ingredientViewItem);
            }
        }

        if (!result.getCookingMethods().isEmpty()) {
            SectionViewItem sectionViewItem = new SectionViewItem(this.getString(R.string.label_method));
            list.add(sectionViewItem);
            for (CookingMethod method : result.getCookingMethods()) {
                MethodViewItem methodViewItem = new MethodViewItem(method.getProcedureNo(), method.getProcedure());
                list.add(methodViewItem);
            }
        }

        return list;
    }

    private void showList(Recipe result) {
        List<DetailViewItem> itemList = createList(result);
        if (adapter == null) {
            adapter = new RecipeDetailRecyclerAdapter(itemList, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.addAll(itemList);
        }
    }
}
