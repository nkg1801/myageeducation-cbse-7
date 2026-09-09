package com.myAgeEducation.cbseClass7.maths.mappingskills;


import java.util.List;

public class DirectionPoint
{
    private final String name;
    private final String imageName;
    private final int x;
    private final int y;
    private final List<DirectionMove> moves;

    public DirectionPoint(String name, String imageName, int x, int y, List<DirectionMove> moves)
    {
        this.name = name;
        this.imageName = imageName;
        this.x = x;
        this.y = y;
        this.moves = moves;
    }

    public String getName()
    {
        return name;
    }

    public String getImageName()
    {
        return imageName;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public List<DirectionMove> getMoves()
    {
        return moves;
    }
}
