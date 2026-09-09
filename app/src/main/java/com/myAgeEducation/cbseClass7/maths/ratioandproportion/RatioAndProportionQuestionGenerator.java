package com.myAgeEducation.cbseClass7.maths.ratioandproportion;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RatioAndProportionQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        RatioAndProportionQuestionType type = RatioAndProportionQuestionType.values()[RANDOM.nextInt(RatioAndProportionQuestionType.values().length)];
        switch (type) {
            case SIMPLEST_FORM:
                return generateSimplestForm();
            case EQUIVALENT_RATIO:
                return generateEquivalentRatio();
            case PROPORTION_CHECK:
                return generateProportionCheck();
            case PROPORTION_MISSING_VALUE:
                return generateProportionMissingValue();
            case DIVIDE_QUANTITY:
                return generateDivideQuantity();
            case WORD_PROBLEM_RATIO:
                return generateWordProblemRatio();
            case UNITARY_METHOD:
                return generateUnitaryMethod();
            case UNIT_CONVERSION_RATIO:
                return generateUnitConversionRatio();
            default:
                return generateSimplestForm();
        }
    }

    private static Question generateUnitConversionRatio() {
        int scenario = RANDOM.nextInt(3);
        String questionText = "";
        String answer = "";
        
        switch (scenario) {
            case 0: // Length: Lizard vs Crocodile
            {
                int lizardCm = 10 + RANDOM.nextInt(21); // 10 to 30 cm
                int crocM = 2 + RANDOM.nextInt(5); // 2 to 6 m
                int crocCm = crocM * 100;
                
                int common = gcd(crocCm, lizardCm);
                questionText = "Length of a house lizard is " + lizardCm + " cm and the length of a crocodile is " + crocM + " m. What is the ratio of the length of the crocodile to the length of the lizard?";
                answer = (crocCm / common) + " : " + (lizardCm / common);
                break;
            }

            case 1: // Time: Saurabh vs Sachin
            {
                int saurabhMin = (2 + RANDOM.nextInt(5)) * 5; // 10, 15, 20, 25, 30 mins
                int sachinHours = 1 + RANDOM.nextInt(2); // 1 or 2 hours
                int sachinMin = sachinHours * 60;
                
                int common = gcd(saurabhMin, sachinMin);
                questionText = "Saurabh takes " + saurabhMin + " minutes to reach school and Sachin takes " + sachinHours + " " + (sachinHours == 1 ? "hour" : "hours") + " to reach school. What is the ratio of the time taken by Saurabh to the time taken by Sachin?";
                answer = (saurabhMin / common) + " : " + (sachinMin / common);
                break;
            }

            case 2: // Cost: Toffee vs Chocolate
            {
                int toffeePaise = (1 + RANDOM.nextInt(5)) * 25; // 25, 50, 75, 100, 125 paise
                int chocRs = 5 + RANDOM.nextInt(16); // 5 to 20 Rs
                int chocPaise = chocRs * 100;
                
                int common = gcd(toffeePaise, chocPaise);
                questionText = "Cost of a toffee is " + toffeePaise + " paise and cost of a chocolate is Rs " + chocRs + ". What is the ratio of the cost of a toffee to the cost of a chocolate?";
                answer = (toffeePaise / common) + " : " + (chocPaise / common);
                break;
            }
        }

        List<String> options = new ArrayList<>();
        options.add(answer);
        // Add some common distractors: inverted ratio or wrong conversion
        String[] parts = answer.split(" : ");
        options.add(parts[1] + " : " + parts[0]);
        options.add("1 : 2");
        options.add("2 : 5");
        
        while (options.size() < 4) {
            options.add((RANDOM.nextInt(10) + 1) + " : " + (RANDOM.nextInt(10) + 1));
        }
        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateSimplestForm() {
        int commonFactor = 2 + RANDOM.nextInt(9);
        int aSimplest = 1 + RANDOM.nextInt(12);
        int bSimplest = 1 + RANDOM.nextInt(12);

        while (gcd(aSimplest, bSimplest) != 1) {
            bSimplest = 1 + RANDOM.nextInt(12);
        }

        int a = aSimplest * commonFactor;
        int b = bSimplest * commonFactor;

        String questionText = "Express the ratio " + a + " : " + b + " in the simplest form.";
        String answer = aSimplest + " : " + bSimplest;

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(bSimplest + " : " + aSimplest);
        options.add((aSimplest + 1) + " : " + bSimplest);
        options.add(aSimplest + " : " + (bSimplest + 1));
        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateEquivalentRatio() {
        int a = 1 + RANDOM.nextInt(10);
        int b = 2 + RANDOM.nextInt(10);
        int multiplier = 2 + RANDOM.nextInt(5);
        int c = a * multiplier;
        int d = b * multiplier;

        boolean missingC = RANDOM.nextBoolean();
        String questionText;
        String answer;

        if (missingC) {
            questionText = "Find the missing value: " + a + " / " + b + " = ____ / " + d;
            answer = String.valueOf(c);
        } else {
            questionText = "Find the missing value: " + a + " / " + b + " = " + c + " / ____";
            answer = String.valueOf(d);
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, OptionUtils.generateNumberOptions(Integer.parseInt(answer), 100).toArray(new String[0]));
        return question;
    }

    private static Question generateProportionCheck() {
        boolean inProportion = RANDOM.nextBoolean();
        int a, b, c, d;

        if (inProportion) {
            int r1 = 1 + RANDOM.nextInt(5);
            int r2 = 2 + RANDOM.nextInt(5);
            int m1 = 1 + RANDOM.nextInt(5);
            int m2 = 6 + RANDOM.nextInt(5);
            a = r1 * m1;
            b = r2 * m1;
            c = r1 * m2;
            d = r2 * m2;
        } else {
            a = 1 + RANDOM.nextInt(10);
            b = 11 + RANDOM.nextInt(10);
            c = 21 + RANDOM.nextInt(10);
            d = 31 + RANDOM.nextInt(10);
            // double check just in case
            if ((long)a * d == (long)b * c) d++;
        }

        String questionText = "Are " + a + ", " + b + ", " + c + ", " + d + " in proportion?";
        String answer = inProportion ? "TRUE" : "FALSE";

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static Question generateProportionMissingValue() {
        int r1 = 1 + RANDOM.nextInt(5);
        int r2 = 2 + RANDOM.nextInt(5);
        int m1 = 2 + RANDOM.nextInt(5);
        int m2 = 7 + RANDOM.nextInt(5);
        
        int a = r1 * m1;
        int b = r2 * m1;
        int c = r1 * m2;
        int d = r2 * m2;

        int missing = RANDOM.nextInt(4);
        String questionText;
        String answer;

        switch (missing) {
            case 0:
                questionText = "Find x if x : " + b + " :: " + c + " : " + d;
                answer = String.valueOf(a);
                break;

            case 1:
                questionText = "Find x if " + a + " : x :: " + c + " : " + d;
                answer = String.valueOf(b);
                break;

            case 2:
                questionText = "Find x if " + a + " : " + b + " :: x : " + d;
                answer = String.valueOf(c);
                break;

            default:
                questionText = "Find x if " + a + " : " + b + " :: " + c + " : x";
                answer = String.valueOf(d);
                break;
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, OptionUtils.generateNumberOptions(Integer.parseInt(answer), 100).toArray(new String[0]));
        return question;
    }

    private static Question generateDivideQuantity() {
        int r1 = 1 + RANDOM.nextInt(5);
        int r2 = 1 + RANDOM.nextInt(5);
        int m = 5 + RANDOM.nextInt(16);
        int total = (r1 + r2) * m;
        int part1 = r1 * m;
        int part2 = r2 * m;

        String[] scenarios = {
            "Divide Rs " + total + " between Ravi and Shikha in the ratio " + r1 + " : " + r2 + ". How much does Ravi get?",
            "Divide Rs " + total + " between Ravi and Shikha in the ratio " + r1 + " : " + r2 + ". How much does Shikha get?",
            "Divide " + total + " kg of sweets between A and B in the ratio " + r1 + " : " + r2 + ". How much does A get?"
        };

        int scenarioIdx = RANDOM.nextInt(scenarios.length);
        String questionText = scenarios[scenarioIdx];
        String answer = String.valueOf(scenarioIdx == 1 ? part2 : part1);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, OptionUtils.generateNumberOptions(Integer.parseInt(answer), total).toArray(new String[0]));
        return question;
    }

    private static Question generateWordProblemRatio() {
        int m = 2 + RANDOM.nextInt(10);
        int boysSimplest = 2 + RANDOM.nextInt(5);
        int girlsSimplest = 2 + RANDOM.nextInt(5);
        while (boysSimplest == girlsSimplest) girlsSimplest = 2 + RANDOM.nextInt(5);
        
        int boys = boysSimplest * m;
        int girls = girlsSimplest * m;
        int total = boys + girls;

        int type = RANDOM.nextInt(3);
        String questionText;
        String answer;
        
        int g = gcd(boys, girls);
        int gTotalBoys = gcd(total, boys);
        int gTotalGirls = gcd(total, girls);

        switch (type) {
            case 0:
                questionText = "In a class, there are " + boys + " boys and " + girls + " girls. What is the ratio of number of boys to number of girls?";
                answer = (boys/g) + " : " + (girls/g);
                break;
            case 1:
                questionText = "In a class, there are " + boys + " boys and " + girls + " girls. What is the ratio of number of boys to total number of students?";
                answer = (boys/gTotalBoys) + " : " + (total/gTotalBoys);
                break;
            default:
                questionText = "In a class, there are " + boys + " boys and " + girls + " girls. What is the ratio of number of girls to total number of students?";
                answer = (girls/gTotalGirls) + " : " + (total/gTotalGirls);
                break;
        }

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((girls/g) + " : " + (boys/g));
        options.add("1 : 2");
        options.add("2 : 3");
        if (options.size() > 4) options = options.subList(0, 4);
        while (options.size() < 4) options.add(RANDOM.nextInt(5)+":"+(RANDOM.nextInt(5)+6));
        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateUnitaryMethod() {
        int unitCount = 2 + RANDOM.nextInt(9);
        int unitPrice = 5 + RANDOM.nextInt(20);
        int total1 = unitCount * unitPrice;
        int targetCount = 2 + RANDOM.nextInt(15);
        while (targetCount == unitCount) targetCount = 2 + RANDOM.nextInt(15);
        int answerVal = targetCount * unitPrice;

        String[] items = {"pens", "books", "notebooks", "erasers", "chocolates"};
        String item = items[RANDOM.nextInt(items.length)];

        String questionText = "If the cost of " + unitCount + " " + item + " is Rs " + total1 + ", find the cost of " + targetCount + " " + item + ".";
        String answer = String.valueOf(answerVal);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, OptionUtils.generateNumberOptions(answerVal, answerVal * 2).toArray(new String[0]));
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
}
