package com.myAgeEducation.cbseClass7.maths.pattern;

public class NumberPatternQuestionData
{
    public NumberPatternData patternData;

    public String questionText;

    public String correctAnswer;

    public String[] options;


    public NumberPatternQuestionData(
            NumberPatternData patternData,
            String questionText,
            String correctAnswer)
    {
        this.patternData = patternData;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }
}
