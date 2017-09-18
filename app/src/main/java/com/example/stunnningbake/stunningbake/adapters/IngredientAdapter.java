package com.example.stunnningbake.stunningbake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.models.Ingredient;
import com.example.stunnningbake.stunningbake.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-Lenovo on 16/09/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder>{

    List<Ingredient> mIngredients = new ArrayList<>();
    Recipe mRecipe;
    Context mContext;
    public IngredientAdapter(Recipe recipe, Context context) {
        mRecipe = recipe;
        mContext = context;
        mIngredients = recipe.getIngredients();
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIngredientNo;
        public final TextView mIngredientName;
        public final TextView mIngredientQuanntity;
        public final TextView mIngredientMeasure;
        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            mIngredientNo = itemView.findViewById(R.id.tv_ingredient_no);
            mIngredientName = itemView.findViewById(R.id.tv_ingredient_name);
            mIngredientQuanntity = itemView.findViewById(R.id.tv_ingredient_quantity);
            mIngredientMeasure = itemView.findViewById(R.id.tv_ingredient_measure);
        }
    }

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.ingredients_list, parent, false);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {
        String ingredientName = mIngredients.get(position).getIngredient();
        String ingredientQuantity = Float.toString(mIngredients.get(position).getQuantity()).trim();
        if (ingredientQuantity.endsWith(".0")){
            ingredientQuantity = ingredientQuantity.substring(0, ingredientQuantity.length()-2);
        }
        ingredientQuantity = ingredientQuantity + " ";
        String ingredientMeasure = mIngredients.get(position).getMeasure().toLowerCase();
        holder.mIngredientNo.setText(Integer.toString(position + 1) + ". ");
        holder.mIngredientName.setText(ingredientName);
        holder.mIngredientQuanntity.setText(ingredientQuantity);
        holder.mIngredientMeasure.setText(ingredientMeasure);
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null){
            return 0;
        }
        return mIngredients.size();
    }


}
