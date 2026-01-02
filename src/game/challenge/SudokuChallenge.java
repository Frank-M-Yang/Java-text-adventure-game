package game.challenge;

import java.util.*;

/**
 * Final BOSS Challenge.
 * The player must solve a 4x4 Sudoku puzzle.
 * The player has at most THREE attempts.
 * Any mistake immediately fails the current attempt.
 * After three failures, the challenge returns a critical failure.
 */
public class SudokuChallenge implements Challenge<int[][]> {

    private static final int SIZE = 4;
    private static final int MAX_ATTEMPTS = 3;

    private final int[][] solution = {
            {1, 2, 3, 4},
            {4, 3, 2, 1},
            {2, 1, 4, 3},
            {3, 4, 1, 2}
    };

    private final int[][] puzzle = {
            {1, 0, 3, 0},
            {0, 3, 0, 1},
            {0, 1, 0, 3},
            {3, 0, 1, 0}
    };

    private final List<int[]> emptyCells = new ArrayList<>();

    public SudokuChallenge() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (puzzle[i][j] == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
    }

    @Override
    public ChallengeResult<int[][]> execute(Scanner scanner) {

        printIntro();

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            System.out.println(
                    "\nEvil Doctor: \"Attempt " + attempt + " of " + MAX_ATTEMPTS + ". Do not disappoint me.\""
            );

            boolean success = playSingleAttempt(scanner);

            if (success) {
                return new ChallengeResult<>(
                        true,
                        solution,
                        "Evil Doctor: \"No... This was supposed to be impossible...\""
                );
            }

            if (attempt < MAX_ATTEMPTS) {
                System.out.println(
                        "Evil Doctor: \"Pathetic. Try again, if you dare.\""
                );
            }
        }

        // After three failed attempts → critical failure
        return new ChallengeResult<>(
                false,
                puzzle,
                "Evil Doctor: \"Enough. Your journey ends here.\""
        );
    }

    /**
     * Plays a single Sudoku attempt.
     * Any invalid input or rule violation immediately fails the attempt.
     */
    private boolean playSingleAttempt(Scanner scanner) {
        int[][] board = deepCopy(puzzle);
        int step = 0;

        while (step < emptyCells.size()) {
            displayBoard(board);

            System.out.print("Enter the number for the next empty cell (1-4): ");
            String input = scanner.nextLine().trim();

            try {
                int num = Integer.parseInt(input);

                if (num < 1 || num > 4) {
                    System.out.println(
                            "Evil Doctor: \"Wrong range. The mechanism resets.\""
                    );
                    return false;
                }

                int row = emptyCells.get(step)[0];
                int col = emptyCells.get(step)[1];

                if (!isValidMove(board, row, col, num)) {
                    System.out.println(
                            "Evil Doctor: \"Incorrect. The system rejects your input.\""
                    );
                    return false;
                }

                board[row][col] = num;
                step++;

            } catch (NumberFormatException e) {
                System.out.println(
                        "Evil Doctor: \"Numbers only. Failure detected.\""
                );
                return false;
            }
        }

        return Arrays.deepEquals(board, solution);
    }

    private void printIntro() {
        System.out.println("\n=====================================");
        System.out.println("Evil Doctor: \"You should not be here.\"");
        System.out.println("Evil Doctor: \"Yet... you survived everything.\"");
        System.out.println();
        System.out.println("Evil Doctor: \"Very well. This is our final duel.\"");
        System.out.println("Evil Doctor: \"Fail three times... and this facility will be your grave.\"");
        System.out.println("=====================================\n");

        System.out.println("Final Trial: Sequential Sudoku");
        System.out.println("- Fill the empty cells in order");
        System.out.println("- Numbers range from 1 to 4");
        System.out.println("- One mistake ends the attempt\n");
    }

    private void displayBoard(int[][] board) {
        System.out.println("╔═══════╦═══════╗");

        for (int i = 0; i < SIZE; i++) {
            System.out.print("║ ");

            for (int j = 0; j < SIZE; j++) {
                String cell = board[i][j] == 0 ? "?" : String.valueOf(board[i][j]);
                System.out.print(cell + " ");

                if (j == 1) System.out.print("║ ");
            }

            System.out.println("║");

            if (i == 1) {
                System.out.println("╠═══════╬═══════╣");
            }
        }

        System.out.println("╚═══════╩═══════╝\n");
    }

    private boolean isValidMove(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int boxRow = (row / 2) * 2;
        int boxCol = (col / 2) * 2;

        for (int i = boxRow; i < boxRow + 2; i++) {
            for (int j = boxCol; j < boxCol + 2; j++) {
                if (board[i][j] == num) return false;
            }
        }
        return true;
    }

    private int[][] deepCopy(int[][] src) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            copy[i] = src[i].clone();
        }
        return copy;
    }

    @Override
    public String getDescription() {
        return "Final Boss: Evil Doctor";
    }

    @Override
    public boolean isCritical() {
        return true;
    }
}
