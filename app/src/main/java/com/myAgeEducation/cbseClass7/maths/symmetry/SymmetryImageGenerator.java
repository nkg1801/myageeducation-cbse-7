package com.myAgeEducation.cbseClass7.maths.symmetry;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

public class SymmetryImageGenerator {
    public static Bitmap generate(String imageCode) {
        if (imageCode.equals("SYMMETRY_STAR_4")) {
            return generateStar4();
        } else if (imageCode.equals("SYMMETRY_ARROW")) {
            return generateArrow();
        } else if (imageCode.equals("SYMMETRY_PLUS")) {
            return generatePlus();
        } else if (imageCode.equals("SYMMETRY_DIAMOND")) {
            return generateDiamond();
        } else if (imageCode.equals("SYMMETRY_HEART")) {
            return generateHeart();
        } else if (imageCode.equals("SYMMETRY_RECT_LINES")) {
            return generateRectangleWithLines();
        } else if (imageCode.equals("SYMMETRY_SQUARE_LINES")) {
            return generateSquareWithLines();
        } else if (imageCode.equals("SYMMETRY_TRIANGLE_LINES")) {
            return generateTriangleWithLines();
        } else if (imageCode.equals("SYMMETRY_TRIPLE_ARROW")) {
            return generateTripleArrow();
        } else if (imageCode.equals("SYMMETRY_HEXAGON")) {
            return generateHexagon();
        } else if (imageCode.equals("SYMMETRY_KITE")) {
            return generateKite();
        } else if (imageCode.equals("SYMMETRY_PARALLELOGRAM")) {
            return generateParallelogram();
        } else if (imageCode.equals("SYMMETRY_WIND_MILL")) {
            return generateWindMill();
        } else if (imageCode.equals("SYMMETRY_WAVE")) {
            return generateWaveShape();
        } else if (imageCode.startsWith("SYMMETRY_TABLE_")) {
            int missingIndex = Integer.parseInt(imageCode.substring("SYMMETRY_TABLE_".length()));
            return generateSymmetryTable(missingIndex);
        }
        return null;
    }

