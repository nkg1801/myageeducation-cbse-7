package com.myAgeEducation.cbseClass7.maths.primecomposite;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PrimeCompositeQuestionGenerator {
    private static final Random RANDOM = new Random();

    public static Question generateQuestion() {
        PrimeCompositeQuestionType type = PrimeCompositeQuestionType.values()[RANDOM.nextInt(PrimeCompositeQuestionType.values().length)];
        switch (type) {
            case DEFINITION_PRIME:
                return generateDefinitionPrime();
            case DEFINITION_COMPOSITE:
                return generateDefinitionComposite();
            case IDENTIFY_PRIME:
                return generateIdentifyPrime();
            case IDENTIFY_COMPOSITE:
                return generateIdentifyComposite();
            case TRUE_FALSE_PRIME:
                return generateTrueFalsePrime();
            case TRUE_FALSE_COMPOSITE:
                return generateTrueFalseComposite();
            case SMALLEST_PRIME_COMPOSITE:
                return generateSmallestPrimeComposite();
            case EVEN_ODD_PRIME_PROPERTIES:
                return generateEvenOddPrimeProperties();
            default:
                return generateDefinitionPrime();
        }
    }

    private static Question generateDefinitionPrime() {
        Question question = new Question();
        question.setQuestion("Numbers more than 1 that have only two factors are called ______ numbers.");
        question.setAnswer("prime");
        OptionUtils.setQuestionOptions(question, new String[]{"prime", "composite", "even", "odd"});
        return question;
    }

    private static Question generateDefinitionComposite() {
        Question question = new Question();
        question.setQuestion("Numbers more than 1 that have more than two factors are called ______ numbers.");
        question.setAnswer("composite");
        OptionUtils.setQuestionOptions(question, new String[]{"composite", "prime", "even", "odd"});
        return question;
    }

    private static Question generateIdentifyPrime() {
        int prime = getRandomPrime(2, 50);
        List<String> options = new ArrayList<>();
        options.add(String.valueOf(prime));
        while (options.size() < 4) {
            int composite = 4 + RANDOM.nextInt(46);
            if (!isPrime(composite) && !options.contains(String.valueOf(composite))) {
                options.add(String.valueOf(composite));
            }
        }
        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion("Which of the following is a prime number?");
        question.setAnswer(String.valueOf(prime));
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateIdentifyComposite() {
        int composite = getRandomComposite(4, 50);
        List<String> options = new ArrayList<>();
        options.add(String.valueOf(composite));
        while (options.size() < 4) {
            int prime = getRandomPrime(2, 50);
            if (!options.contains(String.valueOf(prime))) {
                options.add(String.valueOf(prime));
            }
        }
        Collections.shuffle(options);

        Question question = new Question();
        question.setQuestion("Which of the following is a composite number?");
        question.setAnswer(String.valueOf(composite));
        OptionUtils.setQuestionOptions(question, options.toArray(new String[0]));
        return question;
    }

    private static Question generateTrueFalsePrime() {
        int num = 2 + RANDOM.nextInt(49);
        boolean isPrimeNum = isPrime(num);
        
        boolean statementIsTrue = RANDOM.nextBoolean();
        int displayNum = statementIsTrue ? (isPrimeNum ? num : getRandomPrime(2, 50)) : (isPrimeNum ? getRandomComposite(4, 50) : num);
        
        // Simpler logic for TF
        int testNum = 2 + RANDOM.nextInt(49);
        boolean actualPrime = isPrime(testNum);
        boolean claimPrime = RANDOM.nextBoolean();

        Question question = new Question();
        question.setQuestion("TRUE or FALSE. " + testNum + " is a prime number.");
        question.setAnswer(actualPrime == claimPrime ? (claimPrime ? "TRUE" : "FALSE") : (claimPrime ? "FALSE" : "TRUE"));
        
        // Wait, the above logic is confusing. Let's redo.
        boolean showPrime = isPrime(testNum);
        question.setQuestion("TRUE or FALSE. " + testNum + " is a prime number.");
        question.setAnswer(showPrime ? "TRUE" : "FALSE");
        
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static Question generateTrueFalseComposite() {
        int testNum = 2 + RANDOM.nextInt(49);
        boolean showComposite = !isPrime(testNum) && testNum > 1;
        
        Question question = new Question();
        question.setQuestion("TRUE or FALSE. " + testNum + " is a composite number.");
        question.setAnswer(showComposite ? "TRUE" : "FALSE");
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static Question generateSmallestPrimeComposite() {
        int type = RANDOM.nextInt(4);
        String questionText;
        String answer;
        String[] options;
        switch (type) {
            case 0:
                questionText = "The smallest prime number is:";
                answer = "2";
                options = new String[]{"0", "1", "2", "3"};
                break;
            case 1:
                questionText = "What is the smallest composite number?";
                answer = "4";
                options = new String[]{"1", "2", "3", "4"};
                break;
            case 2:
                questionText = "What is the smallest odd prime number?";
                answer = "3";
                options = new String[]{"1", "2", "3", "5"};
                break;
            default:
                questionText = "Which number is neither prime nor composite?";
                answer = "1";
                options = new String[]{"0", "1", "2", "3"};
                break;
        }
        
        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(answer);
        OptionUtils.setQuestionOptions(question, options);
        return question;
    }

    private static Question generateEvenOddPrimeProperties() {
        String[][] bank = {
                {"TRUE or FALSE. Every prime number is odd.", "FALSE"},
                {"TRUE or FALSE. Every prime number is even.", "FALSE"},
                {"TRUE or FALSE. Every prime number except 2 is odd.", "TRUE"},
                {"TRUE or FALSE. The sum of three odd numbers is even.", "FALSE"},
                {"TRUE or FALSE. The sum of two odd numbers and one even number is even.", "TRUE"},
                {"TRUE or FALSE. The product of three odd numbers is odd.", "TRUE"},
                {"TRUE or FALSE. If an even number is divided by 2, the quotient is always odd.", "FALSE"},
                {"TRUE or FALSE. Prime numbers do not have any factors.", "FALSE"},
                {"TRUE or FALSE. Sum of two prime numbers is always even.", "FALSE"},
                {"TRUE or FALSE. 2 is the only even prime number.", "TRUE"},
                {"TRUE or FALSE. The product of two even numbers is always even.", "TRUE"},
                {"TRUE or FALSE. All even numbers are composite numbers.", "FALSE"}
        };

        int idx = RANDOM.nextInt(bank.length);
        String[] item = bank[idx];

        Question question = new Question();
        question.setQuestion(item[0]);
        question.setAnswer(item[1]);
        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
        return question;
    }

    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private static int getRandomPrime(int min, int max) {
        int num;
        do {
            num = min + RANDOM.nextInt(max - min + 1);
        } while (!isPrime(num));
        return num;
    }

    private static int getRandomComposite(int min, int max) {
        int num;
        do {
            num = min + RANDOM.nextInt(max - min + 1);
        } while (isPrime(num) || num <= 1);
        return num;
    }
}
