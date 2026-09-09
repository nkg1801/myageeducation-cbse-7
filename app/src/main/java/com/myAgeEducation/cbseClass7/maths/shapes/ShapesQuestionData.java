package com.myAgeEducation.cbseClass7.maths.shapes;

public class ShapesQuestionData {
    public String question;
    public String answer;
    public String[] options;
    public ShapesQuestionType type;

    public ShapesQuestionData(String question, String answer, String[] options, ShapesQuestionType type) {
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.type = type;
    }
}
