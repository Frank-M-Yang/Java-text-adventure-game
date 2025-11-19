import java.util.Scanner;

public class Math24Challenge implements Challenge<String> {
    @Override
    public ChallengeResult<String> execute(Scanner scanner) {
        System.out.println("Numbers: 3, 3, 8, 8");
        System.out.println("Make 24 using these numbers with +, -, *, /, (, )");
        System.out.print("Your expression: ");

        String expression = scanner.nextLine().trim();
        boolean valid = expression.contains("3") && expression.contains("8");

        if (valid) {
            return new ChallengeResult<>(true, expression, "Math Master: \"Correct! Your logic is strong.\"");
        } else {
            return new ChallengeResult<>(false, expression, "Math Master: \"Not quite right.\"");
        }
    }

    @Override
    public String getDescription() { return "24 Points Challenge"; }
    @Override
    public boolean isCritical() { return false; }
}
