package com.myAgeEducation.cbseClass7.maths.simpleequations;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SimpleEquationQuestionGenerator {
    private static final Random RANDOM = new Random();

    private enum QuestionType {
        SUM_TIMES, SUBTRACT_FROM_TIMES, FRACTION_PLUS, FATHER_AGE, SUM_NUMBERS,
        SUBTRACTED_FROM, TIMES_VAR, DIVIDED_BY, TIMES_PLUS, TAKE_AWAY,
        MARBLES, LAXMI_FATHER, HIGHEST_MARKS, ISOSCELES_TRIANGLE
    }

    public static Question generateQuestion() {
        QuestionType type = QuestionType.values()[RANDOM.nextInt(QuestionType.values().length)];
        switch (type) {
            case SUM_TIMES: return generateSumTimes();
            case SUBTRACT_FROM_TIMES: return generateSubtractFromTimes();
            case FRACTION_PLUS: return generateFractionPlus();
            case FATHER_AGE: return generateFatherAge();
            case SUBTRACTED_FROM: return generateSubtractedFrom();
            case TIMES_VAR: return generateTimesVar();
            case DIVIDED_BY: return generateDividedBy();
            case TIMES_PLUS: return generateTimesPlus();
            case TAKE_AWAY: return generateTakeAway();
            case MARBLES: return generateMarbles();
            case LAXMI_FATHER: return generateLaxmiFather();
            case HIGHEST_MARKS: return generateHighestMarks();
            case ISOSCELES_TRIANGLE: return generateIsoscelesTriangle();
            default: return generateSumNumbers();
        }
    }

    private static Question generateSumTimes() {
        int n = 2 + RANDOM.nextInt(8);
        int m = 2 + RANDOM.nextInt(20);
        int xVal = 1 + RANDOM.nextInt(10);
        int res = n * xVal + m;
        String var = "x";

        String questionText = "Write the following statements in the form of equations:\n\nThe sum of " + numberToWords(n) + " times " + var + " and " + m + " is " + res + ".";
        String answer = n + var + " + " + m + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + var + " - " + m + " = " + res);
        options.add(m + var + " + " + n + " = " + res);
        options.add(n + var + " + " + res + " = " + m);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateSubtractFromTimes() {
        int n = 2 + RANDOM.nextInt(8);
        int m = 1 + RANDOM.nextInt(10);
        int xVal = 2 + RANDOM.nextInt(10);
        int res = n * xVal - m;

        String var = "n";
        String questionText = "Write the following statements in the form of equations:\n\nIf you subtract " + m + " from " + n + " times a number, you get " + res + ".";
        String answer = n + var + " - " + m + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + var + " + " + m + " = " + res);
        options.add(m + var + " - " + n + " = " + res);
        options.add(var + "/" + n + " - " + m + " = " + res);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateFractionPlus() {
        int n = 2 + RANDOM.nextInt(5); // denominator
        int m = 2 + RANDOM.nextInt(10);
        int xVal = n * (1 + RANDOM.nextInt(5));
        int res = xVal / n + m;

        String var = "m";
        String questionText = "Write the following statements in the form of equations:\n\nOne " + ordinalToWords(n) + " of a number plus " + m + " is " + res + ".";
        String answer = "\\(\\frac{" + var + "}{" + n + "}\\) + " + m + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + var + " + " + m + " = " + res);
        options.add("\\(\\frac{" + var + "}{" + n + "}\\) - " + m + " = " + res);
        options.add("\\(\\frac{" + n + "}{" + var + "}\\) + " + m + " = " + res);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateFatherAge() {
        int rajuAge = 5 + RANDOM.nextInt(15);
        int multiplier = 3;
        int extra = 2 + RANDOM.nextInt(8);
        int fatherAge = multiplier * rajuAge + extra;
        String var = "y";

        //String questionText = "What would be the right equation for the following statement:\n\nRaju's father's age is " + extra + " years more than " + numberToWords(multiplier) + " times Raju's age. Raju's father is " + fatherAge + " years old. (Let Raju's age be " + var + ")";
        String personName;

        if (RANDOM.nextBoolean()) {
            personName = PersonNameUtil.getMaleName();
        } else {
            personName = PersonNameUtil.getFemaleName();
        }

        String questionText = String.format(
                Locale.US,
                "What would be the right equation for the following statement:\n\n%s's father's age is %d years more than %s times %s's age. %s's father is %d years old. (Let %s's age be %s)",
                personName,
                extra,
                numberToWords(multiplier),
                personName,
                personName,
                fatherAge,
                personName,
                var
        );

        String answer = multiplier + var + " + " + extra + " = " + fatherAge;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(multiplier + var + " - " + extra + " = " + fatherAge);
        options.add(extra + var + " + " + multiplier + " = " + fatherAge);
        options.add(var + "/" + multiplier + " + " + extra + " = " + fatherAge);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateSumNumbers() {
        int m = 2 + RANDOM.nextInt(20);
        int xVal = 1 + RANDOM.nextInt(20);
        int res = xVal + m;
        String var = "x";

        String questionText = "What would be the equation for the following statement:\n\nThe sum of numbers " + var + " and " + m + " is " + res + ".";
        String answer = var + " + " + m + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(var + " - " + m + " = " + res);
        options.add(m + var + " = " + res);
        options.add(var + "/" + m + " = " + res);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateSubtractedFrom() {
        int m = 2 + RANDOM.nextInt(15);
        int yVal = m + 1 + RANDOM.nextInt(20);
        int res = yVal - m;
        String var = "y";

        String questionText = "What would be the equation for the following statement:\n\n" + m + " subtracted from " + var + " is " + res + ".";
        String answer = var + " - " + m + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(m + " - " + var + " = " + res);
        options.add(var + " + " + m + " = " + res);
        options.add(var + "/" + m + " = " + res);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateTimesVar() {
        int n = 2 + RANDOM.nextInt(11);
        int aVal = 2 + RANDOM.nextInt(10);
        int res = n * aVal;
        String var = "a";

        String questionText = "What would be the equation for the following statement:\n\n" + capitalize(numberToWords(n)) + " times " + var + " is " + res + ".";
        String answer = n + var + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(var + " + " + n + " = " + res);
        options.add(n + "/" + var + " = " + res);
        options.add(var + " - " + n + " = " + res);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateDividedBy() {
        int n = 2 + RANDOM.nextInt(8);
        int res = 2 + RANDOM.nextInt(10);
        String var = "b";

        String questionText = "What would be the equation for the following statement:\n\nThe number " + var + " divided by " + n + " gives " + res + ".";
        String answer = var + " / " + n + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + " / " + var + " = " + res);
        options.add(var + " - " + n + " = " + res);
        options.add(n + var + " = " + res);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateTimesPlus() {
        int n = 2 + RANDOM.nextInt(8);
        int p = 2 + RANDOM.nextInt(10);
        int mVal = 2 + RANDOM.nextInt(10);
        int res = n * mVal + p;
        String var = "m";

        String questionText = "What would be the equation for the following statement:\n\n" + capitalize(numberToWords(n)) + " times " + var + " plus " + p + " gets you " + res;
        String answer = n + var + " + " + p + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + var + " - " + p + " = " + res);
        options.add(var + "/" + n + " + " + p + " = " + res);
        options.add(n + var + " + " + res + " = " + p);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateTakeAway() {
        int n = 2 + RANDOM.nextInt(8);
        int m = 2 + RANDOM.nextInt(10);
        int yVal = 5 + RANDOM.nextInt(10);
        int res = n * yVal - m;
        
        String var = "y";
        String questionText = "What would be the equation for the following statement:\n\nIf you take away " + m + " from " + n + " times " + var + ", you get " + res + ".";
        String answer = n + var + " - " + m + " = " + res;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + var + " + " + m + " = " + res);
        options.add(var + "/" + n + " - " + m + " = " + res);
        options.add(m + " - " + n + var + " = " + res);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateMarbles() {
        int n = 3 + RANDOM.nextInt(5);
        int extra = 2 + RANDOM.nextInt(10);
        int parmitMarbles = 5 + RANDOM.nextInt(10);
        int irfanMarbles = n * parmitMarbles + extra;
        String var = "m";

        //String questionText = "What would be the equation for the following statement:\n\nIrfan says that he has " + extra + " marbles more than " + numberToWords(n) + " times the marbles Parmit has. Irfan has " + irfanMarbles + " marbles. (Take " + var + " to be the number of Parmit's marbles.)";

        String[] names = PersonNameUtil.getDifferentNames(2);
        String person1Name = names[0];
        String person2Name = names[1];

        String questionText = String.format(
                Locale.US,
                "What would be the equation for the following statement:\n\n%s says that %s has %d marbles more than %s times the marbles %s has. %s has %d marbles. (Take %s to be the number of %s's marbles.)",
                person1Name,
                person1Name,
                extra,
                numberToWords(n),
                person2Name,
                person1Name,
                irfanMarbles,
                var,
                person2Name
        );

        String answer = n + var + " + " + extra + " = " + irfanMarbles;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + var + " - " + extra + " = " + irfanMarbles);
        options.add(extra + var + " + " + n + " = " + irfanMarbles);
        options.add(var + "/" + n + " + " + extra + " = " + irfanMarbles);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateLaxmiFather() {
        int laxmiAge = 10 + RANDOM.nextInt(10);
        int multiplier = 3;
        int extra = 2 + RANDOM.nextInt(8);
        int fatherAge = multiplier * laxmiAge + extra;
        String var = "y";

        String questionText = "What would be the equation for the following statement:\n\nLaxmi's father is " + fatherAge + " years old. He is " + extra + " years older than " + numberToWords(multiplier) + " times Laxmi's age. (Take Laxmi's age to be " + var + " years.)";
        String answer = multiplier + var + " + " + extra + " = " + fatherAge;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(multiplier + var + " - " + extra + " = " + fatherAge);
        options.add(extra * multiplier + var + " = " + fatherAge);
        options.add(var + "/" + multiplier + " + " + extra + " = " + fatherAge);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateHighestMarks() {
        int lowest = 30 + RANDOM.nextInt(20);
        int n = 2;
        int extra = 5 + RANDOM.nextInt(10);
        int highest = n * lowest + extra;
        String var = "k";

        String questionText = "What would be the equation for the following statement:\n\nThe teacher tells the class that the highest marks obtained by a student in her class is " + numberToWords(n) + " the lowest marks plus " + extra + ". The highest score is " + highest + ". (Take the lowest score to be " + var + ".)";
        String answer = n + var + " + " + extra + " = " + highest;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(n + var + " - " + extra + " = " + highest);
        options.add(extra + var + " + " + n + " = " + highest);
        options.add(var + "/" + n + " + " + extra + " = " + highest);

        return createQuestion(questionText, answer, options);
    }

    private static Question generateIsoscelesTriangle() {
        int n = 2 + RANDOM.nextInt(3);
        String var = "b";
        int totalFactor = 2 + n;

        String questionText = "What would be the equation for the following statement:\n\nIn an isosceles triangle, the vertex angle is " + numberToWords(n) + " either base angle. (Let the base angle be " + var + " in degrees. Remember that the sum of angles of a triangle is 180 degrees).";
        String answer = totalFactor + var + " = 180";

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((n + 1) + var + " = 180");
        options.add(n + var + " = 180");
        options.add(var + " + " + n + " = 180");

        return createQuestion(questionText, answer, options);
    }

    private static Question createQuestion(String text, String answer, List<String> options) {
        Question question = new Question();
        question.setQuestion(text);
        question.setAnswer(answer);
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static String numberToWords(int n) {
        switch (n) {
            case 2: return "twice";
            case 3: return "three";
            case 4: return "four";
            case 5: return "five";
            case 6: return "six";
            case 7: return "seven";
            case 8: return "eight";
            case 9: return "nine";
            case 10: return "ten";
            default: return String.valueOf(n);
        }
    }

    private static String ordinalToWords(int n) {
        switch (n) {
            case 2: return "half";
            case 3: return "third";
            case 4: return "fourth";
            case 5: return "fifth";
            case 6: return "sixth";
            default: return n + "th";
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
