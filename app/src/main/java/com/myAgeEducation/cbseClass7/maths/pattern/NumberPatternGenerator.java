package com.myAgeEducation.cbseClass7.maths.pattern;

import java.util.Random;

public class NumberPatternGenerator
{
    private static final Random RANDOM =
            new Random();

    // Suitable step values for Class 5
    private static final int[] STEP_VALUES =
            {
                    2, 5, 10, 20, 25, 50, 100, 111, 200, 250, 500, 900, 1000, 1050
            };


    public static NumberPatternData generate()
    {
        // Random ADD or SUBTRACT
        NumberPatternType patternType =
                RANDOM.nextBoolean()
                        ? NumberPatternType.ADD
                        : NumberPatternType.SUBTRACT;


        // Random step
        int step =
                STEP_VALUES[
                        RANDOM.nextInt(
                                STEP_VALUES.length)];


        // Sequence length: 4 to 7 numbers
        int length =
                4 + RANDOM.nextInt(4);


        int startNumber;

        if (patternType == NumberPatternType.ADD)
        {
            startNumber =
                    generateStartForAddition(
                            step,
                            length);
        }
        else
        {
            startNumber =
                    generateStartForSubtraction(
                            step,
                            length);
        }


        // Generate complete sequence
        int[] numbers =
                new int[length];

        numbers[0] =
                startNumber;


        for (int i = 1;
             i < length;
             i++)
        {
            if (patternType == NumberPatternType.ADD)
            {
                numbers[i] =
                        numbers[i - 1]
                                + step;
            }
            else
            {
                numbers[i] =
                        numbers[i - 1]
                                - step;
            }
        }


        // Select a random missing index
        int missingIndex =
                RANDOM.nextInt(length);


        return new NumberPatternData(
                patternType,
                startNumber,
                step,
                numbers,
                missingIndex);
    }


    private static int generateStartForAddition(int step, int length)
    {
        /*
         * Keep the final number <= 100000.
         */

        int maximumStart = 100000 - ((length - 1) * step);

        if (maximumStart <= 1) return 1;

        // Start from at least 1
        return 1 + RANDOM.nextInt(maximumStart);
    }


    private static int generateStartForSubtraction(
            int step,
            int length)
    {
        /*
         * The starting number must be large enough
         * so that the final number remains positive.
         */

        int minimumStart =
                ((length - 1) * step)
                        + 1;

        int maximumStart = Math.max(minimumStart + 1000, 100000);

        // Maximum starting value = 100000
        return minimumStart
                + RANDOM.nextInt(
                maximumStart - minimumStart + 1);
    }
}
