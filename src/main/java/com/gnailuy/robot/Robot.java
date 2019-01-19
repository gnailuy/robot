package com.gnailuy.robot;

public abstract class Robot {
    private String name;
    private Coordinate position;
    private boolean alive;

    public Robot() {
        name = "Unknown";
        position = new Coordinate();
        alive = true;
    }

    public Robot(String name) {
        this();
        this.name = name;
    }

    public Robot(Coordinate position) {
        this();
        this.position.setPosition(position);
    }

    public Robot(String name, Coordinate position) {
        this();
        this.name = name;
        this.position.setPosition(position);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position.setPosition(position);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void moveTo(int x, int y) {
        this.position.setXY(x, y);
    }

    public void moveTo(Coordinate position) {
        this.position.setPosition(position);
    }

    public void kill() {
        setAlive(false);
    }

    public void reborn() {
        setAlive(true);
    }

    public abstract Character getChar();

    public String toString() {
        if (alive) {
            return name + ": " + position;
        } else {
            return "[Junk]" + name + ": " + position;
        }
    }
}

