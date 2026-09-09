package com.myAgeEducation.cbseClass7.maths.decimals;

public class DecimalArithmeticQuestionData
{
    private final String question;
    private final String answer;
    private final String[] options;
    private final DecimalArithmeticQuestionType type;

    public DecimalArithmeticQuestionData(
            String question,
            String answer,
            String[] options,
            DecimalArithmeticQuestionType type)
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

    public DecimalArithmeticQuestionType getType()
    {
        return type;
    }
}
