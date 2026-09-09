package com.myAgeEducation.cbseClass7.maths.multiples;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultipleQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        MultipleQuestionType[] types = MultipleQuestionType.values();
        MultipleQuestionType type = types[RANDOM.nextInt(types.length)];
        MultipleQuestionData data = generateQuestion(type);
        return convertToQuestion(data);
    }

    public static MultipleQuestionData generateQuestion(MultipleQuestionType type) {
        switch (type) {
            case LIST_MULTIPLE_TRUE_FALSE:
                return generateListMultipleTrueFalse();
            case PROPERTY_ONE_MULTIPLE:
                return generatePropertyQuestion(MultipleQuestionType.PROPERTY_ONE_MULTIPLE);
            case PROPERTY_SELF_MULTIPLE:
                return generatePropertyQuestion(MultipleQuestionType.PROPERTY_SELF_MULTIPLE);
            case PROPERTY_MULTIPLE_OF_ONE:
                return generatePropertyQuestion(MultipleQuestionType.PROPERTY_MULTIPLE_OF_ONE);
            case COMMON_MULTIPLE:
                return generateCommonMultiple();
            case FIRST_FIVE_COMMON_MULTIPLES:
                return generateFirstFiveCommonMultiples();
            case COMMON_MULTIPLE_WORD_PROBLEM:
                return generateCommonMultipleWordProblem();
            case PROPERTY_MULTIPLE_SIZE:
                return generatePropertyMultipleSizeQuestion();
            case PROPERTY_MULTIPLES_INFINITE:
                return generatePropertyMultiplesInfiniteQuestion();
            default: //for MULTIPLE_TRUE_FALSE
                return generateMultipleTrueFalse();
        }
    }

    private static MultipleQuestionData generatePropertyMultipleSizeQuestion() {
        boolean shouldBeGreater = RANDOM.nextBoolean();
        String question = "TRUE or FALSE. Every multiple of a number is " + (shouldBeGreater ? "greater than or equal to" : "less than or equal to") + " that number.";
        String answer = shouldBeGreater ? "TRUE" : "FALSE";
        String[] options = {"TRUE", "FALSE"};

        return new MultipleQuestionData(0, 0, 0, question, answer, options, MultipleQuestionType.PROPERTY_MULTIPLE_SIZE);
    }

    private static MultipleQuestionData generatePropertyMultiplesInfiniteQuestion() {
        boolean shouldBeInfinite = RANDOM.nextBoolean();
        String question = "TRUE or FALSE. The number of multiples of a given number is " + (shouldBeInfinite ? "infinite" : "finite") + ".";
        String answer = shouldBeInfinite ? "TRUE" : "FALSE";
        String[] options = {"TRUE", "FALSE"};

        return new MultipleQuestionData(0, 0, 0, question, answer, options, MultipleQuestionType.PROPERTY_MULTIPLES_INFINITE);
    }

    private static MultipleQuestionData generateMultipleTrueFalse() {
        int base = 2 + RANDOM.nextInt(11); // 2-12
        boolean isTrue = RANDOM.nextBoolean();
        int multiple;
        if (isTrue) {
            multiple = base * (2 + RANDOM.nextInt(10));
        } else {
            multiple = base * (2 + RANDOM.nextInt(10)) + 1;
        }
        
        String[] templates = MultipleQuestionTemplatesUtil.getQuestionTemplates(MultipleQuestionType.MULTIPLE_TRUE_FALSE);
        String question = String.format(templates[RANDOM.nextInt(templates.length)], multiple, base);
        String answer = isTrue ? "TRUE" : "FALSE";
        
        return new MultipleQuestionData(multiple, base, 0, question, answer, MultipleOptionUtils.generateTrueFalseOptions(), MultipleQuestionType.MULTIPLE_TRUE_FALSE);
    }

    private static MultipleQuestionData generateListMultipleTrueFalse() {
        int base = 2 + RANDOM.nextInt(8); // 2-9
        boolean isTrue = RANDOM.nextBoolean();
        List<Integer> multiples = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            multiples.add(base * i);
        }
        
        if (!isTrue) {
            // Change one to be not a multiple
            int index = RANDOM.nextInt(multiples.size());
            multiples.set(index, multiples.get(index) + 1);
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < multiples.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(multiples.get(i));
        }
        
        String[] templates = MultipleQuestionTemplatesUtil.getQuestionTemplates(MultipleQuestionType.LIST_MULTIPLE_TRUE_FALSE);
        String question = String.format(templates[RANDOM.nextInt(templates.length)], sb.toString(), base);
        String answer = isTrue ? "TRUE" : "FALSE";
        
        return new MultipleQuestionData(base, 0, 0, question, answer, MultipleOptionUtils.generateTrueFalseOptions(), MultipleQuestionType.LIST_MULTIPLE_TRUE_FALSE);
    }

    private static MultipleQuestionData generatePropertyQuestion(MultipleQuestionType type) {
        String[] templates = MultipleQuestionTemplatesUtil.getQuestionTemplates(type);
        String question = templates[RANDOM.nextInt(templates.length)];
        
        // 1 is a multiple of every number -> FALSE (1 is a factor of every number, but a multiple only of 1)
        // A number is a multiple of itself -> TRUE
        // Every number is a multiple of 1 -> TRUE
        
        String answer;
        if (type == MultipleQuestionType.PROPERTY_ONE_MULTIPLE) {
            answer = "FALSE";
        } else {
            answer = "TRUE";
        }
        
        return new MultipleQuestionData(0, 0, 0, question, answer, MultipleOptionUtils.generateTrueFalseOptions(), type);
    }

    private static MultipleQuestionData generateCommonMultiple() {
        int n1 = 2 + RANDOM.nextInt(5); // 2-6
        int n2 = 2 + RANDOM.nextInt(5); // 2-6
        while (n1 == n2) n2 = 2 + RANDOM.nextInt(5);
        
        String[] options = MultipleOptionUtils.generateCommonMultipleOptions(n1, n2);
        String answer = "";
        for (String opt : options) {
            int val = Integer.parseInt(opt);
            if (val % n1 == 0 && val % n2 == 0) {
                answer = opt;
                break;
            }
        }
        
        String[] templates = MultipleQuestionTemplatesUtil.getQuestionTemplates(MultipleQuestionType.COMMON_MULTIPLE);
        String question = String.format(templates[RANDOM.nextInt(templates.length)], n1, n2);
        
        return new MultipleQuestionData(n1, n2, 0, question, answer, options, MultipleQuestionType.COMMON_MULTIPLE);
    }

    private static MultipleQuestionData generateFirstFiveCommonMultiples() {
        // Pairs from user request
        int[][] pairs = {
            {2, 3}, {5, 8}, {2, 4}, {3, 9}, {5, 10}, 
            {9, 12}, {8, 12}, {6, 8}, {6, 9}
        };
        
        int[] pair = pairs[RANDOM.nextInt(pairs.length)];
        int n1 = pair[0];
        int n2 = pair[1];
        
        int lcm = MultipleOptionUtils.getLCM(n1, n2);
        StringBuilder answerBuilder = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            answerBuilder.append(lcm * i);
            if (i < 5) answerBuilder.append(", ");
        }
        String answer = answerBuilder.toString();
        
        String[] options = MultipleOptionUtils.generateFirstFiveCommonMultipleOptions(n1, n2);
        
        String[] templates = MultipleQuestionTemplatesUtil.getQuestionTemplates(MultipleQuestionType.FIRST_FIVE_COMMON_MULTIPLES);
        String question = String.format(templates[RANDOM.nextInt(templates.length)], n1, n2);
        
        return new MultipleQuestionData(n1, n2, 0, question, answer, options, MultipleQuestionType.FIRST_FIVE_COMMON_MULTIPLES);
    }

    private static MultipleQuestionData generateCommonMultipleWordProblem() {
        String[] templates = MultipleQuestionTemplatesUtil.getQuestionTemplates(MultipleQuestionType.COMMON_MULTIPLE_WORD_PROBLEM);
        int templateIndex = RANDOM.nextInt(templates.length);
        String question = "";
        String answer = "";
        int lcm = 0;
        String unit = "";

        if (templateIndex == 0) {
            // Animal pairs to ensure logical mapping
            String[][] characters = {
                {"Sher Khan", "tiger"},
                {"Bagheera", "panther"},
                {"Simba", "lion"},
                {"Baloo", "bear"},
                {"Mufasa", "lion"},
                {"Akela", "wolf"}
            };

            int i1 = RANDOM.nextInt(characters.length);
            int i2 = RANDOM.nextInt(characters.length);
            while (i1 == i2) i2 = RANDOM.nextInt(characters.length);

            String name1 = characters[i1][0];
            String animal1 = characters[i1][1];
            String name2 = characters[i2][0];
            String animal2 = characters[i2][1];

            int n1 = 2 + RANDOM.nextInt(4); // 2-5
            int n2 = 3 + RANDOM.nextInt(4); // 3-6
            while (n1 == n2) n2 = 3 + RANDOM.nextInt(4);
            lcm = MultipleOptionUtils.getLCM(n1, n2);
            
            question = String.format(templates[0], name1, animal1, NumberFormatUtil.formatOrdinal(n1), name2, animal2, NumberFormatUtil.formatOrdinal(n2));
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= 3; i++) {
                sb.append(lcm * i);
                if (i < 3) sb.append(", ");
            }
            answer = sb + " days";
            unit = "days";
        } else if (templateIndex == 1) {
            // Bells
            int n1 = 15 + RANDOM.nextInt(15);
            int n2 = 20 + RANDOM.nextInt(20);
            lcm = MultipleOptionUtils.getLCM(n1, n2);
            question = String.format(templates[1], n1, n2);

            int totalMinutes = 10 * 60 + lcm;
            int newHour24 = (totalMinutes / 60) % 24;
            int mins = totalMinutes % 60;
            String period = (newHour24 >= 12) ? "PM" : "AM";
            int newHour12 = newHour24 % 12;
            if (newHour12 == 0) newHour12 = 12;
            answer = String.format("%02d:%02d %s", newHour12, mins, period);
        }
        else if (templateIndex == 2) {
            // Traffic lights
            int n1 = 20 + RANDOM.nextInt(20);
            int n2 = 30 + RANDOM.nextInt(30);
            int n3 = 40 + RANDOM.nextInt(40);
            lcm = MultipleOptionUtils.getLCM(n1, MultipleOptionUtils.getLCM(n2, n3));
            question = String.format(templates[2], n1, n2, n3);

            int totalSeconds = 8 * 3600 + lcm;
            int newHour24 = (totalSeconds / 3600) % 24;
            int remainingSecs = totalSeconds % 3600;
            int mins = remainingSecs / 60;
            int secs = remainingSecs % 60;
            String period = (newHour24 >= 12) ? "PM" : "AM";
            int newHour12 = newHour24 % 12;
            if (newHour12 == 0) newHour12 = 12;
            answer = String.format("%d:%02d:%02d %s", newHour12, mins, secs, period);
        } else {
            // Friends running
            //String[] names = {"Amit", "Rahul", "Sneha", "Priya"};
            String[] names = PersonNameUtil.getDifferentNames(4);
            int n1 = 5 + RANDOM.nextInt(10);
            int n2 = 5 + RANDOM.nextInt(10);
            while (n1 == n2) n2 = 5 + RANDOM.nextInt(10);
            lcm = MultipleOptionUtils.getLCM(n1, n2);
            question = String.format(templates[3], names[0], names[1], names[0], n1, names[1], n2);
            answer = lcm + " minutes";
            unit = "minutes";
        }

        String[] options = MultipleOptionUtils.generateWordProblemOptions(answer, lcm, unit);
        return new MultipleQuestionData(0, 0, 0, question, answer, options, MultipleQuestionType.COMMON_MULTIPLE_WORD_PROBLEM);
    }

    private static Question convertToQuestion(MultipleQuestionData data) {
        Question q = new Question();
        q.setQuestion(data.question);
        q.setAnswer(data.answer);
        OptionUtils.setQuestionOptions(q, data.options);
        return q;
    }
}
