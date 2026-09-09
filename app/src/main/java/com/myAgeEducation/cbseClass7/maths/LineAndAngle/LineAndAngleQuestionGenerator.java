package com.myAgeEducation.cbseClass7.maths.LineAndAngle;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.shapes.ShapesQuestionData;
import com.myAgeEducation.cbseClass7.utils.ConceptQuestion;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LineAndAngleQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion()
    {
        ConceptQuestion data = FIXED_QUESTIONS[RANDOM.nextInt(FIXED_QUESTIONS.length)];
        List<String> options = new ArrayList<>();
        options.add(data.correctAnswer);
        Collections.addAll(options, data.wrongAnswers);
        Collections.shuffle(options);
        return createQuestion(data.question, data.correctAnswer, options);
    }

    /*public static Question generateQuestion() {
        LineAndAngleQuestionType[] types = LineAndAngleQuestionType.values();
        LineAndAngleQuestionType type = types[RANDOM.nextInt(types.length)];
        LineAndAngleQuestionData data = generateQuestionData(type);

        Question q = new Question();
        q.setQuestion(data.question);
        q.setAnswer(data.answer);
        OptionUtils.setQuestionOptions(q, data.options);
        return q;
    }*/

    private static Question createQuestion(String questionText, String correctAnswer, List<String> options)
    {
        Question question = new Question();
        question.setQuestion(questionText);
        OptionUtils.setQuestionOptions(question, options);
        question.setAnswer(correctAnswer);
        return question;
    }

    private static final ConceptQuestion[] FIXED_QUESTIONS =
            {
                    // 1
                    new ConceptQuestion("Two sides that meet at a corner for an _______", "angle", "line", "point", "ray"),

                    // 2
                    new ConceptQuestion("A _______ is a collection of points going endlessly in both directions along a straight path", "line", "ray", "point", "angle"),

                    // 3
                    new ConceptQuestion("A _______ is the basic unit of geometry. It shows an exact location. It is represented with a dot", "point", "line", "plane", "angle"),

                    // 4
                    new ConceptQuestion("A _______ is part of a line. It has two endpoints.", "line segment", "line", "ray", "point"),

                    // 5
                    new ConceptQuestion("Angles that are less than a right angle are called _____ angles", "acute", "obtuse", "right", "reflex"),

                    // 6
                    new ConceptQuestion("Angles that are more than a right angle are called _____ angles", "obtuse", "acute", "right", "reflex"),

                    // 7
                    new ConceptQuestion("Angles that are greater than 90 degrees and less than 180 degrees are called ______", "obtuse", "acute", "right", "reflex"),

                    // 8
                    new ConceptQuestion("Angles that are less than 90 degrees and greater than 0 degree are called ______", "acute", "obtuse", "right", "reflex"),

                    // 9
                    new ConceptQuestion("You cannot measure a ray and a ________", "line", "point", "angle", "line segment"),

                    // 10
                    new ConceptQuestion("An obtuse angle is more than ______ degree and less than 180 degree", "90", "0", "180", "45"),

                    // 11
                    new ConceptQuestion("A _______ is part of a line. It has one endpoint and goes on endlessly in one direction.", "ray", "line", "line segment", "point"),

                    // 12
                    new ConceptQuestion("An angle is formed by two _______ having a common endpoint", "rays", "square", "plane", "angle"),

                    // 13
                    new ConceptQuestion("A right angle is exactly ______ degrees.", "90", "180", "45", "360"),

                    // 14
                    new ConceptQuestion("A straight angle is exactly ______ degrees.", "180", "90", "0", "360"),

                    // 15
                    new ConceptQuestion("The common endpoint where two rays meet to form an angle is called the ______", "vertex", "center", "corner", "point"),

                    // 16
                    new ConceptQuestion("Lines that never meet, no matter how far they are extended, are called ______ lines.", "parallel", "intersecting", "perpendicular", "straight"),

                    // 17
                    new ConceptQuestion("When two lines meet at a right angle, they are called ______ lines.", "perpendicular", "parallel", "intersecting", "slanted"),

                    // 18
                    new ConceptQuestion("A ______ is used to measure and draw angles.", "protractor", "ruler", "divider", "compass"),

                    // 19
                    new ConceptQuestion("Angles are measured in ______.", "degrees", "centimeters", "liters", "kilograms"),

                    // 20
                    new ConceptQuestion("The corner of a square forms a ______ angle.", "right", "acute", "obtuse", "straight"),

                    // 21
                    new ConceptQuestion("The angle formed by the hands of a clock at 3 o'clock is a ______ angle.", "right", "acute", "obtuse", "straight"),

                    // 22
                    new ConceptQuestion("A line has ______ endpoints.", "no", "one", "two", "many"),

                    // 23
                    new ConceptQuestion("A ray has ______ endpoint.", "one", "no", "two", "infinite"),

                    // 24
                    new ConceptQuestion("A line segment has ______ endpoints.", "two", "one", "no", "three"),

                    // 25
                    new ConceptQuestion("An angle greater than 180 degrees but less than 360 degrees is a ______ angle.", "reflex", "obtuse", "acute", "straight"),

                    // 26
                    new ConceptQuestion("Two lines that cross each other at a point are called ______ lines.", "intersecting", "parallel", "perpendicular", "curved"),

                    // 27
                    new ConceptQuestion("A complete turn is an angle of ______ degrees.", "360", "180", "90", "0"),

                    // 28
                    new ConceptQuestion("Double of a right angle is a ______ angle.", "straight", "right", "reflex", "acute"),

                    // 29
                    new ConceptQuestion("Half of a straight angle is a ______ angle.", "right", "acute", "obtuse", "reflex"),

                    // 30
                    new ConceptQuestion("The two rays forming an angle are called its ______.", "arms", "legs", "sides", "vertices"),

                    // 31
                    new ConceptQuestion("The measure of an acute angle is always ______ than 90 degrees.", "less", "greater", "equal", "double"),

                    // 32
                    new ConceptQuestion("The measure of an obtuse angle is always ______ than 90 degrees.", "greater", "less", "equal", "half"),

                    // 33
                    new ConceptQuestion("A line segment has a ______ length.", "fixed", "infinite", "changing", "zero"),

                    // 34
                    new ConceptQuestion("We use a ______ to measure the length of a line segment.", "ruler", "protractor", "compass", "divider"),

                    // 35
                    new ConceptQuestion("A _______ angle is exactly 180 degrees.", "straight", "right", "acute", "obtuse"),

                    // 36
                    new ConceptQuestion("A _______ angle is exactly 90 degrees.", "right", "straight", "acute", "obtuse"),

                    // 37
                    new ConceptQuestion("Vertical and horizontal lines meet at a _______ angle.", "right", "acute", "obtuse", "straight"),

                    // 38
                    new ConceptQuestion("The angle between the hands of a clock at 6:00 is a _______ angle.", "straight", "right", "acute", "obtuse"),
            };

    private static LineAndAngleQuestionData generateQuestionData(LineAndAngleQuestionType type) {
        String question;
        String answer;
        String[] options;

        switch (type) {
            case ANGLE_DEFINITION:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "angle";
                options = new String[]{"angle", "line", "point", "ray"};
                break;
            case LINE_DEFINITION:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "line";
                options = new String[]{"line", "ray", "point", "angle"};
                break;
            case POINT_DEFINITION:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "point";
                options = new String[]{"point", "line", "plane", "angle"};
                break;
            case LINE_SEGMENT_DEFINITION:
            case LINE_SEGMENT_ENDPOINTS:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "line segment";
                options = new String[]{"line segment", "ray", "line", "point"};
                break;
            case RAY_DEFINITION:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "ray";
                options = new String[]{"ray", "line", "line segment", "point"};
                break;
            case ACUTE_ANGLE_DEFINITION:
            case ACUTE_RANGE_DEFINITION:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "acute";
                options = new String[]{"acute", "obtuse", "right", "reflex"};
                break;
            case OBTUSE_ANGLE_DEFINITION:
            case OBTUSE_RANGE_DEFINITION:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "obtuse";
                options = new String[]{"obtuse", "acute", "right", "reflex"};
                break;
            case MEASURE_RAY_LINE:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "line";
                options = new String[]{"line", "point", "angle", "line segment"};
                break;
            case OBTUSE_DEGREE_MIN:
                question = LineAndAngleQuestionTemplatesUtil.getTemplate(type);
                answer = "90";
                options = new String[]{"90", "0", "180", "45"};
                break;
            default:
                question = "";
                answer = "";
                options = new String[]{};
        }

        // Shuffle options if they are not TRUE/FALSE to add variety
        if (options.length > 2) {
            List<String> optionList = new ArrayList<>();
            Collections.addAll(optionList, options);
            Collections.shuffle(optionList, RANDOM);
            options = optionList.toArray(new String[0]);
        }

        return new LineAndAngleQuestionData(question, answer, options, type);
    }

    private static String[] generateNumericOptions(int correct) {
        List<Integer> options = new ArrayList<>();
        options.add(correct);
        while (options.size() < 4) {
            int offset = (RANDOM.nextInt(10) + 1) * (RANDOM.nextBoolean() ? 1 : -1);
            int candidate = correct + offset;
            if (candidate > 0 && !options.contains(candidate)) {
                options.add(candidate);
            }
        }
        Collections.shuffle(options, RANDOM);
        String[] res = new String[4];
        for (int i = 0; i < 4; i++) {
            res[i] = String.valueOf(options.get(i));
        }
        return res;
    }

    private static Question convertToQuestion(ShapesQuestionData data) {
        Question q = new Question();
        q.setQuestion(data.question);
        q.setAnswer(data.answer);
        OptionUtils.setQuestionOptions(q, data.options);
        return q;
    }
}
