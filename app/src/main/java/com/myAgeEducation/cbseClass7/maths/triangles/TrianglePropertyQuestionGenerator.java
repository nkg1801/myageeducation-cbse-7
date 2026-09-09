package com.myAgeEducation.cbseClass7.maths.triangles;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TrianglePropertyQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        //return generateExteriorAnglePuzzle();
        int type = RANDOM.nextInt(35);
        switch (type) {
            case 0: return generateMediansCount();
            case 1: return generateMedianInterior();
            case 2: return generateAltitudesCount();
            case 3: return generateAltitudeInterior();
            case 4: return generateAltitudeMedianSame();
            case 5: return generateExteriorAnglesEqual();
            case 6: return generateExteriorStraight();
            case 7: return generateAngleSum();
            case 8: return generateThirdAngle();
            case 9: return generateIsoscelesEqualAngles();
            case 10: return generateTwoRightAngles();
            case 11: return generateTwoObtuseAngles();
            case 12: return generateTwoAcuteAngles();
            case 13: return generateAllGreater60();
            case 14: return generateAll60();
            case 15: return generateAllLess60();
            case 16: return generateEquilateralDefinition();
            case 17: return generateIsoscelesDefinition();
            case 18: return generateSideSumProperty();
            case 19: return generateSideDifferenceProperty();
            case 20: return generateTriangleExistenceSides();
            case 21: return generateThirdSideRange();
            case 22: return generateMinThirdSide();
            case 23: return generateBrokenTree();
            case 24: return generateMedianDefinition();
            case 25: return generateEastNorthWalk();
            case 26: return generateRightTriangleThirdAngle();
            case 27: return generateAnglesInRatio();
            case 28: return generateExteriorAndIsosceles();
            case 29: return generateIsoscelesPerimeter();
            case 30: return generateIsoscelesRatio();
            case 31: return generateOneAngleEqualsSum();
            case 32: return generateSideSumPropertyGE();
            case 33: return generateExteriorAnglePuzzle();
            case 34: return generateCircleTriangleQuestion();
            default: return generateAngleSum();
        }
    }

    private static Question generateMediansCount() {
        Question q = new Question();
        q.setQuestion("How many medians can a triangle have?");
        q.setAnswer("3");
        OptionUtils.setQuestionOptions(q, new String[]{"1", "2", "3", "4"});
        return q;
    }

    private static Question generateMedianInterior() {
        Question q = new Question();
        q.setQuestion("Does a median lie wholly in the interior of the triangle?");
        q.setAnswer("Yes");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateAltitudesCount() {
        Question q = new Question();
        q.setQuestion("How many altitudes can a triangle have?");
        q.setAnswer("3");
        OptionUtils.setQuestionOptions(q, new String[]{"1", "2", "3", "4"});
        return q;
    }

    private static Question generateAltitudeInterior() {
        Question q = new Question();
        q.setQuestion("Will an altitude always lie in the interior of a triangle?");
        q.setAnswer("No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateAltitudeMedianSame() {
        Question q = new Question();
        q.setQuestion("Can the altitude and median be same for a triangle?");
        q.setAnswer("Yes");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateExteriorAnglesEqual() {
        Question q = new Question();
        q.setQuestion("Are the exterior angles formed at each vertex of a triangle equal?");
        q.setAnswer("No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateExteriorStraight() {
        Question q = new Question();
        q.setQuestion("Can the exterior angle of a triangle be a straight angle?");
        q.setAnswer("No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateAngleSum() {
        Question q = new Question();
        q.setQuestion("The sum of the measures of the three angles of a triangle is ______ degrees");
        q.setAnswer("180");
        OptionUtils.setQuestionOptions(q, new String[]{"90", "270", "180", "360"});
        return q;
    }

    private static Question generateThirdAngle() {
        int a = 20 + RANDOM.nextInt(60);
        int b = 20 + RANDOM.nextInt(60);
        int c = 180 - a - b;
        
        Question q = new Question();
        q.setQuestion("Two angles of a triangle are " + a + "° and " + b + "°. What is the measure of the third angle?");
        q.setAnswer(c + "°");
        
        List<String> options = new ArrayList<>();
        options.add(c + "°");
        options.add((c + 10) + "°");
        options.add((c - 10) + "°");
        options.add((180 - c) + "°");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateIsoscelesEqualAngles() {
        int angle = 40 + RANDOM.nextInt(50) * 2; // Even for nice integer results
        int equalAngle = (180 - angle) / 2;
        
        Question q = new Question();
        q.setQuestion("One of the angles of a triangle is " + angle + " degree and the other two angles are equal. What is the measure of each of the equal angles?");
        q.setAnswer(equalAngle + "°");
        
        List<String> options = new ArrayList<>();
        options.add(equalAngle + "°");
        options.add(angle + "°");
        options.add((180 - angle) + "°");
        options.add((equalAngle + 10) + "°");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateTwoRightAngles() {
        Question q = new Question();
        q.setQuestion("Can you have a triangle with two right angles?");
        q.setAnswer("No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateTwoObtuseAngles() {
        Question q = new Question();
        q.setQuestion("Can you have a triangle with two obtuse angles?");
        q.setAnswer("No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateTwoAcuteAngles() {
        Question q = new Question();
        q.setQuestion("Can you have a triangle with two acute angles?");
        q.setAnswer("Yes");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateAllGreater60() {
        Question q = new Question();
        q.setQuestion("Can you have a triangle with all the three angles greater than 60 degree?");
        q.setAnswer("No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateAll60() {
        Question q = new Question();
        q.setQuestion("Can you have a triangle with all the three angles equal to 60 degree?");
        q.setAnswer("Yes");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateAllLess60() {
        Question q = new Question();
        q.setQuestion("Can you have a triangle with all the three angles less than 60 degree?");
        q.setAnswer("No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateEquilateralDefinition() {
        boolean correct = RANDOM.nextBoolean();
        Question q = new Question();
        String name = correct ? "equilateral" : "isosceles";
        q.setQuestion("TRUE or FALSE. A triangle in which all the three sides are of equal lengths is called an " + name + " triangle.");
        q.setAnswer(correct ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateIsoscelesDefinition() {
        boolean correct = RANDOM.nextBoolean();
        Question q = new Question();
        String name = correct ? "isosceles" : "equilateral";
        q.setQuestion("TRUE or FALSE. A triangle in which two sides are of equal lengths is called an " + name + " triangle.");
        q.setAnswer(correct ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateSideSumProperty() {
        boolean correct = RANDOM.nextBoolean();
        Question q = new Question();
        String relation = correct ? "greater than" : "smaller than";
        q.setQuestion("TRUE or FALSE. The sum of the lengths of any two sides of a triangle is " + relation + " the third side.");
        q.setAnswer(correct ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateSideDifferenceProperty() {
        boolean correct = RANDOM.nextBoolean();
        Question q = new Question();
        String relation = correct ? "smaller than" : "greater than";
        q.setQuestion("TRUE or FALSE. The difference between the length of any two sides of a triangle is " + relation + " the length of the third side.");
        q.setAnswer(correct ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateTriangleExistenceSides() {
        double a = 5 + RANDOM.nextInt(10);
        double b = 5 + RANDOM.nextInt(10);
        double c;
        boolean possible = RANDOM.nextBoolean();
        
        if (possible) {
            c = Math.abs(a - b) + 1 + RANDOM.nextInt(5);
            if (c >= a + b) c = (a + b) / 2.0;
        } else {
            c = a + b + 1 + RANDOM.nextInt(5);
        }
        
        Question q = new Question();
        q.setQuestion("Is there a triangle whose sides have lengths " + a + " cm, " + b + " cm and " + c + " cm?");
        q.setAnswer(possible ? "Yes" : "No");
        OptionUtils.setQuestionOptions(q, new String[]{"Yes", "No"});
        return q;
    }

    private static Question generateThirdSideRange() {
        int a = 5 + RANDOM.nextInt(10);
        int b = a + 2 + RANDOM.nextInt(5);
        
        int low = b - a;
        int high = b + a;
        
        Question q = new Question();
        q.setQuestion("The lengths of two sides of a triangle are " + a + " cm and " + b + " cm. Between which two numbers can length of the third side fall?");
        q.setAnswer("Greater than " + low + " and less than " + high + " cm");
        
        List<String> options = new ArrayList<>();
        options.add("Greater than " + low + " and less than " + high + " cm");
        options.add("Greater than " + (low - 1) + " and less than " + high + " cm");
        options.add("Greater than 0 and less than " + high + " cm");
        options.add("Greater than " + a + " and less than " + b + " cm");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateMinThirdSide() {
        int a = 10 + RANDOM.nextInt(10);
        double b = 5 + RANDOM.nextDouble() * 5;
        int minVal = (int) Math.floor(Math.abs(a - b)) + 1;
        
        Question q = new Question();
        String bStr = String.format(Locale.US, "%.1f", b);
        q.setQuestion("The sides of a triangle have lengths " + a + " cm, " + bStr + " cm and <i>a</i>, where <i>a</i> is a whole number. The minimum value that <i>a</i> can take is __________");
        q.setAnswer(minVal + " cm");
        
        List<String> options = new ArrayList<>();
        options.add(minVal + " cm");
        options.add((minVal + 1) + " cm");
        options.add((minVal - 1) + " cm");
        options.add((int)(a + b) + " cm");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateBrokenTree() {
        int[][] pyth = {{3, 4, 5}, {5, 12, 13}, {8, 15, 17}, {7, 24, 25}};
        int[] set = pyth[RANDOM.nextInt(pyth.length)];
        int base = set[0];
        int height = set[1];
        int hyp = set[2];
        
        if (RANDOM.nextBoolean()) {
            int temp = base;
            base = height;
            height = temp;
        }
        
        int totalHeight = height + hyp;
        
        Question q = new Question();
        q.setQuestion("The top of a broken tree touches the ground at a distance of " + base + " m from the base. If the tree is broken at a height of " + height + " m from the ground then the actual height of the tree is ________");
        q.setAnswer(totalHeight + " m");
        
        List<String> options = new ArrayList<>();
        options.add(totalHeight + " m");
        options.add(hyp + " m");
        options.add((height + base) + " m");
        options.add((totalHeight + 5) + " m");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateMedianDefinition() {
        Question q = new Question();
        q.setQuestion("If we join a vertex to a point on opposite side which divides that side in the ratio 1:1, then what is the special name of that line segment?");
        q.setAnswer("Median");
        OptionUtils.setQuestionOptions(q, new String[]{"Median", "Angle bisector", "Altitude", "Hypotenuse"});
        return q;
    }

    private static Question generateEastNorthWalk() {
        int[][] set = {{6, 8, 10}, {9, 12, 15}, {12, 16, 20}, {5, 12, 13}, {8, 15, 17}};
        int[] choice = set[RANDOM.nextInt(set.length)];
        int east = choice[0];
        int north = choice[1];
        int dist = choice[2];
        
        Question q = new Question();
        q.setQuestion("A person walks " + east + " km due east and then " + north + " km due north. How far is he from his starting place?");
        q.setAnswer(dist + " km");
        
        List<String> options = new ArrayList<>();
        options.add(dist + " km");
        options.add((east + north) + " km");
        options.add((dist - 1) + " km");
        options.add((dist + 2) + " km");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateRightTriangleThirdAngle() {
        int angle = 10 + RANDOM.nextInt(70);
        int other = 90 - angle;
        
        Question q = new Question();
        q.setQuestion("In a right-angled triangle if one acute angle measures " + angle + "°, then what is the measure of the third angle?");
        q.setAnswer(other + "°");
        
        List<String> options = new ArrayList<>();
        options.add(other + "°");
        options.add((other + 10) + "°");
        options.add((other - 10) + "°");
        options.add(angle + "°");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateAnglesInRatio() {
        int r1 = 1 + RANDOM.nextInt(3);
        int r2 = 2 + RANDOM.nextInt(3);
        int r3 = 3 + RANDOM.nextInt(3);
        
        int sum = r1 + r2 + r3;
        float unit = 180.0f / sum;
        int a1 = Math.round(r1 * unit);
        int a2 = Math.round(r2 * unit);
        int a3 = 180 - a1 - a2;
        
        Question q = new Question();
        q.setQuestion("The angles of a triangle are in the ratio " + r1 + ":" + r2 + ":" + r3 + ". Find the angles.");
        String answer = a1 + "°, " + a2 + "° and " + a3 + "°";
        q.setAnswer(answer);
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((a1-5) + "°, " + (a2+2) + "° and " + (a3+3) + "°");
        options.add((a1+10) + "°, " + (a2-5) + "° and " + (a3-5) + "°");
        options.add("60°, 60° and 60°");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateExteriorAndIsosceles() {
        int equalAngle = 30 + RANDOM.nextInt(40);
        int vertexAngle = 180 - 2 * equalAngle;
        int extAngle = 180 - equalAngle;
        
        Question q = new Question();
        q.setQuestion("In ΔABC, if ∠A = ∠C, and exterior angle ABX = " + extAngle + "°, then find the angles of the triangle.");
        String answer = "∠A = " + equalAngle + "°, ∠C = " + equalAngle + "° and ∠ABC = " + vertexAngle + "°";
        q.setAnswer(answer);
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("∠A = " + (equalAngle-10) + "°, ∠C = " + (equalAngle+10) + "° and ∠ABC = " + vertexAngle + "°");
        options.add("∠A = " + equalAngle + "°, ∠C = " + equalAngle + "° and ∠ABC = " + (vertexAngle+10) + "°");
        options.add("∠A = 60°, ∠C = 60° and ∠ABC = 60°");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateIsoscelesPerimeter() {
        int a = 5 + RANDOM.nextInt(10);
        int b = a + 5 + RANDOM.nextInt(10);
        
        boolean case1Possible = (2 * a > b);
        
        int perimeter;
        if (case1Possible && RANDOM.nextBoolean()) {
            perimeter = 2 * a + b;
        } else {
            perimeter = 2 * b + a;
        }
        
        Question q = new Question();
        q.setQuestion("The lengths of two sides of an isosceles triangle are " + a + " cm and " + b + " cm. What is the perimeter of the triangle?");
        q.setAnswer(perimeter + " cm");
        
        List<String> options = new ArrayList<>();
        options.add(perimeter + " cm");
        if (case1Possible && perimeter != 2 * a + b) options.add((2 * a + b) + " cm");
        else options.add((perimeter + 5) + " cm");
        options.add((2 * b + a) + " cm");
        options.add("Cannot be determined");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateIsoscelesRatio() {
        int multiplier = 2 + RANDOM.nextInt(4);
        // Case: Two equal angles are 'multiplier' times the third angle
        // 2mx + x = 180 => (2m + 1)x = 180
        // Find x that makes 180 divisible or just use float
        float third = 180.0f / (2 * multiplier + 1);
        int a3 = Math.round(third);
        int a1 = Math.round(multiplier * third);
        int a2 = 180 - a1 - a3;
        
        Question q = new Question();
        q.setQuestion("Each of the two equal angles of an isosceles triangle is " + multiplier + " times the third angle. Find the angles of the triangle.");
        String answer = a1 + "°, " + a2 + "° and " + a3 + "°";
        q.setAnswer(answer);
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(a3 + "°, " + a3 + "° and " + a1 + "°");
        options.add("60°, 60° and 60°");
        options.add((a1-10) + "°, " + (a2-10) + "° and " + (a3+20) + "°");
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        return q;
    }

    private static Question generateOneAngleEqualsSum() {
        Question q = new Question();
        q.setQuestion("If one angle of a triangle is equal to the sum of other two, then the measure of that angle is ________");
        q.setAnswer("90°");
        OptionUtils.setQuestionOptions(q, new String[]{"45°", "60°", "120°", "90°"});
        return q;
    }

    private static Question generateSideSumPropertyGE() {
        Question q = new Question();
        q.setQuestion("TRUE or FALSE. Sum of two sides of a triangle is greater than or equal to the third side.");
        q.setAnswer("FALSE");
        OptionUtils.setQuestionOptions(q, new String[]{"TRUE", "FALSE"});
        return q;
    }

    private static Question generateExteriorAnglePuzzle() {
        // Logic:
        // Angle D = d
        // Angle B = b
        // Angle CAB = a1
        // x = angle DAC
        // In triangle ABC, exterior angle ACD = angle CAB + angle B = a1 + b
        // In triangle ADC, exterior angle angle ACD = angle DAC + angle D = x + d? No.
        // Wait, ACD is interior to triangle ADC.
        // Angle sum of triangle ADC: angle DAC + angle D + angle ACD = 180
        // Angle sum of triangle ADB: angle D + angle B + angle DAB = 180
        // angle DAB = angle DAC + angle CAB = x + a1
        // So: d + b + (x + a1) = 180
        // x = 180 - d - b - a1

        int d = 30 + RANDOM.nextInt(40); // 30 to 70
        int b = 25 + RANDOM.nextInt(40); // 25 to 65
        int a1 = 15 + RANDOM.nextInt(40); // 15 to 55
        
        int x = 180 - d - b - a1;
        if (x <= 10) return generateExteriorAnglePuzzle(); // retry if too small

        String questionText = "In the figure given below, the value of x is ______";
        String answer = x + "°";
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((x + 10) + "°");
        options.add((x - 10) + "°");
        options.add((180 - x) + "°");
        Collections.shuffle(options);

        Question q = new Question();
        q.setQuestion(questionText);
        q.setAnswer(answer);
        OptionUtils.setQuestionOptions(q, options);
        // TRIANGLE_EXTERIOR_PUZZLE_D=60_B=35_A1=25
        q.setImage("TRIANGLE_EXTERIOR_PUZZLE_D=" + d + "_B=" + b + "_A1=" + a1);
        return q;
    }

    private static Question generateCircleTriangleQuestion() {
        // Central angle A
        int angleA = 40 + RANDOM.nextInt(101); // 40 to 140
        while (angleA % 2 != 0) angleA++; // Ensure even for whole number base angles
        
        int baseAngle = (180 - angleA) / 2;
        
        Question q = new Question();
        q.setQuestion("Find ∠B and ∠C, if A is the centre of the circle.");
        String answer = "∠B = " + baseAngle + "°, ∠C = " + baseAngle + "°";
        q.setAnswer(answer);
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("∠B = " + (baseAngle + 10) + "°, ∠C = " + (baseAngle - 10) + "°");
        options.add("∠B = " + (angleA) + "°, ∠C = " + (baseAngle) + "°");
        options.add("∠B = 60°, ∠C = 60°");
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(q, options);
        
        // TRIANGLE_CIRCLE_PUZZLE_A=120
        q.setImage("TRIANGLE_CIRCLE_PUZZLE_A=" + angleA);
        return q;
    }
}
