package game.challenge;

import java.util.Scanner;
import game.core.GameConfiguration;
import game.logic.Math24Logic;

/**
 * 24 Points Math Challenge
 *
 * <p>The player must use the given numbers to create an expression equal to the target value.
 * Uses two-stack algorithm for expression evaluation.
 *
 * <p>Allowed operators: +, -, *, /, (, )
 *
 * @see <a href="https://blog.csdn.net/Chen_Yu7/article/details/116662816">Reference 1</a>
 * @see <a href="https://blog.csdn.net/qq_40161609/article/details/82950251">Reference 2</a>
 */

public class Math24Challenge implements Challenge<String> {

    private final Math24Logic logic =
            new Math24Logic(
                    GameConfiguration.MATH24_TARGET,
                    GameConfiguration.MATH24_NUMBERS
            );

    @Override
    public ChallengeResult<String> execute(Scanner scanner) {
        printIntro();

        System.out.print("Your expression: ");
        String expr = scanner.nextLine().trim();

        if (!logic.hasValidCharacters(expr)) {
            return fail(expr, "Math Master: \"Invalid characters.\"");
        }

        if (!logic.usesCorrectNumbers(expr)) {
            return fail(expr, "Math Master: \"Use the given numbers only.\"");
        }

        try {
            if (logic.hitsTarget(expr)) {
                return new ChallengeResult<>(
                        true,
                        expr,
                        "Math Master: \"Correct. Elegant solution.\""
                );
            } else {
                return fail(expr,
                        "Math Master: \"That is not " +
                                GameConfiguration.MATH24_TARGET + ".\"");
            }
        } catch (Exception e) {
            return fail(expr, "Math Master: \"Invalid expression.\"");
        }
    }

    private ChallengeResult<String> fail(String expr, String msg) {
        return new ChallengeResult<>(false, expr, msg);
    }

    private void printIntro() {
        System.out.println("\n=== 24 POINTS CHALLENGE ===");
        System.out.print("Numbers: ");
        for (int n : GameConfiguration.MATH24_NUMBERS) {
            System.out.print(n + " ");
        }
        System.out.println("\nGoal: " + GameConfiguration.MATH24_TARGET);
        System.out.println("==========================\n");
    }

    @Override
    public String getDescription() {
        return "24 Points Challenge";
    }

    @Override
    public boolean isCritical() {
        return false;
    }
}