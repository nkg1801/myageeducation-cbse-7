package com.myAgeEducation.cbseClass7.maths.fractions;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EquivalentFractionQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(4);
        if (type == 0) {
            return generateSeriesQuestion();
        } else if (type == 1) {
            return generateSingleEquivalentQuestion();
        } else if (type == 2) {
            return generateFillInTheBoxQuestion();
        } else {
            return generateEqualToHalfQuestion();
        }
    }

    private static Question generateEqualToHalfQuestion() {
        int multiplier = 2 + RANDOM.nextInt(10);
        String correctAnswer = "\\(\\frac{" + multiplier + "}{" + (multiplier * 2) + "}\\)";

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        int attempts = 0;
        while (options.size() < 4 && attempts < 100) {
            attempts++;
            int d = 3 + RANDOM.nextInt(20);
            int n = 1 + RANDOM.nextInt(d - 1);
            
            // Ensure it's not equal to 1/2
            if (n * 2 == d) continue;
            
            String opt = "\\(\\frac{" + n + "}{" + d + "}\\)";
            if (!options.contains(opt)) {
                options.add(opt);
            }
        }

        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion("Choose the fraction that is equal to \\(\\frac{1}{2}\\):");
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateFillInTheBoxQuestion() {
        int numerator = 1 + RANDOM.nextInt(8);
        int denominator = numerator + 1 + RANDOM.nextInt(10);
        int multiplier = 2 + RANDOM.nextInt(9);

        boolean hideNumerator = RANDOM.nextBoolean();
        String correctAnswer;
        int n2 = numerator * multiplier;
        int d2 = denominator * multiplier;
        int missingIndex;

        if (hideNumerator) {
            correctAnswer = String.valueOf(n2);
            missingIndex = 0;
        } else {
            correctAnswer = String.valueOf(d2);
            missingIndex = 1;
        }

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        
        int ans = Integer.parseInt(correctAnswer);
        options.add(String.valueOf(ans + 1));
        options.add(String.valueOf(ans + multiplier));
        options.add(String.valueOf(ans - 1));

        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion("Fill in the box such that the fractions become equivalent:");
        question.setImage(EquivalentFractionImageGenerator.createImageCode(numerator, denominator, n2, d2, missingIndex));
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateSeriesQuestion() {
        int numerator = 1;
        int denominator = 2 + RANDOM.nextInt(5); // 2 to 6

        String questionText = "Complete the process of finding equivalent fractions:\n"
                + "\\(\\frac{" + numerator + "}{" + denominator + "}\\)"
                + " = "
                + "\\(\\frac{" + (numerator * 2) + "}{" + (denominator * 2) + "}\\)"
                + " = "
                + "\\(\\frac{" + (numerator * 3) + "}{" + (denominator * 3) + "}\\)"
                + " = _______";

        int targetNumerator = numerator * 4;
        int targetDenominator = denominator * 4;
        String correctAnswer = "\\(\\frac{" + targetNumerator + "}{" + targetDenominator + "}\\)";

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.add("\\(\\frac{" + (targetNumerator + 1) + "}{" + targetDenominator + "}\\)");
        options.add("\\(\\frac{" + targetNumerator + "}{" + (targetDenominator + 1) + "}\\)");
        options.add("\\(\\frac{" + (targetNumerator - 1) + "}{" + targetNumerator + "}\\)");

        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateSingleEquivalentQuestion() {
        int numerator = 1 + RANDOM.nextInt(4);
        int denominator = numerator + 1 + RANDOM.nextInt(5);

        int multiplier = 2 + RANDOM.nextInt(4);
        int eqNumerator = numerator * multiplier;
        int eqDenominator = denominator * multiplier;

        String questionText = "Which of the following is equivalent to \\(\\frac{" + numerator + "}{" + denominator + "}\\)?";
        String correctAnswer = "\\(\\frac{" + eqNumerator + "}{" + eqDenominator + "}\\)";

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.add("\\(\\frac{" + (eqNumerator + 1) + "}{" + eqDenominator + "}\\)");
        options.add("\\(\\frac{" + eqNumerator + "}{" + (eqDenominator + multiplier) + "}\\)");
        options.add("\\(\\frac{" + (eqNumerator * 2) + "}{" + eqDenominator + "}\\)");

        // Ensure unique options
        int attempts = 0;
        while ((options.size() < 4 || new java.util.HashSet<>(options).size() < 4) && attempts < 100) {
            attempts++;
            options.add("\\(\\frac{" + RANDOM.nextInt(10) + "}{" + (RANDOM.nextInt(10) + 11) + "}\\)");
        }

        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }
}
