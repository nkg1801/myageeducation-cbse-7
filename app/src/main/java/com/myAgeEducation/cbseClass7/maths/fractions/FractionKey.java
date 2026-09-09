package com.myAgeEducation.cbseClass7.maths.fractions;

import androidx.annotation.NonNull;

public class FractionKey
{
    public final int numerator;
    public final int denominator;

    public FractionKey(int numerator, int denominator)
    {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @NonNull
    @Override
    public String toString()
    {
        return numerator + "/" + denominator;
    }
}