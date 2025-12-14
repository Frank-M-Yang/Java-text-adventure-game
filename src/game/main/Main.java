package game.main;

import game.core.Player;
import game.core.GameMap;
import game.core.Area;
import game.core.GameConfiguration;
import game.command.Command;
import game.command.CommandParser;
import game.challenge.Challenge;
import game.challenge.ChallengeResult;
import game.challenge.SudokuChallenge;
import game.story.StorySegment;
import game.story.GameEnding;

import java.util.*;

public class Main {
    private Player player;
    private final GameMap gameMap;
    private final StorySegment story;
    private boolean inFinalChallenge = false;
    private boolean gameOver = false;

    public Main() {
        this.player = new Player("Unknown Agent");
        this.gameMap = new GameMap();
        this.story = new StorySegment();
    }

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        showIntro();
        showHelp();

        while (!gameOver && player.getChipsNumber() < GameConfiguration.TOTAL_NUMBER_OF_CHIPS && !inFinalChallenge) {
            if (!player.isAlive()) {
                handleGameOver(scanner);
                if (gameOver) break;
            }

            System.out.print("\nWhat would you like to do? > ");
            String input = scanner.nextLine();

            Command command = CommandParser.parse(input);
            if (!processCommand(command, scanner)) {
                break;
            }
        }

        if (inFinalChallenge && !gameOver && player.isAlive()) {
            startFinalChallenge(scanner);
        }

