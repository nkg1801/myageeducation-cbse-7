package com.myAgeEducation.cbseClass7.maths.mappingskills;

import android.graphics.Color;

import com.myAgeEducation.cbseClass7.Question;
import com.myAgeEducation.cbseClass7.utils.ImageCodeType;
import com.myAgeEducation.cbseClass7.utils.OptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MetroMapQuestionGenerator {
    private static final Random RANDOM = new Random();

    private static class MapData {
        String cityName;
        List<MetroLine> lines = new ArrayList<>();
        List<MetroStation> allStations = new ArrayList<>();
        String routeStart;
        String routeEnd;
        String routeChange;
    }

    public static Question generateQuestion() {
        MapData mapData = createMapData();
        Question question = new Question();
        
        int type = RANDOM.nextInt(5);
        switch (type) {
            case 0:
                generateStationLineQuestion(question, mapData);
                break;
            case 1:
                generateMaxStationsQuestion(question, mapData);
                break;
            case 2:
                generateLeastStationsQuestion(question, mapData);
                break;
            case 3:
                generateInterchangeQuestion(question, mapData);
                break;
            case 4:
                generateRouteQuestion(question, mapData);
                break;
            default:
                generateStationLineCountQuestion(question, mapData);
                break;
        }

        question.setImage(createImageCode(mapData));
        return question;
    }

    private static MapData createMapData() {
        MapData data = new MapData();
        int cityIndex = RANDOM.nextInt(3); // Toggle between Delhi, Mumbai and Bengaluru

        if (cityIndex == 0) {
            data.cityName = "Delhi";
            MetroLine red = new MetroLine("red", "Red Line", Color.RED);
            MetroLine yellow = new MetroLine("yellow", "Yellow Line", Color.rgb(255, 200, 0));
            MetroLine blue = new MetroLine("blue", "Blue Line", Color.BLUE);
            MetroLine green = new MetroLine("green", "Green Line", Color.rgb(0, 150, 0));

            MetroStation int_RB = new MetroStation("Kriti Nagar", 4, 4);
            MetroStation int_RY = new MetroStation("Rajiv Chowk", 6, 4);
            MetroStation int_RG = new MetroStation("Inderlok", 4, 2);

            red.addStation(new MetroStation("Mundka", 1, 4));
            red.addStation(new MetroStation("Rajouri Garden", 3, 4));
            red.addStation(int_RB);
            red.addStation(int_RY);
            red.addStation(new MetroStation("Kashmere Gate", 8, 4));
            red.addStation(new MetroStation("Dilshad Garden", 10, 4));

            yellow.addStation(new MetroStation("Model Town", 6, 1));
            yellow.addStation(new MetroStation("Civil Lines", 6, 2));
            yellow.addStation(int_RY);
            yellow.addStation(new MetroStation("Hauz Khas", 6, 7));
            yellow.addStation(new MetroStation("Saket", 6, 9));
            yellow.addStation(new MetroStation("Millenium City", 6, 10));

            blue.addStation(new MetroStation("Dwarka Sec 21", 1, 8));
            blue.addStation(new MetroStation("Janakpuri West", 2, 6));
            blue.addStation(int_RB);
            blue.addStation(new MetroStation("Yamuna Bank", 7, 3));
            blue.addStation(new MetroStation("Noida City Centre", 10, 2));

            green.addStation(new MetroStation("Ashok Park", 2, 1));
            green.addStation(int_RG);
            green.addStation(int_RB);
            green.addStation(new MetroStation("Inderlok Ext", 5, 7));

            data.lines.add(red);
            data.lines.add(yellow);
            data.lines.add(blue);
            data.lines.add(green);
            
            data.routeStart = "Mundka";
            data.routeEnd = "Yamuna Bank";
            data.routeChange = "Kriti Nagar";
        } else if (cityIndex == 1) {
            data.cityName = "Mumbai";
            MetroLine blue = new MetroLine("blue", "Line 1 (Blue)", Color.BLUE);
            MetroLine yellow = new MetroLine("yellow", "Line 2 (Yellow)", Color.rgb(255, 200, 0));
            MetroLine red = new MetroLine("red", "Line 7 (Red)", Color.RED);

            // Blue: Horizontal (1,5) to (10,5)
            // Yellow: L-shape (2,1) to (2,5) to (10,5) -- NO, that overlaps.
            // Let's make Blue: (1,5) -> (10,5)
            // Yellow: (2,2) -> (2,5) -> (5,5) -> (8,8) -> (9,10)
            
            MetroStation int_BY = new MetroStation("DN Nagar", 2, 5);
            MetroStation int_YR = new MetroStation("Gundavali", 6, 5);

            blue.addStation(new MetroStation("Versova", 1, 5));
            blue.addStation(int_BY);
            blue.addStation(new MetroStation("Andheri", 4, 5));
            blue.addStation(int_YR);
            blue.addStation(new MetroStation("Marol Naka", 8, 5));
            blue.addStation(new MetroStation("Ghatkopar", 10, 5));

            yellow.addStation(new MetroStation("Dahisar", 2, 2));
            yellow.addStation(int_BY);
            yellow.addStation(new MetroStation("Oshiwara", 4, 7));
            yellow.addStation(int_YR);
            yellow.addStation(new MetroStation("Mankhurd", 9, 9));

            red.addStation(new MetroStation("Aarey", 6, 2));
            red.addStation(int_YR);
            red.addStation(new MetroStation("Jogeshwari", 6, 7));
            red.addStation(new MetroStation("Andheri East", 6, 9));

            data.lines.add(blue);
            data.lines.add(yellow);
            data.lines.add(red);
            
            data.routeStart = "Versova";
            data.routeEnd = "Mankhurd";
            data.routeChange = "DN Nagar";
        } else {
            data.cityName = "Bengaluru";
            MetroLine purple = new MetroLine("purple", "Purple Line", Color.rgb(128, 0, 128));
            MetroLine green = new MetroLine("green", "Green Line", Color.rgb(0, 128, 0));

            MetroStation majestic = new MetroStation("Majestic", 5, 5);

            purple.addStation(new MetroStation("Challaghatta", 1, 5));
            purple.addStation(new MetroStation("Mysuru Road", 3, 5));
            purple.addStation(majestic);
            purple.addStation(new MetroStation("MG Road", 7, 5));
            purple.addStation(new MetroStation("Indiranagar", 8, 5));
            purple.addStation(new MetroStation("Whitefield", 10, 5));

            green.addStation(new MetroStation("Nagasandra", 5, 1));
            green.addStation(new MetroStation("Yeswanthpur", 5, 3));
            green.addStation(majestic);
            green.addStation(new MetroStation("Jayanagar", 5, 7));
            green.addStation(new MetroStation("Silk Institute", 5, 10));

            data.lines.add(purple);
            data.lines.add(green);

            data.routeStart = "Indiranagar";
            data.routeEnd = "Jayanagar";
            data.routeChange = "Majestic";
        }

        Map<String, MetroStation> uniqueStations = new HashMap<>();
        for (MetroLine line : data.lines) {
            for (MetroStation s : line.getStations()) {
                uniqueStations.put(s.getName(), s);
            }
        }
        data.allStations.addAll(uniqueStations.values());
        
        return data;
    }

    private static void generateStationLineQuestion(Question question, MapData mapData) {
        MetroStation station = mapData.allStations.get(RANDOM.nextInt(mapData.allStations.size()));
        
        List<String> correctLineNames = new ArrayList<>();
        for(String id : station.getLineIds()) {
            for(MetroLine ml : mapData.lines) {
                if(ml.getId().equals(id)) {
                    correctLineNames.add(ml.getName());
                }
            }
        }
        
        // Pick one of the correct lines as the answer
        String answerLine = correctLineNames.get(RANDOM.nextInt(correctLineNames.size()));
        
        question.setQuestion("Which of these lines passes through the '" + station.getName() + "' station?");
        question.setAnswer(answerLine);
        
        List<String> options = new ArrayList<>();
        options.add(answerLine);
        
        // Distractors must NOT be any of the lines passing through this station
        List<MetroLine> distractorPool = new ArrayList<>();
        for (MetroLine ml : mapData.lines) {
            if (!correctLineNames.contains(ml.getName())) {
                distractorPool.add(ml);
            }
        }
        
        Collections.shuffle(distractorPool);
        for (int i = 0; i < 3 && i < distractorPool.size(); i++) {
            options.add(distractorPool.get(i).getName());
        }
        
        // Standard line names if we need more options
        String[] fallbackLines = {"Orange Line", "Pink Line", "Cyan Line", "Purple Line", "Brown Line"};
        for (String fl : fallbackLines) {
            if (options.size() < 4 && !options.contains(fl) && !correctLineNames.contains(fl)) {
                options.add(fl);
            }
        }
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateMaxStationsQuestion(Question question, MapData mapData) {
        MetroLine maxLine = mapData.lines.get(0);
        for(MetroLine ml : mapData.lines) {
            if(ml.getStations().size() > maxLine.getStations().size()) maxLine = ml;
        }
        
        question.setQuestion("Which metro line has the maximum number of stations in the " + mapData.cityName + " map?");
        question.setAnswer(maxLine.getName());
        
        List<String> options = new ArrayList<>();
        for(MetroLine ml : mapData.lines) options.add(ml.getName());
        while(options.size() < 4) options.add("Pink Line");
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateLeastStationsQuestion(Question question, MapData mapData) {
        MetroLine minLine = mapData.lines.get(0);
        for(MetroLine ml : mapData.lines) {
            if(ml.getStations().size() < minLine.getStations().size()) minLine = ml;
        }
        
        question.setQuestion("Which metro line has the least number of stations in the " + mapData.cityName + " map?");
        question.setAnswer(minLine.getName());
        
        List<String> options = new ArrayList<>();
        for(MetroLine ml : mapData.lines) options.add(ml.getName());
        while(options.size() < 4) options.add("Cyan Line");
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateInterchangeQuestion(Question question, MapData mapData) {
        List<MetroStation> interchanges = new ArrayList<>();
        for(MetroStation ms : mapData.allStations) {
            if(ms.isInterchange()) interchanges.add(ms);
        }
        
        MetroStation correct = interchanges.get(RANDOM.nextInt(interchanges.size()));
        question.setQuestion("Which of these is an interchange station in " + mapData.cityName + "?");
        question.setAnswer(correct.getName());
        
        List<String> options = new ArrayList<>();
        options.add(correct.getName());
        List<MetroStation> others = new ArrayList<>(mapData.allStations);
        for(MetroStation ms : interchanges) others.remove(ms);
        Collections.shuffle(others);
        for(int i=0; i<3 && i<others.size(); i++) options.add(others.get(i).getName());
        
        while(options.size() < 4) options.add("Railway Station");
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateStationLineCountQuestion(Question question, MapData mapData) {
        MetroStation pivot = null;
        for(MetroStation ms : mapData.allStations) {
            if(ms.isInterchange()) { pivot = ms; break; }
        }
        if(pivot == null) pivot = mapData.allStations.get(0);
        
        question.setQuestion("How many different metro lines pass through the '" + pivot.getName() + "' station?");
        int count = pivot.getLineIds().size();
        question.setAnswer(String.valueOf(count));
        
        List<String> options = new ArrayList<>();
        options.add(String.valueOf(count));
        options.add(String.valueOf(count + 1));
        options.add(String.valueOf(Math.max(1, count - 1)));
        options.add("0");
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static void generateRouteQuestion(Question question, MapData mapData) {
        question.setQuestion("To travel from '" + mapData.routeStart + "' to '" + mapData.routeEnd + "', at which station must you change the line?");
        question.setAnswer(mapData.routeChange);
        
        List<String> options = new ArrayList<>();
        options.add(mapData.routeChange);
        List<MetroStation> others = new ArrayList<>(mapData.allStations);
        others.removeIf(s -> s.getName().equals(mapData.routeChange));
        Collections.shuffle(others);
        for(int i=0; i<3 && i<others.size(); i++) options.add(others.get(i).getName());
        
        Collections.shuffle(options);
        OptionUtils.setQuestionOptions(question, options);
    }

    private static String createImageCode(MapData data) {
        StringBuilder sb = new StringBuilder(ImageCodeType.METRO_MAP);
        sb.append("_").append(data.cityName);
        sb.append("_").append(data.lines.size());
        for (MetroLine line : data.lines) {
            sb.append("_").append(line.getId())
              .append(":").append(line.getName())
              .append(":").append(line.getColor())
              .append(":").append(line.getStations().size());
            for (MetroStation s : line.getStations()) {
                sb.append(":").append(s.getName())
                  .append(":").append(s.getX())
                  .append(":").append(s.getY());
            }
        }
        return sb.toString();
    }
}
