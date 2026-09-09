package com.myAgeEducation.cbseClass7.maths.pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.myAgeEducation.cbseClass7.maths.utils.NumberFormatUtil;

import java.util.Random;

public class PatternSequenceImageGenerator {
    private static final int IMAGE_WIDTH = 1000;
    private static final int IMAGE_HEIGHT = 400; // Can be multi-line if many items
    private static final float BOX_WIDTH = 150;
    private static final float BOX_HEIGHT = 100;
    private static final float BOX_GAP = 50;
    private static final float MARGIN = 40;

    private static final int[] THEME_COLORS = {
        Color.rgb(180, 50, 50),   // Red
        Color.rgb(50, 150, 50),   // Green
        Color.rgb(200, 150, 50),  // Orange
        Color.rgb(50, 100, 180)   // Blue
    };

    private PatternSequenceImageGenerator() {}

    public static Bitmap generate(Context context, String imageCode) {
        String[] parts = imageCode.split("_");
        int length = Integer.parseInt(parts[1]);
        int missingIndex = Integer.parseInt(parts[2]);
        int[] numbers = new int[length];
        for (int i = 0; i < length; i++) {
            numbers[i] = Integer.parseInt(parts[3 + i]);
        }

        int themeColor = THEME_COLORS[new Random().nextInt(THEME_COLORS.length)];

        // Calculate rows
        int boxesPerRow = 5;
        int rowCount = (length + boxesPerRow - 1) / boxesPerRow;
        int dynamicHeight = (int) (MARGIN * 2 + rowCount * (BOX_HEIGHT + BOX_GAP));
        
        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, Math.max(IMAGE_HEIGHT, dynamicHeight), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boxPaint.setColor(themeColor);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(5);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(24);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

        Paint arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrowPaint.setColor(Color.BLACK);
        arrowPaint.setStrokeWidth(3);

        for (int i = 0; i < length; i++) {
            int row = i / boxesPerRow;
            int col;
            if (row % 2 == 0) {
                col = i % boxesPerRow;
            } else {
                col = (boxesPerRow - 1) - (i % boxesPerRow);
            }

            float x = MARGIN + col * (BOX_WIDTH + BOX_GAP);
            float y = MARGIN + row * (BOX_HEIGHT + BOX_GAP);

            RectF rect = new RectF(x, y, x + BOX_WIDTH, y + BOX_HEIGHT);
            canvas.drawRoundRect(rect, 20, 20, boxPaint);

            if (i != missingIndex) {
                String val = NumberFormatUtil.formatIndianNumber(numbers[i]);
                canvas.drawText(val, rect.centerX(), rect.centerY() + 10, textPaint);
            }

            // Draw arrow to next box
            if (i < length - 1) {
                float startX, startY, endX, endY;
                if ((i + 1) % boxesPerRow == 0) {
                    // Down arrow to start the next row
                    startX = x + BOX_WIDTH / 2;
                    startY = y + BOX_HEIGHT;
                    endX = x + BOX_WIDTH / 2;
                    endY = y + BOX_HEIGHT + BOX_GAP;
                    drawArrow(canvas, startX, startY, endX, endY, arrowPaint);
                } else {
                    // Horizontal arrow
                    if (row % 2 == 0) {
                        // Going Right
                        startX = x + BOX_WIDTH;
                        startY = y + BOX_HEIGHT / 2;
                        endX = x + BOX_WIDTH + BOX_GAP;
                        endY = y + BOX_HEIGHT / 2;
                    } else {
                        // Going Left
                        startX = x;
                        startY = y + BOX_HEIGHT / 2;
                        endX = x - BOX_GAP;
                        endY = y + BOX_HEIGHT / 2;
                    }
                    drawArrow(canvas, startX, startY, endX, endY, arrowPaint);
                }
            }
        }

        return bitmap;
    }

    private static void drawArrow(Canvas canvas, float x1, float y1, float x2, float y2, Paint paint) {
        canvas.drawLine(x1, y1, x2, y2, paint);
        float angle = (float) Math.atan2(y2 - y1, x2 - x1);
        float arrowSize = 15;
        canvas.drawLine(x2, y2, x2 - arrowSize * (float) Math.cos(angle - Math.PI / 6), y2 - arrowSize * (float) Math.sin(angle - Math.PI / 6), paint);
        canvas.drawLine(x2, y2, x2 - arrowSize * (float) Math.cos(angle + Math.PI / 6), y2 - arrowSize * (float) Math.sin(angle + Math.PI / 6), paint);
    }
}
