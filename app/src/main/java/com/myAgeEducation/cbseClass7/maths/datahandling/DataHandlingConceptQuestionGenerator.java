package com.myAgeEducation.cbseClass7.maths.datahandling;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.ConceptQuestion;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataHandlingConceptQuestionGenerator
{
    private static final Random RANDOM = new Random();

    public static Question generateQuestion()
    {
        return generateFixedQuestion();
    }

    private static final ConceptQuestion[] FIXED_QUESTIONS =
            {
                    new ConceptQuestion(
                            "Every bar graph must have a _______ explaining the information given in the graph",
                            "title",
                            "scales"),

                    new ConceptQuestion(
                            "Every bar graph must have horizontal and vertical _______",
                            "scales",
                            "title"),

                    new ConceptQuestion(
                            "A _______ graph uses line segments to show how data changes over a specific period of time.",
                            "line",
                            "circle",
                            "horizontal bar",
                            "vertical bar"),
            };

    private static Question generateFixedQuestion()
    {
        ConceptQuestion data = FIXED_QUESTIONS[RANDOM.nextInt(FIXED_QUESTIONS.length)];
        List<String> options = new ArrayList<>();
        options.add(data.correctAnswer);
        Collections.addAll(options, data.wrongAnswers);
        Collections.shuffle(options);
        return createQuestion(data.question, data.correctAnswer, options);
    }

    private static Question createQuestion(String questionText, String correctAnswer, List<String> options)
    {
        Question question = new Question();
        question.setQuestion(questionText);
        OptionUtils.setQuestionOptions(question, options);
        question.setAnswer(correctAnswer);
        return question;
    }
}