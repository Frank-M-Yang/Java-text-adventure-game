package game.command;

/**
 * Parser for converting raw user input into structured Command objects.
 * Handles command normalization, synonyms, and argument extraction.
 */
public class CommandParser {

    /**
     * Parses raw user input into a structured Command.
     *
     * Parsing process:
     * 1. Trims whitespace and converts to lowercase
     * 2. Splits into command word and optional argument
     * 3. Maps synonyms to standardized command types
     * 4. Returns UNKNOWN for unrecognized commands
     *
     * @param input Raw user input (e.g., "go east", "look", "help")
     * @return Parsed Command object, never null
     *
     * @example parse("go east") → Command(GO, "east")
     * @example parse("move north") → Command(GO, "north") // synonym
     * @example parse("help") → Command(HELP, "")
     * @example parse("xyz") → Command(UNKNOWN, "xyz")
     */
    public static Command parse(String input) {
        // Handle null or empty input
        if (input == null || input.trim().isEmpty()) {
            return new Command(CommandType.UNKNOWN, "");
        }

        // Split into max 2 parts: command and optional argument
        // "go east" → ["go", "east"]
        // "look" → ["look"]
        String[] parts = input.trim().toLowerCase().split("\\s+", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";

        // Map command words to types, supporting synonyms
        switch (command) {
            case "go": case "move":       // Synonyms for movement
                return new Command(CommandType.GO, argument);
            case "look": case "examine":  // Synonyms for examination
                return new Command(CommandType.LOOK, argument);
            case "status":                // Player status check
                return new Command(CommandType.STATUS, argument);
            case "help": case "?":        // Help request
                return new Command(CommandType.HELP, argument);
            case "quit": case "exit":     // Exit game
                return new Command(CommandType.QUIT, argument);
            default:                      // Unrecognized command
                return new Command(CommandType.UNKNOWN, input);
        }
    }
}