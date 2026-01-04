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
        this.player = new Player("Ming Yang");
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
            System.out.print("\nWhat would you like to do? > ");
            String input = scanner.nextLine();

            Command command = CommandParser.parse(input);
            if (!processCommand(command, scanner)) {
                break;
            }
        }

        if (inFinalChallenge && !gameOver) {
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

            story.showChipObtained(direction, player.getChipsNumber(),
                    GameConfiguration.TOTAL_NUMBER_OF_CHIPS);

            if (player.getChipsNumber() >= GameConfiguration.TOTAL_NUMBER_OF_CHIPS) {
                story.showAllChipsCollected();
                inFinalChallenge = true;
            }
        } else {
            if (direction.equals(GameConfiguration.DANGEROUS_DIRECTION)) {
                handleCriticalFailure(scanner);
            } else {
                System.out.println("\n→ Challenge failed! You can try again.");
            }
        }
    }

    private void handleCriticalFailure(Scanner scanner) {
        story.showSphinxCurse();
        story.showSphinxCurseEffect();
        showEnding(GameEnding.GAME_OVER);
        gameOver = true;
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
        story.showFinalChallengeIntro();

        SudokuChallenge finalChallenge = new SudokuChallenge();
        ChallengeResult<int[][]> result = finalChallenge.execute(scanner);

        if (result.isSuccess()) {
            story.showDoctorDefeated();
            showPostVictoryOptions(scanner);
        } else {
            if (result.getMessage().equals("OFFER_DEAL")) {
                handleDoctorDeal(scanner);
            } else {
                System.out.println("\nDoctor: \"Pathetic! You're worthless!\"");
                showEnding(GameEnding.GAME_OVER);
                gameOver = true;
            }
        }
    }

    private void handleDoctorDeal(Scanner scanner) {
        story.showDoctorDealIntro();
        story.showDoctorDealOffer();
        story.showDoctorDealOptions();

        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("\nWhat is your decision? (1 or 2): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    acceptDoctorDeal();
                    validChoice = true;
                    break;
                case "2":
                    refuseDoctorDeal();
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid choice! Enter 1 or 2.");
            }
        }

        gameOver = true;
    }

    private void acceptDoctorDeal() {
        story.showDealAcceptance_Part1();
        story.showDealAcceptance_Part2();
        story.showDealAcceptance_Part3();
        story.showDealAcceptance_Part4();
        showEnding(GameEnding.ETERNAL_SERVANT);
    }

    private void refuseDoctorDeal() {
        story.showDealRefusal_Part1();
        story.showDealRefusal_Part2();
        story.showDealRefusal_Part3();
        story.showDealRefusal_Part4();
        story.showDealRefusal_Part5();
        showEnding(GameEnding.GAME_OVER);
    }

    private void showPostVictoryOptions(Scanner scanner) {
        story.showVictoryOptions();

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
        story.showDarkLordPath();
        showEnding(GameEnding.DARK_LORD);
        gameOver = true;
    }

    private void showEternalServantPath(Scanner scanner) {
        story.showEternalServantPathIntro();

        System.out.print("\nDo you surrender the memory chips? (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("yes") || answer.equals("y")) {
            story.showEternalServantEnding();
            showEnding(GameEnding.ETERNAL_SERVANT);
        } else {
            story.showEternalServantRefusal();
            showPostVictoryOptions(scanner);
            return;
        }

        gameOver = true;
    }

    private void showRedemptionPath(Scanner scanner) {
        story.showRedemptionPathIntro();
        story.showRedemptionChoice();

        System.out.print("\nDo you accept their offer? (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("yes") || answer.equals("y")) {
            story.showRedemptionAccepted();
        } else {
            story.showRedemptionDeclined();
        }

        showEnding(GameEnding.REDEMPTION);
        gameOver = true;
    }

    private void showEnding(GameEnding ending) {
        final int BOX_WIDTH = 52;

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                                                    ║");

        String endingText = "ENDING: " + ending.getName();
        int padding = (BOX_WIDTH - endingText.length()) / 2;
        String centeredEnding = " ".repeat(padding) + endingText +
                " ".repeat(BOX_WIDTH - padding - endingText.length());
        System.out.println("║" + centeredEnding + "║");

        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");

        System.out.println("\n" + ending.getDescription());

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                                                    ║");

        String thankYou = "THANK YOU FOR PLAYING";
        int thankPadding = (BOX_WIDTH - thankYou.length()) / 2;
        String centeredThank = " ".repeat(thankPadding) + thankYou +
                " ".repeat(BOX_WIDTH - thankPadding - thankYou.length());
        System.out.println("║" + centeredThank + "║");

        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }
}