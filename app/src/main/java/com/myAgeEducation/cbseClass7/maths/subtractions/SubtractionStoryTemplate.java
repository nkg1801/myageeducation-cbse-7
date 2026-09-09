package com.myAgeEducation.cbseClass7.maths.subtractions;

public class SubtractionStoryTemplate
{
    public final SubtractionStoryType type;

    public final String questionTemplate;

    public SubtractionStoryTemplate(
            SubtractionStoryType type,
            String questionTemplate)
    {
        this.type = type;
        this.questionTemplate = questionTemplate;
    }
}