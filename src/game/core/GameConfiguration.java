package game.core;

/**
 * Central configuration for all game parameters.
 * All magic numbers and game settings are defined here for easy modification.
 */

public class GameConfiguration {
    public static final int TOTAL_NUMBER_OF_CHIPS = 4;
    public static final String[] DIRECTION = {"East", "West", "North", "South"};
    public static final String DANGEROUS_DIRECTION = "South";

    // SudokuChallenge
    public static final int SUDOKU_SIZE = 4;
    public static final int SUDOKU_MAX_ATTEMPTS = 3;

    // Math 24
    public static final int MATH24_TARGET = 24;
    public static final int[] MATH24_NUMBERS = {3, 8, 8, 9};

}
