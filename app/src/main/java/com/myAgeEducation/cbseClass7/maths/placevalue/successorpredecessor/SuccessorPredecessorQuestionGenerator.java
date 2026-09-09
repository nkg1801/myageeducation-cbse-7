package com.myAgeEducation.cbseClass7.maths.placevalue.successorpredecessor;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SuccessorPredecessorQuestionGenerator {
    private static final Random RANDOM = new Random();
    private static final int MIN_NUMBER = 10000;
    private static final int MAX_NUMBER = 99999999;

    public static Question generateQuestion() {
        boolean isSuccessor = RANDOM.nextBoolean();
        int number = MIN_NUMBER + RANDOM.nextInt(MAX_NUMBER - MIN_NUMBER + 1);

        String questionText;
        int answer;

        if (isSuccessor) {
            questionText = "The number after " + NumberFormatUtil.formatIndianNumber(number) + " is ________";
            answer = number + 1;
        } else {
            questionText = "The number before " + NumberFormatUtil.formatIndianNumber(number) + " is ________";
            answer = number - 1;
        }

        String correctAnswer = NumberFormatUtil.formatIndianNumber(answer);
        String[] options = generateOptions(answer);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options);

        return question;
    }

    private static String[] generateOptions(int answer) {
        Set<Integer> options = new LinkedHashSet<>();
        options.add(answer);

        while (options.size() < 4) {
            int offset = RANDOM.nextInt(20) - 10;
            if (offset == 0) {
                offset = 1;
            }
            int wrong = answer + offset;
            if (wrong > 0) {
                options.add(wrong);
            }
        }

        List<String> optionList = new ArrayList<>();
        for (int opt : options) {
            optionList.add(NumberFormatUtil.formatIndianNumber(opt));
        }
        Collections.shuffle(optionList);
        return optionList.toArray(new String[0]);
    }
}
