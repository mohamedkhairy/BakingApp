package khairy.com.bakingapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import khairy.com.bakingapp.R;
import khairy.com.bakingapp.intoStepsActivity;
import khairy.com.bakingapp.jsonData.ReviewJson;
import khairy.com.bakingapp.prefrance;
import khairy.com.bakingapp.widget.WidgetService;

public class IntoSteps_fragment extends Fragment {

    public static final String info_bundle = "infpData";
    public static final String position_bundle = "position";
    private ReviewJson reviewJsonData;
    private int position;
    private String Url;
    private long videoState;
    private Bundle bundlesavedState = null;


    private SimpleExoPlayer player;
    @BindView(R.id.videoView)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.text)
    TextView recips;


    public static IntoSteps_fragment information_Sitter(ReviewJson rj , int pos) {
        IntoSteps_fragment intoStepsFragment = new IntoSteps_fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(info_bundle, rj);
        bundle.putInt(position_bundle, pos);
        intoStepsFragment.setArguments(bundle);
        return intoStepsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        reviewJsonData = (ReviewJson) getArguments().getSerializable(info_bundle);
        position = getArguments().getInt(position_bundle);
        View view =  inflater.inflate(R.layout.into_steps, container, false);
        ButterKnife.bind(this , view);
        if(savedInstanceState != null && bundlesavedState == null) {
            bundlesavedState = savedInstanceState.getBundle("poss");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recips.setText(reviewJsonData.getSteps().get(position).getDescription());
        Url = reviewJsonData.getSteps().get(position).getVideoURL();
        if (!Url.isEmpty()) {
            initial_state(Url);
        }else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }
    }



    public void initial_state (String link){


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);



        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector );

        simpleExoPlayerView.setUseController(true);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);

        Uri mp4VideoUri =Uri.parse(link);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "myVideo") );
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);

        final LoopingMediaSource loopingSource = new LoopingMediaSource(mediaSource);

        player.prepare(loopingSource);
        player.addListener(new ExoPlayer.EventListener() {
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

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });
        if(bundlesavedState != null) {
            videoState = bundlesavedState.getLong("poss");
            if (videoState != C.TIME_UNSET){
                player.seekTo(videoState);
            }
            bundlesavedState = null;
        }
        player.setPlayWhenReady(true);

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("poss", (bundlesavedState != null) ? bundlesavedState : saveState());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bundlesavedState = saveState();
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        if (!Url.isEmpty()) {
            state.putLong("poss", player.getContentPosition());
            player.stop();
            player.release();
        }
        return state;
    }



    @Override
    public void onPause() {
        super.onPause();
        if (!Url.isEmpty()) {
            player.stop();
            player.release();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }

        return true;
    }



}
