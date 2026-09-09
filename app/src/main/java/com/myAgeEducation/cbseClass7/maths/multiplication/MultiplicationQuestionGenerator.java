package com.myAgeEducation.cbseClass7.maths.multiplication;

import com.myAgeEducation.cbseClass7.OptionUtil;
import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class MultiplicationQuestionGenerator {
    private static final Random RANDOM = new Random();

    private MultiplicationQuestionGenerator() {
    }

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(2);
        if (type == 0) {
            return generateMultiplyByHundredMultiple();
        } else {
            return generateThreeByThreeMultiplication();
        }
    }

    private static Question generateMultiplyByHundredMultiple() {
        int num1 = 100 + RANDOM.nextInt(900); // 3-digit number
        int num2 = (1 + RANDOM.nextInt(9)) * 100; // multiple of 100
        int answer = num1 * num2;

        String questionText = String.format("%s × %s = ______",
                NumberFormatUtil.formatIndianNumber(num1),
                NumberFormatUtil.formatIndianNumber(num2));

        return createQuestion(questionText, answer);
    }

    private static Question generateThreeByThreeMultiplication() {
        int num1 = 100 + RANDOM.nextInt(900);
        int num2 = 100 + RANDOM.nextInt(900);
        int answer = num1 * num2;

        String questionText = String.format("%s × %s = ______",
                NumberFormatUtil.formatIndianNumber(num1),
                NumberFormatUtil.formatIndianNumber(num2));

        return createQuestion(questionText, answer);
    }

    private static Question createQuestion(String questionText, int answer) {
        String correctAnswer = NumberFormatUtil.formatIndianNumber(answer);
        String[] options = generateOptions(answer);

        Question question = new Question();
        question.setQuestion(questionText);
        OptionUtils.setQuestionOptions(question, options);
        question.setAnswer(correctAnswer);
        return question;
    }

    private static String[] generateOptions(int correctAnswer) {
        Set<String> distractors = new LinkedHashSet<>();

        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer + 100));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer - 100));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer + 1000));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer - 1000));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer + 10));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer + RANDOM.nextInt(500) + 1));

        return OptionUtil.createOptions(NumberFormatUtil.formatIndianNumber(correctAnswer), distractors, 4);
    }
}
