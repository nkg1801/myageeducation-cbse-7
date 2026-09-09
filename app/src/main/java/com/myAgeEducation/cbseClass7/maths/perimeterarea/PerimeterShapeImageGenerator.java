package com.myAgeEducation.cbseClass7.maths.perimeterarea;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import com.myAgeEducation.cbseClass7.utils.ImageCodeParser;
import java.util.Map;

public class PerimeterShapeImageGenerator {

    public static Bitmap generate(String imageCode) {
        Map<String, String> values = ImageCodeParser.parse(imageCode);
        String verticesStr = values.get("VERTICES");
        String labelsStr = values.get("LABELS");

        if (verticesStr == null || labelsStr == null) return null;

        String[] vParts = verticesStr.split("\\|");
        String[] lParts = labelsStr.split("\\|");

        int width = 800;
        int height = 500;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);

        Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(Color.rgb(100, 150, 220)); // Light blue like image
        fillPaint.setStyle(Paint.Style.FILL);

        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.rgb(150, 180, 230));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(3);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(35);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Path path = new Path();
        float[][] points = new float[vParts.length][2];
        for (int i = 0; i < vParts.length; i++) {
            String[] coord = vParts[i].split(",");
            points[i][0] = Float.parseFloat(coord[0]);
            points[i][1] = Float.parseFloat(coord[1]);
            if (i == 0) path.moveTo(points[i][0], points[i][1]);
            else path.lineTo(points[i][0], points[i][1]);
        }
        path.close();

        canvas.drawPath(path, fillPaint);
        canvas.drawPath(path, borderPaint);

        // Draw labels
        for (int i = 0; i < points.length; i++) {
            float x1 = points[i][0];
            float y1 = points[i][1];
            float x2 = points[(i + 1) % points.length][0];
            float y2 = points[(i + 1) % points.length][1];

            float midX = (x1 + x2) / 2f;
            float midY = (y1 + y2) / 2f;

            // Offset label from side
            float dx = x2 - x1;
            float dy = y2 - y1;
            float length = (float) Math.sqrt(dx * dx + dy * dy);
            float nx = -dy / length;
            float ny = dx / length;

            // Heuristic to ensure labels are outside for simple convex polygons
            // This is basic, might need refinement for concave
            float offsetX = nx * 30;
            float offsetY = ny * 30;

            canvas.drawText(lParts[i], midX + offsetX, midY + offsetY + 12, textPaint);
        }

        return bitmap;
    }
}
