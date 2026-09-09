package com.myAgeEducation.cbseClass7.maths.average;

public class AverageQuestionData
{
    private final String question;
    private final String answer;
    private final String[] options;
    private final AverageQuestionType type;

    public AverageQuestionData(
            String question,
            String answer,
            String[] options,
            AverageQuestionType type)
    {
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.type = type;
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

    public AverageQuestionType getType()
    {
        return type;
    }
}
