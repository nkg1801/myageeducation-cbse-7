package com.myAgeEducation.cbseClass7.maths.mappingskills;


import java.util.List;

public class DirectionDistanceQuestionData
{
    private final String question;
    private final String[] options;
    private final String answer;
    private final List<DirectionPoint> points;
    private final String startImage;
    private final String scaleLabel;

    public DirectionDistanceQuestionData(
            String question,
            String[] options,
            String answer,
            List<DirectionPoint> points,
            String startImage,
            String scaleLabel)
    {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.points = points;
        this.startImage = startImage;
        this.scaleLabel = scaleLabel;
    }

    public String getQuestion()
    {
        return question;
    }

    public String[] getOptions()
    {
        return options;
    }

    public String getAnswer()
    {
        return answer;
    }

    public List<DirectionPoint> getPoints()
    {
        return points;
    }

    public String getStartImage()
    {
        return startImage;
    }

    public String getScaleLabel()
    {
        return scaleLabel;
    }
}
