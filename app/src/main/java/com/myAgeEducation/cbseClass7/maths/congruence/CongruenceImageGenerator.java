package com.myAgeEducation.cbseClass7.maths.congruence;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class CongruenceImageGenerator {

    public static Bitmap generate(String imageCode) {
        switch (imageCode) {
            case "CONG_SSS_KITE":
                return generateSSSKite();
            case "CONG_SAS_SHARED":
                return generateSASSharedSide();
            case "CONG_ASA_SHARED":
                return generateASASharedSide();
            case "CONG_RHS_SHARED":
                return generateRHSSharedSide();
            case "CONG_OVERLAPPING_SSS":
                return generateOverlappingTriangles();
            default:
                return null;
        }
    }

    private static Bitmap generateSSSKite() {
        int width = 500;
        int height = 700;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        float centerX = width / 2f;
        float topY = 50;
        float bottomY = 650;
        float leftX = 100;
        float rightX = 400;
        float middleY = 300;

        // Triangle ABD (Left) and CBD (Right)
        // Vertices: D(centerX, topY), B(centerX, bottomY), A(leftX, middleY), C(rightX, middleY)
        Path path = new Path();
        path.moveTo(centerX, topY);
        path.lineTo(leftX, middleY);
        path.lineTo(centerX, bottomY);
        path.lineTo(rightX, middleY);
        path.close();
        canvas.drawPath(path, paint);

        // Common side BD (dashed)
        Paint dashedPaint = new Paint(paint);
        dashedPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        canvas.drawLine(centerX, topY, centerX, bottomY, dashedPaint);

        // Labels
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        canvas.drawText("D", centerX - 10, topY - 10, textPaint);
        canvas.drawText("B", centerX - 10, bottomY + 40, textPaint);
        canvas.drawText("A", leftX - 40, middleY + 10, textPaint);
        canvas.drawText("C", rightX + 10, middleY + 10, textPaint);

        // Mark AD = CD (one tick)
        drawTick(canvas, (centerX + leftX) / 2, (topY + middleY) / 2, -45, 1);
        drawTick(canvas, (centerX + rightX) / 2, (topY + middleY) / 2, 45, 1);

        // Mark AB = CB (two ticks)
        drawTick(canvas, (centerX + leftX) / 2, (bottomY + middleY) / 2, 45, 2);
        drawTick(canvas, (centerX + rightX) / 2, (bottomY + middleY) / 2, -45, 2);

        return bitmap;
    }

    private static Bitmap generateSASSharedSide() {
        int width = 600;
        int height = 500;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        float leftX = 100;
        float rightX = 500;
        float bottomY = 400;
        float topY = 100;
        float midX = 300;

        // Triangle ABD and CBD sharing side BD
        // D(midX, topY), B(midX, bottomY), A(leftX, bottomY), C(rightX, bottomY)
        canvas.drawLine(leftX, bottomY, rightX, bottomY, paint);
        canvas.drawLine(leftX, bottomY, midX, topY, paint);
        canvas.drawLine(rightX, bottomY, midX, topY, paint);
        
        // Common side BD
        Paint dashedPaint = new Paint(paint);
        dashedPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        canvas.drawLine(midX, topY, midX, bottomY, dashedPaint);

        // Labels
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        canvas.drawText("D", midX - 10, topY - 10, textPaint);
        canvas.drawText("B", midX - 10, bottomY + 40, textPaint);
        canvas.drawText("A", leftX - 40, bottomY + 10, textPaint);
        canvas.drawText("C", rightX + 10, bottomY + 10, textPaint);

        // SAS markings: AD = CD, and angle ADB = angle CDB
        // AD = CD
        drawTick(canvas, (leftX + midX) / 2, (bottomY + topY) / 2, -30, 1);
        drawTick(canvas, (rightX + midX) / 2, (bottomY + topY) / 2, 30, 1);

        // Angle ADB and CDB
        RectF arcRect = new RectF(midX - 50, topY - 50, midX + 50, topY + 50);
        canvas.drawArc(arcRect, 90, 45, false, paint);
        canvas.drawArc(arcRect, 45, 45, false, paint);

        return bitmap;
    }

    private static Bitmap generateASASharedSide() {
        int width = 600;
        int height = 500;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        float leftX = 100;
        float rightX = 500;
        float bottomY = 400;
        float topY = 100;
        float midX = 300;

        // Triangle ABD and CBD sharing side BD
        canvas.drawLine(leftX, bottomY, rightX, bottomY, paint);
        canvas.drawLine(leftX, bottomY, midX, topY, paint);
        canvas.drawLine(rightX, bottomY, midX, topY, paint);
        
        // Common side BD
        Paint dashedPaint = new Paint(paint);
        dashedPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        canvas.drawLine(midX, topY, midX, bottomY, dashedPaint);

        // Labels
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        canvas.drawText("D", midX - 10, topY - 10, textPaint);
        canvas.drawText("B", midX - 10, bottomY + 40, textPaint);
        canvas.drawText("A", leftX - 40, bottomY + 10, textPaint);
        canvas.drawText("C", rightX + 10, bottomY + 10, textPaint);

        // ASA markings: angle ADB = angle CDB, and angle ABD = angle CBD
        RectF topArcRect = new RectF(midX - 50, topY - 50, midX + 50, topY + 50);
        canvas.drawArc(topArcRect, 90, 45, false, paint);
        canvas.drawArc(topArcRect, 45, 45, false, paint);

        RectF bottomArcRect = new RectF(midX - 50, bottomY - 50, midX + 50, bottomY + 50);
        canvas.drawArc(bottomArcRect, 270, -45, false, paint);
        canvas.drawArc(bottomArcRect, 270, 45, false, paint);

        return bitmap;
    }

    private static Bitmap generateRHSSharedSide() {
        int width = 600;
        int height = 500;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        float leftX = 100;
        float rightX = 500;
        float bottomY = 400;
        float topY = 100;
        float midX = 300;

        // Two right triangles sharing BD (vertical)
        // D(midX, topY), B(midX, bottomY), A(leftX, bottomY), C(rightX, bottomY)
        // Assume angles at B are 90 degrees
        canvas.drawLine(leftX, bottomY, rightX, bottomY, paint);
        canvas.drawLine(leftX, bottomY, midX, topY, paint);
        canvas.drawLine(rightX, bottomY, midX, topY, paint);
        
        // Common side BD
        Paint dashedPaint = new Paint(paint);
        dashedPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        canvas.drawLine(midX, topY, midX, bottomY, dashedPaint);

        // Labels
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        canvas.drawText("D", midX - 10, topY - 10, textPaint);
        canvas.drawText("B", midX - 10, bottomY + 40, textPaint);
        canvas.drawText("A", leftX - 40, bottomY + 10, textPaint);
        canvas.drawText("C", rightX + 10, bottomY + 10, textPaint);

        // Right angle symbols at B
        canvas.drawLine(midX - 20, bottomY, midX - 20, bottomY - 20, paint);
        canvas.drawLine(midX - 20, bottomY - 20, midX, bottomY - 20, paint);
        canvas.drawLine(midX + 20, bottomY, midX + 20, bottomY - 20, paint);
        canvas.drawLine(midX + 20, bottomY - 20, midX, bottomY - 20, paint);

        // RHS markings: Hypotunuse AD = CD
        drawTick(canvas, (leftX + midX) / 2, (bottomY + topY) / 2, -30, 1);
        drawTick(canvas, (rightX + midX) / 2, (bottomY + topY) / 2, 30, 1);

        return bitmap;
    }

    private static Bitmap generateOverlappingTriangles() {
        int width = 600;
        int height = 400;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.rgb(176, 224, 230)); // Light blue background like the image

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        float leftX = 100;
        float rightX = 500;
        float bottomY = 350;
        float topLeftY = 100;
        float topRightY = 100;

        // Common base AB
        canvas.drawLine(leftX, bottomY, rightX, bottomY, paint);

        // Side AD (left vertical-ish) and BC (right vertical-ish)
        canvas.drawLine(leftX, bottomY, leftX, topLeftY, paint);
        canvas.drawLine(rightX, bottomY, rightX, topRightY, paint);

        // Diagonals AC and BD
        canvas.drawLine(leftX, bottomY, rightX, topRightY, paint); // AC
        canvas.drawLine(rightX, bottomY, leftX, topLeftY, paint); // BD

        // Labels
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        canvas.drawText("A", leftX - 40, bottomY + 20, textPaint);
        canvas.drawText("B", rightX + 10, bottomY + 20, textPaint);
        canvas.drawText("D", leftX - 40, topLeftY - 10, textPaint);
        canvas.drawText("C", rightX + 10, topRightY - 10, textPaint);

        // Markings: AD = BC (one tick)
        drawTick(canvas, leftX, (bottomY + topLeftY) / 2, 0, 1);
        drawTick(canvas, rightX, (bottomY + topRightY) / 2, 0, 1);

        // Markings: AC = BD (two ticks)
        drawTick(canvas, (leftX + rightX) / 2 - 50, (bottomY + topRightY) / 2 + 30, 30, 2); // on AC
        drawTick(canvas, (leftX + rightX) / 2 + 50, (bottomY + topLeftY) / 2 + 30, -30, 2); // on BD

        return bitmap;
    }

    private static void drawTick(Canvas canvas, float x, float y, float angle, int count) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);

        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(angle);
        
        float tickLen = 20;
        float spacing = 10;
        
        float startOffset = -(count - 1) * spacing / 2f;
        for (int i = 0; i < count; i++) {
            float ox = startOffset + i * spacing;
            canvas.drawLine(ox, -tickLen/2, ox, tickLen/2, paint);
        }
        
        canvas.restore();
    }
}
