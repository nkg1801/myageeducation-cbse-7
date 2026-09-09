package com.myAgeEducation.cbseClass7.maths.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatUtil {
    public static String formatIndianNumber(int number) {
        return NumberFormat
                .getInstance(new Locale("en", "IN"))
                .format(number);
    }

    public static String formatOrdinal(int number) {
        if (number <= 0) return String.valueOf(number);
        
        int lastDigit = number % 10;
        int lastTwoDigits = number % 100;
        
        if (lastTwoDigits >= 11 && lastTwoDigits <= 13) {
            return number + "th";
        }
        
        switch (lastDigit) {
            case 1: return number + "st";
            case 2: return number + "nd";
            case 3: return number + "rd";
            default: return number + "th";
        }
    }
}
