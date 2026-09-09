package com.myAgeEducation.cbseClass7.maths.mappingskills;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class MetroMapImageGenerator {
    private static final int IMAGE_WIDTH = 900;
    private static final int IMAGE_HEIGHT = 900;
    private static final int GRID_SIZE = 10;
    private static final float MARGIN = 100;
    private static final float CELL_SIZE = (IMAGE_WIDTH - 2f * MARGIN) / GRID_SIZE;

    private MetroMapImageGenerator() {}

    public static Bitmap generate(Context context, String imageCode) {
        String[] parts = imageCode.split("_");
        String cityName = parts[1];
        int lineCount = Integer.parseInt(parts[2]);
        
        List<MetroLine> lines = new ArrayList<>();
        java.util.Map<String, MetroStation> stationMap = new java.util.HashMap<>();

        for (int i = 0; i < lineCount; i++) {
            String[] lineParts = parts[3 + i].split(":");
            String id = lineParts[0];
            String name = lineParts[1];
            int color = Integer.parseInt(lineParts[2]);
            int stationCount = Integer.parseInt(lineParts[3]);
            
            MetroLine line = new MetroLine(id, name, color);
            int k = 4;
            for (int j = 0; j < stationCount; j++) {
                String sName = lineParts[k++];
                int x = Integer.parseInt(lineParts[k++]);
                int y = Integer.parseInt(lineParts[k++]);
                
                MetroStation s = stationMap.get(sName);
                if (s == null) {
                    s = new MetroStation(sName, x, y);
                    stationMap.put(sName, s);
                }
                line.addStation(s);
            }
            lines.add(line);
        }

        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        drawTitle(canvas, cityName);
        drawLines(canvas, lines);
        drawStations(canvas, lines);
        drawLegend(canvas, lines);
        drawCompass(canvas);

        return bitmap;
    }

    private static void drawTitle(Canvas canvas, String cityName) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        paint.setFakeBoldText(true);
        canvas.drawText(cityName + " Metro Map", MARGIN, MARGIN - 40, paint);
    }

    private static void drawLines(Canvas canvas, List<MetroLine> lines) {
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(12);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);

        for (MetroLine line : lines) {
            linePaint.setColor(line.getColor());
            List<MetroStation> stations = line.getStations();
            for (int i = 0; i < stations.size() - 1; i++) {
                MetroStation s1 = stations.get(i);
                MetroStation s2 = stations.get(i + 1);
                canvas.drawLine(getX(s1.getX()), getY(s1.getY()), getX(s2.getX()), getY(s2.getY()), linePaint);
            }
        }
    }

    private static void drawStations(Canvas canvas, List<MetroLine> lines) {
        Paint stationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        stationPaint.setColor(Color.WHITE);
        stationPaint.setStyle(Paint.Style.FILL);

        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(3);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(22);
        textPaint.setFakeBoldText(true);

        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.argb(180, 255, 255, 255)); // Semi-transparent white
        bgPaint.setStyle(Paint.Style.FILL);

        // To avoid drawing same station multiple times
        List<String> drawnStations = new ArrayList<>();

        for (MetroLine line : lines) {
            for (MetroStation s : line.getStations()) {
                String key = s.getName();
                float px = getX(s.getX());
                float py = getY(s.getY());

                if (!drawnStations.contains(key)) {
                    if (s.isInterchange()) {
                        stationPaint.setColor(Color.YELLOW);
                        canvas.drawCircle(px, py, 12, stationPaint);
                        canvas.drawCircle(px, py, 12, borderPaint);
                    } else {
                        stationPaint.setColor(Color.WHITE);
                        canvas.drawCircle(px, py, 8, stationPaint);
                        canvas.drawCircle(px, py, 8, borderPaint);
                    }
                    
                    // Improved label drawing with background for readability
                    String label = s.getName();
                    float textWidth = textPaint.measureText(label);
                    float textHeight = textPaint.getTextSize();
                    
                    // Position label slightly above and to the right of the station circle
                    float tx = px + 15;
                    float ty = py - 15;
                    
                    // Draw a small background rectangle to make text readable over lines
                    RectF rect = new RectF(tx - 5, ty - textHeight, tx + textWidth + 5, ty + 5);
                    canvas.drawRoundRect(rect, 5, 5, bgPaint);
                    
                    canvas.drawText(label, tx, ty, textPaint);
                    drawnStations.add(key);
                } else if (s.isInterchange()) {
                     // Redraw interchange if it was drawn as regular station first
                     stationPaint.setColor(Color.YELLOW);
                     canvas.drawCircle(px, py, 12, stationPaint);
                     canvas.drawCircle(px, py, 12, borderPaint);
                }
            }
        }
    }

    private static void drawLegend(Canvas canvas, List<MetroLine> lines) {
        // Single row legend at the bottom
        float lx = MARGIN;
        float ly = IMAGE_HEIGHT - 60;
        
        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.argb(220, 240, 240, 240));
        canvas.drawRoundRect(new RectF(lx - 20, ly - 35, IMAGE_WIDTH - MARGIN + 20, ly + 40), 10, 10, bgPaint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20);
        textPaint.setFakeBoldText(true);
        canvas.drawText("KEYS:", lx, ly + 5, textPaint);

        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(8);
        
        float currentX = lx + 80;
        for (MetroLine line : lines) {
            linePaint.setColor(line.getColor());
            canvas.drawLine(currentX, ly - 5, currentX + 30, ly - 5, linePaint);
            canvas.drawText(line.getName(), currentX + 40, ly + 5, textPaint);
            
            // Increment X for the next item based on text length
            currentX += textPaint.measureText(line.getName()) + 80;
        }
    }

    private static void drawCompass(Canvas canvas) {
        float cx = IMAGE_WIDTH - MARGIN - 50;
        float cy = MARGIN + 50;
        
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(2);
        p.setStyle(Paint.Style.STROKE);
        
        canvas.drawLine(cx, cy - 40, cx, cy + 40, p);
        canvas.drawLine(cx - 40, cy, cx + 40, cy, p);
        
        p.setTextSize(20);
        p.setFakeBoldText(true);
        p.setStyle(Paint.Style.FILL);
        canvas.drawText("N", cx - 8, cy - 45, p);
        canvas.drawText("S", cx - 8, cy + 60, p);
        canvas.drawText("E", cx + 45, cy + 7, p);
        canvas.drawText("W", cx - 65, cy + 7, p);
    }

    private static float getX(int gridX) {
        return MARGIN + (gridX - 1) * CELL_SIZE;
    }

    private static float getY(int gridY) {
        return MARGIN + (gridY - 1) * CELL_SIZE;
    }
}
