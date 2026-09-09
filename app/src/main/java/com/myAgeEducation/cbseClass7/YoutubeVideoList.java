package com.myAgeEducation.cbseClass7;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.youtube.player.YouTubeBaseActivity;
import java.util.ArrayList;
import java.util.Objects;

import static android.view.View.GONE;
import static com.myAgeEducation.cbseClass7.Util.mNativeAds;

public class YoutubeVideoList extends YouTubeBaseActivity {
    private SharedPreferences _sharedPreferences;
    RecyclerView recyclerView;
    int _chapterNumber;
    boolean isFirstTime;
    NativeAdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_video_list);
        ringProgressDialog = new ProgressDialog(YoutubeVideoList.this);
        _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        recyclerView = findViewById(android.R.id.list);
        RewardedAd rewardedAd = Util.rewardedAd;
        recyclerView.setVisibility(View.INVISIBLE);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        _chapterNumber = bundle.getInt("chapter_number");
        String chapterName = bundle.getString("chapter_name");
        setTitle(chapterName);
        isFirstTime = true;
        //hideProgressBar();

        setFieldsForNativeAd();
        if(!mNativeAds.isEmpty()) {
            if(mNativeAds.get(0) != null) {
                findViewById(R.id.cardViewNativeAds).setVisibility(View.VISIBLE);
                populateNativeAdView(mNativeAds.get(0));
            }
        }

        //populateAdapter();
        populateVideoList();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter;

    private void populateVideoList()
    {
        FirebaseManager.readYouTubeVideoList("maths", _chapterNumber, value -> {
            findViewById(R.id.progressBarLoadingChapters).setVisibility(GONE);
            findViewById(R.id.textViewLoadingViews).setVisibility(GONE);
            if(value != null)
            {
                if(value.toString().equals("error"))
                {
                    recyclerView.setVisibility(View.INVISIBLE);
                    TextView textView = findViewById(R.id.textViewNoVideos);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Error reading video list, please try again");
                }
                else {
                    recyclerViewAdapter = new RecyclerViewAdapterForYouTubeVideos(YoutubeVideoList.this, (ArrayList<YouTubeVideoDetails>) value);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                recyclerView.setVisibility(View.INVISIBLE);
                TextView textView = findViewById(R.id.textViewNoVideos);
                textView.setVisibility(View.VISIBLE);
                textView.setText("No Videos available for the selected chapter");
            }
        });
    }

    private void displayError() {
        TextView textViewLoadingViews = findViewById(R.id.textViewLoadingViews);
        textViewLoadingViews.setText("No chapters found for the selected class and subject");

        textViewLoadingViews.setTextColor(Color.RED);
        textViewLoadingViews.setVisibility(View.VISIBLE);
    }

    /*private void hideProgressBar() {
        ProgressBar progressBarLoadingChapters = findViewById(R.id.progressBarLoadingChapters);
        progressBarLoadingChapters.setVisibility(View.INVISIBLE);

        TextView textViewLoadingViews = findViewById(R.id.textViewLoadingViews);
        textViewLoadingViews.setVisibility(View.INVISIBLE);
    }*/

    private void showProgressBar() {
        ProgressBar progressBarLoadingChapters = findViewById(R.id.progressBarLoadingChapters);
        progressBarLoadingChapters.setVisibility(View.VISIBLE);

        TextView textViewLoadingViews = findViewById(R.id.textViewLoadingViews);
        textViewLoadingViews.setVisibility(View.VISIBLE);
    }

    private void showProgressDialog(String message)
    {
        try {
            ringProgressDialog.setTitle("Please wait ...");
            ringProgressDialog.setMessage(message);
            ringProgressDialog.setCancelable(false);
            ringProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            if (ringProgressDialog != null && (!ringProgressDialog.isShowing())) {
                ringProgressDialog.show();
                ringProgressDialog.setCancelable(true);
            }
        }
        catch (Exception e)
        {
        }
    }


    ProgressDialog ringProgressDialog;
    private void dismissProgressDialog()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(ringProgressDialog!=null && ringProgressDialog.isShowing()) {
                    ringProgressDialog.dismiss();
                }
            }
        });
    }

    private void saveLastCloudVersion(int cloudVersion)
    {
        SharedPreferences.Editor prefEdit = _sharedPreferences.edit();
        prefEdit.putInt("CloudVersion", cloudVersion);
        prefEdit.apply();
    }

    private void setFieldsForNativeAd()
    {
        adView = findViewById(R.id.ad_view);

        // The MediaView will display a video asset if one is present in the ad, and the
        // first image asset otherwise.
        adView.setMediaView(adView.findViewById(R.id.ad_media));

        // Register the view used for each individual asset.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
    }

    private void populateNativeAdView(NativeAd nativeAd) {
        //NativeAdView adViewx = (NativeAdView)findViewById(R.id.ad_view1);
        // Some assets are guaranteed to be in every NativeAd.

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView()))
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }
}
