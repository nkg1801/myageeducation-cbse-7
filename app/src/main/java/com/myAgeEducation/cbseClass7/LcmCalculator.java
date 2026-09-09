package com.myAgeEducation.cbseClass7;

import java.util.ArrayList;

public class LcmCalculator {
    static ArrayList<Integer> numbers = new ArrayList<>();
    static int multiplyBy = 1;
    static int greatestNumber;
    static int lcm;

    public static int calculateLcm(int num1, int num2, int num3, int num4, int num5)
    {
        lcm = 1;
        numbers.clear();
        numbers.add(num1);
        numbers.add(num2);
        numbers.add(num3);
        numbers.add(num4);
        numbers.add(num5);

        multiplyBy = 1;
        findGreatestNumber();
        try {
            IsGivenNumberMultipleOfAllOtherNumbers(greatestNumber);
        }
        catch(Exception e)
        {
            return -1;
        }

        return lcm;
    }

    private static void IsGivenNumberMultipleOfAllOtherNumbers(int number)
    {
        boolean multipleOfAll = true;
        for(int i = 0; i < numbers.size(); i++)
        {
            if(number % numbers.get(i) != 0)
            {
                multipleOfAll = false;
                multiplyBy += 1;
                if(multiplyBy > 50)
                {
                    lcm = -1;
                    return;
                }
                break;
            }
        }
        if(multipleOfAll)
        {
            lcm = number;
        }
        else
        {
            IsGivenNumberMultipleOfAllOtherNumbers(greatestNumber * multiplyBy);
        }
    }

    private static int findGreatestNumber()
    {
        greatestNumber = numbers.get(0);
        for(int index = 1; index < numbers.size(); index++) {
            if(numbers.get(index) > greatestNumber)
            {
                greatestNumber = numbers.get(index);
            }
        }
        return greatestNumber;
    }

    private static ArrayList<Integer> getFactors(int number)
    {
        ArrayList<Integer> numberOneFactors = new ArrayList<>();
        for (int i = 1; i <= number; i++)
        {
            if (number % i == 0)
            {
                numberOneFactors.add(i);
            }
        }
        return numberOneFactors;
    }
}
