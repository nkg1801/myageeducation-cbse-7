package com.myAgeEducation.cbseClass7;

import static android.view.View.VISIBLE;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.myAgeEducation.cbseClass7.maths.LineAndAngle.AngleQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.LineAndAngle.LineAndAngleArithmeticQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.LineAndAngle.LineAndAngleQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.divisibility.DivisibilityQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.factors.FactorQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.geometricalideas.BasicGeometricalIdeasQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.hcf.HcfQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.lcm.LcmQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.multiples.MultipleQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.practicalgeometry.PracticalGeometryQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.charts.BarChartQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.circlegraph.CircleGraphQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.datahandling.DataHandlingArithmeticQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.datahandling.DataHandlingConceptQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.decimals.DecimalQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.EquivalentFractionQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionAgeQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionComparisonQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionConceptQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionOfMeasurementQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionOfNumberQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionSeriesQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionStoryQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionTimeStoryQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionTrueFalseQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionTypes;
import com.myAgeEducation.cbseClass7.maths.algebra.AlgebraicExpressionsQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.congruence.CongruenceQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.exponents.ExponentsQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.integers.IntegerQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.percentages.PercentageQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.perimeterarea.PerimeterAreaQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.primecomposite.PrimeCompositeQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.rationalnumbers.RationalNumberQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.simpleequations.SimpleEquationQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.triangles.TrianglePropertyQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.pictograph.PictographQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.symmetry.SymmetryQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.ratioandproportion.ComparingQuantitiesQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.tabularquestions.TableQuestionGenerator;
import com.myAgeEducation.cbseClass7.maths.visualizingsolidshapes.VisualizingSolidShapesQuestionGenerator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionLoaderActivity extends Activity {
    private volatile boolean generationFinished = false;
    private final Handler handler =  new Handler(Looper.getMainLooper());
    TextView textViewCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_loader);
        textViewCounter = findViewById(R.id.textViewCounter);

        readBundle();

        if(Util.IsUnderAutomaticTest) {
            textViewCounter.setVisibility(VISIBLE);
            textViewCounter.setText(String.valueOf(Util.AutoTestCount));
            handler.postDelayed(() -> {

                if (generationFinished) {
                    Log.d("TEST", "Generation completed. Going back.");
                    Util.AutoTestCount++;
                    finish();
                }
                else {
                    Log.e("TEST", "Generation still running after 5 sec.");

                    // Do nothing.
                    // Stay on Y./
                }

            }, 5000);
        }

        setHeaderImage();
        Util.allQuestions.clear();
        
        new Thread(() -> {
            try {
                long startTime = System.currentTimeMillis();
                addGeneratedQuestionsForMaths();
                generationFinished = true;
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                Log.d("QuestionLoader", "Total loading time: " + duration + "ms");

                // Ensure loader stays for at least 3 seconds
                if (duration < 2000) {
                    try {
                        Thread.sleep(2000 - duration);
                    } catch (InterruptedException ignored) {
                    }
                }
            } catch (Exception e) {
                Log.e("QuestionLoader", "Error generating questions", e);
            } finally {
                if(!Util.IsUnderAutomaticTest) {
                    runOnUiThread(() -> {
                        openTestActivity(_setNumber);
                        finish();
                    });
                }
            }
        }).start();
    }

    int _setNumber;
    int _chapterNumber;
    private void readBundle()
    {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            _setNumber = bundle.getInt("set_number");
            _chapterNumber = bundle.getInt("chapter_number");
        }
    }

    public void openTestActivity(int setNumber)
    {
        Bundle bundle = getIntent().getExtras();
        QuestionPage.QuestionList = Util.allQuestions;

        if (Util.allQuestions.isEmpty()) {
            Util.displayAlert("Questions could not be generated for this chapter yet.", "Work in Progress", QuestionLoaderActivity.this);
            finish();
            return;
        }

        Intent testPage = new Intent();
        testPage.setClassName(Util.PACKAGE_NAME, Util.PACKAGE_NAME + ".QuestionPage");

        int questionCount = Util.allQuestions.size();

        testPage.putExtra("questionCount", questionCount);
        testPage.putExtra("isRevision", "false");
        testPage.putExtra("isExit", "false");
        testPage.putExtra("recover_mode", false);
        if (bundle != null) {
            testPage.putExtra("question_set", bundle.getString("question_set"));
        }
        testPage.putExtra("chapter_number", _chapterNumber);
        testPage.putExtra("set_number", setNumber);

        testPage.putStringArrayListExtra("wrongAns_list", null);
        testPage.putIntegerArrayListExtra("used_numbers", null);

        startActivity(testPage);
    }

    private void updateLoadingText(String text) {
        runOnUiThread(() -> {
            TextView textView = findViewById(R.id.progressMessage);
            if (textView != null) {
                textView.setText(text);
            }
        });
    }

    private void addGeneratedQuestionsForMaths()
    {
        long start;

        switch(_chapterNumber)
        {
            case 1:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #1");
                addQuestionsForChapterOne();
                Log.d("QuestionLoader", "Chapter 1 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 2:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #2");
                addQuestionsForChapterTwo();
                Log.d("QuestionLoader", "Chapter 2 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 3:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #3");
                addQuestionsForChapterThree();
                Log.d("QuestionLoader", "Chapter 3 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 4:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #4");
                addQuestionsForChapterFour();
                Log.d("QuestionLoader", "Chapter 4 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 5:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #5");
                addQuestionsForChapterFive();
                Log.d("QuestionLoader", "Chapter 5 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 6:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #6");
                addQuestionsForChapterSix();
                Log.d("QuestionLoader", "Chapter 6 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 7:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #7");
                addQuestionsForChapterSeven();
                Log.d("QuestionLoader", "Chapter 7 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 8:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #8");
                addQuestionsForChapterEight();
                Log.d("QuestionLoader", "Chapter 8 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 9:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #9");
                addQuestionsForChapterNine();
                Log.d("QuestionLoader", "Chapter 9 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 10:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #10");
                addQuestionsForChapterTen();
                Log.d("QuestionLoader", "Chapter 10 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 11:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #11");
                addQuestionsForChapterEleven();
                Log.d("QuestionLoader", "Chapter 11 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 12:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #12");
                addQuestionsForChapterTwelve();
                Log.d("QuestionLoader", "Chapter 12 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 13:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #13");
                addQuestionsForChapterThirteen();
                Log.d("QuestionLoader", "Chapter 13 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 14:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #14");
                addQuestionsForChapterFourteen();
                Log.d("QuestionLoader", "Chapter 14 took: " + (System.currentTimeMillis() - start) + "ms");
                break;

            case 15:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #15");
                addQuestionsForChapterFifteen();
                Log.d("QuestionLoader", "Chapter 15 took: " + (System.currentTimeMillis() - start) + "ms");

            default:
                start = System.currentTimeMillis();
                updateLoadingText("Loading questions for Chapter #15");
                addQuestionsForChapterSixteen();
                Log.d("QuestionLoader", "Chapter 15 took: " + (System.currentTimeMillis() - start) + "ms");
        }
    }

    // updated for class 7
    private void addQuestionsForChapterOne()
    {
        int chapterNumber = 1;
        String chapterName = "Integers";

        for(int i = 0; i < 20; i++)
        {
            Question question = IntegerQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // not fully updated for class 7. add more if possible
    private void addQuestionsForChapterTwo() {
        int chapterNumber = 2;
        String chapterName = "Fractions and Decimals";
        Question question;

        for (int i = 0; i < 20; i++) {
            if(RANDOM.nextBoolean()) {
                question = fractionQuestionGenerator();
            }
            else {
                question = DecimalQuestionGenerator.generateQuestion();
            }

            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    private Question fractionQuestionGenerator() {
        FractionTypes[] types = FractionTypes.values();
        FractionTypes type = types[RANDOM.nextInt(types.length)];

        switch (type) {
            case FRACTION_SERIES:
                return FractionSeriesQuestionGenerator.generateQuestion();

            case STORY_TYPE:
                return FractionStoryQuestionGenerator.generateRemainingQuestion();

            case FRACTION_WITH_AGE:
                return FractionAgeQuestionGenerator.generateQuestion();

            case FRACTION_OF_NUMBER:
                return FractionOfNumberQuestionGenerator.generateQuestion();

            case FRACTION_OF_MEASUREMENT_DATA:
                return FractionOfMeasurementQuestionGenerator.generateQuestion();

            case FRACTION_TIME_STORY:
                return FractionTimeStoryQuestionGenerator.generateQuestion();

            case FRACTION_TRUE_FALSE:
                return FractionTrueFalseQuestionGenerator.generateQuestion();

            case FRACTION_CONCEPTS:
                return FractionConceptQuestionGenerator.generateQuestion();

            case EQUIVALENT_FRACTIONS:
                //checked
                return EquivalentFractionQuestionGenerator.generateQuestion();

            case FRACTION_COMPARISON:
                return FractionComparisonQuestionGenerator.generateQuestion();

            default:
                return FractionQuestionGenerator.generateQuestion();
        }
    }

    // updated for class 7. add more if possible for mean, average types etc
    private void addQuestionsForChapterThree()
    {
        int chapterNumber = 3;
        String chapterName = "Data Handling";
        final Random RANDOM = new Random();
        int randomNumber;

        Question question;

        for(int i = 0; i < 20; i++) {
            randomNumber = RANDOM.nextInt(100);

            if(randomNumber < 90) // 90%  // this is specific to class 7. all below are for revisions from earlier classes
            {
                question = DataHandlingArithmeticQuestionGenerator.generateQuestion();

            }
            else if(randomNumber < 92) // 2%
            {
                question = BarChartQuestionGenerator.generateQuestion();
            }
            else if(randomNumber < 94) // 2%
            {
                question = CircleGraphQuestionGenerator.generateQuestion();
            }
            else if(randomNumber < 96) // 2%
            {
                question = TableQuestionGenerator.generateQuestion();
            }
            else if(randomNumber < 98) // 2%
            {
                question = PictographQuestionGenerator.generateQuestion();
            }
            else // 2%
            {
                question = DataHandlingConceptQuestionGenerator.generateQuestion();
            }

            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterFour()
    {
        int chapterNumber = 4;
        String chapterName = "Simple Equations";

        for(int i = 0; i < 20; i++)
        {
            Question question = SimpleEquationQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7. add more types if possible
    private void addQuestionsForChapterFive()
    {
        int chapterNumber = 5;
        String chapterName = "Lines and Angles";
        final Random RANDOM = new Random();
        int randomNumber;

        for(int i=0; i < 20; i++)
        {
            Question question;
            randomNumber = RANDOM.nextInt(100);
            if(randomNumber < 40) {
                question = AngleQuestionGenerator.generateQuestion();
            }
            else if(randomNumber < 70) {
                question = LineAndAngleQuestionGenerator.generateQuestion();
            }
            else {
                question = LineAndAngleArithmeticQuestionGenerator.generateQuestion();
            }
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterSix()
    {
        int chapterNumber = 6;
        String chapterName = "The Triangles and its Properties";

        for(int i = 0; i < 20; i++)
        {
            Question question = TrianglePropertyQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterSeven()
    {
        int chapterNumber = 7;
        String chapterName = "Congruence of triangles";

        for(int i = 0; i < 20; i++)
        {
            Question question = CongruenceQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterEight()
    {
        int chapterNumber = 8;
        String chapterName = "Comparing Quantities";

        for(int i = 0; i < 20; i++) {
            Question question;
            if (RANDOM.nextBoolean()) {
                question = ComparingQuantitiesQuestionGenerator.generateQuestion();
            } else {
                question = PercentageQuestionGenerator.generateQuestion();
            }
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterNine()
    {
        int chapterNumber = 9;
        String chapterName = "Rational Numbers";

        for(int i = 0; i < 20; i++) {
            Question question = RationalNumberQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterTen()
    {
        int chapterNumber = 10;
        String chapterName = "Practical Geometry";

        for(int i = 0; i < 20; i++)
        {
            Question question;
            if (RANDOM.nextBoolean()) {
                question = PracticalGeometryQuestionGenerator.generateQuestion();
            } else {
                question = BasicGeometricalIdeasQuestionGenerator.generateQuestion();
            }
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // check if update is required for class 7
    private void addQuestionsForChapterEleven()
    {
        int chapterNumber = 11;
        String chapterName = "Perimeter and Area";

        for(int i = 0; i < 20; i++)
        {
            Question question;
            question = PerimeterAreaQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterTwelve()
    {
        int chapterNumber = 12;
        String chapterName = "Algebraic Expressions";

        for(int i = 0; i < 20; i++)
        {
            Question question = AlgebraicExpressionsQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterThirteen()
    {
        int chapterNumber = 13;
        String chapterName = "Exponents and Powers";

        for(int i = 0; i < 20; i++)
        {
            Question question = ExponentsQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterFourteen()
    {
        int chapterNumber = 14;
        String chapterName = "Symmetry";

        for(int i = 0; i < 20; i++)
        {
            Question question = SymmetryQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    // updated for class 7
    private void addQuestionsForChapterFifteen()
    {
        int chapterNumber = 15;
        String chapterName = "Visualizing Solid Shapes";

        for(int i = 0; i < 20; i++)
        {
            Question question = VisualizingSolidShapesQuestionGenerator.generateQuestion();
            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    private void addQuestionsForChapterSixteen()
    {
        int chapterNumber = 16;
        String chapterName = "LCM and HCF";
        final Random RANDOM = new Random();
        int randomNumber;

        for(int i=0; i < 20; i++) {
            Question question;
            randomNumber = RANDOM.nextInt(100);
            if (randomNumber < 15) {
                question = LcmQuestionGenerator.generateQuestion();
            } else if (randomNumber < 30) {
                question = HcfQuestionGenerator.generateQuestion();
            } else if (randomNumber < 45) {
                question = PrimeCompositeQuestionGenerator.generateQuestion();
            } else if (randomNumber < 60) {
                question = DivisibilityQuestionGenerator.generateQuestion();
            } else if (randomNumber < 80) {
                question = MultipleQuestionGenerator.generateQuestion();
            } else {
                question = FactorQuestionGenerator.generateQuestion();
            }

            question.setChapter(chapterNumber);
            question.setChapterName(chapterName);
            Util.allQuestions.add(question);
        }
    }

    private void setHeaderImage()
    {
        ImageView img = findViewById(R.id.imgEducation);
        int resourceIdentifier = HEADER_IMAGES.get(ThreadLocalRandom.current().nextInt(HEADER_IMAGES.size()));
        img.setImageResource(resourceIdentifier);

        Size size = getDrawableSize(this, resourceIdentifier);

        int IMAGE_WIDTH = 600;
        float factor = (float) IMAGE_WIDTH / size.getWidth();
        int width = (int) (size.getWidth() * factor);
        int height = (int)(size.getHeight() * factor);
        setImageViewWidthHeight(img, width/2, height/2);
    }

    public Size getDrawableSize(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),resId,options);
        return new Size(options.outWidth, options.outHeight);
    }

    private void setImageViewWidthHeight(ImageView img, int width, int height)
    {
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.width = width;
        params.height = height;
        img.setLayoutParams(params);
        img.setTop(20);
    }

    private static final Random RANDOM = new Random();

    private static final List<Integer> HEADER_IMAGES = List.of(
            R.drawable.thinking_owl,
            R.drawable.blue_bird,
            R.drawable.ant_thinking,
            R.drawable.boy_thinking,
            R.drawable.girl_thinking,
            R.drawable.tortoise,
            R.drawable.snail_thinking,
            R.drawable.slate_thinking,
            R.drawable.school_bag_thinking,
            R.drawable.puppy,
            R.drawable.protector_thinking,
            R.drawable.plus_thinking,
            R.drawable.pie_thinking
    );
}
