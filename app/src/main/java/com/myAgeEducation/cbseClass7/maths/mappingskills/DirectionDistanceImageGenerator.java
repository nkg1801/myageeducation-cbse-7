package com.myAgeEducation.cbseClass7.maths.mappingskills;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import java.util.List;

public class DirectionDistanceImageGenerator
{
    private static final int IMAGE_WIDTH = 900;
    private static final int IMAGE_HEIGHT = 900;

    /*
     * Grid area.
     */
    private static final int GRID_LEFT = 45;
    private static final int GRID_TOP = 45;
    private static final int GRID_SIZE = 810;

    /*
     * 1 cm = 75 pixels.
     *
     * Grid goes approximately from -5 to +5.
     */
    private static final float CELL_SIZE = 75f;

    /*
     * Starting point is always at (0,0).
     */
    private static final int START_X = 0;
    private static final int START_Y = 0;

    private DirectionDistanceImageGenerator()
    {
        // Prevent object creation
    }

    public static Bitmap generate(Context context, String imageCode)
    {
        String[] parts = imageCode.split("_");
        if (parts.length < 4)
        {
            throw new IllegalArgumentException("Invalid image code: " + imageCode);
        }

        String startImage = parts[1];
        String scaleLabel = parts[2];
        int pointCount = Integer.parseInt(parts[3]);
        List<DirectionPoint> points = new java.util.ArrayList<>();
        int k = 4;

        for (int i = 0; i < pointCount; i++)
        {
            String imageName = parts[k++];
            int x = Integer.parseInt(parts[k++]);
            int y = Integer.parseInt(parts[k++]);
            int moveCount = Integer.parseInt(parts[k++]);
            List<DirectionMove> moves = new java.util.ArrayList<>();
            for (int j = 0; j < moveCount; j++)
            {
                int distance = Integer.parseInt(parts[k++]);
                String direction = parts[k++];
                moves.add(new DirectionMove(distance, direction));
            }
            points.add(new DirectionPoint(imageName, imageName, x, y, moves));
        }

        return generate(context, points, startImage, scaleLabel);
    }

