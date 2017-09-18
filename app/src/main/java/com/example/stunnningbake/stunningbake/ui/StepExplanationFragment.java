package com.example.stunnningbake.stunningbake.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.stunnningbake.stunningbake.R;
import com.example.stunnningbake.stunningbake.models.Recipe;
import com.example.stunnningbake.stunningbake.models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

/**
 * Created by PC-Lenovo on 17/09/2017.
 */

public class StepExplanationFragment extends Fragment{
    private static final String TAG = "StepExplanationFragment";

    Recipe mRecipe;
    List<Step> mSteps;
    Step mStep;
    String videoUrl;
    int selectedIndex;
    int index;
    long playbackPosition;
    int currentWindow;
    boolean landscape = false;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView mStepDescription;
    private FrameLayout nextButton;
    private FrameLayout prevButton;
    private ButtonOnClickListener buttonOnClickListener;
    private OnSavedPlayback savedPlayback;

    public StepExplanationFragment() {
    }


    public interface ButtonOnClickListener{
        void buttonClicked(int index);
    }
    public interface OnSavedPlayback{
        void getPlaybackState(long position, int index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        try{
            buttonOnClickListener = (ButtonOnClickListener) getContext();
            savedPlayback = (OnSavedPlayback) getContext();

        } catch (ClassCastException e){
            throw new ClassCastException(e.toString() + " Must be implemented ButtonOnClickListener");
        }

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable("Recipe");
            index = savedInstanceState.getInt("index");
        }

        View rootView = inflater.inflate(R.layout.fragment_step_explanation, container, false);
        if (landscape) {
            rootView = inflater.inflate(R.layout.fragment_step_explanation_landscape, container, false);
        }



        mPlayerView = rootView.findViewById(R.id.step_player_view);

        mSteps = mRecipe.getSteps();
        mStep = mSteps.get(index);

        if (rootView.findViewById(R.id.next_button)!= null && rootView.findViewById(R.id.prev_button)!= null &&
                rootView.findViewById(R.id.tv_step_description)!= null){



            mStepDescription = rootView.findViewById(R.id.tv_step_description);
            mStepDescription.setText(mStep.getDescription().trim());

            nextButton = rootView.findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (index < mSteps.size() -1){
                        buttonOnClickListener.buttonClicked(selectedIndex + 1);
                   }
                }
            });


            prevButton = rootView.findViewById(R.id.prev_button);
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (index > 0){
                        buttonOnClickListener.buttonClicked(selectedIndex - 1);
                    }
                }
            });

        }


//        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.play_button_image));
        initializePlayer();
        return rootView;
    }




    private void initializePlayer(){
        if (mExoPlayer == null) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            mPlayerView.setPlayer(mExoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "Stunning Bake"), (TransferListener<? super DataSource>) bandwidthMeter);

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            videoUrl = mStep.getVideoURL();
            Uri videoUri = Uri.parse(videoUrl);
            MediaSource videoSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory,null, null);
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(playbackPosition);
        }
    }

    public void setPlaybackState(long position, int index){
        this.playbackPosition = position;
        this.currentWindow = index;
    }

    public void setLandscapeMode(){
        landscape = true;
    }

    public void setPotraitMode(){
        landscape = false;

    }

    private void releasePlayer(){
        if (mExoPlayer!= null){
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            if (savedPlayback != null){
                savedPlayback.getPlaybackState(playbackPosition,currentWindow);
            }
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("Recipe", mRecipe);
        outState.putInt("index", index);
    }

    public void setmRecipe(Recipe mRecipe) {
        this.mRecipe = mRecipe;
    }
    public void setSelectedIndex(int selectedIndex) {
        Log.d(TAG, "Selected Index = " + Integer.toString(selectedIndex));
        this.selectedIndex = selectedIndex;
        if (selectedIndex == 0){
            this.index = selectedIndex;
        }else {
            this.index = selectedIndex - 1;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
}
