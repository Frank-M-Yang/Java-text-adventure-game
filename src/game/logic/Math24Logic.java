package game.logic;

import java.util.*;

/**
 * Core logic for the 24 Points challenge.
 * Contains only rules and evaluation logic.
 * No I/O, no game text, no Scanner.
 */
public class Math24Logic {

    private final int target;
    private final int[] numbers;

    public Math24Logic(int target, int[] numbers) {
        this.target = target;
        this.numbers = numbers.clone();
    }

    /* ---------- Public API (for Challenge & JUnit) ---------- */

    /** Validates characters in expression */
    public boolean hasValidCharacters(String expr) {
        return expr.matches("[0-9+\\-*/()\\s]+");
    }

    /** Validates that the correct numbers are used */
    public boolean usesCorrectNumbers(String expr) {
        List<Integer> extracted = extractNumbers(expr);
        if (extracted.size() != numbers.length) {
            return false;
        }

        List<Integer> expected = new ArrayList<>();
        for (int n : numbers) expected.add(n);
        Collections.sort(expected);

        Collections.sort(extracted);
        return extracted.equals(expected);
    }

    /** Evaluates expression and returns result */
    public double evaluate(String expr) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isWhitespace(c)) continue;

            if (Character.isDigit(c)) {
                double num = 0;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    num = num * 10 + (expr.charAt(i) - '0');
                    i++;
                }
                i--;
                values.push(num);
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    apply(values, ops.pop());
                }
                ops.pop();
            } else {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    apply(values, ops.pop());
                }
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            apply(values, ops.pop());
        }

        return values.pop();
    }

    /** Checks if expression evaluates to target */
    public boolean hitsTarget(String expr) {
        return Math.abs(evaluate(expr) - target) < 1e-6;
    }

    //Internal helpers

    private void apply(Stack<Double> values, char op) {
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

    private List<Integer> extractNumbers(String expr) {
        List<Integer> list = new ArrayList<>();
        for (String t : expr.split("[^0-9]")) {
            if (!t.isEmpty()) {
                list.add(Integer.parseInt(t));
            }
        }
        return list;
    }
}
