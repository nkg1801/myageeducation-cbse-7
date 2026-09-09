package com.myAgeEducation.cbseClass7.maths.pattern;

public class WeekdayPatternData
{
    public String[] sequence;
    public int missingIndex;

    public WeekdayPatternData(
            String[] sequence,
            int missingIndex)
    {
        this.sequence = sequence;
        this.missingIndex = missingIndex;
    }

    public String getMissingValue()
    {
        return sequence[missingIndex];
    }

    public String getSequenceText()
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < sequence.length; i++)
        {
            if (i > 0)
            {
                builder.append(", ");
            }

            if (i == missingIndex)
            {
                builder.append("_____");
            }
            else
            {
                builder.append(sequence[i]);
            }
        }

        return builder.toString();
    }
}