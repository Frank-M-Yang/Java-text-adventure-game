package game.logic;

import java.util.List;

/**
 * Core logic for the 4x4 Sudoku boss puzzle.
 * Pure logic only. No input/output.
 */
public class SudokuLogic {

    private static final int SIZE = 4;

    // Initial puzzle (0 = empty)
    private final int[][] puzzle = {
            {1, 0, 3, 0},
            {0, 3, 0, 1},
            {0, 1, 0, 3},
            {3, 0, 1, 0}
    };

    // Unique solution
    private final int[][] solution = {
            {1, 2, 3, 4},
            {4, 3, 2, 1},
            {2, 1, 4, 3},
            {3, 4, 1, 2}
    };

    /**
     * Verifies numbers entered sequentially:
     * top-to-bottom, left-to-right, only for empty cells.
     */
    public boolean verifySequentialInput(List<Integer> input) {
        int index = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (puzzle[i][j] == 0) {
                    if (index >= input.size()) {
                        return false; // not enough inputs
                    }
                    if (!input.get(index).equals(solution[i][j])) {
                        return false; // wrong number
                    }
                    index++;
                }
            }
        }
        // must use exactly all required inputs
        return index == input.size();
    }
}

