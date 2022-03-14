package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoloGameRoundTest {

    @Test
    void getFinalAnswer() {
        SoloGameRound soloGameRound = new SoloGameRound(1L, "answer1");
        assertEquals("answer1", soloGameRound.finalAnswer);
    }

    @Test
    void setFinalAnswer() {
        SoloGameRound soloGameRound = new SoloGameRound(1L, "answer1");
        soloGameRound.setFinalAnswer("answer2");
        assertEquals("answer2", soloGameRound.finalAnswer);
    }

    @Test
    void getId() {
        SoloGameRound soloGameRound = new SoloGameRound(1L, "answer1");
        assertEquals(1L, soloGameRound.getId());
    }

    @Test
    void setId() {
        SoloGameRound soloGameRound = new SoloGameRound(1L, "answer1");
        soloGameRound.setId(1L);
        assertEquals(1L, soloGameRound.getId());
    }
}