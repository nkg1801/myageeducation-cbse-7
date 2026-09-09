package com.myAgeEducation.cbseClass7.maths.pattern;

import java.util.Random;

public class MonthPatternGenerator
{
    private static final Random RANDOM =
            new Random();

    private static final String[] MONTHS =
            {
                    "January",
                    "February",
                    "March",
                    "April",
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "December"
            };


    public static MonthPatternData generate()
    {
        // Step 1 = consecutive months
        // Step 2 = alternate months
        int step =
                RANDOM.nextBoolean()
                        ? 1
                        : 2;

        // Keep 4 items in the pattern
        int length = 4;

        int maximumStart =
                MONTHS.length
                        - 1
                        - ((length - 1) * step);

        int startIndex =
                RANDOM.nextInt(
                        maximumStart + 1);

        String[] sequence =
                new String[length];

        for (int i = 0; i < length; i++)
        {
            sequence[i] =
                    MONTHS[
                            startIndex
                                    + (i * step)];
        }

        // Avoid hiding the first item
        int missingIndex =
                1 + RANDOM.nextInt(
                        length - 1);

        return new MonthPatternData(
                sequence,
                missingIndex);
    }
}