package com.myAgeEducation.cbseClass7.maths.lcm;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LcmQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int a, b;
        // Logic to pick a and b
        int type = RANDOM.nextInt(3);
        if (type == 0) {
            // small numbers
            a = 2 + RANDOM.nextInt(10);
            b = 2 + RANDOM.nextInt(10);
        } else if (type == 1) {
            // one small, one medium
            a = 3 + RANDOM.nextInt(10);
            b = 10 + RANDOM.nextInt(20);
        } else {
            // two medium numbers (like 16 and 20)
            a = 12 + RANDOM.nextInt(15);
            b = 12 + RANDOM.nextInt(15);
        }
        
        while (a == b) b = 2 + RANDOM.nextInt(30);

        int lcmValue = lcm(a, b);
        String questionText = "What is the lowest common multiple (LCM) of " + a + " and " + b + "?";
        String answer = String.valueOf(lcmValue);

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(String.valueOf(a * b)); // common mistake if they are not co-prime
        options.add(String.valueOf(Math.max(a, b))); // common mistake (picking the largest number)
        options.add(String.valueOf(gcd(a, b))); // common mistake (picking GCD)
        
        // Ensure options are unique and plausible
        List<String> finalOptions = new ArrayList<>();
        for (String opt : options) {
            if (!finalOptions.contains(opt)) finalOptions.add(opt);
        }
        
        while (finalOptions.size() < 4) {
            int distractor = lcmValue + (RANDOM.nextBoolean() ? 1 : -1) * (1 + RANDOM.nextInt(10));
            if (distractor > 0 && !finalOptions.contains(String.valueOf(distractor))) {
                finalOptions.add(String.valueOf(distractor));
            }
        }

        Collections.shuffle(finalOptions);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, finalOptions.toArray(new String[0]));
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

    private static int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs(a * b) / gcd(a, b);
    }
}
