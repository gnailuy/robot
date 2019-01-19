package com.gnailuy.robot;

public class Player extends Robot {
    public Player() {
        super();
    }

    public Player(String name) {
        super();
        super.setName(name);
    }

    public Player(Coordinate position) {
        super();
        moveTo(position);
    }

    public Player(String name, Coordinate position) {
        super(name);
        this.moveTo(position);
    }

    public Coordinate move(Direction direction, int maxX, int maxY) {
        Coordinate current = getPosition();

        int x = current.getX() + direction.getX();
        if (x < 0) x = 0;
        if (x >= maxX) x = maxX-1;

        int y = current.getY() + direction.getY();
        if (y < 0) y = 0;
        if (y >= maxY) y = maxY-1;

        moveTo(x, y);
        return getPosition();
    }

    @Override
    public Character getChar() {
        if (isAlive()) {
            return 'P';
        } else {
            return 'J';
        }
    }

    @Override
    public String toString() {
        return "[Player]" + super.toString();
    }

    public static void main(String[] args) {
        Player player = new Player();
        System.out.println(player);

        player.kill();
        System.out.println(player);
    }
}

