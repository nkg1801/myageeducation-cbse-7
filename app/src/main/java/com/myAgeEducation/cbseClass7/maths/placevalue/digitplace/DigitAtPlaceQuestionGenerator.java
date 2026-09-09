package com.myAgeEducation.cbseClass7.maths.placevalue.digitplace;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DigitAtPlaceQuestionGenerator {
    private static final Random RANDOM = new Random();
    private static final int MIN_VALUE = 10000;
    private static final int MAX_VALUE = 999999999;

    private static final String[] PLACE_NAMES = {
            "ones", "tens", "hundreds", "thousands", "ten thousands", "lakhs", "ten lakhs", "crore", "ten crore"
    };

    public static Question generateQuestion() {
        int number = MIN_VALUE + RANDOM.nextInt(MAX_VALUE - MIN_VALUE + 1);
        String numStr = String.valueOf(number);
        int maxPosition = numStr.length() - 1;
        int position = RANDOM.nextInt(maxPosition + 1);
        
        int digit = getDigitAtPosition(number, position);

        String placeName = PLACE_NAMES[position];

        String questionText = NumberFormatUtil.formatIndianNumber(number) + " has ____ " + placeName;
        String correctAnswer = String.valueOf(digit);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);

        Set<String> options = new LinkedHashSet<>();
        options.add(correctAnswer);
        
        // Add other digits from the same number as distractors first
        for (int i = 0; i < numStr.length(); i++) {
            String d = String.valueOf(numStr.charAt(i));
            if (options.size() < 4) {
                options.add(d);
            }
        }

        // Fill remaining with random digits
        while (options.size() < 4) {
            options.add(String.valueOf(RANDOM.nextInt(10)));
        }

        List<String> optionList = new ArrayList<>(options);
        Collections.shuffle(optionList);
        OptionUtils.setQuestionOptions(question, optionList.toArray(new String[0]));

        return question;
    }

    private static int getDigitAtPosition(int number, int position) {
        long divisor = 1;
        for (int i = 0; i < position; i++) {
            divisor *= 10;
        }
        return (int) ((number / divisor) % 10);
    }
}
