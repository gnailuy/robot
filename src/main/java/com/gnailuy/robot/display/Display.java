package com.gnailuy.robot.display;

import com.gnailuy.robot.GameBoard;
import com.gnailuy.robot.Direction;
import com.gnailuy.robot.GameStatus;
import com.gnailuy.robot.exception.IllegalInputException;

import java.io.IOException;

public interface Display {
    void init(GameBoard board) throws IOException;
    void close() throws IOException;

    int getX() throws IOException;
    int getY() throws IOException;
    Direction getInput() throws IOException, IllegalInputException;
    boolean getExit() throws IOException;

    void drawBoard(GameBoard board) throws IOException;
    void putResult(GameStatus status) throws IOException;
}

