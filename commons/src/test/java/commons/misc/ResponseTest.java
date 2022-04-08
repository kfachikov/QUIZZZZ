package commons.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameResponseTest {
    GameResponse response;
    GameResponse response2;
    
    @BeforeEach
    void setup() {
        response = new GameResponse(4L, 5, 23, "katya", "answer");
        response2 = new GameResponse(4L, 5, 23, "katya", "answer");

    }

    @Test
    void getTimeSubmitted() {
        assertEquals(5, response.getTimeSubmitted());

    }

    @Test
    void setTimeSubmitted() {
        response.setTimeSubmitted(5.6);
        assertEquals(5.6, response.getTimeSubmitted());

    }

    @Test
    void getRoundNumber() {
        assertEquals(23, response.getRoundNumber());

    }

    @Test
    void setRoundNumber() {
        response.setRoundNumber(7);
        assertEquals(7, response.getRoundNumber());

    }

    @Test
    void getAnswerChoice() {
        assertEquals("answer", response.getAnswerChoice());

    }

    @Test
    void setAnswerChoice() {
        response.setAnswerChoice("another");
        assertEquals("another", response.getAnswerChoice());

    }

    @Test
    void getGameId() {
        assertEquals(4L, response.getGameId());
    }

    @Test
    void setGameId() {
        response.setGameId(5L);
        assertEquals(5L, response.getGameId());

    }

    @Test
    void getPlayerUsername() {
        assertEquals("katya", response.getPlayerUsername());

    }

    @Test
    void testEquals() {
        assertTrue(response.equals(response2));
    }


    @Test
    void testHashCode() {
        assertEquals(response.hashCode(), response2.hashCode());

    }

    @Test
    void testToString() {
        assertEquals("GameResponse{" +
                "gameId=" + response.getGameId() +
                ", timeSubmitted=" + response.getTimeSubmitted() +
                ", roundNumber=" + response.getRoundNumber() +
                ", playerUsername='" + response.getPlayerUsername() + '\'' +
                ", answerChoice='" + response.getAnswerChoice() + '\'' +
                '}', response.toString());
    }
}

