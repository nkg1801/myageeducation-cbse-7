package com.myAgeEducation.cbseClass7.maths.symmetry;

public class SymmetryQuestionData {
    public String question;
    public String answer;
    public String[] options;
    public String image;

    public SymmetryQuestionData(String question, String answer, String[] options) {
        this(question, answer, options, null);
    }

    public SymmetryQuestionData(String question, String answer, String[] options, String image) {
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.image = image;
    }
}
