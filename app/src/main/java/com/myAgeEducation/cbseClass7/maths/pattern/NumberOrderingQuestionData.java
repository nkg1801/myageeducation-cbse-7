package com.myAgeEducation.cbseClass7.maths.pattern;

public class NumberOrderingQuestionData
{
    public final int[] numbers;

    public final NumberOrderingQuestionType type;

    public final int position;


    public NumberOrderingQuestionData(
            int[] numbers,
            NumberOrderingQuestionType type,
            int position)
    {
        this.numbers = numbers;
        this.type = type;
        this.position = position;
    }
}
