package game.core;

import java.util.*;

/**
 * Player character for the text adventure game.
 * Manages identity, progress tracking, and memory chip collection.
 *
 * @author Ming Yang
 */
public class Player {

    /**
     * The player's name (immutable).
     */
    private final String name;

    /**
     * Set of directions where memory chips have been collected.
     * Uses HashSet for O(1) lookup and duplicate prevention.
     */
    private final Set<String> collectedChips;

    /**
     * Constructs a new Player with the specified name.
     *
     * @param name The player's name
     */
    public Player(String name) {
        this.name = name;
        this.collectedChips = new HashSet<>();
    }

    /**
     * Collects a memory chip from the specified direction.
     *
     * @param direction The direction/region from which the chip is collected
     */
    public void collectChips(String direction) {
        collectedChips.add(direction);
    }

    /**
     * Checks if a chip from the specified direction has been collected.
     *
     * @param direction The direction to check
     * @return true if chip exists, false otherwise
     */
    public boolean hasChips(String direction) {
        return collectedChips.contains(direction);
    }

    /**
     * Returns the total number of memory chips collected.
     *
     * @return Number of unique chips collected (0-4)
     */
    public int getChipsNumber() {
        return collectedChips.size();
    }

    /**
     * Resets the player's chip collection (Sphinx's curse effect).
     */
    public void resetGame() {
        collectedChips.clear();
    }

    /**
     * Displays the player's current status in a formatted box.
     */
    public void showStatus() {
        System.out.println("\n╔═══════════════ PLAYER STATUS ═══════════════╗");
        System.out.println("║ Agent: " + name);
        System.out.println("║ Memory Chips: " + getChipsNumber() + "/" +
                GameConfiguration.TOTAL_NUMBER_OF_CHIPS);

        if (!collectedChips.isEmpty()) {
            System.out.println("║ Completed Areas: " + collectedChips);
        } else {
            System.out.println("║ Completed Areas: None");
        }

        System.out.println("╚═════════════════════════════════════════════╝");
    }

    /**
     * Returns a defensive copy of the collected chips set.
     *
     * @return Copy of the collected chips set
     */
    public Set<String> getCollectedChips() {
        return new HashSet<>(collectedChips);
    }

    /**
     * Returns the player's name.
     *
     * @return The player's name
     */
    public String getName() {
        return name;
    }
}