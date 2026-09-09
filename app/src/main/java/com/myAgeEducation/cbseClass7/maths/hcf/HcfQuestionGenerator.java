package com.myAgeEducation.cbseClass7.maths.hcf;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HcfQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(3);
        switch (type) {
            case 0:
                return generateHcfTwoNumbers();
            case 1:
                return generateHcfThreeNumbers();
            case 2:
                return generateHcfTrueFalse();
            default:
                return generateHcfTwoNumbers();
        }
    }

    private static Question generateHcfTwoNumbers() {
        int commonFactor = 2 + RANDOM.nextInt(9);
        int aSimplest = 1 + RANDOM.nextInt(12);
        int bSimplest = 1 + RANDOM.nextInt(12);
        while (gcd(aSimplest, bSimplest) != 1 || aSimplest == bSimplest) {
            bSimplest = 1 + RANDOM.nextInt(12);
        }

        int a = aSimplest * commonFactor;
        int b = bSimplest * commonFactor;

        String questionText = "Highest common factor (H.C.F) of " + a + " and " + b + " is ______";
        String answer = String.valueOf(commonFactor);

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(String.valueOf(a));
        options.add(String.valueOf(b));
        options.add(String.valueOf(gcd(a, b))); // This should be the same as answer

        // Refresh options to ensure uniqueness and variety
        options.clear();
        options.add(answer);
        while (options.size() < 4) {
            int range = Math.max(10, Math.min(a, b));
            int distractor = RANDOM.nextInt(range) + 1;
            if (!options.contains(String.valueOf(distractor))) {
                options.add(String.valueOf(distractor));
            }
        }

        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateHcfThreeNumbers() {
        int commonFactor = 2 + RANDOM.nextInt(6);
        int aSimplest = 1 + RANDOM.nextInt(10);
        int bSimplest = 1 + RANDOM.nextInt(10);
        int cSimplest = 1 + RANDOM.nextInt(10);
        
        while (gcd(gcd(aSimplest, bSimplest), cSimplest) != 1 || aSimplest == bSimplest || bSimplest == cSimplest) {
            bSimplest = 1 + RANDOM.nextInt(10);
            cSimplest = 1 + RANDOM.nextInt(10);
        }

        int a = aSimplest * commonFactor;
        int b = bSimplest * commonFactor;
        int c = cSimplest * commonFactor;

        String questionText = "Highest common factor (H.C.F) of " + a + ", " + b + " and " + c + " is ______";
        String answer = String.valueOf(commonFactor);

        List<String> options = new ArrayList<>();
        options.add(answer);
        while (options.size() < 4) {
            int range = Math.max(10, Math.min(Math.min(a, b), c));
            int distractor = RANDOM.nextInt(range) + 1;
            if (!options.contains(String.valueOf(distractor))) {
                options.add(String.valueOf(distractor));
            }
        }

        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateHcfTrueFalse() {
        int commonFactor = 2 + RANDOM.nextInt(9);
        int aSimplest = 1 + RANDOM.nextInt(10);
        int bSimplest = 1 + RANDOM.nextInt(10);
        while (gcd(aSimplest, bSimplest) != 1 || aSimplest == bSimplest) {
            bSimplest = 1 + RANDOM.nextInt(10);
        }

        int a = aSimplest * commonFactor;
        int b = bSimplest * commonFactor;
        
        boolean isTrue = RANDOM.nextBoolean();
        int displayFactor = isTrue ? commonFactor : commonFactor + (RANDOM.nextBoolean() ? 1 : -1) * (1 + RANDOM.nextInt(3));
        if (displayFactor <= 0) displayFactor = commonFactor + 1;
        if (!isTrue && displayFactor == commonFactor) displayFactor++;

        String questionText = "TRUE or FALSE. " + displayFactor + " is the Highest common factor (H.C.F) of " + a + " and " + b + ".";
        String answer = isTrue ? "TRUE" : "FALSE";

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static int gcd(int a, int b) {
        while (b > 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
