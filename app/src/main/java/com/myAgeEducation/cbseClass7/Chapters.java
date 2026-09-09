package com.myAgeEducation.cbseClass7;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import androidx.annotation.NonNull;
import com.myAgeEducation.cbseClass7.adapters.ListViewAdapterForChapterList;
import static android.view.View.GONE;

public class Chapters  extends Activity
{
	private InterstitialAd mInterstitialAd;
    ArrayList<String> chapterNames = new ArrayList<>();
    ListView listView;
	ProgressDialog ringProgressDialog;
	private ArrayList<Question> _questionList = new ArrayList<>();
	boolean isVideoAvailable = false;

	@SuppressWarnings("unchecked")
    @Override
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
		setContentView(R.layout.chapter_list);

        listView = findViewById(android.R.id.list);
        setTitle(Util.Subject + " - Chapters");
		//readChapters();
		readSettings();
        addInterstitialAd();
		addBannerAd();

		listView.setOnItemClickListener((parent, view, position, id) -> {
			if(!Util.Subject.equalsIgnoreCase("maths") || !Util.IsVideoAvailable) {
				Intent intent = new Intent(Chapters.this, QuestionSets.class);
				intent.putExtra("chapter_number", position + 1);
				String[] temp = Util.subjectChapters.split(";");
				if(temp.length > position) {
					intent.putExtra("chapter_name", temp[position]);
				}
				startActivity(intent);
			}
		});

		ringProgressDialog = new ProgressDialog(Chapters.this);
	}

	private void addBannerAd()
	{
		AdView mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.build();
		mAdView.loadAd(adRequest);
	}

	private void addInterstitialAd()
	{
		String adUnitId = Util.isReleaseVersion ? Util.AdMobInterstitialAdUnitId : Util.AdMobInterstitialAdUnitDummyId;

		AdRequest adRequest = new AdRequest.Builder()
				.build();

		InterstitialAd.load(this, adUnitId, adRequest, new InterstitialAdLoadCallback() {
			@Override
			public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
				mInterstitialAd = interstitialAd;
				mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
					@Override
					public void onAdDismissedFullScreenContent() {
						Util.isFullPageAdDisplayed = false;
						mInterstitialAd = null;
						addInterstitialAd();
					}

					@Override
					public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
						mInterstitialAd = null;
					}

					@Override
					public void onAdShowedFullScreenContent() {
						Util.isFullPageAdDisplayed = true;
					}
				});
			}

			@Override
			public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
				mInterstitialAd = null;
			}
		});
	}

	private void showInterstitialAdAd()
	{
		if (mInterstitialAd != null)
		{
			mInterstitialAd.show(this);
		}
	}

    private void readChapters() {
        final String subject = Util.Subject.toLowerCase();
        if(TextUtils.equals(subject, "maths") && !TextUtils.isEmpty(Util.mathChapters))
        {
			hideProgressBar();
            Util.subjectChapters = Util.mathChapters;
            final String[] temp = Util.subjectChapters.split(";");
            populateAdapter(temp);
            return;
        }
		if(TextUtils.equals(subject, "science") && !TextUtils.isEmpty(Util.scienceChapters))
		{
			hideProgressBar();
			Util.subjectChapters = Util.scienceChapters;
			final String[] temp = Util.subjectChapters.split(";");
			populateAdapter(temp);
			return;
		}

        FirebaseManager firebaseManager = new FirebaseManager();
        String dataPath = subject + "/chapter-names";
        firebaseManager.readSingleValue(dataPath, value -> {
            hideProgressBar();
            if (value == null) {
                displayComingSoon();
            } else if (TextUtils.equals(value.toString(), "error")) {
                displayError();
            } else {
                String chapters = value.toString();
                Util.subjectChapters = chapters;
                if (!TextUtils.isEmpty(chapters)) {
                    final String[] temp = chapters.split(";");
                    populateAdapter(temp);
                }
            }
        });
    }

    private void readSettings()
	{
		FirebaseManager firebaseManager = new FirebaseManager();
		String dataPath = "/settings/videoAvailable";
		firebaseManager.readSingleValue(dataPath, value -> {

            hideProgressBar();
            if (value == null) {
                //isVideoAvailable = false;
                Util.IsVideoAvailable = false;
            } else if (TextUtils.equals(value.toString(), "error")) {
                //isVideoAvailable = false;
                Util.IsVideoAvailable = false;
            } else {
                if(value.toString().equals("1"))
                {
                    //isVideoAvailable = true;
                    Util.IsVideoAvailable = true;
                }
            }
            readChapters();
        });
	}

    private void populateAdapter(String[] chapters) {
        ArrayList<String> chapterImage = new ArrayList<>();

        ArrayList<Integer> chapterNumbers = new ArrayList<>();
        chapterNames.clear();

        for (int i = 0; i < chapters.length; i++) {
            chapterImage.add(i, "");
            chapterNumbers.add(i, i);
            chapterNames.add(i, chapters[i]);
        }

        BaseAdapter listAdapter;
        listAdapter = new ListViewAdapterForChapterList(this, chapterImage, chapterNames, chapterNumbers, Util.TEST_CHAPTER_WISE);
        listView.setAdapter(listAdapter);
    }

    private void displayComingSoon() {
        TextView textViewComingSoon = findViewById(R.id.textViewComingSoon);
        textViewComingSoon.setVisibility(View.VISIBLE);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        ProgressBar progressBarLoadingChapters = findViewById(R.id.progressBarLoadingChapters);
        progressBarLoadingChapters.setVisibility(View.INVISIBLE);

        TextView textViewLoadingViews = findViewById(R.id.textViewLoadingViews);
        textViewLoadingViews.setVisibility(View.INVISIBLE);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setVisibility(GONE);
    }

    private void displayError() {
        TextView textViewLoadingViews = findViewById(R.id.textViewLoadingViews);
        textViewLoadingViews.setText("No chapters found for the selected class and subject");

        textViewLoadingViews.setTextColor(Color.RED);
        textViewLoadingViews.setVisibility(View.VISIBLE);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.VISIBLE);
    }

	@Override
	public void onResume()
	{
		super.onResume();
		if(Util.IsRewardedAdConsumed) {
			//Util.rewardedAd = createAndLoadRewardedAd();
		}
	}

	public void createAndLoadRewardedAd() {
		String adUnitId = Util.isReleaseVersion ? getString(R.string.admob_rewarded_adunitid) : getString(R.string.admob_rewarded_testunitid);

		RewardedAd.load(this, adUnitId, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
			@Override
			public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
				// Ad successfully loaded.
				// Util.rewardedAd = rewardedAd; // Uncomment if needed
			}

			@Override
			public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
				// Ad failed to load.
			}
		});
	}
}
