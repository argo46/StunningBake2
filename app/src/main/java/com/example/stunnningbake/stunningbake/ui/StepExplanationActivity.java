package com.example.stunnningbake.stunningbake.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.models.Recipe;

public class StepExplanationActivity extends AppCompatActivity implements StepExplanationFragment.ButtonOnClickListener{

    private final static String TAG = StepExplanationActivity.class.getSimpleName();

    Recipe recipe;
    int selectedIndex;
    int screenOrientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.v(TAG, "LANDSCAPE MODE");
//            this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN); //Remove notification bar
        }
        setContentView(R.layout.activity_step_explanation);

        Intent intentFromParent = getIntent();
        if (intentFromParent != null){
            if (intentFromParent.hasExtra("Recipe")){
                recipe = intentFromParent.getParcelableExtra("Recipe");
            }
            if (intentFromParent.hasExtra("selectedIndex")){
                selectedIndex = intentFromParent.getIntExtra("selectedIndex", 0);
            }
        }
        getSupportActionBar().setTitle(recipe.getName());

        if (savedInstanceState == null) {
            StepExplanationFragment stepExplanationFragment = new StepExplanationFragment();
            stepExplanationFragment.setmRecipe(recipe);
            stepExplanationFragment.setSelectedIndex(selectedIndex);
            if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE){
                stepExplanationFragment.setLandscapeMode();
                Log.v(TAG, "LANDSCAPE MODE");
            } else{
                stepExplanationFragment.setPotraitMode();
                Log.v(TAG, "NOT LANDSCAPE MODE");
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_explanation_container, stepExplanationFragment)
                    .commit();
        } else {
            refreshFragment(selectedIndex);
        }





    }

    @Override
    public void buttonClicked(int index) {
        refreshFragment(index);
    }

    private void refreshFragment(int index){
        StepExplanationFragment stepExplanationFragment = new StepExplanationFragment();
        stepExplanationFragment.setmRecipe(recipe);
        stepExplanationFragment.setSelectedIndex(index);
        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE){
            stepExplanationFragment.setLandscapeMode();
            Log.v(TAG, "LANDSCAPE MODE");
        } else{
            stepExplanationFragment.setPotraitMode();
            Log.v(TAG, "NOT LANDSCAPE MODE");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_explanation_container,stepExplanationFragment)
                .commit();
    }


}
