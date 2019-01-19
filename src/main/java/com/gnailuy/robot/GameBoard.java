package com.gnailuy.robot;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Robot Game Board
 *
 */
public class GameBoard {
    public static final int DEFAULT_GUARDS = 7;

    private int x;
    private int y;
    private Robot[][] board;
    private Robot[][] anotherBoard;
    private Random randGenerator;

    private Player player;
    private List<Guard> guards;

    public GameBoard(int x, int y) {
        this.x = x;
        this.y = y;
        board = new Robot[x][y];
        anotherBoard = new Robot[x][y];
        randGenerator = new Random(System.currentTimeMillis());
        guards = new LinkedList<Guard>();
    }

    public GameBoard() {
        this(20, 18);
    }

    public Coordinate randomPosition() {
        return new Coordinate(randGenerator.nextInt(x), randGenerator.nextInt(y));
    }

    public void initBoard(int numGuard) {
        Coordinate rp = randomPosition();
        player = new Player("Player", rp);
        board[rp.getX()][rp.getY()] = player;

        for (int i = 0; i < numGuard; i++) {
            do {
                rp = randomPosition();
            } while (null != board[rp.getX()][rp.getY()]);

            Guard newGuard = new Guard("Guard " + i, rp);
            newGuard.turnTo(player);
            guards.add(newGuard);
            board[rp.getX()][rp.getY()] = newGuard;
        }
    }

    private void swapBoard() {
        Robot[][] tmp = board;
        board = anotherBoard;
        anotherBoard = tmp;
    }

    public GameStatus move(Direction direction) {
        Coordinate oldPos = new Coordinate(player.getPosition());
        player.move(direction, x, y);
        Coordinate newPos = player.getPosition();

        board[oldPos.getX()][oldPos.getY()] = null;
        if (null != board[newPos.getX()][newPos.getY()]) {
            anotherBoard[oldPos.getX()][oldPos.getY()] = player;
            player.moveTo(oldPos);
            Robot robot = board[newPos.getX()][newPos.getY()];
            if (robot instanceof Guard && robot.isAlive()) {
                return GameStatus.GAMEOVER;
            }
        } else {
            anotherBoard[newPos.getX()][newPos.getY()] = player;
        }

        boolean gameContinue = true;
        for (Guard guard: guards) {
            if (!guard.isAlive()) {
                oldPos = new Coordinate(guard.getPosition());
                board[oldPos.getX()][oldPos.getY()] = null;
                anotherBoard[oldPos.getX()][oldPos.getY()] = guard;
            }
        }
        for (Guard guard: guards) {
            oldPos = new Coordinate(guard.getPosition());
            board[oldPos.getX()][oldPos.getY()] = null;

            if (!guard.isAlive()) {
                continue;
            } else {
                newPos = guard.moveToward(player);
            }

            if (null == anotherBoard[newPos.getX()][newPos.getY()]) {
                anotherBoard[newPos.getX()][newPos.getY()] = guard;
            } else {
                Robot other = anotherBoard[newPos.getX()][newPos.getY()];
                other.kill();
                guard.kill();
                if (other instanceof Player) {
                    gameContinue = false;
                }
            }
        }
        swapBoard();

        if (!gameContinue) {
            return GameStatus.GAMEOVER;
        }

        for (Guard guard: guards) {
            if (guard.isAlive()) {
                return GameStatus.ONGOING;
            }
        }
        return GameStatus.USERWIN;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Character getChar(int x, int y) {
        if (x < 0 || x >= this.x || y < 0 || y >= this.y) {
            return null;
        }

        if (null == board[x][y]) {
            return ' ';
        } else if (board[x][y] instanceof Guard || board[x][y] instanceof Player) {
            return board[x][y].getChar();
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(' ');
        for (int i = 0; i < x; i++) {
            sb.append('-');
        }
        sb.append(" \n");
        for (int j = 0; j < y; j++) {
            sb.append('|');
            for (int i = 0; i < x; i++) {
                sb.append(getChar(i, j));
            }
            sb.append("|\n");
        }
        sb.append(' ');
        for (int i = 0; i < x; i++) {
            sb.append('-');
        }
        sb.append(" \n");

        return sb.toString();
    }

    public static void main( String[] args ) {
        GameBoard board = new GameBoard(40, 12);
        board.initBoard(7);
        System.out.println(board);

        Scanner scanner = new Scanner(System.in);
        GameStatus status = GameStatus.ONGOING;
        while (GameStatus.ONGOING == status) {
            String input = scanner.nextLine();
            Direction direction = Direction.valueOf(input);
            status = board.move(direction);
            System.out.println(board);
        }
    }
}

