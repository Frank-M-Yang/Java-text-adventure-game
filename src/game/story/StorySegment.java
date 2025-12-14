package game.story;

import java.util.LinkedList;
import java.util.Queue;

public class StorySegment {
    private final Queue<StoryLine> storyQueue;

    public StorySegment() {
        storyQueue = new LinkedList<>();
    }

    public void showIntro() {
        System.out.println("Who Am I- Text Adventure Game");

        queueStory(new StoryLine(
                "You wake up on a vast grassland...\nYour head is throbbing, and you have no memory of who you are or why you're here."));
        queueStory(new StoryLine(
                "You check your body and find that the chip slots meant for storing memories are empty!\nAll your memory chips are gone!"));
        queueStory(new StoryLine(
                "At this moment, a group of indigenous people approaches...\nTribal Elder: \"Outsider, I see your predicament.\"\nTribal Elder: \"Your chips were taken by an evil man and scattered in four directions.\""));
        queueStory(new StoryLine(
                "The Tribal Elder says gravely:\n\"Take special note: The South is the domain of the Sphinx, the most dangerous!\"\n\"If you fail in the South, the Sphinx's curse will make you lose everything and start over!\""));

        playAllStories();
        System.out.println("==========================================\n");
    }

    public void showFinalStory() {
        System.out.println("You've collected all the memory chips!");

        queueStory(new StoryLine(
                "As the chips are reinserted into your brain, memories flood back like a tidal wave..."));
        queueStory(new StoryLine(
                "You remember everything:\nYou are Jackie Chan, an enhanced human agent created by the Evil Doctor.\nYour mission was to execute secret intelligence operations and steal treasures all over the world. for the doctor."));
        queueStory(new StoryLine(
                "But during the last mission...\nThe doctor wanted to keep all the treasures for himself and tried to eliminate you!\nHe erased your memories and abandoned you in this grassland."));
        queueStory(new StoryLine(
                "Suddenly, an sinister laugh echoes...\nEvil Doctor: \"Hahaha! I never thought you'd recover your memories!\"\nEvil Doctor: \"But this changes nothing, you're still my creation!\""));

        playAllStories();
    }

    public void showDirectionPrompt() {
        System.out.println("Which direction will you explore?");
        System.out.println("North: Forest Domain - Memory Chip 1");
        System.out.println("East: Desert Domain - Memory Chip 2");
        System.out.println("South: Sphinx Domain - Memory Chip 3 (Most Dangerous!)");
        System.out.println("West: Mountain Domain - Memory Chip 4");
    }

    public void showChipCollected(int chipNumber, String domain) {
        System.out.println("Success! You collected Memory Chip " + chipNumber + " from the " + domain + "!");
    }

    public void showGameOver() {
        System.out.println("You have been defeated...");
        System.out.println("【Game Over】");
    }


    public void queueStory(StoryLine segment) {
        storyQueue.offer(segment);
    }

    public void playAllStories() {
        while (!storyQueue.isEmpty()) {
            storyQueue.poll().display();
        }
    }
}
