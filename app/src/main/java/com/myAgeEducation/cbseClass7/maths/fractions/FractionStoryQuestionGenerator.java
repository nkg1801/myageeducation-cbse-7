package com.myAgeEducation.cbseClass7.maths.fractions;

import com.myAgeEducation.cbseClass7.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FractionStoryQuestionGenerator
{
    private static final Random RANDOM = new Random();

    private static final String[] OBJECTS = {
            "pencils",
            "chocolates",
            "marbles",
            "toffees",
            "books",
            "balloons",
            "flowers",
            "stickers",
            "biscuits",
            "oranges",
            "apples",
            "crayons",
            "coins",
            "cupcakes",
            "candies"
    };

    private static String buildStory(StoryTemplateNew template, StoryCharacter character, FractionData fraction, int total)
    {
        String objectName = OBJECTS[RANDOM.nextInt(OBJECTS.length)];

        return template.template
                .replace("%NAME%", character.giverName)
                .replace("%RECIPIENT%", character.recipientName)
                .replace("%SUBJECT%", character.subjectPronoun)
                .replace("%SUBJECT_SMALL%", character.subjectPronoun.toLowerCase())
                .replace("%POSSESSIVE%", character.possessivePronoun.toLowerCase())
                .replace("%TOTAL%",String.valueOf(total))
                .replace("%OBJECT%", objectName)
                .replace("%OBJECT_PRONOUN%", character.objectPronoun.toLowerCase())
                .replace("%FRACTION%","\\(\\frac{" + fraction.numerator + "}{" + fraction.denominator + "}\\)");
    }

    public static Question generateRemainingQuestion()
    {
        StoryTemplateNew template1 = StoryTemplates.FRACTION_TEMPLATES_NEW[RANDOM.nextInt(StoryTemplates.FRACTION_TEMPLATES_NEW.length)];

        StoryCharacter character = StoryCharacterGenerator.randomCharacter();

        FractionData fraction = getStoryFraction();

        int total = fraction.denominator * (2 + RANDOM.nextInt(5));     // 2× to 6×

        int given = total * fraction.numerator / fraction.denominator;

        int remaining = total - given;

        String questionText = buildStory(template1,character,fraction,total);

        List<String> options = generateOptions(remaining);
        Question question = new Question();
        question.setQuestion(questionText);
        
        question.setAnswer(String.valueOf(remaining));
        com.myAgeEducation.cbseClass7.utils.OptionUtils.setQuestionOptions(question, options);
        
        return question;
    }

    private static FractionData getStoryFraction()
    {
        FractionData fraction;

        do
        {
            fraction = FractionImageGenerator.randomFraction();
        } while (fraction.numerator == fraction.denominator);

        return fraction;
    }

    private static List<String> generateOptions(int answer)
    {
        java.util.Set<String> values = new java.util.LinkedHashSet<>();
        values.add(String.valueOf(answer));

        if (answer > 1) {
            values.add(String.valueOf(answer - 1));
        }

        values.add(String.valueOf(answer + 1));
        values.add(String.valueOf(answer + 2));
        
        while (values.size() < 4) {
            values.add(String.valueOf(answer + 3 + values.size()));
        }
        
        List<String> list = new ArrayList<>(values);
        Collections.shuffle(list);
        return list;
    }
}