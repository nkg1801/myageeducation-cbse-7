package com.myAgeEducation.cbseClass7.utils;

public final class ImageCodeType
{
    /* Warning: Do not use underscore (_) in the value text as "_" is used as a delimiter in the image code.
       The image code is parsed using "_" as a delimiter.
     */
    public static final String DIVISION = "DIV";
    public static final String PICTOGRAPH = "PICTOGRAPH";
    public static final String FRACTION_CHOICE = "use_fraction_choice_generator_code;";
    public static final String CLOCK = "CLOCK";
    public static final String CIRCLE_GRAPH = "CIRCLE-GRAPH";
    public static final String BARCHART = "BARCHART";
    public static final String NUMERIC_FRACTION = "NUMERIC-FRACTION";
    public static final String SHAPE_PART_FRACTION = "SHAPE-PART-FRACTION";
    public static final String DECIMAL_IMAGE = "DECIMAL-IMAGE";
    public static final String DECIMAL_GRID = "DECIMAL-GRID";
    public static final String TABLE = "TABLE";
    public static final String ANGLE = "ANGLE";
    public static final String CALENDAR = "CALENDAR";
    public static final String DISTANCE_GRID_QUIZ = "DISTANCE-GRID-QUIZ";
    public static final String ZOO_MAP = "ZOO-MAP";
    public static final String NEIGHBORHOOD_MAP = "NEIGHBORHOOD-MAP";
    public static final String METRO_MAP = "METRO-MAP";
    public static final String PATTERN_SEQUENCE = "PATTERN-SEQUENCE";
    public static final String EQUIVALENT_FRACTION = "EQ-FRAC";
    public static final String FRACTION_COMPARISON = "FRAC-COMP";
    public static final String RESOURCE_IMAGE = "RESOURCE-IMAGE"; // should use ; as delimiter since resource name can have _
    public static final String TILE_COVERING = "TILE-COVERING";
    public static final String PERIMETER_SHAPE = "PERIM-SHAPE";
    public static final String TRIANGLE = "TRIANGLE";

    private ImageCodeType()
    {
    }
}