package com.myAgeEducation.cbseClass7;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util
{
    static boolean IsUnderAutomaticTest = false;
    public static int AutoTestCount = 1;
    static boolean isReleaseVersion = true;
    static String Android_id = "";
	static ArrayList<Question> allQuestions = new ArrayList<>();
	static ArrayList<Question> revisionQuestions = new ArrayList<>();
	public static boolean IsVideoAvailable = false;

    //public static ArrayList questionNumbers = new ArrayList();

    static String UserUid = "";
    static String ClassName = "class-7";
    public static String SyllabusAndGrade = "cbse-7";
    static final String SCHOOL_NAME = "CBSE";

    static String forLogD = "";
    static AdData adData;
    public static String UserNamePrefix = "cbse7";
    static String TestName = "cbse7";
    static String AppUriInPlayStore = "market://details?id=com.myAgeEducation.cbseClass7";
    static boolean isFullPageAdDisplayed = false;

    /// Firebase related
    static String DefaultDatabaseLocation = "";
    static String DatabaseLocation = DefaultDatabaseLocation;
    static String TestReportRoot = "testReport/cbse/" + ClassName;
    static String SubjectRoot = "";
    public static String subjectChapters = "";
    static final String TEST_CHAPTER_WISE = "chapter_wise";

    ///================ Contest related============================================
    static int TestTimeOut = 15; // timeout in minute
    static boolean IsContestTest = false;
    //================== contest related data ends here===============================

    public static String AdDataRoot = SubjectRoot + "/extras/ads/activeAd";

    public static final String PACKAGE_NAME = "com.myAgeEducation.cbseClass7";

    static final String GRADE = "7";
    public static String Subject = "";
    static boolean isFreeApp = true;

    static String mathChapters = "Integers;Fractions and Decimals;Data Handling;Simple Equations;Lines and Angles;The Triangles and its Properties;Congruence of Triangles;Comparing Quantities;Rational Numbers;Practical Geometry;Perimeter and Area;Algebraic Expressions;Exponents and Powers;Symmetry;Visualising Solid Shapes";
    static String scienceChapters = "Nutrition in Plants;Nutrition in Animals;Fibre to Fabric;Chemical and Chemical Changes;Acids, Bases and Salts;Heat and Temperature;Climate and Adaption;Soil;Respiration in Organisms;Transportation of Substances;Reproduction in Plants;Time and Motion;Electric Current and its Effects;Wind and Storm;Light;Water;Forests: Our Lifeline;Wastewater Management";
    static String mathSets = "10;10;10;10;10;10;10;10;10;10;10;10;10;10;10"; // each chapter is set to 10 sets of questions
    static String scienceSets = "";
    static String computerSets = "";
    static String gkSets = "";
    static String QuestionSets = "";
    static final String AdMobInterstitialAdUnitId = "ca-app-pub-4837855590190532/6498051165"; //cbse class-7
    static final String AdMobInterstitialAdUnitDummyId = "ca-app-pub-3940256099942544/1033173712";
    static final String ADMOB_APP_ID = "ca-app-pub-4837855590190532~4119299833"; // class-7

    static final String PlayStoreLink = "https://play.google.com/store/apps/details?id=com.myAgeEducation.cbseClass" + GRADE;
    static final String ShareLinkTitle = "Link for " + SCHOOL_NAME + "-" + GRADE + " app download";
    static RewardedAd rewardedAd;
    static boolean IsRewardedAdConsumed = false;

    // NativeAds

    // The AdLoader used to load ads.
    public static AdLoader adLoader;

    // List of native ads that have been successfully loaded.
    public static List<NativeAd> mNativeAds = new ArrayList<>();
    public static NativeAd nativeAd;
    public static boolean IsNativeAdsAdded = false;

    static String getCurrentDateTime()
    {
        Date date = new Date();
        SimpleDateFormat monthFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        return monthFormat.format(date);
    }

    public static String getCurrentTime()
    {
        Date date = new Date();
        SimpleDateFormat monthFormat = new SimpleDateFormat("hh:mm a");
        return monthFormat.format(date);
    }

    static String getCurrentDate()
    {
        Date date = new Date();
        SimpleDateFormat monthFormat = new SimpleDateFormat("dd-MM-yyyy");
        return monthFormat.format(date);
    }

    static void displayAlert(String message, String title, Context context)
    {
        if(!((Activity)context).isFinishing()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setMessage(message);
            alert.setTitle(title);
            alert.setPositiveButton("OK", null);
            alert.setCancelable(true);
            alert.create().show();
        }
    }

    static Bitmap LoadBitmapFromBase64Encoding(String imageData)
    {
        imageData = imageData.replace("data:image/png;base64,",""); // introduced in Release 1.19
        byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
