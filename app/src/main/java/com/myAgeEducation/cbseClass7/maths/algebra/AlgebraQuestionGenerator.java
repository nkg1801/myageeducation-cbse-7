package com.myAgeEducation.cbseClass7.maths.algebra;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlgebraQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        AlgebraQuestionType type = AlgebraQuestionType.values()[RANDOM.nextInt(AlgebraQuestionType.values().length)];
        switch (type) {
            case CONCEPTUAL:
                return generateConceptual();
            case NUMERICAL_EXPRESSION:
                return generateNumericalExpression();
            case WORD_PROBLEM:
                return generateWordProblem();
            case IDENTIFY_EQUATION:
                return generateIdentifyEquation();
            default:
                return generateConceptual();
        }
    }

    private static Question generateConceptual() {
        String[][] bank = {
                {"The branch of mathematics in which we study numbers is ________", "Arithmetic", "Geometry", "Algebra", "Trigonometry"},
                {"The branch of mathematics in which we study shapes is _________", "Geometry", "Arithmetic", "Algebra", "Calculus"},
                {"The branch of mathematics in which we use letters to represent numbers is ________", "Algebra", "Arithmetic", "Geometry", "Statistics"},
                {"A symbol which takes various numerical values is called a ________", "variable", "constant", "equation", "expression"},
                {"A symbol which has a fixed numerical value is called a ________", "constant", "variable", "term", "factor"}
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

    private static Question generateNumericalExpression() {
        String var = "xyzpmn".charAt(RANDOM.nextInt(6)) + "";
        String[] good = {
                "(5 x 7) + 3",
                "10 - (2 x 4)",
                "5 + 8 + 3",
                "(20 / 4) + 1"
        };
        String[] bad = {
                "5" + var + " + 3",
                "10 - 2" + var,
                "5 + " + var + " + 3",
                "(20 / " + var + ") + 1"
        };

        boolean numbersOnly = RANDOM.nextBoolean();
        String questionText = "Which out of the following are expressions with numbers only?";
        String answer;
        
        List<String> options = new ArrayList<>();
        if (numbersOnly) {
            answer = good[RANDOM.nextInt(good.length)];
            options.add(answer);
            while (options.size() < 4) {
                options.add(bad[RANDOM.nextInt(bad.length)]);
            }
        } else {
            // Not a great question format for "numbers only" if I want just one answer.
            // Let's stick to identifying the one that IS numbers only.
            answer = good[RANDOM.nextInt(good.length)];
            options.add(answer);
            List<String> badList = new ArrayList<>(Arrays.asList(bad));
            Collections.shuffle(badList);
            for(int i=0; i<3; i++) options.add(badList.get(i));
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateWordProblem() {
        int n = 2 + RANDOM.nextInt(5); // 2 to 6
        int m = 1 + RANDOM.nextInt(10); // 1 to 10
        String var = "b";
        String[] scenarios = {
            "The length of a rectangular hall is " + m + " meters less than " + n + " times the breadth of the hall. What is the length, if the breadth is " + var + " meters?",
            "The length of a rectangular hall is " + m + " meters more than " + n + " times the breadth of the hall. What is the length, if the breadth is " + var + " meters?",
        };
        
        int scenarioIdx = RANDOM.nextInt(scenarios.length);
        String questionText = scenarios[scenarioIdx];
        String answer = (scenarioIdx == 0) ? (n + var + " - " + m) : (n + var + " + " + m);

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + var);
        options.add(var + " + " + m);
        options.add(n + " + " + var + " + " + m);
        
        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateIdentifyEquation() {
        String var = "xyzpmn".charAt(RANDOM.nextInt(6)) + "";
        int a = 10 + RANDOM.nextInt(50);
        int b = 10 + RANDOM.nextInt(50);
        
        String answer = var + " + " + a + " = " + b;
        String questionText = "Which of the following is an equation?";

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(var + " + " + a + " > " + b);
        options.add(a + " < " + b);
        options.add(RANDOM.nextInt(100) + " > " + RANDOM.nextInt(50));
        
        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }
}
