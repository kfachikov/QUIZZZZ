package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerGameRoundTest {

    @Test
    void getFinalAnswer() {
        MultiPlayerGameRound multiPlayerGameRound = new MultiPlayerGameRound(1L, "answer1");
        assertEquals("answer1", multiPlayerGameRound.finalAnswer);
    }

    @Test
    void setFinalAnswer() {
        MultiPlayerGameRound multiPlayerGameRound = new MultiPlayerGameRound(1L, "answer1");
        multiPlayerGameRound.setFinalAnswer("answer2");
        assertEquals("answer2", multiPlayerGameRound.finalAnswer);
    }

    @Test
    void getId() {
        MultiPlayerGameRound multiPlayerGameRound = new MultiPlayerGameRound(1L, "answer1");
        assertEquals(1L, multiPlayerGameRound.roundId);
    }

    @Test
    void setId() {
        MultiPlayerGameRound multiPlayerGameRound = new MultiPlayerGameRound(1L, "answer1");
        multiPlayerGameRound.setId(2L);
        assertEquals(2L, multiPlayerGameRound.roundId);
    }
}