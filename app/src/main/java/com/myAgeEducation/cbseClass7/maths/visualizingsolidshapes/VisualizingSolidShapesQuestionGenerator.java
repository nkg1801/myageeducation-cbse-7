package com.myAgeEducation.cbseClass7.maths.visualizingsolidshapes;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VisualizingSolidShapesQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(8);
        switch (type) {
            case 0: return generateShadowQuestion();
            case 1: return generateDimensionQuestion();
            case 2: return generateExampleClassificationQuestion();
            case 3: return generatePartNameQuestion();
            case 4: return generateNetDefinitionQuestion();
            case 5: return generateEulerFormulaQuestion();
            case 6: return generateSolidPartCountQuestion();
            case 7: return generateTrueFalseDimensionalityQuestion();
            default: return generateShadowQuestion();
        }
    }

    private static Question generateShadowQuestion() {
        String[][] shadows = {
                {"cube", "square", "TRUE"},
                {"cube", "rectangle", "TRUE"},
                {"cube", "hexagon", "FALSE"},
                {"sphere", "circle", "TRUE"},
                {"sphere", "triangle", "FALSE"},
                {"cylinder (vertical light)", "circle", "TRUE"},
                {"cylinder (horizontal light)", "rectangle", "TRUE"},
                {"cone", "triangle", "TRUE"},
                {"cone", "circle", "TRUE"}
        };

        int idx = RANDOM.nextInt(shadows.length);
        String shape = shadows[idx][0];
        String shadow = shadows[idx][1];
        boolean expected = shadows[idx][2].equals("TRUE");

        boolean questionIsPositive = RANDOM.nextBoolean();
        String questionText;
        String answer;

        if (questionIsPositive) {
            questionText = "TRUE or FALSE. A " + shape + " can cast a shadow in the shape of a " + shadow + ".";
            answer = expected ? "TRUE" : "FALSE";
        } else {
            // Flip the expectation for variety if possible, but simpler to just use sample pattern
            questionText = "A " + shape + " can cast a shadow in the shape of a " + shadow + ".";
            answer = expected ? "TRUE" : "FALSE";
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setOption1("TRUE");
        question.setOption2("FALSE");
        return question;
    }

    private static Question generateDimensionQuestion() {
        boolean isPlane = RANDOM.nextBoolean();
        String questionText = (isPlane ? "Plane figures" : "Solid shapes") + " are of ________";
        String answer = isPlane ? "Two-dimensions (2-D)" : "Three-dimensions (3-D)";
        
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setOption1("Two-dimensions (2-D)");
        question.setOption2("Three-dimensions (3-D)");
        return question;
    }

    private static Question generateExampleClassificationQuestion() {
        String[] planeFigures = {"circle", "square", "rectangle", "quadrilateral", "triangle"};
        String[] solidShapes = {"cube", "cuboid", "sphere", "cylinder", "cone", "pyramid"};

        boolean askPlane = RANDOM.nextBoolean();
        StringBuilder sb = new StringBuilder("The ");
        String[] pool = askPlane ? planeFigures : solidShapes;
        
        for (int i = 0; i < pool.length; i++) {
            sb.append(pool[i]);
            if (i < pool.length - 2) sb.append(", ");
            else if (i == pool.length - 2) sb.append(" and ");
        }
        sb.append(" are examples of _________");

        String answer = askPlane ? "plane figures" : "solid shapes.";

        Question question = new Question();
        question.setQuestion(sb.toString());
        question.setAnswer(answer);
        question.setOption1("solid shapes.");
        question.setOption2("plane figures");
        return question;
    }

    private static Question generatePartNameQuestion() {
        String[][] parts = {
                {"The corners of a solid shape are called its ______", "vertices", "edges", "faces"},
                {"The flat surfaces of a solid shape are called its ______", "faces", "edges", "vertices"},
                {"The line segments where two faces of a solid shape meet are called its ______", "edges", "faces", "vertices"}
        };

        int idx = RANDOM.nextInt(parts.length);
        String[] item = parts[idx];

        Question question = new Question();
        question.setQuestion(item[0]);
        question.setAnswer(item[1]);
        
        List<String> options = new ArrayList<>();
        options.add(item[1]);
        options.add(item[2]);
        options.add(item[3]);
        Collections.shuffle(options);
        
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateNetDefinitionQuestion() {
        Question question = new Question();
        question.setQuestion("A _____ is a skeleton-outline of a solid that can be folded to make it");
        question.setAnswer("net");
        OptionUtils.setQuestionOptions(question, new String[]{"drawing", "outline", "net", "box"});
        return question;
    }

    private static Question generateEulerFormulaQuestion() {
        // F + V - E = 2
        String questionText = "Which of the following is Euler's formula for polyhedrons (where F=Faces, V=Vertices, E=Edges)?";
        String answer = "F + V - E = 2";
        String[] options = {answer, "F + V + E = 2", "F - V + E = 2", "F + V - E = 1"};
        
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        List<String> optList = new ArrayList<>();
        Collections.addAll(optList, options);
        Collections.shuffle(optList);
        OptionUtils.setQuestionOptions(question, optList.toArray(new String[0]));
        return question;
    }

    private static Question generateSolidPartCountQuestion() {
        // {Name, Faces, Vertices, Edges}
        String[][] counts = {
                {"cube", "6", "8", "12"},
                {"cuboid", "6", "8", "12"},
                {"triangular pyramid", "4", "4", "6"},
                {"triangular prism", "5", "6", "9"},
                {"square pyramid", "5", "5", "8"}
        };

        int idx = RANDOM.nextInt(counts.length);
        String[] solid = counts[idx];
        int partType = RANDOM.nextInt(3); // 0: Faces, 1: Vertices, 2: Edges
        
        String[] partNames = {"faces", "vertices", "edges"};
        String questionText = "How many " + partNames[partType] + " does a " + solid[0] + " have?";
        String answer = solid[partType + 1];

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        
        int ansVal = Integer.parseInt(answer);
        List<String> options = new ArrayList<>();
        options.add(answer);
        options.add(String.valueOf(ansVal + 2));
        options.add(String.valueOf(ansVal - 2 > 0 ? ansVal - 2 : ansVal + 4));
        options.add(String.valueOf(ansVal + 1));
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        
        return question;
    }

    private static Question generateTrueFalseDimensionalityQuestion() {
        boolean isPlane = RANDOM.nextBoolean();
        boolean is2D = RANDOM.nextBoolean();
        
        String questionText = "TRUE or FALSE. " + (isPlane ? "Plane figures" : "Solid shapes") + " are of " + (is2D ? "Two-dimensions (2-D)" : "Three-dimensions (3-D)");
        
        boolean expected;
        if (isPlane) expected = is2D;
        else expected = !is2D;

        String answer = expected ? "TRUE" : "FALSE";

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        question.setOption1("TRUE");
        question.setOption2("FALSE");
        return question;
    }
}
