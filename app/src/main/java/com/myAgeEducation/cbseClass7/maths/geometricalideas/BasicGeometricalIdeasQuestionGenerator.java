package com.myAgeEducation.cbseClass7.maths.geometricalideas;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BasicGeometricalIdeasQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int idx = RANDOM.nextInt(questionBank.length);
        String[] item = questionBank[idx];

        Question question = new Question();
        question.setQuestion(item[0]);
        question.setAnswer(item[1]);

        List<String> options = new ArrayList<>(Arrays.asList(item).subList(1, item.length));
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static final String[][] questionBank = {
            {"If two lines have one common point, they are called ________", "intersecting lines", "parallel lines", "straight lines", "curved lines"},
            {"TRUE or FALSE. Two lines can intersect in more than one point.", "FALSE", "TRUE"},
            {"TRUE or FALSE. More than two lines can intersect in one point.", "TRUE", "FALSE"},
            {"TRUE or FALSE. Lines which do not meet are called parallel lines.", "TRUE", "FALSE"},
            {"TRUE or FALSE. Lines which do not meet are called intersecting lines.", "FALSE", "TRUE"},
            {"TRUE or FALSE. All closed curves form polygons.", "FALSE", "TRUE"},
            {"A _______ is part of a line. It has one endpoint and goes on endlessly in one direction.", "ray", "line segment", "triangle", "point"},
            {"A ________ has two endpoints.", "line segment", "line", "ray", "angle"},
            {"A _______ extends endlessly in both directions.", "line", "line segment", "ray", "point"}
    };
}
