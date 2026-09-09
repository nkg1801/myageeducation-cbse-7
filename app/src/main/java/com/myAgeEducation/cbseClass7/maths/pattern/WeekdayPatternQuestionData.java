package com.myAgeEducation.cbseClass7.maths.pattern;

public class WeekdayPatternQuestionData
{
    public WeekdayPatternData patternData;

    public String questionText;
    public String correctAnswer;
    public String[] options;

    public WeekdayPatternQuestionData(
            WeekdayPatternData patternData,
            String questionText,
            String correctAnswer)
    {
        this.patternData = patternData;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }
}