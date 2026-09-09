package com.myAgeEducation.cbseClass7.maths.LineAndAngle;

import com.myAgeEducation.cbseClass7.maths.shapes.ShapesQuestionType;

public class LineAndAngleQuestionTemplatesUtil {
    public static String getTemplate(LineAndAngleQuestionType type) {
        switch (type) {
            case ANGLE_DEFINITION: return "Two sides that meet at a corner for an _______";
            case LINE_DEFINITION: return "A _______ is a collection of points going endlessly in both directions along a straight path";
            case POINT_DEFINITION: return "A _______ is the basic unit of geometry. It shows an exact location. It is represented with a dot";
            case LINE_SEGMENT_DEFINITION: return "A _______ is part of a line. It has two endpoints.";

            case RAY_DEFINITION: return "A _______ is part of a line. It has one endpoint and goes on endlessly in one direction.";
            case ACUTE_ANGLE_DEFINITION: return "Angles that are less than a right angle are called _____ angles";
            case OBTUSE_ANGLE_DEFINITION: return "Angles that are more than a right angle are called _____ angles";
            case OBTUSE_RANGE_DEFINITION: return "Angles that are greater than 90 degrees and less than 180 degrees are called ______";
            case ACUTE_RANGE_DEFINITION: return "Angles that are less than 90 degrees and greater than 0 degree are called ______";
            case MEASURE_RAY_LINE: return "You cannot measure a ray and a ________";
            case LINE_SEGMENT_ENDPOINTS: return "A part of a line that has two endpoints is a _______";
            case OBTUSE_DEGREE_MIN: return "An obtuse angle is more than ______ degree and less than 180 degree";
            default: return "";
        }
    }
}
