package com.myAgeEducation.cbseClass7;

import android.os.Bundle;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Objects;

public class YouTubePlayerActivity extends YouTubeBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtubeplayer);

        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey("link")) {
            finish();
            return;
        }

        String link = Objects.requireNonNull(extras.get("link")).toString();
        String[] data = link.split(";");
        if(data.length > 1)
        {
            link = data[1];
        }
        else
        {
            link = data[0];
        }
        playVideo(link, findViewById(R.id.youtubePlayerView));
    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        youTubePlayerView.initialize("AIzaSyAFs1j7I1S3-tnm_M58qa7EIPvmiGhbDpc",    //this api is from GtcClasses
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                        //youTubePlayer.cuePlaylist("PL7YjFGahOhJUjOWlbX4Hsyqi2VZDVqIIf");
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        if (youTubeInitializationResult.isUserRecoverableError()) {
                            youTubeInitializationResult.getErrorDialog(YouTubePlayerActivity.this, 1).show();
                        } else {
                            Util.displayAlert("YouTube Player Error: " + youTubeInitializationResult.toString(), "Error", YouTubePlayerActivity.this);
                        }
                    }
                });
    }
}
