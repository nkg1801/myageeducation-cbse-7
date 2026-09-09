package com.myAgeEducation.cbseClass7.maths.placevalue.rounding;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoundingQuestionGenerator
{
    private RoundingQuestionGenerator()
    {
        // Prevent object creation
    }

    public static Question generateQuestion()
    {
        RoundingQuestionData questionData = generate(new Random(), 4);

        Question question = new Question();
        question.setQuestion(questionData.getQuestionText());
        question.setAnswer(questionData.getAnswer());
        List<String> options = questionData.getOptions();
        List<String> optionsList = new ArrayList<>();

        optionsList.addAll(options);

        OptionUtils.setQuestionOptions(question, optionsList);
        return  question;
    }

    public static RoundingQuestionData generate(Random random, int optionCount)
    {
        if (optionCount < 2 || optionCount > 4)
        {
            throw new IllegalArgumentException("Option count must be between 2 and 4");
        }

        /*
         * Generate the ONE correct answer.
         */
        int validNumber = RoundingUtils.generateValidNumber(random);

        /*
         * Create the options.
         */
        List<String> options = new ArrayList<>();
        options.add(validNumber + "");

        /*
         * Generate all remaining options as invalid numbers.
         */
        while (options.size() < optionCount)
        {
            int invalidNumber = RoundingUtils.generateInvalidNumber(random);
            String invalidStr = String.valueOf(invalidNumber);
            if (!options.contains(invalidStr))
            {
                options.add(invalidStr);
            }
        }

        Collections.shuffle(options, random);

        final Random RANDOM = new Random();
        String firstName, secondName;
        if(RANDOM.nextBoolean()) {
            firstName = PersonNameUtil.getMaleName();
            secondName = PersonNameUtil.getFemaleName();
        } else {
            firstName = PersonNameUtil.getFemaleName();
            secondName = PersonNameUtil.getMaleName();
        }

        String questionText =
                firstName + " rounded off a number to the nearest "
                        + "hundred. " + secondName + " rounded off the same "
                        + "number to the nearest thousand. "
                        + "Both got the same result. "
                        + "Choose the number they "
                        + "might have used.";

        return new RoundingQuestionData(questionText, options, validNumber + "");
    }
}
