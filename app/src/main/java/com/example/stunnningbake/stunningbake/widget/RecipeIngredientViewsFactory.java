package com.example.stunnningbake.stunningbake.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.models.Ingredient;

import java.util.List;

import static com.example.stunnningbake.stunningbake.widget.RecipeIngredientWidgetProvider.ingredients;

/**
 * Created by PC-Lenovo on 17/09/2017.
 */

public class RecipeIngredientViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    List<Ingredient> remoteViewIngredients;
    Context mContext;
    public RecipeIngredientViewsFactory(Context context, Intent intent) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() { remoteViewIngredients = ingredients;

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return remoteViewIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews list = new RemoteViews(mContext.getPackageName(), R.layout.recipe_ingredient_list);
        String ingredientName = remoteViewIngredients.get(position).getIngredient().trim();
        String ingredientQuantity = Float.toString(remoteViewIngredients.get(position).getQuantity()).trim();
        if (ingredientQuantity.endsWith(".0")){
            ingredientQuantity = ingredientQuantity.substring(0, ingredientQuantity.length()-2);
        }
        String ingredientMeasure = remoteViewIngredients.get(position).getMeasure().toLowerCase().trim();
        String ingredientForwidget = ingredientName + " : " + ingredientQuantity + " " + ingredientMeasure;
        list.setTextViewText(R.id.text1,ingredientForwidget);
        Log.d("WIDGET",ingredientForwidget);

        Intent intent = new Intent();
        list.setOnClickFillInIntent(R.id.text1,intent);


        return list;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
