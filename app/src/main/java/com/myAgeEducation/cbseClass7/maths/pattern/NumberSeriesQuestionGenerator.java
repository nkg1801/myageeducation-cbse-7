package com.myAgeEducation.cbseClass7.maths.pattern;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NumberSeriesQuestionGenerator {
    private static final Random RANDOM = new Random();

    private static final int[] STEP_VALUES = {
            10, 20, 25, 50, 100, 111, 200, 250, 500, 900, 1000, 1050
    };

    public static Question generateQuestion() {
        int step = STEP_VALUES[RANDOM.nextInt(STEP_VALUES.length)];
        int start = 100 + RANDOM.nextInt(10000);
        int length = 3; // Number of visible items before blanks
        int blanks = 3; // Number of blanks to fill

        int[] series = new int[length + blanks];
        series[0] = start;
        for (int i = 1; i < series.length; i++) {
            series[i] = series[i - 1] + step;
        }

        StringBuilder sb = new StringBuilder("Continue the series:\n\n");
        for (int i = 0; i < length; i++) {
            sb.append(NumberFormatUtil.formatIndianNumber(series[i])).append("; ");
        }
        for (int i = 0; i < blanks; i++) {
            sb.append("____");
            if (i < blanks - 1) sb.append("; ");
        }

        String correctAnswer = formatSequence(series, length, blanks);
        String[] options = generateOptions(series, length, blanks, step);

        Question question = new Question();
        question.setQuestion(sb.toString());
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static String formatSequence(int[] series, int startIdx, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(NumberFormatUtil.formatIndianNumber(series[startIdx + i]));
            if (i < count - 1) sb.append("; ");
        }
        return sb.toString();
    }

    private static String[] generateOptions(int[] series, int startIdx, int count, int step) {
        Set<String> options = new LinkedHashSet<>();
        options.add(formatSequence(series, startIdx, count));

        while (options.size() < 4) {
            int wrongStep = step + (RANDOM.nextInt(5) - 2) * 10;
            if (wrongStep == step || wrongStep <= 0) wrongStep = step + 100;

            int[] wrongSeries = new int[series.length];
            wrongSeries[startIdx - 1] = series[startIdx - 1];
            for (int i = startIdx; i < wrongSeries.length; i++) {
                wrongSeries[i] = wrongSeries[i - 1] + wrongStep;
            }
            options.add(formatSequence(wrongSeries, startIdx, count));
        }

        List<String> list = new ArrayList<>(options);
        Collections.shuffle(list);
        return list.toArray(new String[0]);
    }
}
