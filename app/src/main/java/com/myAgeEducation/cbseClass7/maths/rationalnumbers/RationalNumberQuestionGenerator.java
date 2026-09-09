package com.myAgeEducation.cbseClass7.maths.rationalnumbers;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RationalNumberQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(9);
        switch (type) {
            case 0: return generateConceptual();
            case 1: return generateIsRational();
            case 2: return generatePositiveNegativeIdentity();
            case 3: return generateChoosePositiveNegative();
            case 4: return generateStandardFormDefinition();
            case 5: return generateIsStandardForm();
            case 6: return generateConvertToStandardForm();
            case 7: return generateSameRationalNumber();
            case 8: return generateEquivalentProperty();
            default: return generateConceptual();
        }
    }

    private static Question generateConceptual() {
        String[][] bank = {
                {"All fractions are rational numbers.", "TRUE", "FALSE"},
                {"All integers are rational numbers.", "TRUE", "FALSE"},
                {"Rational numbers include integers and fractions.", "TRUE", "FALSE"},
                {"Zero is a rational number.", "TRUE", "FALSE"},
                {"Every natural number is a rational number.", "TRUE", "FALSE"},
                {"All fractions are NOT rational numbers.", "FALSE", "TRUE"}
        };
        int idx = RANDOM.nextInt(bank.length);
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. " + bank[idx][0]);
        q.setAnswer(bank[idx][1]);
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateIsRational() {
        int n = 1 + RANDOM.nextInt(20);
        int d = 1 + RANDOM.nextInt(20);
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. " + formatFraction(n, d) + " is a rational number");
        q.setAnswer("TRUE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generatePositiveNegativeIdentity() {
        int n = 1 + RANDOM.nextInt(20);
        int d = 1 + RANDOM.nextInt(20);
        int scenario = RANDOM.nextInt(4);

        Question q = new Question();
        String question;
        String answer;

        switch (scenario) {
            case 0: // both neg -> positive
                question = formatFraction(-n, -d) + " is a _______";
                answer = "positive rational number";
                break;
            case 1: // num neg -> negative
                question = formatFraction(-n, d) + " is a _______";
                answer = "negative rational number";
                break;
            case 2: // den neg -> negative
                question = formatFraction(n, -d) + " is a _______";
                answer = "negative rational number";
                break;
            default: // both pos -> positive
                question = formatFraction(n, d) + " is a _______";
                answer = "positive rational number";
                break;
        }

        q.setQuestion(question);
        q.setAnswer(answer);
        OptionUtils.setQuestionOptions(q, new String[]{"positive rational number", "negative rational number"});
        return q;
    }

    private static Question generateChoosePositiveNegative() {
        boolean findPositive = RANDOM.nextBoolean();
        Question q = new Question();
        q.setQuestion("Which one of the following is a " + (findPositive ? "positive" : "negative") + " rational number?");
        
        List<String> options = new ArrayList<>();
        String answer;
        
        if (findPositive) {
            answer = formatFraction(1 + RANDOM.nextInt(10), 1 + RANDOM.nextInt(10));
            options.add(answer);
            options.add(formatFraction(-(1 + RANDOM.nextInt(10)), 1 + RANDOM.nextInt(10)));
            options.add(formatFraction(1 + RANDOM.nextInt(10), -(1 + RANDOM.nextInt(10))));
            options.add(formatFraction(-(2 + RANDOM.nextInt(5)), 7 + RANDOM.nextInt(5)));
        } else {
            answer = formatFraction(-(1 + RANDOM.nextInt(10)), 1 + RANDOM.nextInt(10));
            options.add(answer);
            options.add(formatFraction(1 + RANDOM.nextInt(10), 1 + RANDOM.nextInt(10)));
            options.add(formatFraction(-(1 + RANDOM.nextInt(10)), -(1 + RANDOM.nextInt(10))));
            options.add(formatFraction(5, 7));
        }
        
        q.setAnswer(answer);
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateStandardFormDefinition() {
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. A rational number is said to be in the standard form if its denominator is a positive integer and the numerator and denominator have no common factor other than 1.");
        q.setAnswer("TRUE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateIsStandardForm() {
        Question q = new Question();
        q.setQuestion("Which one of the following rational number is in standard form?");
        
        List<String> options = new ArrayList<>();
        String answer = formatFraction(3, 5); 
        options.add(answer);
        options.add(formatFraction(16, 8));
        options.add(formatFraction(8, 12));
        options.add(formatFraction(8, 16));
        
        q.setAnswer(answer);
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateConvertToStandardForm() {
        int nSimplest = 1 + RANDOM.nextInt(5);
        int dSimplest = nSimplest + 1 + RANDOM.nextInt(5);
        while (gcd(nSimplest, dSimplest) != 1) {
            dSimplest++;
        }
        int common = 2 + RANDOM.nextInt(5);
        
        boolean neg = RANDOM.nextBoolean();
        int finalN = nSimplest * common;
        int finalD = dSimplest * common;
        if (neg) {
            if (RANDOM.nextBoolean()) finalN = -finalN; else finalD = -finalD;
        }

        Question q = new Question();
        q.setQuestion("Which one of the following rational number is the standard form of " + formatFraction(finalN, finalD) + "?");
        
        String answerStr = formatFraction(neg ? -nSimplest : nSimplest, dSimplest);
        q.setAnswer(answerStr);
        
        List<String> options = new ArrayList<>();
        options.add(answerStr);
        options.add(formatFraction(finalN, finalD));
        options.add(formatFraction(nSimplest, neg ? -dSimplest : dSimplest));
        options.add(formatFraction(nSimplest + 1, dSimplest));
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateSameRationalNumber() {
        int n1 = 1 + RANDOM.nextInt(5);
        int d1 = n1 + 1 + RANDOM.nextInt(5);
        while (gcd(n1, d1) != 1) d1++;

        int m1 = 2 + RANDOM.nextInt(3);
        int m2 = 4 + RANDOM.nextInt(3);
        
        boolean neg = RANDOM.nextBoolean();
        
        String pairAns;
        if (neg) {
            pairAns = formatFraction(-n1 * m1, d1 * m1) + " and " + formatFraction(n1 * m2, -d1 * m2);
        } else {
            pairAns = formatFraction(n1 * m1, d1 * m1) + " and " + formatFraction(n1 * m2, d1 * m2);
        }

        Question q = new Question();
        q.setQuestion("Which of the following pairs represent the same rational number?");
        q.setAnswer(pairAns);
        
        List<String> options = new ArrayList<>();
        options.add(pairAns);
        options.add(formatFraction(1, 3) + " and " + formatFraction(-1, 9));
        options.add(formatFraction(-7, 21) + " and " + formatFraction(3, 10));
        options.add(formatFraction(-16, 21) + " and " + formatFraction(20, -25));
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateEquivalentProperty() {
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. By multiplying the numerator and denominator of a rational number by the same non zero integer, we obtain another rational number equivalent to the given rational number.");
        q.setAnswer("TRUE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static String formatFraction(int n, int d) {
        return "\\(\\frac{" + n + "}{" + denominatorToString(d) + "}\\)";
    }

    private static String denominatorToString(int d) {
        return d < 0 ? "(" + d + ")" : String.valueOf(d);
    }

    private static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b > 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
