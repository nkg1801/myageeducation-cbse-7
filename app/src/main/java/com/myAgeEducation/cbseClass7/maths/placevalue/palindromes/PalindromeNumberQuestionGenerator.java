package com.myAgeEducation.cbseClass7.maths.placevalue.palindromes;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PalindromeNumberQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(4);
        switch (type) {
            case 0:
                return generateIdentificationQuestion();
            case 1:
                return generateDefinitionQuestion();
            case 2:
                return generateTrueFalseQuestion();
            default:
                return generateNextPalindromeQuestion();
        }
    }

    private static Question generateIdentificationQuestion() {
        int palindrome = generateRandomPalindrome();
        String correctAnswer = NumberFormatUtil.formatIndianNumber(palindrome);

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        while (options.size() < 4) {
            int wrong = 100 + RANDOM.nextInt(9000);
            if (!isPalindrome(wrong)) {
                String opt = NumberFormatUtil.formatIndianNumber(wrong);
                if (!options.contains(opt)) {
                    options.add(opt);
                }
            }
        }

        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion("Which of the following is a palindrome number?");
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateDefinitionQuestion() {
        Question question = new Question();
        question.setQuestion("Numbers that read the same when read from left to right or from right to left are called ______ numbers.");
        question.setAnswer("Palindrome");
        OptionUtils.setQuestionOptions(question, new String[]{"Palindrome", "Even", "Odd", "Prime"});
        return question;
    }

    private static Question generateTrueFalseQuestion() {
        boolean isCorrect = RANDOM.nextBoolean();
        int num;
        if (isCorrect) {
            num = generateRandomPalindrome();
        } else {
            do {
                num = 100 + RANDOM.nextInt(9000);
            } while (isPalindrome(num));
        }

        Question question = new Question();
        question.setQuestion("The number " + NumberFormatUtil.formatIndianNumber(num) + " is a palindrome number. TRUE or FALSE?");
        question.setAnswer(isCorrect ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static Question generateNextPalindromeQuestion() {
        // e.g. What is the next palindrome after 99? 101. 
        // Or after 121? 131.
        int[] bases = {9, 11, 22, 99, 121, 232, 444, 999, 1001, 1221};
        int start = bases[RANDOM.nextInt(bases.length)];
        
        int next = start + 1;
        while (!isPalindrome(next)) {
            next++;
        }

        String correctAnswer = NumberFormatUtil.formatIndianNumber(next);
        
        Question question = new Question();
        question.setQuestion("What is the next palindrome number after " + NumberFormatUtil.formatIndianNumber(start) + "?");
        question.setAnswer(correctAnswer);
        
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.add(NumberFormatUtil.formatIndianNumber(next + 10));
        options.add(NumberFormatUtil.formatIndianNumber(next + 1));
        options.add(NumberFormatUtil.formatIndianNumber(start + 2));
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static int generateRandomPalindrome() {
        int type = RANDOM.nextInt(3);
        if (type == 0) { // 2 digits: 11, 22, ... 99
            int d = 1 + RANDOM.nextInt(9);
            return d * 10 + d;
        } else if (type == 1) { // 3 digits: 101, 111, ... 999
            int d1 = 1 + RANDOM.nextInt(9);
            int d2 = RANDOM.nextInt(10);
            return d1 * 100 + d2 * 10 + d1;
        } else { // 4 digits: 1001, 1111, ... 9999
            int d1 = 1 + RANDOM.nextInt(9);
            int d2 = RANDOM.nextInt(10);
            return d1 * 1000 + d2 * 100 + d2 * 10 + d1;
        }
    }

    private static boolean isPalindrome(int n) {
        String s = String.valueOf(n);
        return s.equals(new StringBuilder(s).reverse().toString());
    }
}
