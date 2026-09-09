package com.myAgeEducation.cbseClass7.maths.placevalue.rounding;

import java.util.Random;

public class RoundingUtils
{
    private RoundingUtils()
    {
        // Prevent object creation
    }

    public static boolean hasSameRoundingResult(int number, int firstPlace, int secondPlace)
    {
        int firstResult =
                roundToPlace(
                        number,
                        firstPlace);

        int secondResult =
                roundToPlace(
                        number,
                        secondPlace);

        return firstResult == secondResult;
    }

    private static int roundToPlace(int number, int place)
    {
        return Math.round(number / (float) place) * place;
    }

    public static int generateValidNumber(Random random)
    {
        /*
         * A number is valid for this question if it rounds to the same value
         * when rounded to the nearest hundred and to the nearest thousand.
         *
         * This happens when the number is within [Thousand - 50, Thousand + 49].
         */
        int thousand = (2 + random.nextInt(8)) * 1000;
        return thousand - 50 + random.nextInt(100);
    }

    public static int generateInvalidNumber(Random random)
    {
        while (true)
        {
            int number = 1000 + random.nextInt(9000);
            if (!hasSameRoundingResult(number, 100, 1000))
            {
                return number;
            }
        }
    }

}
