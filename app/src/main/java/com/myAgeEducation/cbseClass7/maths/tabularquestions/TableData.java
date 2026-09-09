package com.myAgeEducation.cbseClass7.maths.tabularquestions;

public class TableData
{
    public final TableScenario scenario;
    public final String[] labels;
    public final int[] values;

    public TableData(
            TableScenario scenario,
            String[] labels,
            int[] values)
    {
        this.scenario = scenario;
        this.labels = labels;
        this.values = values;
    }

    public String getLabel(int index)
    {
        return labels[index];
    }

    public int getValue(int index)
    {
        return values[index];
    }
}