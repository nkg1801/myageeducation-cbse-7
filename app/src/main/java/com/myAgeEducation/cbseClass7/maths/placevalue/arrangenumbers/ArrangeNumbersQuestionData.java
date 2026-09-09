package com.myAgeEducation.cbseClass7.maths.placevalue.arrangenumbers;

public class ArrangeNumbersQuestionData
{
    public final int[] originalNumbers;
    public final int[] arrangedNumbers;
    public final boolean ascending;

    public ArrangeNumbersQuestionData(
            int[] originalNumbers,
            int[] arrangedNumbers,
            boolean ascending)
    {
        this.originalNumbers = originalNumbers;
        this.arrangedNumbers = arrangedNumbers;
        this.ascending = ascending;
    }
}