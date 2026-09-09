package com.myAgeEducation.cbseClass7.maths.wholenumbers;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WholeNumbersQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        WholeNumbersQuestionType type = WholeNumbersQuestionType.values()[RANDOM.nextInt(WholeNumbersQuestionType.values().length)];
        switch (type) {
            case PREDECESSOR_SUCCESSOR_CONCEPT:
                return generatePredecessorSuccessorConcept();
            case NUMERICAL_PREDECESSOR_SUCCESSOR:
                return generateNumericalPredecessorSuccessor();
            case NUMBER_LINE_POSITION:
                return generateNumberLinePosition();
            case WHOLE_NATURAL_CONCEPT:
                return generateWholeNaturalConcept();
            case PROPERTIES_CLOSURE:
                return generatePropertiesClosure();
            default:
                return generateWholeNaturalConcept();
        }
    }

    private static Question generatePredecessorSuccessorConcept() {
        String[][] bank = {
                {"Is there any natural number that has no predecessor?", "Yes, its the number 1", "No, all numbers have predecessor", "Yes, its the number 0", "Yes, its the number 999"},
                {"Does the natural number 1 have both a successor and a predecessor?", "No, 1 has only successor", "Yes", "No, 1 has only predecessor", "None of these"},
                {"Is there any natural number which has no successor?", "No, all natural numbers have successor", "Yes, its 99999999", "Yes, its 10000000000", "Yes, its 0"},
                {"TRUE or FALSE. The natural number 1 has no predecessor.", "TRUE", "FALSE"},
                {"TRUE or FALSE. The whole number 1 has no predecessor", "FALSE", "TRUE"},
                {"TRUE or FALSE. The whole number 0 has no predecessor", "TRUE", "FALSE"},
                {"TRUE or FALSE. The predecessor of a two digit number is never a single digit number", "FALSE", "TRUE"},
                {"TRUE or FALSE. The successor of a two digit number is always a two digit number.", "FALSE", "TRUE"}
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

    private static Question generateNumericalPredecessorSuccessor() {
        int number = 100 + RANDOM.nextInt(9000);
        boolean isSuccessor = RANDOM.nextBoolean();
        
        Question question = new Question();
        if (isSuccessor) {
            question.setQuestion("What is the successor of the number " + number + "?");
            question.setAnswer(String.valueOf(number + 1));
        } else {
            question.setQuestion("What is the predecessor of the number " + number + "?");
            question.setAnswer(String.valueOf(number - 1));
        }

        List<String> options = new ArrayList<>();
        int ans = Integer.parseInt(question.getAnswer());
        options.add(String.valueOf(ans));
        options.add(String.valueOf(number));
        options.add(String.valueOf(ans + 2));
        options.add(String.valueOf(ans - 2));
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateNumberLinePosition() {
        int a = 10000 + RANDOM.nextInt(90000);
        int b = 10000 + RANDOM.nextInt(90000);
        while (Math.abs(a - b) < 1000) b = 10000 + RANDOM.nextInt(90000);

        int leftNumber = Math.min(a, b);
        int rightNumber = Math.max(a, b);

        Question question = new Question();
        question.setQuestion("In the following pair of numbers: " + rightNumber + ", " + leftNumber + ", select which whole number is on the left of the other number on the number line");
        question.setAnswer(String.valueOf(leftNumber));

        List<String> options = new ArrayList<>();
        options.add(String.valueOf(leftNumber));
        options.add(String.valueOf(rightNumber));
        options.add(String.valueOf(leftNumber + 100));
        options.add(String.valueOf(rightNumber - 100));
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateWholeNaturalConcept() {
        String[][] bank = {
                {"TRUE or FALSE. Zero is the smallest natural number.", "FALSE", "TRUE"},
                {"TRUE or FALSE. Zero is the smallest whole number.", "TRUE", "FALSE"},
                {"TRUE or FALSE. 1 is the smallest whole number", "FALSE", "TRUE"},
                {"TRUE or FALSE. All natural numbers are whole numbers", "TRUE", "FALSE"},
                {"TRUE or FALSE. All whole numbers are natural numbers", "FALSE", "TRUE"},
                {"TRUE or FALSE. The whole number 13 lies between 11 and 12", "FALSE", "TRUE"},
                {"TRUE or FALSE. 400 is the predecessor of 399", "FALSE", "TRUE"},
                {"TRUE or FALSE. 600 is the successor of 599", "TRUE", "FALSE"}
        };
        int idx = RANDOM.nextInt(bank.length);
        String[] item = bank[idx];

        Question question = new Question();
        question.setQuestion(item[0]);
        question.setAnswer(item[1]);
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static Question generatePropertiesClosure() {
        String[][] bank = {
                {"TRUE or FALSE. Sum of any two whole numbers is always a whole number.", "TRUE", "FALSE"},
                {"TRUE or FALSE. Sum of any three whole numbers is not a whole number.", "FALSE", "TRUE"},
                {"TRUE or FALSE. The multiplication of two whole numbers is always a whole number.", "TRUE", "FALSE"},
                {"TRUE or FALSE. Subtraction of any two whole numbers is always a whole number.", "FALSE", "TRUE"},
                {"TRUE or FALSE. Division of any two whole numbers is always a whole number.", "FALSE", "TRUE"}
        };
        int idx = RANDOM.nextInt(bank.length);
        String[] item = bank[idx];

        Question question = new Question();
        question.setQuestion(item[0]);
        question.setAnswer(item[1]);
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }
}
