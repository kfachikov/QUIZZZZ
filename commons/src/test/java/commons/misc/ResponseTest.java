package commons.misc;

import commons.misc.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    @Test
    void getTimeSubmitted() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        assertEquals(5, response.getTimeSubmitted());

    }

    @Test
    void setTimeSubmitted() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        response.setTimeSubmitted(5.6);
        assertEquals(5.6, response.getTimeSubmitted());

    }

    @Test
    void getRoundNumber() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        assertEquals(23, response.getRoundNumber());

    }

    @Test
    void setRoundNumber() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        response.setRoundNumber(7);
        assertEquals(7, response.getRoundNumber());

    }

    @Test
    void getAnswerChoice() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        assertEquals("answer", response.getAnswerChoice());

    }

    @Test
    void setAnswerChoice() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        response.setAnswerChoice("another");
        assertEquals("another", response.getAnswerChoice());

    }

    @Test
    void getGameId() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        assertEquals(4L, response.getGameId());
    }

    @Test
    void setGameId() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        response.setGameId(5L);
        assertEquals(5L, response.getGameId());

    }

    @Test
    void getPlayerUsername() {

        Response response = new Response(4L, 5, 23, "katya", "answer");
        assertEquals("katya", response.getPlayerUsername());

    }

    @Test
    void testEquals() {

        Response response1 = new Response(4L, 5, 23, "katya", "answer");
        Response response2 = new Response(4L, 5, 23, "katya", "answer");
        assertTrue(response1.equals(response2));

    }


    @Test
    void testHashCode() {

        Response response1 = new Response(4L, 5, 23, "katya", "answer");
        Response response2 = new Response(4L, 5, 23, "katya", "answer");
        assertEquals(response1.hashCode(), response2.hashCode());

    }

    @Test
    void testToString() {
        Response response1 = new Response(4L, 5, 23, "katya", "answer");
        assertEquals("Response{" +
                "gameId=" + response1.getGameId() +
                ", timeSubmitted=" + response1.getTimeSubmitted() +
                ", roundNumber=" + response1.getRoundNumber() +
                ", playerUsername='" + response1.getPlayerUsername() + '\'' +
                ", answerChoice='" + response1.getAnswerChoice() + '\'' +
                '}', response1.toString());
    }
}

