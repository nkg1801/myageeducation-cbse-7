package com.myAgeEducation.cbseClass7.maths.ratioandproportion;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ComparingQuantitiesQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(20);
        switch (type) {
            case 0: return generateRatioKmToM();
            case 1: return generateRatioMToM();
            case 2: return generateEquivalentRatios();
            case 3: return generateMapScale();
            case 4: return generateCarPetrol();
            case 5: return generateComputerLab();
            case 6: return generatePopulationDensity();
            case 7: return generateGirlPercentage();
            case 8: return generateDecimalToPercent();
            case 9: return generateAbsentPercentage();
            case 10: return generateDefectivePercentage();
            case 11: return generateVoterPercentage();
            case 12: return generateComplementaryPercentage();
            case 13: return generateBasketPercentage();
            case 14: return generateLessPopulatedStateQuestion();
            case 15: return generateOutOfOrderPercentageQuestion();
            case 18: return generateCityPopulationPercentage();
            case 19: return generateSalaryFromSavings();
            default: return generateGirlPercentage();
        }
    }

    private static Question generateRatioKmToM() {
        int km = 1 + RANDOM.nextInt(10);
        int m = (1 + RANDOM.nextInt(9)) * 100;
        int kmInM = km * 1000;
        int common = gcd(kmInM, m);
        
        Question q = new Question();
        q.setQuestion("What is the ratio of " + km + " km to " + m + " m?");
        String answer = (kmInM / common) + ":" + (m / common);
        q.setAnswer(answer);
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((m / common) + ":" + (kmInM / common));
        options.add(km + ":" + (m/100));
        options.add("1:10");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateRatioMToM() {
        int m1 = 1 + RANDOM.nextInt(10);
        int m2 = (1 + RANDOM.nextInt(9)) * 100;
        int common = gcd(m1, m2);
        
        Question q = new Question();
        q.setQuestion("What is the ratio of " + m1 + " m to " + m2 + " m?");
        String answer = (m1 / common) + ":" + (m2 / common);
        q.setAnswer(answer);
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((m2 / common) + ":" + (m1 / common));
        options.add("1:100");
        options.add("1:10");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateEquivalentRatios() {
        int a = 1 + RANDOM.nextInt(5);
        int b = a + 1 + RANDOM.nextInt(5);
        int c = 1 + RANDOM.nextInt(5);
        int d = c + 1 + RANDOM.nextInt(5);
        
        boolean equivalent = (a * d == b * c);
        
        Question q = new Question();
        q.setQuestion("Are the ratios " + a + ":" + b + " and " + c + ":" + d + " equivalent?");
        q.setAnswer(equivalent ? "Yes" : "No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateMapScale() {
        int scaleCm = 2;
        int scaleKm = 500 * (1 + RANDOM.nextInt(4));
        float mapDist = 1.5f + 0.5f * RANDOM.nextInt(5);
        float actualDist = (mapDist * scaleKm) / scaleCm;
        
        Question q = new Question();
        q.setQuestion("A map is given with a scale of " + scaleCm + " cm = " + scaleKm + " km. What is the actual distance between the two places in kms, if the distance in the map is " + mapDist + " cm?");
        q.setAnswer((int)actualDist + " km");
        
        List<String> options = new ArrayList<>();
        options.add((int)actualDist + " km");
        options.add((int)(actualDist * 2) + " km");
        options.add((int)(actualDist / 2) + " km");
        options.add((int)(actualDist + 500) + " km");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateCarPetrol() {
        int km1 = 100 + 10 * RANDOM.nextInt(10);
        int petrol1 = 10 + 5 * RANDOM.nextInt(5);
        int petrol2 = petrol1 + 5;
        int km2 = (km1 * petrol2) / petrol1;
        
        Question q = new Question();
        q.setQuestion("A car can go " + km1 + " km with " + petrol1 + " litres of petrol. How far can it go with " + petrol2 + " litres of petrol?");
        q.setAnswer(km2 + " km");
        
        List<String> options = new ArrayList<>();
        options.add(km2 + " km");
        options.add((km2 - 20) + " km");
        options.add((km2 + 50) + " km");
        options.add((km1 + 50) + " km");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateComputerLab() {
        int comp = 3;
        int students1 = 6;
        int students2 = 12 + 6 * RANDOM.nextInt(4);
        int compNeeded = (comp * students2) / students1;
        
        Question q = new Question();
        q.setQuestion("In a computer lab, there are " + comp + " computers for every " + students1 + " students. How many computers will be needed for " + students2 + " students?");
        q.setAnswer(String.valueOf(compNeeded));
        
        OptionUtils.setQuestionOptions(q, OptionUtils.generateNumberOptions(compNeeded, students2).toArray(new String[0]));
        return q;
    }

    private static Question generatePopulationDensity() {
        int pop = 300 + 10 * RANDOM.nextInt(50);
        int area = 2 + RANDOM.nextInt(4);
        int density = pop / area;
        
        Question q = new Question();
        q.setQuestion("Population of a region = " + pop + " lakhs. Area = " + area + " lakh km². How many people are there per km² in this region?");
        q.setAnswer(String.valueOf(density));
        
        OptionUtils.setQuestionOptions(q, OptionUtils.generateNumberOptions(density, pop).toArray(new String[0]));
        return q;
    }

    private static Question generateGirlPercentage() {
        int total = 20 + 5 * RANDOM.nextInt(3); // 20, 25, 30
        int girls = 5 * (1 + RANDOM.nextInt(total/5 - 1));
        int percent = (girls * 100) / total;
        
        Question q = new Question();
        q.setQuestion("Out of " + total + " children in a class, " + girls + " are girls. What is the percentage of girls?");
        q.setAnswer(percent + "%");
        
        List<String> options = new ArrayList<>();
        options.add(percent + "%");
        options.add((100 - percent) + "%");
        options.add((percent - 10) + "%");
        options.add((percent + 20) + "%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateDecimalToPercent() {
        float dec = 0.05f * (1 + RANDOM.nextInt(19));
        int percent = Math.round(dec * 100);
        
        Question q = new Question();
        q.setQuestion("Convert " + String.format(Locale.US, "%.2f", dec) + " to per cents.");
        q.setAnswer(percent + "%");
        
        List<String> options = new ArrayList<>();
        options.add(percent + "%");
        options.add(dec + "%");
        options.add((percent / 10.0f) + "%");
        options.add("50%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateAbsentPercentage() {
        int total = 20 + RANDOM.nextInt(20);
        int absent = 2 + RANDOM.nextInt(10);
        int percent = Math.round((absent * 100.0f) / total);
        
        Question q = new Question();
        q.setQuestion("Out of " + total + " students, " + absent + " are absent. What per cent of the students are absent?");
        q.setAnswer(percent + "%");
        
        List<String> options = new ArrayList<>();
        options.add(percent + "%");
        options.add((percent + 5) + "%");
        options.add("25%");
        options.add("10%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateDefectivePercentage() {
        int total = 100 * (1 + RANDOM.nextInt(5));
        int defective = 2 + RANDOM.nextInt(10);
        float percent = (defective * 100.0f) / total;
        
        Question q = new Question();
        q.setQuestion("A shop has " + total + " items, out of which " + defective + " are defective. What per cent are defective?");
        String ans = String.format(Locale.US, "%.1f", percent) + "%";
        if (ans.endsWith(".0%")) ans = (int)percent + "%";
        q.setAnswer(ans);
        
        List<String> options = new ArrayList<>();
        options.add(ans);
        options.add("1%");
        options.add("5%");
        options.add("10%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateVoterPercentage() {
        int total = 50 + 10 * RANDOM.nextInt(10);
        int voted = (int)(total * (0.5 + 0.1 * RANDOM.nextInt(5)));
        int percent = Math.round((voted * 100.0f) / total);
        
        Question q = new Question();
        q.setQuestion("There are " + total + " voters, " + voted + " of them voted. What per cent voted?");
        q.setAnswer(percent + "%");
        
        List<String> options = new ArrayList<>();
        options.add(percent + "%");
        options.add((percent - 10) + "%");
        options.add("75%");
        options.add("50%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateComplementaryPercentage() {
        int p1 = 20 + 5 * RANDOM.nextInt(10);
        int p2 = 100 - p1;
        
        Question q = new Question();
        q.setQuestion("If " + p1 + "% of students in a class have a bicycle, what per cent of the students do not have bicycles?");
        q.setAnswer(p2 + "%");
        
        List<String> options = new ArrayList<>();
        options.add(p2 + "%");
        options.add(p1 + "%");
        options.add((p2 - 10) + "%");
        options.add("50%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateBasketPercentage() {
        int pApples = 20 + 5 * RANDOM.nextInt(5); // 20 to 40
        int pOranges = 10 + 5 * RANDOM.nextInt(5); // 10 to 30
        int pMangoes = 100 - pApples - pOranges;
        
        Question q = new Question();
        q.setQuestion("We have a basket full of apples, oranges and mangoes. If " + pApples + "% are apples, " + pOranges + "% are oranges, then what per cent are mangoes?");
        q.setAnswer(pMangoes + "%");
        
        List<String> options = new ArrayList<>();
        options.add(pMangoes + "%");
        options.add((pMangoes - 5) + "%");
        options.add("20%");
        options.add("40%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateLessPopulatedStateQuestion() {
        int pop1 = 570;
        int area1 = 3;
        int pop2 = 1660;
        int area2 = 2;
        
        // Rajasthan vs UP style
        String state1 = "Rajasthan";
        String state2 = "UP";
        
        int density1 = pop1 / area1;
        int density2 = pop2 / area2;
        
        String questionText = "Population of " + state1 + " = " + pop1 + " lakhs and population of " + state2 + " = " + pop2 + " lakhs.<br>" +
                " Area of " + state1 + " = " + area1 + " lakh km² and area of " + state2 + " = " + area2 + " lakh km²<br>" +
                "Which state is less populated?";
        
        String answer = (density1 < density2) ? state1 : state2;
        
        Question q = new Question();
        q.setQuestion(questionText);
        q.setAnswer(answer);
        OptionUtils.setQuestionOptions(q, new String[]{state1, state2});
        return q;
    }

    private static Question generateOutOfOrderPercentageQuestion() {
        int total = 25;
        int outOfOrder = 16;
        int percent = (outOfOrder * 100) / total;
        
        Question q = new Question();
        q.setQuestion("There are " + total + " radios, " + outOfOrder + " of them are out of order. What per cent of radios are out of order?");
        q.setAnswer(percent + "%");
        
        List<String> options = new ArrayList<>();
        options.add(percent + "%");
        options.add("16%");
        options.add("25%");
        options.add("8%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateCityPopulationPercentage() {
        int pFemales = 20 + RANDOM.nextInt(20);
        int pMales = 30 + RANDOM.nextInt(20);
        int pChildren = 100 - pFemales - pMales;
        
        Question q = new Question();
        q.setQuestion("In a city, " + pFemales + "% are females, " + pMales + "% are males and remaining are children. What per cent are children?");
        q.setAnswer(pChildren + "%");
        
        List<String> options = new ArrayList<>();
        options.add(pChildren + "%");
        options.add((pChildren + 10) + "%");
        options.add("30%");
        options.add("20%");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateSalaryFromSavings() {
        int savings = 200 + 100 * RANDOM.nextInt(10);
        int percent = 5 + 5 * RANDOM.nextInt(4); // 5, 10, 15, 20
        int salary = (savings * 100) / percent;
        
        Question q = new Question();
        q.setQuestion("Meeta saves Rs " + savings + " from her salary. If this is " + percent + "% of her salary. What is her salary?");
        q.setAnswer(String.valueOf(salary));
        
        OptionUtils.setQuestionOptions(q, OptionUtils.generateNumberOptions(salary, salary + 1000).toArray(new String[0]));
        return q;
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
