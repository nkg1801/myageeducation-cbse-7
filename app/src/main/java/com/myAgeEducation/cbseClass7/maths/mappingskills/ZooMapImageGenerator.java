package com.myAgeEducation.cbseClass7.maths.mappingskills;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.myAgeEducation.cbseClass7.utils.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZooMapImageGenerator {
    private static final int IMAGE_WIDTH = 900;
    private static final int IMAGE_HEIGHT = 900;
    private static final int GRID_SIZE = 12;
    private static final int MARGIN = 60;
    private static final float CELL_SIZE = (IMAGE_WIDTH - 2f * MARGIN) / GRID_SIZE;

    /*private static final int[] LIGHT_BACKGROUND_COLORS =
            {
                    Color.rgb(220, 240, 220), // Light green
                    Color.rgb(220, 235, 250), // Light blue
                    Color.rgb(250, 235, 210), // Light peach
                    Color.rgb(245, 235, 250), // Light lavender
                    Color.rgb(250, 245, 210), // Light yellow
                    Color.rgb(235, 240, 225)  // Light olive
            };*/

    private ZooMapImageGenerator() {}

    public static Bitmap generate(Context context, String imageCode) {
        String[] parts = imageCode.split("_");
        int animalCount = Integer.parseInt(parts[1]);
        List<ZooAnimal> animals = new ArrayList<>();
        int k = 2;
        for (int i = 0; i < animalCount; i++) {
            String name = parts[k++];
            int x = Integer.parseInt(parts[k++]);
            int y = Integer.parseInt(parts[k++]);
            ZooAnimal animal = new ZooAnimal(name, name);
            animal.setX(x);
            animal.setY(y);
            animals.add(animal);
        }

        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        int backgroundColor = Utility.getRandomLightBackgroundColor();
                /*LIGHT_BACKGROUND_COLORS[
                        random.nextInt(
                                LIGHT_BACKGROUND_COLORS.length)];*/

        canvas.drawColor(backgroundColor);
        //canvas.drawColor(Color.rgb(200, 230, 200)); // Light green grass background

        drawGrid(canvas);
        drawAxes(canvas);
        drawAnimals(context, canvas, animals);

        return bitmap;
    }

    private static void drawGrid(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(100, 150, 100));
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i <= GRID_SIZE; i++) {
            float pos = MARGIN + i * CELL_SIZE;
            // Vertical lines
            canvas.drawLine(pos, MARGIN, pos, IMAGE_HEIGHT - MARGIN, paint);
            // Horizontal lines
            canvas.drawLine(MARGIN, pos, IMAGE_WIDTH - MARGIN, pos, paint);
        }
    }

    private static void drawAxes(Canvas canvas) {
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(24);
        textPaint.setTextAlign(Paint.Align.CENTER);

        for (int i = 0; i <= GRID_SIZE; i++) {
            float pos = MARGIN + i * CELL_SIZE;
            // X-axis labels (bottom)
            canvas.drawText(String.valueOf(i), pos, IMAGE_HEIGHT - MARGIN + 30, textPaint);
            // Y-axis labels (left)
            canvas.drawText(String.valueOf(i), MARGIN - 30, IMAGE_HEIGHT - (pos), textPaint);
        }
    }

    private static void drawAnimals(Context context, Canvas canvas, List<ZooAnimal> animals) {
        for (ZooAnimal animal : animals) {
            int resourceId = context.getResources().getIdentifier(animal.getImageName(), "drawable", context.getPackageName());
            if (resourceId != 0) {
                Bitmap animalBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
                if (animalBitmap != null) {
                    float centerX = MARGIN + animal.getX() * CELL_SIZE;
                    float centerY = IMAGE_HEIGHT - (MARGIN + animal.getY() * CELL_SIZE);
                    
                    float size = CELL_SIZE * 1.2f;
                    RectF dest = new RectF(centerX - size / 2, centerY - size / 2, centerX + size / 2, centerY + size / 2);
                    canvas.drawBitmap(animalBitmap, null, dest, null);
                }
            }
        }
    }
}
