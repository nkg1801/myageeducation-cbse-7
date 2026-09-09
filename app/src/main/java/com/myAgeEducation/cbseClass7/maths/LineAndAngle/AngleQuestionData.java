package com.myAgeEducation.cbseClass7.maths.LineAndAngle;
import android.graphics.Bitmap;

public class AngleQuestionData
{
    private final String question;
    private final String answer;
    private final String[] options;
    private final AngleQuestionType type;
    private final int angle;
    private String imageCode;

    public AngleQuestionData(
            String question,
            String answer,
            String[] options,
            AngleQuestionType type,
            int angle)
    {
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.type = type;
        this.angle = angle;
    }

    public void setImageCode(String imageCode)
    {
        this.imageCode = imageCode;
    }

    public String getImageCode()
    {
        return imageCode;
    }

    public String getQuestion()
    {
        return question;
    }

    public String getAnswer()
    {
        return answer;
    }

    public String[] getOptions()
    {
        return options;
    }

    public AngleQuestionType getType()
    {
        return type;
    }

    public int getAngle()
    {
        return angle;
    }
}
