package com.sample.droider.legacyrecipeapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.droider.legacyrecipeapp.R;
import com.sample.droider.legacyrecipeapp.dto.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeListRecyclerAdapter extends RecyclerView.Adapter<RecipeListRecyclerAdapter.ViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();
    private Context context;
    private OnItemClickListener listener;

    public RecipeListRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(recipeList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void addAllRecipeList(List<Recipe> data) {
        this.recipeList.clear();
        recipeList = data;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private ImageView mainImage;
        private TextView recommendTag;
        private TextView recipeName;
        private TextView introduction;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            mainImage = itemView.findViewById(R.id.image_main);
            recommendTag = itemView.findViewById(R.id.text_recommend);
            recipeName = itemView.findViewById(R.id.text_recipe_name);
            introduction = itemView.findViewById(R.id.text_introduction);
        }

        void bind(final Recipe recipe, final OnItemClickListener listener) {
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickItem(recipe);
                    }
                }
            });
            Picasso.get().load(recipe.getMainImageUrl()).into(mainImage);
            if (TextUtils.equals("1", recipe.getRecommendFlg())) {
                recommendTag.setVisibility(View.VISIBLE);
            } else  {
                recommendTag.setVisibility(View.GONE);
            }
            recipeName.setText(recipe.getRecipeName());
            introduction.setText(recipe.getIntroduction());
        }
    }

    interface OnItemClickListener {
        void onClickItem(Recipe recipe);
    }
}