    private static Bitmap generateStar4() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(33, 150, 243));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        Path path = new Path();
        float center = size / 2f;
        float outer = size * 0.45f;
        float inner = size * 0.15f;
        path.moveTo(center, center - outer);
        path.lineTo(center + inner, center - inner);
        path.lineTo(center + outer, center);
        path.lineTo(center + inner, center + inner);
        path.lineTo(center, center + outer);
        path.lineTo(center - inner, center + inner);
        path.lineTo(center - outer, center);
        path.lineTo(center - inner, center - inner);
        path.close();
        canvas.drawPath(path, paint);
        return bitmap;
    }

    private static Bitmap generateArrow() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        Path path = new Path();
        path.moveTo(100, 200);
        path.lineTo(300, 200);
        path.lineTo(300, 100);
        path.lineTo(450, 250);
        path.lineTo(300, 400);
        path.lineTo(300, 300);
        path.lineTo(100, 300);
        path.close();
        canvas.drawPath(path, paint);
        return bitmap;
    }

    private static Bitmap generatePlus() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        Path path = new Path();
        path.moveTo(200, 50);
        path.lineTo(300, 50);
        path.lineTo(300, 200);
        path.lineTo(450, 200);
        path.lineTo(450, 300);
        path.lineTo(300, 300);
        path.lineTo(300, 450);
        path.lineTo(200, 450);
        path.lineTo(200, 300);
        path.lineTo(50, 300);
        path.lineTo(50, 200);
        path.lineTo(200, 200);
        path.close();
        canvas.drawPath(path, paint);
        return bitmap;
    }

    private static Bitmap generateDiamond() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        Path path = new Path();
        path.moveTo(250, 50);
        path.lineTo(450, 250);
        path.lineTo(250, 450);
        path.lineTo(50, 250);
        path.close();
        canvas.drawPath(path, paint);
        return bitmap;
    }

    private static Bitmap generateHeart() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(255, 64, 129));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        Path path = new Path();
        path.moveTo(250, 150);
        path.cubicTo(250, 100, 100, 100, 100, 250);
        path.cubicTo(100, 350, 250, 450, 250, 450);
        path.cubicTo(250, 450, 400, 350, 400, 250);
        path.cubicTo(400, 100, 250, 100, 250, 150);
        canvas.drawPath(path, paint);
        return bitmap;
    }

    private static Bitmap generateRectangleWithLines() {
        int width = 600;
        int height = 400;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        // Rectangle
        canvas.drawRect(100, 100, 500, 300, paint);

        // Line A (Vertical Symmetry)
        Paint lineAPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineAPaint.setColor(Color.BLUE);
        lineAPaint.setStrokeWidth(4);
        canvas.drawLine(300, 50, 300, 350, lineAPaint);
        drawLabel(canvas, "Line A", 300, 40, Color.BLUE);

        // Line B (Diagonal - Not Symmetry)
        Paint lineBPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineBPaint.setColor(Color.rgb(255, 165, 0)); // Orange
        lineBPaint.setStrokeWidth(4);
        canvas.drawLine(50, 350, 550, 50, lineBPaint);
        drawLabel(canvas, "Line B", 550, 40, Color.rgb(255, 165, 0));

        return bitmap;
    }

    private static Bitmap generateSquareWithLines() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        // Square
        canvas.drawRect(100, 100, 400, 400, paint);

        // Line A (Diagonal Symmetry)
        Paint lineAPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineAPaint.setColor(Color.RED);
        lineAPaint.setStrokeWidth(4);
        canvas.drawLine(50, 50, 450, 450, lineAPaint);
        drawLabel(canvas, "Line A", 50, 40, Color.RED);

        // Line B (Vertical Symmetry)
        Paint lineBPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineBPaint.setColor(Color.GREEN);
        lineBPaint.setStrokeWidth(4);
        canvas.drawLine(250, 50, 250, 450, lineBPaint);
        drawLabel(canvas, "Line B", 250, 40, Color.GREEN);

        return bitmap;
    }

    private static Bitmap generateTriangleWithLines() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        // Equilateral Triangle
        Path path = new Path();
        path.moveTo(250, 100);
        path.lineTo(100, 400);
        path.lineTo(400, 400);
        path.close();
        canvas.drawPath(path, paint);

        // Line A (Median Symmetry)
        Paint lineAPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineAPaint.setColor(Color.MAGENTA);
        lineAPaint.setStrokeWidth(4);
        canvas.drawLine(250, 50, 250, 450, lineAPaint);
        drawLabel(canvas, "Line A", 250, 40, Color.MAGENTA);

        // Line B (Base line - Not Symmetry)
        Paint lineBPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineBPaint.setColor(Color.CYAN);
        lineBPaint.setStrokeWidth(4);
        canvas.drawLine(50, 300, 450, 300, lineBPaint);
        drawLabel(canvas, "Line B", 50, 290, Color.CYAN);

        return bitmap;
    }

    private static Bitmap generateTripleArrow() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4);
        
        Path path = new Path();
        // Horizontal bar top
        path.moveTo(100, 150);
        path.lineTo(400, 150);
        path.lineTo(400, 200);
        path.lineTo(100, 200);
        path.close();
        
        // Vertical bar center
        path.moveTo(225, 200);
        path.lineTo(275, 200);
        path.lineTo(275, 350);
        path.lineTo(225, 350);
        path.close();
        
        // Arrow head Left
        path.moveTo(100, 125);
        path.lineTo(50, 175);
        path.lineTo(100, 225);
        path.close();
        
        // Arrow head Right
        path.moveTo(400, 125);
        path.lineTo(450, 175);
        path.lineTo(400, 225);
        path.close();
        
        // Arrow head Down
        path.moveTo(200, 350);
        path.lineTo(250, 400);
        path.lineTo(300, 350);
        path.close();
        
        canvas.drawPath(path, paint);
        
        // Draw blue outline to match user image
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        canvas.drawPath(path, paint);

        return bitmap;
    }

    private static Bitmap generateHexagon() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        Path path = new Path();
        float centerX = size / 2f;
        float centerY = size / 2f;
        float radius = size * 0.4f;
        for (int i = 0; i < 6; i++) {
            float angle = (float) (i * Math.PI / 3);
            float x = (float) (centerX + radius * Math.cos(angle));
            float y = (float) (centerY + radius * Math.sin(angle));
            if (i == 0) path.moveTo(x, y);
            else path.lineTo(x, y);
        }
        path.close();
        canvas.drawPath(path, paint);
        return bitmap;
    }

    private static Bitmap generateKite() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        Path path = new Path();
        path.moveTo(250, 50);  // Top
        path.lineTo(400, 200); // Right
        path.lineTo(250, 450); // Bottom
        path.lineTo(100, 200); // Left
        path.close();
        canvas.drawPath(path, paint);
        return bitmap;
    }

    private static Bitmap generateParallelogram() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        Path path = new Path();
        path.moveTo(150, 150);
        path.lineTo(450, 150);
        path.lineTo(350, 350);
        path.lineTo(50, 350);
        path.close();
        canvas.drawPath(path, paint);
        return bitmap;
    }

    private static Bitmap generateWindMill() {
        int size = 500;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        
        float centerX = size / 2f;
        float centerY = size / 2f;
        float radius = size * 0.4f;
        
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(3);
        
        Paint flagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        flagPaint.setStyle(Paint.Style.FILL);
        
        int numSpokes = 12;
        for (int i = 0; i < numSpokes; i++) {
            float angle = (float) (i * 2 * Math.PI / numSpokes);
            float stopX = (float) (centerX + radius * Math.cos(angle - Math.PI / 2));
            float stopY = (float) (centerY + radius * Math.sin(angle - Math.PI / 2));
            
            // Draw line
            canvas.drawLine(centerX, centerY, stopX, stopY, linePaint);
            
            // Draw flag
            Path flagPath = new Path();
            flagPath.moveTo(stopX, stopY);
            
            // Flag color alternates every 6 spokes for order 2 symmetry
            // The user image has 12 spokes. 
            // Colors: R, Y, Y, R, R, R, R, Y, Y, R, R, R? No.
            // Let's look closely at image: 
            // Top (0 deg): Red
            // 30 deg: Yellow
            // 60 deg: Yellow
            // 90 deg: Red
            // 120 deg: Red
            // 150 deg: Red
            // 180 deg: Yellow (Wait, let's re-examine pattern)
            // It seems to be a complex pattern. 
            // However, a standard rotational symmetry order 2 wind mill usually repeats every 180 degrees.
            
            if (i < 6) {
                // First half pattern: R, Y, Y, R, R, R
                int[] colors = {Color.RED, Color.YELLOW, Color.YELLOW, Color.RED, Color.RED, Color.RED};
                flagPaint.setColor(colors[i]);
            } else {
                // Repeat second half for order 2
                int[] colors = {Color.RED, Color.YELLOW, Color.YELLOW, Color.RED, Color.RED, Color.RED};
                flagPaint.setColor(colors[i - 6]);
            }

            // Simple triangular flag logic
            float nextAngle = angle - (float)(Math.PI / 2) + (float)(Math.PI / 8);
            float fX = (float) (centerX + (radius * 0.8f) * Math.cos(nextAngle));
            float fY = (float) (centerY + (radius * 0.8f) * Math.sin(nextAngle));
            flagPath.lineTo(fX, fY);
            
            float prevAngle = angle - (float)(Math.PI / 2) - (float)(Math.PI / 18);
            float bX = (float) (centerX + (radius * 0.9f) * Math.cos(prevAngle));
            float bY = (float) (centerY + (radius * 0.9f) * Math.sin(prevAngle));
            flagPath.lineTo(bX, bY);
            
            flagPath.close();
            canvas.drawPath(flagPath, flagPaint);
        }

        return bitmap;
    }

    private static Bitmap generateWaveShape() {
        int width = 600;
        int height = 300;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.rgb(176, 224, 230)); // Light blue background

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);

        float centerY = height / 2f;
        float startX = 50;
        float endX = 550;
        float centerX = width / 2f;

        // Horizontal Line
        canvas.drawLine(startX, centerY, endX, centerY, paint);

        // Top arc (left)
        canvas.drawArc(startX, centerY - 100, centerX, centerY + 100, 180, 180, false, paint);

        // Bottom arc (right)
        canvas.drawArc(centerX, centerY - 100, endX, centerY + 100, 0, 180, false, paint);

        // Marked point 'X'
        Paint xPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xPaint.setColor(Color.BLACK);
        xPaint.setStrokeWidth(8);
        float xSize = 20;
        canvas.drawLine(centerX - xSize, centerY - xSize, centerX + xSize, centerY + xSize, xPaint);
        canvas.drawLine(centerX + xSize, centerY - xSize, centerX - xSize, centerY + xSize, xPaint);

        return bitmap;
    }

    private static Bitmap generateSymmetryTable(int missingIndex) {
        int width = 1000;
        int height = 650;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        float left = 100, top = 70, right = 900;
        float rowHeight = 95;
        float columnDivider = 500;

        // Header Background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(245, 245, 245));
        canvas.drawRect(left, top, right, top + rowHeight, paint);

        // Grid
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(left, top, right, top + 5 * rowHeight, paint);
        canvas.drawLine(columnDivider, top, columnDivider, top + 5 * rowHeight, paint);
        for (int i = 1; i < 5; i++) {
            canvas.drawLine(left, top + i * rowHeight, right, top + i * rowHeight, paint);
        }

        // Text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(36);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFakeBoldText(true);
        float headerY = top + rowHeight/2 - (paint.ascent() + paint.descent())/2;
        canvas.drawText("Shape", (left + columnDivider)/2, headerY, paint);
        
        // Multi-line header for 2nd column to prevent overflow
        paint.setTextSize(32); // Slightly smaller for long header
        String line1 = "Order of Rotational";
        String line2 = "Symmetry";
        float lineSpacing = 38;
        float multiHeaderY = top + rowHeight/2; // Center of the row
        canvas.drawText(line1, (columnDivider + right)/2, multiHeaderY - 10, paint);
        canvas.drawText(line2, (columnDivider + right)/2, multiHeaderY + lineSpacing - 10, paint);
        paint.setTextSize(36); // Reset

        String[] shapes = {"Regular Triangle", "Square", "Regular Pentagon", "Regular Octagon"};
        int[] orders = {3, 4, 5, 8};
        paint.setFakeBoldText(false);
        for (int i = 0; i < shapes.length; i++) {
            float y = top + (i + 1) * rowHeight + rowHeight/2 - (paint.ascent() + paint.descent())/2;
            canvas.drawText(shapes[i], (left + columnDivider)/2, y, paint);
            String val = (i == missingIndex) ? "?" : String.valueOf(orders[i]);
            canvas.drawText(val, (columnDivider + right)/2, y, paint);
        }

        return bitmap;
    }

    private static void drawLabel(Canvas canvas, String text, float x, float y, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, x, y, paint);
    }
}
