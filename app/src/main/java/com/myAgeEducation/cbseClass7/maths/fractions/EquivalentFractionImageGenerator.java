package com.myAgeEducation.cbseClass7.maths.fractions;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.myAgeEducation.cbseClass7.utils.ImageCodeType;

import java.util.Random;

public class EquivalentFractionImageGenerator {
    private static final Random RANDOM = new Random();
    private static final int[] COLORS = {
            Color.rgb(120, 50, 180),   // Purple
            Color.rgb(30, 100, 200),   // Blue
            Color.rgb(20, 140, 90),    // Green
            Color.rgb(210, 80, 40),    // Orange
            Color.rgb(190, 50, 90)     // Pink
    };

    public static Bitmap generate(String imageCode) {
        // Format: EQ-FRAC_n1_d1_n2_d2_missingIndex
        // missingIndex: 0=n2, 1=d2
        String[] parts = imageCode.split("_");
        String n1 = parts[1];
        String d1 = parts[2];
        String n2 = parts[3];
        String d2 = parts[4];
        int missingIndex = Integer.parseInt(parts[5]);

        int width = 800;
        int height = 300;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(COLORS[RANDOM.nextInt(COLORS.length)]);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setStrokeWidth(8);

        Paint boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setColor(Color.BLACK);
        boxPaint.setStrokeWidth(5);

        // Fraction 1
        float x1 = 200;
        canvas.drawText(n1, x1, 100, paint);
        canvas.drawLine(x1 - 60, 130, x1 + 60, 130, paint);
        canvas.drawText(d1, x1, 230, paint);

        // Equals sign
        canvas.drawText("=", 400, 160, paint);

        // Fraction 2
        float x2 = 600;
        if (missingIndex == 0) { // Hide n2
            canvas.drawRoundRect(new RectF(x2 - 60, 30, x2 + 60, 110), 10, 10, boxPaint);
        } else {
            canvas.drawText(n2, x2, 100, paint);
        }

        canvas.drawLine(x2 - 60, 130, x2 + 60, 130, paint);

        if (missingIndex == 1) { // Hide d2
            canvas.drawRoundRect(new RectF(x2 - 60, 160, x2 + 60, 240), 10, 10, boxPaint);
        } else {
            canvas.drawText(d2, x2, 230, paint);
        }

        return bitmap;
    }

    public static String createImageCode(int n1, int d1, int n2, int d2, int missingIndex) {
        return ImageCodeType.EQUIVALENT_FRACTION + "_" + n1 + "_" + d1 + "_" + n2 + "_" + d2 + "_" + missingIndex;
    }
}
