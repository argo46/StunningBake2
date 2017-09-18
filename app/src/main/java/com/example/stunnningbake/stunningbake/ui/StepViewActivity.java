package com.example.stunnningbake.stunningbake.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.adapters.StepAdapter;
import com.example.stunnningbake.stunningbake.models.Ingredient;
import com.example.stunnningbake.stunningbake.models.Recipe;
import com.example.stunnningbake.stunningbake.widget.UpdateIngredientService;

import java.util.ArrayList;

public class StepViewActivity extends AppCompatActivity implements StepAdapter.StepAdapterOnClickHandler,
        StepExplanationFragment.OnSavedPlayback{
    private static final String TAG = StepViewActivity.class.getSimpleName();
    Recipe recipe;
    boolean mTwoPane;
    int mSelectedIndex = 0;
    long playbackPosition;
    int currentWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view);


        Intent intentFromParent = getIntent();
        if (intentFromParent != null) {
            if (intentFromParent.hasExtra("Recipe")){
                recipe = intentFromParent.getParcelableExtra("Recipe");
            }
        }
        getSupportActionBar().setTitle(recipe.getName());

        if (savedInstanceState == null) {
            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setRecipe(recipe);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepsFragment)
                    .commit();
        }

        if(findViewById(R.id.step_explanation_container) != null){
            mTwoPane = true;
            if (mSelectedIndex == 0) {
                IngredientFragment ingredientFragment = new IngredientFragment();
                ingredientFragment.setRecipe(recipe);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.step_explanation_container, ingredientFragment)
                        .commit();
            } else {
                StepExplanationFragment stepExplanationFragment = new StepExplanationFragment();
                stepExplanationFragment.setmRecipe(recipe);
                stepExplanationFragment.setSelectedIndex(mSelectedIndex);
                stepExplanationFragment.setPlaybackState(playbackPosition, currentWindow);
                stepExplanationFragment.setTabMode(true);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.step_explanation_container, stepExplanationFragment)
                        .commit();
            }

        }

        //update widget
        UpdateIngredientService.startIngredientService(this,(ArrayList<Ingredient>) recipe.getIngredients(), recipe.getName());
        Log.v(TAG, "Widget Updated : " + recipe.getName());


    }

    @Override
    public void onStepClick(int position) {
        if (mTwoPane){
            mSelectedIndex = position;
            if (mSelectedIndex == 0){
                IngredientFragment newIngredientFragment = new IngredientFragment();
                newIngredientFragment.setRecipe(recipe);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_explanation_container, newIngredientFragment)
                        .commit();
            } else {
                StepExplanationFragment newStepExplanationFragment = new StepExplanationFragment();
                newStepExplanationFragment.setmRecipe(recipe);
                newStepExplanationFragment.setSelectedIndex(mSelectedIndex);
                newStepExplanationFragment.setPlaybackState(playbackPosition, currentWindow);
                newStepExplanationFragment.setTabMode(true);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_explanation_container, newStepExplanationFragment)
                        .commit();
            }
        }else {
            Intent intent;
            if(position == 0){
                intent = new Intent(this, IngredientsAcivity.class);
            } else {
                intent = new Intent(this, StepExplanationActivity.class);
                intent.putExtra("selectedIndex", position);
            }
            intent.putExtra("Recipe", recipe);
            startActivity(intent);
        }
    }

    @Override
    public void getPlaybackState(long position, int index) {
        playbackPosition = position;
        currentWindow = index;
    }

}
