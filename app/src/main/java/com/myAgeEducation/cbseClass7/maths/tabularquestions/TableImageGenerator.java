package com.myAgeEducation.cbseClass7.maths.tabularquestions;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class TableImageGenerator
{
    public static Bitmap generate(String imageCode)
    {
        String[] parts = imageCode.split("_");

        // TABLE_SCENARIO_L1_L2_L3_L4_V1_V2_V3_V4_[TYPE]_[INDEX]
        if (!parts[0].equals("TABLE"))
        {
            throw new IllegalArgumentException("Invalid table image code: " + imageCode);
        }

        String scenarioCode = parts[1];

        String[] displayLabels =
                {
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5]
                };

        int[] values =
                {
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7]),
                        Integer.parseInt(parts[8]),
                        Integer.parseInt(parts[9])
                };

        int missingIndex = -1;
        if (parts.length > 11 && parts[10].equals("MISSING")) {
            missingIndex = Integer.parseInt(parts[11]);
        }

        TableDisplayData displayData = getDisplayData(scenarioCode, displayLabels);
        return generate(displayData, values, missingIndex);
    }

    private static Bitmap generate(TableDisplayData data, int[] values, int missingIndex)
    {
        int width = 1000;
        int height = 650;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // TABLE DIMENSIONS

        float left = 100;
        float top = 70;
        float right = 900;
        float rowHeight = 95;
        int rowCount = data.labels.length + 1; // + header
        float bottom = top + rowCount * rowHeight;
        float firstColumnWidth = 400;
        float columnDivider = left + firstColumnWidth;

        // HEADER BACKGROUND

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(230, 230, 230));
        canvas.drawRect(left, top, right, top + rowHeight, paint);

        // TABLE BORDER AND GRID

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);

        canvas.drawRect(new RectF(left, top, right, bottom),paint);

        // Vertical divider

        canvas.drawLine(columnDivider, top, columnDivider, bottom, paint);

        // Horizontal lines

        for (int i = 1; i < rowCount; i++)
        {
            float y = top + i * rowHeight;
            canvas.drawLine(left, y, right, y, paint);
        }

        // HEADER TEXT

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(38);
        paint.setFakeBoldText(true);
        paint.setTextAlign(Paint.Align.CENTER);

        float headerY = top + rowHeight / 2 - (paint.ascent() + paint.descent()) / 2;
        canvas.drawText(data.firstColumnTitle, left + firstColumnWidth / 2, headerY, paint);
        canvas.drawText(data.secondColumnTitle, columnDivider + (right - columnDivider) / 2, headerY, paint);

        // DATA ROWS

        paint.setFakeBoldText(false);
        paint.setTextSize(36);

        for (int i = 0; i < data.labels.length; i++)
        {
            float rowTop = top + (i + 1) * rowHeight;

            float centerY = rowTop + rowHeight / 2 - (paint.ascent() + paint.descent()) / 2;
            // First column

            canvas.drawText(data.labels[i],left + firstColumnWidth / 2, centerY, paint);

            // Second column

            String valueText;
            if (i == missingIndex) {
                valueText = "?";
            } else {
                valueText = formatNumber(values[i]);

                if (data.unit != null && !data.unit.trim().isEmpty())
                {
                    valueText += " " + data.unit;
                }
            }

            canvas.drawText(valueText, columnDivider + (right - columnDivider) / 2, centerY, paint);
        }

        return bitmap;
    }

    private static String formatNumber(int value)
    {
        return String.format(java.util.Locale.US, "%,d", value);
    }

    private static TableDisplayData getDisplayData(String scenarioCode, String[] displayLabels)
    {
        switch (scenarioCode)
        {
            case "ROADNETWORK":
                return new TableDisplayData("City", "Road Network", displayLabels, "km");

            case "AIRPORTS":
                return new TableDisplayData("Country", "Number of Airports", displayLabels, "");

            case "LIBRARIES":
                return new TableDisplayData("City", "Number of Libraries", displayLabels, "");

            case "MOUNTAINPEAKS":
                return new TableDisplayData("Mountain Peak", "Height", displayLabels, "m");

            case "PLANETS":
                return new TableDisplayData("Planet", "Diameter", displayLabels, "km");

            case "CROPPRODUCTION":
                return new TableDisplayData("State", "Wheat Produced", displayLabels, "tonnes");

            case "FORESTAREA":
                return new TableDisplayData("State", "Forest Area", displayLabels, "sq km");

            case "TOWNPOPULATION":
                return new TableDisplayData("Town", "Population", displayLabels, "");

            case "RIVERLENGTHS":
                return new TableDisplayData("River", "Length", displayLabels, "km");

            case "STADIUMCAPACITY":
                return new TableDisplayData("Stadium", "Capacity", displayLabels, "seats");

            case "ANNUALRAINFALL":
                return new TableDisplayData("City", "Annual Rainfall", displayLabels, "mm");

            case "PLANETDISTANCE":
                return new TableDisplayData("Planet", "Distance", displayLabels, "million km");

            case "ANIMALPOPULATION":
                return new TableDisplayData("Animal", "Population", displayLabels, "");

            case "LIBRARYBOOKS":
                return new TableDisplayData("School", "Number of Books", displayLabels, "books");

            case "CRICKETRUNS":
                return new TableDisplayData("Batsman", "Runs Scored", displayLabels, "runs");

            case "ELECTRICITY":
                return new TableDisplayData("Family", "Units Consumed", displayLabels, "units");

            case "ANIMALSPEED":
                return new TableDisplayData("Animal", "Top Speed", displayLabels, "km/hr");

            case "STEPCOUNT":
                return new TableDisplayData("Friend", "Total Steps", displayLabels, "steps");

            case "GADGETCOST":
                return new TableDisplayData("Gadget", "Price", displayLabels, "rupees");

            case "STATUEHEIGHT":
                return new TableDisplayData("Statue", "Height", displayLabels, "meters");

            case "WATERUSAGE":
                return new TableDisplayData("Household", "Water Used", displayLabels, "litres");

            case "LAKEAREA":
                return new TableDisplayData("Lake", "Surface Area", displayLabels, "sq km");

            case "ANIMALLIFESPAN":
                return new TableDisplayData("Animal", "Average Lifespan", displayLabels, "years");

            case "OCEANDEPTH":
                return new TableDisplayData("Ocean", "Maximum Depth", displayLabels, "meters");

            case "BUILDINGHEIGHT":
                return new TableDisplayData("Building", "Height", displayLabels, "meters");

            case "MONTHLYSAVINGS":
                return new TableDisplayData("Friend", "Amount Saved", displayLabels, "rupees");

            case "STORAGE":
                return new TableDisplayData("Pen Drive", "Storage", displayLabels, "GB");

            case "BOOKPAGES":
                return new TableDisplayData("Book Title", "Number of Pages", displayLabels, "pages");

            case "MOVIETIME":
                return new TableDisplayData("Movie", "Duration", displayLabels, "minutes");

            case "CITYDISTANCE":
                return new TableDisplayData("City", "Distance", displayLabels, "km");

            case "FRUITPRODUCTION":
                return new TableDisplayData("Tree", "Apples Harvested", displayLabels, "kg");

            case "ATTENDANCE":
                return new TableDisplayData("Class", "Students Present", displayLabels, "students");

            case "CITYTEMP":
                return new TableDisplayData("City", "Temperature", displayLabels, "°C");

            case "VEHICLESPEED":
                return new TableDisplayData("Vehicle", "Top Speed", displayLabels, "km/hr");

            case "ANIMALWEIGHT":
                return new TableDisplayData("Animal", "Weight", displayLabels, "kg");

            case "FRUITSALES":
                return new TableDisplayData("Fruit", "Total Sales", displayLabels, "rupees");

            case "SOLARENERGY":
                return new TableDisplayData("Solar Panel", "Energy Produced", displayLabels, "units");

            case "SPORTSPOINTS":
                return new TableDisplayData("House", "Total Points", displayLabels, "points");

            case "PARKVISITORS":
                return new TableDisplayData("Park", "Visitors", displayLabels, "people");

            case "APPDOWNLOADS":
                return new TableDisplayData("Game", "Downloads", displayLabels, "lakhs");

            case "EXAMSCORES":
                return new TableDisplayData("Student", "Total Marks", displayLabels, "marks");

            case "SYMMETRY_ORDER":
                return new TableDisplayData("Shape", "Order of Rotational Symmetry", displayLabels, "");

            default:
                throw new IllegalArgumentException("Unknown table scenario: " + scenarioCode);
        }
    }

    private static class TableDisplayData
    {
        final String firstColumnTitle;
        final String secondColumnTitle;
        final String[] labels;
        final String unit;

        TableDisplayData(String firstColumnTitle, String secondColumnTitle, String[] labels, String unit)
        {
            this.firstColumnTitle = firstColumnTitle;
            this.secondColumnTitle = secondColumnTitle;
            this.labels = labels;
            this.unit = unit;
        }
    }
}
