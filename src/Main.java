//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

public class Main {
    private Player player;
    private final GameMap gameMap;
    private final StorySegment story;  // ✅ 使用项目中的 StorySegment
    private boolean inFinalChallenge = false;
    private boolean gameOver = false;

    public Main() {
        this.player = new Player("Unknown Agent");
        this.gameMap = new GameMap();
        this.story = new StorySegment();  // ✅ 初始化故事
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
            // Use GameMap Method
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

        Challenge<?> challenge = area.getChallenge();  // ✅ 需要在Area中添加这个方法
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
                System.out.println("\n→ Challenge failed! You can try again.");
            }
        }
    }

    private void handleCriticalFailure(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    THE SPHINX'S CURSE STRIKES!         ║");
        System.out.println("║    All your memories fade away...      ║");
        System.out.println("╚════════════════════════════════════════╝");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        handleGameOver(scanner);
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



        SudokuChallenge finalChallenge = new SudokuChallenge();
        ChallengeResult<int[][]> result = finalChallenge.execute(scanner);

        if (result.isSuccess()) {
            showVictoryEnding();
        } else {
            showDefeatEnding();
        }


        showVictoryEnding();

        gameOver = true;
    }

    private void showVictoryEnding() {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║                                                    ║");
        System.out.println("║          🎉🎉🎉 CONGRATULATIONS! 🎉🎉🎉             ║");
        System.out.println("║                                                    ║");
        System.out.println("║    You defeated the Evil Doctor!                   ║");
        System.out.println("║    Your true identity has been revealed...         ║");
        System.out.println("║                                                    ║");
        System.out.println("║    You are Jackie Chan - Hero of Justice!          ║");
        System.out.println("║                                                    ║");
        System.out.println("║              YOU WIN!                              ║");
        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }
}