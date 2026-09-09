package com.myAgeEducation.cbseClass7.maths.exponents;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExponentsQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(12);
        switch (type) {
            case 0: return generatePowerOfBaseQuestion();
            case 1: return generateGreaterNumberQuestion();
            case 2: return generatePrimeFactorPowerQuestion();
            case 3: return generateValueOfPowerQuestion();
            case 4: return generateExponentialFormOfProductQuestion();
            case 5: return generateExponentLawMultiplicationQuestion();
            case 6: return generateExponentLawDivisionQuestion();
            case 7: return generateExponentLawPowerOfPowerQuestion();
            case 8: return generateExponentZeroQuestion();
            case 9: return generateStandardFormQuestion();
            case 10: return generateExpandedFormQuestion();
            case 11: return generateExponentTrueFalseQuestion();
            default: return generatePowerOfBaseQuestion();
        }
    }

    private static Question generatePowerOfBaseQuestion() {
        int[][] basesAndPowers = {
                {2, 3, 8}, {2, 4, 16}, {2, 5, 32}, {2, 6, 64}, {2, 7, 128}, {2, 8, 256}, {2, 9, 512}, {2, 10, 1024},
                {3, 2, 9}, {3, 3, 27}, {3, 4, 81}, {3, 5, 243}, {3, 6, 729},
                {4, 2, 16}, {4, 3, 64}, {4, 4, 256},
                {5, 2, 25}, {5, 3, 125}, {5, 4, 625}, {5, 5, 3125},
                {6, 2, 36}, {6, 3, 216},
                {7, 2, 49}, {7, 3, 343},
                {8, 2, 64}, {8, 3, 512},
                {9, 2, 81}, {9, 3, 729},
                {10, 2, 100}, {10, 3, 1000}, {10, 4, 10000}
        };

        int[] choice = basesAndPowers[RANDOM.nextInt(basesAndPowers.length)];
        int base = choice[0];
        int power = choice[1];
        int value = choice[2];

        String questionText = "Express " + value + " as a power of " + base;
        String answer = "\\(" + base + "^{" + power + "}\\)";

        List<String> options = new ArrayList<>();
        options.add(answer);
        while (options.size() < 4) {
            int p = power + (RANDOM.nextBoolean() ? 1 : -1) * (RANDOM.nextInt(3) + 1);
            if (p < 1) p = power + RANDOM.nextInt(4) + 1;
            String opt = "\\(" + base + "^{" + p + "}\\)";
            if (!options.contains(opt)) options.add(opt);
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateGreaterNumberQuestion() {
        int a, b, m, n;
        // Case 1: a^b vs b^a
        if (RANDOM.nextBoolean()) {
            a = 2 + RANDOM.nextInt(4);
            b = a + 1;
            m = b;
            n = a;
        } else {
            // Random distinct small powers
            a = 2 + RANDOM.nextInt(8);
            m = 2 + RANDOM.nextInt(3);
            b = 2 + RANDOM.nextInt(8);
            n = 2 + RANDOM.nextInt(3);
            while (a == b && m == n) {
                b = 2 + RANDOM.nextInt(8);
            }
        }

        double val1 = Math.pow(a, m);
        double val2 = Math.pow(b, n);

        String q1 = "\\(" + a + "^{" + m + "}\\)";
        String q2 = "\\(" + b + "^{" + n + "}\\)";

        String questionText = "Which one is greater: " + q1 + " or " + q2 + "?";
        String answer;
        if (val1 > val2) answer = q1;
        else if (val2 > val1) answer = q2;
        else answer = "Both are equal";

        List<String> options = new ArrayList<>();
        options.add(q1);
        options.add(q2);
        options.add("Both are equal");
        options.add("Not possible to find out");

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generatePrimeFactorPowerQuestion() {
        int[] numbers = {72, 432, 1000, 16000, 800, 225, 675, 108, 250, 450};
        int num = numbers[RANDOM.nextInt(numbers.length)];

        String questionText = "Which of the following expresses the number " + num + " as a product of powers of prime factors?";
        String answer = getPrimeFactorization(num);

        List<String> options = new ArrayList<>();
        options.add(answer);
        while (options.size() < 4) {
            int fakeNum = num + (RANDOM.nextBoolean() ? 8 : -8) * (RANDOM.nextInt(5) + 1);
            if (fakeNum <= 1) fakeNum = num + 12;
            String opt = getPrimeFactorization(fakeNum);
            if (!options.contains(opt)) options.add(opt);
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static String getPrimeFactorization(int n) {
        int temp = n;
        StringBuilder sb = new StringBuilder("\\(");
        int[] primes = {2, 3, 5, 7, 11, 13};
        boolean first = true;
        for (int p : primes) {
            int count = 0;
            while (temp > 0 && temp % p == 0) {
                count++;
                temp /= p;
            }
            if (count > 0) {
                if (!first) sb.append(" \\times ");
                sb.append(p).append("^{").append(count).append("}");
                first = false;
            }
        }
        if (temp > 1) {
            if (!first) sb.append(" \\times ");
            sb.append(temp).append("^{1}");
        }
        sb.append("\\)");
        return sb.toString();
    }

    private static Question generateValueOfPowerQuestion() {
        int base = 2 + RANDOM.nextInt(11);
        int power = 2 + RANDOM.nextInt(5);
        if (base > 5) power = 2 + RANDOM.nextInt(2);
        if (base >= 11) power = 2;

        long value = (long) Math.pow(base, power);

        String questionText = "What is the value of \\(" + base + "^{" + power + "}\\)?";
        String answer = String.valueOf(value);

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(String.valueOf(value + (RANDOM.nextBoolean() ? 10 : -10)));
        options.add(String.valueOf(base * power));
        options.add(String.valueOf(value + RANDOM.nextInt(100) + 1));

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateExponentialFormOfProductQuestion() {
        int subtype = RANDOM.nextInt(3);
        String questionText;
        String answer;
        List<String> options = new ArrayList<>();

        if (subtype == 0) {
            int n = 3 + RANDOM.nextInt(5);
            int base = 2 + RANDOM.nextInt(8);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(base);
                if (i < n - 1) sb.append(" \\times ");
            }
            questionText = "The exponential form of \\(" + sb + "\\) is:";
            answer = "\\(" + base + "^{" + n + "}\\)";
            options.add(answer);
            options.add("\\(" + n + "^{" + base + "}\\)");
            options.add("\\(" + base + " \\times " + n + "\\)");
            options.add("\\(" + base + "^{" + (n + 1) + "}\\)");
        } else if (subtype == 1) {
            int n = 3 + RANDOM.nextInt(3);
            String var = "txyzab".charAt(RANDOM.nextInt(6)) + "";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(var);
                if (i < n - 1) sb.append(" \\times ");
            }
            questionText = "The exponential form of \\(" + sb + "\\) is:";
            answer = "\\(" + var + "^{" + n + "}\\)";
            options.add(answer);
            options.add("\\(" + n + var + "\\)");
            options.add("\\(" + var + "^{" + (n - 1) + "}\\)");
            options.add("\\(" + var + "^{" + (n + 1) + "}\\)");
        } else {
            // Mixed variables
            String v1 = "a", v2 = "c", v3 = "d";
            questionText = "The exponential form of \\(" + v1 + " \\times " + v1 + " \\times " + v1 + " \\times " + v2 + " \\times " + v2 + " \\times " + v3 + " \\times " + v3 + " \\times " + v3 + "\\) is:";
            answer = "\\(" + v1 + "^{3}" + v2 + "^{2}" + v3 + "^{3}\\)";
            options.add(answer);
            options.add("\\(" + v1 + "^{3}" + v2 + "^{3}" + v3 + "^{2}\\)");
            options.add("\\(" + v1 + "^{2}" + v2 + "^{2}" + v3 + "^{3}\\)");
            options.add("\\(" + v1 + "^{3}" + v2 + "^{2}" + v3 + "^{5}\\)");
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateExponentLawMultiplicationQuestion() {
        int subtype = RANDOM.nextInt(3);
        String questionText;
        String answer;
        List<String> options = new ArrayList<>();

        if (subtype == 0) {
            int base = 2 + RANDOM.nextInt(10);
            int m = 2 + RANDOM.nextInt(5);
            int n = 2 + RANDOM.nextInt(5);
            questionText = "Choose the right number in replacement of '?': \\((" + (-base) + ")^{" + m + "} \\times (" + (-base) + ")^{" + n + "} = (" + (-base) + ")^{?}\\)";
            answer = String.valueOf(m + n);
            options.add(answer);
            options.add(String.valueOf(Math.abs(m - n)));
            options.add(String.valueOf(m * n));
            options.add("0");
        } else if (subtype == 1) {
            String var = "xyza".charAt(RANDOM.nextInt(4)) + "";
            int m = 5 + RANDOM.nextInt(10);
            int n = 2 + RANDOM.nextInt(8);
            questionText = "Choose the right number in replacement of '?': \\(" + var + "^{" + m + "} \\times " + var + "^{" + n + "} = " + var + "^{?}\\)";
            answer = String.valueOf(m + n);
            options.add(answer);
            options.add(String.valueOf(Math.abs(m - n)));
            options.add(String.valueOf(m * n));
            options.add(String.valueOf(m + n + 1));
        } else {
            String var = "dbck".charAt(RANDOM.nextInt(4)) + "";
            int res = 5 + RANDOM.nextInt(10);
            int n = 2 + RANDOM.nextInt(res - 2);
            int m = res - n;
            questionText = "Choose the right number in replacement of '?': \\(" + var + "^{?} \\times " + var + "^{" + n + "} = " + var + "^{" + res + "}\\)";
            answer = String.valueOf(m);
            options.add(answer);
            options.add(String.valueOf(res + n));
            options.add(String.valueOf(Math.abs(res - 2*n)));
            options.add(String.valueOf(res));
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateExponentLawDivisionQuestion() {
        int base = 10;
        int m = 5 + RANDOM.nextInt(10);
        int n = 2 + RANDOM.nextInt(m - 2);
        String questionText = "\\(" + base + "^{" + m + "} \\div " + base + "^{" + n + "}\\) is equal to:";
        String answer = "\\(" + base + "^{" + (m - n) + "}\\)";

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("\\(" + base + "^{" + (m + n) + "}\\)");
        options.add("\\(" + base + "^{" + (m * n) + "}\\)");
        options.add("\\(" + base + "^{" + m + "}\\)");

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateExponentLawPowerOfPowerQuestion() {
        int base = 2 + RANDOM.nextInt(5);
        int m = 2 + RANDOM.nextInt(4);
        int n = 2 + RANDOM.nextInt(4);
        String questionText = "\\([(" + (-base) + ")^{" + m + "}]^{" + n + "}\\) is equal to:";
        String answer = "\\((" + (-base) + ")^{" + (m * n) + "}\\)";

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("\\((" + (-base) + ")^{" + (m + n) + "}\\)");
        options.add("\\((" + (-base) + ")^{" + (m - n) + "}\\)");
        options.add("\\((" + (-base) + ")^{" + (m + n + 1) + "}\\)");

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateExponentZeroQuestion() {
        int subtype = RANDOM.nextInt(3);
        String questionText;
        String answer;
        List<String> options = new ArrayList<>();

        if (subtype == 0) {
            questionText = "\\((1^0 + 2^0 + 3^0)\\) is equal to:";
            answer = "3";
            options.add("0");
            options.add("1");
            options.add("3");
            options.add("6");
        } else if (subtype == 1) {
            questionText = "Which of the following is equal to 1?";
            answer = "\\(2^0 \\times 3^0 \\times 4^0\\)";
            options.add("\\(2^0 + 3^0 + 4^0\\)");
            options.add(answer);
            options.add("\\((3^0 - 2^0) \\times 4^0\\)");
            options.add("\\((3^0 - 2^0) \\times (3^0 + 2^0)\\)");
        } else {
            questionText = "TRUE or FALSE. \\(y^0 \\times y^0 = y^0 \\div y^0\\) is true for all non-zero values of y.";
            answer = "TRUE";
            options.add("TRUE");
            options.add("FALSE");
        }

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        if (options.size() == 2) {
            question.setOption1(options.get(0));
            question.setOption2(options.get(1));
        } else {
            OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        }
        return question;
    }

    private static Question generateStandardFormQuestion() {
        long num = 10000 + RANDOM.nextInt(90000);
        int zeros = RANDOM.nextInt(5);
        StringBuilder sb = new StringBuilder(String.valueOf(num));
        for (int i = 0; i < zeros; i++) sb.append("0");
        String original = sb.toString();

        String questionText = "The standard form of the number " + original + " is:";
        int n = original.length() - 1;
        String k = original.charAt(0) + "." + original.substring(1).replaceAll("0+$", "");
        if (k.endsWith(".")) k = k.substring(0, k.length() - 1);
        String answer = "\\(" + k + " \\times 10^{" + n + "}\\)";

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("\\(" + k + " \\times 10^{" + (n - 1) + "}\\)");
        options.add("\\(" + (k.replace(".", "")) + " \\times 10^{" + (n - 2) + "}\\)");
        options.add("\\(" + (original.substring(0, 2) + "." + original.substring(2).replaceAll("0+$", "")) + " \\times 10^{" + (n - 1) + "}\\)");

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateExpandedFormQuestion() {
        int num = 1000 + RANDOM.nextInt(9000);
        String s = String.valueOf(num);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i)).append(" \\times 10^{").append(s.length() - 1 - i).append("}");
            if (i < s.length() - 1) sb.append(" + ");
        }
        String answer = sb.toString();
        String questionText = "Which is the correct expanded form of " + num + "?";

        List<String> options = new ArrayList<>();
        options.add("\\(" + answer + "\\)");
        
        StringBuilder fake = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            fake.append(s.charAt(i)).append(" \\times 10^{").append(s.length() - i).append("}");
            if (i < s.length() - 1) fake.append(" + ");
        }
        options.add("\\(" + fake + "\\)");
        options.add("None of these");
        options.add("Incorrect data");

        Collections.shuffle(options);
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer("\\(" + answer + "\\)");
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateExponentTrueFalseQuestion() {
        String[][] bank = {
                {"a^m \\times a^n = a^{m+n}", "TRUE"},
                {"a^m \\div a^n = a^{m-n}", "TRUE"},
                {"(a^m)^n = a^{mn}", "TRUE"},
                {"a^m \\times b^m = (ab)^m", "TRUE"},
                {"a^m \\div b^m = (a/b)^m", "TRUE"},
                {"a^m + a^m = a^{2m}", "FALSE"},
                {"a^m \\times b^m = (ab)^{2m}", "FALSE"},
                {"One million = 10^7", "FALSE"},
                {"One million = 10^6", "TRUE"},
                {"3^4 > 4^3", "TRUE"},
                {"4^2 > 2^4", "FALSE"},
                {"(-3)^4 = -12", "FALSE"},
                {"(-3)^4 = 81", "TRUE"}
        };

        int idx = RANDOM.nextInt(bank.length);
        String formula = bank[idx][0];
        String answer = bank[idx][1];

        Question question = new Question();
        question.setQuestion("TRUE or FALSE. \\(" + formula + "\\)");
        question.setAnswer(answer);
        question.setOption1("TRUE");
        question.setOption2("FALSE");
        return question;
    }
}
