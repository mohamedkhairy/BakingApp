package khairy.com.bakingapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import khairy.com.bakingapp.MainActivity;
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
    private long stopstate;
    private Bundle bundlesavedState = null;


    private SimpleExoPlayer player;
    @BindView(R.id.videoView)
    PlayerView playerView;
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recips.setText(reviewJsonData.getSteps().get(position).getDescription());
        Url = reviewJsonData.getSteps().get(position).getVideoURL();

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null && bundlesavedState == null) {
            bundlesavedState = savedInstanceState.getBundle("poss");
        }
    }



    public void initial_state (String link){


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector );

        playerView.setPlayer(player);

        Uri mp4VideoUri =Uri.parse(link);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                getContext(),
                Util.getUserAgent(getContext(), getString(R.string.app_name)));
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mp4VideoUri);
        player.prepare(mediaSource);


        if(bundlesavedState != null) {
            videoState = bundlesavedState.getLong("poss");
            if (videoState != C.TIME_UNSET){
                player.seekTo(videoState);
            }
            bundlesavedState = null;
        }else if (stopstate!=0){
            player.seekTo(stopstate);
            stopstate=0;
        }
        player.setPlayWhenReady(true);

    }

    @Override
    public void onStop() {
        if (!Url.isEmpty()) {
            playerView.setPlayer(null);
            player.release();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopstate = player.getCurrentPosition();
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
    public void onStart() {
        super.onStart();
            if (!Url.isEmpty()) {
                initial_state(Url);
            } else {
                playerView.setVisibility(View.GONE);
            }
    }


}
