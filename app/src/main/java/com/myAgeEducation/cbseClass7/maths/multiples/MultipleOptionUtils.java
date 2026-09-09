package com.myAgeEducation.cbseClass7.maths.multiples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultipleOptionUtils {
    private static final Random RANDOM = new Random();
    private static final int OPTION_COUNT = 4;

    private MultipleOptionUtils() {}

    public static String[] generateTrueFalseOptions() {
        return new String[]{"TRUE", "FALSE"};
    }

    public static String[] generateCommonMultipleOptions(int n1, int n2) {
        int lcm = getLCM(n1, n2);
        
        // Correct answer is a multiple of LCM
        int correct = lcm * (1 + RANDOM.nextInt(3));
        
        List<Integer> options = new ArrayList<>();
        options.add(correct);
        
        // Distractors: multiples of n1 but not n2, or n2 but not n1, or neither.
        List<Integer> distractors = new ArrayList<>();
        int candidate = Math.min(n1, n2);
        while (distractors.size() < 10) {
            if (candidate % n1 == 0 && candidate % n2 == 0) {
                // skip, it's a common multiple
            } else if (candidate % n1 == 0 || candidate % n2 == 0) {
                distractors.add(candidate);
            } else {
                // also add some that are neither if we need
                if (RANDOM.nextBoolean()) distractors.add(candidate);
            }
            candidate++;
            if (candidate > correct + 50) break;
        }

        // Add 3 distractors
        addRandomDistinctValues(options, distractors, OPTION_COUNT);
        
        // Ensure we have 4
        while (options.size() < OPTION_COUNT) {
            int r = RANDOM.nextInt(100) + 1;
            if (!options.contains(r) && (r % n1 != 0 || r % n2 != 0)) {
                options.add(r);
            }
        }

        Collections.shuffle(options, RANDOM);
        return toStringArray(options);
    }

    public static String[] generateFirstFiveCommonMultipleOptions(int n1, int n2) {
        int lcm = getLCM(n1, n2);
        
        StringBuilder correctList = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            correctList.append(lcm * i);
            if (i < 5) correctList.append(", ");
        }
        
        List<String> options = new ArrayList<>();
        options.add(correctList.toString());
        
        // Distractors: sequences of multiples that are not the first five common multiples
        
        // Option 2: Multiples of only one of the numbers
        StringBuilder distractor1 = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            distractor1.append(n1 * i);
            if (i < 5) distractor1.append(", ");
        }
        if (!options.contains(distractor1.toString())) options.add(distractor1.toString());

        // Option 3: Multiples of only the other number
        StringBuilder distractor2 = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            distractor2.append(n2 * i);
            if (i < 5) distractor2.append(", ");
        }
        if (!options.contains(distractor2.toString())) options.add(distractor2.toString());

        // Option 4: Common multiples but starting from 2nd one
        StringBuilder distractor3 = new StringBuilder();
        for (int i = 2; i <= 6; i++) {
            distractor3.append(lcm * i);
            if (i < 6) distractor3.append(", ");
        }
        if (!options.contains(distractor3.toString())) options.add(distractor3.toString());
        
        // Fill to 4 options if needed
        while (options.size() < 4) {
            StringBuilder distractorRandom = new StringBuilder();
            int offset = 1 + RANDOM.nextInt(10);
            for (int i = 1; i <= 5; i++) {
                distractorRandom.append((lcm + offset) * i);
                if (i < 5) distractorRandom.append(", ");
            }
            String s = distractorRandom.toString();
            if (!options.contains(s)) options.add(s);
        }

        Collections.shuffle(options, RANDOM);
        return options.toArray(new String[0]);
    }

    public static String[] generateWordProblemOptions(String correctAnswer, int lcm, String unit) {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        // Add distractors based on the type of answer
        if (correctAnswer.contains(",")) {
            // It's a list (like hunting days)
            // Distractor 1: First 3 common multiples
            StringBuilder d1 = new StringBuilder();
            for (int i = 1; i <= 3; i++) {
                d1.append(lcm * i);
                if (i < 3) d1.append(", ");
            }
            if (unit != null) d1.append(" ").append(unit);
            options.add(d1.toString());

            // Distractor 2: Random multiples
            StringBuilder d2 = new StringBuilder();
            d2.append(lcm).append(", ").append(lcm*2 + 1).append(", ").append(lcm*3);
            if (unit != null) d2.append(" ").append(unit);
            options.add(d2.toString());

            // Distractor 3: Multiples of one of the bases (approximate)
            options.add("Every " + (lcm + 2) + " days");
        } else if (correctAnswer.contains(":")) {
            // It's a time
            String[] timeParts = correctAnswer.split(" ");
            String time = timeParts[0];
            String period = timeParts.length > 1 ? timeParts[1] : "AM";

            if (time.split(":").length > 2) {
                // H:MM:SS format
                addTimeDistractor(options, "8:05:00 " + period);
                addTimeDistractor(options, "8:10:00 " + period);
                addTimeDistractor(options, "8:15:00 " + period);
            } else {
                // HH:MM format
                addTimeDistractor(options, "10:15 " + period);
                addTimeDistractor(options, "10:30 " + period);
                addTimeDistractor(options, "11:00 " + period);
            }
        } else {
            // It's a single number
            int val = Integer.parseInt(correctAnswer.split(" ")[0]);
            options.add((val + 5) + " " + unit);
            options.add((val * 2) + " " + unit);
            options.add((val / 2 + 3) + " " + unit);
        }

        while (options.size() < 4) {
            options.add("None of these");
        }

        Collections.shuffle(options, RANDOM);
        return options.toArray(new String[0]);
    }

    private static void addTimeDistractor(List<String> options, String distractor) {
        if (!options.contains(distractor)) {
            options.add(distractor);
        }
    }

    public static int getGCD(int a, int b) {
        while (b > 0) {
            a %= b;
            int temp = a;
            a = b;
            b = temp;
        }
        return a;
    }

    public static int getLCM(int a, int b) {
        if (a == 0 || b == 0) return 0;
        return Math.abs(a * b) / getGCD(a, b);
    }

    private static void addRandomDistinctValues(List<Integer> target, List<Integer> source, int totalCount) {
        List<Integer> available = new ArrayList<>(source);
        Collections.shuffle(available, RANDOM);
        for (Integer val : available) {
            if (!target.contains(val)) {
                target.add(val);
            }
            if (target.size() >= totalCount) break;
        }
    }

    private static String[] toStringArray(List<Integer> options) {
        String[] res = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            res[i] = String.valueOf(options.get(i));
        }
        return res;
    }
}
