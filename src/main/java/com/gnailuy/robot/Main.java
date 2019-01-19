package com.gnailuy.robot;

import com.gnailuy.robot.display.Display;
import com.gnailuy.robot.display.TerminalDisplay;
import com.gnailuy.robot.exception.IllegalInputException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

/**
 * Robot Game
 */
public class Main {
    private static boolean newRound(Display display, int numGuard) throws IOException {
        int x = display.getX();
        int y = display.getY();

        // Draw init game board
        GameBoard board = new GameBoard(x, y);
        if (numGuard >= x*y) {
            numGuard = GameBoard.DEFAULT_GUARDS;
        }
        board.initBoard(numGuard);
        display.init(board);

        GameStatus status = GameStatus.ONGOING;

        // Update game board on user input
        while (GameStatus.ONGOING == status) {
            // Get input
            try {
                Direction direction = display.getInput();
                if (null == direction) {
                    continue;
                }

                // Move
                status = board.move(direction);

                // Redraw board
                display.drawBoard(board);
            } catch (IllegalInputException e) {
                return false;
            }
        }
        display.putResult(status);

        return true;
    }

    public static void main(String[] args) {
        Options options = new Options();

        Option numGuardOpt = new Option("n", "num-guard", true, "Number of Guards");
        options.addOption(numGuardOpt);

        Option helpOpt = new Option("h", "help", false, "Display Help");
        options.addOption(helpOpt);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        if (cmd.hasOption("help")) {
            formatter.printHelp("Robot Game", options);
            System.exit(0);
        }

        int numGuard = GameBoard.DEFAULT_GUARDS; // Default value
        if (cmd.hasOption("num-guard")) {
            try {
                numGuard = Integer.parseInt(cmd.getOptionValue("num-guard"));
            } catch (NumberFormatException e) {
                System.err.println("Wrong number format: n/num-guard should be an integer");
            }
        }
        if (numGuard <= 0) {
            numGuard = GameBoard.DEFAULT_GUARDS;
        }

        Display display = null;
        try {
            display = new TerminalDisplay();

            while (true) {
                boolean nextRound = newRound(display, numGuard);
                if (!nextRound) {
                    break;
                }

                if (display.getExit()) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != display) {
                try {
                    display.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

