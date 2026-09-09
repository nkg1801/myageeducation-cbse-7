package com.myAgeEducation.cbseClass7.maths.pattern;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NumberOrderingQuestionGenerator
{
    private NumberOrderingQuestionGenerator()
    {
        // Prevent object creation
    }

    public static Question generateQuestion()
    {
        NumberOrderingQuestionData data = NumberOrderingDataGenerator.generate();
        String questionText = buildQuestionText(data);
        int correctValue = getCorrectAnswer(data);
        String correctAnswer = String.valueOf(correctValue);
        String[] options = generateOptions(data);
        Question question = new Question();
        question.setQuestion(questionText);
        OptionUtils.setQuestionOptions(question, options);
        question.setAnswer(correctAnswer);
        return question;
    }

    private static int getCorrectAnswer(NumberOrderingQuestionData data)
    {
        int[] sortedNumbers = data.numbers.clone();

        Arrays.sort(sortedNumbers);

        if (data.type == NumberOrderingQuestionType.SMALLEST_TO_LARGEST)
        {
            return sortedNumbers[data.position - 1];
        }
        else
        {
            return sortedNumbers[sortedNumbers.length - data.position];
        }
    }

    private static String buildQuestionText(NumberOrderingQuestionData data)
    {
        String orderText;
        if (data.type == NumberOrderingQuestionType.LARGEST_TO_SMALLEST)
        {
            orderText = "largest to smallest";
        }
        else
        {
            orderText = "smallest to largest";
        }

        return "If you arrange these numbers from "
                + orderText
                + ", which number will come "
                + getPositionText(data.position)
                + "? "
                + formatNumbers(data.numbers);
    }

    private static String getPositionText(int position)
    {
        switch (position)
        {
            case 2:
                return "second";

            case 3:
                return "third";

            case 4:
                return "fourth";

            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    private static String formatNumbers(int[] numbers)
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < numbers.length; i++)
        {
            if (i > 0)
            {
                builder.append(", ");
            }

            builder.append(numbers[i]);
        }
        return builder.toString();
    }
    private static String[] generateOptions(NumberOrderingQuestionData data)
    {
        List<String> options = new ArrayList<>();
        for (int number : data.numbers)
        {
            options.add(String.valueOf(number));
        }
        Collections.shuffle(options);
        return options.toArray(new String[0]);
    }

}
