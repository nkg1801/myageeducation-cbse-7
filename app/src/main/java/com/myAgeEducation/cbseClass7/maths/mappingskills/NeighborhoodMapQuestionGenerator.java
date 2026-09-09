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

public class NeighborhoodMapQuestionGenerator {
    private static final Random RANDOM = new Random();
    private static final int GRID_SIZE = 10;

    private static final MapLandmark[] HOUSES = {
            new MapLandmark("lali_house", "Lali's House", MapLandmark.Type.HOUSE),
            new MapLandmark("rohan_house", "Rohan's House", MapLandmark.Type.HOUSE),
            new MapLandmark("ravi_house", "Ravi's House", MapLandmark.Type.HOUSE),
            new MapLandmark("tinku_house", "Tinku's House", MapLandmark.Type.HOUSE),
            new MapLandmark("golu_house", "Golu's House", MapLandmark.Type.HOUSE),
            new MapLandmark("jaideep_house", "Jaideep's House", MapLandmark.Type.HOUSE),
            new MapLandmark("prem_house", "Prem's House", MapLandmark.Type.HOUSE),
            new MapLandmark("raju_house", "Raju's House", MapLandmark.Type.HOUSE)
    };

    private static final MapLandmark[] FACILITIES = {
            new MapLandmark("hospital", "Hospital", MapLandmark.Type.FACILITY),
            new MapLandmark("shopping_center", "Shopping Centre", MapLandmark.Type.FACILITY),
            new MapLandmark("basketball_court", "Basketball Court", MapLandmark.Type.FACILITY),
            new MapLandmark("children_park", "Children's Park", MapLandmark.Type.FACILITY),
            new MapLandmark("parking", "Parking", MapLandmark.Type.FACILITY)
    };

    private static final MapLandmark[] STOPS = {
            new MapLandmark("stop1", "Stop 1", MapLandmark.Type.STOP),
            new MapLandmark("stop2", "Stop 2", MapLandmark.Type.STOP)
    };

    private NeighborhoodMapQuestionGenerator() {}

    public static Question generateQuestion() {
        List<MapLandmark> allOnMap = pickLandmarks();
        assignPositions(allOnMap);

        Question question = new Question();
        int type = RANDOM.nextInt(3);

        switch (type) {
            case 0:
                generateDirectionalQuestion(question, allOnMap);
                break;
            case 1:
                generateProximityQuestion(question, allOnMap);
                break;
            default:
                generateNavigationQuestion(question, allOnMap);
                break;
        }

        question.setImage(createImageCode(allOnMap));
        return question;
    }

    private static List<MapLandmark> pickLandmarks() {
        List<MapLandmark> result = new ArrayList<>();
        
        // Pick some houses
        List<MapLandmark> housePool = new ArrayList<>();
        Collections.addAll(housePool, HOUSES);
        Collections.shuffle(housePool);
        result.addAll(housePool.subList(0, 5));

        // Pick some facilities
        List<MapLandmark> facilityPool = new ArrayList<>();
        Collections.addAll(facilityPool, FACILITIES);
        Collections.shuffle(facilityPool);
        result.addAll(facilityPool.subList(0, 3));

        // Add both stops
        Collections.addAll(result, STOPS);

        return result;
    }

    private static void assignPositions(List<MapLandmark> landmarks) {
        Set<String> used = new HashSet<>();
        // Reserve top-right corner for compass (approximately x >= 9 and y >= 9)
        for (MapLandmark l : landmarks) {
            int x, y;
            int attempts = 0;
            do {
                x = 1 + RANDOM.nextInt(GRID_SIZE);
                y = 1 + RANDOM.nextInt(GRID_SIZE);
                attempts++;
            } while (isInvalidPosition(x, y, used) && attempts < 100);
            
            used.add(x + "," + y);
            l.setX(x);
            l.setY(y);
        }
    }

    private static boolean isInvalidPosition(int x, int y, Set<String> used) {
        // Avoid compass area (top-right)
        if (x >= 9 && y >= 9) return true;

        // Check for proximity to existing landmarks to avoid label overlap
        for (String pos : used) {
            String[] parts = pos.split(",");
            int ux = Integer.parseInt(parts[0]);
            int uy = Integer.parseInt(parts[1]);

            // Ensure at least 1 cell gap in any direction
            if (Math.abs(x - ux) <= 1 && Math.abs(y - uy) <= 1) {
                return true;
            }
        }
        return false;
    }

    private static String FIXED_QUESTION_TEXT = "The map below shows your neighborhood. Looking at the map, answer the following question:" +  System.lineSeparator() + System.lineSeparator();

