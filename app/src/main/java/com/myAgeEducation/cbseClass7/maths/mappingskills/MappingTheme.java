package com.myAgeEducation.cbseClass7.maths.mappingskills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MappingTheme
{
    private final String subject;
    private final String verb;
    private final String startImage;
    private final DirectionObject[] objects;
    private final String scaleLabel;
    private final String unit;
    private final int scaleMultiplier;

    /*
     * Remembers the theme used for the previous question.
     */
    private static int lastThemeIndex = -1;

    public MappingTheme(
            String subject,
            String verb,
            String startImage,
            DirectionObject[] objects,
            String scaleLabel,
            String unit,
            int scaleMultiplier)
    {
        this.subject = subject;
        this.verb = verb;
        this.startImage = startImage;
        this.objects = objects;
        this.scaleLabel = scaleLabel;
        this.unit = unit;
        this.scaleMultiplier = scaleMultiplier;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getVerb()
    {
        return verb;
    }

    public String getStartImage()
    {
        return startImage;
    }

    public DirectionObject[] getObjects()
    {
        return objects;
    }

    public String getScaleLabel() {
        return scaleLabel;
    }

    public String getUnit() {
        return unit;
    }

    public int getScaleMultiplier() {
        return scaleMultiplier;
    }

    private static class ThemeDefinition {
        String subject;
        String verb;
        String startImage;
        String[] objectNames;
        String scaleLabel;
        String unit;
        int scaleMultiplier;

        ThemeDefinition(String subject, String verb, String startImage, String[] objectNames, String scaleLabel, String unit, int scaleMultiplier) {
            this.subject = subject;
            this.verb = verb;
            this.startImage = startImage;
            this.objectNames = objectNames;
            this.scaleLabel = scaleLabel;
            this.unit = unit;
            this.scaleMultiplier = scaleMultiplier;
        }
    }

    private static final ThemeDefinition[] THEME_DEFS = {
            //1
            new ThemeDefinition("ant", "crawl", "ant", new String[]{
                    "laddoos", "sugarcubes", "bread", "apple", "mango", "orange", "pear", "strawberry", "watermelon"
            }, "1 cm", "cm", 1),

            //2
            new ThemeDefinition("honeybee", "fly", "honeybee", new String[]{
                    "rose", "sunflower", "lotus", "hibiscus"
            }, "1 cm", "cm", 1),

            //3
            new ThemeDefinition("rabbit", "hop", "rabbit", new String[]{
                    "carrot", "cabbage", "turnip", "radish", "toffee"
            }, "1 cm", "cm", 1),
            /*new ThemeDefinition("rat", "scurry", "rat", new String[]{
                    "cheese", "cake", "cookie", "corn", "bread", "nut", "cracker"
            }, "1 cm", "cm", 1),*/

            //4
            new ThemeDefinition("bus", "drive", "bus", new String[]{
                    "delhi", "mumbai", "kolkata", "chennai", "bengaluru", "hyderabad", "ahmedabad", "pune", "indore", "lucknow", "noida",
                    "bhopal", "mysuru", "nagpur", "kanpur", "nagpur", "patna", "surat", "jaipur", "chandigarh", "agra"
            }, "100 km", "km", 100),

            //5
            new ThemeDefinition("car", "drive", "car", new String[]{
                    "delhi", "mumbai", "kolkata", "chennai", "bengaluru", "hyderabad", "ahmedabad", "pune", "indore", "lucknow", "noida",
                    "bhopal", "mysuru", "nagpur", "kanpur", "nagpur", "patna", "surat", "jaipur", "chandigarh", "agra"
            }, "100 km", "km", 100)
    };

    public static MappingTheme getRandomTheme(Random random)
    {
        int themeIndex;
        do
        {
            themeIndex = random.nextInt(THEME_DEFS.length);
        }
        while (themeIndex == lastThemeIndex && THEME_DEFS.length > 1);

        lastThemeIndex = themeIndex;
        ThemeDefinition def = THEME_DEFS[themeIndex];
        
        List<String> allNames = new ArrayList<>();
        Collections.addAll(allNames, def.objectNames);
        Collections.shuffle(allNames, random);
        
        // Pick 4 random objects for this specific question
        DirectionObject[] selectedObjects = new DirectionObject[4];
        for (int i = 0; i < 4; i++) {
            selectedObjects[i] = new DirectionObject(allNames.get(i));
        }
        
        return new MappingTheme(def.subject, def.verb, def.startImage, selectedObjects, def.scaleLabel, def.unit, def.scaleMultiplier);
    }
}
