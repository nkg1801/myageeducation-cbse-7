package com.myAgeEducation.cbseClass7.maths.measurement;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MeasurementStoryQuestionGenerator {
    private static final Random RANDOM = new Random();

    private enum Operation {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
    }

    private static class StoryTemplate {
        String pattern;
        String unit1;
        String unit2;
        int factor;
        Operation operation;

        StoryTemplate(String pattern, String unit1, String unit2, int factor, Operation operation) {
            this.pattern = pattern;
            this.unit1 = unit1;
            this.unit2 = unit2;
            this.factor = factor;
            this.operation = operation;
        }
    }

    private static final StoryTemplate[] TEMPLATES = {
        new StoryTemplate(
            "A road {total1} {unit1} {total2} {unit2} long is being laid in a town. The workers lay an equal length of road each day, and complete the work in {divisor} days. How much road-laying work is done on each day?",
            "km", "m", 1000, Operation.DIVISION
        ),
        new StoryTemplate(
            "In a relief camp, each family is given {val1} {unit1} {val2} {unit2} of rice. If there are {multiplier} families, what is the total weight of rice distributed?",
            "kg", "g", 1000, Operation.MULTIPLICATION
        ),
        new StoryTemplate(
            "A milkman has two containers. One contains {val1} {unit1} {val2} {unit2} of milk and the other contains {val3} {unit1} {val4} {unit2} of milk. What is the total quantity of milk with the milkman?",
            "l", "ml", 1000, Operation.ADDITION
        ),
        new StoryTemplate(
            "A tailor had a roll of cloth {total1} {unit1} {total2} {unit2} long. He cut a piece of length {val1} {unit1} {val2} {unit2} from it for a shirt. How much cloth is left in the roll?",
            "m", "cm", 100, Operation.SUBTRACTION
        ),
        new StoryTemplate(
            "A water tank had {total1} {unit1} {total2} {unit2} of water. During the day, {val1} {unit1} {val2} {unit2} of water was used. How much water is left in the tank?",
            "l", "ml", 1000, Operation.SUBTRACTION
        ),
        new StoryTemplate(
            "An oil merchant has two tins containing {val1} {unit1} {val2} {unit2} and {val3} {unit1} {val4} {unit2} of oil respectively. What is the total quantity of oil?",
            "l", "ml", 1000, Operation.ADDITION
        ),
        new StoryTemplate(
            "A sack of potatoes weighs {val1} {unit1} {val2} {unit2}. What is the weight of {multiplier} such sacks?",
            "kg", "g", 1000, Operation.MULTIPLICATION
        ),
        new StoryTemplate(
            "A ribbon {total1} {unit1} {total2} {unit2} long is cut into {divisor} equal pieces. What is the length of each piece?",
            "m", "cm", 100, Operation.DIVISION
        )
    };

    public static Question generateQuestion() {
        StoryTemplate template = TEMPLATES[RANDOM.nextInt(TEMPLATES.length)];
        
        String questionText = "";
        String correctAnswer = "";
        int totalValue = 0;

        switch (template.operation) {
            case DIVISION:
            {
                int days = 2 + RANDOM.nextInt(7);
                int kmPerDay = 1 + RANDOM.nextInt(5);
                int mPerDay = RANDOM.nextInt(10) * 100;
                int totalM = (kmPerDay * template.factor + mPerDay) * days;
                
                totalValue = totalM;
                questionText = template.pattern
                    .replace("{total1}", String.valueOf(totalM / template.factor))
                    .replace("{total2}", String.valueOf(totalM % template.factor))
                    .replace("{divisor}", String.valueOf(days))
                    .replace("{unit1}", template.unit1)
                    .replace("{unit2}", template.unit2);
                
                correctAnswer = kmPerDay + " " + template.unit1 + " " + mPerDay + " " + template.unit2;
                break;
            }
            case MULTIPLICATION:
            {
                int families = 5 + RANDOM.nextInt(10);
                int kgPerFamily = 2 + RANDOM.nextInt(3);
                int gPerFamily = (1 + RANDOM.nextInt(9)) * 50;
                
                int totalG = (kgPerFamily * template.factor + gPerFamily) * families;
                totalValue = totalG;
                
                questionText = template.pattern
                    .replace("{val1}", String.valueOf(kgPerFamily))
                    .replace("{val2}", String.valueOf(gPerFamily))
                    .replace("{multiplier}", String.valueOf(families))
                    .replace("{unit1}", template.unit1)
                    .replace("{unit2}", template.unit2);
                
                correctAnswer = (totalG / template.factor) + " " + template.unit1 + " " + (totalG % template.factor) + " " + template.unit2;
                break;
            }
            case ADDITION:
            {
                int v1_1 = 2 + RANDOM.nextInt(5);
                int v1_2 = (1 + RANDOM.nextInt(9)) * 100;
                int v2_1 = 1 + RANDOM.nextInt(4);
                int v2_2 = (1 + RANDOM.nextInt(9)) * 100;
                
                int total = (v1_1 + v2_1) * template.factor + (v1_2 + v2_2);
                totalValue = total;
                
                questionText = template.pattern
                    .replace("{val1}", String.valueOf(v1_1))
                    .replace("{val2}", String.valueOf(v1_2))
                    .replace("{val3}", String.valueOf(v2_1))
                    .replace("{val4}", String.valueOf(v2_2))
                    .replace("{unit1}", template.unit1)
                    .replace("{unit2}", template.unit2);
                
                correctAnswer = (total / template.factor) + " " + template.unit1 + " " + (total % template.factor) + " " + template.unit2;
                break;
            }
            case SUBTRACTION:
            {
                int t1 = 10 + RANDOM.nextInt(10);
                int t2 = RANDOM.nextInt(10) * 10;
                int v1 = 2 + RANDOM.nextInt(5);
                int v2 = (1 + RANDOM.nextInt(9)) * (template.factor == 100 ? 10 : 50);
                
                int remaining = (t1 * template.factor + t2) - (v1 * template.factor + v2);
                totalValue = remaining;
                
                questionText = template.pattern
                    .replace("{total1}", String.valueOf(t1))
                    .replace("{total2}", String.valueOf(t2))
                    .replace("{val1}", String.valueOf(v1))
                    .replace("{val2}", String.valueOf(v2))
                    .replace("{unit1}", template.unit1)
                    .replace("{unit2}", template.unit2);
                
                correctAnswer = (remaining / template.factor) + " " + template.unit1 + " " + (remaining % template.factor) + " " + template.unit2;
                break;
            }
        }

        Question question = new Question();
        question.setQuestion(questionText);
        question.setAnswer(correctAnswer);
        OptionUtils.setQuestionOptions(question, generateOptions(totalValue, template.factor, template.unit1, template.unit2));
        return question;
    }

    private static String[] generateOptions(int totalValue, int factor, String unit1, String unit2) {
        Set<String> options = new LinkedHashSet<>();

        int v1 = totalValue / factor;
        int v2 = totalValue % factor;
        options.add(v1 + " " + unit1 + " " + v2 + " " + unit2);

        // Variations
        options.add((v1 + 1) + " " + unit1 + " " + v2 + " " + unit2);
        if (v1 > 1) options.add((v1 - 1) + " " + unit1 + " " + v2 + " " + unit2);
        options.add(v1 + " " + unit1 + " " + (v2 + (factor / 10)) % factor + " " + unit2);
        
        while (options.size() < 4) {
            options.add((v1 + RANDOM.nextInt(5) + 2) + " " + unit1 + " " + RANDOM.nextInt(factor) + " " + unit2);
        }

        List<String> list = new ArrayList<>(options);
        Collections.shuffle(list);
        return list.subList(0, 4).toArray(new String[0]);
    }
}
