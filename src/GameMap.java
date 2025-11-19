import java.util.*;

public class GameMap {
    private final Map<String, Area<?>> areas;

    public GameMap() {
        areas = new HashMap<>();
        areas.put("East", new Area<>("East", "Memory Maze", new MemoryPuzzleChallenge()));
        areas.put("West", new Area<>("West", "Word Search Game", new WordSearchChallenge()));
        areas.put("South", new Area<>("South", "Sphinx's Riddle", new SphinxRiddleChallenge()));
        areas.put("North", new Area<>("North", "24 Points Challenge", new Math24Challenge()));
    }

    public Area<?> getArea(String direction) {
        return areas.get(direction);
    }

    public boolean isValidDirection(String direction) {
        return areas.containsKey(direction);
    }

    public Set<String> getAvailableDirections() {
        return areas.keySet();
    }

    public void displayAvailableAreas() {
        System.out.println("\nAvailable directions:");
        for (String direction : areas.keySet()) {
            Area<?> area = areas.get(direction);
            String status = area.isChipCollected() ? "✓ Completed" : "○ Available";
            System.out.println("- " + direction + ": " + area.getDescription() + " (" + status + ")");
        }
    }
}