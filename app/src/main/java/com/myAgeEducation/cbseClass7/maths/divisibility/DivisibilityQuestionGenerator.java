package com.myAgeEducation.cbseClass7.maths.divisibility;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DivisibilityQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(3);
        switch (type) {
            case 0:
                return generateRuleFillBlank();
            case 1:
                return generateTrueFalseRule();
            case 2:
                return generateApplyRule();
            default:
                return generateRuleFillBlank();
        }
    }

    private static Question generateRuleFillBlank() {
        String[][] bank = {
                {"A number is divisible by _____ if the last digit is 0, 2, 4, 6, 8.", "2", "3", "5", "10"},
                {"A number is divisible by _____ if the last digit is 0 or 5.", "5", "2", "10", "4"},
                {"A number is divisible by _____ if the last digit is 0.", "10", "5", "2", "3"},
                {"A number is divisible by _____ if the sum of its digits is divisible by 3.", "3", "6", "9", "2"},
                {"A number is divisible by _____ if the sum of its digits is divisible by 9.", "9", "3", "6", "4"},
                {"A number is divisible by _____ if it is divisible by both 2 and 3.", "6", "4", "9", "12"},
                {"A number is divisible by _____ if the last two digits are divisible by 4.", "4", "2", "8", "6"}
        };
        int idx = RANDOM.nextInt(bank.length);
        String[] item = bank[idx];

        Question question = new Question();
        question.setQuestion(item[0]);
        question.setAnswer(item[1]);

        List<String> options = new ArrayList<>(Arrays.asList(item).subList(1, item.length));
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateTrueFalseRule() {
        String[][] bank = {
                {"TRUE or FALSE. All numbers that are divisible by 10 are also divisible by 5.", "TRUE"},
                {"TRUE or FALSE. All numbers that are divisible by 5 are also divisible by 10.", "FALSE"},
                {"TRUE or FALSE. A number is divisible by 3 if the last digit is 3.", "FALSE"},
                {"TRUE or FALSE. A number is divisible by 3 if the sum of the digits is divisible by 3.", "TRUE"},
                {"TRUE or FALSE. A number is divisible by 6 if the last digit is 6.", "FALSE"},
                {"TRUE or FALSE. A number is divisible by 6 if the number is both divisible by 2 and 3.", "TRUE"},
                {"TRUE or FALSE. A number is divisible by 9 if the sum of the digits is divisible by 9.", "TRUE"},
                {"TRUE or FALSE. A number is divisible by 4 if the sum of the digits is divisible by 4.", "FALSE"},
                {"TRUE or FALSE. A number is divisible by 4 if the number formed by the last 2 digits is divisible by 4 or ends with '00'.", "TRUE"},
                {"TRUE or FALSE. If a number is divisible by 9, it must be divisible by 3.", "TRUE"},
                {"TRUE or FALSE. If a number is divisible by 3, it must be divisible by 9.", "FALSE"}
        };
        int idx = RANDOM.nextInt(bank.length);
        String[] item = bank[idx];

        Question question = new Question();
        question.setQuestion(item[0]);
        question.setAnswer(item[1]);
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static Question generateApplyRule() {
        int num = 100 + RANDOM.nextInt(900);
        int[] divisors = {2, 3, 4, 5, 6, 9, 10};
        int divisor = divisors[RANDOM.nextInt(divisors.length)];

        boolean isDiv = (num % divisor == 0);
        
        Question question = new Question();
        question.setQuestion("Is the number " + num + " divisible by " + divisor + "?");
        question.setAnswer(isDiv ? "YES" : "NO");
        OptionUtils.setQuestionOptions(question, new String[]{"YES", "NO"});
        return question;
    }
}
