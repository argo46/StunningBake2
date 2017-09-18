package com.example.stunnningbake.stunningbake.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.stunnningbake.stunningbake.models.Ingredient;

import java.util.ArrayList;

/**
 * Created by PC-Lenovo on 17/09/2017.
 */

public class UpdateIngredientService extends IntentService {

    public static final String FROM_ACTIVITY_INGREDIENTS_LIST = "FROM_CATIVITY_INGREDIENT_LIST";
    public static final String STRING_FROM_ACTIVITY_INGREDIENT_LIST = "STRING_FROM_ACTIVITY_INGREDIENT_LIST";

    public UpdateIngredientService() {
        super("UpdateIngredientService");
    }

    public static void startIngredientService(Context context, ArrayList<Ingredient> ingredients, String recipeName){
        Intent intent = new Intent(context, UpdateIngredientService.class);
        intent.putParcelableArrayListExtra(FROM_ACTIVITY_INGREDIENTS_LIST, ingredients);
        intent.putExtra(STRING_FROM_ACTIVITY_INGREDIENT_LIST, recipeName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(FROM_ACTIVITY_INGREDIENTS_LIST);
            String recipeName = intent.getStringExtra(STRING_FROM_ACTIVITY_INGREDIENT_LIST);
            handleActionUpdateIngredientWidgets(ingredients, recipeName);
        }

    }

    private void handleActionUpdateIngredientWidgets(ArrayList<Ingredient> ingredients, String recipeName) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putParcelableArrayListExtra(FROM_ACTIVITY_INGREDIENTS_LIST,ingredients);
        intent.putExtra(STRING_FROM_ACTIVITY_INGREDIENT_LIST, recipeName);
        sendBroadcast(intent);
    }
}
