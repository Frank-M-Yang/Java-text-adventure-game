package game.story;

public enum GameEnding {
    DARK_LORD("The New Evil Mastermind", "You defeated the old doctor and became the new lord of evil"),
    REDEMPTION("Light of Redemption", "You chose justice and found your true self - you are Jackie Chan!Even though you are an android created to commit evil, you betrayed the darkness and walked towards the light."),
    ETERNAL_SERVANT("Eternal Servant", "Without memory, you will fall into Avici hell,forever follow the doctor in an evil cycle"),
    GAME_OVER("Game Over", "You have been defeated");

    private final String name;
    private final String description;

    GameEnding(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
}
