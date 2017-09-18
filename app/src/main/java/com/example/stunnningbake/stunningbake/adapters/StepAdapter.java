package com.example.stunnningbake.stunningbake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.models.Recipe;
import com.example.stunnningbake.stunningbake.models.Step;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by PC-Lenovo on 14/09/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder>{
    Recipe mRecipe = new Recipe();
    List<Step> mSteps = new ArrayList<>();
    Context mContext;

    private final StepAdapterOnClickHandler mClickHandler;

    public interface StepAdapterOnClickHandler{
        void onStepClick(int position);
    }

    public StepAdapter(Recipe recipe, Context context, StepAdapterOnClickHandler clickHandler){
        mRecipe = recipe;
        mContext = context;
        mSteps = recipe.getSteps();
        mClickHandler = clickHandler;
    }


    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mStepTitleTV;
        public final TextView mStepNoTV;
        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            mStepTitleTV = itemView.findViewById(R.id.tv_step_title);
            mStepNoTV = itemView.findViewById(R.id.tv_step_no);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
           mClickHandler.onStepClick(clickedPosition);
        }
    }

    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.steps_list;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapterViewHolder holder, int position) {
        holder.mStepNoTV.setText(String.format("%2d",position )+". ");
        if (position == 0){
            String ingredients = mContext.getResources().getString(R.string.ingredients);
            String recipeName = mRecipe.getName();
            holder.mStepTitleTV.setText(recipeName.trim() + " " + ingredients);
            holder.mStepNoTV.setVisibility(View.INVISIBLE);
        } else {
            String stepDesc = mSteps.get(position - 1).getShortDescription();
            holder.mStepTitleTV.setText(stepDesc.trim());
            Log.d(TAG, stepDesc.trim());
        }


    }

    @Override
    public int getItemCount() {
        if (mRecipe == null && mSteps == null){
            return 0;
        }
        int size = mSteps.size()+1;
        return size;
    }


}
