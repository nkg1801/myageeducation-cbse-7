package com.myAgeEducation.cbseClass7.maths.mappingskills;

import java.util.ArrayList;
import java.util.List;

public class MetroStation {
    private final String name;
    private final int x;
    private final int y;
    private final List<String> lineIds = new ArrayList<>();

    public MetroStation(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addLine(String lineId) {
        if (!lineIds.contains(lineId)) {
            lineIds.add(lineId);
        }
    }

    public List<String> getLineIds() {
        return lineIds;
    }

    public boolean isInterchange() {
        return lineIds.size() > 1;
    }
}