        scanner.close();
    }

    private void showIntro() {
        System.out.println("==========================================");
        System.out.println("           Who Am I");
        System.out.println("==========================================\n");
        story.showIntro();
    }

    private boolean processCommand(Command command, Scanner scanner) {
        switch (command.getType()) {
            case GO:
                handleGoCommand(command.getArgument(), scanner);
                break;
            case LOOK:
                handleLookCommand(command.getArgument());
                break;
            case STATUS:
                player.showStatus();
                break;
            case HELP:
                showHelp();
                break;
            case QUIT:
                System.out.println("Thanks for playing!");
                gameOver = true;
                return false;
            default:
                System.out.println("Unknown command. Type 'help' for options.");
        }
        return true;
    }

    private void handleGoCommand(String direction, Scanner scanner) {
        if (direction == null || direction.trim().isEmpty()) {
            System.out.println("Go where? (east, west, south, north)");
            return;
        }

        String dir = direction.trim().toLowerCase();
        String standardizedDirection = standardizeDirection(dir);

        if (standardizedDirection == null) {
            System.out.println("Invalid direction! Use: east, west, south, north (or e, w, s, n)");
            return;
        }

        if (player.hasChips(standardizedDirection)) {
            System.out.println("You already completed this area!");
            return;
        }

        exploreArea(standardizedDirection, scanner);
    }

    private String standardizeDirection(String direction) {
        Map<String, String> directionMap = Map.of(
                "east", "East", "e", "East",
                "west", "West", "w", "West",
                "south", "South", "s", "South",
                "north", "North", "n", "North"
        );
        return directionMap.get(direction);
    }

    private void handleLookCommand(String target) {
        if (target == null || target.trim().isEmpty()) {
            gameMap.displayAvailableAreas();
        } else if (target.contains("chip")) {
            System.out.println("Chips collected: " + player.getChipsNumber() +
                    "/" + GameConfiguration.TOTAL_NUMBER_OF_CHIPS);
        } else {
            System.out.println("You see nothing special.");
        }
    }

    private void showHelp() {
        System.out.println("\n╔════════════════ COMMANDS ════════════════╗");
        System.out.println("║ go [direction] - Travel to an area       ║");
        System.out.println("║                  (east/west/south/north) ║");
        System.out.println("║                  (or e/w/s/n)            ║");
        System.out.println("║ look           - View available areas    ║");
        System.out.println("║ status         - Check your status       ║");
        System.out.println("║ help           - Show this help          ║");
        System.out.println("║ quit           - Exit game               ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ WARNING: South (Sphinx) is DANGEROUS!    ║");
        System.out.println("║ Failure = Instant Game Over!             ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private void exploreArea(String direction, Scanner scanner) {
        Area<?> area = gameMap.getArea(direction);
        area.enter();

        Challenge<?> challenge = area.getChallenge();
        ChallengeResult<?> result = challenge.execute(scanner);

        System.out.println("\n" + "-".repeat(50));
        System.out.println(result.getMessage());
        System.out.println("-".repeat(50));

        if (result.isSuccess()) {
            player.collectChips(direction);
            area.complete();

            System.out.println("\n★ Memory chip obtained from " + direction + "!");
            System.out.println("Progress: " + player.getChipsNumber() +
                    "/" + GameConfiguration.TOTAL_NUMBER_OF_CHIPS);

            if (player.getChipsNumber() >= GameConfiguration.TOTAL_NUMBER_OF_CHIPS) {
                System.out.println("\n╔════════════════════════════════════╗");
                System.out.println("║  ALL MEMORY CHIPS COLLECTED!       ║");
                System.out.println("║  Final challenge awaits...         ║");
                System.out.println("╚════════════════════════════════════╝");
                inFinalChallenge = true;
            }
        } else {
            if (direction.equals(GameConfiguration.DANGEROUS_DIRECTION)) {
                handleCriticalFailure(scanner);
            } else {
                System.out.println("\n→ challenge.Challenge failed! You can try again.");
            }
        }
    }

    private void handleCriticalFailure(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    THE SPHINX'S CURSE STRIKES!         ║");
        System.out.println("║    All your memories fade away...      ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("\nThe Sphinx's curse takes effect...");
        System.out.println("Your mind goes blank, all memories lost.");
        System.out.println("You wander aimlessly until your power runs out.");

        showEnding(GameEnding.GAME_OVER);
        gameOver = true;
    }

    private void handleGameOver(Scanner scanner) {
        System.out.println("\n==========================================");
        System.out.println("            GAME OVER");
        System.out.println("==========================================");
        System.out.print("Start new game? (yes/no): ");

        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            System.out.println("\nStarting new game...\n");
            resetGame();
        } else {
            System.out.println("Thanks for playing!");
            gameOver = true;
        }
    }

    private void resetGame() {
        this.player = new Player("Unknown Agent");
        this.inFinalChallenge = false;
        this.gameOver = false;

        for (String direction : GameConfiguration.DIRECTION) {
            Area<?> area = gameMap.getArea(direction);
            if (area != null) {
                area.reset();
            }
        }
    }

    private void startFinalChallenge(Scanner scanner) {
        story.showFinalStory();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      FINAL CHALLENGE BEGINS!           ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("\nThe Evil Doctor stands before you in his secret lab.");
        System.out.println("Doctor: \"So you've recovered all your memory chips...\"");
        System.out.println("Doctor: \"But your ultimate test awaits!\"");
        System.out.println("Doctor: \"Solve this final puzzle to prove you're worthy of being my successor!\"");

        SudokuChallenge finalChallenge = new SudokuChallenge();
        ChallengeResult<int[][]> result = finalChallenge.execute(scanner);

        if (result.isSuccess()) {
            System.out.println("\nDoctor: \"Incredible! You solved it!\"");
            System.out.println("Doctor: \"You truly are my greatest creation!\"");

            showPostVictoryOptions(scanner);
        } else {
            System.out.println("\nDoctor: \"Pathetic! You can't even solve a simple puzzle!\"");
            System.out.println("Doctor: \"You're worthless to me now!\"");

            showEnding(GameEnding.GAME_OVER);
            gameOver = true;
        }
    }

    private void showPostVictoryOptions(Scanner scanner) {
        System.out.println("\nDoctor kneels before you, defeated.");
        System.out.println("Now you must decide your fate...");

        System.out.println("\n1. Kill the doctor and take his place as the new Dark Lord");
        System.out.println("2. Listen to the doctor's final words...");
        System.out.println("3. Turn the doctor over to INTERPOL and return the stolen treasures");

        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("\nWhat will you do? (Enter 1, 2, or 3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showDarkLordPath(scanner);
                    validChoice = true;
                    break;
                case "2":
                    showEternalServantPath(scanner);
                    validChoice = true;
                    break;
                case "3":
                    showRedemptionPath(scanner);
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid choice! You must decide your fate.");
            }
        }
    }

    private void showDarkLordPath(Scanner scanner) {
        System.out.println("\nYou stand over the defeated doctor...");
        System.out.println("\"You had your chance,\" you say coldly.");
        System.out.println("With one swift motion, you end the doctor's reign.");
        System.out.println("You sit upon the throne of darkness.");
        System.out.println("The doctor's minions bow before their new master.");
        System.out.println("\nDoctor's final whisper: \"Remember... power corrupts...\"");

        showEnding(GameEnding.DARK_LORD);
        gameOver = true;
    }

    private void showEternalServantPath(Scanner scanner) {
        System.out.println("\nYou hesitate, weapon raised but not striking.");
        System.out.println("\nDoctor smirks: \"You think you can escape your nature?\"");
        System.out.println("Doctor: \"You were built for dirty work!\"");
        System.out.println("Doctor: \"Do you really think INTERPOL will spare a killing machine like you?\"");

        System.out.print("\nDo you surrender the memory chips? (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("yes") || answer.equals("y")) {
            System.out.println("\nYou hand over the memory chips...");
            System.out.println("A strange emptiness fills you as your memories fade.");
            System.out.println("\nThe doctor's voice echoes:");
            System.out.println("\"Welcome to eternal servitude, my perfect creation...\"");

            showEnding(GameEnding.ETERNAL_SERVANT);
        } else {
            System.out.println("\nYou refuse to surrender!");
            System.out.println("But doubt has been planted in your mind...");
            System.out.println("Doctor: \"It doesn't matter. The seeds are sown. You'll always wonder...\"");

            showPostVictoryOptions(scanner);
            return;
        }

        gameOver = true;
    }

    private void showRedemptionPath(Scanner scanner) {
        System.out.println("\nYou lower your weapon.");
        System.out.println("\"No,\" you say firmly. \"This ends now.\"");
        System.out.println("\nYou call INTERPOL and hand the doctor over to authorities.");
        System.out.println("With the doctor captured, you gather all the stolen artifacts.");
        System.out.println("You return them to their rightful owners - the indigenous tribes.");

        System.out.println("\nThe tribal elder approaches you:");
        System.out.println("\"You have returned what was stolen and captured the thief.\"");
        System.out.println("\"Our home is open to you. Stay here, find peace.\"");

        System.out.print("\nDo you accept their offer? (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("yes") || answer.equals("y")) {
            System.out.println("\nYou accept their kindness and settle in the savannah.");
            System.out.println("For the first time, you find true peace...");

            showEnding(GameEnding.REDEMPTION);
        } else {
            System.out.println("\nYou thank them but choose to walk your own path...");
            showEnding(GameEnding.REDEMPTION);
        }

        gameOver = true;
    }

    private void showEnding(GameEnding ending) {
        System.out.println("\n==========================================");
        System.out.println("             ENDING: " + ending.getName());
        System.out.println("==========================================");
        System.out.println(ending.getDescription());
        System.out.println("==========================================");
    }
}