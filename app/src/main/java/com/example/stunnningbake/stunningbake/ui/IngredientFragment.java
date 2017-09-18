package com.example.stunnningbake.stunningbake.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.adapters.IngredientAdapter;
import com.example.stunnningbake.stunningbake.models.Recipe;

/**
 * Created by PC-Lenovo on 16/09/2017.
 */

public class IngredientFragment extends Fragment {
    private static final String TAG = "Ingredient Fragment";
    Recipe mRecipe;

    private RecyclerView mRecyclerView;
    private IngredientAdapter mIngredientAdapter;

    public IngredientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable("Recipe");
        }

        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        mRecyclerView = rootView.findViewById(R.id.rv_ingredient);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mIngredientAdapter = new IngredientAdapter(mRecipe, rootView.getContext());
        mRecyclerView.setAdapter(mIngredientAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("Recipe", mRecipe);
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }
}
