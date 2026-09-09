package com.myAgeEducation.cbseClass7.maths.LineAndAngle;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LineAndAngleArithmeticQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(6);
        switch (type) {
            case 0: return generateDefinitionQuestion();
            case 1: return generateTrueFalsePropertyQuestion();
            case 2: return generateFindSupplementaryQuestion();
            case 3: return generateFindComplementaryQuestion();
            case 4: return generatePossibilityQuestion();
            case 5: return generateLinearPairQuestion();
            default: return generateDefinitionQuestion();
        }
    }

    private static Question generateDefinitionQuestion() {
        boolean comp = RANDOM.nextBoolean();
        String questionText = String.format("When the sum of the measures of two angles is %d°, the angles are called ________ angles.", comp ? 90 : 180);
        String answer = comp ? "complementary" : "supplementary";
        
        Question q = new Question();
        q.setQuestion(questionText);
        q.setAnswer(answer);
        OptionUtils.setQuestionOptions(q, new String[]{"complementary", "supplementary"});
        return q;
    }

    private static Question generateTrueFalsePropertyQuestion() {
        String[][] bank = {
                {"Two right angles complement each other.", "FALSE"},
                {"Two obtuse angles complement each other.", "FALSE"},
                {"Two acute angles can never complement each other.", "FALSE"},
                {"Two acute angles always complement each other.", "FALSE"},
                {"Two right angles supplement each other.", "TRUE"},
                {"Two acute angles can never supplement each other.", "TRUE"},
                {"Two obtuse angles supplement each other.", "FALSE"}
        };
        int idx = RANDOM.nextInt(bank.length);
        
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. " + bank[idx][0]);
        q.setAnswer(bank[idx][1]);
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateFindSupplementaryQuestion() {
        int angle = 10 + RANDOM.nextInt(160);
        int supp = 180 - angle;
        
        String questionText = String.format("What will be the supplementary angle for a %d degree angle?", angle);
        String answer = supp + "°";
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(angle + "°");
        options.add((90 - angle > 0 ? (90 - angle) : angle + 10) + "°");
        options.add((angle + supp + 80) + "°");
        
        Collections.shuffle(options);
        Question q = new Question();
        q.setQuestion(questionText);
        q.setAnswer(answer);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateFindComplementaryQuestion() {
        int angle = 10 + RANDOM.nextInt(70);
        int comp = 90 - angle;
        
        String questionText = String.format("What will be the complementary angle for a %d degree angle?", angle);
        String answer = comp + "°";
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(angle + "°");
        options.add((180 - angle) + "°");
        options.add((angle + comp + 170) + "°");
        
        Collections.shuffle(options);
        Question q = new Question();
        q.setQuestion(questionText);
        q.setAnswer(answer);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generatePossibilityQuestion() {
        String[][] bank = {
                {"Can two obtuse angles be adjacent angles?", "Yes"},
                {"Can two adjacent angles be supplementary?", "Yes"},
                {"Can two adjacent angles be complementary?", "Yes"},
                {"Can an acute angle be adjacent to an obtuse angle?", "Yes"}
        };
        int idx = RANDOM.nextInt(bank.length);
        
        Question q = new Question();
        q.setQuestion(bank[idx][0]);
        q.setAnswer(bank[idx][1]);
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateLinearPairQuestion() {
        String[][] bank = {
                {"Can two acute angles form a linear pair?", "No"},
                {"Can two obtuse angles form a linear pair?", "No"},
                {"Can two right angles form a linear pair?", "Yes"}
        };
        int idx = RANDOM.nextInt(bank.length);
        
        Question q = new Question();
        q.setQuestion(bank[idx][0]);
        q.setAnswer(bank[idx][1]);
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }
}
