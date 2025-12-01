public class Area<T> {
    private final String name;
    private final String description;
    private final Challenge<T> challenge;
    private boolean chipCollected;

    public Area(String name, String description, Challenge<T> challenge) {
        this.name = name;
        this.description = description;
        this.challenge = challenge;
        this.chipCollected = false;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isChipCollected() { return chipCollected; }
    public Challenge<T> getChallenge() { return challenge; }


    public void complete() {
        this.chipCollected = true;
    }


    public void reset() {
        this.chipCollected = false;
    }

    public boolean canChallenge() {
        return !chipCollected;
    }

    public void enter() {
        System.out.println("You travel towards the " + name.toLowerCase() + "...");
        System.out.println(description);

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