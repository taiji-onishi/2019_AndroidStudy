package com.sample.droider.legacyrecipeapp.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.droider.legacyrecipeapp.R;
import com.sample.droider.legacyrecipeapp.adapter.RecipeListRecyclerAdapter;
import com.sample.droider.legacyrecipeapp.api.ApiRequestTask;
import com.sample.droider.legacyrecipeapp.api.ErrorCode;
import com.sample.droider.legacyrecipeapp.api.request.RecipeListRequest;
import com.sample.droider.legacyrecipeapp.dto.Recipe;
import com.sample.droider.legacyrecipeapp.util.DimensUtil;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements ApiRequestTask.CallbackToMainThread<List<Recipe>> {

    private ApiRequestTask<List<Recipe>> task;
    private RecipeListRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchRecipeList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (task != null) {
            task.cancel();
        }
    }

    private void fetchRecipeList() {
        task = new ApiRequestTask<>(RecipeListRequest.createRequest(), this);
        task.execute(this);
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new RecipeListRecyclerAdapter(this);
        }
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DimensUtil.dpToPx(8, getApplicationContext());
                outRect.right = DimensUtil.dpToPx(8, getApplicationContext());
                outRect.left = DimensUtil.dpToPx(8, getApplicationContext());

                int position = parent.getChildAdapterPosition(view);
                if (position == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = DimensUtil.dpToPx(8, getApplicationContext());
                }
            }
        });
    }

    @Override
    public void onSuccess(List<Recipe> result) {
        adapter.addAllRecipeList(result);
    }

    @Override
    public void onError(ErrorCode errorCode, String errorMessage, Exception e) {

    }

    @Override
    public void onCancel() {
        // nothing to do
    }
}
