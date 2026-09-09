package com.myAgeEducation.cbseClass7.maths.mappingskills;

public class ZooAnimal {
    private final String name;
    private final String imageName;
    private int x;
    private int y;

    public ZooAnimal(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return imageName;
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
