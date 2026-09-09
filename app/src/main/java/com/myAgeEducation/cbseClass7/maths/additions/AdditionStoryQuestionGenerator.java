package com.myAgeEducation.cbseClass7.maths.additions;

import com.myAgeEducation.cbseClass7.OptionUtil;
import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class AdditionStoryQuestionGenerator
{
    private AdditionStoryQuestionGenerator()
    {
    }

    public static Question generateQuestion()
    {
        AdditionStoryQuestionData data = AdditionStoryDataGenerator.generate();
        String correctAnswer = NumberFormatUtil.formatIndianNumber(data.answer);
        String[] options = generateOptions(data.answer);
        Question question = new Question();
        question.setQuestion(data.question);
        OptionUtils.setQuestionOptions(question, options);
        question.setAnswer(correctAnswer);
        return question;
    }

    private static String[] generateOptions(int correctAnswer)
    {
        Set<String> distractors = new LinkedHashSet<>();
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer + 1));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer - 1));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer + 10));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer - 10));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer + 2));
        distractors.add(NumberFormatUtil.formatIndianNumber(correctAnswer - 2));
        return OptionUtil.createOptions(NumberFormatUtil.formatIndianNumber(correctAnswer), distractors, 4);
    }
}