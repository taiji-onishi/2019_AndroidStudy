package com.sample.droider.legacyrecipeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.droider.legacyrecipeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailRecyclerAdapter extends RecyclerView.Adapter<RecipeDetailRecyclerAdapter.DetailVH> {

    private List<DetailViewItem> list;
    private Context context;

    public RecipeDetailRecyclerAdapter(List<DetailViewItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CellViewType cellViewType = CellViewType.getCellViewType(viewType);
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (cellViewType) {
            case IMAGE:
                return new ImageVH(inflater.inflate(R.layout.item_detail_image, parent, false));
            case INTRODUCTION:
                return new IntroductionVH(inflater.inflate(R.layout.item_detail_introduction, parent, false));
            case INGREDIENT:
                return new IngredientVH(inflater.inflate(R.layout.item_detail_ingredient, parent, false));
            case METHOD:
                return new MethodVH(inflater.inflate(R.layout.item_detail_method, parent, false));
            default:
                return new SectionVH(inflater.inflate(R.layout.item_detail_section, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DetailVH holder, int position) {
        int viewType = getItemViewType(position);
        CellViewType cellViewType = CellViewType.getCellViewType(viewType);

        switch (cellViewType) {
            case IMAGE:
                ImageVH imageVH = (ImageVH) holder;
                imageVH.bind((ImageViewItem) list.get(position));
                break;
            case INTRODUCTION:
                IntroductionVH introductionVH = (IntroductionVH) holder;
                introductionVH.bind((IntroductionViewItem) list.get(position));
                break;
            case INGREDIENT:
                IngredientVH ingredientVH = (IngredientVH) holder;
                ingredientVH.bind((IngredientViewItem) list.get(position));
                break;
            case METHOD:
                MethodVH methodVH = (MethodVH) holder;
                methodVH.bind((MethodViewItem) list.get(position));
                break;
            case SECTION:
                SectionVH vh = (SectionVH) holder;
                vh.bind((SectionViewItem) list.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getCellViewType().getViewType();
    }

    public void addAll(List<DetailViewItem> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public interface DetailViewItem {
        CellViewType getCellViewType();
    }

    public static class ImageViewItem implements DetailViewItem {
        String imageUrl;

        @Override
        public CellViewType getCellViewType() {
            return CellViewType.IMAGE;
        }

        public ImageViewItem(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class IntroductionViewItem implements DetailViewItem {
        String introduction;

        @Override
        public CellViewType getCellViewType() {
            return CellViewType.INTRODUCTION;
        }

        public IntroductionViewItem(String introduction) {
            this.introduction = introduction;
        }
    }

    public static class IngredientViewItem implements DetailViewItem {
        String material;
        String quantity;

        @Override
        public CellViewType getCellViewType() {
            return CellViewType.INGREDIENT;
        }

        public IngredientViewItem(String material, String quantity) {
            this.material = material;
            this.quantity = quantity;
        }
    }

    public static class MethodViewItem implements DetailViewItem {
        String procedureNo;
        String procedure;

        @Override
        public CellViewType getCellViewType() {
            return CellViewType.METHOD;
        }

        public MethodViewItem(String procedureNo, String procedure) {
            this.procedureNo = procedureNo;
            this.procedure = procedure;
        }
    }

    public static class SectionViewItem implements DetailViewItem {
        String title;
        @Override
        public CellViewType getCellViewType() {
            return CellViewType.SECTION;
        }

        public SectionViewItem(String title) {
            this.title = title;
        }
    }

    abstract class DetailVH<T extends DetailViewItem> extends RecyclerView.ViewHolder {
        DetailVH(@NonNull View itemView) {
            super(itemView);
        }
        abstract void bind(T item);
    }

    class ImageVH extends DetailVH<ImageViewItem> {
        ImageView mainImage;

        ImageVH(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.image_main);
        }

        @Override
        void bind(ImageViewItem item) {
            Picasso.get().load(item.imageUrl).into(mainImage);
        }
    }

    class IntroductionVH extends DetailVH<IntroductionViewItem> {
        TextView introductionText;

        IntroductionVH(@NonNull View itemView) {
            super(itemView);
            introductionText = itemView.findViewById(R.id.text_introduction);
        }

        @Override
        void bind(IntroductionViewItem item) {
            introductionText.setText(item.introduction);
        }
    }

    class IngredientVH extends DetailVH<IngredientViewItem> {
        TextView materialText;
        TextView quantityText;

        IngredientVH(@NonNull View itemView) {
            super(itemView);
            materialText = itemView.findViewById(R.id.text_material);
            quantityText = itemView.findViewById(R.id.text_quantity);
        }

        @Override
        void bind(IngredientViewItem item) {
            materialText.setText(item.material);
            quantityText.setText(item.quantity);
        }
    }

    class MethodVH extends DetailVH<MethodViewItem> {
        TextView noText;
        TextView methodText;

        MethodVH(@NonNull View itemView) {
            super(itemView);
            noText = itemView.findViewById(R.id.text_no);
            methodText = itemView.findViewById(R.id.text_method);
        }

        @Override
        void bind(MethodViewItem item) {
            noText.setText(item.procedureNo);
            methodText.setText(item.procedure);
        }
    }

    class SectionVH extends DetailVH<SectionViewItem> {
        TextView titleText;

        SectionVH(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.text_title);
        }

        @Override
        void bind(SectionViewItem item) {
            titleText.setText(item.title);
        }
    }

    enum CellViewType {
        IMAGE(1), INTRODUCTION(2), INGREDIENT(3), METHOD(4), SECTION(5), NONE(6);

        private int viewType;
        CellViewType(int viewType) {
            this.viewType = viewType;
        }

        public int getViewType() {
            return viewType;
        }

        public static CellViewType getCellViewType(int viewType) {
            for (CellViewType cellViewType : values()) {
                if (cellViewType.viewType == viewType) {
                    return cellViewType;
                }
            }
            return NONE;
        }
    }
}