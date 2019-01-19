package com.gnailuy.robot;

public class Guard extends Robot {
    private Direction direction = Direction.RIGHT;

    public Guard() {
        super();
    }

    public Guard(String name) {
        super();
        super.setName(name);
    }

    public Guard(Coordinate position) {
        super();
        moveTo(position);
    }

    public Guard(String name, Coordinate position) {
        super(name);
        this.moveTo(position);
    }

    public void turnTo(Robot robot) {
        Coordinate current = getPosition();
        Coordinate target = robot.getPosition();

        int deltaX = Math.abs(current.getX() - target.getX());
        int deltaY = Math.abs(current.getY() - target.getY());

        if (deltaX >= deltaY) {
            if (current.getX() < target.getX()) {
                direction = Direction.RIGHT;
            } else if (current.getX() > target.getX()) {
                direction = Direction.LEFT;
            }
        } else {
            if (current.getY() < target.getY()) {
                direction = Direction.DOWN;
            } else if (current.getY() > target.getY()) {
                direction = Direction.UP;
            }
        }
    }

    public Coordinate moveToward(Robot robot) {
        Coordinate current = getPosition();

        switch (direction) {
            case UP:
                current.setY(current.getY() - 1);
                break;
            case DOWN:
                current.setY(current.getY() + 1);
                break;
            case LEFT:
                current.setX(current.getX() - 1);
                break;
            case RIGHT:
                current.setX(current.getX() + 1);
                break;
        }

        turnTo(robot);

        return current;
    }

    @Override
    public Character getChar() {
        if (!isAlive()) {
            return 'J';
        }
        switch (direction) {
            case UP:
                return '^';
            case DOWN:
                return 'v';
            case LEFT:
                return '<';
            case RIGHT:
                return '>';
            default:
                return 'G';
        }
    }

    @Override
    public String toString() {
        return "[Guard]" + super.toString();
    }

    public static void main(String[] args) {
        Guard guard = new Guard();
        System.out.println(guard);

        Guard junk = new Guard("Junk");
        junk.kill();
        System.out.println(junk);
    }
}

