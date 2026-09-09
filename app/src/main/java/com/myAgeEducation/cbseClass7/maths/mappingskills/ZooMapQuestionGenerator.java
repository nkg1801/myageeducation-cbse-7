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

public class ZooMapQuestionGenerator {
    private static final Random RANDOM = new Random();
    private static final int GRID_SIZE = RANDOM.nextInt(5) + 8; // 8 to 12

    private static final ZooAnimal[] ALL_ANIMALS = {
            new ZooAnimal("lion", "lion"),
            new ZooAnimal("tiger", "tiger"),
            new ZooAnimal("elephant", "elephant"),
            new ZooAnimal("zebra", "zebra"),
            new ZooAnimal("giraffe", "giraffe"),
            new ZooAnimal("panda", "panda"),
            new ZooAnimal("deer", "deer"),
            new ZooAnimal("monkey", "monkey"),
            new ZooAnimal("crocodile", "crocodile"),
            new ZooAnimal("snake", "snake"),
            new ZooAnimal("turtle", "turtle"),
            new ZooAnimal("parrot", "parrot"),
            new ZooAnimal("flamingo", "flamingo"),
            new ZooAnimal("toucan", "toucan"),
            new ZooAnimal("hippo", "hippo"),
            new ZooAnimal("cat", "cat"),
            new ZooAnimal("fish", "fish"),
            new ZooAnimal("duck", "duck"),
            new ZooAnimal("hen", "hen"),
            new ZooAnimal("peacock", "peacock"),
            new ZooAnimal("camel", "camel"),
            new ZooAnimal("cat", "cat"),
            new ZooAnimal("pigeon", "pigeon"),
            new ZooAnimal("crow", "crow"),
    };

    private ZooMapQuestionGenerator() {}

    public static Question generateQuestion() {
        List<ZooAnimal> selectedAnimals = pickRandomAnimals(GRID_SIZE - 4);
        assignRandomPositions(selectedAnimals);

        int questionType = RANDOM.nextInt(3);
        Question question = new Question();

        if (questionType == 0) {
            generateLocateAnimalAtPositionQuestion(question, selectedAnimals);
        } else if (questionType == 1) {
            generateFindPositionOfAnimalQuestion(question, selectedAnimals);
        } else {
            generateTrueFalsePositionQuestion(question, selectedAnimals);
        }

        question.setImage(createImageCode(selectedAnimals));
        return question;
    }

    private static List<ZooAnimal> pickRandomAnimals(int count) {
        List<ZooAnimal> pool = new ArrayList<>();
        Collections.addAll(pool, ALL_ANIMALS);
        Collections.shuffle(pool, RANDOM);
        return pool.subList(0, count);
    }

    private static void assignRandomPositions(List<ZooAnimal> animals) {
        Set<String> usedPositions = new HashSet<>();
        for (ZooAnimal animal : animals) {
            int x, y;
            do {
                x = 1 + RANDOM.nextInt(GRID_SIZE);
                y = 1 + RANDOM.nextInt(GRID_SIZE);
            } while (usedPositions.contains(x + "," + y));
            usedPositions.add(x + "," + y);
            animal.setX(x);
            animal.setY(y);
        }
    }

    private static void generateLocateAnimalAtPositionQuestion(Question question, List<ZooAnimal> animals) {
        ZooAnimal target = animals.get(RANDOM.nextInt(animals.size()));
        question.setQuestion("Locate the animal at positions (" + target.getX() + ", " + target.getY() + ") on the map.");
        
        String answer = target.getName();
        question.setAnswer(answer);

        List<String> options = new ArrayList<>();
        options.add(answer);
        
        List<ZooAnimal> distractorPool = new ArrayList<>(animals);
        distractorPool.remove(target);
        Collections.shuffle(distractorPool);
        
        for (int i = 0; i < 3 && i < distractorPool.size(); i++) {
            options.add(distractorPool.get(i).getName());
        }
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateFindPositionOfAnimalQuestion(Question question, List<ZooAnimal> animals) {
        ZooAnimal target = animals.get(RANDOM.nextInt(animals.size()));
        question.setQuestion("What is the position of the " + target.getName() + " on the map?");
        
        String answer = "(" + target.getX() + ", " + target.getY() + ")";
        question.setAnswer(answer);

        List<String> options = new ArrayList<>();
        options.add(answer);

        Set<String> usedOptions = new HashSet<>();
        usedOptions.add(answer);

        while (options.size() < 4) {
            int rx = 1 + RANDOM.nextInt(GRID_SIZE);
            int ry = 1 + RANDOM.nextInt(GRID_SIZE);
            String opt = "(" + rx + ", " + ry + ")";
            if (!usedOptions.contains(opt)) {
                options.add(opt);
                usedOptions.add(opt);
            }
        }
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateTrueFalsePositionQuestion(Question question, List<ZooAnimal> animals) {
        ZooAnimal target = animals.get(RANDOM.nextInt(animals.size()));
        boolean isTrue = RANDOM.nextBoolean();

        int x = target.getX();
        int y = target.getY();

        if (!isTrue) {
            // Generate a false position
            int oldX = x;
            int oldY = y;
            do {
                if (RANDOM.nextBoolean()) {
                    x = 1 + RANDOM.nextInt(GRID_SIZE);
                } else {
                    y = 1 + RANDOM.nextInt(GRID_SIZE);
                }
            } while (x == oldX && y == oldY);
        }

        question.setQuestion("The position of the " + target.getName() + " is (" + x + ", " + y + "). TRUE or FALSE?");
        String answer = isTrue ? "TRUE" : "FALSE";
        question.setAnswer(answer);

        OptionUtils.setQuestionOptions(question, new String[]{"TRUE", "FALSE"});
    }

    private static String createImageCode(List<ZooAnimal> animals) {
        StringBuilder sb = new StringBuilder(ImageCodeType.ZOO_MAP);
        sb.append("_").append(animals.size());
        for (ZooAnimal animal : animals) {
            sb.append("_").append(animal.getImageName())
              .append("_").append(animal.getX())
              .append("_").append(animal.getY());
        }
        return sb.toString();
    }
}
