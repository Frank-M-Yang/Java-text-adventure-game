package game.core;

import java.util.*;

public class Player {
    private final String name;
    private final Set<String> collectedChips;

    public Player(String name) {
        this.name = name;
        this.collectedChips = new HashSet<>();
    }

    public void collectChips(String direction){
        collectedChips.add(direction);
    }

    public boolean hasChips(String direction){
        return collectedChips.contains(direction);
    }

    public int getChipsNumber(){
        return collectedChips.size();
    }

    public void resetGame(){
        collectedChips.clear();
    }

    public void showStatus(){
        System.out.println("\n╔═══════════════ PLAYER STATUS ═══════════════╗");
        System.out.println("║ Agent: " + name);
        System.out.println("║ Memory Chips: " + getChipsNumber() + "/" +
                game.core.GameConfiguration.TOTAL_NUMBER_OF_CHIPS);

        if(!collectedChips.isEmpty()){
            System.out.println("║ Completed Areas: " + collectedChips);
        } else {
            System.out.println("║ Completed Areas: None");
        }

        System.out.println("╚═════════════════════════════════════════════╝");
    }

    public Set<String> getCollectedChips(){
        return new HashSet<>(collectedChips);
    }

    public String getName(){
        return name;
    }
}
