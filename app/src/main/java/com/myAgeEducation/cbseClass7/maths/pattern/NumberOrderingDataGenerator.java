package com.myAgeEducation.cbseClass7.maths.pattern;

import java.util.Arrays;
import java.util.Random;

public class NumberOrderingDataGenerator
{
    private static final Random RANDOM =
            new Random();

    private static final int NUMBER_COUNT = 4;

    private static final int MIN_VALUE = 10;
    private static final int MAX_VALUE = 999;


    public static NumberOrderingQuestionData generate()
    {
        int[] numbers =
                generateUniqueNumbers();

        NumberOrderingQuestionType type =
                RANDOM.nextBoolean()
                        ? NumberOrderingQuestionType.LARGEST_TO_SMALLEST
                        : NumberOrderingQuestionType.SMALLEST_TO_LARGEST;

        // Generates 2, 3 or 4
        int position =
                RANDOM.nextInt(3) + 2;

        return new NumberOrderingQuestionData(
                numbers,
                type,
                position);
    }


    private static int[] generateUniqueNumbers()
    {
        int[] numbers =
                new int[NUMBER_COUNT];

        for (int i = 0;
             i < NUMBER_COUNT;
             i++)
        {
            int number;

            do
            {
                number =
                        MIN_VALUE
                                + RANDOM.nextInt(
                                MAX_VALUE
                                        - MIN_VALUE
                                        + 1);
            }
            while (contains(
                    numbers,
                    i,
                    number));

            numbers[i] =
                    number;
        }

        return numbers;
    }


    private static boolean contains(
            int[] numbers,
            int count,
            int value)
    {
        for (int i = 0;
             i < count;
             i++)
        {
            if (numbers[i] == value)
            {
                return true;
            }
        }

        return false;
    }

    private static int getCorrectAnswer(
            NumberOrderingQuestionData data)
    {
        int[] sortedNumbers =
                data.numbers.clone();

        Arrays.sort(sortedNumbers);

        int index;

        if (data.type ==
                NumberOrderingQuestionType.SMALLEST_TO_LARGEST)
        {
            index =
                    data.position - 1;
        }
        else
        {
            index =
                    sortedNumbers.length
                            - data.position;
        }

        return sortedNumbers[index];
    }
}
