package com.myAgeEducation.cbseClass7.maths.pattern;

import java.util.Random;

public class WeekdayPatternGenerator
{
    private static final Random RANDOM =
            new Random();

    private static final String[] WEEKDAYS =
            {
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday",
                    "Sunday"
            };

    public static WeekdayPatternData generate()
    {
        // Step 1 = consecutive days
        // Step 2 = alternate days
        int step =
                RANDOM.nextBoolean()
                        ? 1
                        : 2;

        int length = 4;

        // Any starting day is valid because
        // weekdays repeat after Sunday.
        int startIndex =
                RANDOM.nextInt(WEEKDAYS.length);

        String[] sequence =
                new String[length];

        for (int i = 0; i < length; i++)
        {
            int index =
                    (startIndex + (i * step))
                            % WEEKDAYS.length;

            sequence[i] =
                    WEEKDAYS[index];
        }

        // Do not hide the first item
        int missingIndex =
                1 + RANDOM.nextInt(length - 1);

        return new WeekdayPatternData(
                sequence,
                missingIndex);
    }
}