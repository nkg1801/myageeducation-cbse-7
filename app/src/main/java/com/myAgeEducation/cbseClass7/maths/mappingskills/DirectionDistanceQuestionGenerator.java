package com.myAgeEducation.cbseClass7.maths.mappingskills;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.ImageCodeType;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DirectionDistanceQuestionGenerator
{
    private static final Random RANDOM = new Random();
    private static final int MIN_DISTANCE = 1;
    private static final int MAX_DISTANCE = 4;
    private static final int GRID_MIN = -4;
    private static final int GRID_MAX = 4;

    private static final String[] DIRECTIONS =
            {
                    "north",
                    "south",
                    "east",
                    "west"
            };

    private DirectionDistanceQuestionGenerator()
    {
        // Prevent object creation
    }

    public static Question generateQuestion()
    {
        DirectionDistanceQuestionData data = generateQuestionData();

        Question question = new Question();
        question.setQuestion(data.getQuestion());
        OptionUtils.setQuestionOptions(question, data.getOptions());
        question.setAnswer(data.getAnswer());

        question.setImage(createImageCode(data));
        return question;
    }

    public static DirectionDistanceQuestionData generateQuestionData()
    {
        MappingTheme theme = MappingTheme.getRandomTheme(RANDOM);
        List<DirectionPoint> points = generatePoints(theme);
        int questionType = RANDOM.nextInt(4);

        switch (questionType)
        {
            case 0:
                return generateOneMoveQuestion(points, theme);

            case 1:
                return generateTwoMoveQuestion(points, theme);

            case 2:
                return generateThreeMoveQuestion(points, theme);

            default:
                return generateMixedQuestion(points, theme);
        }
    }

    private static List<DirectionPoint> generatePoints(MappingTheme theme)
    {
        List<DirectionPoint> points = new ArrayList<>();
        Set<String> usedPositions = new HashSet<>();
        usedPositions.add("0,0");

        /*
         * Select four different objects.
         */
        List<DirectionObject> objects =
                new ArrayList<>();

        Collections.addAll(
                objects,
                theme.getObjects());

        Collections.shuffle(
                objects,
                RANDOM);

        for (int i = 0; i < 4; i++)
        {
            DirectionObject object =
                    objects.get(i);

            int x;
            int y;

            do
            {
                x = randomCoordinate();
                y = randomCoordinate();
            }
            while ((x == 0 && y == 0)
                    || usedPositions.contains(
                    x + "," + y)
                    || !isFarEnough(
                    x,
                    y,
                    points));

            usedPositions.add(
                    x + "," + y);

            List<DirectionMove> moves =
                    generateMovesForCoordinate(
                            x,
                            y);

            points.add(
                    new DirectionPoint(
                            object.getName(),
                            object.getImageName(),
                            x,
                            y,
                            moves));
        }

        return points;
    }

    private static boolean isFarEnough(
            int x,
            int y,
            List<DirectionPoint> points)
    {
        for (DirectionPoint point : points)
        {
            int dx =
                    Math.abs(
                            x - point.getX());

            int dy =
                    Math.abs(
                            y - point.getY());

            /*
             * Keep at least one grid cell
             * between objects.
             */
            if (dx <= 1 && dy <= 1)
            {
                return false;
            }
        }

        return true;
    }

    private static List<DirectionMove> generateMovesForCoordinate(
            int x,
            int y)
    {
        List<DirectionMove> moves =
                new ArrayList<>();

        /*
         * Horizontal movement.
         */
        if (x > 0)
        {
            moves.add(
                    new DirectionMove(
                            x,
                            "east"));
        }
        else if (x < 0)
        {
            moves.add(
                    new DirectionMove(
                            Math.abs(x),
                            "west"));
        }

        /*
         * Vertical movement.
         */
        if (y > 0)
        {
            moves.add(
                    new DirectionMove(
                            y,
                            "north"));
        }
        else if (y < 0)
        {
            moves.add(
                    new DirectionMove(
                            Math.abs(y),
                            "south"));
        }

        /*
         * We normally want the shortest path.
         *
         * Example:
         *
         *       3 North
         *       |
         *       |
         *       +---- 2 East
         *
         * instead of unnecessarily splitting it.
         */
        Collections.shuffle(
                moves,
                RANDOM);

        /*
         * Sometimes create a 3-movement path,
         * but only by splitting one movement.
         *
         * Example:
         *
         * 4 East
         *
         * becomes:
         *
         * 2 East
         * 2 East
         *
         * which is NOT desirable because the directions
         * are identical.
         *
         * Therefore, instead of simply splitting a movement,
         * we create a small detour.
         */
        if (moves.size() < 3
                && RANDOM.nextBoolean())
        {
            List<DirectionMove> threeMoves =
                    createThreeMovePath(
                            x,
                            y);

            if (threeMoves != null)
            {
                return threeMoves;
            }
        }

        return moves;
    }

    private static List<DirectionMove>
    createThreeMovePath(
            int targetX,
            int targetY)
    {
        List<DirectionMove> result =
                new ArrayList<>();

        /*
         * Need at least one horizontal and
         * one vertical component.
         */
        if (targetX == 0 || targetY == 0)
        {
            return null;
        }

        /*
         * Example target:
         *
         * (3, 2)
         *
         * We can create:
         *
         * 1 East
         * 2 North
         * 2 East
         *
         * Final position = (3,2)
         */
        int horizontal =
                Math.abs(targetX);

        int vertical =
                Math.abs(targetY);

        String horizontalDirection =
                targetX > 0
                        ? "east"
                        : "west";

        String verticalDirection =
                targetY > 0
                        ? "north"
                        : "south";

        /*
         * We need at least 2 units in the
         * horizontal direction to split it.
         */
        if (horizontal >= 2)
        {
            int firstPart =
                    1 + RANDOM.nextInt(
                            horizontal - 1);

            int secondPart =
                    horizontal - firstPart;

            result.add(
                    new DirectionMove(
                            firstPart,
                            horizontalDirection));

            result.add(
                    new DirectionMove(
                            vertical,
                            verticalDirection));

            result.add(
                    new DirectionMove(
                            secondPart,
                            horizontalDirection));

            return result;
        }

        /*
         * Otherwise split the vertical movement.
         */
        if (vertical >= 2)
        {
            int firstPart =
                    1 + RANDOM.nextInt(
                            vertical - 1);

            int secondPart =
                    vertical - firstPart;

            result.add(
                    new DirectionMove(
                            firstPart,
                            verticalDirection));

            result.add(
                    new DirectionMove(
                            horizontal,
                            horizontalDirection));

            result.add(
                    new DirectionMove(
                            secondPart,
                            verticalDirection));

            return result;
        }

        return null;
    }

    private static int randomCoordinate()
    {
        return GRID_MIN
                + RANDOM.nextInt(
                GRID_MAX - GRID_MIN + 1);
    }

    private static DirectionDistanceQuestionData generateOneMoveQuestion(List<DirectionPoint> points, MappingTheme theme)
    {
        DirectionPoint point = findPointWithOneMove(points);

        if (point == null)
        {
            return generateTwoMoveQuestion(points, theme);
        }

        List<DirectionMove> moves = point.getMoves();

        String question;

        if(isVehicle(theme.getSubject()))
        {
            question = "The blue dot represents the starting position. " +
                    "To get to the "
                    + point.getName()
                    + ", the " + theme.getSubject() + " needs to " + theme.getVerb() + " "
                    + "which of the following ways?";
        }
        else {

            question = "To collect food, the " + theme.getSubject() + " can only " + theme.getVerb() +
                    " along the dotted lines on the grid." +
                    "To get to the "
                    + point.getName()
                    + ", the " + theme.getSubject() + " has to " + theme.getVerb() + " "
                    + "____ " + theme.getUnit() + " towards the "
                    + "________ direction.";
        }

        String answer = buildAnswer(moves, theme);
        String[] options = generateOptions(moves, theme);
        return new DirectionDistanceQuestionData(question, options, answer, points, theme.getStartImage(), theme.getScaleLabel());
    }

    private static boolean isVehicle(String subject)
    {
        return subject.equalsIgnoreCase("bus") || subject.equalsIgnoreCase("car");
    }

    private static DirectionDistanceQuestionData generateTwoMoveQuestion(List<DirectionPoint> points, MappingTheme theme)
    {
        DirectionPoint point = findPointWithTwoMoves(points);

        if (point == null)
        {
            return generateMixedQuestion(points, theme);
        }

        List<DirectionMove> moves = point.getMoves();

        String question;

        if(isVehicle(theme.getSubject()))
        {
            question = "The blue dot represents the starting position. " +
                    "To get to the "
                    + point.getName()
                    + ", the " + theme.getSubject() + " has to " + theme.getVerb() + " "
                    + "____ " + theme.getUnit() + " towards the "
                    + "________ direction; then "
                    + "____ " + theme.getUnit() + " towards the "
                    + "________ direction.";
        }
        else {

            question = "To collect food, the " + theme.getSubject() + " can only " + theme.getVerb() +
                    " along the dotted lines on the grid." +
                    "To get to the "
                    + point.getName()
                    + ", the " + theme.getSubject() + " has to " + theme.getVerb() + " "
                    + "____ " + theme.getUnit() + " towards the "
                    + "________ direction; then "
                    + "____ " + theme.getUnit() + " towards the "
                    + "________ direction.";
        }

        String answer =
                buildAnswer(moves, theme);

        String[] options =
                generateOptions(moves, theme);

        return new DirectionDistanceQuestionData(
                question,
                options,
                answer,
                points,
                theme.getStartImage(),
                theme.getScaleLabel());
    }

    private static DirectionDistanceQuestionData generateThreeMoveQuestion(List<DirectionPoint> points, MappingTheme theme)
    {
        DirectionPoint point = findPointWithThreeMoves(points);

        if (point == null)
        {
            return generateMixedQuestion(points, theme);
        }

        List<DirectionMove> moves = point.getMoves();

        String question;

        if(isVehicle(theme.getSubject()))
        {
            question = "The blue dot represents the starting position. " +
                    "To get to the "
                    + point.getName()
                    + ", the " + theme.getSubject() + " needs to " + theme.getVerb() + " "
                    + "which of the following ways?";
        }
        else {
            question = "To collect food, the " + theme.getSubject() + " can only " + theme.getVerb() +
                    " along the dotted lines on the grid." +
                    "To get to the "
                    + point.getName()
                    + ", the " + theme.getSubject() + " needs to " + theme.getVerb() + " "
                    + "which of the following ways?";
        }

        String answer = buildAnswer(moves, theme);

        String[] options = generateOptions(moves, theme);

        return new DirectionDistanceQuestionData(
                question,
                options,
                answer,
                points,
                theme.getStartImage(),
                theme.getScaleLabel());
    }

    private static DirectionDistanceQuestionData generateMixedQuestion(List<DirectionPoint> points, MappingTheme theme)
    {
        DirectionPoint point = points.get(RANDOM.nextInt(points.size()));
        List<DirectionMove> moves = point.getMoves();
        StringBuilder question = new StringBuilder();

        question.append("To get to the ").append(point.getName())
                .append(", the " + theme.getSubject() + " needs to " + theme.getVerb() + " ");

        for (int i = 0; i < moves.size(); i++)
        {
            if (i > 0)
            {
                if (i == moves.size() - 1)
                {
                    question.append("and finally ");
                }
                else
                {
                    question.append("then ");
                }
            }

            question.append("____ " + theme.getUnit() + " towards _________");

            if (i < moves.size() - 1)
            {
                question.append(", ");
            }
            else
            {
                question.append(".");
            }
        }

        String answer = buildAnswer(moves, theme);
        String[] options = generateOptions(moves, theme);

        return new DirectionDistanceQuestionData(
                question.toString(),
                options,
                answer,
                points,
                theme.getStartImage(),
                theme.getScaleLabel());
    }

    private static DirectionPoint findPointWithOneMove(List<DirectionPoint> points)
    {
        for (DirectionPoint point : points)
        {
            if (point.getMoves().size() == 1)
            {
                return point;
            }
        }

        return null;
    }

    private static DirectionPoint findPointWithTwoMoves(List<DirectionPoint> points)
    {
        for (DirectionPoint point : points)
        {
            if (point.getMoves().size() == 2)
            {
                return point;
            }
        }

        return null;
    }

    private static DirectionPoint findPointWithThreeMoves(List<DirectionPoint> points)
    {
        for (DirectionPoint point : points)
        {
            if (point.getMoves().size() == 3)
            {
                return point;
            }
        }

        return null;
    }

    private static String buildAnswer(List<DirectionMove> moves, MappingTheme theme)
    {
        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < moves.size(); i++)
        {
            DirectionMove move = moves.get(i);

            if (i > 0)
            {
                answer.append(", ");

                if (i == moves.size() - 1)
                {
                    answer.append("and ");
                }
            }

            answer.append(move.getDistance() * theme.getScaleMultiplier()).append(" ").append(theme.getUnit()).append(" ").append(move.getDirection());
        }

        return answer.toString();
    }

    private static String[] generateOptions(List<DirectionMove> correctMoves, MappingTheme theme)
    {
        String correctAnswer = buildAnswer(correctMoves, theme);
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        Set<String> used = new HashSet<>();
        used.add(normalizeOption(correctAnswer));

        int attempts = 0;

        while (options.size() < 4 && attempts < 100)
        {
            attempts++;
            List<DirectionMove> moves = generateDistractorMoves(correctMoves);
            String option = buildAnswer(moves, theme);
            String normalized = normalizeOption(option);

            if (!used.contains(normalized))
            {
                used.add(normalized);
                options.add(option);
            }
        }

        /*
         * This should practically never be needed,
         * but guarantees four options.
         */
        if (options.size() < 4)
        {
            addFallbackOptions(options, correctMoves, used, theme);
        }

        Collections.shuffle(options, RANDOM);
        return options.toArray(new String[0]);
    }

    private static List<DirectionMove>
    generateDistractorMoves(List<DirectionMove> correctMoves)
    {
        List<DirectionMove> moves = new ArrayList<>();

        for (DirectionMove correct : correctMoves)
        {
            int distance = correct.getDistance();
            String direction = correct.getDirection();
            int distractorType = RANDOM.nextInt(4);

            switch (distractorType)
            {
                case 0:
                    /*
                     * Change the distance.
                     */
                    distance = getDifferentDistance(distance);
                    break;

                case 1:
                    /*
                     * Change the direction.
                     */
                    direction = getDifferentDirection(direction);
                    break;

                case 2:
                    /*
                     * Keep distance but use
                     * opposite direction.
                     */
                    direction = getOppositeDirection(direction);
                    break;

                default:
                    /*
                     * Change both.
                     */
                    distance = getDifferentDistance(distance);
                    direction = getDifferentDirection(direction);
                    break;
            }

            moves.add(new DirectionMove(distance, direction));
        }

        /*
         * Occasionally change the order of
         * movements as well.
         */
        if (moves.size() > 1 && RANDOM.nextBoolean())
        {
            Collections.shuffle(moves, RANDOM);
        }

        return moves;
    }

    private static int getDifferentDistance(int current)
    {
        int distance;

        do
        {
            distance = MIN_DISTANCE + RANDOM.nextInt(MAX_DISTANCE - MIN_DISTANCE + 1);
        }
        while (distance == current);

        return distance;
    }

    private static String getDifferentDirection(String current)
    {
        String direction;

        do
        {
            direction = DIRECTIONS[RANDOM.nextInt(DIRECTIONS.length)];
        }
        while (direction.equals(current));

        return direction;
    }

    private static String getOppositeDirection(String direction)
    {
        switch (direction)
        {
            case "north":
                return "south";

            case "south":
                return "north";

            case "east":
                return "west";

            case "west":
                return "east";

            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private static String normalizeOption(String option)
    {
        return option
                .trim()
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }

    private static void addFallbackOptions(List<String> options, List<DirectionMove> correctMoves, Set<String> used, MappingTheme theme)
    {
        for (int distance = 1; distance <= MAX_DISTANCE && options.size() < 4; distance++)
        {
            for (String direction : DIRECTIONS)
            {
                List<DirectionMove> moves = new ArrayList<>();

                for (DirectionMove move : correctMoves)
                {
                    moves.add(new DirectionMove(distance, direction));
                }

                String option = buildAnswer(moves, theme);
                String normalized = normalizeOption(option);

                if (!used.contains(normalized))
                {
                    used.add(normalized);
                    options.add(option);
                }
            }
        }
    }

    private static String createImageCode(DirectionDistanceQuestionData data)
    {
        StringBuilder code = new StringBuilder(ImageCodeType.DISTANCE_GRID_QUIZ);
        code.append("_").append(data.getStartImage());
        code.append("_").append(data.getScaleLabel());
        List<DirectionPoint> points = data.getPoints();
        code.append("_").append(points.size());

        for (DirectionPoint point : points)
        {
            code.append("_").append(point.getImageName());
            code.append("_").append(point.getX());
            code.append("_").append(point.getY());

            List<DirectionMove> moves = point.getMoves();
            code.append("_").append(moves.size());
            for (DirectionMove move : moves)
            {
                code.append("_").append(move.getDistance());
                code.append("_").append(move.getDirection());
            }
        }
        return code.toString();
    }
}