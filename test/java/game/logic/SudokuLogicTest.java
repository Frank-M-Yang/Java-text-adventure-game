package game.logic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SudokuLogic.
 */
public class SudokuLogicTest {

    @Test
    public void correctSequenceShouldPass() {
        SudokuLogic logic = new SudokuLogic();

        assertTrue(
                logic.verifySequentialInput(
                        List.of(2, 4, 4, 2, 2, 4, 4, 2)
                )
        );
    }

    @Test
    public void wrongSequenceShouldFail() {
        SudokuLogic logic = new SudokuLogic();

        assertFalse(
                logic.verifySequentialInput(
                        List.of(2, 4, 4, 1)
                )
        );
    }
}
