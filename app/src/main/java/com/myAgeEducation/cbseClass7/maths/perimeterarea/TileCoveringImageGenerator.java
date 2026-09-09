package com.myAgeEducation.cbseClass7.maths.perimeterarea;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import com.myAgeEducation.cbseClass7.utils.ImageCodeParser;
import java.util.Map;

public class TileCoveringImageGenerator {

    public static Bitmap generate(String imageCode) {
        Map<String, String> values = ImageCodeParser.parse(imageCode);
        int cols = Integer.parseInt(values.get("COLS"));
        int rows = Integer.parseInt(values.get("ROWS"));

        int cellSize = 60;
        int padding = 40;
        int width = cols * cellSize + 2 * padding;
        int height = rows * cellSize + 2 * padding;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(1.5f);

        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2.0f);

        // Draw grid
        for (int i = 0; i <= cols; i++) {
            canvas.drawLine(padding + i * cellSize, padding, padding + i * cellSize, padding + rows * cellSize, gridPaint);
        }
        for (int i = 0; i <= rows; i++) {
            canvas.drawLine(padding, padding + i * cellSize, padding + cols * cellSize, padding + i * cellSize, gridPaint);
        }
        // Draw outer border
        canvas.drawRect(padding, padding, padding + cols * cellSize, padding + rows * cellSize, borderPaint);

        // DATA format: SHAPE1|SHAPE2|...
        // SHAPE: TYPE,COL,ROW,W,H,COLOR
        String data = values.get("DATA");
        if (data != null && !data.isEmpty()) {
            String[] shapes = data.split("\\|");
            for (String shape : shapes) {
                String[] p = shape.split(",");
                if (p.length < 6) continue;
                int type = Integer.parseInt(p[0]);
                if (type == 5) {
                    // COMPOSITE SHAPE: 5,COLOR,LABEL,C1,R1,C2,R2...
                    int color = Color.parseColor(p[1]);
                    String label = p[2];
                    drawCompositeShape(canvas, p, color, label, cellSize, padding);
                } else {
                    int col = Integer.parseInt(p[1]);
                    int row = Integer.parseInt(p[2]);
                    int w = Integer.parseInt(p[3]);
                    int h = Integer.parseInt(p[4]);
                    int color = Color.parseColor(p[5]);
                    String label = p.length > 6 ? p[6] : "";

                    drawShape(canvas, type, col, row, w, h, color, label, cellSize, padding);
                }
            }
        }

        return bitmap;
    }

    private static void drawCompositeShape(Canvas canvas, String[] p, int color, String label, int cellSize, int padding) {
        Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(color);
        fillPaint.setStyle(Paint.Style.FILL);

        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(3.0f); // Thicker border

        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

        float gap = 2.0f; // Gap between cells to see grid lines clearly

        for (int i = 3; i < p.length; i += 2) {
            int col = Integer.parseInt(p[i]);
            int row = Integer.parseInt(p[i+1]);
            float left = padding + (col - 1) * cellSize;
            float top = padding + (row - 1) * cellSize;
            
            // Draw slightly smaller rect to keep grid lines visible between same-color cells
            canvas.drawRect(left + gap, top + gap, left + cellSize - gap, top + cellSize - gap, fillPaint);
            canvas.drawRect(left + gap, top + gap, left + cellSize - gap, top + cellSize - gap, strokePaint);
            
            minX = Math.min(minX, left);
            minY = Math.min(minY, top);
            maxX = Math.max(maxX, left + cellSize);
            maxY = Math.max(maxY, top + cellSize);
        }

        if (label != null && !label.isEmpty()) {
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(40); // Much bigger text
            textPaint.setFakeBoldText(true);
            textPaint.setTextAlign(Paint.Align.CENTER);
            
            // Draw label below or inside depending on space
            canvas.drawText(label, (minX + maxX) / 2f, maxY + 45, textPaint);
        }
    }

    private static void drawShape(Canvas canvas, int type, int col, int row, int w, int h, int color, String label, int cellSize, int padding) {
        Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(color);
        fillPaint.setStyle(Paint.Style.FILL);

        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(3.0f); // Thicker border

        float left = padding + (col - 1) * cellSize;
        float top = padding + (row - 1) * cellSize;
        float right = left + w * cellSize;
        float bottom = top + h * cellSize;

        float gap = 2.0f;

        if (type == 0) { // Square/Rectangle
            canvas.drawRect(left + gap, top + gap, right - gap, bottom - gap, fillPaint);
            canvas.drawRect(left + gap, top + gap, right - gap, bottom - gap, strokePaint);
        } else {
            Path path = new Path();
            // Complex paths for triangles should also respect gap, but let's simplify for now
            switch (type) {
                case 1: // Top-Right half (TL, TR, BR)
                    path.moveTo(left, top);
                    path.lineTo(right, top);
                    path.lineTo(right, bottom);
                    break;
                case 2: // Top-Left half (TL, TR, BL)
                    path.moveTo(left, top);
                    path.lineTo(right, top);
                    path.lineTo(left, bottom);
                    break;
                case 3: // Bottom-Left half (TL, BL, BR)
                    path.moveTo(left, top);
                    path.lineTo(left, bottom);
                    path.lineTo(right, bottom);
                    break;
                case 4: // Bottom-Right half (TR, BR, BL)
                    path.moveTo(right, top);
                    path.lineTo(right, bottom);
                    path.lineTo(left, bottom);
                    break;
                case 6: // Large Triangle (Base at top, point at bottom center)
                    path.moveTo(left, top);
                    path.lineTo(right, top);
                    path.lineTo(left + (w * cellSize) / 2f, bottom);
                    break;
            }
            path.close();
            canvas.drawPath(path, fillPaint);
            canvas.drawPath(path, strokePaint);
        }

        if (label != null && !label.isEmpty()) {
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(40); // Much bigger text
            textPaint.setFakeBoldText(true);
            textPaint.setTextAlign(Paint.Align.CENTER);
            
            if (label.length() <= 3) {
                 // Try drawing label just below the shape for consistency with irregular shapes
                 canvas.drawText(label, left + (w * cellSize) / 2f, bottom + 45, textPaint);
            } else {
                canvas.drawText(label, left + (w * cellSize) / 2f, top + (h * cellSize) / 2f + 15, textPaint);
            }
        }
    }
}
