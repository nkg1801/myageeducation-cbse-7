package com.myAgeEducation.cbseClass7.maths.shapes;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShapesQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        ShapesQuestionType[] types = ShapesQuestionType.values();
        ShapesQuestionType type = types[RANDOM.nextInt(types.length)];
        ShapesQuestionData data = generateQuestionData(type);
        return convertToQuestion(data);
    }

    /*public static Question generateLineAndAngleQuestion() {
        LineAndAngleQuestionType[] types = LineAndAngleQuestionType.values();
        LineAndAngleQuestionType type = types[RANDOM.nextInt(types.length)];
        LineAndAngleQuestionData data = generateLineAndAngleQuestionData(type);

        Question q = new Question();
        q.setQuestion(data.question);
        q.setAnswer(data.answer);
        OptionUtils.setQuestionOptions(q, data.options);
        return q;
    }*/

    private static ShapesQuestionData generateQuestionData(ShapesQuestionType type) {
        String question;
        String answer;
        String[] options;

        switch (type) {
            case POLYGON_3_SIDES:
                question = ShapesQuestionTemplatesUtil.getTemplate(type);
                answer = "Triangle";
                options = new String[]{"Triangle", "Square", "Rectangle", "Circle"};
                break;
            case POLYGON_4_SIDES:
                question = ShapesQuestionTemplatesUtil.getTemplate(type);
                answer = "Quadrilateral";
                options = new String[]{"Quadrilateral", "Triangle", "Pentagon", "Circle"};
                break;
            case RECTANGLE_DEFINITION:
                question = ShapesQuestionTemplatesUtil.getTemplate(type);
                answer = "Rectangle";
                options = new String[]{"Rectangle", "Square", "Triangle", "Circle"};
                break;
            case SQUARE_DEFINITION:
                question = ShapesQuestionTemplatesUtil.getTemplate(type);
                answer = "Square";
                options = new String[]{"Square", "Rectangle", "Rhombus", "Parallelogram"};
                break;
            case SQUARE_QUADRILATERAL_TF:
            case TRIANGLE_POLYGON_TF:
            case POLYGON_SEGMENTS_TF:
            case RADII_EQUAL_TF:
            case DIAMETER_CENTER_TF:
            case CIRCUMFERENCE_DEFINITION_TF:
            case RADIUS_DIAMETER_CENTER_TF:
            case RADIUS_HALF_DIAMETER_TF:
            case SUM_RADII_DIAMETER_TF:
                question = ShapesQuestionTemplatesUtil.getTemplate(type);
                answer = "TRUE";
                options = new String[]{"TRUE", "FALSE"};
                break;
            case SYMMETRY_CONCEPT:
            {
                String[][] symQuestions = {
                    {"How many lines of symmetry does a rectangle have?", "2", "1", "4", "infinite"},
                    {"How many lines of symmetry does a square have?", "4", "2", "1", "8"},
                    {"How many lines of symmetry does a pair of scissors have?", "1", "2", "0", "4"},
                    {"TRUE or FALSE. A circle has only one line of symmetry.", "FALSE", "TRUE"},
                    {"A circle has ___________ lines of symmetry.", "infinite", "one", "two", "four"},
                    {"How many lines of symmetry does an equilateral triangle have?", "3", "1", "2", "0"},
                    {"How many lines of symmetry does a regular pentagon have?", "5", "1", "4", "infinite"},
                    {"How many lines of symmetry does the letter 'H' have?", "2", "1", "0", "4"},
                    {"Which of these letters has no line of symmetry?", "F", "A", "M", "T"},
                    {"A line that divides a figure into two identical halves is called a line of _________.", "symmetry", "boundary", "intersection", "division"}
                };
                int idx = RANDOM.nextInt(symQuestions.length);
                question = symQuestions[idx][0];
                answer = symQuestions[idx][1];
                options = Arrays.copyOfRange(symQuestions[idx], 1, symQuestions[idx].length);
                break;
            }
            case QUADRILATERAL_RECTANGLE_TF:
            case DIAMETER_HALF_RADIUS_TF:
            case CLOSED_CURVES_POLYGON_TF:
                question = ShapesQuestionTemplatesUtil.getTemplate(type);
                answer = "FALSE";
                options = new String[]{"TRUE", "FALSE"};
                break;
            case DIAMETER_RADIUS_RELATION:
                question = ShapesQuestionTemplatesUtil.getTemplate(type);
                answer = "Diameter";
                options = new String[]{"Diameter", "Circumference", "Chord", "Center"};
                break;
            case CALCULATE_RADIUS:
                int d = (5 + RANDOM.nextInt(46)) * 2; // Even number between 10 and 100
                question = String.format(ShapesQuestionTemplatesUtil.getTemplate(type), d);
                answer = String.valueOf(d / 2);
                options = generateNumericOptions(d / 2);
                break;
            case CALCULATE_DIAMETER:
                int r = 5 + RANDOM.nextInt(46); // 5 to 50
                question = String.format(ShapesQuestionTemplatesUtil.getTemplate(type), r);
                answer = String.valueOf(r * 2);
                options = generateNumericOptions(r * 2);
                break;
            case OTHER_RADII_LENGTH:
                int r2 = 2 + RANDOM.nextInt(20);
                question = String.format(ShapesQuestionTemplatesUtil.getTemplate(type), r2);
                answer = r2 + " cm";
                options = new String[]{answer, r2 * 2 + " cm", r2 / 2 + " cm", "10 cm"};
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

        return new ShapesQuestionData(question, answer, options, type);
    }

    /*private static LineAndAngleQuestionData generateLineAndAngleQuestionData(LineAndAngleQuestionType type) {
        String question;
        String answer;
        String[] options;

        switch (type) {
            case ANGLE_DEFINITION:
                question = ShapesQuestionTemplatesUtil.getTemplate(type);
                answer = "angle";
                options = new String[]{"angle", "line", "point", "ray"};
                break;
            case LINE_DEFINITION:
                question = ShapesQuestionTemplatesUtil.getLineAndAngleTemplate(type);
                answer = "line";
                options = new String[]{"line", "ray", "point", "angle"};
                break;
            case POINT_DEFINITION:
                question = ShapesQuestionTemplatesUtil.getLineAndAngleTemplate(type);
                answer = "point";
                options = new String[]{"point", "line", "plane", "angle"};
                break;
            case LINE_SEGMENT_DEFINITION:
            case LINE_SEGMENT_ENDPOINTS:
                question = ShapesQuestionTemplatesUtil.getLineAndAngleTemplate(type);
                answer = "line segment";
                options = new String[]{"line segment", "ray", "line", "point"};
                break;
            case RAY_DEFINITION:
                question = ShapesQuestionTemplatesUtil.getLineAndAngleTemplate(type);
                answer = "ray";
                options = new String[]{"ray", "line", "line segment", "point"};
                break;
            case ACUTE_ANGLE_DEFINITION:
            case ACUTE_RANGE_DEFINITION:
                question = ShapesQuestionTemplatesUtil.getLineAndAngleTemplate(type);
                answer = "acute";
                options = new String[]{"acute", "obtuse", "right", "reflex"};
                break;
            case OBTUSE_ANGLE_DEFINITION:
            case OBTUSE_RANGE_DEFINITION:
                question = ShapesQuestionTemplatesUtil.getLineAndAngleTemplate(type);
                answer = "obtuse";
                options = new String[]{"obtuse", "acute", "right", "reflex"};
                break;
            case MEASURE_RAY_LINE:
                question = ShapesQuestionTemplatesUtil.getLineAndAngleTemplate(type);
                answer = "line";
                options = new String[]{"line", "point", "angle", "line segment"};
                break;
            case OBTUSE_DEGREE_MIN:
                question = ShapesQuestionTemplatesUtil.getLineAndAngleTemplate(type);
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
    }*/

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
