import java.util.Scanner;

public class WordSearchChallenge implements Challenge<String> {
    private static final String TARGET = "cyberpunk";

    // A letter maze, cyberpunk words hidden diagonally.
    private char[][] letterGrid = {
            {'c', 'a', 'b', 'd', 'e', 'f', 'g', 'h', 'i', 'j'},
            {'k', 'y', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's'},
            {'t', 'u', 'b', 'v', 'w', 'x', 'y', 'z', 'a', 'b'},
            {'c', 'd', 'e', 'e', 'f', 'g', 'h', 'i', 'j', 'k'},
            {'l', 'm', 'n', 'o', 'r', 'p', 'q', 'r', 's', 't'},
            {'u', 'v', 'w', 'x', 'y', 'p', 'z', 'a', 'b', 'c'},
            {'d', 'e', 'f', 'g', 'h', 'i', 'u', 'j', 'k', 'l'},
            {'m', 'n', 'o', 'p', 'q', 'r', 's', 'n', 't', 'u'},
            {'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'k', 'd'},
            {'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'}
    };

    public void displayGrid() {
        System.out.println("\n WORD MAZE CHALLENGE");
        System.out.println("A word is hidden in this alphabet maze!");
        System.out.println("Try to find it!\n");

        for (int i = 0; i < letterGrid.length; i++) {
            for (int j = 0; j < letterGrid[i].length; j++) {
                System.out.print(letterGrid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean checkAnswer(String answer) {
        return answer != null && answer.equalsIgnoreCase(TARGET);
    }

    @Override
    public ChallengeResult<String> execute(Scanner scanner) {
        displayGrid();
        System.out.print("Enter the word you found: ");
        String answer = scanner.nextLine().trim();

        if (checkAnswer(answer)) {
            return new ChallengeResult<>(true, answer, "Congratulations! You found the correct word: " + TARGET);
        } else {
            return new ChallengeResult<>(false, answer, "Wrong! Try again!");
        }
    }

    @Override
    public String getDescription() {
        return "Word Maze Challenge";
    }

    @Override
    public boolean isCritical() {
        return false;
    }
}