    private static void generateDirectionalQuestion(Question question, List<MapLandmark> landmarks) {
        MapLandmark pivot = landmarks.get(RANDOM.nextInt(landmarks.size()));
        String[] directions = {"north", "south", "east", "west"};
        String dir = directions[RANDOM.nextInt(4)];

        List<MapLandmark> matches = new ArrayList<>();
        for (MapLandmark l : landmarks) {
            if (l == pivot) continue;
            if (dir.equals("north") && l.getY() > pivot.getY() && l.getX() == pivot.getX()) matches.add(l);
            if (dir.equals("south") && l.getY() < pivot.getY() && l.getX() == pivot.getX()) matches.add(l);
            if (dir.equals("east") && l.getX() > pivot.getX() && l.getY() == pivot.getY()) matches.add(l);
            if (dir.equals("west") && l.getX() < pivot.getX() && l.getY() == pivot.getY()) matches.add(l);
        }

        if (matches.isEmpty()) {
            // Try another pivot or fallback
            generateProximityQuestion(question, landmarks);
            return;
        }

        MapLandmark target = matches.get(RANDOM.nextInt(matches.size()));
        question.setQuestion(FIXED_QUESTION_TEXT + "Whose house or building is situated to the " + dir + " of " + pivot.getLabel() + "?");
        String answer = target.getLabel();
        question.setAnswer(answer);

        List<String> options = new ArrayList<>();
        options.add(answer);
        while (options.size() < 4) {
            MapLandmark opt = landmarks.get(RANDOM.nextInt(landmarks.size()));
            if (!options.contains(opt.getLabel())) {
                options.add(opt.getLabel());
            }
        }
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateProximityQuestion(Question question, List<MapLandmark> landmarks) {
        MapLandmark pivot = null;
        for(MapLandmark l : landmarks) {
            if(l.getType() == MapLandmark.Type.HOUSE) {
                pivot = l;
                break;
            }
        }
        if(pivot == null) pivot = landmarks.get(0);

        question.setQuestion(FIXED_QUESTION_TEXT + "Which stop is closer to " + pivot.getLabel() + "?");
        
        MapLandmark s1 = null, s2 = null;
        for(MapLandmark l : landmarks) {
            if(l.getName().equals("stop1")) s1 = l;
            if(l.getName().equals("stop2")) s2 = l;
        }

        if(s1 == null || s2 == null) {
            generateNavigationQuestion(question, landmarks);
            return;
        }

        double d1 = Math.sqrt(Math.pow(s1.getX() - pivot.getX(), 2) + Math.pow(s1.getY() - pivot.getY(), 2));
        double d2 = Math.sqrt(Math.pow(s2.getX() - pivot.getX(), 2) + Math.pow(s2.getY() - pivot.getY(), 2));

        String answer = d1 < d2 ? "Stop 1" : "Stop 2";
        question.setAnswer(answer);
        
        List<String> options = new ArrayList<>();
        options.add("Stop 1");
        options.add("Stop 2");
        options.add("Both are at same distance");
        options.add("None of these");
        
        if (Math.abs(d1 - d2) < 0.1) {
            question.setAnswer("Both are at same distance");
        }

        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateNavigationQuestion(Question question, List<MapLandmark> landmarks) {
        MapLandmark start = null;
        for(MapLandmark l : landmarks) {
            if(l.getType() == MapLandmark.Type.HOUSE) {
                start = l;
                break;
            }
        }
        if(start == null) start = landmarks.get(0);

        MapLandmark end = STOPS[RANDOM.nextInt(2)];
        
        String person = start.getLabel().replace("'s House", "");
        question.setQuestion(FIXED_QUESTION_TEXT + "In which direction would " + person + " have to move to reach " + end.getLabel() + "?");
        
        String horizontal = "";
        if (end.getX() > start.getX()) horizontal = "East";
        else if (end.getX() < start.getX()) horizontal = "West";

        String vertical = "";
        if (end.getY() > start.getY()) vertical = "North";
        else if (end.getY() < start.getY()) vertical = "South";

        String answer = "";
        if (!horizontal.isEmpty() && !vertical.isEmpty()) {
            answer = vertical + "-" + horizontal;
        } else if (!horizontal.isEmpty()) {
            answer = horizontal;
        } else if (!vertical.isEmpty()) {
            answer = vertical;
        } else {
            answer = "Already there";
        }

        question.setAnswer(answer);
        
        List<String> options = new ArrayList<>();
        options.add(answer);
        String[] allDirs = {"North", "South", "East", "West", "North-East", "North-West", "South-East", "South-West"};
        List<String> pool = new ArrayList<>();
        Collections.addAll(pool, allDirs);
        Collections.shuffle(pool);
        
        for (String d : pool) {
            if (options.size() < 4 && !options.contains(d)) {
                options.add(d);
            }
        }
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);

    }

    private static String createImageCode(List<MapLandmark> landmarks) {
        StringBuilder sb = new StringBuilder(ImageCodeType.NEIGHBORHOOD_MAP);
        sb.append("_").append(landmarks.size());
        for (MapLandmark l : landmarks) {
            sb.append(":").append(l.getName())
              .append(":").append(l.getX())
              .append(":").append(l.getY());
        }
        return sb.toString();
    }
}
