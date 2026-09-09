package com.myAgeEducation.cbseClass7.maths.percentages;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.fractions.ChoiceFractionData;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionChoiceGenerator;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionData;
import com.myAgeEducation.cbseClass7.maths.fractions.FractionImageGenerator;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PercentageQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(6);
        switch (type) {
            case 0: return generatePercentValueShadedQuestion();
            case 1: return generateWhichFigureIsPercentageShadedQuestion();
            case 2: return generatePercentValueNotShadedQuestion();
            case 3: return generatePercentOfNumberQuestion();
            case 4: return generateFindTotalFromPercentQuestion();
            default: return generateXIsPercentOfWhatQuestion();
        }
    }

    public static Question generatePercentValueShadedQuestion() {
        // Select a denominator that gives a nice percentage (multiples of 2, 4, 5, 10, 20, 25, 50)
        int[] denominators = {2, 4, 5, 10};
        int denominator = denominators[RANDOM.nextInt(denominators.length)];
        int numerator = RANDOM.nextInt(denominator) + 1;
        
        int percentage = (numerator * 100) / denominator;
        String answer = percentage + "%";
        
        FractionData fraction = FractionImageGenerator.randomFraction();
        // Override to use our specific fraction but keep random shape/theme if supported
        FractionImageGenerator.FractionShape shape = fraction.shape;
        if (!shape.supports(denominator)) {
            shape = FractionImageGenerator.FractionShape.CIRCLE;
        }
        fraction = new FractionData(numerator, denominator, shape, fraction.theme, fraction.variation);

        String questionText = "What per cent of the following figure is shaded?";
        
        Set<String> options = new LinkedHashSet<>();
        options.add(answer);
        while (options.size() < 4) {
            int fake = (RANDOM.nextInt(10) + 1) * 10;
            if (fake == percentage) continue;
            options.add(fake + "%");
        }
        
        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);

        Question question = new Question();
        question.setQuestion(questionText);
        OptionUtils.setQuestionOptions(question, optionList);
        question.setAnswer(answer);
        question.setImage(fraction.getImageCode());
        return question;
    }

    public static Question generateWhichFigureIsPercentageShadedQuestion() {
        ChoiceFractionData data = FractionChoiceGenerator.generatePercentageShadedQuestion();
        Question question = new Question();
        question.setQuestion(data.questionText);
        question.setImage(data.imageCode);

        question.setOption1("A");
        question.setOption2("B");
        question.setOption3("C");
        question.setOption4("D");

        question.setAnswer(data.answer);
        return question;
    }

    public static Question generatePercentValueNotShadedQuestion() {
        // Select a denominator that gives a nice percentage
        int[] denominators = {2, 4, 5, 10};
        int denominator = denominators[RANDOM.nextInt(denominators.length)];
        int numerator = RANDOM.nextInt(denominator) + 1; // shaded parts

        int unshadedParts = denominator - numerator;
        int percentage = (unshadedParts * 100) / denominator;
        String answer = percentage + "%";

        FractionData fraction = FractionImageGenerator.randomFraction();
        FractionImageGenerator.FractionShape shape = fraction.shape;
        if (!shape.supports(denominator)) {
            shape = FractionImageGenerator.FractionShape.CIRCLE;
        }
        fraction = new FractionData(numerator, denominator, shape, fraction.theme, fraction.variation);

        String questionText = "What per cent of the following figure is NOT shaded?";

        Set<String> options = new LinkedHashSet<>();
        options.add(answer);
        while (options.size() < 4) {
            int fake = (RANDOM.nextInt(10) + 1) * 10;
            if (fake == percentage) continue;
            options.add(fake + "%");
        }

        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);

        Question question = new Question();
        question.setQuestion(questionText);
        OptionUtils.setQuestionOptions(question, optionList);
        question.setAnswer(answer);
        question.setImage(fraction.getImageCode());
        return question;
    }

    public static Question generatePercentOfNumberQuestion() {
        int[] totals = {20, 40, 50, 60, 80, 100, 120, 200};
        int[] percentages = {10, 20, 25, 30, 40, 50, 60, 70, 75, 80, 90};
        
        int total, percent, part;
        while (true) {
            total = totals[RANDOM.nextInt(totals.length)];
            percent = percentages[RANDOM.nextInt(percentages.length)];
            if ((total * percent) % 100 == 0) {
                part = (total * percent) / 100;
                break;
            }
        }

        String questionText = String.format("A survey of %d children showed that %d%% liked playing football. How many children liked playing football?", total, percent);
        String answer = String.valueOf(part);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, OptionUtils.generateNumberOptions(part, total).toArray(new String[0]));
        return question;
    }

    public static Question generateFindTotalFromPercentQuestion() {
        int[] parts = {5, 10, 15, 20, 25, 30, 40, 50};
        int[] percentages = {10, 20, 25, 40, 50, 75};

        int part, percent, total;
        while (true) {
            part = parts[RANDOM.nextInt(parts.length)];
            percent = percentages[RANDOM.nextInt(percentages.length)];
            if ((part * 100) % percent == 0) {
                total = (part * 100) / percent;
                break;
            }
        }

        String questionText;
        if (RANDOM.nextBoolean()) {
            questionText = String.format("Rahul bought a sweater and saved Rs %d when a discount of %d%% was given. What was the price of the sweater before the discount?", part, percent);
            Question question = new Question();
            question.setQuestion(questionText);
            question.setAnswer("Rs " + total);
            List<String> options = new ArrayList<>();
            options.add("Rs " + total);
            options.add("Rs " + (total + 20));
            options.add("Rs " + (total - 20));
            options.add("Rs " + (part * 2));
            Collections.shuffle(options);
            OptionUtils.setQuestionOptions(question, options);
            return question;
        } else {
            questionText = String.format("%d%% of what number is %d?", percent, part);
            Question question = new Question();
            question.setQuestion(questionText);
            question.setAnswer(String.valueOf(total));
            OptionUtils.setQuestionOptions(question, OptionUtils.generateNumberOptions(total, total * 2).toArray(new String[0]));
            return question;
        }
    }

    public static Question generateXIsPercentOfWhatQuestion() {
        int[] parts = {5, 9, 10, 12, 15, 18, 20, 25};
        int[] percentages = {10, 20, 25, 50, 75};

        int part, percent, total;
        while (true) {
            part = parts[RANDOM.nextInt(parts.length)];
            percent = percentages[RANDOM.nextInt(percentages.length)];
            if ((part * 100) % percent == 0) {
                total = (part * 100) / percent;
                break;
            }
        }

        String questionText = String.format("%d is %d%% of what number?", part, percent);
        String answer = String.valueOf(total);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, OptionUtils.generateNumberOptions(total, total * 2).toArray(new String[0]));
        return question;
    }
}
