package com.myAgeEducation.cbseClass7;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.graphics.Color;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import androidx.annotation.NonNull;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;
import java.util.Random;

import io.github.kexanie.library.MathView;

import static android.view.View.GONE;

public class QuestionPage extends Activity
{
	private RadioButton radioSelectedButton;
  	private Button buttonNext;
    private TableLayout tableLayout1;
  	private int _currentQuestionNumber;
	private Question _currentQuestion;
	public static ArrayList<Question> QuestionList;
	private ArrayList<Question> revisionQuestions;
  	private String answer;
  	private int correctAnswerCount;
  	private int questionCount;
  	public int seconds = 0;
  	public int minutes = 0;
  	private String isRevision;
  	private ArrayList<Integer> _usedNumbers = new ArrayList<Integer>();
	public ArrayList _questionNumbers = new ArrayList();
  	private SharedPreferences sharedPrefs;
  	private String reward = "";
  	private boolean _automaticallyMoveToNextQuestion = false;
	private boolean _isRandomQuestions = false;
  	private String rewardPoints = "";
	private InterstitialAd mInterstitialAd;
	private boolean _isRecoverMode;
	private String isExit;
	private Bundle _bundle;
	private String _questionSet;
	private int _questionIndex;
	private String _linkText;
	private boolean isAnswerCorrect;
	int totalScored = 0;

