package game.model;

public class MemoryFragment implements Comparable<MemoryFragment> {
    private final String id;
    private final String description;
    private final int order;

    public MemoryFragment(String id, String description, int order) {
        this.id = id;
        this.description = description;
        this.order = order;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public int getOrder() { return order; }

    @Override
    public int compareTo(MemoryFragment other) {
        return Integer.compare(this.order, other.order);
    }
}