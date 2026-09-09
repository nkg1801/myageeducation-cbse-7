package com.myAgeEducation.cbseClass7.maths.integers;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class IntegerQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        IntegerQuestionType type = IntegerQuestionType.values()[RANDOM.nextInt(IntegerQuestionType.values().length)];
        switch (type) {
            case STEPS_ON_NUMBER_LINE:
                return generateStepsOnNumberLine();
            case CONCEPTUAL_PROPERTIES:
                return generateConceptualProperties();
            case OPPOSITE_INTEGER:
                return generateOppositeInteger();
            case COMPARE_INTEGERS:
                return generateCompareIntegers();
            case ABSOLUTE_VALUE:
                return generateAbsoluteValue();
            default:
                return generateConceptualProperties();
        }
    }

    private static Question generateStepsOnNumberLine() {
        int steps = 1 + RANDOM.nextInt(20);
        boolean left = RANDOM.nextBoolean();
        String direction = left ? "left" : "right";
        String questionText = steps + " steps to the " + direction + " of zero is ______";
        
        int val = left ? -steps : steps;
        String answer = (val > 0 ? "+" : "") + val;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((val > 0 ? "-" : "+") + steps);
        options.add("0");
        options.add(String.valueOf(RANDOM.nextInt(50) - 25));
        
        // Ensure options are unique and well-formatted
        List<String> finalOptions = new ArrayList<>();
        for(String opt : options) {
            if(!finalOptions.contains(opt)) finalOptions.add(opt);
        }
        while(finalOptions.size() < 4) finalOptions.add(String.valueOf(RANDOM.nextInt(100) - 50));
        
        Collections.shuffle(finalOptions);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, finalOptions.toArray(new String[0]));
        return question;
    }

    private static Question generateConceptualProperties() {
        String[][] bank = {
                {"Every positive integer is larger than every negative integer.", "TRUE", "FALSE"},
                {"Every positive integer is smaller than every negative integer.", "FALSE", "TRUE"},
                {"Zero is less than every positive integer.", "TRUE", "FALSE"},
                {"Zero is larger than every positive integer.", "FALSE", "TRUE"},
                {"Zero is neither a negative integer nor a positive integer.", "TRUE", "FALSE"},
                {"Zero is larger than every negative integer.", "TRUE", "FALSE"},
                {"-1 is the largest negative integer.", "TRUE", "FALSE"},
                {"1 is the smallest positive integer.", "TRUE", "FALSE"},
                {"The integer -10 is greater than -5.", "FALSE", "TRUE"},
                {"The integer 0 is greater than -100.", "TRUE", "FALSE"}
        };
        int idx = RANDOM.nextInt(bank.length);
        String[] item = bank[idx];

        Question question = new Question();
        question.setQuestion(item[0]);
        question.setAnswer(item[1]);
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static Question generateOppositeInteger() {
        int val = RANDOM.nextInt(100) + 1;
        boolean negative = RANDOM.nextBoolean();
        if (negative) val = -val;

        String questionText = "The opposite of the integer " + val + " is ______";
        String answer = String.valueOf(-val);

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(String.valueOf(val));
        options.add("0");
        options.add(String.valueOf(val + 1));
        
        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateCompareIntegers() {
        Set<Integer> uniqueNumbers = new LinkedHashSet<>();
        while (uniqueNumbers.size() < 4) {
            uniqueNumbers.add(RANDOM.nextInt(101) - 50); // -50 to 50
        }

        List<Integer> list = new ArrayList<>(uniqueNumbers);
        boolean findGreater = RANDOM.nextBoolean();
        String questionText = "Which integer is " + (findGreater ? "greater" : "smaller") + "?";
        
        int targetValue = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (findGreater) {
                if (list.get(i) > targetValue) targetValue = list.get(i);
            } else {
                if (list.get(i) < targetValue) targetValue = list.get(i);
            }
        }
        
        String answer = String.valueOf(targetValue);
        List<String> options = new ArrayList<>();
        for (int n : list) {
            options.add(String.valueOf(n));
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateAbsoluteValue() {
        int val = RANDOM.nextInt(50) + 1;
        boolean negative = RANDOM.nextBoolean();
        int signedVal = negative ? -val : val;

        String questionText = "What is the absolute value (numerical value) of " + signedVal + "?";
        String answer = String.valueOf(val);

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(String.valueOf(-val));
        options.add("0");
        options.add(String.valueOf(val + 10));

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }
}
