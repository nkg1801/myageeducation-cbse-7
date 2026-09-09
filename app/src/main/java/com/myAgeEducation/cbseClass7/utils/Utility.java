package com.myAgeEducation.cbseClass7.utils;

import android.graphics.Color;

import java.util.Random;

public class Utility {
    public enum BackgroundTheme {
        WHITE,
        LIGHT_BLUE,
        LIGHT_GREEN,
        LIGHT_YELLOW,
        LIGHT_PINK,
        LIGHT_PURPLE,
        LIGHT_ORANGE,
        LIGHT_CYAN,
        CREAM,
        RANDOM
    }

    private static final int[] LIGHT_BACKGROUND_COLORS =
            {
                    Color.rgb(220, 240, 220), // Light green
                    Color.rgb(220, 235, 250), // Light blue
                    Color.rgb(250, 235, 210), // Light peach
                    Color.rgb(245, 235, 250), // Light lavender
                    Color.rgb(250, 245, 210), // Light yellow
                    Color.rgb(235, 240, 225)  // Light olive
            };

    public static int getRandomLightBackgroundColor()
    {
        Random random = new Random();
        return LIGHT_BACKGROUND_COLORS[random.nextInt(LIGHT_BACKGROUND_COLORS.length)];
    }
}
