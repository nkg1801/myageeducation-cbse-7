package com.myAgeEducation.cbseClass7.maths.triangles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class TriangleImageGenerator {

    public static Bitmap generate(String imageCode) {
        if (imageCode.startsWith("TRIANGLE_EXTERIOR_PUZZLE")) {
            return generateExteriorAnglePuzzle(imageCode);
        } else if (imageCode.startsWith("TRIANGLE_CIRCLE_PUZZLE")) {
            return generateCircleTrianglePuzzle(imageCode);
        }
        return null;
    }

    private static Bitmap generateExteriorAnglePuzzle(String imageCode) {
        String[] parts = imageCode.split("_");
        int angleDValue = Integer.parseInt(parts[3].split("=")[1]);
        int angleBValue = Integer.parseInt(parts[4].split("=")[1]);
        int angleA1Value = Integer.parseInt(parts[5].split("=")[1]);

        int width = 800;
        int height = 500;
        float padding = 80;
        float availableWidth = width - 2 * padding;
        float availableHeight = height - 2 * padding;

        double dRad = Math.toRadians(angleDValue);
        double bRad = Math.toRadians(angleBValue);
        double aRadTotal = Math.PI - dRad - bRad;

        // Calculate height with a temporary side DB = availableWidth
        double h = (availableWidth * Math.sin(bRad) * Math.sin(dRad)) / Math.sin(dRad + bRad);
        
        float scale = 1.0f;
        if (h > availableHeight) {
            scale = (float) (availableHeight / h);
        }
        
        float sideDB = availableWidth * scale;
        float dx = padding + (availableWidth - sideDB) / 2;
        float dy = height - padding;
        float bx = dx + sideDB;
        float by = dy;

        double sideAD = (sideDB * Math.sin(bRad)) / Math.sin(dRad + bRad);
        float ax = (float) (dx + sideAD * Math.cos(dRad));
        float ay = (float) (dy - sideAD * Math.sin(dRad));

        double sideAB = (sideDB * Math.sin(dRad)) / Math.sin(dRad + bRad);
        double a1Rad = Math.toRadians(angleA1Value);
        double acbRad = Math.PI - bRad - a1Rad;
        double sideBC = (sideAB * Math.sin(a1Rad)) / Math.sin(acbRad);
        
        float cx = (float) (bx - sideBC);
        float cy = dy;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(0, 150, 255));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        canvas.drawLine(dx, dy, bx, by, paint);
        canvas.drawLine(dx, dy, ax, ay, paint);
        canvas.drawLine(ax, ay, bx, by, paint);
        canvas.drawLine(ax, ay, cx, cy, paint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.rgb(0, 150, 255));
        textPaint.setTextSize(40);
        canvas.drawText("A", ax - 20, ay - 15, textPaint);
        canvas.drawText("D", dx - 40, dy + 25, textPaint);
        canvas.drawText("B", bx + 10, by + 25, textPaint);
        canvas.drawText("C", cx - 20, cy + 50, textPaint);

        Paint arcPaint = new Paint(paint);
        arcPaint.setStrokeWidth(3);

        // Calculate segment angles in degrees (0 is horizontal right, clockwise is positive)
        double angleDA = Math.toDegrees(Math.atan2(ay - dy, ax - dx));
        double angleDB = 0; // horizontal line to the right

        double angleBD = 180; // horizontal line to the left
        double angleBA = Math.toDegrees(Math.atan2(ay - by, ax - bx));

        double angleAD = Math.toDegrees(Math.atan2(dy - ay, dx - ax));
        double angleAC = Math.toDegrees(Math.atan2(cy - ay, cx - ax));
        double angleAB = Math.toDegrees(Math.atan2(by - ay, bx - ax));

        // Draw Angle D
        drawAngleMark(canvas, dx, dy, angleDA, angleDB, angleDValue + "°", arcPaint, textPaint, 70);

        // Draw Angle B
        drawAngleMark(canvas, bx, by, angleBD, angleBA, angleBValue + "°", arcPaint, textPaint, 100);

        // Angle A (Two parts: DAC and CAB)
        // Part 1: x (Angle DAC)
        drawAngleMark(canvas, ax, ay, angleAC, angleAD, "x", arcPaint, textPaint, 110);
        
        // Part 2: angleA1Value (Angle CAB)
        drawAngleMark(canvas, ax, ay, angleAB, angleAC, angleA1Value + "°", arcPaint, textPaint, 80);

        return bitmap;
    }

    private static Bitmap generateCircleTrianglePuzzle(String imageCode) {
        // TRIANGLE_CIRCLE_PUZZLE_A=120
        String[] parts = imageCode.split("_");
        int angleAValue = Integer.parseInt(parts[3].split("=")[1]);

        int width = 800;
        int height = 600;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        float cx = width / 2f;
        float cy = height / 2f;
        float radius = 220;

        // Draw Circle
        canvas.drawCircle(cx, cy, radius, paint);

        // Draw Center dot
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, 6, paint);
        paint.setStyle(Paint.Style.STROKE);

        // Calculate B and C positions
        // Place A at center. Isosceles triangle ABC where AB=AC=radius.
        // Central angle at A is angleAValue.
        double aRad = Math.toRadians(angleAValue);
        double halfA = aRad / 2.0;

        // Orient triangle symmetrically: A is center, BC is horizontal at bottom
        float ax = cx;
        float ay = cy;
        
        float bx = (float) (cx - radius * Math.sin(halfA));
        float by = (float) (cy + radius * Math.cos(halfA));
        
        float ccx = (float) (cx + radius * Math.sin(halfA));
        float ccy = (float) (cy + radius * Math.cos(halfA));

        // Draw lines AB, AC, BC
        canvas.drawLine(ax, ay, bx, by, paint);
        canvas.drawLine(ax, ay, ccx, ccy, paint);
        canvas.drawLine(bx, by, ccx, ccy, paint);

        // Labels
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(45);
        canvas.drawText("A", ax - 15, ay - 30, textPaint);
        canvas.drawText("B", bx - 45, by + 10, textPaint);
        canvas.drawText("C", ccx + 15, ccy + 10, textPaint);

        // Central Angle Marking
        Paint arcPaint = new Paint(paint);
        arcPaint.setStrokeWidth(3);
        float arcRadius = 50;
        RectF arcRect = new RectF(ax - arcRadius, ay - arcRadius, ax + arcRadius, ay + arcRadius);
        // Arcs go from -90 + (90 - angle/2) to -90 + (90 + angle/2)? 
        // Our B is at angle (PI/2 + a/2), C is at (PI/2 - a/2) in polar
        float startAngle = 90 - (angleAValue / 2f);
        canvas.drawArc(arcRect, startAngle, angleAValue, false, arcPaint);
        
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(angleAValue + "°", ax, ay + arcRadius + 40, textPaint);

        return bitmap;
    }

    private static void drawAngleMark(Canvas canvas, float cx, float cy, double angleStart, double angleEnd, String label, Paint arcPaint, Paint textPaint, float radius) {
        double start = angleStart;
        double end = angleEnd;
        
        double sweep = end - start;
        while (sweep < 0) sweep += 360;
        while (sweep > 360) sweep -= 360;

        if (sweep > 180) {
            double temp = start;
            start = end;
            end = temp;
            sweep = 360 - sweep;
        }

        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(oval, (float)start, (float)sweep, false, arcPaint);

        // Draw label at bisector
        double bisector = start + sweep / 2;
        float labelRadius = radius + 35;
        float lx = (float) (cx + labelRadius * Math.cos(Math.toRadians(bisector)));
        float ly = (float) (cy + labelRadius * Math.sin(Math.toRadians(bisector)));
        
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(label, lx, ly + 15, textPaint);
    }
}
