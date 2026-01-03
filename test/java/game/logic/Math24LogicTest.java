package game.logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class Math24LogicTest {

    private final Math24Logic logic =
            new Math24Logic(24, new int[]{3, 8, 8, 9});

    @Test
    void validExpressionHits24() {
        assertTrue(logic.hitsTarget("(9-8)*8*3"));
    }

    @Test
    void invalidCharactersRejected() {
        assertFalse(logic.hasValidCharacters("9^8*3"));
    }

    @Test
    void wrongNumbersRejected() {
        assertFalse(logic.usesCorrectNumbers("9*8*3"));
    }

    @Test
    void wrongResultFails() {
        assertFalse(logic.hitsTarget("9+8+8-3"));
    }
}

