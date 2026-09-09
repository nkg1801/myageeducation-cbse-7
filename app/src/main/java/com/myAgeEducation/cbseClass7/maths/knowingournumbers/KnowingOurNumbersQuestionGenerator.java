package com.myAgeEducation.cbseClass7.maths.knowingournumbers;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class KnowingOurNumbersQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(6);
        switch (type) {
            case 0:
                return generateSmallestGreatestWithDigits(false);
            case 1:
                return generateExtremeDigitNumber();
            case 2:
                return generateSmallestGreatestWithRepetition();
            /*case 3:
                return generateRomanNumeral();*/
            case 3:
                return generateUnitConversion();
            case 4:
                return generateSuccessorOfGreatest();
            case 5:
                return generateSmallestGreatestWithDigits(true);
            default:
                return generateUnitConversion();
        }
    }

    private static Question generateSmallestGreatestWithDigits(boolean greatest) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 9; i++) list.add(i);
        Collections.shuffle(list);
        int[] digits = new int[4];
        for (int i = 0; i < 4; i++) digits[i] = list.get(i);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(digits[i]);
            if (i < 3) sb.append(", ");
        }

        String questionText = "Use digits " + sb + " without repetition and make the " + (greatest ? "greatest" : "smallest") + " 4-digit number";

        Arrays.sort(digits);
        String answer;
        if (greatest) {
            StringBuilder ans = new StringBuilder();
            for (int i = 3; i >= 0; i--) ans.append(digits[i]);
            answer = ans.toString();
        } else {
            if (digits[0] == 0) {
                int tempIndex = 1;
                while(tempIndex < 4 && digits[tempIndex] == 0) tempIndex++;
                if(tempIndex < 4) {
                    int temp = digits[0];
                    digits[0] = digits[tempIndex];
                    digits[tempIndex] = temp;
                }
            }
            StringBuilder ans = new StringBuilder();
            for (int i = 0; i < 4; i++) ans.append(digits[i]);
            answer = ans.toString();
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);

        Set<String> options = new LinkedHashSet<>();
        options.add(answer);
        while (options.size() < 4) {
            Collections.shuffle(list);
            if (list.get(0) == 0) continue;
            StringBuilder opt = new StringBuilder();
            for (int i = 0; i < 4; i++) opt.append(list.get(i));
            options.add(opt.toString());
        }
        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);
        OptionUtils.setQuestionOptions(question, optionList.toArray(new String[0]));
        return question;
    }

    private static Question generateExtremeDigitNumber() {
        int digits = 4 + RANDOM.nextInt(3); // 4 to 6 digits
        boolean greatest = RANDOM.nextBoolean();
        String questionText = "Which is the " + (greatest ? "greatest" : "smallest") + " " + digits + "-digit number?";

        String answer;
        if (greatest) {
            char[] arr = new char[digits];
            Arrays.fill(arr, '9');
            answer = new String(arr);
        } else {
            char[] arr = new char[digits];
            Arrays.fill(arr, '0');
            arr[0] = '1';
            answer = new String(arr);
        }

        String formattedAnswer = NumberFormatUtil.formatIndianNumber(Integer.parseInt(answer));
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(formattedAnswer);

        Set<String> options = new LinkedHashSet<>();
        options.add(formattedAnswer);
        while (options.size() < 4) {
            int d = 4 + RANDOM.nextInt(3);
            boolean g = RANDOM.nextBoolean();
            String opt;
            if (g) {
                char[] arr = new char[d]; Arrays.fill(arr, '9'); opt = new String(arr);
            } else {
                char[] arr = new char[d]; Arrays.fill(arr, '0'); arr[0] = '1'; opt = new String(arr);
            }
            options.add(NumberFormatUtil.formatIndianNumber(Integer.parseInt(opt)));
        }
        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);
        OptionUtils.setQuestionOptions(question, optionList.toArray(new String[0]));
        return question;
    }

    private static Question generateSmallestGreatestWithRepetition() {
        boolean greatest = RANDOM.nextBoolean();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 9; i++) list.add(i);
        Collections.shuffle(list);
        int[] digits = new int[3];
        for (int i = 0; i < 3; i++) digits[i] = list.get(i);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(digits[i]);
            if (i < 2) sb.append(", ");
        }

        String questionText = "Make the " + (greatest ? "greatest" : "smallest") + " 4-digit number with the digits " + sb + " by using any one digit twice.";

        Arrays.sort(digits);
        String answer;
        if (greatest) {
            StringBuilder ans = new StringBuilder();
            ans.append(digits[2]).append(digits[2]).append(digits[1]).append(digits[0]);
            answer = ans.toString();
        } else {
            if (digits[0] == 0) {
                StringBuilder ans = new StringBuilder();
                ans.append(digits[1]).append(digits[0]).append(digits[0]).append(digits[2]);
                answer = ans.toString();
            } else {
                StringBuilder ans = new StringBuilder();
                ans.append(digits[0]).append(digits[0]).append(digits[1]).append(digits[2]);
                answer = ans.toString();
            }
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);

        Set<String> options = new LinkedHashSet<>();
        options.add(answer);
        while (options.size() < 4) {
            int val = 1000 + RANDOM.nextInt(9000);
            options.add(String.valueOf(val));
        }
        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);
        OptionUtils.setQuestionOptions(question, optionList.toArray(new String[0]));
        return question;
    }

    /*private static Question generateRomanNumeral() {
        int number = 10 + RANDOM.nextInt(90);
        String questionText = "What is the Roman numeral for the number " + number + "?";
        String answer = toRoman(number);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);

        Set<String> options = new LinkedHashSet<>();
        options.add(answer);
        while (options.size() < 4) {
            options.add(toRoman(10 + RANDOM.nextInt(90)));
        }
        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);
        OptionUtils.setQuestionOptions(question, optionList.toArray(new String[0]));
        return question;
    }

    private static String toRoman(int number) {
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                sb.append(roman[i]);
            }
        }
        return sb.toString();
    }*/

    private static Question generateUnitConversion() {
        String[][] conversions = {
                {"1 million", "hundred thousand", "10"},
                {"1 lakh", "ten thousand", "10"},
                {"1 crore", "ten lakh", "10"},
                {"1 crore", "million", "10"},
                {"1 million", "lakh", "10"},
                {"1 billion", "million", "1000"}
        };
        int idx = RANDOM.nextInt(conversions.length);
        String[] conv = conversions[idx];

        String questionText = conv[0] + " = _______ " + conv[1];
        String answer = conv[2];

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);

        List<String> options = new ArrayList<>(Arrays.asList("10", "100", "1000", "10000"));
        if (!options.contains(answer)) {
            options.set(RANDOM.nextInt(4), answer);
        }
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateSuccessorOfGreatest() {
        String[] digitWords = {"single", "two", "three", "four", "five"};
        String[] nextDigitWords = {"two-digit", "three-digit", "four-digit", "five-digit", "six-digit"};
        int idx = RANDOM.nextInt(digitWords.length);

        String questionText = "Greatest " + digitWords[idx] + " digit number + 1 = smallest _____ number";
        String answer = nextDigitWords[idx];

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);

        List<String> options = new ArrayList<>(Arrays.asList(nextDigitWords));
        Collections.shuffle(options);
        options = options.subList(0, 4);
        if (!options.contains(answer)) {
            options.set(RANDOM.nextInt(4), answer);
        }
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }
}
