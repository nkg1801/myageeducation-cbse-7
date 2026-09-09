package com.myAgeEducation.cbseClass7.maths.average;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AverageQuestionGenerator
{
    private static final Random RANDOM =
            new Random();

    private AverageQuestionGenerator()
    {
        // Prevent object creation
    }

    public static Question generateQuestion()
    {
        AverageQuestionType type = AverageQuestionType.values()[RANDOM.nextInt(AverageQuestionType.values().length)];
        AverageQuestionData averageQuestionData;
        switch (type)
        {
            case FIND_AVERAGE:
                averageQuestionData = AverageQuestionGenerator.generateFindAverage();
                break;

            case AVERAGE_WORD_PROBLEM:
                averageQuestionData = AverageQuestionGenerator.generateAverageWordProblem();
                break;

            case AVERAGE_WITH_UNITS:
                averageQuestionData = AverageQuestionGenerator.generateAverageWithUnits();
                break;

            case FIND_NUMBERS_FOR_AVERAGE:
                averageQuestionData = AverageQuestionGenerator.generateNumbersForAverage();
                break;

            default:
                averageQuestionData = AverageQuestionGenerator.generateTotalFromAverage();
                break;
        }
        return convertToQuestion(averageQuestionData);
    }

    private static Question convertToQuestion(AverageQuestionData data) {
        Question question = new Question();
        question.setQuestion(data.getQuestion());
        question.setAnswer(data.getAnswer());
        OptionUtils.setQuestionOptions(question, data.getOptions());
        return question;
    }

    /*
     * Generates 4 different numbers whose average
     * is a whole number.
     */
    private static int[] generateFourNumbers()
    {
        int average = 10 + RANDOM.nextInt(41); // 10-50
        Set<Integer> numbers = new LinkedHashSet<>();

        /*
         * Generate numbers around the average.
         * We keep trying until the four numbers
         * are different.
         */
        while (numbers.size() < 4)
        {
            int value = average - 15 + RANDOM.nextInt(31);

            if (value > 0)
            {
                numbers.add(value);
            }
        }

        /*
         * The above numbers may not have the
         * required average. Adjust the last
         * number so that:
         *
         * sum = average × 4
         */
        List<Integer> list = new ArrayList<>(numbers);
        int requiredTotal = average * 4;
        int currentTotal = list.get(0) + list.get(1) + list.get(2);
        int fourth = requiredTotal - currentTotal;

        /*
         * Make sure the fourth number is positive
         * and different from the other numbers.
         */
        if (fourth <= 0 || list.get(0) == fourth || list.get(1) == fourth || list.get(2) == fourth)
        {
            return generateFourNumbers();
        }

        list.set(3, fourth);

        return new int[]
                {
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3)
                };
    }

    private static int calculateAverage(int[] numbers)
    {
        int total = 0;

        for (int number : numbers)
        {
            total += number;
        }

        return total / numbers.length;
    }

    private static String formatNumbers(int[] numbers)
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < numbers.length; i++)
        {
            if (i > 0)
            {
                builder.append(", ");
            }

            builder.append(numbers[i]);
        }

        return builder.toString();
    }

    /*
     * Generates four numeric MCQ options.
     */
    private static String[] generateOptions(int correctAnswer)
    {
        LinkedHashSet<Integer> options = new LinkedHashSet<>();

        options.add(correctAnswer);

        int[] distractors =
                {
                        correctAnswer + 1,
                        correctAnswer - 1,
                        correctAnswer + 2,
                        correctAnswer - 2,
                        correctAnswer + 5,
                        correctAnswer - 5,
                        correctAnswer + 10,
                        correctAnswer - 10
                };

        for (int value : distractors)
        {
            if (value > 0)
            {
                options.add(value);
            }

            if (options.size() == 4)
            {
                break;
            }
        }

        if (options.size() != 4)
        {
            throw new IllegalStateException("Unable to generate four average options");
        }

        List<Integer> shuffled = new ArrayList<>(options);

        Collections.shuffle(
                shuffled,
                RANDOM);

        String[] result =
                new String[4];

        for (int i = 0; i < 4; i++)
        {
            result[i] =
                    String.valueOf(
                            shuffled.get(i));
        }

        return result;
    }

    /*
     * Type 1:
     *
     * What is the average of these numbers:
     * 15, 14, 21, 30?
     */
    public static AverageQuestionData generateFindAverage()
    {
        int[] numbers =
                generateFourNumbers();

        int average =
                calculateAverage(numbers);

        String question =
                "What is the average of these numbers: "
                        + formatNumbers(numbers)
                        + "?";

        String answer = String.valueOf(average);

        String[] options =
                generateOptions(average);

        return new AverageQuestionData(
                question,
                answer,
                options,
                AverageQuestionType.FIND_AVERAGE);
    }

    /*
     * Type 2:
     *
     * A travelling salesman travelled 75 km,
     * 49 km, 81 km, 36 km, and 59 km...
     */
    public static AverageQuestionData generateAverageWordProblem()
    {
        int[] distances = generateFiveNumbers();
        int average = calculateAverage(distances);

        String[] templates =
                {
                        "A travelling salesman travelled %d km, %d km, %d km, %d km, and %d km in the first 5 days of the week. What was the average distance travelled by him?",
                        "Ravi walked %d km, %d km, %d km, %d km, and %d km in five days. What was the average distance walked by him each day?",
                        "A shop sold %d, %d, %d, %d, and %d notebooks on five days. What was the average number of notebooks sold each day?",
                        "A student scored %d, %d, %d, %d, and %d marks in five tests. What was the average score?",
                        "A farmer collected %d kg, %d kg, %d kg, %d kg, and %d kg of vegetables on five days. What was the average amount collected each day?",
                        "The maximum temperature recorded on five consecutive days was %d°C, %d°C, %d°C, %d°C, and %d°C. What was the average daily maximum temperature?",
                        "The weight of five apples are %d g, %d g, %d g, %d g, and %d g. What is the average weight of an apple?",
                        "A library issued %d, %d, %d, %d, and %d books over five days. What was the average number of books issued per day?",
                        "A car used %d, %d, %d, %d, and %d litres of petrol in five weeks. What was the average consumption of petrol per week?",
                        "The monthly rainfall in five months was %d mm, %d mm, %d mm, %d mm, and %d mm. What was the average monthly rainfall?",
                        "A factory produced %d, %d, %d, %d, and %d toys on five days. What was the average number of toys produced each day?",
                        "Five friends have %d, %d, %d, %d, and %d marbles respectively. What is the average number of marbles with each friend?",
                        "A tailor used %d m, %d m, %d m, %d m, and %d m of cloth to make 5 different dresses. What was the average length of cloth used per dress?",
                        "A milkman sold %d litres, %d litres, %d litres, %d litres, and %d litres of milk on five days. What was the average amount of milk sold each day?",
                        "A batsman scored %d, %d, %d, %d, and %d runs in five matches. What was his average score per match?"
                };

        String template = templates[RANDOM.nextInt(templates.length)];

        String question =
                String.format(
                        template,
                        distances[0],
                        distances[1],
                        distances[2],
                        distances[3],
                        distances[4]);

        String answer = String.valueOf(average);
        String[] options = generateOptions(average);
        return new AverageQuestionData(question, answer, options, AverageQuestionType.AVERAGE_WORD_PROBLEM);
    }

    /*
     * Generates five different numbers whose
     * average is a whole number.
     */
    private static int[] generateFiveNumbers()
    {
        int average = 10 + RANDOM.nextInt(31); // 10-40
        Set<Integer> numbers = new LinkedHashSet<>();

        while (numbers.size() < 4)
        {
            int value = average - 15 + RANDOM.nextInt(31);

            if (value > 0)
            {
                numbers.add(value);
            }
        }

        List<Integer> list = new ArrayList<>(numbers);
        int requiredTotal = average * 5;
        int currentTotal = list.get(0) + list.get(1) + list.get(2) + list.get(3);
        int fifth = requiredTotal - currentTotal;

        if (fifth <= 0 || list.contains(fifth))
        {
            return generateFiveNumbers();
        }

        list.add(fifth);

        return new int[]
                {
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3),
                        list.get(4)
                };
    }

    public static AverageQuestionData
    generateAverageWithUnits()
    {
        int[] numbers =
                generateFiveNumbers();

        int average =
                calculateAverage(numbers);

        String[] units =
                {
                        "Rs ",
                        "",
                        "km ",
                        "kg ",
                        "litres "
                };

        String unit =
                units[
                        RANDOM.nextInt(
                                units.length)];

        StringBuilder numberText =
                new StringBuilder();

        for (int i = 0;
             i < numbers.length;
             i++)
        {
            if (i > 0)
            {
                numberText.append(", ");
            }

            numberText.append(unit)
                    .append(numbers[i]);
        }

        String question;

        if (unit.equals("Rs "))
        {
            question =
                    "What is the average of these amounts: "
                            + numberText
                            + "?";
        }
        else if (unit.equals("km "))
        {
            question =
                    "What is the average of these distances: "
                            + numberText
                            + "?";
        }
        else if (unit.equals("kg "))
        {
            question =
                    "What is the average of these weights: "
                            + numberText
                            + "?";
        }
        else if (unit.equals("litres "))
        {
            question =
                    "What is the average of these quantities: "
                            + numberText
                            + "?";
        }
        else
        {
            question =
                    "What is the average of these numbers: "
                            + numberText
                            + "?";
        }

        String answer =
                String.valueOf(average);

        String[] options =
                generateOptions(average);

        return new AverageQuestionData(
                question,
                answer,
                options,
                AverageQuestionType
                        .AVERAGE_WITH_UNITS);
    }

    private static int[] generateNumbersForAverage(
            int average)
    {
        int[] numbers =
                generateFourNumbersWithAverage(
                        average);

        return numbers;
    }

    private static int[] generateFourNumbersWithAverage(
            int average)
    {
        while (true)
        {
            int first =
                    10 + RANDOM.nextInt(81);

            int second =
                    10 + RANDOM.nextInt(81);

            int third =
                    10 + RANDOM.nextInt(81);

            int fourth =
                    average * 4
                            - first
                            - second
                            - third;

            if (fourth <= 0
                    || fourth > 100)
            {
                continue;
            }

            if (first == second
                    || first == third
                    || first == fourth
                    || second == third
                    || second == fourth
                    || third == fourth)
            {
                continue;
            }

            return new int[]
                    {
                            first,
                            second,
                            third,
                            fourth
                    };
        }
    }

    private static String formatNumberList(
            int[] numbers)
    {
        return numbers[0]
                + ", "
                + numbers[1]
                + ", "
                + numbers[2]
                + ", "
                + numbers[3];
    }

    public static AverageQuestionData
    generateNumbersForAverage()
    {
        int average =
                20 + RANDOM.nextInt(61);

        int[] correctNumbers =
                generateNumbersForAverage(
                        average);

        List<String> options =
                new ArrayList<>();

        String correctOption =
                formatNumberList(
                        correctNumbers);

        options.add(correctOption);

        /*
         * Generate three lists whose averages
         * are NOT equal to the required average.
         */
        while (options.size() < 4)
        {
            int[] candidate =
                    generateFourRandomNumbers();

            String candidateText =
                    formatNumberList(candidate);

            if (options.contains(
                    candidateText))
            {
                continue;
            }

            int candidateAverage =
                    calculateAverage(candidate);

            if (candidateAverage != average)
            {
                options.add(candidateText);
            }
        }

        Collections.shuffle(options, RANDOM);

        String question =
                "Which of the following lists contains "
                        + "4 different numbers whose average is "
                        + average
                        + "?";

        String[] optionArray = options.toArray(new String[0]);

        return new AverageQuestionData(question, correctOption, optionArray, AverageQuestionType.FIND_NUMBERS_FOR_AVERAGE);
    }

    private static int[] generateFourRandomNumbers()
    {
        Set<Integer> numbers = new LinkedHashSet<>();

        while (numbers.size() < 4)
        {
            numbers.add(5 + RANDOM.nextInt(96));
        }

        List<Integer> list = new ArrayList<>(numbers);

        return new int[]
                {
                        list.get(0),
                        list.get(1),
                        list.get(2),
                        list.get(3)
                };
    }

    public static AverageQuestionData generateTotalFromAverage()
    {
        String[] items =
                {
                        "umbrellas",
                        "notebooks",
                        "books",
                        "pencils",
                        "bottles",
                        "toys",
                        "packets of biscuits",
                        "apples",
                        "ice creams",
                        "raincoats",
                        "fans",
                        "sweaters"
                };

        String item =
                items[
                        RANDOM.nextInt(
                                items.length)];

        int average =
                20 + RANDOM.nextInt(181);

        int count =
                3 + RANDOM.nextInt(4); // 3-6

        int total =
                average * count;

        String question;

        if (count == 3 && item.equals("umbrellas") && RANDOM.nextBoolean())
        {
            question = "An average of "
                    + average
                    + " umbrellas per month were sold over the monsoon months of June, July, and August. How many umbrellas were sold in all?";
        }
        else
        {
            String[] timePeriods =
                    {
                            "months",
                            "weeks",
                            "days"
                    };

            String period =
                    timePeriods[
                            RANDOM.nextInt(
                                    timePeriods.length)];

            question =
                    "An average of "
                            + average
                            + " "
                            + item
                            + " per "
                            + period
                            + " were sold over "
                            + count
                            + " "
                            + period
                            + ". How many "
                            + item
                            + " were sold in all?";
        }

        String answer = String.valueOf(total);

        String[] options = generateTotalOptions(total);

        return new AverageQuestionData(
                question,
                answer,
                options,
                AverageQuestionType
                        .FIND_TOTAL_FROM_AVERAGE);
    }

    private static String[] generateTotalOptions(int correctAnswer)
    {
        LinkedHashSet<Integer> options = new LinkedHashSet<>();
        options.add(correctAnswer);

        /*
         * Useful distractors:
         *
         * average itself
         * one group less
         * one group more
         * simple arithmetic mistakes
         */
        options.add(correctAnswer / 2);
        options.add(correctAnswer + 50);
        options.add(correctAnswer - 50);
        int attempts = 0;

        while (options.size() < 4 && attempts < 50)
        {
            attempts++;
            int value = correctAnswer + RANDOM.nextInt(101) - 50;

            if (value > 0)
            {
                options.add(value);
            }
        }

        List<Integer> shuffled = new ArrayList<>(options);
        Collections.shuffle(shuffled, RANDOM);
        String[] result = new String[4];

        for (int i = 0; i < 4; i++)
        {
            result[i] = String.valueOf(shuffled.get(i));
        }

        return result;
    }
}
