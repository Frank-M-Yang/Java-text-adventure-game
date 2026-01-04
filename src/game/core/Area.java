package game.core;
import game.challenge.Challenge;

/**
 * Represents a game area/region that contains a challenge.
 * Each area corresponds to one cardinal direction and holds one memory chip.
 *
 * @param <T> The type of result returned by the area's challenge
 */
public class Area<T> {
    /** Area name (e.g., "East", "West", "North", "South") */
    private final String name;

    /** Brief description of the area's environment */
    private final String description;

    /** The challenge that must be completed in this area */
    private final Challenge<T> challenge;

    /** Tracks whether this area's memory chip has been collected */
    private boolean chipCollected;

    /**
     * Constructs a new Area with the specified properties.
     *
     * @param name The area's name (typically a cardinal direction)
     * @param description Text describing the area's environment
     * @param challenge The challenge to be completed in this area
     */
    public Area(String name, String description, Challenge<T> challenge) {
        this.name = name;
        this.description = description;
        this.challenge = challenge;
        this.chipCollected = false;  // Initially not completed
    }

    /** @return The area's name */
    public String getName() { return name; }

    /** @return The area's description */
    public String getDescription() { return description; }

    /** @return true if this area's chip has been collected */
    public boolean isChipCollected() { return chipCollected; }

    /** @return The challenge associated with this area */
    public Challenge<T> getChallenge() { return challenge; }

    /**
     * Marks this area as completed (chip collected).
     * Called after successfully completing the challenge.
     */
    public void complete() {
        this.chipCollected = true;
    }

    /**
     * Resets this area's completion status.
     * Used when the Sphinx's curse resets all progress.
     */
    public void reset() {
        this.chipCollected = false;
    }

    /**
     * Displays the area's entry narrative and introduction.
     * Shows different flavor text based on which area is being entered.
     */
    public void enter() {
        System.out.println("You travel towards the " + name.toLowerCase() + "...");
        System.out.println(description);

        // Display area-specific introductory text
        switch (name) {
            case "East":
                System.out.println("An ancient temple stands before you with memory fragments on the walls...");
                System.out.println("Guardian: \"To pass, you must correctly arrange these memories!\"\n");
                break;
            case "West":
                System.out.println("You arrive at a mysterious room filled with words...");
                System.out.println("Guardian: \"Find the keywords hidden in this sea of text!\"\n");
                break;
            case "South":
                System.out.println("You arrive at desert ruins with a massive Sphinx statue!");
                System.out.println("Sphinx: \"Who are you? Wanna the chip? Answer my riddle first!\"");
                System.out.println("Sphinx: \"The price of failure... is to lose everything and start over!\"\n");
                System.out.println("WARNING: Wrong answer will reset the game completely!");
                break;
            case "North":
                System.out.println("You arrive at snowy mountain peaks...");
                System.out.println("A mysterious Math Master guards this place.\n");
                System.out.println("Math Master: \"Use these four numbers and arithmetic operations to make 24!\"\n");
                break;
        }
    }
}