package com.myAgeEducation.cbseClass7.maths.pattern;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeekdayPatternQuestionGenerator
{
    private static final String[] WEEKDAYS =
            {
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday",
                    "Sunday"
            };

    public static Question generateQuestion()
    {
        WeekdayPatternQuestionData questionData = generate();
        Question question = new Question();
        question.setQuestion(questionData.questionText);
        OptionUtils.setQuestionOptions(question, questionData.options);
        question.setAnswer(questionData.correctAnswer);
        return question;
    }

    public static WeekdayPatternQuestionData generate()
    {
        WeekdayPatternData data = WeekdayPatternGenerator.generate();
        String questionText = "Fill in the blank: " + data.getSequenceText();
        String correctAnswer = data.getMissingValue();
        WeekdayPatternQuestionData questionData = new WeekdayPatternQuestionData(data, questionText, correctAnswer);
        questionData.options = generateOptions(correctAnswer);
        return questionData;
    }

    private static String[] generateOptions(String correctAnswer)
    {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        int correctIndex = getWeekdayIndex(correctAnswer);

        // Nearby weekdays as distractors
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
            // Wrap around the week
            int index = (correctIndex + offset + WEEKDAYS.length) % WEEKDAYS.length;
            String weekday = WEEKDAYS[index];

            if (!options.contains(weekday))
            {
                options.add(weekday);
            }

            if (options.size() == 4)
            {
                break;
            }
        }

        Collections.shuffle(options);
        return options.toArray(new String[0]);
    }

    private static int getWeekdayIndex(String weekday)
    {
        for (int i = 0; i < WEEKDAYS.length; i++)
        {
            if (WEEKDAYS[i].equals(weekday))
            {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown weekday: " + weekday);
    }
}