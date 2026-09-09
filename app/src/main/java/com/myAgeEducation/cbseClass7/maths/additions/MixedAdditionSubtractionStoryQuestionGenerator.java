package com.myAgeEducation.cbseClass7.maths.additions;

import com.myAgeEducation.cbseClass7.OptionUtil;
import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;
import com.myAgeEducation.cbseClass7.maths.utils.PersonNameUtil;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MixedAdditionSubtractionStoryQuestionGenerator {
    private static final Random RANDOM = new Random();

    private static class StoryTemplate {
        String template;
        // Steps: positive for income/gift/earning, negative for spending/loss/payment
        // Indices represent values generated: 0=Initial, 1=Val1, 2=Val2, 3=Val3, 4=Val4
        int[] operations; 

        StoryTemplate(String template, int[] operations) {
            this.template = template;
            this.operations = operations;
        }
    }

    private static final String[] CITIES = {"Kolkata", "Varanasi", "Delhi", "Mumbai", "Chennai", "Bengaluru", "Ahmedabad", "Pune", "Lucknow", "Jaipur"};
    private static final String[] RELATIVES = {"uncle", "aunt", "grandfather", "grandmother", "father", "mother"};

    private static final StoryTemplate[] TEMPLATES = {
        // Scenario 1: Travel (The requested one)
        new StoryTemplate(
            "{name} is on a journey. {gender_subject} starts from {city1} with ₹{val0}. " +
            "{gender_subject} spends ₹{val1} on food and other expenses during {gender_possessive} trip to {city2}. " +
            "In {city2}, {gender_possessive} {relative} gives {gender_object} a gift worth ₹{val2}. " +
            "{gender_subject} then travels to {city3}, spending ₹{val3} on the ticket. " +
            "{gender_subject} spends ₹{val4} on souvenirs in {city3}. " +
            "How much money is {name} left with at the end of the journey?",
            new int[]{1, -1, 1, -1, -1} // Initial - Spend1 + Income1 - Spend2 - Spend3
        ),
        // Scenario 2: Business/Shopkeeper
        new StoryTemplate(
            "A shopkeeper starts the day with ₹{val0} in the cash box. " +
            "He sells goods worth ₹{val1} in the morning. " +
            "He pays ₹{val2} to a supplier for new stock. " +
            "In the afternoon, he sells more items for ₹{val3}. " +
            "He then pays ₹{val4} for electricity bill from the cash box. " +
            "How much money is left in the cash box now?",
            new int[]{1, 1, -1, 1, -1} // Initial + Income1 - Spend1 + Income2 - Spend2
        ),
        // Scenario 3: Monthly Budget/Savings
        new StoryTemplate(
            "{name} had ₹{val0} in {gender_possessive} bank account. " +
            "{gender_subject} received a salary of ₹{val1}. " +
            "{gender_subject} paid ₹{val2} for house rent and ₹{val3} for groceries. " +
            "{gender_possessive} friend returned ₹{val4} that {gender_subject} had borrowed. " +
            "What is the final balance in {name}'s bank account?",
            new int[]{1, 1, -1, -1, 1} // Initial + Income1 - Spend1 - Spend2 + Income3
        ),
        // Scenario 4: School Event
        new StoryTemplate(
            "A school collected ₹{val0} for a charity event. " +
            "The school spent ₹{val1} on decorations and ₹{val2} on snacks. " +
            "A local businessman donated ₹{val3} to the fund. " +
            "The school also spent ₹{val4} on prizes for the winners. " +
            "How much money is remaining in the charity fund?",
            new int[]{1, -1, -1, 1, -1} // Initial - Spend1 - Spend2 + Income1 - Spend3
        ),
        // Scenario 5: Farmer
        new StoryTemplate(
            "A farmer had ₹{val0} in savings. " +
            "He spent ₹{val1} on seeds and ₹{val2} on fertilizers. " +
            "After selling his crop, he earned ₹{val3}. " +
            "He then bought a new tool for ₹{val4}. " +
            "Calculate the total amount the farmer has now.",
            new int[]{1, -1, -1, 1, -1}
        ),
        // Scenario 6: Library
        new StoryTemplate(
            "A public library has a budget of ₹{val0}. " +
            "It bought new story books for ₹{val1} and science books for ₹{val2}. " +
            "The library received a government grant of ₹{val3}. " +
            "It then paid ₹{val4} for furniture repairs. " +
            "How much budget is left with the library?",
            new int[]{1, -1, -1, 1, -1}
        ),
        // Scenario 7: Construction/Contractor
        new StoryTemplate(
            "A contractor was given ₹{val0} to build a wall. " +
            "He spent ₹{val1} on bricks and ₹{val2} on cement. " +
            "The owner gave him an extra ₹{val3} for good work. " +
            "He then paid ₹{val4} to the laborers. " +
            "Calculate the final amount the contractor has left.",
            new int[]{1, -1, -1, 1, -1}
        ),
        // Scenario 8: Birthday Party
        new StoryTemplate(
            "{name} received ₹{val0} as a birthday gift from {gender_possessive} parents. " +
            "{gender_subject} spent ₹{val1} on a cake and ₹{val2} on decorations. " +
            "{gender_possessive} {relative} gave {gender_object} another ₹{val3}. " +
            "{gender_subject} then bought a return gift for a friend for ₹{val4}. " +
            "How much money does {name} have now?",
            new int[]{1, -1, -1, 1, -1}
        ),
        // Scenario 9: Factory
        new StoryTemplate(
            "A small factory had ₹{val0} in its account. " +
            "It earned ₹{val1} by selling toys. " +
            "The factory paid ₹{val2} for raw materials and ₹{val3} for rent. " +
            "It then earned ₹{val4} more from a new order. " +
            "What is the current balance in the factory's account?",
            new int[]{1, 1, -1, -1, 1}
        ),
        // Scenario 10: Sports Team
        new StoryTemplate(
            "A cricket team had ₹{val0} in their fund. " +
            "They spent ₹{val1} on new bats and ₹{val2} on balls. " +
            "A sponsor donated ₹{val3} to the team. " +
            "The team then spent ₹{val4} on lunch after a match. " +
            "How much money is left in the team fund?",
            new int[]{1, -1, -1, 1, -1}
        ),
        // Scenario 11: Bakery
        new StoryTemplate(
            "A bakery had ₹{val0} worth of ingredients. " +
            "It sold cakes worth ₹{val1} in one day. " +
            "The baker paid ₹{val2} for a new oven repair. " +
            "He then earned ₹{val3} by selling custom cookies. " +
            "Finally, he paid ₹{val4} for packaging boxes. " +
            "Calculate the total cash remaining with the baker.",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 12: Garden/Nursery
        new StoryTemplate(
            "A gardener had ₹{val0} in his savings. " +
            "He sold plants worth ₹{val1} in the morning. " +
            "He spent ₹{val2} on high-quality manure. " +
            "In the evening, he sold decorative pots for ₹{val3}. " +
            "He then paid ₹{val4} for the water bill. " +
            "How much money does he have now?",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 13: NGO/Charity
        new StoryTemplate(
            "A charity group started with ₹{val0} in their fund. " +
            "They received donations worth ₹{val1} from the public. " +
            "They spent ₹{val2} on food kits for the needy. " +
            "A local company donated ₹{val3} to their cause. " +
            "The group then paid ₹{val4} for renting a community hall. " +
            "What is the final balance in the charity fund?",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 14: Book Fair
        new StoryTemplate(
            "A bookseller took ₹{val0} to a book fair. " +
            "He earned ₹{val1} by selling science books. " +
            "He paid ₹{val2} as the stall rent for the day. " +
            "He earned ₹{val3} more by selling storybooks. " +
            "He then spent ₹{val4} on his own lunch and travel. " +
            "How much money does the bookseller have left?",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 15: Tailoring Shop
        new StoryTemplate(
            "A tailor had ₹{val0} in his cash drawer. " +
            "He earned ₹{val1} for stitching five suits. " +
            "He spent ₹{val2} on buying colorful threads and needles. " +
            "He earned ₹{val3} more for altering old clothes. " +
            "He then paid ₹{val4} for a new pair of scissors. " +
            "How much money is now in his cash drawer?",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 16: Poultry Farm
        new StoryTemplate(
            "A poultry farmer had ₹{val0} in his savings. " +
            "He earned ₹{val1} by selling eggs to a local shop. " +
            "He spent ₹{val2} on buying nutritious bird feed. " +
            "He then earned ₹{val3} by selling some chickens. " +
            "Finally, he paid ₹{val4} for the vet's visit and medicines. " +
            "How much total money does the farmer have now?",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 17: Hospital/Clinic
        new StoryTemplate(
            "A small clinic has a daily operational budget of ₹{val0}. " +
            "It collected ₹{val1} as fees from visiting patients. " +
            "The clinic paid ₹{val2} for buying fresh surgical masks and gloves. " +
            "It earned ₹{val3} more from providing lab reports. " +
            "The owner then paid ₹{val4} for the assistant's daily wage. " +
            "What is the final amount left in the clinic's account?",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 18: Hotel/Restaurant
        new StoryTemplate(
            "A restaurant manager starts the shift with ₹{val0} at the counter. " +
            "The restaurant earns ₹{val1} from lunch orders. " +
            "The manager pays ₹{val2} to a vegetable vendor for daily supply. " +
            "In the evening, the restaurant earns ₹{val3} from dinner orders. " +
            "Finally, ₹{val4} is paid for the dishwashing staff's wages. " +
            "How much cash is remaining at the counter?",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 19: Car Repair Shop
        new StoryTemplate(
            "A mechanic had ₹{val0} in his pocket. " +
            "He earned ₹{val1} for repairing a car's engine. " +
            "He spent ₹{val2} on buying engine oil and a filter. " +
            "He earned another ₹{val3} for washing and polishing two bikes. " +
            "He then paid ₹{val4} for a new wrench set. " +
            "How much money does the mechanic have left?",
            new int[]{1, 1, -1, 1, -1}
        ),
        // Scenario 20: Stationery Shop
        new StoryTemplate(
            "A stationery shopkeeper had ₹{val0} in the morning. " +
            "He sold notebook sets worth ₹{val1}. " +
            "He paid ₹{val2} for buying a new stock of pens and erasers. " +
            "He earned ₹{val3} more by selling drawing books. " +
            "He then paid ₹{val4} for his shop's daily maintenance. " +
            "How much money does he have at the end of the day?",
            new int[]{1, 1, -1, 1, -1}
        )
    };

    public static Question generateQuestion() {
        StoryTemplate st = TEMPLATES[RANDOM.nextInt(TEMPLATES.length)];
        
        boolean isMale = RANDOM.nextBoolean();
        String name = isMale ? PersonNameUtil.getMaleName() : PersonNameUtil.getFemaleName();
        String gender_subject = isMale ? "He" : "She";
        String gender_possessive = isMale ? "his" : "her";
        String gender_object = isMale ? "him" : "her";

        List<String> cities = new ArrayList<>();
        Collections.addAll(cities, CITIES);
        Collections.shuffle(cities);

        int[] vals = new int[5];
        vals[0] = 10000 + RANDOM.nextInt(40000); // Initial 10k to 50k
        
        // We need to ensure values don't go negative during the steps
        int current = vals[0];
        for (int i = 1; i < 5; i++) {
            if (st.operations[i] > 0) {
                vals[i] = 1000 + RANDOM.nextInt(10000); // Income 1k to 11k
            } else {
                // Spending - must be less than current balance to stay realistic
                int maxSpend = Math.max(1000, current / 3);
                vals[i] = 500 + RANDOM.nextInt(maxSpend);
            }
            current += st.operations[i] * vals[i];
        }

        String questionText = st.template
            .replace("{name}", name)
            .replace("{gender_subject}", gender_subject)
            .replace("{gender_possessive}", gender_possessive)
            .replace("{gender_object}", gender_object)
            .replace("{city1}", cities.get(0))
            .replace("{city2}", cities.get(1))
            .replace("{city3}", cities.get(2))
            .replace("{relative}", RELATIVES[RANDOM.nextInt(RELATIVES.length)])
            .replace("{val0}", NumberFormatUtil.formatIndianNumber(vals[0]))
            .replace("{val1}", NumberFormatUtil.formatIndianNumber(vals[1]))
            .replace("{val2}", NumberFormatUtil.formatIndianNumber(vals[2]))
            .replace("{val3}", NumberFormatUtil.formatIndianNumber(vals[3]))
            .replace("{val4}", NumberFormatUtil.formatIndianNumber(vals[4]));

        int answer = current;
        String correctAnswerStr = NumberFormatUtil.formatIndianNumber(answer);

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswerStr);
        OptionUtils.setQuestionOptions(question, generateOptions(answer));
        
        return question;
    }

    private static String[] generateOptions(int answer) {
        Set<String> distractors = new LinkedHashSet<>();
        distractors.add(NumberFormatUtil.formatIndianNumber(answer + 100));
        distractors.add(NumberFormatUtil.formatIndianNumber(answer - 100));
        distractors.add(NumberFormatUtil.formatIndianNumber(answer + 500));
        distractors.add(NumberFormatUtil.formatIndianNumber(answer - 500));
        distractors.add(NumberFormatUtil.formatIndianNumber(answer + 1000));
        distractors.add(NumberFormatUtil.formatIndianNumber(answer - 1000));

        return OptionUtil.createOptions(NumberFormatUtil.formatIndianNumber(answer), distractors, 4);
    }
}
