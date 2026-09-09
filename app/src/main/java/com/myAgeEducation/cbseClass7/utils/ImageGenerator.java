package com.myAgeEducation.cbseClass7.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageGenerator {

    public static Bitmap generate(Context context, String imageCode) {
        String[] parts = imageCode.split(";");
        if(parts.length < 2)
        {
            return null;
        }

        imageCode = parts[1];

        int resourceIdentifier = context.getResources().getIdentifier(imageCode, "drawable", context.getPackageName());

        Bitmap bitmap = null;

        if (resourceIdentifier != 0) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), resourceIdentifier);
        }

        return bitmap;
    }
}
