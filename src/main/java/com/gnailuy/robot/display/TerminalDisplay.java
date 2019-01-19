package com.gnailuy.robot.display;

import com.gnailuy.robot.GameBoard;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.gnailuy.robot.Direction;
import com.gnailuy.robot.GameStatus;
import com.gnailuy.robot.exception.IllegalInputException;

import java.io.IOException;

public class TerminalDisplay implements Display {
    private Terminal terminal;

    public TerminalDisplay() throws IOException {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        terminal = defaultTerminalFactory.createTerminal();
    }

    public TerminalSize getTerminalSize() throws IOException {
        return terminal.getTerminalSize();
    }

    public void init(GameBoard board) throws IOException {
        terminal.setCursorVisible(false);
        terminal.clearScreen();
        drawBoard(board);
    }

    public void close() throws IOException {
        terminal.setCursorVisible(true);
        terminal.close();
    }

    public int getX() throws IOException {
        return getTerminalSize().getColumns();
    }

    public int getY() throws IOException {
        return getTerminalSize().getRows();
    }

    public Direction getInput() throws IOException, IllegalInputException {
        KeyStroke firstKey = terminal.readInput();
        KeyStroke secondKey;
        if (null == firstKey || (KeyType.Escape.equals(firstKey.getKeyType())
                || KeyType.EOF.equals(firstKey.getKeyType()))) {
            throw new IllegalInputException("Exit");
        }

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalInputException("Interrupted Exception");
        }
        secondKey = terminal.pollInput();
        if (null != secondKey && (KeyType.Escape.equals(secondKey.getKeyType())
                || KeyType.EOF.equals(secondKey.getKeyType()))) {
            throw new IllegalInputException("Exit");
        }

        // Convert key to Direction
        return Direction.charToDirection(keyToCharacter(firstKey), keyToCharacter(secondKey));
    }

    public boolean getExit() throws IOException {
        // Press any key to continue, Esc to exit
        KeyStroke input = terminal.readInput();
        return input.getKeyType() == KeyType.Escape || input.getKeyType() == KeyType.EOF;
    }

    public void drawBoard(GameBoard board) throws IOException {
        terminal.clearScreen();

        TextGraphics textGraphics = terminal.newTextGraphics();
        int x = board.getX();
        int y = board.getY();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                TerminalPosition tp = new TerminalPosition(i, j);
                textGraphics.setCharacter(tp, board.getChar(i, j));
            }
        }

        terminal.flush();
    }

    public void putResult(GameStatus status) throws IOException {
        String result;
        if (GameStatus.GAMEOVER == status) {
            result = "Game Over!";
        } else {
            result = "You Win!";
        }
        TerminalSize terminalSize = terminal.getTerminalSize();
        TerminalPosition center = new TerminalPosition(terminalSize.getColumns() / 2 - result.length() / 2,
                terminalSize.getRows() / 2 - 2);
        TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.putString(center, result);

        TerminalPosition next = center.withRelative(-8, 1);
        textGraphics.putString(next, "Press any key to continue!");

        terminal.flush();
    }

    private static Character keyToCharacter(KeyStroke keyStroke) {
        if (null == keyStroke) {
            return null;
        }

        KeyType keyType = keyStroke.getKeyType();
        switch (keyType) {
            case ArrowUp:
                return 'w';
            case ArrowDown:
                return 's';
            case ArrowLeft:
                return 'a';
            case ArrowRight:
                return 'd';
            case Enter:
                return 'x';
        }

        return keyStroke.getCharacter();
    }
}

