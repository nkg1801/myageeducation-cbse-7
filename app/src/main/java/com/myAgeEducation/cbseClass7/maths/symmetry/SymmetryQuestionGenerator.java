package com.myAgeEducation.cbseClass7.maths.symmetry;
import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SymmetryQuestionGenerator {
    private static final Random RANDOM = new Random();

    /*public static Question generateQuestion() {
        SymmetryQuestionData data = generateQuestionData();
        return convertToQuestion(data);
    }*/

    public static Question generateQuestion() {
        int type = RANDOM.nextInt(10);
        if (type < 9) {
            ArrayList<Question> bankQuestions = generateAllQuestions();
            return bankQuestions.get(RANDOM.nextInt(bankQuestions.size()));
        } else {
            return generateTableQuestion();
        }
    }

    private static Question generateTableQuestion() {
        String[] shapes = {"Regular Triangle", "Square", "Regular Pentagon", "Regular Octagon"};
        int[] orders = {3, 4, 5, 8};
        
        int missingIndex = RANDOM.nextInt(shapes.length);
        String questionText = "Complete the table by finding the missing value marked as '?' for " + shapes[missingIndex] + ".";
        String answer = String.valueOf(orders[missingIndex]);
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        while (options.size() < 4) {
            int fake = 2 + RANDOM.nextInt(10);
            if (!options.contains(String.valueOf(fake))) {
                options.add(String.valueOf(fake));
            }
        }
        Collections.shuffle(options);
        
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        
        // Custom image code for symmetry table
        // Format: SYMMETRY_TABLE_MISSING_INDEX
        question.setImage("SYMMETRY_TABLE_" + missingIndex);
        
        return question;
    }

    public static ArrayList<Question> generateAllQuestions()
    {
        ArrayList<Question> questions = new ArrayList<>();
        for(Object[] s : symmetryQuestionBank)
        {
            SymmetryQuestionData data = generateQuestionData(s);
            Question question = convertToQuestion(data);
            questions.add(question);
        }

        Collections.shuffle(questions);

        return questions;
    }

    private static SymmetryQuestionData generateQuestionData(Object[] questionData) {
        String question = (String) questionData[0];
        String answer = (String) questionData[1];
        String[] options = (String[]) questionData[2];
        String image = (questionData.length > 3) ? (String) questionData[3] : null;

        // Shuffle options if they are not TRUE/FALSE to add variety
        if (options.length > 2) {
            List<String> optionList = new ArrayList<>(Arrays.asList(options));
            Collections.shuffle(optionList, RANDOM);
            options = optionList.toArray(new String[0]);
        }

        return new SymmetryQuestionData(question, answer, options, image);
    }

    private static Question convertToQuestion(SymmetryQuestionData data) {
        Question question = new Question();
        question.setQuestion(data.question);
        question.setAnswer(data.answer);
        question.setImage(data.image);
        OptionUtils.setQuestionOptions(question, data.options);
        return question;
    }

    private static final Object[][] symmetryQuestionBank = {
            {"How many lines of symmetry does a rectangle have?", "2", new String[]{"2", "1", "4", "infinite"}},
            {"How many lines of symmetry does a square have?", "4", new String[]{"4", "2", "1", "8"}},
            {"How many lines of symmetry does a pair of scissors have?", "1", new String[]{"1", "2", "0", "4"}},
            {"TRUE or FALSE. A circle has only one line of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"A circle has ___________ lines of symmetry.", "infinite", new String[]{"infinite", "one", "two", "four"}},
            {"How many lines of symmetry does an equilateral triangle have?", "3", new String[]{"3", "1", "2", "0"}},
            {"How many lines of symmetry does a regular pentagon have?", "5", new String[]{"5", "1", "4", "infinite"}},
            {"How many lines of symmetry does the letter 'H' have?", "2", new String[]{"2", "1", "0", "4"}},
            {"Which of these letters has no line of symmetry?", "F", new String[]{"F", "A", "M", "T"}},
            {"A line that divides a figure into two identical halves is called a line of _________.", "symmetry", new String[]{"symmetry", "boundary", "intersection", "division"}},
            {"TRUE or FALSE. No shape can have more than four line of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"TRUE or FALSE. A square and a circle has the same number of line of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"TRUE or FALSE. All circles has the same number of line of symmetry.", "TRUE", new String[]{"TRUE", "FALSE"}},
            {"TRUE or FALSE. Any line passing through the center of a circle is the line of symmetry of the circle.", "TRUE", new String[]{"TRUE", "FALSE"}},
            {"TRUE or FALSE. All kinds of triangles have same number of lines of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"TRUE or FALSE. All kinds of quadrilaterals have same number of lines of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"TRUE or FALSE. A rectangle has TWO lines of symmetry but a square has FOUR lines of symmetry.", "TRUE", new String[]{"TRUE", "FALSE"}},
            {"Which of the following shape does not have any line of symmetry?", "Scalene triangle", new String[]{"Scalene triangle", "Isosceles triangle", "Equilateral triangle", "Square"}},
            {"TRUE or FALSE. An isosceles triangle has no lines of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"TRUE or FALSE. An isosceles triangle has two lines of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"Which of the following figure has more than three lines of symmetry?", "Square", new String[]{"Square", "Equilateral triangle", "Isosceles triangle", "Rectangle"}},
            {"Which of the following triangle has three lines of symmetry?", "Equilateral triangle", new String[]{"Equilateral triangle", "Isosceles triangle", "Scalene triangle", "Right angled triangle"}},
            {"Which of the following triangle has only one lines of symmetry?", "Isosceles triangle", new String[]{"Isosceles triangle", "Equilateral triangle", "Scalene triangle", "All of these"}},
            {"Which of the following triangle has no lines of symmetry?", "Scalene triangle", new String[]{"Scalene triangle", "Isosceles triangle", "Equilateral triangle", "None of these"}},
            {"Which of the following figure has three lines of symmetry?", "Equilateral triangle", new String[]{"Equilateral triangle", "Square", "Rectangle", "Circle"}},
            {"Which of the following figure has infinite lines of symmetry?", "Circle", new String[]{"Circle", "Square", "Rectangle", "Equilateral triangle"}},
            {"Which of the following figure has two lines of symmetry?", "Rectangle", new String[]{"Rectangle", "Square", "Circle", "Equilateral triangle"}},
            {"Which of the following figure has four lines of symmetry?", "Square", new String[]{"Square", "Rectangle", "Circle", "Equilateral triangle"}},
            {"How many lines of symmetry does a circle has?", "Infinite", new String[]{"Infinite", "4", "2", "1"}},
            {"TRUE or FALSE. A circle has four line of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"How many lines of symmetry does the shape given in the below image have?", "4", new String[]{"4", "2", "1", "infinite"}, "SYMMETRY_STAR_4"},
            {"How many lines of symmetry does the shape given in the below image have?", "1", new String[]{"1", "2", "0", "infinite"}, "SYMMETRY_ARROW"},
            {"How many lines of symmetry does the shape given in the below image have?", "4", new String[]{"4", "2", "8", "infinite"}, "SYMMETRY_PLUS"},
            {"How many lines of symmetry does the shape given in the below image have?", "2", new String[]{"2", "4", "1", "infinite"}, "SYMMETRY_DIAMOND"},
            {"How many lines of symmetry does the shape given in the below image have?", "1", new String[]{"1", "2", "0", "infinite"}, "SYMMETRY_HEART"},
            {"In the following figure which line is the line of symmetry?", "Line A", new String[]{"Line A", "Line B", "Both", "None"}, "SYMMETRY_RECT_LINES"},
            {"In the following figure which line is the line of symmetry?", "Both", new String[]{"Line A", "Line B", "Both", "None"}, "SYMMETRY_SQUARE_LINES"},
            {"In the following figure which line is the line of symmetry?", "Line A", new String[]{"Line A", "Line B", "Both", "None"}, "SYMMETRY_TRIANGLE_LINES"},
            {"How many lines of symmetry are there for the following figure:", "1", new String[]{"1", "2", "3", "0"}, "SYMMETRY_TRIPLE_ARROW"},
            {"How many lines of symmetry are there for a regular hexagon as shown below?", "6", new String[]{"6", "3", "4", "infinite"}, "SYMMETRY_HEXAGON"},
            {"How many lines of symmetry are there for the kite shown below?", "1", new String[]{"1", "2", "0", "4"}, "SYMMETRY_KITE"},
            {"How many lines of symmetry are there for the parallelogram shown below?", "0", new String[]{"0", "2", "4", "1"}, "SYMMETRY_PARALLELOGRAM"},
            {"What is the order of rotational symmetry of a regular pentagon?", "5", new String[]{"5", "1", "4", "infinite"}},
            {"What is the order of rotational symmetry of a square?", "4", new String[]{"4", "2", "1", "8"}},
            {"What is the order of rotational symmetry of a rectangle?", "2", new String[]{"2", "4", "1", "infinite"}},
            {"TRUE or FALSE. A square has a rotational symmetry of order 4 about its centre.", "TRUE", new String[]{"TRUE", "FALSE"}},
            {"A square has a rotational symmetry of order _______ about its centre", "4", new String[]{"4", "1", "2", "3"}},
            {"How many lines of symmetry does a parallelogram has?", "Zero", new String[]{"Zero", "One", "Two", "Infinite"}},
            {"TRUE or FALSE. No shape can have more than four line of symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"TRUE or FALSE. A rectangle has TWO lines of symmetry but a square has FOUR lines of symmetry.", "TRUE", new String[]{"TRUE", "FALSE"}},
            {"Which of the following triangle has three lines of symmetry?", "An Equilateral Triangle", new String[]{"An Equilateral Triangle", "A scalene triangle", "An isosceles triangle", "All of these"}},
            {"What is the order of rotational symmetry of this figure?", "2", new String[]{"1", "2", "4", "6"}, "SYMMETRY_WIND_MILL"},
            {"What is the order of the rotational symmetry of the following shape about the marked point?", "2", new String[]{"1", "2", "3", "4"}, "SYMMETRY_WAVE"},
            {"A shape looks the same after every 90° rotation. What is its order of rotational symmetry?", "4", new String[]{"4", "2", "1", "360"}},
            {"Which of the following has rotational symmetry of order 2?", "Rectangle", new String[]{"Rectangle", "Square", "Circle", "Regular pentagon"}},
            {"TRUE or FALSE. Every shape with line symmetry has rotational symmetry.", "FALSE", new String[]{"FALSE", "TRUE"}},
            {"TRUE or FALSE. A circle has infinite rotational symmetry.", "TRUE", new String[]{"TRUE", "FALSE"}},
            {"TRUE or FALSE. A regular pentagon has rotational symmetry of order 5.", "TRUE", new String[]{"TRUE", "FALSE"}},
            {"What is the order of rotational symmetry of an equilateral triangle?", "3", new String[]{"3", "2", "1", "0"}},
            {"A shape has rotational symmetry of order 12. What is its smallest angle of rotation?", "30°", new String[]{"30°", "12°", "15°", "60°"}},
            {"A shape has rotational symmetry of order 12. How many times will it match its original position in one complete turn?", "12", new String[]{"12", "1", "6", "24"}},
            {"A regular polygon has rotational symmetry of order 9. How many sides does the polygon have?", "9", new String[]{"9", "18", "3", "27"}},
            {"What is the order of rotational symmetry of a regular hexagon?", "6", new String[]{"6", "3", "4", "12"}}
    };
}
