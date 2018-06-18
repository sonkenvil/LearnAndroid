package com.something.kteam.learnandroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class PlayVideo extends AppCompatActivity implements ExoPlayer.EventListener {
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    private InterstitialAd mInterstitialAd;
//    String video = "https://ia801502.us.archive.org/8/items/Bi1GiiThiuVHiuHnhAndroid/B%C3%A0i%201%20-%20Gi%E1%BB%9Bi%20thi%E1%BB%87u%20v%E1%BB%81%20h%E1%BB%87%20%C4%91i%E1%BB%81u%20h%C3%A0nh%20android.mp4";
    ProgressBar progressBar;
    long mLastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        getSupportActionBar().hide();
        String video = getIntent().getStringExtra("link");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(defaultBandwidthMeter));
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        simpleExoPlayer.addListener(this);
        Uri VideoUri = Uri.parse(video);
        DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(VideoUri, defaultHttpDataSourceFactory, extractorsFactory, null, null);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inner));
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_READY) {
            progressBar.setVisibility(View.INVISIBLE);
        }else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        return super.onKeyDown(keyCode, event);
//        if(keyCode == KeyEvent.KEYCODE_HOME) {
//            onStop();
//            return false;
//        }
//        return true;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        simpleExoPlayer.stop();
        mInterstitialAd.show();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLastPosition =  simpleExoPlayer.getCurrentPosition();
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.seekTo(mLastPosition);
    }
}
