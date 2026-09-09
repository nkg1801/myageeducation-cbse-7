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

public class NumberPatternQuestionGenerator
{
    private static final String[] QUESTION_TEXTS =
            {
                    "Which number is missing from this sequence?",
                    "What comes in the blank?",
                    "Find the missing number.",
                    "Which number should come in the blank?",
                    "Complete the pattern:",
                    "Continue the series:"
            };

    private static final Random RANDOM = new Random();

    public static Question generateQuestion()
    {
        NumberPatternQuestionData questionData = generate();
        Question question = new Question();
        question.setQuestion(questionData.questionText);
        OptionUtils.setQuestionOptions(question, questionData.options);
        question.setAnswer(questionData.correctAnswer);
        return question;
    }

    private static NumberPatternQuestionData generate()
    {
        // Generate the number pattern
        NumberPatternData data = NumberPatternGenerator.generate();

        // Create question text
        String questionText = QUESTION_TEXTS[RANDOM.nextInt(QUESTION_TEXTS.length)] + "\n\n" + data.getSequenceText();

        // Correct answer
        String correctAnswer = NumberFormatUtil.formatIndianNumber(data.getMissingNumber());

        // Create question data
        NumberPatternQuestionData questionData = new NumberPatternQuestionData(data, questionText, correctAnswer);

        // Generate four options
        questionData.options = generateOptions( questionData);
        return questionData;
    }

    private static String[] generateOptions(NumberPatternQuestionData questionData)
    {
        int correctAnswerVal = questionData.patternData.getMissingNumber();
        int step = questionData.patternData.step;
        Set<Integer> distractors = new LinkedHashSet<>();

        int[] candidates =
                {
                        correctAnswerVal - step,
                        correctAnswerVal + step,
                        correctAnswerVal - (2 * step),
                        correctAnswerVal + (2 * step),
                        correctAnswerVal - 1,
                        correctAnswerVal + 1,
                        correctAnswerVal + 10,
                        correctAnswerVal - 10,
                        correctAnswerVal + 100,
                        correctAnswerVal - 100
                };

        for (int value : candidates)
        {
            if (value > 0 && value != correctAnswerVal)
            {
                distractors.add(value);
            }

            if (distractors.size() >= 3)
            {
                break;
            }
        }

        // Safety fallback
        int extraValue = correctAnswerVal + 2;

        while (distractors.size() < 3)
        {
            if (extraValue > 0 && extraValue != correctAnswerVal)
            {
                distractors.add(extraValue);
            }

            extraValue++;
        }

        // Now there are exactly 4 options
        List<String> options = new ArrayList<>();

        options.add(questionData.correctAnswer);
        
        List<Integer> distractorList = new ArrayList<>(distractors);
        Collections.shuffle(distractorList);
        
        for (int i = 0; i < 3; i++) {
            options.add(NumberFormatUtil.formatIndianNumber(distractorList.get(i)));
        }

        Collections.shuffle(options);

        return options.toArray(new String[0]);
    }
}
