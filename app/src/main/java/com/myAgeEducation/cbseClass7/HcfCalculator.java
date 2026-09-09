package com.myAgeEducation.cbseClass7;

import java.util.ArrayList;

public class HcfCalculator {
    public static int calculateHcf(int num1, int num2, int num3, int num4, int num5)
    {
        int hcf = 1;

        ArrayList<Integer> numberOneFactors = getFactors(num1);
        ArrayList<Integer> numberTwoFactors = getFactors(num2);
        ArrayList<Integer> numberThreeFactors = getFactors(num3);
        ArrayList<Integer> numberFourFactors = getFactors(num4);
        ArrayList<Integer> numberFiveFactors = getFactors(num5);

        for (int index = numberOneFactors.size() - 1; index >= 0; index--)
        {
            int n1 = numberOneFactors.get(index);
            if (numberTwoFactors.contains(n1) && numberThreeFactors.contains(n1) && numberFourFactors.contains(n1) && numberFiveFactors.contains(n1))
            {
                return n1;
            }
        }

        return hcf;
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
