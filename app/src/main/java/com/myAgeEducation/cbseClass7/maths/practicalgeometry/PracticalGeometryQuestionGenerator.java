package com.myAgeEducation.cbseClass7.maths.practicalgeometry;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PracticalGeometryQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(8);
        switch (type) {
            case 0: return generateFramingQuestion();
            case 1: return generateTriangleInequalityQuestion();
            case 2: return generateAngleSumPropertyQuestion();
            case 3: return generateSSSConstructionQuestion();
            case 4: return generateSASConstructionQuestion();
            case 5: return generateASAConstructionQuestion();
            case 6: return generateRHSConstructionQuestion();
            case 7: return generateParallelLineConstructionQuestion();
            default: return generateFramingQuestion();
        }
    }

    private static Question generateFramingQuestion() {
        String name1 = PersonNameUtil.getMaleName();
        String name2 = PersonNameUtil.getFemaleName();

        int l1 = 40 + RANDOM.nextInt(30);
        int b1 = 20 + RANDOM.nextInt(20);
        int p1 = 2 * (l1 + b1);

        int l2 = 30 + RANDOM.nextInt(30);
        int b2 = 30 + RANDOM.nextInt(20);
        int p2 = 2 * (l2 + b2);

        while (p1 == p2) {
            l2 = 30 + RANDOM.nextInt(30);
            b2 = 30 + RANDOM.nextInt(20);
            p2 = 2 * (l2 + b2);
        }

        int costPerCm = 2 + RANDOM.nextInt(4);

        String questionText = String.format("%s and %s made pictures. %s made his picture on a rectangular sheet of length %d cm and breadth %d cm while %s made hers on a rectangular sheet of length %d cm and breadth %d cm. Both these pictures have to be separately framed. Who has to pay more for framing, if the cost of framing is Rs %d.00 per cm?",
                name1, name2, name1, l1, b1, name2, l2, b2, costPerCm);

        String answer = (p1 > p2) ? name1 : name2;
        String option1 = name1;
        String option2 = name2;

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setOption1(option1);
        question.setOption2(option2);
        return question;
    }

    private static Question generateTriangleInequalityQuestion() {
        int a, b, c;
        boolean possible = RANDOM.nextBoolean();

        if (possible) {
            a = 5 + RANDOM.nextInt(10);
            b = 5 + RANDOM.nextInt(10);
            c = Math.abs(a - b) + 1 + RANDOM.nextInt(Math.min(a, b));
        } else {
            a = 5 + RANDOM.nextInt(5);
            b = 5 + RANDOM.nextInt(5);
            c = a + b + 1 + RANDOM.nextInt(5);
        }

        String questionText = String.format("Is it possible to construct a triangle with sides %d cm, %d cm and %d cm?", a, b, c);
        String answer = possible ? "Yes" : "No";

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setOption1("Yes");
        question.setOption2("No");
        return question;
    }

    private static Question generateAngleSumPropertyQuestion() {
        int a1 = 30 + RANDOM.nextInt(100);
        int a2 = 30 + RANDOM.nextInt(100);
        int a3;
        boolean possible = RANDOM.nextBoolean();

        if (possible) {
            a3 = 180 - a1 - a2;
            if (a3 <= 0) {
                return generateAngleSumPropertyQuestion(); // Retry
            }
        } else {
            a3 = 180 - a1 - a2 + (RANDOM.nextBoolean() ? 10 : -10);
            if (a3 <= 0) a3 = 40;
        }

        String questionText = String.format("Can a triangle be constructed with angles %d°, %d° and %d°?", a1, a2, a3);
        String answer = (a1 + a2 + a3 == 180) ? "Yes" : "No";

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setOption1("Yes");
        question.setOption2("No");
        return question;
    }

    private static Question generateSSSConstructionQuestion() {
        String questionText = "To construct a triangle using SSS criterion, we need to know:";
        String answer = "Lengths of all three sides";
        String[] options = {answer, "Two sides and the included angle", "Two angles and the included side", "Hypotenuse and one side of a right-angled triangle"};

        return createGenericQuestion(questionText, answer, options);
    }

    private static Question generateSASConstructionQuestion() {
        String questionText = "To construct a triangle using SAS criterion, we need to know:";
        String answer = "Two sides and the included angle";
        String[] options = {answer, "Lengths of all three sides", "Two angles and the included side", "Three angles"};

        return createGenericQuestion(questionText, answer, options);
    }

    private static Question generateASAConstructionQuestion() {
        String questionText = "To construct a triangle using ASA criterion, we need to know:";
        String answer = "Two angles and the included side";
        String[] options = {answer, "Two sides and the included angle", "Lengths of all three sides", "Hypotenuse and one side"};

        return createGenericQuestion(questionText, answer, options);
    }

    private static Question generateRHSConstructionQuestion() {
        String questionText = "In RHS triangle construction, 'H' stands for:";
        String answer = "Hypotenuse";
        String[] options = {answer, "Height", "Horizontal", "Heptagon"};

        return createGenericQuestion(questionText, answer, options);
    }

    private static Question generateParallelLineConstructionQuestion() {
        String questionText = "Which property is used when we construct a line parallel to a given line through a point not on it using only a ruler and compass?";
        String answer = "Equal alternate interior angles property";
        String[] options = {answer, "Angle sum property", "Pythagoras property", "Congruence property"};

        return createGenericQuestion(questionText, answer, options);
    }

    private static Question createGenericQuestion(String text, String answer, String[] options) {
        Question question = new Question();
        question.setQuestion(text);
        question.setAnswer(answer);
        List<String> optList = new ArrayList<>();
        Collections.addAll(optList, options);
        Collections.shuffle(optList);
        OptionUtils.setQuestionOptions(question, optList.toArray(new String[0]));
        return question;
    }
}
