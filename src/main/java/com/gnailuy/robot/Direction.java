package com.gnailuy.robot;

public enum Direction {
    STILL       ( 0,  0),
    UP          ( 0, -1),
    DOWN        ( 0,  1),
    LEFT        (-1,  0),
    RIGHT       ( 1,  0),
    UPLEFT      (-1, -1),
    UPRIGHT     ( 1, -1),
    DOWNLEFT    (-1,  1),
    DOWNRIGHT   ( 1,  1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Direction charToDirection(Character c1, Character c2) {
        if (null == c1 && null == c2) {
            return null;
        } else if (null == c1 || null == c2) {
            char c = Character.toLowerCase(null != c1 ? c1 : c2);
            switch (c) {
                case 'w':
                    return UP;
                case 's':
                    return DOWN;
                case 'a':
                    return LEFT;
                case 'd':
                    return RIGHT;
                case 'x':
                    return STILL;
                default:
                    return null;
            }
        } else if ('x' == Character.toLowerCase(c1)
                || 'x' == Character.toLowerCase(c2)) {
            return STILL;
        } else { // null != c1 && null != c2
            c1 = Character.toLowerCase(c1);
            c2 = Character.toLowerCase(c2);
            if (c1 > c2) {
                char tmp = c1;
                c1 = c2;
                c2 = tmp;
            }

            if ('w' == c1 && 'w' == c2) {
                return UP;
            } else if ('s' == c1 && 's' == c2) {
                return DOWN;
            } else if ('a' == c1 && 'a' == c2) {
                return LEFT;
            } else if ('d' == c1 && 'd' == c2) {
                return RIGHT;
            } else if ('a' == c1 && 'w' == c2) {
                return UPLEFT;
            } else if ('d' == c1 && 'w' == c2) {
                return UPRIGHT;
            } else if ('a' == c1 && 's' == c2) {
                return DOWNLEFT;
            } else if ('d' == c1 && 's' == c2) {
                return DOWNRIGHT;
            } else {
                return null;
            }
        }
    }
}

