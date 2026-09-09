package com.myAgeEducation.cbseClass7.maths.placevalue.rounding;


import java.util.List;

public class RoundingQuestionData
{
    private final String questionText;
    private final List<String> options;
    private final String answer;

    public RoundingQuestionData(String questionText, List<String> options, String answer)
    {
        this.questionText = questionText;
        this.options = options;
        this.answer = answer;
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public List<String> getOptions()
    {
        return options;
    }

    public String getAnswer()
    {
        return answer;
    }
}
