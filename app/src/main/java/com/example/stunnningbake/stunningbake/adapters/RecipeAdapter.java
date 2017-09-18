package com.example.stunnningbake.stunningbake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PC-Lenovo on 07/09/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>{

    List<Recipe> recipes = new ArrayList<>();
    Context mContext;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(Context context, RecipeAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mRecipeTitleTV;
        public final ImageView mRecipeImageIV;
        public final TextView mRecipeServingsTV;
        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            mRecipeTitleTV = itemView.findViewById(R.id.tv_recipe_title);
            mRecipeImageIV = itemView.findViewById(R.id.iv_recipe_list_thumbnail);
            mRecipeServingsTV = itemView.findViewById(R.id.tv_servings);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Recipe recipe = recipes.get(getAdapterPosition());
            mClickHandler.onClick(recipe);
        }
    }


    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.recipes_list;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem,parent,false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {

        String recipeTitle = recipes.get(position).getName();
        String imagePath = recipes.get(position).getImage();
        Integer servings = recipes.get(position).getServings();
        holder.mRecipeTitleTV.setText(recipeTitle);
        holder.mRecipeServingsTV.setText(mContext.getResources().getString(R.string.servings) +" : " + Integer.toString(servings));
        if (imagePath != null && !imagePath.equals("")){
            Picasso.with(holder.mRecipeImageIV.getContext()).load(imagePath).into(holder.mRecipeImageIV);
        }

    }

    @Override
    public int getItemCount() {
        if(null == recipes){
            return 0;
        }
        return recipes.size();
    }

    public void setRecipesData(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }



}