	@Override
  	public void onCreate(Bundle savedInstanceState)
  	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionpage);
		_bundle = getIntent().getExtras();
		readBundle();
		
		if(!storeQuestionNumbers())
		{
			Util.displayAlert("Questions could not be retrieved, please try again", "Error", QuestionPage.this);
			finish();
			return;
		}

		addInterstitialAd();
		addBannerAd();

		setTextViewProperties();
	
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

    	_currentQuestionNumber = 1;
		correctAnswerCount = 0;

		revisionQuestions = new ArrayList<>();

		displayInitialScore();

		if(isExit.equalsIgnoreCase("true"))
		{
			finish();
		}

		displayScore();

		buttonNext = findViewById(R.id.buttonNext);
        tableLayout1 = findViewById(R.id.tableLayout1);

    	setQuestionTextColor();

		buttonNext.setEnabled(false);
		HideButtonNext();

		if(sharedPrefs.getBoolean("random_questions", true))
		{
			_isRandomQuestions = true;
		}

		if(isRevision.equals("true"))
		{
			_isRandomQuestions = false;
			QuestionList = (ArrayList<Question>)Util.revisionQuestions.clone();
		}

		if(_isRandomQuestions)
		{
			try {
				_questionIndex = getRandomQuestionNumber();
				_usedNumbers.add(_questionIndex);
			}
			catch(Exception e)
			{
				Util.displayAlert("Questions could not be retrieved, please try again", "Error", QuestionPage.this);
				finish();
			}
		}
		else
		{
			_questionIndex = 0;
		}

		_currentQuestion = QuestionList.get(_questionIndex); // getting the first question

		setQuestionData(_currentQuestion);
    
    	if(questionCount == 1)
    	{
            buttonNext.setText(R.string.submit);
    	}

		addTimer();

      	if(sharedPrefs.getBoolean("move_to_next_question", false))
		{
			_automaticallyMoveToNextQuestion = true;
			tableLayout1.setVisibility(View.INVISIBLE);
		}

		setActivityTitle();
		addButtonListener();
		//addRadioButtonListener();
  	}

	private boolean storeQuestionNumbers()
	{
		Util.forLogD = "";
		_questionNumbers.clear();
		if(QuestionList == null || QuestionList.isEmpty())
		{
			return false;
		}
		for(int i = 0; i < QuestionList.size(); i++)
		{
			_questionNumbers.add(i);
		}
		return true;
	}

	private void displayScore()
	{
		TextView tvScore = findViewById(R.id.textViewScore);
		if(!sharedPrefs.getBoolean("score_with_every_question", true))
		{
			tvScore.setVisibility(GONE);
		}
	}

	private void setTextViewProperties()
	{
		TextView tv = findViewById(R.id.textViewQuestionNumber);
		tv.setTextColor(Color.WHITE);
		tv.setBackgroundColor(Color.DKGRAY);

		tv = findViewById(R.id.textViewTimer);
		tv.setTextColor(Color.WHITE);
		tv.setBackgroundColor(Color.DKGRAY);

		TextView tvScore = findViewById(R.id.textViewScore);
		tvScore.setTextColor(Color.WHITE);
		tvScore.setBackgroundColor(Color.DKGRAY);

		tv = findViewById(R.id.textViewSubject);
		tv.setTextColor(Color.WHITE);
		tv.setBackgroundColor(Color.DKGRAY);
	}

	private void setQuestionTextColor()
	{
		/*TextView textView = findViewById(R.id.textViewQuestion);

		if(isRevision.equals("true"))
		{
			textView.setTextColor(Color.RED);
		}
		else
		{
			textView.setTextColor(Color.BLUE);
		}*/
	}

	private void setActivityTitle()
	{
		String title = getTitle().toString();
		setTitle(title + " - " + Util.Subject);

		if(isRevision.equals("true"))
		{
			title = getTitle().toString();
			setTitle(title + " (Revision)");
		}

		String subject;
		if(Util.Subject.equalsIgnoreCase("cs"))
		{
			subject = "Computers";
		}
		else if(Util.Subject.equalsIgnoreCase("evs"))
		{
			subject = "Science";
		}
		else
		{
			subject = Util.Subject;
		}


		if(Util.Android_id.equalsIgnoreCase("6d692d322d2df2fb") || Util.Android_id.equalsIgnoreCase("e64b49e28d3e849c")) {
			displayQuestionSetAndQuestionNumber();
		}
		else
		{
			((TextView) findViewById(R.id.textViewSubject)).setText("Subject: " + subject);
		}
	}

	private void displayInitialScore()
	{
		TextView tvScore = findViewById(R.id.textViewScore);
		if(_isRecoverMode)
		{
			int lastScore = _bundle.getInt("last_score");
			tvScore.setText("Score: " + lastScore + "/" + questionCount);
		}
		else
		{
			tvScore.setText("Score: 0/" + questionCount);
		}
	}

	int _setNumber;
	int _chapterNumber;
	private void readBundle()
	{
		Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        questionCount = bundle.getInt("questionCount");
		isRevision = bundle.getString("isRevision");
		isExit = bundle.getString("isExit");
		reward = bundle.getString("reward");
		rewardPoints = bundle.getString("points");
		_isRecoverMode = bundle.getBoolean("recover_mode");
		_questionSet = bundle.getString("question_set");
		_setNumber = bundle.getInt("set_number");
		_chapterNumber = bundle.getInt("chapter_number");
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

  public void displayAlertBox(String message)
	{
		if(!QuestionPage.this.isFinishing()) {
			Util.displayAlert(message, "Test Report", QuestionPage.this);
		}
	}

    public void displayAlertWithOkCancel(String message, String title, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(message);
        alert.setTitle(title);
        alert.setPositiveButton("Yes", null);
        alert.setCancelable(true);

        alert.setPositiveButton("Yes", (dialog, which) -> {
			clearState(); // Test is completed. so remove the saved state
			openTestReportActivity();
			finish();
            showInterstitialAdAd();
        });

        alert.setNegativeButton("No",null);
        alert.create().show();
    }

    private void DisplayTime(int seconds)
	{
		int minutes = seconds / 60;
		int second = seconds % 60;
		int hours = minutes / 60;
		minutes = minutes %60;

		String elapsedTime;
		TextView textView = findViewById(R.id.textViewTimer);
		elapsedTime = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", second) + " ";
		textView.setText(elapsedTime);
	}
  
  public void addTimer()
  {
	  final Timer timer = new Timer();
	  timer.schedule(new TimerTask() {
		  @Override
		  public void run() {
			  runOnUiThread(() -> {
                  DisplayTime(seconds);

                  if(!Util.isFullPageAdDisplayed) {
                      seconds += 1;
                  }
              });
		  }
	  }, 0, 1000);
  }


	private Bitmap loadBitmapFromBase64Encoding(String imageData)
	{
		imageData = imageData.replace("data:image/png;base64,",""); // introduced in Release 1.6
		byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	}

	private void showAllOptions()
	{
		findViewById(R.id.radioOption1).setVisibility(View.VISIBLE);
		findViewById(R.id.radioOption2).setVisibility(View.VISIBLE);
		findViewById(R.id.radioOption3).setVisibility(View.VISIBLE);
		findViewById(R.id.radioOption4).setVisibility(View.VISIBLE);

		findViewById(R.id.textViewOption1).setVisibility(View.VISIBLE);
		findViewById(R.id.textViewOption2).setVisibility(View.VISIBLE);
		findViewById(R.id.textViewOption3).setVisibility(View.VISIBLE);
		findViewById(R.id.textViewOption4).setVisibility(View.VISIBLE);

/*		findViewById(R.id.imageViewOption1).setVisibility(View.VISIBLE);
		findViewById(R.id.imageViewOption1).setVisibility(View.VISIBLE);
		findViewById(R.id.imageViewOption1).setVisibility(View.VISIBLE);
		findViewById(R.id.imageViewOption1).setVisibility(View.VISIBLE);*/

		findViewById(R.id.tableRowOption1).setVisibility(View.VISIBLE);
		findViewById(R.id.tableRowOption2).setVisibility(View.VISIBLE);
		findViewById(R.id.tableRowOption3).setVisibility(View.VISIBLE);
		findViewById(R.id.tableRowOption4).setVisibility(View.VISIBLE);

	}

	private void setImageForQuestion(String imageData)
	{
		ImageView img = findViewById(R.id.imageViewQuestionImage);
		img.setVisibility(GONE);

		if(TextUtils.isEmpty(imageData))
		{
			return;
		}

		Bitmap dynamicBitmap = com.myAgeEducation.cbseClass7.utils.DynamicImageDispatcher.dispatch(this, imageData);
		if (dynamicBitmap != null) {
			img.setImageBitmap(dynamicBitmap);
			img.setVisibility(View.VISIBLE);
			setImageViewWidth(img, dynamicBitmap.getWidth());
			return;
		}

		if(imageData.length() < 1000) {
			int resourceIdentifier = getResources().getIdentifier(imageData, "drawable", getPackageName());
			if(resourceIdentifier != 0)
			{
				img.setImageResource(resourceIdentifier);
				img.setVisibility(View.VISIBLE);
			}
		}
		else {
			img.setImageBitmap(loadBitmapFromBase64Encoding(imageData));
			img.setVisibility(View.VISIBLE);
		}
	}

	private void setImageViewWidth(ImageView img, int bitmapWidth)
	{
		int availableWidth =
				getResources().getDisplayMetrics().widthPixels
						- (int)(16 * getResources().getDisplayMetrics().density);
		ViewGroup.LayoutParams params = img.getLayoutParams();
		params.width = Math.min(bitmapWidth, availableWidth);
		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		img.setLayoutParams(params);
	}

	private void setSupportiveText(String supportiveText)
	{
		findViewById(R.id.textViewSupportiveText).setVisibility(View.INVISIBLE);

		if(supportiveText == null)
		{
			return;
		}
		((TextView)findViewById(R.id.textViewSupportiveText)).setText(supportiveText);
		findViewById(R.id.textViewSupportiveText).setVisibility(View.VISIBLE);
	}

  public void setQuestionData(Question question)
  {
	  showAllOptions();
	  _linkText = "";

	  String imageData = question.getImage();
	  String supportiveText = question.getSupportiveText();

	  setImageForQuestion(imageData);
	  setSupportiveText(supportiveText);

	  if(imageData == null && supportiveText == null)
	  {
		  if(_currentQuestionNumber < 2)
		  {
			  displayAdImage();
		  }
	  }

	  TextView textViewQNum;

	  textViewQNum = findViewById(R.id.textViewQuestionNumber);
	  String myString = " Question " + _currentQuestionNumber + " of " + questionCount;
	  textViewQNum.setText(myString);
	    
	    // Set the Question
	  MathView webViewQuestionText;
	  webViewQuestionText = findViewById(R.id.webViewQuestionText);
	  String questionText = question.getQuestion();
	  webViewQuestionText.setEngine(MathView.Engine.KATEX);

	  webViewQuestionText.setText(questionText);//,"text/html", "UTF-8");

	  setOptions(question);
	    
	    answer = question.getAnswer();
  }

  private String selectedAnswer;

	public void onClickOptions(View view)
	{
		if (_currentQuestionNumber == questionCount)
		{
			ShowSubmitButton();
		}
		uncheckAllRadioButtons();
		enableOptions(false);

		int id = view.getId();
		RadioButton button = findViewById(id);

		MathView mathView;

		if (id == R.id.radioOption1) {
			((RadioButton) view).setChecked(true);
			if(Util.Subject.equalsIgnoreCase("maths")) {
				mathView = findViewById(R.id.textViewOption1);
				selectedAnswer = mathView.getText();
			}
			else {
				selectedAnswer = "a";
			}
		} else if (id == R.id.radioOption2) {
			((RadioButton) view).setChecked(true);
			if(Util.Subject.equalsIgnoreCase("maths")) {
				mathView = findViewById(R.id.textViewOption2);
				selectedAnswer = mathView.getText();
			}
			else {
				selectedAnswer = "b";
			}
		} else if (id == R.id.radioOption3) {
			((RadioButton) view).setChecked(true);
			if(Util.Subject.equalsIgnoreCase("maths")) {
				mathView = findViewById(R.id.textViewOption3);
				selectedAnswer = mathView.getText();
			}
			else {
				selectedAnswer = "c";
			}

		} else if (id == R.id.radioOption4) {
			((RadioButton) view).setChecked(true);
			if(Util.Subject.equalsIgnoreCase("maths")) {
				mathView = findViewById(R.id.textViewOption4);
				selectedAnswer = mathView.getText();
			}
			else {
				selectedAnswer = "d";
			}
		} else {
			selectedAnswer = "";
		}

		WhenAnswerSelected();
		//updateMarks();
	}

	private void updateMarks()
	{
		RadioButton radioOption1 = findViewById(R.id.radioOption1);
		RadioButton radioOption2 = findViewById(R.id.radioOption2);
		RadioButton radioOption3 = findViewById(R.id.radioOption3);
		RadioButton radioOption4 = findViewById(R.id.radioOption4);

		ImageView imageViewRight = findViewById(R.id.imageViewRight);
		ImageView imageViewWrong = findViewById(R.id.imageViewWrong);
		TextView textViewScore = findViewById(R.id.textViewScore);

		if (radioOption1.isChecked()) {
			if (TextUtils.equals(answer, selectedAnswer)) {
				correctAnswerCount++;
				imageViewRight.setVisibility(View.VISIBLE);
				imageViewWrong.setVisibility(View.INVISIBLE);
				isAnswerCorrect = true;
				//findViewById(R.id.option1).setBackgroundResource(R.drawable.tablerow_border_green);
			} else {
				imageViewWrong.setVisibility(View.VISIBLE);
				imageViewRight.setVisibility(View.INVISIBLE);
				isAnswerCorrect = false;

				//findViewById(R.id.option1).setBackgroundResource(R.drawable.tablerow_border_red);
			}
		}
		if (radioOption2.isChecked()) {
			if (TextUtils.equals(answer, "b")) {
				correctAnswerCount++;
				imageViewRight.setVisibility(View.VISIBLE);
				imageViewWrong.setVisibility(View.INVISIBLE);
				isAnswerCorrect = true;
			} else {
				imageViewWrong.setVisibility(View.VISIBLE);
				imageViewRight.setVisibility(View.INVISIBLE);
				isAnswerCorrect = false;
			}
		}
		if (radioOption3.isChecked()) {
			if (TextUtils.equals(answer, "c")) {
				correctAnswerCount++;
				imageViewRight.setVisibility(View.VISIBLE);
				imageViewWrong.setVisibility(View.INVISIBLE);
				isAnswerCorrect = true;
			} else {
				imageViewWrong.setVisibility(View.VISIBLE);
				imageViewRight.setVisibility(View.INVISIBLE);
				isAnswerCorrect = false;
			}
		}
		if (radioOption4.isChecked()) {
			if (TextUtils.equals(answer, "d")) {
				correctAnswerCount++;
				imageViewRight.setVisibility(View.VISIBLE);
				imageViewWrong.setVisibility(View.INVISIBLE);
				isAnswerCorrect = true;
			} else {
				imageViewWrong.setVisibility(View.VISIBLE);
				imageViewRight.setVisibility(View.INVISIBLE);
				isAnswerCorrect = false;
			}
		}

		textViewScore.setText("Correct: " + correctAnswerCount + "/" + questionCount);

		if(isAnswerCorrect)
		{
			totalScored = totalScored + 1;
		}
		else
		{
			revisionQuestions.add(_currentQuestion);
		}

		TextView textViewTotalScored = findViewById(R.id.textViewScore);
		textViewTotalScored.setText("Total scored: " + totalScored);
	}

	private void updateMarksOld()
	{
		RadioButton radioOption1 = findViewById(R.id.radioOption1);
		RadioButton radioOption2 = findViewById(R.id.radioOption2);
		RadioButton radioOption3 = findViewById(R.id.radioOption3);
		RadioButton radioOption4 = findViewById(R.id.radioOption4);

		ImageView imageViewRight = findViewById(R.id.imageViewRight);
		ImageView imageViewWrong = findViewById(R.id.imageViewWrong);
		TextView textViewScore = findViewById(R.id.textViewScore);

			if (radioOption1.isChecked()) {
				if (TextUtils.equals(answer, "a")) {
					correctAnswerCount++;
					imageViewRight.setVisibility(View.VISIBLE);
					imageViewWrong.setVisibility(View.INVISIBLE);
					isAnswerCorrect = true;
					//findViewById(R.id.option1).setBackgroundResource(R.drawable.tablerow_border_green);
				} else {
					imageViewWrong.setVisibility(View.VISIBLE);
					imageViewRight.setVisibility(View.INVISIBLE);
					isAnswerCorrect = false;

					//findViewById(R.id.option1).setBackgroundResource(R.drawable.tablerow_border_red);
				}
			}
			if (radioOption2.isChecked()) {
				if (TextUtils.equals(answer, "b")) {
					correctAnswerCount++;
					imageViewRight.setVisibility(View.VISIBLE);
					imageViewWrong.setVisibility(View.INVISIBLE);
					isAnswerCorrect = true;
				} else {
					imageViewWrong.setVisibility(View.VISIBLE);
					imageViewRight.setVisibility(View.INVISIBLE);
					isAnswerCorrect = false;
				}
			}
			if (radioOption3.isChecked()) {
				if (TextUtils.equals(answer, "c")) {
					correctAnswerCount++;
					imageViewRight.setVisibility(View.VISIBLE);
					imageViewWrong.setVisibility(View.INVISIBLE);
					isAnswerCorrect = true;
				} else {
					imageViewWrong.setVisibility(View.VISIBLE);
					imageViewRight.setVisibility(View.INVISIBLE);
					isAnswerCorrect = false;
				}
			}
			if (radioOption4.isChecked()) {
				if (TextUtils.equals(answer, "d")) {
					correctAnswerCount++;
					imageViewRight.setVisibility(View.VISIBLE);
					imageViewWrong.setVisibility(View.INVISIBLE);
					isAnswerCorrect = true;
				} else {
					imageViewWrong.setVisibility(View.VISIBLE);
					imageViewRight.setVisibility(View.INVISIBLE);
					isAnswerCorrect = false;
				}
			}

		textViewScore.setText("Correct: " + correctAnswerCount + "/" + questionCount);

		if(isAnswerCorrect)
		{
			totalScored = totalScored + 1;
		}
		else
        {
            revisionQuestions.add(_currentQuestion);
        }

		TextView textViewTotalScored = findViewById(R.id.textViewScore);
		textViewTotalScored.setText("Total scored: " + totalScored);
	}

	private void enableOptions(boolean enable)
	{
		findViewById(R.id.radioOption1).setEnabled(enable);
		findViewById(R.id.radioOption2).setEnabled(enable);
		findViewById(R.id.radioOption3).setEnabled(enable);
		findViewById(R.id.radioOption4).setEnabled(enable);
		findViewById(R.id.tableRowOption1).setEnabled(enable);
		findViewById(R.id.tableRowOption2).setEnabled(enable);
		findViewById(R.id.tableRowOption3).setEnabled(enable);
		findViewById(R.id.tableRowOption4).setEnabled(enable);
		findViewById(R.id.textViewOption1).setEnabled(enable);
		findViewById(R.id.textViewOption2).setEnabled(enable);
		findViewById(R.id.textViewOption3).setEnabled(enable);
		findViewById(R.id.textViewOption4).setEnabled(enable);

		findViewById(R.id.imageViewRight).setVisibility(View.INVISIBLE);
		findViewById(R.id.imageViewWrong).setVisibility(View.INVISIBLE);
	}

	public void onClickButtonOption1(View view)
	{
		onClickOptions(findViewById(R.id.radioOption1));
	}

	public void onClickButtonOption2(View view)
	{
		onClickOptions(findViewById(R.id.radioOption2));
	}

	public void onClickButtonOption3(View view)
	{
		onClickOptions(findViewById(R.id.radioOption3));
	}

	public void onClickButtonOption4(View view)
	{
		onClickOptions(findViewById(R.id.radioOption4));
	}

	public void onClickTableRow(View view)
	{
		int id = view.getId();
		if (id == R.id.tableRowOption1) {
			onClickOptions(findViewById(R.id.radioOption1));
		} else if (id == R.id.tableRowOption2) {
			onClickOptions(findViewById(R.id.radioOption2));
		} else if (id == R.id.tableRowOption3) {
			onClickOptions(findViewById(R.id.radioOption3));
		} else if (id == R.id.tableRowOption4) {
			onClickOptions(findViewById(R.id.radioOption4));
		}
	}

	private void uncheckAllRadioButtons()
	{
		findViewById(R.id.radioOption1).setEnabled(false);
		findViewById(R.id.radioOption2).setEnabled(false);
		findViewById(R.id.radioOption3).setEnabled(false);
		findViewById(R.id.radioOption4).setEnabled(false);
	}

	private void setOptions(Question question)
	{
		String option1 = question.getOption1();
		String option2 = question.getOption2();
		String option3 = question.getOption3();
		String option4 = question.getOption4();

		if(TextUtils.isEmpty(option1))
		{
			findViewById(R.id.tableRowOption1).setVisibility(GONE);
		}
		if(TextUtils.isEmpty(option2))
		{
			findViewById(R.id.tableRowOption2).setVisibility(GONE);
		}

		if(TextUtils.isEmpty(option3))
		{
			findViewById(R.id.tableRowOption3).setVisibility(GONE);
		}
		if(TextUtils.isEmpty(option4))
		{
			findViewById(R.id.tableRowOption4).setVisibility(GONE);
		}

		setOptionTextOrImage(question.getOption1(), findViewById(R.id.textViewOption1), findViewById(R.id.radioOption1));//, findViewById(R.id.imageViewOption1));
		setOptionTextOrImage(question.getOption2(), findViewById(R.id.textViewOption2), findViewById(R.id.radioOption2));//, findViewById(R.id.imageViewOption2));
		setOptionTextOrImage(question.getOption3(), findViewById(R.id.textViewOption3), findViewById(R.id.radioOption3));//, findViewById(R.id.imageViewOption3));
		setOptionTextOrImage(question.getOption4(), findViewById(R.id.textViewOption4), findViewById(R.id.radioOption4));//, findViewById(R.id.imageViewOption4));

		// to resolve the issue with text not fully shown for 3rd and 4th option

		findViewById(R.id.textViewOption1).invalidate();
		findViewById(R.id.textViewOption1).requestLayout();
		findViewById(R.id.textViewOption2).invalidate();
		findViewById(R.id.textViewOption2).requestLayout();
		findViewById(R.id.textViewOption3).invalidate();
		findViewById(R.id.textViewOption3).requestLayout();
		findViewById(R.id.textViewOption4).invalidate();
		findViewById(R.id.textViewOption4).requestLayout();
	}

	final String imageHeaderPng = "data:image/png;base64,";
	final String imageHeaderJpg = "data:image/jpeg;base64,";

	private void setOptionTextOrImage(String option, MathView mathView, RadioButton radioButton)//, ImageView imageViewOption)
	{
		mathView.setVisibility(View.VISIBLE);
		radioButton.setVisibility(View.VISIBLE);
		//imageViewOption.setVisibility(View.VISIBLE);

		if(TextUtils.isEmpty(option))
		{
			return;
		}
		mathView.setText(option);
		//mathView.reload();
		mathView.invalidate();
	}

	private void displayAdImage()
	{
		if(Util.adData == null)
		{
			return;
		}

		if(Util.adData.getImage() == null)
		{
			return;
		}

		ImageView img = findViewById(R.id.imageView1);
		if (!Util.adData.getImage().isEmpty()) {
			img.setImageBitmap(loadBitmapFromBase64Encoding(Util.adData.getImage()));
			img.setVisibility(View.VISIBLE);
			_linkText = Util.adData.getLinkText();
		}
	}

  public void SetAnswerFeedback()
  {
	  //boolean isAnsCorrect = radioSelectedButton.getText().equals(answer);
	  
	  if(TextUtils.equals(selectedAnswer, answer))
	  {
		  correctAnswerCount++;
		  findViewById(R.id.imageViewRight).setVisibility(View.VISIBLE);
	  }
	  else
	  {
		  revisionQuestions.add(_currentQuestion);
		  findViewById(R.id.imageViewWrong).setVisibility(View.VISIBLE);
	  }

      TextView tv = findViewById(R.id.textViewScore);
	  tv.setText("Score: " + correctAnswerCount + "/" + questionCount);
  }
  
  public void EnableAnswers(boolean val)
  {
	  RadioButton button = findViewById(R.id.radioOption1);
      button.setEnabled(val);

      button = findViewById(R.id.radioOption2);
      button.setEnabled(val);

      button = findViewById(R.id.radioOption3);
      button.setEnabled(val);

      button = findViewById(R.id.radioOption4);
      button.setEnabled(val);

	  findViewById(R.id.imageViewRight).setVisibility(View.INVISIBLE);
	  findViewById(R.id.imageViewWrong).setVisibility(View.INVISIBLE);

	  enableOptions(true);
  }

    private void ShowSubmitButton()
    {
		tableLayout1.setVisibility(View.VISIBLE);
		Button buttonSubmit = findViewById(R.id.buttonNext);
		buttonSubmit.setText("Submit");

		Button exitButton = findViewById(R.id.buttonExitTest);
		exitButton.setEnabled(false);
		exitButton.setBackgroundColor(Color.DKGRAY);
    }

  private void WhenAnswerSelected()
  {
      try {
		  enableOptions(false);
		  SetAnswerFeedback();
          buttonNext.setEnabled(true);
		  buttonNext.setVisibility(View.VISIBLE);
		  tableLayout1.setVisibility(View.VISIBLE);

          if (_currentQuestionNumber == questionCount)
		  {
              ShowSubmitButton();
          }
		  else
		  {
              if (_automaticallyMoveToNextQuestion)
			  {
				  moveToNextQuestion();
				  enableOptions(true);
              }
          }
      }
      catch(Exception e)
      {
          displayAlertBox(e.getMessage());
      }
  }

  public void addRadioButtonListener(){
	  RadioButton b = findViewById(R.id.radioOption1);
	  b.setOnClickListener(new OnClickListener(){
		  @Override
			public void onClick(View v) {
			  RadioButton b1 = findViewById(R.id.radioOption1);
			  if(b1.isChecked())
			  {
                  radioSelectedButton = findViewById(R.id.radioOption1);
                  WhenAnswerSelected();
			  }
		  }
	  });
	  
	  b = findViewById(R.id.radioOption2);
	  b.setOnClickListener(v -> {

          RadioButton b2 = findViewById(R.id.radioOption2);
          if(b2.isChecked())
          {
              radioSelectedButton = findViewById(R.id.radioOption2);
              WhenAnswerSelected();
          }
      });
	  
	  b = findViewById(R.id.radioOption3);
	  b.setOnClickListener(v -> {
          RadioButton b3 = findViewById(R.id.radioOption3);
          if(b3.isChecked())
          {
              radioSelectedButton = findViewById(R.id.radioOption3);
WhenAnswerSelected();
          }
      });
	  
	  b = findViewById(R.id.radioOption4);
	  b.setOnClickListener(v -> {
          RadioButton b4 = findViewById(R.id.radioOption4);
          if (b4.isChecked()) {
              radioSelectedButton = findViewById(R.id.radioOption4);
              WhenAnswerSelected();
          }
      });
  }

    private void HideButtonNext()
	{
        tableLayout1.setVisibility(GONE);
	}

  public void moveToNextQuestion()
  {
	  HideButtonNext();

	  try
	  {
          if (_currentQuestionNumber <= questionCount)
		  {
              if (_isRandomQuestions)
			  {
				  try {
					  _questionIndex = getRandomQuestionNumber();
					  _usedNumbers.add(_questionIndex);
				  }
				  catch(Exception e)
				  {
                      Util.displayAlert("Questions could not be retrieved, please try again","Error", QuestionPage.this);
					  finish();
				  }
              }
			  else
			  {
				  _questionIndex = _currentQuestionNumber;
              }

			  if(Util.Android_id.equalsIgnoreCase("6d692d322d2df2fb") || Util.Android_id.equalsIgnoreCase("e64b49e28d3e849c")) {
				  displayQuestionSetAndQuestionNumber();
			  }

			  _currentQuestion = QuestionList.get(_questionIndex);
              _currentQuestionNumber++;
			  setQuestionData(_currentQuestion);
			  enableOptions(true);

              RadioButton b = findViewById(R.id.radioOption1);
              b.setChecked(false);

              b = findViewById(R.id.radioOption2);
              b.setChecked(false);

              b = findViewById(R.id.radioOption3);
              b.setChecked(false);

              b = findViewById(R.id.radioOption4);
              b.setChecked(false);
              buttonNext.setEnabled(false);
		  }
      }
      catch (Exception e)
      {
          displayAlertBox(e.getMessage());
      }
  }

	private void displayQuestionSetAndQuestionNumber()
	{
		((TextView) findViewById(R.id.textViewSubject)).setText("Subject: " + Util.Subject + "/" + _questionSet + "/" + _questionIndex);
	}

	private void submitTest()
	{
		clearState(); // Test is completed.. so remove the saved state
		openTestReportActivity();
		finish();
		showInterstitialAdAd();
	}

	private void writeSingleStringValueToCloud(String nodePath, String nodeChild, String nodeValue)
	{
		FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
		DatabaseReference databaseReference = firebaseDatabase.getReference(nodePath + "/" + nodeChild);
		databaseReference.setValue(nodeValue);
	}

	private void writeSingleIntegerValueToCloud(String nodePath, String nodeChild, int nodeValue)
	{
		FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
		DatabaseReference databaseReference = firebaseDatabase.getReference(nodePath + "/" + nodeChild);
		databaseReference.setValue(nodeValue);
	}

	/*private void SaveLastAttemptScore()
	{
		//Firebase.goOnline();
		Log.d("Server_Time", "Getting Server Time");
		DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
		offsetRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				Firebase.goOffline();
				double offset = snapshot.getValue(Double.class);
				Util.ServerTime = System.currentTimeMillis() + offset;

				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
				SimpleDateFormat monthYear = new SimpleDateFormat("MMM-yyyy");
				long temp = (new Double(Util.ServerTime)).longValue();
				Date resultDate = new Date(temp);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(resultDate);
				int date = calendar.get(Calendar.DATE);

				// Contest was started probably during midnight and was completed on the 1st of the month
				if(date < 10)
				{
					TextView textView = findViewById(R.id.textViewTimeOut);
					textView.setText(getString(R.string.contest_closed));
					Button button = findViewById(R.id.buttonNext);
					button.setText(getString(R.string.close));
					return;
				}

				Log.d("Server_Time", sdf.format(resultDate));
				String nodePath = Util.ContestClassRoot + "/" + monthYear.format(resultDate) + "/contest_details_users/" + Util.UserUid + "/" + Util.Subject;
				//String nodePath = Util.ContestUserRoot + "/" + monthYear.format(resultdate) + "/" + Util.UserUid + "/" + Util.Subject;
				writeSingleIntegerValueToCloud(nodePath,"lastAttemptScore", correctAnswerCount);
				writeSingleStringValueToCloud(nodePath, "lastAttemptTime", sdf.format(resultDate));
				//int attemptNumber = Util.testAttemptDetails.getAttempts() + 1;
				int attemptNumber = ((PojoTestAttemptDetails)Util.SubjectTestAttemptDetailsMaps.get(Util.Subject)).getAttempts() + 1;
				writeSingleIntegerValueToCloud(nodePath,"attempts", attemptNumber);
				String attemptCountScore = "attempt" + String.valueOf(attemptNumber) + "Score";
				writeSingleIntegerValueToCloud(nodePath, attemptCountScore, correctAnswerCount);

				PojoTestAttemptDetails pojoTestAttemptDetails = (PojoTestAttemptDetails)Util.SubjectTestAttemptDetailsMaps.get(Util.Subject);

				if(pojoTestAttemptDetails != null) {
					pojoTestAttemptDetails.setLastAttemptScore(correctAnswerCount);
				}

				openTestReportActivity();
				finish();
			}

			@Override
			public void onCancelled(DatabaseError error) {
				Firebase.goOffline();
				//System.err.println("Listener was cancelled");
			}
		});
	}*/

  public void addButtonListener() {

	  buttonNext.setOnClickListener(new OnClickListener() {

		  @Override
		  public void onClick(View v) {
			  //saveStateOfTest();
			  if(buttonNext.getText().toString().compareToIgnoreCase("submit") == 0)
              {
                  submitTest();
			  }
			  else if(buttonNext.getText().toString().compareToIgnoreCase(getString(R.string.close)) == 0)
			  {
			  	   finish();
			  }
			  else
			  {
                  moveToNextQuestion();
			  }
		  }
	  });

      Button buttonExitTest = findViewById(R.id.buttonExitTest);
      buttonExitTest.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
              if(!QuestionPage.this.isFinishing()) {
                  displayAlertWithOkCancel("Are you sure to exit the test", "Exit?", QuestionPage.this);
              }
          }
      });
  }

	private void openTestReportActivity()
	{
		Intent testReport = new Intent();
		testReport.setClassName(Util.PACKAGE_NAME, Util.PACKAGE_NAME + ".TestReport");
		testReport.putExtra("correct_ans_count", String.valueOf(correctAnswerCount));
		testReport.putExtra("questionCount", String.valueOf(questionCount));
		testReport.putExtra("isRevision", isRevision);
		testReport.putExtra("reward", reward);
		testReport.putExtra("points", rewardPoints);
		testReport.putExtra("set_number", _setNumber);
		testReport.putExtra("chapter_number", _chapterNumber);
		Util.revisionQuestions = (ArrayList<Question>)revisionQuestions.clone();
		startActivity(testReport);
	}
  
  public void clearState()
  {
	  String fileName = Util.SCHOOL_NAME + "_" + Util.GRADE + "_" + Util.Subject + "_state.txt";

	  try
	  {
		  FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
			
		  OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
		  BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
          bufferedWriter.write("");
          bufferedWriter.close();
		  outputStreamWriter.close();
		  fileOutputStream.close();
	  }
	  catch(IOException e)
	  {
		  displayAlertBox("ERROR-404:" + e.getMessage());
	  }
  }

	public void onClickAdImage(View view)
	{
	    if(_linkText == null)
		{
			return;
		}

		if(_linkText.isEmpty())
		{
			return;
		}

		try {
			//saveIfAdClicked();
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(_linkText));
			startActivity(intent);
		}
		catch(Exception e)
		{
			Log.d("ADIMAGEERROR", e.getMessage());
		}
	}

	public int getRandomQuestionNumber()
	{
		Random random = new Random();
		int generatedRandomNumber;
		generatedRandomNumber = random.nextInt(_questionNumbers.size());
		Log.d("QuestionNumbersSize", String.valueOf(_questionNumbers.size()));
		int questionNumber = ((Integer)(_questionNumbers.get(generatedRandomNumber))).intValue();
		_questionNumbers.remove(generatedRandomNumber);
		return questionNumber;
	}
}
