package com.myAgeEducation.cbseClass7.maths.pattern;

import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;

public class NumberPatternData
{
    public NumberPatternType patternType;
    public int startNumber;

    // Example: 2, 5, 10
    public int step;

    // Complete sequence, including the correct missing value
    public int[] numbers;

    // Position that should be hidden
    public int missingIndex;


    public NumberPatternData(NumberPatternType patternType, int startNumber, int step, int[] numbers, int missingIndex)
    {
        this.patternType = patternType;
        this.startNumber = startNumber;
        this.step = step;
        this.numbers = numbers;
        this.missingIndex = missingIndex;
    }

    public int getMissingNumber()
    {
        return numbers[missingIndex];
    }

    public String getSequenceText()
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < numbers.length; i++)
        {
            if (i > 0)
            {
                builder.append("; ");
            }

            if (i == missingIndex)
            {
                builder.append("_____");
            }
            else
            {
                builder.append(NumberFormatUtil.formatIndianNumber(numbers[i]));
            }
        }

        return builder.toString();
    }
}
