package com.myAgeEducation.cbseClass7.maths.mappingskills;

import java.util.ArrayList;
import java.util.List;

public class MetroLine {
    private final String id;
    private final String name;
    private final int color;
    private final List<MetroStation> stations = new ArrayList<>();

    public MetroLine(String id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public void addStation(MetroStation station) {
        stations.add(station);
        station.addLine(this.id);
    }

    public List<MetroStation> getStations() {
        return stations;
    }
}
