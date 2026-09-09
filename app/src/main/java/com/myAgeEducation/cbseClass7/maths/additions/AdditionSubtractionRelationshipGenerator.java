package com.myAgeEducation.cbseClass7.maths.additions;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AdditionSubtractionRelationshipGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        if (RANDOM.nextBoolean()) {
            return generateFromAddition();
        } else {
            return generateFromSubtraction();
        }
    }

    private static Question generateFromAddition() {
        // Generate two numbers A and B
        int a = 10 + RANDOM.nextInt(900);
        int b = 10 + RANDOM.nextInt(900);
        int c = a + b;

        boolean askFirst = RANDOM.nextBoolean();
        
        String questionText = "Find the relationship between the numbers in the given statement and fill in the blank appropriately:\n\n"
                + "If " + a + " + " + b + " = " + c + ", then\n"
                + (askFirst ? (c + " - " + b + " = _______") : (c + " - " + a + " = _______"));

        int answerVal = askFirst ? a : b;
        return finalizeQuestion(questionText, answerVal, askFirst ? b : a, c);
    }

    private static Question generateFromSubtraction() {
        // Generate C - B = A
        int c = 100 + RANDOM.nextInt(900);
        int b = 10 + RANDOM.nextInt(c - 20);
        int a = c - b;

        boolean askAddition = RANDOM.nextBoolean();
        
        String questionText = "Find the relationship between the numbers in the given statement and fill in the blank appropriately:\n\n"
                + "If " + c + " - " + b + " = " + a + ", then\n"
                + (askAddition ? (a + " + _______ = " + c) : (c + " - _______ = " + b));

        int answerVal = askAddition ? b : a;
        return finalizeQuestion(questionText, answerVal, askAddition ? a : b, c);
    }

    private static Question finalizeQuestion(String questionText, int answerVal, int otherVal, int sumVal) {
        String correctAnswer = String.valueOf(answerVal);

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        
        // Logical distractors
        options.add(String.valueOf(otherVal)); 
        options.add(String.valueOf(sumVal)); 
        
        while (options.size() < 4) {
            int wrong = answerVal + (RANDOM.nextBoolean() ? 10 : -10);
            if (wrong > 0 && !options.contains(String.valueOf(wrong))) {
                options.add(String.valueOf(wrong));
            } else {
                String randomVal = String.valueOf(RANDOM.nextInt(1000));
                if (!options.contains(randomVal)) {
                    options.add(randomVal);
                }
            }
        }

        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }
}
