package com.myAgeEducation.cbseClass7.maths.LineAndAngle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class AngleImageGenerator {
    private static final int IMAGE_WIDTH = 700;
    private static final int SINGLE_ANGLE_IMAGE_WIDTH = 1000;
    private static final int IMAGE_HEIGHT = 450;
    private static final int SINGLE_IMAGE_IMAGE_HEIGHT = 1000;

    public AngleImageGenerator()
    {

    }

    public static Bitmap generateImage(String imageCode)
    {
        String[] parts = imageCode.split("_");

        if (parts.length < 3)
        {
            throw new IllegalArgumentException("Invalid image code: " + imageCode);
        }

        String subType = parts[1];
        boolean showDegree;

        switch (subType)
        {
            case "SINGLE":
                return generateAngleImage(
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]) == 1);

            case "TWO":
                return generateTwoAnglesImage(
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]) == 1);

            case "MULTIPLE":
                int[] angles = new int[4];
                for (int i = 0; i < 4; i++) {
                    angles[i] = Integer.parseInt(parts[2 + i]);
                }
                showDegree = Integer.parseInt(parts[6]) == 1;
                return generateMultipleAnglesImage(angles, showDegree);

            case "PAIRS":
                int[][] pairs = new int[4][2];
                for (int i = 0; i < 4; i++) {
                    pairs[i][0] = Integer.parseInt(parts[2 + i * 2]);
                    pairs[i][1] = Integer.parseInt(parts[3 + i * 2]);
                }
                showDegree = Integer.parseInt(parts[10]) == 1;
                return generateAnglePairsImage(pairs, showDegree);

            default:
                // Fallback for old codes or direct angle code
                return generateAngleImage(
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]) == 1);
        }
    }

    private static Bitmap generateTwoAnglesImage(int angleA, int angleB, boolean showDegree)
    {
        int width = 900;
        int height = 500;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(android.graphics.Color.WHITE);

        float centerAX = 250;
        float centerAY = 300;
        float centerBX = 650;
        float centerBY = 300;

        drawSmallAngle(canvas, centerAX, centerAY, angleA, "A", showDegree, 110);
        drawSmallAngle(canvas, centerBX, centerBY, angleB, "B", showDegree, 110);

        return bitmap;
    }

    private static Bitmap generateMultipleAnglesImage(int[] angles, boolean showDegree)
    {
        int width = 900;
        int height = 650;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(android.graphics.Color.WHITE);

        float[][] centers =
                {
                        {220, 220},
                        {680, 220},
                        {220, 520},
                        {680, 520}
                };

        for (int i = 0; i < 4; i++)
        {
            drawSmallAngle(canvas, centers[i][0], centers[i][1], angles[i], String.valueOf((char) ('A' + i)), showDegree, 110);
        }

        return bitmap;
    }

    private static Bitmap generateAnglePairsImage(int[][] pairs, boolean showDegree)
    {
        int width = 900;
        int height = 650;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(android.graphics.Color.WHITE);

        float[][] centers =
                {
                        {220, 200},
                        {680, 200},
                        {220, 510},
                        {680, 510}
                };

        for (int i = 0; i < 4; i++)
        {
            drawAnglePair(canvas, centers[i][0], centers[i][1], pairs[i][0], pairs[i][1], String.valueOf((char) ('A' + i)), showDegree);
        }

        return bitmap;
    }

    private static void drawAnglePair(
            Canvas canvas,
            float centerX,
            float centerY,
            int angle1,
            int angle2,
            String label,
            boolean showDegree)
    {
        /*
         * Keep the two angles clearly separated.
         *
         * Each angle gets its own area.
         */
        float separation = 100;
        float leftX = centerX - separation;
        float rightX = centerX + separation;

        /*
         * Use a shorter ray length for pair questions.
         * This prevents the two diagrams from touching,
         * especially for obtuse angles.
         */
        float rayLength = 75;

        drawSmallAngle(
                canvas,
                leftX,
                centerY,
                angle1,
                "",
                showDegree,
                rayLength);

        drawSmallAngle(
                canvas,
                rightX,
                centerY,
                angle2,
                "",
                showDegree,
                rayLength);

        /*
         * Pair label: A, B, C or D.
         */
        Paint labelPaint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        labelPaint.setColor(
                android.graphics.Color.BLACK);

        labelPaint.setTextSize(34);

        labelPaint.setTextAlign(
                Paint.Align.CENTER);

        canvas.drawText(
                label,
                centerX,
                centerY - 115,
                labelPaint);
    }

    /*private static void drawSmallAngle(Canvas canvas, float centerX, float centerY, int angle, String label, boolean showDegree)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(android.graphics.Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        float length = 110;
        float x1 = centerX + length;
        float y1 = centerY;
        canvas.drawLine(centerX, centerY, x1, y1, paint);

        double radians = Math.toRadians(angle);
        float x2 = centerX + (float) (length * Math.cos(radians));
        float y2 = centerY - (float) (length * Math.sin(radians));
        canvas.drawLine(centerX, centerY, x2, y2, paint);

        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(android.graphics.Color.BLACK);
        fill.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, 7, fill);

        if (angle < 180)
        {
            RectF rect = new RectF(centerX - 45, centerY - 45, centerX + 45, centerY + 45);
            canvas.drawArc(rect, 0, -angle, false, paint);
        }

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(android.graphics.Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);

        if (showDegree)
        {
            textPaint.setTextSize(30);
            float textRadius = 70;
            double midRadians = Math.toRadians(angle / 2.0);
            float textX = centerX + (float) (textRadius * Math.cos(midRadians));
            float textY = centerY - (float) (textRadius * Math.sin(midRadians));

            if (angle == 180)
            {
                textX = centerX;
                textY = centerY - 35;
            }

            canvas.drawText(angle + "°", textX, textY, textPaint);
        }

        textPaint.setTextSize(34);
        canvas.drawText(label, centerX, centerY - 80, textPaint);
    }*/

    private static void drawSmallAngle(
            Canvas canvas,
            float centerX,
            float centerY,
            int angle,
            String label,
            boolean showDegree,
            float rayLength)
    {
        Paint linePaint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        linePaint.setColor(
                android.graphics.Color.BLACK);

        linePaint.setStyle(
                Paint.Style.STROKE);

        linePaint.setStrokeWidth(5);

        /*
         * First ray points to the right.
         */
        float firstX =
                centerX + rayLength;

        float firstY =
                centerY;

        canvas.drawLine(
                centerX,
                centerY,
                firstX,
                firstY,
                linePaint);

        /*
         * Second ray.
         */
        double radians =
                Math.toRadians(angle);

        float secondX;
        float secondY;

        if (angle == 180)
        {
            secondX =
                    centerX - rayLength;

            secondY =
                    centerY;
        }
        else
        {
            secondX =
                    centerX
                            + (float)
                            (rayLength
                                    * Math.cos(radians));

            secondY =
                    centerY
                            - (float)
                            (rayLength
                                    * Math.sin(radians));
        }

        canvas.drawLine(
                centerX,
                centerY,
                secondX,
                secondY,
                linePaint);

        /*
         * Arrowheads.
         */
        drawArrowHead(
                canvas,
                firstX,
                firstY,
                0);

        float secondDirection =
                (float) Math.toDegrees(radians);

        if (angle == 180)
        {
            secondDirection = 180;
        }

        drawArrowHead(
                canvas,
                secondX,
                secondY,
                secondDirection);

        /*
         * Vertex.
         */
        Paint fillPaint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        fillPaint.setColor(
                android.graphics.Color.BLACK);

        fillPaint.setStyle(
                Paint.Style.FILL);

        canvas.drawCircle(
                centerX,
                centerY,
                7,
                fillPaint);

        /*
         * Angle arc.
         */
        if (angle < 180)
        {
            float arcSize = 35;

            RectF arcRect =
                    new RectF(
                            centerX - arcSize,
                            centerY - arcSize,
                            centerX + arcSize,
                            centerY + arcSize);

            canvas.drawArc(
                    arcRect,
                    0,
                    -angle,
                    false,
                    linePaint);
        }

        /*
         * Text.
         */
        Paint textPaint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        textPaint.setColor(
                android.graphics.Color.BLACK);

        textPaint.setTextAlign(
                Paint.Align.CENTER);

        /*
         * Show degree only when requested.
         */
        if (showDegree)
        {
            textPaint.setTextSize(25);

            float textRadius = 50;

            double textRadians =
                    Math.toRadians(angle / 2.0);

            float textX =
                    centerX
                            + (float)
                            (textRadius
                                    * Math.cos(textRadians));

            float textY =
                    centerY
                            - (float)
                            (textRadius
                                    * Math.sin(textRadians));

            if (angle == 180)
            {
                textX = centerX;
                textY = centerY - 30;
            }

            canvas.drawText(
                    angle + "°",
                    textX,
                    textY,
                    textPaint);
        }

        /*
         * Individual label, used by the normal
         * four-angle questions.
         */
        if (label != null && !label.isEmpty())
        {
            textPaint.setTextSize(34);

            float labelY =
                    centerY
                            - rayLength
                            - 30;

            canvas.drawText(
                    label,
                    centerX,
                    labelY,
                    textPaint);
        }
    }

    private static Bitmap generateAngleImage(int angle, boolean showDegree)
    {
        Bitmap bitmap = Bitmap.createBitmap(SINGLE_ANGLE_IMAGE_WIDTH, SINGLE_IMAGE_IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(android.graphics.Color.WHITE);
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(android.graphics.Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(6);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(android.graphics.Color.BLACK);
        textPaint.setTextSize(42);
        textPaint.setTextAlign(Paint.Align.CENTER);

        /*
         * Vertex.
         */
        float centerX = IMAGE_WIDTH * 0.50f;
        float centerY = IMAGE_HEIGHT * 0.68f;

        /*
         * Right ray is fixed horizontally.
         */
        float rayLength = 210;
        float rightX = centerX + rayLength;
        float rightY = centerY;

        /*
         * The second ray is calculated from
         * the requested angle.
         *
         * Android's Y axis points downwards,
         * so we use -sin(angle).
         */
        float secondX;
        float secondY;
        double radians = Math.toRadians(angle);

        if (angle == 180)
        {
            /*
             * Straight angle.
             */
            secondX = centerX - rayLength;
            secondY = centerY;
        }
        else
        {
            secondX = centerX + (float) (rayLength * Math.cos(radians));
            secondY = centerY - (float) (rayLength * Math.sin(radians));
        }

        /*
         * Draw first ray.
         */
        canvas.drawLine(centerX, centerY, rightX, rightY, linePaint);

        /*
         * Draw second ray.
         */
        canvas.drawLine(centerX, centerY, secondX, secondY, linePaint);

        /*
         * Draw arrow heads.
         */
        drawArrowHead(canvas, rightX, rightY,0);
        drawArrowHead(canvas, secondX, secondY, (float) Math.toDegrees(radians));

        /*
         * Draw vertex.
         */
        Paint vertexPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        vertexPaint.setColor(android.graphics.Color.BLACK);
        vertexPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, 9, vertexPaint);

        /*
         * Draw the angle arc.
         */
        float arcRadius = 85;

        RectF arcRect = new RectF(centerX - arcRadius, centerY - arcRadius, centerX + arcRadius, centerY + arcRadius);
        canvas.drawArc(arcRect, 0, -angle, false, linePaint);

        /*
         * Degree text.
         */
        if (showDegree)
        {
            float textRadius = 125;
            double textRadians = Math.toRadians(angle / 2.0);
            float textX = centerX + (float) (textRadius * Math.cos(textRadians));
            float textY = centerY - (float) (textRadius * Math.sin(textRadians));

            if (angle == 180)
            {
                textX = centerX;
                textY = centerY - 45;
            }

            canvas.drawText(angle + "°", textX, textY, textPaint);
        }

        return bitmap;
    }

    private static void drawArrowHead(Canvas canvas, float x, float y, float directionDegrees)
    {
        float arrowSize = 25;

        double direction =
                Math.toRadians(
                        directionDegrees);

        double leftAngle =
                direction
                        + Math.toRadians(150);

        double rightAngle =
                direction
                        - Math.toRadians(150);

        float x1 =
                x + arrowSize
                        * (float) Math.cos(leftAngle);

        float y1 =
                y - arrowSize
                        * (float) Math.sin(leftAngle);

        float x2 =
                x + arrowSize
                        * (float) Math.cos(rightAngle);

        float y2 =
                y - arrowSize
                        * (float) Math.sin(rightAngle);

        Path path =
                new Path();

        path.moveTo(x, y);
        path.lineTo(x1, y1);
        path.lineTo(x2, y2);
        path.close();

        Paint paint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(
                android.graphics.Color.BLACK);

        paint.setStyle(
                Paint.Style.FILL);

        canvas.drawPath(
                path,
                paint);
    }
}
