package com.myAgeEducation.cbseClass7.maths.algebra;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AlgebraicExpressionsQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(4);
        switch (type) {
            case 0: return generateCoefficientQuestion();
            case 1: return generateExpressionTypeConceptQuestion();
            case 2: return generateIdentifyExpressionTypeQuestion();
            case 3: return generateTrueFalseExpressionTypeQuestion();
            default: return generateCoefficientQuestion();
        }
    }

    private static Question generateCoefficientQuestion() {
        int subtype = RANDOM.nextInt(5);
        String questionText;
        String answer;
        List<String> options = new ArrayList<>();

        switch (subtype) {
            case 0: // Simple coefficient: "What is the coefficient of xy in 5xy"
            {
                int coeff = 2 + RANDOM.nextInt(8);
                questionText = "What is the coefficient of xy in " + coeff + "xy";
                answer = String.valueOf(coeff);
                options.add(answer);
                options.add("x");
                options.add("y");
                options.add("1");
                break;
            }
            case 1: // Negative coefficient with squares: "What is the coefficient of x²y² in -7x²y²"
            {
                int coeff = -(2 + RANDOM.nextInt(8));
                questionText = "What is the coefficient of \\(x^2y^2\\) in " + coeff + "\\(x^2y^2\\)";
                answer = String.valueOf(coeff);
                options.add(answer);
                options.add(String.valueOf(Math.abs(coeff)));
                options.add("\\(y^2\\)");
                options.add("\\(x^2\\)");
                break;
            }
            case 2: // Coefficient in a polynomial: "What is the coefficient of x in 8 - x + y"
            {
                int constant = 5 + RANDOM.nextInt(10);
                questionText = "What is the coefficient of x in " + constant + " - x + y";
                answer = "-1";
                options.add(answer);
                options.add("1");
                options.add(String.valueOf(constant));
                options.add("y");
                break;
            }
            case 3: // Coefficient with square: "What is the coefficient of x in y²x - y"
            {
                questionText = "What is the coefficient of x in \\(y^2x - y\\)";
                answer = "\\(y^2\\)";
                options.add(answer);
                options.add("y");
                options.add("-1");
                options.add("2");
                break;
            }
            default: // Coefficient with another variable: "What is the coefficient of y in 8 + yz"
            {
                int constant = 5 + RANDOM.nextInt(10);
                questionText = "What is the coefficient of y in " + constant + " + yz";
                answer = "z";
                options.add(answer);
                options.add("1");
                options.add(String.valueOf(constant));
                options.add("+");
                break;
            }
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateExpressionTypeConceptQuestion() {
        String[] types = {"monomial", "binomial", "trinomial", "polynomial"};
        int typeIdx = RANDOM.nextInt(types.length);
        String targetType = types[typeIdx];
        
        int termCount;
        switch (targetType) {
            case "monomial":
                termCount = 1;
                break;
            case "binomial":
                termCount = 2;
                break;
            case "trinomial":
                termCount = 3;
                break;
            default:
                termCount = 1 + RANDOM.nextInt(3); // Polynomial can be any of them
                break;
        }

        boolean isTrue = RANDOM.nextBoolean();
        String questionText;
        String answer;

        if (isTrue) {
            questionText = "TRUE or FALSE. An expression with only " + numberToWords(termCount) + (termCount == 1 ? " term" : " unlike terms") + " is called a " + targetType;
            answer = "TRUE";
        } else {
            String wrongType;
            do {
                wrongType = types[RANDOM.nextInt(types.length)];
            } while (Objects.equals(wrongType, targetType) || (Objects.equals(wrongType, "polynomial") && termCount <= 3));
            
            questionText = "TRUE or FALSE. An expression with only " + numberToWords(termCount) + (termCount == 1 ? " term" : " unlike terms") + " is called a " + wrongType;
            answer = "FALSE";
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setOption1("TRUE");
        question.setOption2("FALSE");
        return question;
    }

    private static Question generateIdentifyExpressionTypeQuestion() {
        int termCount = 1 + RANDOM.nextInt(3);
        String expression;
        String answer;

        if (termCount == 1) {
            expression = (1 + RANDOM.nextInt(10)) + "x";
            answer = "monomial";
        } else if (termCount == 2) {
            expression = "x + y";
            answer = "binomial";
        } else {
            expression = "x + y + " + (1 + RANDOM.nextInt(10));
            answer = "trinomial";
        }

        String questionText = "The expression " + expression + " is a __________";
        String[] options = {"monomial", "binomial", "trinomial", "All of the above"};
        
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static Question generateTrueFalseExpressionTypeQuestion() {
        // Covering specific user request: "TRUE or FALSE. An expression which contains three terms is called a trinomial."
        int termCount = 2 + RANDOM.nextInt(2); // 2 or 3
        String type = (termCount == 2) ? "binomial" : "trinomial";
        
        boolean isTrue = RANDOM.nextBoolean();
        String questionText;
        String answer;

        if (isTrue) {
            questionText = "TRUE or FALSE. An expression which contains " + numberToWords(termCount) + " unlike terms is called a " + type;
            answer = "TRUE";
        } else {
            String wrongType = (termCount == 2) ? "trinomial" : "binomial";
            questionText = "TRUE or FALSE. An expression which contains " + numberToWords(termCount) + " unlike terms is called a " + wrongType;
            answer = "FALSE";
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setOption1("TRUE");
        question.setOption2("FALSE");
        return question;
    }

    private static String numberToWords(int n) {
        switch (n) {
            case 1: return "one";
            case 2: return "two";
            case 3: return "three";
            default: return String.valueOf(n);
        }
    }
}
