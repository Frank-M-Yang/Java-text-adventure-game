import java.util.*;

public class SphinxRiddleChallenge implements Challenge<String> {
    private final String riddle;
    private final Set<String> acceptableAnswers;

    public SphinxRiddleChallenge() {
        this.riddle = "What walks on four legs in the morning, two legs at noon, and three legs in the evening?";
        this.acceptableAnswers = new HashSet<>(Arrays.asList("man", "human", "person", "people"));
    }

    @Override
    public ChallengeResult<String> execute(Scanner scanner) {
        System.out.println("The Sphinx's Riddle:");
        System.out.println("\"" + riddle + "\"\n");
        System.out.print("Your answer: ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (acceptableAnswers.stream().anyMatch(answer::contains)) {
            return new ChallengeResult<>(true, answer,
                    "The Sphinx falls silent...\nSphinx: \"...Correct. You may pass.\"");
        } else {
            return new ChallengeResult<>(false, answer,
                    "Sphinx: \"Wrong! You shall be devoured!\"");
        }
    }

    @Override
    public String getDescription() {
        return "Sphinx's Riddle";
    }

    @Override
    public boolean isCritical() {
        return true;
    }
}
