package com.myAgeEducation.cbseClass7.maths.congruence;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CongruenceQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(28);
        switch (type) {
            case 0: return generateRealWorldObjects();
            case 1: return generateLineSegmentCongruence();
            case 2: return generateAngleCongruence();
            case 3: return generateCongruentAngleMeasure();
            case 4: return generateTriangleCongruenceSSS();
            case 6: return generateCorrespondenceSymbolic();
            case 7: return generateCriterionIdentification();
            case 8: return generateNonCongruenceCriterion();
            case 9: return generateSquareRectangleAreaCongruence();
            case 10: return generateRightTriangleCongruence();
            case 11: return generateTriangleCoverExactly();
            case 12: return generateKaleidoscopeCongruence();
            case 13:
            case 14:
            case 15:
            case 16:
                return generateVisualCongruenceQuestion();
            case 17:
                return generateOverlappingTriangleStatement();
            case 18:
            case 19:
                return generateCorrespondenceNotTrue();

            default: return generateTriangleCongruenceCriteria();
        }
    }

    private static Question generateRealWorldObjects() {
        String[][] items = {
                {"sheets of the same letter-pad", "YES"},
                {"biscuits in the same packet", "YES"},
                {"shaving blades of the same company", "YES"},
                {"stamps of the same denomination and company", "YES"},
                {"two coins of the same value", "YES"}
        };
        int idx = RANDOM.nextInt(items.length);
        Question q = new Question();
        q.setQuestion("Are the " + items[idx][0] + " congruent?");
        q.setAnswer(items[idx][1]);
        OptionUtils.setQuestionOptions(q, new String[]{"YES", "NO"});
        return q;
    }

    private static Question generateLineSegmentCongruence() {
        boolean correct = RANDOM.nextBoolean();
        Question q = new Question();
        if (RANDOM.nextBoolean()) {
            q.setQuestion("TRUE or FALSE. If two line segments are congruent, they have " + (correct ? "the same" : "different") + " length.");
        } else {
            q.setQuestion("TRUE or FALSE. If two line segments have the same length, they are " + (correct ? "" : "not ") + "congruent.");
        }
        q.setAnswer(correct ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateAngleCongruence() {
        boolean correct = RANDOM.nextBoolean();
        Question q = new Question();
        if (RANDOM.nextBoolean()) {
            q.setQuestion("TRUE or FALSE. If two angles have the same measure, they are " + (correct ? "" : "not ") + "congruent.");
        } else {
            q.setQuestion("TRUE or FALSE. If two angles are congruent, their measures are " + (correct ? "the same" : "different") + ".");
        }
        q.setAnswer(correct ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateCongruentAngleMeasure() {
        int angle = 10 + RANDOM.nextInt(160);
        Question q = new Question();
        q.setQuestion("Among two congruent angles, one has a measure of " + angle + "°; the measure of the other angle is ___________.");
        q.setAnswer(angle + "°");
        
        List<String> options = new ArrayList<>();
        options.add(angle + "°");
        options.add((180 - angle) + "°");
        options.add((angle / 2) + "°");
        options.add((angle + 20) + "°");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateTriangleCongruenceCriteria() {
        String[][] criteria = {
                {"SSS", "If under a given correspondence, the three sides of one triangle are equal to the three corresponding sides of another triangle, then the triangles are congruent."},
                {"SAS", "If under a correspondence, two sides and the angle included between them of a triangle are equal to two corresponding sides and the angle included between them of another triangle, then the triangles are congruent."},
                {"ASA", "If under a correspondence, two angles and the included side of a triangle are equal to two corresponding angles and the included side of another triangle, then the triangles are congruent."},
                {"RHS", "If under a correspondence, the hypotenuse and one side of a right-angled triangle are respectively equal to the hypotenuse and one side of another right-angled triangle, then the triangles are congruent."}
        };
        int idx = RANDOM.nextInt(criteria.length);
        boolean correct = RANDOM.nextBoolean();
        
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. " + (correct ? criteria[idx][1] : criteria[idx][1].replace("equal to", "different from")));
        q.setAnswer(correct ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateTriangleCongruenceSSS() {
        double s1 = 3 + RANDOM.nextInt(5) + 0.1 * RANDOM.nextInt(10);
        double s2 = 5 + RANDOM.nextInt(5) + 0.1 * RANDOM.nextInt(10);
        double s3 = 4 + RANDOM.nextInt(5) + 0.1 * RANDOM.nextInt(10);
        
        boolean congruent = RANDOM.nextBoolean();
        
        Question q = new Question();
        String t1 = "AB = " + s1 + " cm, BC = " + s2 + " cm, AC = " + s3 + " cm";
        String t2;
        if (congruent) {
            t2 = "PQ = " + s2 + " cm, QR = " + s3 + " cm, PR = " + s1 + " cm";
        } else {
            t2 = "PQ = " + (s2 + 1) + " cm, QR = " + s3 + " cm, PR = " + s1 + " cm";
        }
        
        q.setQuestion("In triangles ABC and PQR, " + t1 + " and in triangle PQR, " + t2 + ". Examine whether the two triangles are congruent or not.");
        q.setAnswer(congruent ? "Triangles ABC and PQR are congruent" : "Triangles ABC and PQR are not congruent");
        OptionUtils.setQuestionOptions(q, new String[]{"Triangles ABC and PQR are congruent", "Triangles ABC and PQR are not congruent"});
        return q;
    }

    private static Question generateCorrespondenceSymbolic() {
        String[] vertices2 = {"P", "Q", "R"};
        
        List<Integer> map = new ArrayList<>();
        map.add(0); map.add(1); map.add(2);
        Collections.shuffle(map);
        
        // ABC <-> Map(A)Map(B)Map(C)
        String corr = "ABC ↔ " + vertices2[map.get(0)] + vertices2[map.get(1)] + vertices2[map.get(2)];
        
        int part = RANDOM.nextInt(3);
        String side1;
        String side2;
        
        switch(part) {
            case 0: // Side AB
                side1 = "AB";
                side2 = vertices2[map.get(0)] + vertices2[map.get(1)];
                break;
            case 1: // Side BC
                side1 = "BC";
                side2 = vertices2[map.get(1)] + vertices2[map.get(2)];
                break;
            default: // Side AC
                side1 = "AC";
                side2 = vertices2[map.get(0)] + vertices2[map.get(2)];
                break;
        }
        
        Question q = new Question();
        q.setQuestion("If ΔABC ≅ ΔPQR under the correspondence " + corr + ", then " + side1 + " = _________");
        q.setAnswer(side2);
        
        List<String> options = new ArrayList<>();
        options.add(side2);
        options.add(vertices2[map.get(0)] + vertices2[map.get(2)]); // wrong combinations
        options.add(vertices2[map.get(1)] + vertices2[map.get(0)]);
        options.add(vertices2[map.get(2)] + vertices2[map.get(1)]);
        
        // Clean up options to ensure unique
        List<String> uniqueOptions = new ArrayList<>();
        for(String opt : options) if(!uniqueOptions.contains(opt)) uniqueOptions.add(opt);
        while(uniqueOptions.size() < 4) uniqueOptions.add(vertices2[RANDOM.nextInt(3)] + vertices2[RANDOM.nextInt(3)]);
        
        Collections.shuffle(uniqueOptions);
        OptionUtils.setQuestionOptions(q, uniqueOptions);
        return q;
    }

    private static Question generateCriterionIdentification() {
        String[] types = {"SSS", "SAS", "ASA", "RHS"};
        String[] desc = {
            "If under a given correspondence, the three sides of one triangle are equal to the three corresponding sides of another triangle, then the triangles are congruent.",
            "If under a correspondence, two sides and the angle included between them of a triangle are equal to two corresponding sides and the angle included between them of another triangle, then the triangles are congruent.",
            "If under a correspondence, two angles and the included side of a triangle are equal to two corresponding angles and the included side of another triangle, then the triangles are congruent.",
            "If under a correspondence, the hypotenuse and one side of a right-angled triangle are respectively equal to the hypotenuse and one side of another right-angled triangle, then the triangles are congruent."
        };
        
        int targetIdx = RANDOM.nextInt(4);
        Question q = new Question();
        q.setQuestion("Which one of the following is " + types[targetIdx] + " Congruence criterion for a triangle?");
        q.setAnswer(desc[targetIdx]);
        
        List<String> options = new ArrayList<String>(Arrays.asList(desc));
        // We don't shuffle these because they are long sentences, but setQuestionOptions will assign them.
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateNonCongruenceCriterion() {
        Question q = new Question();
        q.setQuestion("By which of the following criterion two triangles cannot be proved congruent?");
        q.setAnswer("AAA");
        OptionUtils.setQuestionOptions(q, new String[]{"SSS", "SAS", "ASA", "AAA"});
        return q;
    }

    private static Question generateSquareRectangleAreaCongruence() {
        boolean isSquare = RANDOM.nextBoolean();
        boolean correct = RANDOM.nextBoolean();
        Question q = new Question();
        if (isSquare) {
            q.setQuestion("TRUE or FALSE. If the areas of two squares are same, they are " + (correct ? "" : "not ") + "congruent.");
            q.setAnswer(correct ? "TRUE" : "FALSE");
        } else {
            q.setQuestion("TRUE or FALSE. If the areas of two rectangles are same, they are " + (correct ? "not " : "") + "congruent.");
            q.setAnswer(correct ? "TRUE" : "FALSE");
        }
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateRightTriangleCongruence() {
        boolean legRule = RANDOM.nextBoolean();
        Question q = new Question();
        if (legRule) {
            q.setQuestion("TRUE or FALSE. If two legs of a right triangle are equal to two legs of another right triangle, then the right triangles are congruent.");
        } else {
            q.setQuestion("TRUE or FALSE. If hypotenuse and an acute angle of one right triangle are equal to the hypotenuse and an acute angle of another right triangle, then the triangles are congruent.");
        }
        q.setAnswer("TRUE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateTriangleCoverExactly() {
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. Two triangles are congruent if they are copies of each other and when superposed, they cover each other exactly.");
        q.setAnswer("TRUE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateKaleidoscopeCongruence() {
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. The top and bottom faces of a kaleidoscope are congruent.");
        q.setAnswer("TRUE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateVisualCongruenceQuestion() {
        int variant = RANDOM.nextInt(4);
        String questionText = "";
        String answer = "";
        String imageCode = "";
        
        switch(variant) {
            case 0: // SSS
                questionText = "In the given figure, AD = CD and AB = CB. By which congruence rule, ΔABD and ΔCBD are congruent?";
                answer = "SSS";
                imageCode = "CONG_SSS_KITE";
                break;
            case 1: // SAS
                questionText = "In the given figure, AD = CD and ∠ADB = ∠CDB. By which congruence rule, ΔABD and ΔCBD are congruent?";
                answer = "SAS";
                imageCode = "CONG_SAS_SHARED";
                break;
            case 2: // ASA
                questionText = "In the given figure, ∠ADB = ∠CDB and ∠ABD = ∠CBD. By which congruence rule, ΔABD and ΔCBD are congruent?";
                answer = "ASA";
                imageCode = "CONG_ASA_SHARED";
                break;
            default: // RHS
                questionText = "In the given figure, AD = CD and ∠ABD = ∠CBD = 90°. By which congruence rule, ΔABD and ΔCBD are congruent?";
                answer = "RHS";
                imageCode = "CONG_RHS_SHARED";
                break;
        }

        Question q = new Question();
        q.setQuestion(questionText);
        q.setAnswer(answer);
        q.setImage(imageCode);
        OptionUtils.setQuestionOptions(q, new String[]{"SSS", "SAS", "ASA", "RHS"});
        return q;
    }

    private static Question generateOverlappingTriangleStatement() {
        Question q = new Question();
        q.setQuestion("In the given figure, AC = BD and AD = BC. Which of the following statements is meaningfully written?");
        q.setAnswer("ΔABC ≅ ΔBAD");
        q.setImage("CONG_OVERLAPPING_SSS");
        
        String[] options = {
            "ΔABC ≅ ΔABD",
            "ΔABC ≅ ΔBAD",
            "ΔACB ≅ ΔBAD",
            "ΔBCA ≅ ΔBAD"
        };
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateCorrespondenceNotTrue() {
        // CAB <-> EDF
        // Mapping: C-E, A-D, B-F
        String corr = "CAB ↔ EDF";
        
        Question q = new Question();
        q.setQuestion("If for ΔABC and ΔDEF, the correspondence " + corr + " gives a congruence, then which of the following is not TRUE?");
        
        // Correct (True) statements based on C-E, A-D, B-F:
        // Sides: CA=ED, AB=DF, CB=EF
        // Angles: C=E, A=D, B=F
        
        // AC = DE (True since A-D and C-E)
        // AB = EF (False since A-D and B-F means AB should be DF)
        // Angle A = Angle D (True)
        // Angle C = Angle E (True)
        
        q.setAnswer("AB = EF");
        
        String[] options = {
            "AC = DE",
            "AB = EF",
            "∠A = ∠D",
            "∠C = ∠E"
        };
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }


}
