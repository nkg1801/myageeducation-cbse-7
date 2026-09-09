package com.myAgeEducation.cbseClass7.maths.pattern;

public class MonthPatternQuestionData
{
    public MonthPatternData patternData;

    public String questionText;
    public String correctAnswer;
    public String[] options;


    public MonthPatternQuestionData(
            MonthPatternData patternData,
            String questionText,
            String correctAnswer)
    {
        this.patternData = patternData;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }
}