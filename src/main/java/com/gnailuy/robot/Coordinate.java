package com.gnailuy.robot;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate() {
        x = 0;
        y = 0;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        setX(x);
        setY(y);
    }

    public void setPosition(Coordinate position) {
        setX(position.getX());
        setY(position.getY());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Coordinate))
            return false;

        Coordinate other = (Coordinate) o;
        return other.getX() == getX() && other.getY() == getY();
    }

    @Override
    public int hashCode() {
        return (getX() + " " + getY()).hashCode();
    }
}

