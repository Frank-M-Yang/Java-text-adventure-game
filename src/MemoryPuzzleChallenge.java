import java.util.*;

public class MemoryPuzzleChallenge implements Challenge<String> {
    private final List<QuestionOption> options;
    private final String correctAnswer;

    public MemoryPuzzleChallenge() {
        options = Arrays.asList(
                new QuestionOption("A", "Ethical systems are human constructs designed to mitigate an inherent cosmic cruelty"),
                new QuestionOption("B", "The necessity of self-betrayal is an inescapable and universal aspect of being"),
                new QuestionOption("C", "Creation is an inherently flawed process, destined to produce suffering"),
                new QuestionOption("D", "Identity is a fragile concept that cannot withstand external pressures")
        );

        correctAnswer = "B";
    }

    @Override
    public ChallengeResult<String> execute(Scanner scanner) {
        System.out.println("          MEMORY FRAGMENT ANALYSIS CHALLENGE");
        System.out.println();
        System.out.println("Recovered memory fragment - philosophical text:");
        System.out.println();
        System.out.println("\"You will be required to do wrong no matter where you go.");
        System.out.println("It is the basic condition of life, to be required to");
        System.out.println("violate your own identity. At some time, every creature");
        System.out.println("which lives must do so. It is the ultimate shadow, the");
        System.out.println("defeat of creation; this is the curse at work, the curse");
        System.out.println("that feeds on all life. Everywhere in the universe.\"");
        System.out.println("— Recovered from Philip K. Dick, Do Androids Dream of Electric Sheep?");
        System.out.println();
        System.out.println("Analyze the core meaning of this memory fragment:");
        System.out.println("What is the author's primary contention?");
        System.out.println();

        options.forEach(opt -> System.out.println(opt.getId() + ". " + opt.getDescription()));

        System.out.println();
        System.out.print("Enter your choice (A, B, C, or D): ");

        String answer = scanner.nextLine().trim().toUpperCase();

        if (answer.equals(correctAnswer)) {
            System.out.println("Correct! The memory fragment is successfully decoded.");
            return new ChallengeResult<>(true, answer, "Philosophical memory fragment decoded successfully");
        } else {
            System.out.println("Incorrect! Memory fragment remains corrupted.");
            return new ChallengeResult<>(false, answer, "Failed to decode the memory fragment");
        }
    }

    @Override
    public String getDescription() {
        return "Philosophical Memory Analysis";
    }

    @Override
    public boolean isCritical() {
        return true;
    }

    private static class QuestionOption {
        private final String id;
        private final String description;

        public QuestionOption(String id, String description) {
            this.id = id;
            this.description = description;
        }

        public String getId() { return id; }
        public String getDescription() { return description; }
    }
}