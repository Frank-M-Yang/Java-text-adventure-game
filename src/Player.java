import java.util.*;

public class Player {
    private final String name;
    private final Set<String> collectedChips;
    private int health;


    public Player(String name) {
        this.name = name;
        this.collectedChips = new HashSet<>();
        this.health = GameConfiguration.MAX_HEALTH;
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
        health = GameConfiguration.MAX_HEALTH;
        collectedChips.clear();
    }

    public void showStatus(){
        System.out.println("Player " + name + " has " + getChipsNumber() + " chips");

        if(!collectedChips.isEmpty()){
            System.out.println("You have Completed Directions:" +  collectedChips);
        }
    }

    public void takeDamage(int damage){
        this.health = Math.max(0, this.health - damage);
        System.out.println("You took " + damage + " damage! Now HP is" + this.health);

        if(this.health <= 0){
            System.out.println("You lost!");
        }
    }

    public boolean isAlive(){
        return health > 0;
    }

    public Set<String> getCollectedChips(){
        return new HashSet<>(collectedChips);
    }

    public int getHealth(){
        return health;
    }

    public String getName(){
        return name;
    }

    public boolean canChallenge(String direction) {
        return !collectedChips.contains(direction);
    }

}
