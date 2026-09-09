package com.myAgeEducation.cbseClass7.maths.mappingskills;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class NeighborhoodMapImageGenerator {
    private static final int IMAGE_WIDTH = 900;
    private static final int IMAGE_HEIGHT = 900;
    private static final int GRID_SIZE = 10;
    private static final int MARGIN = 60;
    private static final float CELL_SIZE = (IMAGE_WIDTH - 2f * MARGIN) / GRID_SIZE;

    private NeighborhoodMapImageGenerator() {}

    public static Bitmap generate(Context context, String imageCode) {
        String[] parts = imageCode.split("_", 2);
        String[] subParts = parts[1].split(":");
        int count = Integer.parseInt(subParts[0]);
        List<MapLandmark> landmarks = new ArrayList<>();
        int k = 1;
        for (int i = 0; i < count; i++) {
            String name = subParts[k++];
            int x = Integer.parseInt(subParts[k++]);
            int y = Integer.parseInt(subParts[k++]);
            
            MapLandmark.Type type = MapLandmark.Type.HOUSE;
            if (name.startsWith("stop")) type = MapLandmark.Type.STOP;
            else if (name.equals("hospital") || name.equals("shopping_center") || 
                     name.equals("basketball_court") || name.equals("children_park") || 
                     name.equals("parking")) {
                type = MapLandmark.Type.FACILITY;
            }

            MapLandmark l = new MapLandmark(name, formatLabel(name), type);
            l.setX(x);
            l.setY(y);
            landmarks.add(l);
        }

        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.rgb(220, 240, 220)); // Pale green grass

        drawGridAndRoads(canvas);
        drawCompass(canvas);
        drawLandmarks(context, canvas, landmarks);

        return bitmap;
    }


    private static String formatLabel(String name) {
        if (name.endsWith("_house")) {
            String person = name.substring(0, name.indexOf("_house"));
            return person.substring(0, 1).toUpperCase() + person.substring(1) + "'s House";
        }
        if (name.equals("stop1")) return "Stop 1";
        if (name.equals("stop2")) return "Stop 2";

        String label = name.replace("_", " ");
        return label.substring(0, 1).toUpperCase() + label.substring(1);
    }

    private static void drawGridAndRoads(Canvas canvas) {
        Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.rgb(180, 200, 180));
        gridPaint.setStrokeWidth(1);
        gridPaint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i <= GRID_SIZE; i++) {
            float pos = MARGIN + i * CELL_SIZE;
            canvas.drawLine(pos, MARGIN, pos, IMAGE_HEIGHT - MARGIN, gridPaint);
            canvas.drawLine(MARGIN, pos, IMAGE_WIDTH - MARGIN, pos, gridPaint);
        }

        // Draw some "roads" as wider lines on some grid lines
        Paint roadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        roadPaint.setColor(Color.rgb(150, 150, 150));
        roadPaint.setStrokeWidth(30);
        
        // Vertical roads
        canvas.drawLine(MARGIN + 2 * CELL_SIZE, MARGIN, MARGIN + 2 * CELL_SIZE, IMAGE_HEIGHT - MARGIN, roadPaint);
        canvas.drawLine(MARGIN + 8 * CELL_SIZE, MARGIN, MARGIN + 8 * CELL_SIZE, IMAGE_HEIGHT - MARGIN, roadPaint);
        
        // Horizontal roads
        canvas.drawLine(MARGIN, MARGIN + 3 * CELL_SIZE, IMAGE_WIDTH - MARGIN, MARGIN + 3 * CELL_SIZE, roadPaint);
        canvas.drawLine(MARGIN, MARGIN + 7 * CELL_SIZE, IMAGE_WIDTH - MARGIN, MARGIN + 7 * CELL_SIZE, roadPaint);
    }

    private static void drawCompass(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(24);
        paint.setTextAlign(Paint.Align.CENTER);
        
        float cx = IMAGE_WIDTH - MARGIN - 40;
        float cy = MARGIN + 40;
        
        canvas.drawText("N", cx, cy - 25, paint);
        canvas.drawText("S", cx, cy + 45, paint);
        canvas.drawText("W", cx - 35, cy + 10, paint);
        canvas.drawText("E", cx + 35, cy + 10, paint);
        
        paint.setStrokeWidth(2);
        canvas.drawLine(cx, cy - 20, cx, cy + 20, paint);
        canvas.drawLine(cx - 20, cy, cx + 20, cy, paint);
    }

    private static void drawLandmarks(Context context, Canvas canvas, List<MapLandmark> landmarks) {
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(22);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

        for (MapLandmark l : landmarks) {
            float px = MARGIN + (l.getX() - 1) * CELL_SIZE + CELL_SIZE / 2;
            float py = IMAGE_HEIGHT - (MARGIN + (l.getY() - 1) * CELL_SIZE + CELL_SIZE / 2);

            // Try to load icon
            int resId = context.getResources().getIdentifier(l.getName(), "drawable", context.getPackageName());
            if (resId != 0) {
                Bitmap b = BitmapFactory.decodeResource(context.getResources(), resId);
                if (b != null) {
                    float sizeMultiplier = (l.getType() == MapLandmark.Type.FACILITY) ? 0.95f : 0.7f;
                    float size = CELL_SIZE * sizeMultiplier;
                    canvas.drawBitmap(b, null, new RectF(px - size / 2, py - size / 2, px + size / 2, py + size / 2), null);
                }
            } else {
                // Draw a simple square or circle
                Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
                if (l.getName().startsWith("stop")) p.setColor(Color.RED);
                else p.setColor(Color.rgb(100, 100, 255));
                
                float size = 20;
                canvas.drawRect(px - size, py - size, px + size, py + size, p);
            }

            canvas.drawText(l.getLabel(), px, py + 55, textPaint);
        }
    }
}
