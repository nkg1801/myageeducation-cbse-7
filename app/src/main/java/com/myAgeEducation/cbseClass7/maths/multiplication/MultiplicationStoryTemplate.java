package com.myAgeEducation.cbseClass7.maths.multiplication;

public class MultiplicationStoryTemplate
{
    public final MultiplicationStoryType type;

    public final String questionTemplate;

    public MultiplicationStoryTemplate(
            MultiplicationStoryType type,
            String questionTemplate)
    {
        this.type = type;
        this.questionTemplate = questionTemplate;
    }
}
