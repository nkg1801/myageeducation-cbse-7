package com.myAgeEducation.cbseClass7.maths.fractions;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.ImageCodeType;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;
import java.util.Random;

public class FractionComparisonQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(4);
        if (type == 0) {
            return generateLikeDenominatorComparison();
        } else if (type == 1) {
            return generateLikeNumeratorComparison();
        } else if (type == 2) {
            return generateVisualComparison();
        } else {
            return generateGeneralComparison();
        }
    }

    private static Question generateLikeDenominatorComparison() {
        int denominator = 3 + RANDOM.nextInt(17); // Ensure denominator is at least 3
        int n1 = 1 + RANDOM.nextInt(denominator - 1);
        int n2;
        int attempts = 0;
        do {
            n2 = 1 + RANDOM.nextInt(denominator - 1);
            attempts++;
        } while (n1 == n2 && attempts < 20);

        if (n1 == n2) n2 = (n1 % (denominator - 1)) + 1; // Last resort fallback

        String questionText = "Compare the fractions given below using < or > signs:\n\n"
                + "\\(\\frac{" + n1 + "}{" + denominator + "}\\) ______ \\(\\frac{" + n2 + "}{" + denominator + "}\\)";

        String correctAnswer = n1 < n2 ? "<" : ">";

        return createQuestion(questionText, correctAnswer);
    }

    private static Question generateLikeNumeratorComparison() {
        int numerator = 1 + RANDOM.nextInt(9);
        int d1 = numerator + 1 + RANDOM.nextInt(10);
        int d2;
        int attempts = 0;
        do {
            d2 = numerator + 1 + RANDOM.nextInt(10);
            attempts++;
        } while (d1 == d2 && attempts < 20);

        if (d1 == d2) d2 = d1 + 1;

        String questionText = "Compare the fractions given below using < or > signs:\n\n"
                + "\\(\\frac{" + numerator + "}{" + d1 + "}\\) ______ \\(\\frac{" + numerator + "}{" + d2 + "}\\)";

        String correctAnswer = d1 > d2 ? "<" : ">";

        return createQuestion(questionText, correctAnswer);
    }

    private static Question generateGeneralComparison() {
        int n1, d1, n2, d2;
        int attempts = 0;
        do {
            n1 = 1 + RANDOM.nextInt(6);
            d1 = 2 + RANDOM.nextInt(6);
            n2 = 1 + RANDOM.nextInt(6);
            d2 = 2 + RANDOM.nextInt(6);
            attempts++;
        } while (n1 * d2 == n2 * d1 && attempts < 10);

        String questionText = "Compare the fractions given below using < or > signs:\n\n"
                + "\\(\\frac{" + n1 + "}{" + d1 + "}\\) ______ \\(\\frac{" + n2 + "}{" + d2 + "}\\)";

        String correctAnswer = (n1 * d2 < n2 * d1) ? "<" : ">";

        return createQuestion(questionText, correctAnswer);
    }

    private static Question generateVisualComparison() {
        boolean isMixed = RANDOM.nextBoolean();
        int w1=0, n1, d1, w2=0, n2, d2;
        
        int attempts = 0;
        do {
            if (isMixed) {
                w1 = 1 + RANDOM.nextInt(9);
                n1 = 1 + RANDOM.nextInt(5);
                d1 = n1 + 1 + RANDOM.nextInt(5);
                w2 = 1 + RANDOM.nextInt(9);
                n2 = 1 + RANDOM.nextInt(5);
                d2 = n2 + 1 + RANDOM.nextInt(5);
            } else {
                boolean isImproper = RANDOM.nextBoolean();
                if (isImproper) {
                    n1 = 5 + RANDOM.nextInt(10);
                    d1 = 2 + RANDOM.nextInt(4);
                    n2 = 5 + RANDOM.nextInt(10);
                    d2 = 2 + RANDOM.nextInt(4);
                } else {
                    n1 = 1 + RANDOM.nextInt(9);
                    d1 = n1 + 1 + RANDOM.nextInt(5);
                    n2 = 1 + RANDOM.nextInt(9);
                    d2 = n2 + 1 + RANDOM.nextInt(5);
                }
            }
            attempts++;
        } while (Math.abs((w1 + (double)n1/d1) - (w2 + (double)n2/d2)) < 0.001 && attempts < 10);

        double val1 = w1 + (double)n1/d1;
        double val2 = w2 + (double)n2/d2;
        
        boolean isTrueFalse = RANDOM.nextBoolean();
        String imgCode;
        String questionText;
        String answer;
        String[] options;

        if (isTrueFalse) {
            int opType = 1 + RANDOM.nextInt(2); // 1: <, 2: >
            imgCode = ImageCodeType.FRACTION_COMPARISON + "_" + w1 + "_" + n1 + "_" + d1 + "_" + opType + "_" + w2 + "_" + n2 + "_" + d2;
            questionText = "Whether the statement below is correct?";
            
            boolean actualCorrect = (opType == 1 && val1 < val2) || (opType == 2 && val1 > val2);
            answer = actualCorrect ? "TRUE" : "FALSE";
            options = new String[]{"TRUE", "FALSE"};
        } else {
            imgCode = ImageCodeType.FRACTION_COMPARISON + "_" + w1 + "_" + n1 + "_" + d1 + "_0_" + w2 + "_" + n2 + "_" + d2;
            questionText = "Fill in the blanks with > < or =";
            answer = val1 < val2 ? "<" : (val1 > val2 ? ">" : "=");
            options = new String[]{"<", ">", "=", "None of these"};
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setImage(imgCode);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static Question createQuestion(String questionText, String correctAnswer) {
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        
        String[] options = {"<", ">", "=", "None of these"};
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }
}
