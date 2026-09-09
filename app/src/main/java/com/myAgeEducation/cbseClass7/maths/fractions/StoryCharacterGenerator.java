package com.myAgeEducation.cbseClass7.maths.fractions;

import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;

import java.util.Random;

public class StoryCharacterGenerator
{
    private static final Random RANDOM = new Random();

    public static StoryCharacter randomCharacter()
    {
        if (RANDOM.nextBoolean())
        {
            return new StoryCharacter(PersonNameUtil.getMaleName(), PersonNameUtil.getFemaleName(), "He", "him", "his");
        }
        else
        {
            return new StoryCharacter(
                    PersonNameUtil.getFemaleName(),
                    PersonNameUtil.getMaleName(),
                    "She",
                    "her",
                    "her");
        }
    }
}