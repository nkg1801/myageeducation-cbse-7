package com.myAgeEducation.cbseClass7.maths.pattern;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.ImageCodeType;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PatternSequenceQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        NumberPatternData data = NumberPatternGenerator.generate();
        
        // Ensure missing index is toward the end for "continuing" the pattern
        int length = data.numbers.length;
        int missingIndex = length - 1 - RANDOM.nextInt(Math.min(3, length));
        
        // Update missing index in data
        data = new NumberPatternData(data.patternType, data.startNumber, data.step, data.numbers, missingIndex);

        Question question = new Question();
        question.setQuestion("Observe the pattern and find the missing number in the sequence:");
        
        String correctAnswer = NumberFormatUtil.formatIndianNumber(data.getMissingNumber());
        question.setAnswer(correctAnswer);

        // Generate options
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        
        Set<Integer> distractors = new HashSet<>();
        int step = data.step;
        int val = data.getMissingNumber();
        
        int[] candidates = {val + step, val - step, val + 1, val - 1, val + 10, val - 10, val + 100, val - 100};
        for (int c : candidates) {
            if (c > 0 && c != val) distractors.add(c);
            if (distractors.size() >= 3) break;
        }
        
        while (distractors.size() < 3) {
            int extra = val + RANDOM.nextInt(1000) - 500;
            if (extra > 0 && extra != val) distractors.add(extra);
        }
        
        for (int d : distractors) {
            options.add(NumberFormatUtil.formatIndianNumber(d));
        }
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);

        // Create image code
        StringBuilder sb = new StringBuilder(ImageCodeType.PATTERN_SEQUENCE);
        sb.append("_").append(length);
        sb.append("_").append(missingIndex);
        for (int n : data.numbers) {
            sb.append("_").append(n);
        }
        question.setImage(sb.toString());

        return question;
    }
}
