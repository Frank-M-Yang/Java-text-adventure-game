package game.command;

public class CommandParser {
    public static Command parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new Command(CommandType.UNKNOWN, "");
        }

        String[] parts = input.trim().toLowerCase().split("\\s+", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "go": case "move":
                return new Command(CommandType.GO, argument);
            case "look": case "examine":
                return new Command(CommandType.LOOK, argument);
            case "status":
                return new Command(CommandType.STATUS, argument);
            case "help": case "?":
                return new Command(CommandType.HELP, argument);
            case "quit": case "exit":
                return new Command(CommandType.QUIT, argument);
            default:
                return new Command(CommandType.UNKNOWN, input);
        }
    }
}
