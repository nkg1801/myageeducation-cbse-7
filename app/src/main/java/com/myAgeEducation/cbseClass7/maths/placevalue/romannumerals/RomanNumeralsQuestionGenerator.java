package com.myAgeEducation.cbseClass7.maths.placevalue.romannumerals;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RomanNumeralsQuestionGenerator {
    private static final Random RANDOM = new Random();

    private static final String[] ROMAN = {"I", "V", "X", "L", "C", "D", "M"};
    private static final int[] VALUES = {1, 5, 10, 50, 100, 500, 1000};

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(2);
        if (type == 0) {
            return generateValueQuestion();
        } else {
            return generateSeriesQuestion();
        }
    }

    private static Question generateValueQuestion() {
        int index = RANDOM.nextInt(ROMAN.length);
        String symbol = ROMAN[index];
        int value = VALUES[index];

        Question question = new Question();
        question.setQuestion("What does the Roman number '" + symbol + "' represent?");
        question.setAnswer(String.valueOf(value));

        Set<String> options = new LinkedHashSet<>();
        options.add(String.valueOf(value));
        while (options.size() < 4) {
            options.add(String.valueOf(VALUES[RANDOM.nextInt(VALUES.length)]));
        }

        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);
        OptionUtils.setQuestionOptions(question, optionList.toArray(new String[0]));

        return question;
    }

    private static Question generateSeriesQuestion() {
        int start = 1 + RANDOM.nextInt(5);
        int initialMultiplier = RANDOM.nextBoolean() ? 1 : 10;
        if (RANDOM.nextBoolean() && initialMultiplier == 10) {
            initialMultiplier = 100;
        }
        int multiplier = initialMultiplier;

        StringBuilder sb = new StringBuilder("What is the next number in the roman number series: ");
        for (int i = 0; i < 5; i++) {
            sb.append(toRoman((start + i) * multiplier)).append(", ");
        }
        sb.append("____");

        int nextValue = (start + 5) * multiplier;
        String correctAnswer = toRoman(nextValue);

        Question question = new Question();
        question.setQuestion(sb.toString());
        question.setAnswer(correctAnswer);

        Set<String> options = new LinkedHashSet<>();
        options.add(correctAnswer);
        while (options.size() < 4) {
            int wrongValue = (start + 5 + RANDOM.nextInt(5) - 2) * multiplier;
            if (wrongValue > 0) {
                options.add(toRoman(wrongValue));
            }
        }

        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);
        OptionUtils.setQuestionOptions(question, optionList.toArray(new String[0]));

        return question;
    }

    private static String toRoman(int val) {
        if (val <= 0) return "";
        int number = val;
        StringBuilder sb = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                sb.append(roman[i]);
            }
        }
        return sb.toString();
    }
}
