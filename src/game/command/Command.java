package game.command;

/**
 * Immutable container for parsed player commands.
 * Stores a command type and its optional argument.
 */
public class Command {
    private final CommandType type;
    private final String argument;

    /**
     * Constructs a new Command with specified type and argument.
     *
     * @param type The command type (e.g., GO, LOOK, HELP)
     * @param argument Optional argument for the command (e.g., "east" for GO)
     */
    public Command(CommandType type, String argument) {
        this.type = type;
        this.argument = argument;
    }

    /** @return The command type */
    public CommandType getType() { return type; }

    /** @return The command argument, may be null or empty */
    public String getArgument() { return argument; }
}