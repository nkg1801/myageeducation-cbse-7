package com.myAgeEducation.cbseClass7;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetMore extends Activity {
    private BaseAdapter _listAdapter;
    ListView _listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getmore);
        _listView = findViewById(android.R.id.list);

        //populateAdapter();
        readGetMoreApps();
        //addBannerAd();
    }

    /*private void addBannerAd()
    {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        mAdView.loadAd(adRequest);
    }*/

    private void populateAdapter() {
        ArrayList<Integer> appImage = new ArrayList<>();
        ArrayList<String> appTitle = new ArrayList<>();
        ArrayList<String> appDescription = new ArrayList<>();
        ArrayList<String> appLink = new ArrayList<>();

        appImage.add(R.drawable.g2048);
        appTitle.add("my 2048");
        appDescription.add("my 2048 is a numerical puzzle. It helps in developing analytical skills and quick thinking. The game is played by swiping left/right/top/bottom. The similar adjacent numbers will add up to make bigger numbers. Minimum target of the game is to achieve the number tile 256, though you may achieve much higher than this.");
        appLink.add("https://play.google.com/store/apps/details?id=moderndayeducation.game2048");

        appImage.add(R.drawable.flipit);
        appTitle.add("Flip-It Over");
        appDescription.add("Flip-It Over is a memory puzzle. It helps in remembering things and developing memory. The game is played by flipping the boxes. The similar boxes when flipped in sequence will disappear. The target of the game is to make all the boxes disappear.");
        appLink.add("https://play.google.com/store/apps/details?id=com.myageeducation.flipit");

        _listAdapter = new ListViewAdapterForGetMore(GetMore.this, appImage, appTitle, appDescription, appLink);
        _listView.setAdapter(_listAdapter);
    }

    void readGetMoreApps()
    {
        findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("getmoreapps-new/" + Util.SyllabusAndGrade);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
                try{
                    ArrayList<GetMoreApps> getMoreAppsList = new ArrayList<>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        try {
                            GetMoreApps app = postSnapshot.getValue(GetMoreApps.class);
                            getMoreAppsList.add(app);
                        } catch (Exception e) {
                            Log.d("CBSE_ERROR", e.getMessage());
                        }
                    }
                    populateAdapterNew(getMoreAppsList);
                }
                catch(Exception e)
                {
                    findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                findViewById(R.id.progressbar).setVisibility(View.INVISIBLE);
                Log.d("Exception: ", firebaseError.getMessage());
            }
        });
    }

    private void populateAdapterNew(ArrayList<GetMoreApps> list) {

        _listAdapter = new ListViewAdapterForGetMore(GetMore.this, list);//, appTitle, appDescription, appLink);
        _listView.setAdapter(_listAdapter);
    }
}
