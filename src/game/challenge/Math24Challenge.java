package game.challenge;

import java.util.*;
import javax.script.*;

/**
 * 24 Points Math Challenge
 * The player must use two 3's and two 8's to create an expression equal to 24
 * Allowed operators: +, -, *, /, (, )
 * reference: https://blog.csdn.net/Chen_Yu7/article/details/116662816 and https://blog.csdn.net/qq_40161609/article/details/82950251
 */

public class Math24Challenge implements Challenge<String> {

    private static final int TARGET = 24;

    @Override
    public ChallengeResult<String> execute(Scanner scanner) {

        printIntro();

        System.out.print("Your expression: ");
        String expression = scanner.nextLine().trim();

        // Basic character validation
        if (!expression.matches("[0-9+\\-*/()\\s]+")) {
            return fail(expression, "Math Master: \"Invalid characters.\"");
        }

        // Extract and validate numbers
        List<Integer> numbers = extractNumbers(expression);
        if (!isValidNumbers(numbers)) {
            return fail(expression,
                    "Math Master: \"Use exactly 3, 8, 8 and 9. Nothing else.\"");
        }

        try {
            double result = evaluate(expression);

            if (Math.abs(result - TARGET) < 1e-6) {
                return new ChallengeResult<>(
                        true,
                        expression,
                        "Math Master: \"Correct. Clean and precise.\""
                );
            } else {
                return fail(expression,
                        "Math Master: \"That equals " + result + ", not 24.\"");
            }

        } catch (Exception e) {
            return fail(expression,
                    "Math Master: \"Invalid expression.\"");
        }
    }

    /* ---------------- Expression Evaluation ---------------- */

    /**
     * Evaluates a math expression using two stacks.
     */
    private double evaluate(String expr) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isWhitespace(c)) continue;

            // Parse number
            if (Character.isDigit(c)) {
                double num = 0;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    num = num * 10 + (expr.charAt(i) - '0');
                    i++;
                }
                i--;
                values.push(num);
            }
            // Opening parenthesis
            else if (c == '(') {
                ops.push(c);
            }
            // Closing parenthesis
            else if (c == ')') {
                while (ops.peek() != '(') {
                    applyOp(values, ops.pop());
                }
                ops.pop();
            }
            // Operator
            else {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    applyOp(values, ops.pop());
                }
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            applyOp(values, ops.pop());
        }

        return values.pop();
    }

    private void applyOp(Stack<Double> values, char op) {
        double b = values.pop();
        double a = values.pop();

        switch (op) {
            case '+': values.push(a + b); break;
            case '-': values.push(a - b); break;
            case '*': values.push(a * b); break;
            case '/': values.push(a / b); break;
        }
    }

    private int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    /* ---------------- Validation ---------------- */

    private List<Integer> extractNumbers(String expr) {
        List<Integer> list = new ArrayList<>();
        String[] tokens = expr.split("[^0-9]");

        for (String t : tokens) {
            if (!t.isEmpty()) {
                list.add(Integer.parseInt(t));
            }
        }
        return list;
    }

    private boolean isValidNumbers(List<Integer> nums) {
        if (nums.size() != 4) return false;
        Collections.sort(nums);
        return nums.equals(Arrays.asList(3, 8, 8, 9));
    }

    private ChallengeResult<String> fail(String expr, String msg) {
        return new ChallengeResult<>(false, expr, msg);
    }

    private void printIntro() {
        System.out.println("\n=== 24 POINTS CHALLENGE ===");
        System.out.println("Numbers: 3, 8, 8, 9");
        System.out.println("Goal: Make 24");
        System.out.println("Operators: + - * / ( )");
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