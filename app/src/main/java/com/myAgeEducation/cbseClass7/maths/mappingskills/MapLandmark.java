package com.myAgeEducation.cbseClass7.maths.mappingskills;

public class MapLandmark {
    public enum Type {
        HOUSE, FACILITY, STOP
    }

    private final String name;
    private final String label;
    private final Type type;
    private int x;
    private int y;

    public MapLandmark(String name, String label, Type type) {
        this.name = name;
        this.label = label;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public Type getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
