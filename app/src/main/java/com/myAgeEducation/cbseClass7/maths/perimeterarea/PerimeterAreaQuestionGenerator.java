package com.myAgeEducation.cbseClass7.maths.perimeterarea;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.ImageCodeType;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PerimeterAreaQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(27);
        PerimeterAreaQuestionData data;
        switch (type) {
            case 1: data = generatePerimeterSquareQuestion(); break;
            case 2: data = generatePerimeterRectangleQuestion(); break;
            case 3: data = generateAreaComparisonQuestion(); break;
            case 4: data = generatePerimeterComparisonQuestion(); break;
            case 5: data = generateTileCoveringQuestion(); break;
            case 6: data = generateGridAreaComparisonQuestion(); break;
            case 7: data = generateSameAreaComparisonQuestion(); break;
            case 8: data = generateGridMultiShapeQuiz(); break;
            case 9: data = generateAreaLogicComparisonQuestion(); break;
            case 10: data = generateVolumeCubeQuestion(); break;
            case 11: data = generateVolumeCuboidQuestion(); break;
            case 12: data = generateGridDesignAreaQuestion(); break;
            case 13: data = generatePerimeterFromShapeQuestion(); break;
            case 14: data = generateMissingSideFromPerimeterQuestion(); break;
            case 15: data = generateFramingComparisonQuestion(); break;
            case 16: data = generateFramingCostQuestion(); break;
            case 17: data = generatePerimeterFormulaQuestion(); break;
            case 18: data = generatePerimeterAreaRelationQuestion(); break;
            case 19: data = generateWallPaintingDoorQuestion(); break;
            case 20: data = generateFindWidthPerimeterFromAreaQuestion(); break;
            case 21: data = generateFencingCostThreeSidesQuestion(); break;
            case 22: data = generateRebentWireQuestion(); break;
            case 23: data = generateEqualAreaFindLengthQuestion(); break;
            case 24: data = generateLandAreaCostQuestion(); break;
            case 25: data = generateSquareParkAreaFromPerimeterQuestion(); break;
            case 26: data = generateFindBreadthAreaFromPerimeterQuestion(); break;
            default: data = generateConceptQuestion();
        }
        return convertToQuestion(data);
    }

    private static PerimeterAreaQuestionData generatePerimeterFromShapeQuestion() {
        return generatePerimeterShapeLogic(false);
    }

    private static PerimeterAreaQuestionData generateMissingSideFromPerimeterQuestion() {
        return generatePerimeterShapeLogic(true);
    }

    private static PerimeterAreaQuestionData generatePerimeterShapeLogic(boolean findMissing) {
        int shapeType = RANDOM.nextInt(4);
        String vertices;
        int[] sideLengths;
        String unit = "cm";

        switch (shapeType) {
            case 0: // Triangle
                vertices = "100,400|300,100|500,400";
                sideLengths = new int[]{10 + RANDOM.nextInt(10), 10 + RANDOM.nextInt(10), 10 + RANDOM.nextInt(10)};
                break;
            case 1: // Rectangle
                vertices = "100,100|500,100|500,300|100,300";
                int l = 15 + RANDOM.nextInt(10);
                int w = 8 + RANDOM.nextInt(6);
                sideLengths = new int[]{l, w, l, w};
                break;
            case 2: // User Shape (6 sides)
                vertices = "100,200|200,100|400,100|500,200|500,400|100,400";
                int top = 8 + RANDOM.nextInt(5);
                int slant = 5 + RANDOM.nextInt(5);
                int side = 10 + RANDOM.nextInt(10);
                int bottom = top + 2 * 3; // heuristic for drawing
                sideLengths = new int[]{slant, top, slant, side, bottom, side};
                break;
            default: // Regular Pentagon (roughly)
                vertices = "300,100|500,250|450,450|150,450|100,250";
                int s = 5 + RANDOM.nextInt(10);
                sideLengths = new int[]{s, s, s, s, s};
                break;
        }

        int perimeter = 0;
        for (int len : sideLengths) perimeter += len;

        StringBuilder labels = new StringBuilder();
        int missingIdx = findMissing ? RANDOM.nextInt(sideLengths.length) : -1;
        String answer = "";

        for (int i = 0; i < sideLengths.length; i++) {
            if (i == missingIdx) {
                labels.append("x");
                answer = String.valueOf(sideLengths[i]);
            } else {
                labels.append(sideLengths[i]).append(" ").append(unit);
            }
            if (i < sideLengths.length - 1) labels.append("|");
        }

        String question;
        if (findMissing) {
            question = "The perimeter of the shape given below is " + perimeter + " " + unit + ". Find the value of the missing side 'x'.";
        } else {
            question = "Find the perimeter of the shape given in the image below.";
            answer = perimeter + " " + unit;
        }

        List<String> options = new ArrayList<>();
        options.add(answer);
        int ansVal = Integer.parseInt(answer.split(" ")[0]);
        while(options.size() < 4) {
            int off = (RANDOM.nextInt(10) + 1) * (RANDOM.nextBoolean() ? 1 : -1);
            String opt = (ansVal + off) + (findMissing ? "" : " " + unit);
            if (!options.contains(opt)) options.add(opt);
        }
        Collections.shuffle(options);

        PerimeterAreaQuestionData qData = new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), findMissing ? PerimeterAreaQuestionType.MISSING_SIDE_FROM_PERIMETER : PerimeterAreaQuestionType.PERIMETER_FROM_SHAPE);
        qData.setImageData(ImageCodeType.PERIMETER_SHAPE + "_VERTICES=" + vertices + "_LABELS=" + labels.toString());
        return qData;
    }

    private static PerimeterAreaQuestionData generateGridDesignAreaQuestion() {
        int designType = RANDOM.nextInt(4);
        int cols = 7, rows = 6;
        String imageCodePrefix = "TILE-COVERING_COLS=" + cols + "_ROWS=" + rows + "_DATA=";
        StringBuilder data = new StringBuilder();
        double area = 0;
        String color = "#E65100"; // Orange-ish like image 1

        switch (designType) {
            case 0: // Hollow Rectangle (Image 1 style)
            {
                int w = 3 + RANDOM.nextInt(3); // 3-5
                int h = 3 + RANDOM.nextInt(3); // 3-5
                int startC = 2, startR = 2;
                data.append("5,").append(color).append(",");
                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        if (i == 0 || i == w - 1 || j == 0 || j == h - 1) {
                            data.append(startC + i).append(",").append(startR + j).append(",");
                            area++;
                        }
                    }
                }
                data.deleteCharAt(data.length() - 1);
                break;
            }
            case 1: // Polygon with half squares (Image 2 style)
            {
                // Simple octagon-ish: rectangle 3x3 with 4 corner half-squares removed
                // Actually easier to just draw a 3x3 rectangle and subtract corners
                // OR add 5 full squares and 4 half squares
                // Center (2,2) 1x1, mid-edges (2,1),(1,2),(3,2),(2,3) 1x1, corners (1,1),(3,1),(1,3),(3,3) 0.5x0.5
                data.append("0,3,3,1,1,").append(color).append("|"); // center
                data.append("0,3,2,1,1,").append(color).append("|"); // top
                data.append("0,3,4,1,1,").append(color).append("|"); // bottom
                data.append("0,2,3,1,1,").append(color).append("|"); // left
                data.append("0,4,3,1,1,").append(color).append("|"); // right
                
                data.append("1,2,2,1,1,").append(color).append("|"); // TL (Type 1: TR half - wait, let's check types)
                // Type 1: TR half (TL, TR, BR)
                // Type 2: TL half (TL, TR, BL)
                // Type 3: BL half (TL, BL, BR)
                // Type 4: BR half (TR, BR, BL)
                // Corner TL: need type 1
                // Corner TR: need type 2
                // Corner BL: need type 4
                // Corner BR: need type 3
                data.append("1,2,2,1,1,").append(color).append("|");
                data.append("2,4,2,1,1,").append(color).append("|");
                data.append("4,2,4,1,1,").append(color).append("|");
                data.append("3,4,4,1,1,").append(color);
                area = 5 + 4 * 0.5;
                break;
            }
            case 2: // Cutout shape (Image 3 style)
            {
                // Rect 5x3 with some parts removed
                int startC = 2, startR = 2;
                data.append("5,").append(color).append(",");
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (i == 2 && j == 0) continue; // remove top center
                        data.append(startC + i).append(",").append(startR + j).append(",");
                        area++;
                    }
                }
                data.deleteCharAt(data.length() - 1);
                // Add some triangles at edges
                data.append("|1,1,2,1,1,").append(color).append("|2,7,2,1,1,").append(color);
                area += 1.0;
                break;
            }
            default: // Staircase (Image 4 style)
            {
                color = "#FFB300"; // Yellow-ish
                int startC = 2, startR = 1;
                data.append("5,").append(color).append(",");
                for (int i = 0; i < 5; i++) {
                    int height = (i < 4) ? (i + 1) : 2;
                    for (int j = 0; j < height; j++) {
                        data.append(startC + i).append(",").append(rows - j).append(",");
                        area++;
                    }
                }
                data.deleteCharAt(data.length() - 1);
                break;
            }
        }

        String question = "How much area does the design in the picture cover?";
        String ansStr = (area == (int)area) ? String.valueOf((int)area) : String.valueOf(area);
        String answer = ansStr + " sq units";

        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(((int)area + 5) + " sq units");
        options.add(((int)area - 2) + " sq units");
        options.add(((int)area + 2) + " sq units");
        Collections.shuffle(options);

        PerimeterAreaQuestionData qData = new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.GRID_DESIGN_AREA);
        qData.setImageData(imageCodePrefix + data.toString());
        return qData;
    }

    private static PerimeterAreaQuestionData generateConceptQuestion() {
        String[][] concepts = {
            {"The distance around the edge of a figure is its _________", "perimeter", "area", "volume", "length"},
            {"_______ is the amount of surface a figure covers", "area", "perimeter", "boundary", "edge"},
            {"Perimeter of a square = 4 x _________", "side", "area", "length", "width"},
            {"Perimeter of a rectangle = 2 x (length + _________)", "width", "side", "area", "height"},
            {"A _________ is a rectangle with all four sides equal.", "square", "triangle", "circle", "oval"},
            {"To find the perimeter of any figure, we _________ the lengths of all its sides.", "add", "subtract", "multiply", "divide"},
            {"A square has _________ equal sides.", "four", "three", "two", "five"},
            {"In a rectangle, the _________ sides are equal.", "opposite", "all", "adjacent", "no"},
            {"Area is measured in _________ units.", "square", "linear", "cubic", "circular"},
            {"Perimeter is measured in _________ units.", "linear", "square", "cubic", "circular"},
            {"The perimeter of a triangle with sides 3cm, 4cm, and 5cm is _________.", "12 cm", "7 cm", "9 cm", "60 cm"},
            {"If the perimeter of a square is 20 cm, the length of each side is _________.", "5 cm", "4 cm", "10 cm", "80 cm"},
            {"A closed figure made of three line segments is called a _________.", "triangle", "square", "rectangle", "circle"},
            {"Perimeter is a _________ dimensional measurement.", "one", "two", "three", "zero"},
            {"Area is a _________ dimensional measurement.", "two", "one", "three", "four"},
            {"The unit 'sq. cm' is used to measure _________.", "area", "perimeter", "length", "weight"},
            {"We need _________ measurements to find the area of a rectangle.", "two", "one", "three", "four"},
            {"If two figures have the same shape and size, they have _________ area.", "equal", "unequal", "double", "half"},
            {"A _________ does not have a perimeter made of line segments.", "circle", "square", "rectangle", "triangle"},
            {"To find the perimeter of a rectangle, we need its length and _________.", "width", "area", "side", "diagonal"},
            {"Area of a square with side 1 cm is _________.", "1 sq. cm", "1 cm", "4 cm", "4 sq. cm"},
            {"The perimeter of a regular pentagon with side 5 cm is _________.", "25 cm", "20 cm", "30 cm", "5 cm"},
            {"Area of a rectangle = length x _________.", "width", "perimeter", "side", "boundary"},
            {"Standard unit of area is _________.", "square units", "linear units", "meters", "litres"},
            {"A square of side 2 cm has an area of _________.", "4 sq. cm", "4 cm", "8 cm", "2 sq. cm"},
            {"Perimeter of a triangle is the _________ of its three sides.", "sum", "product", "difference", "quotient"},
            {"We use a _________ to measure the length of a small line segment.", "ruler", "weighing scale", "clock", "thermometer"},
            {"Area of a figure _________ if we change its position.", "remains same", "increases", "decreases", "doubles"},
            {"A _________ is a simple closed figure.", "polygon", "open curve", "line", "point"},
            {"Which of these has a larger area?", "A classroom", "A pencil box", "A notebook", "A sharpener"},
            {"Perimeter of a square with side 's' is _________.", "4s", "s x s", "2s", "s + 4"},
            {"To fence a park, we need to know its _________.", "perimeter", "area", "volume", "weight"},
            {"To carpet a room, we need to know its _________.", "area", "perimeter", "height", "length"},
            {"Volume of a cube with side 's' is _________.", "s x s x s", "s x s", "4s", "s + s + s"},
            {"Standard unit of volume is _________.", "cubic units", "square units", "linear units", "kilograms"},
            {"A cuboid has _________ dimensions.", "three", "two", "one", "four"},
            {"The amount of space occupied by a solid is called its _________.", "volume", "area", "perimeter", "surface area"}
        };
        int idx = RANDOM.nextInt(concepts.length);
        String[] item = concepts[idx];
        String[] options = Arrays.copyOfRange(item, 1, 5);
        List<String> optList = Arrays.asList(options);
        Collections.shuffle(optList);
        return new PerimeterAreaQuestionData(item[0], item[1], optList.toArray(new String[0]), PerimeterAreaQuestionType.CONCEPT);
    }

    private static PerimeterAreaQuestionData generatePerimeterSquareQuestion() {
        int side = RANDOM.nextInt(15) + 2;
        String q = String.format("The length of one side of a square is %d cm. What is the perimeter of the square?", side);
        int perimeter = 4 * side;
        String ans = perimeter + " cm";
        List<String> options = new ArrayList<>();
        options.add(ans);
        options.add((perimeter + 4) + " cm");
        options.add((side * side) + " cm");
        options.add((perimeter - 4) + " cm");
        Collections.shuffle(options);
        return new PerimeterAreaQuestionData(q, ans, options.toArray(new String[0]), PerimeterAreaQuestionType.PERIMETER_SQUARE);
    }

    private static PerimeterAreaQuestionData generatePerimeterRectangleQuestion() {
        int length = RANDOM.nextInt(15) + 5;
        int width = RANDOM.nextInt(length - 2) + 2;
        String unit = RANDOM.nextBoolean() ? "cm" : "inches";
        String q = String.format("A rectangle has a length of %d %s and a width of %d %s. What is the perimeter of the rectangle?", length, unit, width, unit);
        int perimeter = 2 * (length + width);
        String ans = perimeter + " " + unit;
        List<String> options = new ArrayList<>();
        options.add(ans);
        options.add((length + width) + " " + unit);
        options.add((length * width) + " " + unit);
        options.add((perimeter + 2) + " " + unit);
        Collections.shuffle(options);
        return new PerimeterAreaQuestionData(q, ans, options.toArray(new String[0]), PerimeterAreaQuestionType.PERIMETER_RECTANGLE);
    }

    private static PerimeterAreaQuestionData generateAreaComparisonQuestion() {
        String[][] items = {
                {"a football ground", "10000000"},
                {"a mobile phone", "50"},
                {"a 55 inch LED TV", "5000"},
                {"a study table", "10000"},
                {"a postage stamp", "5"},
                {"a classroom door", "20000"},
                {"a city park", "50000000"},
                {"a math textbook", "400"},
                {"a currency note", "100"},
                {"a cricket stadium", "15000000"},
                {"a handkerchief", "600"},
                {"a blackboard", "15000"},
                {"a credit card", "40"},
                {"a pillow", "1200"},
                {"a mouse pad", "300"},
                {"a coin", "4"},
                {"a laptop keyboard", "250"},
                {"a single bed sheet", "30000"},
                {"a swimming pool", "500000"},
                {"an airport", "200000000"},
                {"a fingernail", "1"},
                {"a wall calendar", "1500"},
                {"a computer monitor", "1000"},
                {"a standard brick", "150"},
                {"a large dining table", "30000"},
                {"a state", "5000000000000"},
                {"a country", "50000000000000"},
                {"a continent", "500000000000000"},
                {"a classroom floor", "600000"},
                {"a kitchen sponge", "60"},
                {"a door mat", "2400"},
                {"a towel", "8000"}
        };

        boolean findMax = RANDOM.nextBoolean();
        List<Integer> allIndices = new ArrayList<>();
        for (int i = 0; i < items.length; i++) allIndices.add(i);
        Collections.shuffle(allIndices);

        String[] options = new String[4];
        int targetIdx = -1;
        long targetArea = findMax ? -1 : Long.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            int idx = allIndices.get(i);
            options[i] = items[idx][0];
            long area = Long.parseLong(items[idx][1]);
            
            if (findMax) {
                if (area > targetArea) {
                    targetArea = area;
                    targetIdx = i;
                }
            } else {
                if (area < targetArea) {
                    targetArea = area;
                    targetIdx = i;
                }
            }
        }

        String q = findMax ? 
                "Which one of the following occupies maximum area?" : 
                "Which one of the following occupies minimum area?";
        String ans = options[targetIdx];
        return new PerimeterAreaQuestionData(q, ans, options, PerimeterAreaQuestionType.AREA_COMPARISON);
    }

    private static PerimeterAreaQuestionData generatePerimeterComparisonQuestion() {
        boolean max = RANDOM.nextBoolean();
        String q = max ? "Which one of the following has maximum perimeter?" : "Which one of the following has minimum perimeter?";
        
        // Use rectangles with different perimeters
        int[][] rects = new int[4][2];
        Set<Integer> perimeters = new HashSet<>();
        while(perimeters.size() < 4) {
            int l = RANDOM.nextInt(20) + 5;
            int w = RANDOM.nextInt(20) + 5;
            int p = 2 * (l + w);
            if (!perimeters.contains(p)) {
                rects[perimeters.size()][0] = l;
                rects[perimeters.size()][1] = w;
                perimeters.add(p);
            }
        }
        
        String[] options = new String[4];
        int targetIdx = -1;
        int targetVal = max ? -1 : 10000;
        
        for (int i = 0; i < 4; i++) {
            int l = rects[i][0];
            int w = rects[i][1];
            int p = 2 * (l + w);
            options[i] = String.format("Rectangle (L:%d, W:%d)", l, w);
            if (max) {
                if (p > targetVal) {
                    targetVal = p;
                    targetIdx = i;
                }
            } else {
                if (p < targetVal) {
                    targetVal = p;
                    targetIdx = i;
                }
            }
        }
        
        return new PerimeterAreaQuestionData(q, options[targetIdx], options, PerimeterAreaQuestionType.PERIMETER_COMPARISON);
    }

    private static PerimeterAreaQuestionData generateTileCoveringQuestion() {
        int cols = 8 + RANDOM.nextInt(5); // 8-12
        int rows = 6 + RANDOM.nextInt(3); // 6-8
        int totalArea = cols * rows;

        int redSize = 2 + RANDOM.nextInt(2); // 2x2 or 3x3
        double redArea = 0.5 * redSize * redSize;
        
        // Data format: TYPE,COL,ROW,W,H,COLOR
        String blueData = "0,2,2,1,1,#2196F3";
        String redData = "1,4,2," + redSize + "," + redSize + ",#F44336";
        String greenData = "3,4," + rows + ",1,1,#4CAF50";

        String imageCode = "TILE-COVERING_COLS=" + cols + "_ROWS=" + rows + "_DATA=" + blueData + "|" + redData + "|" + greenData;

        int questionSubtype = RANDOM.nextInt(3);
        String question;
        String answer;
        double tileArea;
        String shapeName;

        switch (questionSubtype) {
            case 0:
                shapeName = "Green triangles";
                tileArea = 0.5;
                break;

            case 1:
                shapeName = "Red triangles";
                tileArea = redArea;
                break;

            default:
                shapeName = "Blue squares";
                tileArea = 1.0;
                break;
        }

        if(RANDOM.nextBoolean()) {
            question = PersonNameUtil.getFemaleName() +
            " is playing with tiles. She covers her desk with different shapes as shown below. " +
                    "Look at the different tiles on her desk and answer how many of the following shapes will cover the desk: " + shapeName;
        }
        else {
            question = PersonNameUtil.getMaleName() + " is playing with tiles. He covers his desk with different shapes as shown below. " +
                    "Look at the different tiles on his desk and answer how many of the following shapes will cover the desk: " + shapeName;
        }

        int numTiles = (int) Math.round(totalArea / tileArea);
        answer = String.valueOf(numTiles);

        String[] options = new String[4];
        options[0] = answer;
        options[1] = String.valueOf(numTiles + 5);
        options[2] = String.valueOf(numTiles / 2);
        options[3] = String.valueOf(numTiles + 10);
        
        List<String> optionList = new ArrayList<>(Arrays.asList(options));
        Collections.shuffle(optionList);
        options = optionList.toArray(new String[0]);

        PerimeterAreaQuestionData data = new PerimeterAreaQuestionData(question, answer, options, PerimeterAreaQuestionType.TILE_COVERING);
        data.setImageData(imageCode);
        return data;
    }

    private static PerimeterAreaQuestionData generateGridAreaComparisonQuestion() {
        int cols = 12;
        int rows = 6;

        int wA, hA, areaA, wB, hB, areaB, wC, hC, areaC;

        // Ensure all areas are distinct to avoid multiple correct answers
        while (true) {
            wA = 2 + RANDOM.nextInt(3); // 2-4
            hA = 2 + RANDOM.nextInt(4); // 2-5
            areaA = wA * hA;

            wB = 2 + RANDOM.nextInt(3);
            hB = 2 + RANDOM.nextInt(4);
            areaB = wB * hB;

            wC = 2 + RANDOM.nextInt(3);
            hC = 2 + RANDOM.nextInt(4);
            areaC = wC * hC;

            if (areaA != areaB && areaA != areaC && areaB != areaC) {
                break;
            }
        }

        // Colors
        String[] colors = {"#FFEB3B", "#F8BBD0", "#B39DDB", "#81C784", "#4FC3F7"};
        List<String> colorList = new ArrayList<>(Arrays.asList(colors));
        Collections.shuffle(colorList);

        String dataA = "0,1,1," + wA + "," + hA + "," + colorList.get(0) + ",A";
        String dataB = "0,5,1," + wB + "," + hB + "," + colorList.get(1) + ",B";
        String dataC = "0,9,1," + wC + "," + hC + "," + colorList.get(2) + ",C";

        String imageCode = "TILE-COVERING_COLS=" + cols + "_ROWS=" + rows + "_DATA=" + dataA + "|" + dataB + "|" + dataC;

        boolean findMax = RANDOM.nextBoolean();
        String question = findMax ? "Which of the rectangles shown below has the largest area?" : "Which of the rectangles shown below has the smallest area?";
        
        String answer;
        if (findMax) {
            if (areaA >= areaB && areaA >= areaC) answer = "Rectangle A";
            else if (areaB >= areaA && areaB >= areaC) answer = "Rectangle B";
            else answer = "Rectangle C";
        } else {
            if (areaA <= areaB && areaA <= areaC) answer = "Rectangle A";
            else if (areaB <= areaA && areaB <= areaC) answer = "Rectangle B";
            else answer = "Rectangle C";
        }

        String[] options = {"Rectangle A", "Rectangle B", "Rectangle C", "All have equal area"};
        
        PerimeterAreaQuestionData data = new PerimeterAreaQuestionData(question, answer, options, PerimeterAreaQuestionType.GRID_AREA_COMPARISON);
        data.setImageData(imageCode);
        return data;
    }

    private static PerimeterAreaQuestionData generateSameAreaComparisonQuestion() {
        int cols = 14;
        int rows = 9;

        int wC, hC, wD, hD, wE, hE, wF, hF;
        int areaC, areaD, areaE, areaF;

        // Exactly two must have same area
        while (true) {
            wC = 2 + RANDOM.nextInt(3); hC = 3 + RANDOM.nextInt(3); areaC = wC * hC;
            wD = 3 + RANDOM.nextInt(3); hD = 2 + RANDOM.nextInt(3); areaD = wD * hD;
            wE = 4 + RANDOM.nextInt(3); hE = 1 + RANDOM.nextInt(2); areaE = wE * hE;
            wF = 5 + RANDOM.nextInt(2); hF = 1; areaF = wF * hF;

            int[] areas = {areaC, areaD, areaE, areaF};
            Set<Integer> uniqueAreas = new HashSet<>();
            int sameCount = 0;
            for (int a : areas) {
                if (!uniqueAreas.add(a)) sameCount++;
            }

            if (sameCount == 1) { // Exactly one pair matches
                break;
            }
        }

        String color = "#C5E1A5"; // Light green like the image
        // Placed with more horizontal gap and ensuring they fit within boundaries
        // cols=14, rows=9.
        String dataC = "0,2,1," + wC + "," + hC + "," + color + ",(c)";
        String dataD = "0,2,6," + wD + "," + hD + "," + color + ",(d)";
        String dataE = "0,8,1," + wE + "," + hE + "," + color + ",(e)";
        String dataF = "0,8,6," + wF + "," + hF + "," + color + ",(f)";

        String imageCode = "TILE-COVERING_COLS=" + cols + "_ROWS=" + rows + "_DATA=" + dataC + "|" + dataD + "|" + dataE + "|" + dataF;

        String pair = "";
        if (areaC == areaD) pair = "(c) and (d)";
        else if (areaC == areaE) pair = "(c) and (e)";
        else if (areaC == areaF) pair = "(c) and (f)";
        else if (areaD == areaE) pair = "(d) and (e)";
        else if (areaD == areaF) pair = "(d) and (f)";
        else if (areaE == areaF) pair = "(e) and (f)";

        String question = "Which of the 2 shapes shown below have the same area?";
        String answer = pair;
        
        // Generate options
        List<String> options = new ArrayList<>();
        options.add("(c) and (d)");
        options.add("(c) and (e)");
        options.add("(d) and (e)");
        options.add("(e) and (f)");
        options.add("(c) and (f)");
        options.add("(d) and (f)");
        
        List<String> selectedOptions = new ArrayList<>();
        selectedOptions.add(answer);
        Collections.shuffle(options);
        for(String opt : options) {
            if(!selectedOptions.contains(opt)) selectedOptions.add(opt);
            if(selectedOptions.size() == 4) break;
        }
        Collections.shuffle(selectedOptions);

        PerimeterAreaQuestionData data = new PerimeterAreaQuestionData(question, answer, selectedOptions.toArray(new String[0]), PerimeterAreaQuestionType.SAME_AREA_COMPARISON);
        data.setImageData(imageCode);
        return data;
    }

    private static PerimeterAreaQuestionData generateGridMultiShapeQuiz() {
        int cols = 20;
        int rows = 12;

        // Shape definitions (relative to its own top-left 1,1)
        int[][][] templates = {
            {{1,1}, {2,1}, {3,1}, {1,2}, {2,2}}, // 2x3 block minus one
            {{1,1}, {2,1}, {2,2}, {3,2}, {2,3}}, // T-ish shape
            {{1,1}, {1,2}, {1,3}, {1,4}, {2,4}, {3,4}}, // L shape
            {{2,1}, {1,2}, {2,2}, {3,2}, {2,3}}, // Plus shape
            {{1,1}, {2,1}, {3,1}, {4,1}, {5,1}, {6,1}}, // Long strip
            {{1,1}, {2,1}, {1,2}, {1,3}, {2,3}}, // C shape
            {{1,1}, {2,1}, {3,1}, {2,2}, {1,3}, {2,3}, {3,3}}, // I shape
            {{1,1}, {2,1}, {1,2}, {2,2}, {2,3}, {3,3}}  // Z shape
        };

        String[] labels = {"(a)", "(b)", "(c)", "(d)", "(e)"};
        int numShapes = 5;
        
        List<GridShape> shapes = new ArrayList<>();
        boolean[][] occupied = new boolean[cols + 2][rows + 2]; // Extra padding

        for (int i = 0; i < numShapes; i++) {
            int templateIdx = RANDOM.nextInt(templates.length);
            int[][] template = templates[templateIdx];
            
            int attempts = 0;
            while (attempts < 100) {
                int startCol = 1 + RANDOM.nextInt(cols - 6);
                int startRow = 1 + RANDOM.nextInt(rows - 6);
                
                boolean canPlace = true;
                for (int[] cell : template) {
                    int c = startCol + cell[0] - 1;
                    int r = startRow + cell[1] - 1;
                    // Check self and surroundings for 1-cell gap
                    if (c > cols || r > rows || occupied[c][r] || 
                        occupied[c+1][r] || occupied[c-1][r] || 
                        occupied[c][r+1] || occupied[c][r-1]) {
                        canPlace = false;
                        break;
                    }
                }
                
                if (canPlace) {
                    GridShape s = new GridShape();
                    s.label = labels[i];
                    s.cells = new ArrayList<>();
                    for (int[] cell : template) {
                        int c = startCol + cell[0] - 1;
                        int r = startRow + cell[1] - 1;
                        s.cells.add(new int[]{c, r});
                        occupied[c][r] = true;
                    }
                    s.calculateProperties();
                    shapes.add(s);
                    break;
                }
                attempts++;
            }
        }

        int qType = RANDOM.nextInt(7);
        String question = "";
        String answer = "";
        List<String> options = new ArrayList<>();

        switch (qType) {
            case 0: // Area of specific shape
            {
                GridShape s = shapes.get(RANDOM.nextInt(shapes.size()));
                question = "Considering each square is of size 1 cm in width and height, what is the area of shape " + s.label + "?";
                answer = s.area + " sq cm";
                options.add((s.area + 1) + " sq cm");
                options.add((s.area - 1) + " sq cm");
                options.add((s.area + 2) + " sq cm");
                break;
            }
            case 1: // Perimeter of specific shape
            {
                GridShape s = shapes.get(RANDOM.nextInt(shapes.size()));
                question = "Considering each square is of size 1 cm in width and height, what is the perimeter of shape " + s.label + "?";
                answer = s.perimeter + " cm";
                options.add((s.perimeter + 2) + " cm");
                options.add((s.perimeter - 2) + " cm");
                options.add((s.perimeter + 4) + " cm");
                break;
            }
            case 2: // Same area
            {
                GridShape s1 = null, s2 = null;
                for (int i = 0; i < shapes.size(); i++) {
                    for (int j = i + 1; j < shapes.size(); j++) {
                        if (shapes.get(i).area == shapes.get(j).area) {
                            s1 = shapes.get(i); s2 = shapes.get(j); break;
                        }
                    }
                }
                if (s1 != null) {
                    question = "Which 2 shapes have the same area?";
                    answer = s1.label + " and " + s2.label;
                    options.add(shapes.get(0).label + " and " + shapes.get(1).label);
                    options.add(shapes.get(1).label + " and " + shapes.get(2).label);
                    options.add(shapes.get(2).label + " and " + shapes.get(3).label);
                } else {
                    return generateGridMultiShapeQuiz();
                }
                break;
            }
            case 3: // Same perimeter
            {
                GridShape s1 = null, s2 = null;
                for (int i = 0; i < shapes.size(); i++) {
                    for (int j = i + 1; j < shapes.size(); j++) {
                        if (shapes.get(i).perimeter == shapes.get(j).perimeter) {
                            s1 = shapes.get(i); s2 = shapes.get(j); break;
                        }
                    }
                }
                if (s1 != null) {
                    question = "Which 2 shapes have the same perimeter?";
                    answer = s1.label + " and " + s2.label;
                    options.add(shapes.get(0).label + " and " + shapes.get(1).label);
                    options.add(shapes.get(1).label + " and " + shapes.get(2).label);
                    options.add(shapes.get(2).label + " and " + shapes.get(3).label);
                } else {
                    return generateGridMultiShapeQuiz();
                }
                break;
            }
            case 4: // Sum of 2 areas
            {
                int i1 = RANDOM.nextInt(shapes.size());
                int i2 = RANDOM.nextInt(shapes.size());
                while (i1 == i2) i2 = RANDOM.nextInt(shapes.size());
                GridShape s1 = shapes.get(i1);
                GridShape s2 = shapes.get(i2);
                question = "What is the sum of areas of shape " + s1.label + " and " + s2.label + "?";
                int sum = s1.area + s2.area;
                answer = sum + " sq cm";
                options.add((sum + 2) + " sq cm");
                options.add((sum - 1) + " sq cm");
                options.add((sum + 5) + " sq cm");
                break;
            }
            case 5: // Sum of 3 areas
            {
                question = "What is the sum of areas of shape " + shapes.get(0).label + ", " + shapes.get(1).label + " and " + shapes.get(2).label + "?";
                int sum = shapes.get(0).area + shapes.get(1).area + shapes.get(2).area;
                answer = sum + " sq cm";
                options.add((sum + 3) + " sq cm");
                options.add((sum - 2) + " sq cm");
                options.add((sum + 10) + " sq cm");
                break;
            }
            default: // Sum of 2 perimeters
            {
                int i1 = RANDOM.nextInt(shapes.size());
                int i2 = RANDOM.nextInt(shapes.size());
                while (i1 == i2) i2 = RANDOM.nextInt(shapes.size());
                GridShape s1 = shapes.get(i1);
                GridShape s2 = shapes.get(i2);
                question = "What is the sum of perimeters of shape " + s1.label + " and " + s2.label + "?";
                int sum = s1.perimeter + s2.perimeter;
                answer = sum + " cm";
                options.add((sum + 4) + " cm");
                options.add((sum - 2) + " cm");
                options.add((sum + 10) + " cm");
                break;
            }
        }

        StringBuilder imgCode = new StringBuilder("TILE-COVERING_COLS=" + cols + "_ROWS=" + rows + "_DATA=");
        for (int i = 0; i < shapes.size(); i++) {
            GridShape s = shapes.get(i);
            imgCode.append("5,#EC407A,").append(s.label); // Pinkish color like image
            for (int[] cell : s.cells) {
                imgCode.append(",").append(cell[0]).append(",").append(cell[1]);
            }
            if (i < shapes.size() - 1) imgCode.append("|");
        }

        List<String> optList = new ArrayList<>();
        optList.add(answer);
        for (String o : options) {
            if (!optList.contains(o)) optList.add(o);
            if (optList.size() == 4) break;
        }
        while(optList.size() < 4) optList.add(RANDOM.nextInt(50) + " sq cm");
        Collections.shuffle(optList);

        PerimeterAreaQuestionData data = new PerimeterAreaQuestionData(question, answer, optList.toArray(new String[0]), PerimeterAreaQuestionType.GRID_MULTI_SHAPE_QUIZ);
        data.setImageData(imgCode.toString());
        return data;
    }

    private static PerimeterAreaQuestionData generateAreaLogicComparisonQuestion() {
        int cols = 14;
        int rows = 7;

        // Shape (a) - Square/Rectangle
        int wA = 3 + RANDOM.nextInt(2); // 3-4
        int hA = 3 + RANDOM.nextInt(2); // 3-4
        double areaA = wA * hA;

        // Shape (b) - Triangle with base and height
        // Base at top, point at bottom center
        int wB = 4 + RANDOM.nextInt(3); // 4-6
        int hB = 4 + RANDOM.nextInt(3); // 4-6
        double areaB = 0.5 * wB * hB;

        String colorA = "#448AFF"; // Blue
        String colorB = "#FFCC80"; // Light Orange

        String dataA = "0,2,2," + wA + "," + hA + "," + colorA + ",(a)";
        String dataB = "6,8,2," + wB + "," + hB + "," + colorB + ",(b)";

        String imageCode = "TILE-COVERING_COLS=" + cols + "_ROWS=" + rows + "_DATA=" + dataA + "|" + dataB;

        boolean askLess = RANDOM.nextBoolean();
        String question = askLess ? 
            "Is the area of shape (a) less than the area of shape (b) given below?" :
            "Is the area of shape (a) more than the area of shape (b) given below?";
        
        String answer;
        if (askLess) {
            answer = (areaA < areaB) ? "YES" : "NO";
        } else {
            answer = (areaA > areaB) ? "YES" : "NO";
        }

        String[] options = {"YES", "NO", "CANNOT BE DETERMINED", "BOTH HAVE EQUAL AREA"};
        
        PerimeterAreaQuestionData data = new PerimeterAreaQuestionData(question, answer, options, PerimeterAreaQuestionType.AREA_LOGIC_COMPARISON);
        data.setImageData(imageCode);
        return data;
    }

    private static PerimeterAreaQuestionData generateVolumeCubeQuestion() {
        int side = 2 + RANDOM.nextInt(9); // 2-10 cm
        String question = String.format("What is the volume of a cubic box whose sides are %d cm long?", side);
        int volume = side * side * side;
        String answer = volume + " cubic cm";
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((side * side) + " cubic cm"); // Area distractor
        options.add((4 * side) + " cubic cm"); // Perimeter distractor
        options.add((volume + 10) + " cubic cm");
        
        Collections.shuffle(options);
        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.VOLUME_CUBE);
    }

    private static PerimeterAreaQuestionData generateVolumeCuboidQuestion() {
        int l = 3 + RANDOM.nextInt(7); // 3-9
        int b = 2 + RANDOM.nextInt(l - 1); // 2 to l-1
        int h = 2 + RANDOM.nextInt(5); // 2-6
        
        String question = String.format("Find the volume of a cuboid with length %d cm, breadth %d cm and height %d cm.", l, b, h);
        int volume = l * b * h;
        String answer = volume + " cubic cm";
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add((l + b + h) + " cubic cm");
        options.add((l * b) + " cubic cm");
        options.add((volume - 5) + " cubic cm");
        
        Collections.shuffle(options);
        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.VOLUME_CUBOID);
    }

    private static PerimeterAreaQuestionData generateFramingComparisonQuestion() {
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

        String question = String.format("%s and %s made pictures. %s made his picture on a rectangular sheet of length %d cm and breadth %d cm while %s made hers on a rectangular sheet of length %d cm and breadth %d cm. Both these pictures have to be separately framed. Who has to pay more for framing, if the cost of framing is Rs %d.00 per cm?",
                name1, name2, name1, l1, b1, name2, l2, b2, costPerCm);

        String answer = (p1 > p2) ? name1 : name2;
        String[] options = {name1, name2};

        return new PerimeterAreaQuestionData(question, answer, options, PerimeterAreaQuestionType.FRAMING_COMPARISON);
    }

    private static PerimeterAreaQuestionData generateFramingCostQuestion() {
        String name = PersonNameUtil.getOneName();
        int l = 40 + RANDOM.nextInt(30);
        int b = 20 + RANDOM.nextInt(20);
        int costPerCm = 2 + RANDOM.nextInt(4);
        int perimeter = 2 * (l + b);
        int totalCost = perimeter * costPerCm;

        String question = String.format("%s made his picture on a rectangular sheet of length %d cm and breadth %d cm. Now this picture has to be framed. How much %s has to pay if the cost of framing is Rs %d.00 per cm?",
                name, l, b, name, costPerCm);
        
        String answer = "Rs " + totalCost;
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("Rs " + (perimeter));
        options.add("Rs " + (totalCost / 2));
        options.add("Rs " + (totalCost + 100));
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.FRAMING_COST);
    }

    private static PerimeterAreaQuestionData generatePerimeterFormulaQuestion() {
        boolean square = RANDOM.nextBoolean();
        String question, answer;
        String[] options;

        if (square) {
            question = "Perimeter of a square = ______ x side";
            answer = "4";
            options = new String[]{"1", "2", "3", "4"};
        } else {
            if (RANDOM.nextBoolean()) {
                question = "Perimeter of a rectangle = _______ x (l + b)";
                answer = "2";
                options = new String[]{"1", "2", "3", "4"};
            } else {
                question = "TRUE or FALSE. Perimeter of a regular polygon = number of sides x length of one side";
                answer = "TRUE";
                options = new String[]{"TRUE", "FALSE"};
            }
        }

        return new PerimeterAreaQuestionData(question, answer, options, PerimeterAreaQuestionType.PERIMETER_FORMULA);
    }

    private static PerimeterAreaQuestionData generatePerimeterAreaRelationQuestion() {
        boolean positive = RANDOM.nextBoolean();
        String question, answer;
        if (positive) {
            question = "TRUE or FALSE. Increase of perimeter will always lead to increase in area.";
            answer = "FALSE";
        } else {
            question = "TRUE or FALSE. Increase of perimeter need not lead to increase in area.";
            answer = "TRUE";
        }
        return new PerimeterAreaQuestionData(question, answer, new String[]{"TRUE", "FALSE"}, PerimeterAreaQuestionType.PERIMETER_AREA_RELATION);
    }

    private static PerimeterAreaQuestionData generateWallPaintingDoorQuestion() {
        int wallDim = 8 + RANDOM.nextInt(5); // 8-12m
        int doorL = 2 + RANDOM.nextInt(2); // 2-3m
        int doorW = 1 + RANDOM.nextInt(2); // 1-2m
        float rate = 2.0f + 0.5f * RANDOM.nextInt(5); // 2.0 to 4.0
        
        int wallArea = wallDim * wallDim;
        int doorArea = doorL * doorW;
        int paintArea = wallArea - doorArea;
        float totalCost = paintArea * rate;

        String question = String.format("A door-frame of dimensions %d m x %d m is fixed on the wall of dimension %d m x %d m. What would be the total labour charges for painting the wall if the labour charges for painting 1 m² of the wall is Rs %.2f.",
                doorL, doorW, wallDim, wallDim, rate);
        
        String answer = "Rs " + (totalCost == (int)totalCost ? String.valueOf((int)totalCost) : String.format("%.2f", totalCost));
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("Rs " + (wallArea * rate));
        options.add("Rs " + (doorArea * rate));
        options.add("Rs " + (totalCost - 10));
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.WALL_PAINTING_DOOR);
    }

    private static PerimeterAreaQuestionData generateFindWidthPerimeterFromAreaQuestion() {
        boolean isLand = RANDOM.nextBoolean();
        String object = isLand ? "rectangular plot of land" : "rectangular sheet";
        String unit = isLand ? "m" : "cm";
        String unit2 = isLand ? "m²" : "cm²";

        int l = 10 + RANDOM.nextInt(20);
        int w = 5 + RANDOM.nextInt(15);
        int area = l * w;
        boolean findPerimeter = RANDOM.nextBoolean();

        String question;
        String answer;
        List<String> options = new ArrayList<>();

        if (findPerimeter) {
            question = String.format("The area of a %s is %d %s. If the length of the %s is %d %s, what is its perimeter?", object, area, unit2, (isLand ? "plot" : "sheet"), l, unit);
            int p = 2 * (l + w);
            answer = p + " " + unit;
            options.add(answer);
            options.add(w + " " + unit);
            options.add((p / 2) + " " + unit);
            options.add((p + 20) + " " + unit);
        } else {
            question = String.format("The area of a %s is %d %s. If the length of the %s is %d %s, what is its %s?", object, area, unit2, (isLand ? "plot" : "sheet"), l, unit, (isLand ? "breadth" : "width"));
            answer = w + " " + unit;
            options.add(answer);
            options.add((w + 5) + " " + unit);
            options.add(l + " " + unit);
            options.add(isLand ? "44 m" : "20 cm");
        }
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.FIND_WIDTH_FROM_AREA);
    }

    private static PerimeterAreaQuestionData generateFencingCostThreeSidesQuestion() {
        int s1 = 15 + RANDOM.nextInt(10);
        int s2 = 10 + RANDOM.nextInt(5);
        int s3 = s2;
        int rate = 100 + 10 * RANDOM.nextInt(10);
        int totalLen = s1 + s2 + s3;
        int totalCost = totalLen * rate;

        String question = String.format("%s wants to fence the garden in front of her house, on three sides with lengths %d m, %d m and %d m. Find the cost of fencing at the rate of Rs %d per metre.",
                PersonNameUtil.getFemaleName(), s1, s2, s3, rate);
        
        String answer = "Rs " + totalCost;
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add("Rs " + (totalLen + rate));
        options.add("Rs " + (totalCost - 200));
        options.add("Rs " + (totalCost + 400));
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.FENCING_COST_THREE_SIDES);
    }

    private static PerimeterAreaQuestionData generateRebentWireQuestion() {
        int side = 8 + RANDOM.nextInt(8); // 8-15cm
        int perimeter = 4 * side;
        int areaSquare = side * side;

        int l = side + 2 + RANDOM.nextInt(5);
        int w = (perimeter / 2) - l;
        int areaRect = l * w;

        boolean findBreadth = RANDOM.nextBoolean();
        String question, answer;
        List<String> options = new ArrayList<>();

        if (findBreadth) {
            question = String.format("A wire is in the shape of a square of side %d cm. If the wire is rebent into a rectangle of length %d cm, find its breadth.", side, l);
            answer = w + " cm";
            options.add(answer);
            options.add(side + " cm");
            options.add(l + " cm");
            options.add((w - 2) + " cm");
        } else {
            question = String.format("A wire is in the shape of a square of side %d cm. If the wire is rebent into a rectangle of length %d cm. Which encloses more area, the square or the rectangle?", side, l);
            answer = (areaSquare > areaRect) ? "The square encloses more area" : "The rectangle encloses more area";
            options.add("The square encloses more area");
            options.add("The rectangle encloses more area");
            options.add("Both encloses the same area");
            options.add("Cannot be determined");
        }
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.REBENT_WIRE_BREADTH);
    }

    private static PerimeterAreaQuestionData generateEqualAreaFindLengthQuestion() {
        int side = 10 * (2 + RANDOM.nextInt(4)); // 20, 30, 40, 50
        int area = side * side;
        int w = side - 5 - RANDOM.nextInt(10);
        while (area % w != 0) w--;
        int l = area / w;

        String question = String.format("The area of a square and a rectangle are equal. If the side of the square is %d cm and the breadth of the rectangle is %d cm, find the length of the rectangle.", side, w);
        String answer = l + " cm";
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(side + " cm");
        options.add(w + " cm");
        options.add((l + 10) + " cm");
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.EQUAL_AREA_FIND_LENGTH);
    }

    private static PerimeterAreaQuestionData generateLandAreaCostQuestion() {
        int l = 100 * (3 + RANDOM.nextInt(5)); // 300-700
        int w = 100 * (2 + RANDOM.nextInt(3)); // 200-400
        long area = (long)l * w;
        int rate = 1000 * (5 + RANDOM.nextInt(10)); // 5000-14000
        long totalCost = area * rate;

        boolean findCost = RANDOM.nextBoolean();
        String question, answer;
        List<String> options = new ArrayList<>();

        if (findCost) {
            question = String.format("The length and the breadth of a rectangular piece of land are %d m and %d m respectively. Find the cost of the land, if 1 m² of the land costs Rs %d.", l, w, rate);
            answer = "Rs " + totalCost;
            options.add(answer);
            options.add("Rs " + (totalCost / 10));
            options.add("Rs " + (totalCost * 10));
            options.add("Rs " + (area + rate));
        } else {
            question = String.format("The length and the breadth of a rectangular piece of land are %d m and %d m respectively. Find its area.", l, w);
            answer = area + " m²";
            options.add(answer);
            options.add((l + w) + " m²");
            options.add((area / 10) + " m²");
            options.add(area + " m");
        }
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.LAND_AREA_COST);
    }

    private static PerimeterAreaQuestionData generateSquareParkAreaFromPerimeterQuestion() {
        int perimeter = 40 * (4 + RANDOM.nextInt(10)); // 160-520
        int side = perimeter / 4;
        int area = side * side;

        String question = String.format("Find the area of a square park whose perimeter is %d m.", perimeter);
        String answer = area + " m²";
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(perimeter + " m²");
        options.add(side + " m²");
        options.add((area * 2) + " m²");
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), PerimeterAreaQuestionType.SQUARE_PARK_AREA_FROM_PERIMETER);
    }

    private static PerimeterAreaQuestionData generateFindBreadthAreaFromPerimeterQuestion() {
        int l = 15 + RANDOM.nextInt(25);
        int w = 10 + RANDOM.nextInt(15);
        int p = 2 * (l + w);
        int area = l * w;

        boolean findArea = RANDOM.nextBoolean();
        String question, answer;
        List<String> options = new ArrayList<>();

        if (findArea) {
            question = String.format("The perimeter of a rectangular sheet is %d cm. If the length is %d cm, what would be its area?", p, l);
            answer = area + " cm²";
            options.add(answer);
            options.add((l + w) + " cm²");
            options.add((area - 100) + " cm²");
            options.add((area + 50) + " cm²");
        } else {
            question = String.format("The perimeter of a rectangular sheet is %d cm. If the length is %d cm, what would be its breadth?", p, l);
            answer = w + " cm";
            options.add(answer);
            options.add(l + " cm");
            options.add((p / 2) + " cm");
            options.add((w + 10) + " cm");
        }
        Collections.shuffle(options);

        return new PerimeterAreaQuestionData(question, answer, options.toArray(new String[0]), findArea ? PerimeterAreaQuestionType.FIND_AREA_FROM_PERIMETER : PerimeterAreaQuestionType.FIND_BREADTH_FROM_PERIMETER);
    }

    private static class GridShape {
        String label;
        List<int[]> cells;
        int area;
        int perimeter;

        void calculateProperties() {
            area = cells.size();
            perimeter = 0;
            for (int[] cell : cells) {
                int c = cell[0];
                int r = cell[1];
                if (!hasCell(c + 1, r)) perimeter++;
                if (!hasCell(c - 1, r)) perimeter++;
                if (!hasCell(c, r + 1)) perimeter++;
                if (!hasCell(c, r - 1)) perimeter++;
            }
        }

        boolean hasCell(int c, int r) {
            for (int[] cell : cells) {
                if (cell[0] == c && cell[1] == r) return true;
            }
            return false;
        }
    }

    private static Question convertToQuestion(PerimeterAreaQuestionData data) {
        Question question = new Question();
        question.setQuestion(data.getQuestion());
        question.setAnswer(data.getAnswer());
        question.setImage(data.getImageData());
        OptionUtils.setQuestionOptions(question, data.getOptions());
        return question;
    }
}
