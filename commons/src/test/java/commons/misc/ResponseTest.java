package commons.misc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameResponseTest {

    @Test
    void getTimeSubmitted() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        assertEquals(5, response.getTimeSubmitted());

    }

    @Test
    void setTimeSubmitted() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        response.setTimeSubmitted(5.6);
        assertEquals(5.6, response.getTimeSubmitted());

    }

    @Test
    void getRoundNumber() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        assertEquals(23, response.getRoundNumber());

    }

    @Test
    void setRoundNumber() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        response.setRoundNumber(7);
        assertEquals(7, response.getRoundNumber());

    }

    @Test
    void getAnswerChoice() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        assertEquals("answer", response.getAnswerChoice());

    }

    @Test
    void setAnswerChoice() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        response.setAnswerChoice("another");
        assertEquals("another", response.getAnswerChoice());

    }

    @Test
    void getGameId() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        assertEquals(4L, response.getGameId());
    }

    @Test
    void setGameId() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        response.setGameId(5L);
        assertEquals(5L, response.getGameId());

    }

    @Test
    void getPlayerUsername() {

        GameResponse response = new GameResponse(4L, 5, 23, "katya", "answer");
        assertEquals("katya", response.getPlayerUsername());

    }

    @Test
    void testEquals() {

        GameResponse response1 = new GameResponse(4L, 5, 23, "katya", "answer");
        GameResponse response2 = new GameResponse(4L, 5, 23, "katya", "answer");
        assertTrue(response1.equals(response2));

    }


    @Test
    void testHashCode() {

        GameResponse response1 = new GameResponse(4L, 5, 23, "katya", "answer");
        GameResponse response2 = new GameResponse(4L, 5, 23, "katya", "answer");
        assertEquals(response1.hashCode(), response2.hashCode());

    }

    @Test
    void testToString() {
        GameResponse response1 = new GameResponse(4L, 5, 23, "katya", "answer");
        assertEquals("GameResponse{" +
                "gameId=" + response1.getGameId() +
                ", timeSubmitted=" + response1.getTimeSubmitted() +
                ", roundNumber=" + response1.getRoundNumber() +
                ", playerUsername='" + response1.getPlayerUsername() + '\'' +
                ", answerChoice='" + response1.getAnswerChoice() + '\'' +
                '}', response1.toString());
    }
}

