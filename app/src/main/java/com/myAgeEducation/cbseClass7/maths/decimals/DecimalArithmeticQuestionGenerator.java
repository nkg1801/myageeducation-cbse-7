package com.myAgeEducation.cbseClass7.maths.decimals;
import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class DecimalArithmeticQuestionGenerator
{
    private static final Random RANDOM = new Random();

    private DecimalArithmeticQuestionGenerator()
    {
        // Prevent object creation
    }

    public static Question generateQuestion()
    {
        DecimalArithmeticQuestionType[] types = DecimalArithmeticQuestionType.values();
        int randomNumber = RANDOM.nextInt(100);
        DecimalArithmeticQuestionType type;
        if(randomNumber < 40) // 40%
        {
            type = DecimalArithmeticQuestionType.WORD_PROBLEM_MULTIPLICATION;
        }
        else // 60%
        {
            // this could also include WORD_PROBLEM_MULTIPLICATION,
            // so chances of WORD_PROBLEM_MULTIPLICATION types increases and this is intentional
            type = types[RANDOM.nextInt(types.length)];
        }

        DecimalArithmeticQuestionData data = DecimalArithmeticQuestionGenerator.generateQuestion(type);
        return  convertToQuestion(data);
    }

    private static Question convertToQuestion(DecimalArithmeticQuestionData data) {
        Question question = new Question();
        question.setQuestion(data.getQuestion());
        question.setAnswer(data.getAnswer());
        OptionUtils.setQuestionOptions(question, data.getOptions());
        return question;
    }

    public static DecimalArithmeticQuestionData generateQuestion(DecimalArithmeticQuestionType type)
    {
        switch (type)
        {
            case DECIMAL_MULTIPLY_WHOLE:
                return generateDecimalMultiplyWhole();

            case WHOLE_MULTIPLY_DECIMAL:
                return generateWholeMultiplyDecimal();

            case DECIMAL_DIVIDE_10:
                return generateDecimalDivide10();

            case DECIMAL_DIVIDE_100:
                return generateDecimalDivide100();

            case MISSING_DIVISOR:
                return generateMissingDivisor();

            case MISSING_MULTIPLIER:
                return generateMissingMultiplier();

            case PLACE_VALUE_MULTIPLICATION:
                return generatePlaceValueMultiplication();

            case WORD_PROBLEM_MULTIPLICATION:
                return generateWordProblemMultiplication();

            default:
                throw new IllegalArgumentException(
                        "Unsupported decimal question type: "
                                + type);
        }
    }

    private static void addQuestionType(List<DecimalArithmeticQuestionType> types, DecimalArithmeticQuestionType type, int count)
    {
        for (int i = 0; i < count; i++)
        {
            types.add(type);
        }
    }

    public static List<DecimalArithmeticQuestionData> generateTestQuestions()
    {
        List<DecimalArithmeticQuestionType> types = new ArrayList<>();

        addQuestionType(types, DecimalArithmeticQuestionType.DECIMAL_MULTIPLY_WHOLE, 4);
        addQuestionType(types, DecimalArithmeticQuestionType.WHOLE_MULTIPLY_DECIMAL, 3);
        addQuestionType(types, DecimalArithmeticQuestionType.DECIMAL_DIVIDE_10, 2);
        addQuestionType(types, DecimalArithmeticQuestionType.DECIMAL_DIVIDE_100, 2);
        addQuestionType(types, DecimalArithmeticQuestionType.MISSING_DIVISOR, 2);
        addQuestionType(types, DecimalArithmeticQuestionType.MISSING_MULTIPLIER, 2);
        addQuestionType(types, DecimalArithmeticQuestionType.PLACE_VALUE_MULTIPLICATION, 2);
        addQuestionType(types, DecimalArithmeticQuestionType.WORD_PROBLEM_MULTIPLICATION, 3);

        Collections.shuffle(types, RANDOM);

        List<DecimalArithmeticQuestionData> questions = new ArrayList<>();
        Set<String> usedQuestions = new HashSet<>();
        final int MAX_ATTEMPTS = 50;

        for (DecimalArithmeticQuestionType type : types)
        {
            DecimalArithmeticQuestionData question = null;
            boolean generated = false;

            for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
            {
                DecimalArithmeticQuestionData candidate = generateQuestion(type);

                if (candidate.getOptions() == null || candidate.getOptions().length != 4)
                {
                    continue;
                }

                String normalized = normalizeQuestionText(candidate.getQuestion());

                if (usedQuestions.add(normalized))
                {
                    question = candidate;
                    generated = true;
                    break;
                }
            }

            if (!generated)
            {
                throw new IllegalStateException("Unable to generate a unique question for type " + type + " after " + MAX_ATTEMPTS + " attempts.");
            }

            questions.add(question);
        }

        if (questions.size() != 20)
        {
            throw new IllegalStateException("Expected 20 questions but generated " + questions.size());
        }

        return questions;
    }

    private static String normalizeQuestionText(String question)
    {
        if (question == null)
        {
            return "";
        }

        return question.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    private static String formatDecimal(BigDecimal value)
    {
        return value.stripTrailingZeros().toPlainString();
    }

    public static DecimalArithmeticQuestionData generateDecimalMultiplyWhole()
    {
        BigDecimal decimal = generateDecimal();
        int wholeNumber = generateSmallWholeNumber();
        BigDecimal answer = decimal.multiply(BigDecimal.valueOf(wholeNumber));
        String question = formatDecimal(decimal) + " × " + wholeNumber + " = ?";
        String correctAnswer = formatDecimal(answer);
        String[] options = generateDecimalOptions(correctAnswer);

        return new DecimalArithmeticQuestionData(question, correctAnswer, options, DecimalArithmeticQuestionType.DECIMAL_MULTIPLY_WHOLE);
    }

    private static BigDecimal generateDecimal()
    {
        int type = RANDOM.nextInt(5);

        switch (type)
        {
            case 0:
                return BigDecimal.valueOf(1 + RANDOM.nextInt(9)).movePointLeft(1);
            case 1:
                return BigDecimal.valueOf(1 + RANDOM.nextInt(99)).movePointLeft(2);
            case 2:
                return BigDecimal.valueOf(11 + RANDOM.nextInt(89)).movePointLeft(1);
            case 3:
                return BigDecimal.valueOf(101 + RANDOM.nextInt(899)).movePointLeft(2);
            default:
                String[] values = {"0.25", "0.50", "0.75", "1.25", "1.50", "1.75", "2.25", "2.50", "2.75", "3.25", "3.50", "3.75"};
                return new BigDecimal(values[RANDOM.nextInt(values.length)]);
        }
    }

    public static DecimalArithmeticQuestionData generateWholeMultiplyDecimal()
    {
        int wholeNumber = generateSmallWholeNumber();
        BigDecimal decimal = generateDecimal();
        BigDecimal answer = BigDecimal.valueOf(wholeNumber).multiply(decimal);
        String question = wholeNumber + " × " + formatDecimal(decimal) + " = ?";
        String correctAnswer = formatDecimal(answer);
        String[] options = generateDecimalOptions(correctAnswer);

        return new DecimalArithmeticQuestionData(question, correctAnswer, options, DecimalArithmeticQuestionType.WHOLE_MULTIPLY_DECIMAL);
    }

    public static DecimalArithmeticQuestionData generateDecimalDivide10()
    {
        BigDecimal value = generateDecimal();
        BigDecimal answer = value.divide(BigDecimal.TEN);
        String question = formatDecimal(value) + " ÷ 10 = ?";
        String correctAnswer = formatDecimal(answer);
        String[] options = generateDecimalOptions(correctAnswer);

        return new DecimalArithmeticQuestionData(question, correctAnswer, options, DecimalArithmeticQuestionType.DECIMAL_DIVIDE_10);
    }

    public static DecimalArithmeticQuestionData generateDecimalDivide100()
    {
        BigDecimal value = generateDecimal();
        BigDecimal answer = value.divide(BigDecimal.valueOf(100));
        String question = formatDecimal(value) + " ÷ 100 = ?";
        String correctAnswer = formatDecimal(answer);
        String[] options = generateDecimalOptions(correctAnswer);

        return new DecimalArithmeticQuestionData(question, correctAnswer, options, DecimalArithmeticQuestionType.DECIMAL_DIVIDE_100);
    }

    public static DecimalArithmeticQuestionData generateMissingDivisor()
    {
        int[] divisors = {10, 100, 1000};
        int divisor = divisors[RANDOM.nextInt(divisors.length)];
        BigDecimal result = generateDecimal();
        BigDecimal dividend = result.multiply(BigDecimal.valueOf(divisor));
        String question = formatDecimal(dividend) + " ÷ _____ = " + formatDecimal(result);
        String correctAnswer = String.valueOf(divisor);
        String[] options = generateDivisorOptions(divisor);

        return new DecimalArithmeticQuestionData(question, correctAnswer, options, DecimalArithmeticQuestionType.MISSING_DIVISOR);
    }

    public static DecimalArithmeticQuestionData generateMissingMultiplier()
    {
        int multiplier = generateSmallWholeNumber();
        BigDecimal decimal = generateDecimal();
        BigDecimal result = decimal.multiply(BigDecimal.valueOf(multiplier));
        String question = "_____ × " + formatDecimal(decimal) + " = " + formatDecimal(result);
        String correctAnswer = String.valueOf(multiplier);
        String[] options = generateIntegerOptions(multiplier);

        return new DecimalArithmeticQuestionData(question, correctAnswer, options, DecimalArithmeticQuestionType.MISSING_MULTIPLIER);
    }

    public static DecimalArithmeticQuestionData generatePlaceValueMultiplication()
    {
        int number = 10 + RANDOM.nextInt(90);
        int multiplier = generateSmallWholeNumber();
        int wholeAnswer = number * multiplier;
        int decimalPlaces = RANDOM.nextBoolean() ? 1 : 2;
        BigDecimal decimalNumber = BigDecimal.valueOf(number).movePointLeft(decimalPlaces);
        BigDecimal answer = decimalNumber.multiply(BigDecimal.valueOf(multiplier));

        String question = "If " + number + " × " + multiplier + " = " + wholeAnswer + ", what is the value of " + formatDecimal(decimalNumber) + " × " + multiplier + "?";
        String correctAnswer = formatDecimal(answer);
        String[] options = generatePlaceValueOptions(answer, BigDecimal.valueOf(wholeAnswer));

        return new DecimalArithmeticQuestionData(question, correctAnswer, options, DecimalArithmeticQuestionType.PLACE_VALUE_MULTIPLICATION);
    }

    public static DecimalArithmeticQuestionData generateWordProblemMultiplication()
    {
        BigDecimal quantity = generateWordProblemDecimal();
        int count = 2 + RANDOM.nextInt(7);
        BigDecimal answer = quantity.multiply(BigDecimal.valueOf(count));
        String qStr = formatDecimal(quantity);

        int choice = RANDOM.nextInt(21);
        String question;

        switch (choice)
        {
            case 0:
            {
                boolean male = RANDOM.nextBoolean();
                String name = male ? PersonNameUtil.getMaleName() : PersonNameUtil.getFemaleName();
                String pronoun = male ? "he" : "she";
                question = String.format(Locale.US, "%s drinks %s litre of milk every day. How much milk does %s drink in %d days?", name, qStr, pronoun, count);
                break;
            }
            case 1:
            {
                boolean male = RANDOM.nextBoolean();
                String name = male ? PersonNameUtil.getMaleName() : PersonNameUtil.getFemaleName();
                String pronoun = male ? "he" : "she";
                question = String.format(Locale.US, "%s walks %s km every day. How far does %s walk in %d days?", name, qStr, pronoun, count);
                break;
            }
            case 2:
                question = String.format(Locale.US, "A shop sells %s kg of apples every day. How many kilograms of apples does it sell in %d days?", qStr, count);
                break;
            case 3:
                question = String.format(Locale.US, "One ribbon is %s metre long. What is the total length of %d such ribbons?", qStr, count);
                break;
            case 4:
                question = String.format(Locale.US, "A bottle contains %s litre of juice. How much juice is there in %d bottles?", qStr, count);
                break;
            case 5:
                question = String.format(Locale.US, "One packet weighs %s kg. What is the weight of %d such packets?", qStr, count);
                break;
            case 6:
            {
                boolean male = RANDOM.nextBoolean();
                String name = male ? PersonNameUtil.getMaleName() : PersonNameUtil.getFemaleName();
                String pronoun = male ? "he" : "she";
                question = String.format(Locale.US, "%s uses %s litre of water every day for watering plants. How much water does %s use in %d days?", name, qStr, pronoun, count);
                break;
            }
            case 7:
                question = String.format(Locale.US, "A car travels %s km on one litre of petrol. How far will it travel on %d litres?", qStr, count);
                break;
            case 8:
                question = String.format(Locale.US, "One notebook costs Rs %s. How much will %d such notebooks cost?", qStr, count);
                break;
            case 9:
                question = String.format(Locale.US, "The weight of one watermelon is %s kg. What is the weight of %d such watermelons?", qStr, count);
                break;
            case 10:
                question = String.format(Locale.US, "One bag of rice weighs %s kg. How much will %d such bags weigh?", qStr, count);
                break;
            case 11:
            {
                String name = PersonNameUtil.getOneName();
                question = String.format(Locale.US, "The tailor %s uses %s m of cloth to stitch one shirt. How much cloth is needed for %d shirts?", name, qStr, count);
                break;
            }
            case 12:
                question = String.format(Locale.US, "One pencil costs Rs %s. What is the cost of %d pencils?", qStr, count);
                break;
            case 13:
                question = String.format(Locale.US, "A bucket holds %s litres of water. How much water is there in %d such buckets?", qStr, count);
                break;
            case 14:
            {
                boolean male = RANDOM.nextBoolean();
                String name = male ? PersonNameUtil.getMaleName() : PersonNameUtil.getFemaleName();
                String pronoun = male ? "he" : "she";
                question = String.format(Locale.US, "%s runs %s km in one hour. How far will %s run in %d hours?", name, qStr, pronoun, count);
                break;
            }
            case 15:
                question = String.format(Locale.US, "One chocolate bar weighs %s kg. What is the weight of %d such bars?", qStr, count);
                break;
            case 16:
                question = String.format(Locale.US, "A plant grows %s cm every week. How much will it grow in %d weeks?", qStr, count);
                break;
            case 17:
                question = String.format(Locale.US, "One tin of paint contains %s litre of paint. How much paint is there in %d tins?", qStr, count);
                break;
            case 18:
                question = String.format(Locale.US, "A piece of cheese weighs %s kg. What is the weight of %d such pieces?", qStr, count);
                break;
            case 19:
                question = String.format(Locale.US, "One storybook costs Rs %s. What is the cost of %d such books?", qStr, count);
                break;
            case 20:
                question = String.format(Locale.US, "A small jar contains %s kg of honey. How much honey is there in %d jars?", qStr, count);
                break;
            default:
                question = "";
        }

        String correctAnswer = formatDecimal(answer);
        String[] options = generateWordProblemOptions(quantity, count, answer);

        return new DecimalArithmeticQuestionData(question, correctAnswer, options, DecimalArithmeticQuestionType.WORD_PROBLEM_MULTIPLICATION);
    }

    private static BigDecimal generateWordProblemDecimal()
    {
        String[] values = {"0.25", "0.5", "0.75", "1.0", "1.25", "1.5", "1.75", "2.0", "2.25", "2.5", "0.2", "0.4", "0.6", "0.8", "0.15", "0.45", "0.85", "1.2", "1.4", "1.6", "1.8", "3.25", "3.5", "4.75", "5.5"};
        return new BigDecimal(values[RANDOM.nextInt(values.length)]);
    }

    private static String[] generateDecimalOptions(String correctAnswer)
    {
        BigDecimal correct = new BigDecimal(correctAnswer);
        Set<String> options = new HashSet<>();
        options.add(formatDecimal(correct));

        BigDecimal[] candidates = {
                correct.movePointRight(1), correct.movePointLeft(1),
                correct.movePointRight(2), correct.movePointLeft(2),
                correct.add(BigDecimal.ONE), correct.subtract(BigDecimal.ONE),
                correct.add(new BigDecimal("0.1")), correct.subtract(new BigDecimal("0.1")),
                correct.add(new BigDecimal("0.01")), correct.subtract(new BigDecimal("0.01"))
        };

        for (BigDecimal candidate : candidates)
        {
            if (candidate.compareTo(BigDecimal.ZERO) >= 0)
            {
                options.add(formatDecimal(candidate));
            }
            if (options.size() == 4) break;
        }

        int attempts = 0;
        while (options.size() < 4 && attempts < 50)
        {
            attempts++;
            BigDecimal candidate = correct.add(BigDecimal.valueOf(RANDOM.nextInt(11) - 5).movePointLeft(1));
            if (candidate.compareTo(BigDecimal.ZERO) >= 0)
            {
                options.add(formatDecimal(candidate));
            }
        }

        if (options.size() != 4)
        {
            throw new IllegalStateException("Unable to generate four unique decimal options for " + correctAnswer);
        }

        String[] result = options.toArray(new String[0]);
        shuffleArray(result);
        return result;
    }

    private static void shuffleArray(String[] array)
    {
        for (int i = array.length - 1; i > 0; i--)
        {
            int j = RANDOM.nextInt(i + 1);
            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static int generateSmallWholeNumber()
    {
        return 2 + RANDOM.nextInt(9);
    }

    private static String[] generateDivisorOptions(int correctAnswer)
    {
        Set<String> options = new HashSet<>();
        options.add(String.valueOf(correctAnswer));
        int[] commonValues = {2, 5, 10, 20, 50, 100, 200, 1000};

        for (int value : commonValues)
        {
            options.add(String.valueOf(value));
            if (options.size() == 4) break;
        }

        String[] result = options.toArray(new String[0]);
        shuffleArray(result);
        return result;
    }

    private static String[] generateIntegerOptions(int correctAnswer)
    {
        Set<String> options = new HashSet<>();
        options.add(String.valueOf(correctAnswer));
        int[] candidates = {correctAnswer + 1, correctAnswer - 1, correctAnswer + 2, correctAnswer - 2, correctAnswer + 3, correctAnswer - 3, correctAnswer * 2};

        for (int value : candidates)
        {
            if (value > 0)
            {
                options.add(String.valueOf(value));
            }
            if (options.size() == 4) break;
        }

        String[] result = options.toArray(new String[0]);
        shuffleArray(result);
        return result;
    }

    private static String[] generatePlaceValueOptions(BigDecimal correctAnswer, BigDecimal wholeAnswer)
    {
        Set<String> options = new HashSet<>();
        options.add(formatDecimal(correctAnswer));
        options.add(formatDecimal(correctAnswer.movePointRight(1)));
        options.add(formatDecimal(correctAnswer.movePointLeft(1)));
        options.add(formatDecimal(BigDecimal.valueOf(wholeAnswer.intValue())));

        int attempts = 0;
        while (options.size() < 4 && attempts < 50)
        {
            attempts++;
            BigDecimal candidate = correctAnswer.multiply(BigDecimal.valueOf(1 + RANDOM.nextInt(9)));
            options.add(formatDecimal(candidate));
        }

        if (options.size() != 4)
        {
            throw new IllegalStateException("Unable to generate place-value options");
        }

        String[] result = options.toArray(new String[0]);
        shuffleArray(result);
        return result;
    }

    private static String[] generateWordProblemOptions(BigDecimal quantity, int count, BigDecimal correctAnswer)
    {
        Set<String> options = new HashSet<>();
        options.add(formatDecimal(correctAnswer));
        options.add(formatDecimal(quantity));

        if (count > 1)
        {
            options.add(formatDecimal(quantity.multiply(BigDecimal.valueOf(count - 1))));
        }
        options.add(formatDecimal(quantity.multiply(BigDecimal.valueOf(count + 1))));

        if (options.size() < 4)
        {
            options.add(formatDecimal(correctAnswer.movePointRight(1)));
        }
        if (options.size() < 4)
        {
            options.add(formatDecimal(correctAnswer.movePointLeft(1)));
        }

        String[] result = options.toArray(new String[0]);
        shuffleArray(result);
        return result;
    }
}
