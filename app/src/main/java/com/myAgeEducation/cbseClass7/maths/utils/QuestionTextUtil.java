package com.myAgeEducation.cbseClass7.maths.utils;

import java.util.Random;

public class QuestionTextUtil
{
    private static final Random RANDOM = new Random();

    public static String random(String... texts)
    {
        return texts[RANDOM.nextInt(texts.length)];
    }
}