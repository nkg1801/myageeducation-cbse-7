package com.myAgeEducation.cbseClass7;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import static android.view.View.GONE;
import static com.myAgeEducation.cbseClass7.Util.mNativeAds;

public class QuestionSets extends Activity{
    Activity context;
    private SharedPreferences _sharedPreferences;
    RecyclerView recyclerView;
    int _chapterNumber;
    private final ArrayList<Question> _questionList = new ArrayList<>();
    boolean isFirstTime;
    DataBaseHelper _databaseHelper;
    int _cloudVersion = 0;
    private final ArrayList<String> _pendingDownloads = new ArrayList<>();
    private boolean _runnableStarted = false;
    private boolean _isAddToLocalDatabaseCompleted = true;
    private static final int SUBJECT_INDEX = 3;
    private static final int QUESTION_SET_INDEX = 4;
    private final ArrayList<String> _downloadLinks = new ArrayList<>();
    NativeAdView adView;
    private static final int REQ_Y = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_question_sets);
        ringProgressDialog = new ProgressDialog(QuestionSets.this);
        _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        recyclerView = findViewById(android.R.id.list);
        RewardedAd rewardedAd = Util.rewardedAd;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            _chapterNumber = bundle.getInt("chapter_number");
            String chapterName = bundle.getString("chapter_name");
            setTitle(chapterName);
        }
        isFirstTime = true;
        hideProgressBar();

        try {
            // this is the first time the database is opened.
            openDatabase();
        }
        catch(Exception e)
        {
            Util.displayAlert("Error-SUB-001: " + e.getMessage(), "ERROR-SUB-001", QuestionSets.this);
        }

        if(Util.Subject.equalsIgnoreCase("maths")) // we have already hardcoded the sets of math in Util.mathSets
        {
            Util.QuestionSets = Util.mathSets;
        }
        else {
            readQuestionSets();
        }

        context = this;
        setFieldsForNativeAd();
        if(!mNativeAds.isEmpty()) {
            if(mNativeAds.get(0) != null) {
                findViewById(R.id.cardViewNativeAds).setVisibility(View.VISIBLE);
                populateNativeAdView(mNativeAds.get(0));
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
            if (!TextUtils.isEmpty(Util.QuestionSets)) {
                final String[] temp = Util.QuestionSets.split(";");
                if (temp.length > 0) {
                    findViewById(R.id.cardViewNativeAds).setVisibility(GONE);
                    populateAdapter(temp);
                }
            }
    }

    private void openDatabase()
    {
        _databaseHelper = new DataBaseHelper(getApplicationContext());
        try
        {
            _databaseHelper.createDataBase();
        }
        catch(IOException e)
        {
            Log.d("CBSE_ERROR_OPENDATABASE", Objects.requireNonNull(e.getMessage()));
        }

        _databaseHelper.openDataBase();
    }

    private int getRewardedAmount()
    {
        return _sharedPreferences.getInt( Util.Subject.toLowerCase() + "_" + _chapterNumber + "_rewardedAmount", 1);
    }

    private void incrementRewardedAmount()
    {
        int n = getRewardedAmount();
        SharedPreferences.Editor prefEdit = _sharedPreferences.edit();
        prefEdit.putInt(Util.Subject.toLowerCase() + "_" + _chapterNumber + "_rewardedAmount", n + 1);
        prefEdit.apply();
    }

    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter;

    ArrayList<String> setNames = new ArrayList<>();
    ArrayList<String> rewardList = new ArrayList<>();
    ArrayList<String> setResult = new ArrayList<>();
    ArrayList<Object> sets = new ArrayList<>();
    int _numberOfSets;

    private void populateAdapter(String[] temp)
    {
        setNames.clear();
        rewardList.clear();
        setResult.clear();
        sets.clear();

        _numberOfSets = Integer.parseInt(temp[_chapterNumber - 1]);

        for(int i=1; i<= _numberOfSets;i++) {
            setNames.add("Question Set #" + i);
            sets.add("Question Set #" + i);
        }

        setResult = readResult();

        rewardList = new ArrayList<>();
        int rewardAmount = getRewardedAmount();

        if(_numberOfSets <= rewardAmount)
        {
            findViewById(R.id.textViewRewardedAdInfo).setVisibility(GONE);
        }
        else
        {
            findViewById(R.id.textViewRewardedAdInfo).setVisibility(View.VISIBLE);
        }

        for(int i = 0; i < rewardAmount && i < _numberOfSets; i++)
        {
            rewardList.add("rewarded");
        }

        int unrewarded = _numberOfSets - rewardAmount;

        if(unrewarded > 0)
        {
            rewardList.add("next to unlock");
        }

        unrewarded = _numberOfSets - rewardAmount - 1;

        for(int i = 0; i < (unrewarded); i++)
        {
            rewardList.add("unrewarded");
        }
        insertAdsInMenuItems();
    }

    private void insertAdsInMenuItems() {
        hideProgressBar();
        if (mNativeAds.isEmpty()) {
            recyclerViewAdapter = new RecyclerViewAdapterForQuestionSets(this, sets, setNames, rewardList, setResult);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            return;
        }

        for (NativeAd ad: mNativeAds) {
            sets.add(ad);
            rewardList.add("unrewarded");
            setResult.add("0");
            Util.IsNativeAdsAdded = true;
        }

        recyclerViewAdapter = new RecyclerViewAdapterForQuestionSets(this, sets, setNames, rewardList, setResult);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public RewardedAd createAndLoadRewardedAd(final boolean showProgress) {
        if(showProgress) {
            showProgressDialog("Loading Ad Video");
        }
        String adUnitId = Util.isReleaseVersion ? getString(R.string.admob_rewarded_adunitid) : getString(R.string.admob_rewarded_testunitid);

        RewardedAd.load(this, adUnitId, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                Log.d("NKG-REWARDED", "Video Ad loaded");
                Util.rewardedAd = ad;
                if(showProgress) {
                    dismissProgressDialog();

                    ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Util.rewardedAd = createAndLoadRewardedAd(false);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            Util.rewardedAd = null;
                            rewardedAdError = "failed to display";
                        }
                    });

                    ad.show(QuestionSets.this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            rewardUser();
                        }
                    });
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Util.rewardedAd = null;
                Log.d("NKG-REWARDED", "Video Ad failed to load");
                if(showProgress) {
                    dismissProgressDialog();
                }

                Toast.makeText(
                        context,
                        "Video ad is not available right now. Please try again later.",
                        Toast.LENGTH_SHORT
                ).show();

            }
        });
        return null;
    }

    String rewardedAdError = "";
    int _setNumber;
    public String onVideoClick(int setNumber) throws InterruptedException {
        rewardedAdError = "";
        _setNumber = setNumber;

        if (Util.rewardedAd != null) {
            Util.rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Util.rewardedAd = createAndLoadRewardedAd(false);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    Util.rewardedAd = null;
                    rewardedAdError = "failed to display";
                }
            });

            Util.rewardedAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    rewardUser();
                }
            });
        }
        else
        {
            Util.rewardedAd = createAndLoadRewardedAd(true);
            return "";
        }
        return rewardedAdError;
    }

    private void rewardUser()
    {
        FirebaseManager firebaseManager = new FirebaseManager();
        final String versionCode = "v" + com.myAgeEducation.cbseClass7.BuildConfig.VERSION_CODE;
        String value = Util.ClassName + versionCode + "-" + Util.getCurrentDateTime();
        String nodePath = "questionSetRewarded/" + Util.Subject + "/CH-" + _chapterNumber + "/" + _setNumber + "/" + UUID.randomUUID().toString();
        firebaseManager.write(value, nodePath);

        incrementRewardedAmount();
        rewardList.clear();
        int rewardAmount = getRewardedAmount();

        if(_numberOfSets == rewardAmount)
        {
            findViewById(R.id.textViewRewardedAdInfo).setVisibility(GONE);
        }

        for(int i = 0; i < rewardAmount && i < _numberOfSets; i++)
        {
            rewardList.add("rewarded");
        }

        int unrewarded = _numberOfSets - rewardAmount;

        if(unrewarded > 0)
        {
            rewardList.add("next to unlock");
        }

        unrewarded = _numberOfSets - rewardAmount - 1;

        for(int i = 0; i < (unrewarded); i++)
        {
            rewardList.add("unrewarded");
        }
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void readQuestionSets() {
        setNames.clear();
        rewardList.clear();
        setResult.clear();

        if(recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyDataSetChanged();
        }
        showProgressBar();
        final String subject = Util.Subject.toLowerCase();
        if(TextUtils.equals(subject, "maths") && !TextUtils.isEmpty(Util.mathSets))
        {
            hideProgressBar();
            Util.QuestionSets = Util.mathSets;
            final String[] temp = Util.QuestionSets.split(";");
            if(temp.length > 0) {
                populateAdapter(temp);
            }
            return;
        }
        else if(TextUtils.equals(subject, "science") && !TextUtils.isEmpty(Util.scienceSets))
        {
            hideProgressBar();
            Util.QuestionSets = Util.scienceSets;
            final String[] temp = Util.QuestionSets.split(";");
            if(temp.length > 0) {
                populateAdapter(temp);
            }
            return;
        }
        else if(TextUtils.equals(subject, "computers") && !TextUtils.isEmpty(Util.computerSets))
        {
            hideProgressBar();
            Util.QuestionSets = Util.computerSets;
            final String[] temp = Util.QuestionSets.split(";");
            if(temp.length > 0) {
                populateAdapter(temp);
            }
            return;
        }
        else if(TextUtils.equals(subject, "gk") && !TextUtils.isEmpty(Util.gkSets))
        {
            hideProgressBar();
            Util.QuestionSets = Util.gkSets;
            final String[] temp = Util.QuestionSets.split(";");
            if(temp.length > 0) {
                populateAdapter(temp);
            }
            return;
        }

        FirebaseManager firebaseManager = new FirebaseManager();
        String dataPath = subject + "/sets-new";
        firebaseManager.readSingleValue(dataPath, value -> {
            hideProgressBar();
            findViewById(R.id.cardViewNativeAds).setVisibility(GONE);
            if (value == null) {
                displayError();
            } else if (TextUtils.equals(value.toString(), "error")) {
                displayError();
            } else {
                String sets = value.toString();
                Util.QuestionSets = sets;

                if (!TextUtils.isEmpty(sets)) {
                    if(TextUtils.equals(subject, "maths"))
                    {
                        Util.mathSets = Util.QuestionSets;
                    }
                    else if(TextUtils.equals(subject, "science"))
                    {
                        Util.scienceSets = Util.QuestionSets;
                    }
                    else if(TextUtils.equals(subject, "computers"))
                    {
                        Util.computerSets = Util.QuestionSets;
                    }
                    else if(TextUtils.equals(subject, "gk"))
                    {
                        Util.gkSets = Util.QuestionSets;
                    }

                    final String[] temp = sets.split(";");
                    populateAdapter(temp);
                }
            }
        });
    }

    private void displayError() {
        TextView textViewLoadingViews = findViewById(R.id.textViewLoadingViews);
        textViewLoadingViews.setText("No chapters found for the selected class and subject");

        textViewLoadingViews.setTextColor(Color.RED);
        textViewLoadingViews.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        ProgressBar progressBarLoadingChapters = findViewById(R.id.progressBarLoadingChapters);
        progressBarLoadingChapters.setVisibility(View.INVISIBLE);

        TextView textViewLoadingViews = findViewById(R.id.textViewLoadingViews);
        textViewLoadingViews.setVisibility(View.INVISIBLE);
    }

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

    // this method is not in use
    public void getQuestionsNew(final int questionSet)
    {
        //Question set 1 to 9 is used
        // For easiness, modify the file "maths- set1 to 9 - this is used. Merge this with (use this to import).txt" and
        // then merge it to "maths- set1 to 29-use this to import to firebase.txt" and then import this file to firebase so
        // that previous sets (used by older apps) is retained

        boolean isDownloadQuestion = false;

        if(!isDownloadQuestion) {
            readQuestionsFromLocalDatabase(questionSet, _chapterNumber);
            return;
        }

        isFirstTime = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgressDialog("Connecting to online question database...");
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String path = Util.Subject + "/set" + questionSet;
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);

        Query queryRef = databaseReference.orderByChild("chapter").equalTo(_chapterNumber);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    _questionList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        try {
                            Question question = postSnapshot.getValue(Question.class);
                            if(!TextUtils.isEmpty(question.getAnswer()))
                            {
                                _questionList.add(question);
                            }

                        } catch (Exception e) {
                            Log.d("CBSE_ERROR", e.getMessage());
                        }
                    }

                    Log.d("CBSE_", _questionList.size() + " were downloaded");

                    if (!_questionList.isEmpty()) {
                        Util.allQuestions = (ArrayList<Question>) _questionList.clone();

						/*if (isAddToLocalDatabaseCompleted)  // if the previous addition is completed, then only we add this, otherwise just ignore adding this set
						{
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									addQuestionToLocalDatabase(downloadLink);
								}
							};
							Thread thread = new Thread(runnable);
							//new Thread(runnable).start();
							thread.start();
						}
						else {
							_questionList.clear();
						}*/
                    } else {
                        try {
                            Util.allQuestions.clear();
                            dismissProgressDialog();
                            Util.displayAlert("No questions available for this subject", "Questions not available", QuestionSets.this);
                        }
                        catch(Exception e)
                        {
                            Util.displayAlert(e.getMessage(), "ERROR_SUB_303", QuestionSets.this);
                        }
                        return;
                    }

                    try {
                        dismissProgressDialog();
                    }
                    catch(Exception e)
                    {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        Util.displayAlert(sw.toString(), "ERROR_SUB_304", QuestionSets.this);
                    }

                    openTestActivity(questionSet);
                }
                catch(Exception e)
                {
                    Util.displayAlert(e.getMessage(), "ERROR_SUB_589", QuestionSets.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError firebaseError) {
                Log.d("Exception: ", firebaseError.getMessage());
            }
        });
    }

    public void openTestActivity(int setNumber)
    {
        Bundle bundle = getIntent().getExtras();
        QuestionPage.QuestionList = Util.allQuestions;
        Intent testPage = new Intent();
        testPage.setClassName(Util.PACKAGE_NAME, Util.PACKAGE_NAME + ".QuestionPage");

        int questionCount = Util.allQuestions.size();

        testPage.putExtra("questionCount", questionCount);
        testPage.putExtra("isRevision", "false");
        testPage.putExtra("isExit", "false");
        testPage.putExtra("recover_mode", false);
        assert bundle != null;
        testPage.putExtra("question_set", bundle.getString("question_set"));
        testPage.putExtra("chapter_number", _chapterNumber);
        testPage.putExtra("set_number", setNumber);

        testPage.putStringArrayListExtra("wrongAns_list", null);
        testPage.putIntegerArrayListExtra("used_numbers", null);

        startActivity(testPage);
    }

    ProgressDialog ringProgressDialog;
    private void dismissProgressDialog()
    {
        runOnUiThread(() -> {
            if(ringProgressDialog!=null && ringProgressDialog.isShowing()) {
                ringProgressDialog.dismiss();
            }
        });
    }

    private ArrayList<String> readResult()
    {
        ArrayList<String> result = new ArrayList<>();
        for(int i = 1; i <= _numberOfSets; i++)
        {
            String keyName = Util.Subject.toLowerCase() + "_" + _chapterNumber + "_" + i;
            String setResult = _sharedPreferences.getString( keyName, "NA");
            result.add(setResult);
        }

        return result;
    }

    public void readQuestionsFromLocalDatabase(int questionSet, int chapterNumber)
    {
        String tableName = Util.SCHOOL_NAME + "_" + Util.Subject;
        if(Util.allQuestions != null) {
            Util.allQuestions.clear();
        }
        Util.allQuestions = _databaseHelper.getAllQuestions(tableName.toUpperCase(), questionSet, chapterNumber);
        if(Util.allQuestions == null || Util.allQuestions.isEmpty())
        {
            //Something went wrong, the database has returned null .. will use the questions from the cloud .. hope it does not rain
            //String downloadLink = Util.SubjectRoot + "/" + Util.Subject + "/set" + String.valueOf(questionSet);
            downloadQuestions("", questionSet);
            return;
        }

        Log.d("CBSE_QuestionSet", String.valueOf(questionSet));
        Log.d("CBSE_QuestionCount", String.valueOf(Util.allQuestions.size()));

        if(!Util.allQuestions.isEmpty()) {
            {
                openTestActivity(questionSet);
            }
        }
    }

    private void GetCloudQuestionDatabaseVersion(final int questionSet)
    {
        showProgressDialog("Checking database for newer version...");
        String path = "questionDatabaseVersion/cbse/" + Util.Subject +"/cbseClass" + Util.GRADE;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);

        //Firebase ref = new Firebase(Util.FirebaseRoot + "/schools/question_database_version/cbse/" + Util.Subject +"/cbseClass" + Util.GRADE);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int version = snapshot.getValue(Integer.class);

                dismissProgressDialog();
                Log.d("myVersion", String.valueOf(version));

                try
                {
                    _cloudVersion = version;
                    saveLastCloudVersion(_cloudVersion);
                    Log.d("CloudVersion", String.valueOf(_cloudVersion));
                }

                catch(Exception e)
                {
                    Log.d("CloudVersionException", Objects.requireNonNull(e.getMessage()));
                }

                try {
                    int localVersion = _databaseHelper.getLocalQuestionDatabaseVersion(Util.Subject);
                    Log.d("CBSE_LocalVersion", String.valueOf(localVersion));

                    if (_cloudVersion != localVersion) {
                        Log.d("CBSE_CloudVersion", String.valueOf(_cloudVersion));
                        Log.d("CBSE_CloudVersionInfo", "Cloud Version is greater than local version");
                        _databaseHelper.resetDownloadStatus(Util.Subject);
                    }
                }
                catch(Exception e)
                {
                    Util.displayAlert("ERROR-121: " + e.getMessage(), "ERROR-121", QuestionSets.this);
                }

                readQuestions(questionSet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError firebaseError) {
                dismissProgressDialog();
                Log.d("CBSE_Exception: ", firebaseError.getMessage());
                Util.displayAlert("Unable to connect to the server. Make sure you are connected to the internet and try again","Unable to connect", QuestionSets.this);
            }
        });
    }

    private void saveLastCloudVersion(int cloudVersion)
    {
        SharedPreferences.Editor prefEdit = _sharedPreferences.edit();
        prefEdit.putInt("CloudVersion", cloudVersion);
        prefEdit.apply();
    }

    public void getQuestions(final int questionSet)
    {
        if(Util.Subject.equalsIgnoreCase("maths"))
        {
            launchQuestionLoader(questionSet);
            //openTestActivity(1);
        }
        else {
            GetCloudQuestionDatabaseVersion(questionSet);
        }
    }

    private void launchQuestionLoader(int setNumber)
    {
        Intent intent = new Intent();
        intent.putExtra("chapter_number", _chapterNumber);
        intent.putExtra("set_number", setNumber);
        intent.setClassName(Util.PACKAGE_NAME, Util.PACKAGE_NAME + ".QuestionLoaderActivity");
        startActivityForResult(intent, REQ_Y);
    }

    private void readQuestions(final int questionSet)
    {
        if(_databaseHelper.isDownloadPendingForThisSet(Util.Subject, questionSet, _chapterNumber))
        {
            downloadQuestions("",questionSet);
        }
        else
        {
            try {
                Runnable _runnable = () -> {
                    _runnableStarted = true;
                    readQuestionsFromLocalDatabase(questionSet, _chapterNumber);
                    _runnableStarted = false;
                };
                new Thread(_runnable).start();
            }

            catch(Exception e)
            {
                Util.displayAlert("reading questions from local database failed. " + e.getMessage(), "Error", QuestionSets.this);
            }
        }
    }

    private void addDownloadLinksToDownload()
    {
        _downloadLinks.clear();

        for(int i = 0; i < _pendingDownloads.size(); i++)
        {
            _downloadLinks.add(_pendingDownloads.get(i));
            Log.d("CBSE_PENDING_DOWNLOADS", _pendingDownloads.get(i));
        }
    }

    public void downloadQuestions(final String downloadLink, final int questionSet)
    {
        runOnUiThread(() -> showProgressDialog("Connecting to online question database..."));
        //showProgressDialog("Connecting to online question database...");
        //Firebase ref = new Firebase(downloadLink);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //String path = Util.SubjectRoot + "/" + Util.Subject + "/set" + questionSet;
        String path = Util.Subject + "/set" + questionSet;
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);

        //Query queryRef = databaseReference.orderByChild("chapter");
        Query queryRef = databaseReference.orderByChild("chapter").equalTo(_chapterNumber);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        try {
                            Question question = postSnapshot.getValue(Question.class);
                            _questionList.add(question);
                        } catch (Exception e) {
                            Log.d("CBSE_ERROR", Objects.requireNonNull(e.getMessage()));
                        }
                    }

                    Log.d("CBSE_", String.valueOf(_questionList.size()) + " were downloaded");

                    if (!_questionList.isEmpty()) {
                        Util.allQuestions = (ArrayList<Question>) _questionList.clone();
                        if (_isAddToLocalDatabaseCompleted)  // if the previous addition is completed, then only we add this, otherwise just ignore adding this set
                        {
                            Runnable runnable = () -> addQuestionToLocalDatabase(downloadLink, questionSet, _chapterNumber);
                            Thread thread = new Thread(runnable);
                            thread.start();
                        }
                        else {
                            _questionList.clear();
                        }
                    } else {
                        try {
                            Util.allQuestions.clear();
                            dismissProgressDialog();
                            Util.displayAlert("No questions available for this subject", "Questions not available", QuestionSets.this);
                        }
                        catch(Exception e)
                        {
                            Util.displayAlert(e.getMessage(), "ERROR_SUB_303", QuestionSets.this);
                        }
                        return;
                    }

                    try {
                        dismissProgressDialog();
                    }
                    catch(Exception e)
                    {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        Util.displayAlert(sw.toString(), "ERROR_SUB_304", QuestionSets.this);
                    }

                    try {
                        openTestActivity(questionSet);
                    }
                    catch(Exception e)
                    {
                        Util.displayAlert(e.getMessage(), "ERROR_SUB_305", QuestionSets.this);
                    }
                }
                catch(Exception e)
                {
                    Util.displayAlert(e.getMessage(), "ERROR_SUB_589", QuestionSets.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError firebaseError) {
                Log.d("Exception: ", firebaseError.getMessage());
            }
        });
    }

    private void addQuestionToLocalDatabase(String downloadLink, int questionSet, int chapterNumber) {
        _isAddToLocalDatabaseCompleted = false;
        String subject = Util.Subject;

        ArrayList<Question> questions = (ArrayList<Question>)_questionList.clone();
        _questionList.clear();

        if (_databaseHelper.addQuestions("CBSE_" + subject, questions, questionSet, chapterNumber)) {
            _databaseHelper.updateDownloadStatus(subject, questionSet, chapterNumber);
            if(_cloudVersion > 0) {
                _databaseHelper.updateLocalQuestionDatabaseVersionInfo(subject, _cloudVersion);
            }
        }

        _isAddToLocalDatabaseCompleted = true;
    }

    private void setFieldsForNativeAd()
    {
        adView = findViewById(R.id.ad_view);

        if (adView == null) {
            return;
        }

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
        //UnifiedNativeAdView adViewx = (UnifiedNativeAdView)findViewById(R.id.ad_view1);
        // Some assets are guaranteed to be in every UnifiedNativeAd.

        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        ((TextView) Objects.requireNonNull(adView.getBodyView())).setText(nativeAd.getBody());
        ((Button) Objects.requireNonNull(adView.getCallToActionView())).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
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
