package com.myAgeEducation.cbseClass7.maths.datahandling;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataHandlingArithmeticQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(8);
        switch (type) {
            case 1: return generateMeanRunsInnings();
            case 2: return generateRangeOfAges();
            case 3: return generateMeanFirstWholeNumbers();
            case 4: return generateMeanCricketScore();
            case 5: return generateMeanMarks();
            case 6: return generateHighestLowestMarks();
            case 7: return generateMeanEnrolment();
            default: return generateMeanStudyHours();
        }
    }

    private static Question generateMeanStudyHours() {
        int d1 = 2 + RANDOM.nextInt(4);
        int d2 = 3 + RANDOM.nextInt(4);
        int d3 = 2 + RANDOM.nextInt(4);
        
        int total = d1 + d2 + d3;
        while (total % 3 != 0) {
            d3 = 2 + RANDOM.nextInt(4);
            total = d1 + d2 + d3;
        }
        int mean = total / 3;
        String personName;
        String pronoun;

        if (RANDOM.nextBoolean()) {
            personName = PersonNameUtil.getMaleName();
            pronoun = "he";
        } else {
            personName = PersonNameUtil.getFemaleName();
            pronoun = "she";
        }

        String questionText = String.format(
                Locale.US,
                "%s studies for %d hours, %d hours and %d hours respectively on three consecutive days. How many hours does %s study daily on an average?",
                personName, d1, d2, d3, pronoun);

        String answer = mean + (mean == 1 ? " hour" : " hours");

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("1 hour");
        options.add("2 hours");
        options.add("3 hours");
        options.add("4 hours");
        
        // Clean options
        List<String> finalOptions = new ArrayList<>();
        for(String o : options) if(!finalOptions.contains(o)) finalOptions.add(o);
        while(finalOptions.size() < 4) finalOptions.add(RANDOM.nextInt(10) + " hours");

        Collections.shuffle(finalOptions);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, finalOptions);
        return question;
    }

    private static Question generateMeanRunsInnings() {
        int[] runs = new int[6];
        int total = 0;
        for (int i = 0; i < 6; i++) {
            runs[i] = 30 + RANDOM.nextInt(40);
            total += runs[i];
        }
        
        // Adjust last run to make mean an integer
        int remainder = total % 6;
        if (remainder != 0) {
            runs[5] += (6 - remainder);
            total += (6 - remainder);
        }
        int mean = total / 6;

        String questionText = "A batsman scored the following number of runs in six innings:<br><br> <strong><big>" + 
                Arrays.toString(runs).replace("[", "").replace("]", "") + "</big></strong> <br><br>What is the mean runs scored by him in an inning.";
        
        String answer = String.valueOf(mean);
        List<String> options = OptionUtils.generateNumberOptions(mean, total);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static Question generateRangeOfAges() {
        int[] ages = new int[10];
        int min = 100, max = -1;
        for (int i = 0; i < 10; i++) {
            ages[i] = 23 + RANDOM.nextInt(35);
            if (ages[i] < min) min = ages[i];
            if (ages[i] > max) max = ages[i];
        }
        int range = max - min;

        String questionText = "The ages in years of 10 teachers of a school are:<br><br><strong><big>" + 
                Arrays.toString(ages).replace("[", "").replace("]", "") + "</big></strong><br><br>What is the range of the ages of the teachers?";
        
        String answer = String.valueOf(range);
        List<String> options = OptionUtils.generateNumberOptions(range, 100);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static Question generateMeanFirstWholeNumbers() {
        int n = 5; // As per sample
        int sum = (n - 1) * n / 2;
        int mean = sum / n; // 0,1,2,3,4 -> sum 10, mean 2
        
        String questionText = "The mean of the first five whole numbers is ________";
        String answer = String.valueOf(mean);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, new String[]{"2", "3", "4", "5"});
        return question;
    }

    private static Question generateMeanCricketScore() {
        int[] runs = new int[8];
        int total = 0;
        for (int i = 0; i < 7; i++) {
            runs[i] = RANDOM.nextInt(101);
            total += runs[i];
        }
        // Adjust 8th inning for integer mean
        int remainder = total % 8;
        runs[7] = (remainder == 0) ? 0 : (8 - remainder);
        total += runs[7];
        int mean = total / 8;

        String questionText = "A cricketer scores the following runs in eight innings:<br><br><big><big>" + 
                Arrays.toString(runs).replace("[", "").replace("]", "") + "</big></big><br><br>What is his mean score?";
        
        String answer = String.valueOf(mean);
        List<String> options = OptionUtils.generateNumberOptions(mean, 150);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static Question generateMeanMarks() {
        int[] marks = new int[10];
        int total = 0;
        for (int i = 0; i < 10; i++) {
            marks[i] = 30 + RANDOM.nextInt(71);
            total += marks[i];
        }
        
        // Ensure mean is integer
        int remainder = total % 10;
        if (remainder != 0) {
            marks[9] += (10 - remainder);
            total += (10 - remainder);
        }
        int mean = total / 10;

        String questionText = "The marks (out of 100) obtained by a group of students in a science test are <strong><big>" + 
                Arrays.toString(marks).replace("[", "").replace("]", "") + "</big></strong>. What is the mean marks obtained by the group?";
        
        String answer = String.valueOf(mean);
        List<String> options = OptionUtils.generateNumberOptions(mean, 100);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static Question generateHighestLowestMarks() {
        int[] marks = new int[10];
        int min = 101, max = -1;
        for (int i = 0; i < 10; i++) {
            marks[i] = 30 + RANDOM.nextInt(71);
            if (marks[i] < min) min = marks[i];
            if (marks[i] > max) max = marks[i];
        }

        String questionText = "The marks (out of 100) obtained by a group of students in a science test are <strong><big>" + 
                Arrays.toString(marks).replace("[", "").replace("]", "") + "</big></strong>. What is the highest and the lowest marks obtained by the group?";
        
        String answer = "Highest: " + max + ", Lowest: " + min;
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("Highest: " + (max-5) + ", Lowest: " + min);
        options.add("Highest: " + max + ", Lowest: " + (min+5));
        options.add("Highest: " + (max+2) + ", Lowest: " + (min-2));
        
        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static Question generateMeanEnrolment() {
        int[] enrolment = new int[6];
        int total = 0;
        for (int i = 0; i < 6; i++) {
            enrolment[i] = 1500 + RANDOM.nextInt(1500);
            total += enrolment[i];
        }
        
        // Ensure mean is integer
        int remainder = total % 6;
        if (remainder != 0) {
            enrolment[5] += (6 - remainder);
            total += (6 - remainder);
        }
        int mean = total / 6;

        String questionText = "The enrolment in a school during six consecutive years was as follows:<br><br><strong><big>" + 
                Arrays.toString(enrolment).replace("[", "").replace("]", "") + "</big></strong><br><br>What is the mean enrolment of the school for this period?";
        
        String answer = String.valueOf(mean);
        List<String> options = OptionUtils.generateNumberOptions(mean, 5000);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }
}
