package com.myAgeEducation.cbseClass7.maths.multiples;

public class MultipleQuestionTemplatesUtil {

    public static String[] getQuestionTemplates(MultipleQuestionType type) {
        switch (type) {
            case MULTIPLE_TRUE_FALSE:
                return templatesForMultipleTrueFalse;
            case LIST_MULTIPLE_TRUE_FALSE:
                return templatesForListMultipleTrueFalse;
            case PROPERTY_ONE_MULTIPLE:
                return templatesForPropertyOneMultiple;
            case PROPERTY_SELF_MULTIPLE:
                return templatesForPropertySelfMultiple;
            case PROPERTY_MULTIPLE_OF_ONE:
                return templatesForPropertyMultipleOfOne;
            case COMMON_MULTIPLE:
                return templatesForCommonMultiple;
            case FIRST_FIVE_COMMON_MULTIPLES:
                return templatesForFirstFiveCommonMultiples;
            case COMMON_MULTIPLE_WORD_PROBLEM:
                return templatesForCommonMultipleWordProblem;
            default:
                return new String[]{};
        }
    }

    private static final String[] templatesForMultipleTrueFalse = {
            "%d is a multiple of %d. TRUE or FALSE?",
            "Is it true that %d is a multiple of %d?",
            "TRUE or FALSE: %d is a multiple of %d."
    };

    private static final String[] templatesForListMultipleTrueFalse = {
            "%s are all multiples of %d. TRUE or FALSE?",
            "TRUE or FALSE: The numbers %s are all multiples of %d.",
            "Regarding the numbers %s, are they all multiples of %d?"
    };

    private static final String[] templatesForPropertyOneMultiple = {
            "1 is a multiple of every number. TRUE or FALSE?",
            "TRUE or FALSE: 1 is a multiple of all integers.",
            "Is 1 a multiple of every number?"
    };

    private static final String[] templatesForPropertySelfMultiple = {
            "A number is a multiple of itself. TRUE or FALSE?",
            "TRUE or FALSE: Every number is a multiple of itself.",
            "Is it true that a number is a multiple of itself?"
    };

    private static final String[] templatesForPropertyMultipleOfOne = {
            "Every number is a multiple of 1. TRUE or FALSE?",
            "TRUE or FALSE: 1 is a divisor of every number, so every number is a multiple of 1.",
            "Is every number a multiple of 1?"
    };

    private static final String[] templatesForCommonMultiple = {
            "Which of the following is a common multiple of %d and %d?",
            "Pick a common multiple of %d and %d.",
            "Find a number that is a multiple of both %d and %d.",
            "Select the common multiple of %d and %d from the options below."
    };

    private static final String[] templatesForFirstFiveCommonMultiples = {
            "Find the first five common multiples of %d and %d.",
            "What are the first five common multiples of %d and %d?",
            "Identify the first five common multiples of the following pair of numbers: %d and %d.",
            "List the first five common multiples of %d and %d."
    };

    private static final String[] templatesForCommonMultipleWordProblem = {
            "%s, the %s, goes hunting every %s day. %s, the %s, goes hunting every %s day. If both of them start on the same day, on which days will they be hunting together?",
            "Two bells toll at intervals of %d and %d minutes respectively. If they toll together at 10:00 AM, at what time will they toll together again?",
            "Three traffic lights change after every %d, %d and %d seconds respectively. If they all change together at 8:00 AM, when will they change together again?",
            "Two friends, %s and %s, go for a run around a track. %s takes %d minutes to complete a lap, while %s takes %d minutes. If they start together, after how many minutes will they meet at the starting point again?"
    };
}
