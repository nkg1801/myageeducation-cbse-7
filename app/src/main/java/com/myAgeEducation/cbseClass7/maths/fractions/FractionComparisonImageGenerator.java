package com.myAgeEducation.cbseClass7.maths.fractions;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class FractionComparisonImageGenerator {

    public static Bitmap generate(String imageCode) {
        String[] parts = imageCode.split("_");
        // Format: FRAC-COMP_W1_N1_D1_OP_W2_N2_D2
        if (parts.length < 8) return null;

        int w1 = Integer.parseInt(parts[1]);
        int n1 = Integer.parseInt(parts[2]);
        int d1 = Integer.parseInt(parts[3]);
        int opType = Integer.parseInt(parts[4]); // 0: blank, 1: <, 2: >, 3: =
        int w2 = Integer.parseInt(parts[5]);
        int n2 = Integer.parseInt(parts[6]);
        int d2 = Integer.parseInt(parts[7]);

        int width = 800;
        int height = 400;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        // Draw First Fraction
        drawFraction(canvas, 200, 200, w1, n1, d1, paint);

        // Draw Operator or Blank
        String opStr = "";
        switch (opType) {
            case 1: opStr = "<"; break;
            case 2: opStr = ">"; break;
            case 3: opStr = "="; break;
            default: opStr = " "; break;
        }

        paint.setTextSize(100);
        paint.setColor(Color.GRAY);
        canvas.drawText(opStr, 400, 235, paint);
        if (opType == 0) {
            // Draw a small box for blank
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            canvas.drawRect(360, 170, 440, 260, paint);
            paint.setStyle(Paint.Style.FILL);
        }

        // Draw Second Fraction
        paint.setColor(Color.RED);
        drawFraction(canvas, 600, 200, w2, n2, d2, paint);

        return bitmap;
    }

    private static void drawFraction(Canvas canvas, float x, float y, int w, int n, int d, Paint paint) {
        float fracX = x;
        if (w > 0) {
            paint.setTextSize(140);
            String wStr = String.valueOf(w);
            float wWidth = paint.measureText(wStr);
            canvas.drawText(wStr, x - 40, y + 50, paint);
            fracX = x + wWidth / 2f + 10;
        }

        paint.setTextSize(90);
        // Numerator
        canvas.drawText(String.valueOf(n), fracX, y - 20, paint);
        // Line
        float lineW = Math.max(paint.measureText(String.valueOf(n)), paint.measureText(String.valueOf(d))) + 20;
        paint.setStrokeWidth(6);
        canvas.drawLine(fracX - lineW/2, y, fracX + lineW/2, y, paint);
        // Denominator
        canvas.drawText(String.valueOf(d), fracX, y + 85, paint);
    }
}
