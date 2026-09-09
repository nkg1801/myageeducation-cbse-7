package com.myAgeEducation.cbseClass7.maths.pattern;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MonthPatternQuestionGenerator
{
    private static final Random RANDOM =
            new Random();

    private static final String[] MONTHS =
            {
                    "January",
                    "February",
                    "March",
                    "April",
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "December"
            };


    public static Question generateQuestion()
    {
        MonthPatternQuestionData questionData = generate();
        Question question = new Question();
        question.setQuestion(questionData.questionText);
        OptionUtils.setQuestionOptions(question, questionData.options);
        question.setAnswer(questionData.correctAnswer);
        return question;
    }

    public static MonthPatternQuestionData generate()
    {
        MonthPatternData data =
                MonthPatternGenerator.generate();

        String questionText =
                "Fill in the blank: "
                        + data.getSequenceText();

        String correctAnswer =
                data.getMissingValue();

        MonthPatternQuestionData questionData =
                new MonthPatternQuestionData(
                        data,
                        questionText,
                        correctAnswer);

        questionData.options =
                generateOptions(
                        correctAnswer);

        return questionData;
    }

    private static String[] generateOptions(
            String correctAnswer)
    {
        List<String> options =
                new ArrayList<>();

        // Correct answer is always included
        options.add(correctAnswer);

        int correctIndex =
                getMonthIndex(correctAnswer);

        // Nearby months make better distractors
        int[] offsets =
                {
                        -1,
                        1,
                        -2,
                        2,
                        -3,
                        3
                };

        for (int offset : offsets)
        {
            int index =
                    correctIndex + offset;

            if (index >= 0
                    && index < MONTHS.length)
            {
                String month =
                        MONTHS[index];

                if (!options.contains(month))
                {
                    options.add(month);
                }
            }

            if (options.size() == 4)
            {
                break;
            }
        }

        // Safety fallback
        if (options.size() < 4)
        {
            List<String> candidates =
                    new ArrayList<>();

            Collections.addAll(
                    candidates,
                    MONTHS);

            Collections.shuffle(
                    candidates);

            for (String month : candidates)
            {
                if (!options.contains(month))
                {
                    options.add(month);
                }

                if (options.size() == 4)
                {
                    break;
                }
            }
        }

        Collections.shuffle(options);

        return options.toArray(
                new String[0]);
    }

    private static int getMonthIndex(String month)
    {
        for (int i = 0; i < MONTHS.length; i++)
        {
            if (MONTHS[i].equals(month))
            {
                return i;
            }
        }

        throw new IllegalArgumentException(
                "Unknown month: " + month);
    }
}