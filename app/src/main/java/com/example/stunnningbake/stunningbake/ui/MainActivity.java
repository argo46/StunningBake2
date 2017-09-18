package com.example.stunnningbake.stunningbake.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.stunnningbake.stunningbake.IdlingResource.SimpleIdlingResource;
import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.adapters.RecipeAdapter;
import com.example.stunnningbake.stunningbake.models.Recipe;
import com.example.stunnningbake.stunningbake.rest.ApiClient;
import com.example.stunnningbake.stunningbake.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    public IdlingResource getIdlingResource(){
        if (mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIdlingResource();

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recipes_main);
        int noOfColumn = getNoOfColumn();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, noOfColumn);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecipeAdapter = new RecipeAdapter(this, this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Recipe>> call = apiService.getRecipe();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Integer statusCode = response.code();
                Log.d(TAG,"Status Code : " + statusCode.toString());

                List<Recipe> recipes = response.body();
                mRecipeAdapter.setRecipesData(recipes);
                Log.d(TAG, "Recipe received = " + Integer.toString(recipes.size()));
                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


    }


    private int getNoOfColumn(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumn = (int) (dpWidth/300);
        return noOfColumn;
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, StepViewActivity.class);
        intent.putExtra("Recipe", recipe);
        startActivity(intent);
    }
}