    public static Bitmap generate(Context context, List<DirectionPoint> points, String startImage, String scaleLabel)
    {
        Bitmap bitmap =
                Bitmap.createBitmap(
                        IMAGE_WIDTH,
                        IMAGE_HEIGHT,
                        Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        drawGrid(canvas);

        /*
         * Draw compass.
         */
        drawCompass(canvas);

        /*
         * Draw movement paths.
         */
        for (DirectionPoint point : points)
        {
            drawPath(canvas, point);
        }

        drawSubject(context, canvas, points, startImage);
        drawStartingDot(canvas);

        for (DirectionPoint point : points)
        {
            drawDestinationDot(canvas, point);
        }

        /*
         * Draw destination objects or city labels.
         */
        for (DirectionPoint point : points)
        {
            drawObject(context, canvas, point, startImage);
        }

        /*
         * Indicators.
         */
        drawScaleIndicator(canvas, scaleLabel);

        return bitmap;
    }

    private static void drawStartingDot(Canvas canvas)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(0, 0, 255));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(gridToPixelX(START_X), gridToPixelY(START_Y), 25, paint);
    }

    private static void drawDestinationDot(Canvas canvas, DirectionPoint point)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(210, 35, 45));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(gridToPixelX(point.getX()), gridToPixelY(point.getY()), 15, paint);
    }

    private static void drawGrid(Canvas canvas)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(180, 180, 180));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        for (int i = 0; i <= 10; i++)
        {
            float x = GRID_LEFT + i * CELL_SIZE;
            canvas.drawLine(x, GRID_TOP, x, GRID_TOP + GRID_SIZE, paint);
        }

        for (int i = 0; i <= 10; i++)
        {
            float y = GRID_TOP + i * CELL_SIZE;
            canvas.drawLine(GRID_LEFT, y, GRID_LEFT + GRID_SIZE, y, paint);
        }
    }

    private static void drawCompass(Canvas canvas)
    {
        float left = 25;
        float top = 25;
        float size = 150;

        Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(Color.WHITE);
        fillPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(left, top, left + size, top + size, fillPaint);

        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.rgb(80, 50, 20));
        borderPaint.setStyle(Paint.Style.STROKE);

        borderPaint.setStrokeWidth(4);

        canvas.drawRect(
                left,
                top,
                left + size,
                top + size,
                borderPaint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);

        float centerX = left + size / 2;
        float centerY = top + size / 2;
        canvas.drawText("N", centerX, top + 32, textPaint);
        canvas.drawText("S", centerX,top + size - 8, textPaint);
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("W", left + 10, centerY + 10, textPaint);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("E", left + size - 10, centerY + 10, textPaint);
    }

    private static void drawPath(Canvas canvas, DirectionPoint point)
    {
        List<DirectionMove> moves = point.getMoves();

        if (moves == null || moves.isEmpty())
        {
            return;
        }

        Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.rgb(220, 30, 40));
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(5);

        pathPaint.setPathEffect(new DashPathEffect(new float[] {12, 8 }, 0));

        Path path = new Path();
        int currentX = START_X;
        int currentY = START_Y;

        float startPixelX = gridToPixelX(currentX);
        float startPixelY = gridToPixelY(currentY);

        path.moveTo(startPixelX, startPixelY);

        for (DirectionMove move : moves)
        {
            int nextX = currentX;
            int nextY = currentY;

            switch (move.getDirection())
            {
                case "east":
                    nextX += move.getDistance();
                    break;

                case "west":
                    nextX -= move.getDistance();
                    break;

                case "north":
                    nextY += move.getDistance();
                    break;

                case "south":
                    nextY -= move.getDistance();
                    break;
            }

            path.lineTo(gridToPixelX(nextX), gridToPixelY(nextY));

            currentX = nextX;
            currentY = nextY;
        }

        canvas.drawPath(path, pathPaint);

        /*
         * Draw arrowheads separately to ensure they are solid
         * and not affected by DashPathEffect of the line.
         */
        currentX = START_X;
        currentY = START_Y;
        for (DirectionMove move : moves)
        {
            int nextX = currentX;
            int nextY = currentY;

            switch (move.getDirection())
            {
                case "east":
                    nextX += move.getDistance();
                    break;

                case "west":
                    nextX -= move.getDistance();
                    break;

                case "north":
                    nextY += move.getDistance();
                    break;

                case "south":
                    nextY -= move.getDistance();
                    break;
            }

            drawPathArrow(canvas, currentX, currentY, nextX, nextY);
            currentX = nextX;
            currentY = nextY;
        }
    }

    private static void drawPathArrow(Canvas canvas, int startX, int startY, int endX, int endY)
    {
        float x1 = gridToPixelX(startX);
        float y1 = gridToPixelY(startY);
        float x2 = gridToPixelX(endX);
        float y2 = gridToPixelY(endY);
        float dx = x2 - x1;
        float dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        float size = 14;

        float xA = x2 - size * (float) Math.cos(angle - Math.PI / 6);
        float yA = y2 - size * (float) Math.sin(angle - Math.PI / 6);
        float xB = x2 - size * (float) Math.cos(angle + Math.PI / 6);
        float yB = y2 - size * (float) Math.sin(angle + Math.PI / 6);

        Paint arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrowPaint.setColor(Color.rgb(220, 30, 40));
        arrowPaint.setStyle(Paint.Style.FILL);
        Path arrow = new Path();
        arrow.moveTo(x2, y2);
        arrow.lineTo(xA, yA);
        arrow.lineTo(xB, yB);
        arrow.close();
        canvas.drawPath(arrow, arrowPaint);
    }

    private static boolean isVehicle(String subject)
    {
        return subject.equalsIgnoreCase("bus") || subject.equalsIgnoreCase("car");
    }

    private static void drawObject(Context context, Canvas canvas, DirectionPoint point, String startImage)
    {
        if (isVehicle(startImage))
        {
            drawCityLabel(canvas, point);
            return;
        }

        int resourceId = context.getResources().getIdentifier(point.getImageName(),"drawable", context.getPackageName());

        if (resourceId == 0)
        {
            drawFallbackObject(canvas, point);
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        if (bitmap == null)
        {
            drawFallbackObject(canvas, point);
            return;
        }

        float maxWidth = 110;
        float maxHeight = 110;
        float scale = Math.min(maxWidth / bitmap.getWidth(), maxHeight / bitmap.getHeight());
        float width = bitmap.getWidth() * scale;
        float height = bitmap.getHeight() * scale;
        float centerX = gridToPixelX(point.getX());
        float centerY = gridToPixelY(point.getY());

        /*
         * Place the object icon relative to the dot based on the last movement.
         */
        float left, top;
        List<DirectionMove> moves = point.getMoves();
        String lastDirection = moves != null && !moves.isEmpty() ? moves.get(moves.size() - 1).getDirection() : "east";

        int offset = 10; // Reduced from 20
        switch (lastDirection)
        {
            case "north": // Came from bottom
                left = centerX - width / 2;
                top = centerY - offset - height;
                break;

            case "south": // Came from top
                left = centerX - width / 2;
                top = centerY + offset;
                break;

            case "west": // Came from right
                left = centerX - offset - width;
                top = centerY - height / 2;
                break;

            case "east": // Came from left

            default:
                left = centerX + offset;
                top = centerY - height / 2;
                break;
        }

        RectF destination = new RectF(left, top, left + width, top + height);
        canvas.drawBitmap(bitmap, null, destination, null);
    }

    private static void drawCityLabel(Canvas canvas, DirectionPoint point)
    {
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40); // Larger and clearer
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setFakeBoldText(true);

        String cityName = point.getName();
        if (cityName != null && !cityName.isEmpty())
        {
            cityName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1);
        }
        else
        {
            cityName = "City";
        }

        float centerX = gridToPixelX(point.getX());
        float centerY = gridToPixelY(point.getY());

        List<DirectionMove> moves = point.getMoves();
        String lastDirection = moves != null && !moves.isEmpty() ? moves.get(moves.size() - 1).getDirection() : "east";
        float textHeight = textPaint.getTextSize();

        int offset = 30; // Increased offset to avoid overlapping the dot
        switch (lastDirection)
        {
            case "north": // Came from bottom, put label BELOW
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(cityName, centerX, centerY + offset + textHeight / 2, textPaint);
                break;

            case "south": // Came from top, put label ABOVE
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(cityName, centerX, centerY - offset, textPaint);
                break;

            case "west": // Came from right, put label LEFT
                textPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(cityName, centerX - offset, centerY + textHeight / 3, textPaint);
                break;

            case "east": // Came from left, put label RIGHT
            default:
                textPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(cityName, centerX + offset, centerY + textHeight / 3, textPaint);
                break;
        }
    }

    private static void drawSubject(Context context, Canvas canvas, List<DirectionPoint> points, String startImage)
    {
        int resourceId = context.getResources().getIdentifier(startImage, "drawable", context.getPackageName());

        if (resourceId == 0)
        {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(gridToPixelX(START_X), gridToPixelY(START_Y), 15, paint);
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        if (bitmap == null)
        {
            return;
        }

        float maxWidth = 100;
        float maxHeight = 100;

        float scale = Math.min(maxWidth / bitmap.getWidth(), maxHeight / bitmap.getHeight());
        float width = bitmap.getWidth() * scale;
        float height = bitmap.getHeight() * scale;
        float centerX = gridToPixelX(START_X);
        float centerY = gridToPixelY(START_Y);

        /*
         * Place the icon next to the dot based on the first movement.
         */
        String firstDirection = "north"; // Default
        for (DirectionPoint point : points)
        {
            List<DirectionMove> moves = point.getMoves();
            if (moves != null && !moves.isEmpty())
            {
                firstDirection = moves.get(0).getDirection();
                break;
            }
        }

        float left, top;
        int offset = 15; // Distance between the dot and the subject
        switch (firstDirection)
        {
            case "north": // First move is UP, put subject BELOW
                left = centerX - width / 2;
                top = centerY + offset;
                break;

            case "south": // First move is DOWN, put subject ABOVE
                left = centerX - width / 2;
                top = centerY - offset - height;
                break;

            case "east": // First move is RIGHT, put subject LEFT
                left = centerX - offset - width;
                top = centerY - height / 2;
                break;

            case "west": // First move is LEFT, put subject RIGHT
            default:
                left = centerX + offset;
                top = centerY - height / 2;
                break;
        }

        RectF destination = new RectF(left, top, left + width, top + height);
        canvas.drawBitmap(bitmap, null, destination, null);
    }

    private static void drawFallbackObject(Canvas canvas,DirectionPoint point)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(80, 80, 80));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(gridToPixelX(point.getX()), gridToPixelY(point.getY()), 20, paint);
    }

    private static void drawScaleIndicator(Canvas canvas, String scaleLabel)
    {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        float x = GRID_LEFT + GRID_SIZE - CELL_SIZE;
        float y = GRID_TOP - 15;
        canvas.drawLine(x, y, x + CELL_SIZE, y, paint);
        canvas.drawLine(x, y - 5, x, y + 5, paint);
        canvas.drawLine(x + CELL_SIZE, y - 5, x + CELL_SIZE, y + 5, paint);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(scaleLabel, x + CELL_SIZE / 2, y - 8, paint);

        /*
         * Vertical 1 cm indicator.
         */
        float verticalX = GRID_LEFT + GRID_SIZE + 15;
        float verticalTop = GRID_TOP;
        float verticalBottom = GRID_TOP + CELL_SIZE;

        canvas.drawLine(verticalX, verticalTop, verticalX, verticalBottom, paint);
        canvas.drawLine(verticalX - 5, verticalTop, verticalX + 5, verticalTop, paint);
        canvas.drawLine(verticalX - 5, verticalBottom, verticalX + 5, verticalBottom, paint);
        canvas.save();

        canvas.rotate(-90, verticalX + 8,(verticalTop + verticalBottom) / 2);
        canvas.drawText(scaleLabel,verticalX + 8, (verticalTop + verticalBottom) / 2 + 18, paint);
        canvas.restore();
    }

    private static float gridToPixelX(int x)
    {
        /*
         * Grid has 11 lines (0 to 10).
         * Center line is index 5.
         */
        return GRID_LEFT + (x + 5) * CELL_SIZE;
    }

    private static float gridToPixelY(int y)
    {
        /*
         * Grid has 11 lines (0 to 10).
         * Center line is index 5.
         * Canvas Y increases downwards, so we subtract (y+5).
         */
        return GRID_TOP + (5 - y) * CELL_SIZE;
    }
}
