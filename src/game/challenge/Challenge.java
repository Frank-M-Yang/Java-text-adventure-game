package game.challenge;

import java.util.Scanner;

public interface Challenge<T> {
    ChallengeResult<T> execute(Scanner scanner);
    String getDescription();
    boolean isCritical();
}
