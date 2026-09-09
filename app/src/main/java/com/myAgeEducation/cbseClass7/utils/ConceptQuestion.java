package com.myAgeEducation.cbseClass7.utils;

public class ConceptQuestion
{
    public String question;
    public String correctAnswer;
    public String[] wrongAnswers;

    public ConceptQuestion(String question, String correctAnswer, String... wrongAnswers)
    {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
    }
}
