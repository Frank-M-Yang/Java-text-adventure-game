package game.challenge;

import java.util.*;

public class SudokuChallenge implements Challenge<int[][]> {
    private final int[][] solution = {
            {1, 2, 3, 4},
            {4, 3, 2, 1},
            {2, 1, 4, 3},
            {3, 4, 1, 2}
    };

    @Override
    public ChallengeResult<int[][]> execute(Scanner scanner) {
        System.out.println("\nSudoku challenge.Challenge - Fill the 4x4 grid:");
        displayBoard();
        System.out.println("\nEnter moves as: row col number (0-3, 1-4)");
        System.out.println("Type 'done' when finished");

        int[][] userBoard = {
                {1, 0, 3, 0},
                {0, 3, 0, 1},
                {0, 1, 0, 3},
                {3, 0, 1, 0}
        };

        while (true) {
            System.out.print("Enter move or 'done': ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;

            try {
                String[] parts = input.split(" ");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                int num = Integer.parseInt(parts[2]);

                if (row >= 0 && row < 4 && col >= 0 && col < 4 && num >= 1 && num <= 4) {
                    userBoard[row][col] = num;
                    System.out.println("Placed " + num + " at [" + row + "][" + col + "]");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Use: row col number");
            }
        }

        if (Arrays.deepEquals(userBoard, solution)) {
            return new ChallengeResult<>(true, userBoard, "Doctor: \"Impossible! You solved it!\"");
        } else {
            return new ChallengeResult<>(false, userBoard, "Doctor: \"Wrong answer!\"");
        }
    }

    private void displayBoard() {
        System.out.println("  0 1 2 3");
        int[][] board = {
                {1, 0, 3, 0},
                {0, 3, 0, 1},
                {0, 1, 0, 3},
                {3, 0, 1, 0}
        };
        for (int i = 0; i < 4; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j] == 0 ? "_ " : board[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public String getDescription() { return "Sudoku Duel"; }
    @Override
    public boolean isCritical() { return true; }
}