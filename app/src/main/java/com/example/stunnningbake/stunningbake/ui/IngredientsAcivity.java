package com.example.stunnningbake.stunningbake.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.models.Recipe;

public class IngredientsAcivity extends AppCompatActivity {

    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_acivity);

        Intent intentFromParent = getIntent();
        if (intentFromParent != null){
            if (intentFromParent.hasExtra("Recipe")){
                recipe = intentFromParent.getParcelableExtra("Recipe");
            }
        }
        getSupportActionBar().setTitle(recipe.getName());
        if (savedInstanceState == null) {
            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setRecipe(recipe);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientFragment)
                    .commit();
        }
    }

}
