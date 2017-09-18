package com.example.stunnningbake.stunningbake.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.adapters.StepAdapter;
import com.example.stunnningbake.stunningbake.models.Ingredient;
import com.example.stunnningbake.stunningbake.models.Recipe;
import com.example.stunnningbake.stunningbake.widget.UpdateIngredientService;

import java.util.ArrayList;



public class StepsFragment extends Fragment{

    private static final String TAG = "StepsFragment";
    StepAdapter.StepAdapterOnClickHandler mClickHandler;
    Recipe mRecipe;

    private RecyclerView mRecyclerView;
    private StepAdapter mStepAdapter;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null && savedInstanceState.containsKey("Recipe")){
            mRecipe = savedInstanceState.getParcelable("Recipe");
        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        try{
            mClickHandler = (StepAdapter.StepAdapterOnClickHandler) rootView.getContext();
        } catch (ClassCastException e){
            throw new ClassCastException(rootView.getContext().toString() + " Must be implemented StepViewOnclickHandler");
        }

        mRecyclerView = rootView.findViewById(R.id.rv_steps);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mStepAdapter = new StepAdapter(mRecipe, rootView.getContext(), mClickHandler);
        mRecyclerView.setAdapter(mStepAdapter);


        //update widget
        UpdateIngredientService.startIngredientService(getContext(),(ArrayList<Ingredient>) mRecipe.getIngredients(), mRecipe.getName());

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
