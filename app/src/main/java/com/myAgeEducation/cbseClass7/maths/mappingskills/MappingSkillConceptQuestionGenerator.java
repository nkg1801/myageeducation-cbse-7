package com.myAgeEducation.cbseClass7.maths.mappingskills;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.ConceptQuestion;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MappingSkillConceptQuestionGenerator
{
    private static final Random RANDOM = new Random();

    public static Question generateQuestion()
    {
        return generateFixedQuestion();
    }

    public static ArrayList<Question> generateAllQuestions()
    {
        ArrayList<Question> questions = new ArrayList<>();

        List<ConceptQuestion> list = Arrays.asList(FIXED_QUESTIONS);
        Collections.shuffle(list);

        for (ConceptQuestion data : list) {
            List<String> options = new ArrayList<>();
            options.add(data.correctAnswer);
            Collections.addAll(options, data.wrongAnswers);
            Collections.shuffle(options);
            Question question = createQuestion(data.question, data.correctAnswer, options);
            questions.add(question);
        }

        return questions;
    }

    private static final ConceptQuestion[] FIXED_QUESTIONS =
            {
                    //1
                    new ConceptQuestion(
                            "If the direction is between the north and east, it is called _____ direction",
                            "north-east",
                            "north", "east", "south"),

                    //2
                    new ConceptQuestion(
                            "If the direction is between the north and west, it is called _____ direction",
                            "north-west",
                            "north-east", "east", "south"),

                    //3
                    new ConceptQuestion(
                            "If the direction is between the south and west, it is called _____ direction",
                            "south-west",
                            "north-east", "east", "south"),

                    //4
                    new ConceptQuestion(
                            "If the direction is between the south and east, it is called _____ direction",
                            "south-east",
                            "north-east", "east", "south"),

                    // 5
                    new ConceptQuestion(
                            PersonNameUtil.getOneName() + " is facing the rising Sun. That direction is ___________.",
                            "East",
                            "West", "North", "South"),

                    // 6
                    new ConceptQuestion(
                             PersonNameUtil.getMaleName() + " is facing the rising Sun. His left hand is pointing in the _______ direction.",
                            "North",
                            "South", "East", "West"),

                    // 7
                    new ConceptQuestion(
                            PersonNameUtil.getFemaleName() + " is facing the rising Sun. Her right hand is pointing in the ____________ direction.",
                            "South",
                            "North", "East", "West"),

                    // 8
                    new ConceptQuestion(
                            PersonNameUtil.getMaleName() + " is facing the rising Sun. His back is towards the _______.",
                            "West",
                            "East", "North", "South"),

                    // 9
                    new ConceptQuestion(
                            "The top of a map usually points towards the _______ direction.",
                            "North",
                            "South", "East", "West"),

                    // 10
                    new ConceptQuestion(
                            "A ________ is used to explain the symbols and colors used on a map.",
                            "Legend or Key",
                            "Scale", "Compass", "Title"),

                    // 11
                    new ConceptQuestion(
                            "If 1 cm on a map represents 10 km on the ground, then 5 cm represents _______ km.",
                            "50",
                            "10", "5", "100"),

                    // 12
                    new ConceptQuestion(
                            "A book of maps is called an _______.",
                            "Atlas",
                            "Dictionary", "Encyclopedia", "Journal"),

                    // 13
                    new ConceptQuestion(
                            "If you are facing North, the _______ direction is to your right.",
                            "East",
                            "West", "South", "North-West"),

                    // 14
                    new ConceptQuestion(
                            "In the morning, the shadow of a tree will fall towards the _______.",
                            "West",
                            "East", "North", "South"),

                    // 15
                    new ConceptQuestion(
                            "In the evening, the sun is in the West, so the shadow of a building will fall towards the _______.",
                            "East",
                            "West", "North", "South"),

                    // 16
                    new ConceptQuestion(
                            "The direction directly opposite to North-West is _______.",
                            "South-East",
                            "South-West", "North-East", "North"),

                    // 17
                    new ConceptQuestion(
                            "A _______ is a instrument used to find directions.",
                            "Compass",
                            "Ruler", "Thermometer", "Protractor"),

                    // 18
                    new ConceptQuestion(
                            "If 2 cm on a map represents 100 meters, then 1 cm represents _______ meters.",
                            "50",
                            "100", "200", "25")
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