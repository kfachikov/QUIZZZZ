package commons.multi;

import commons.misc.Activity;
import commons.misc.Response;
import commons.question.AbstractQuestion;
import commons.question.GuessQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerStateTest {

    private List<AbstractQuestion> questions;

    private Response response1;
    private Response response2;
    private Response response3;
    private Response response4;

    private List<Response> answers;
    private List<Response> answers2;
    private List<Response> answers3;
    private List<Response> answers4;

    private List<Activity> activities;

    private MultiPlayer player1;
    private MultiPlayer player2;
    private List<MultiPlayer> players;

    private Reaction reaction;

    private MultiPlayerState game;
    private MultiPlayerState game2;

    private MultiPlayerState game3;

    @BeforeEach
    void setup() {
        questions = Arrays.asList(
                new GuessQuestion(
                        new Activity("1", "taking a shower", "source1", "image1", 250L)
                ),
                new GuessQuestion(
                        new Activity("2", "taking a shower", "source2", "imag2", 260L)
                )
        );

        response1 = new Response(37, 5287, 2, "Kate", "200Wh");
        response2 = new Response(37, 5300, 2, "Lu", "500Wh");
        response3 = new Response(37, 5386, 2, "Kate", "500Wh");
        response4 = new Response(37, 5360, 2, "Lu", "200Wh");

        answers = Arrays.asList(response1, response2);
        answers2 = Arrays.asList(response3, response4);
        answers3 = Arrays.asList(response1, response4);
        answers4 = Arrays.asList(response3, response2);

        activities = Arrays.asList(
                new Activity("1", "title1", "source1", "image1", 250L),
                new Activity("2", "title2", "source2", "image2", 260L)
        );

        player1 = new MultiPlayer("Kate", 10, true, true, true);

        player2 = new MultiPlayer("Lu", 10, true, true, true);

        players = Arrays.asList(player1, player2);

        reaction = new Reaction(Arrays.asList("sad", "tired"));

        game = new MultiPlayerState(37, 5500, 2, questions, answers, answers3, "question", players, reaction);
        game2 = new MultiPlayerState(37, 5500, 2, questions, answers, answers3, "question", players, reaction);
        game3 = new MultiPlayerState(37, 5500, 2, questions, answers2, answers4, "question", players, reaction);
    }

    @Test
    public void testConstructor() {
        assertNotNull(game);
    }

    @Test
    public void testGetNextPhase() {
        assertEquals(5500, game.getNextPhase());
    }

    @Test
    public void testGetRoundNumber() {
        assertEquals(2, game.getRoundNumber());
    }

    @Test
    public void testGetQuestionList() {
        assertEquals(questions, game.getQuestionList());
    }

    @Test
    public void testGetSubmittedAnswers() {
        assertEquals(answers, game.getSubmittedAnswers());
    }

    @Test
    public void testGetState() {
        assertEquals("question", game.getState());
    }

    @Test
    public void testGetPlayers() {
        assertEquals(players, game.getPlayers());
    }

    @Test
    public void testGetReaction() {
        assertEquals(reaction, game.getReaction());
    }

    @Test
    public void testGetFinalAnswers() {
        assertEquals(answers3, game.getFinalAnswers());
    }

    @Test
    public void testSetNextPhase() {
        game.setNextPhase(6000);

        assertEquals(6000, game.getNextPhase());
    }

    @Test
    public void testSetRoundNumber() {
        game.setRoundNumber(3);

        assertEquals(3, game.getRoundNumber());
    }

    @Test
    public void testSetQuestionList() {
        List<AbstractQuestion> newQuestions = Arrays.asList(new GuessQuestion(
                new Activity("3", "taking a shower", "source3", "imag3", 270L)),
                new GuessQuestion(
                        new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        game.setQuestionList(newQuestions);

        assertEquals(newQuestions, game.getQuestionList());
    }

    @Test
    public void testSetSubmittedAnswers() {
        List<Response> newAnswers = Arrays.asList(response3, response4);
        game.setSubmittedAnswers(newAnswers);

        assertEquals(newAnswers, game.getSubmittedAnswers());
    }

    @Test
    public void testSetState() {
        game.setState("game over");

        assertEquals("game over", game.getState());
    }

    @Test
    public void testEquals() {
        assertEquals(game, game2);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(game, game3);
    }

    @Test
    public void testHashCode1() {
        assertEquals(game.hashCode(), game2.hashCode());
    }

    @Test
    public void testHashCode2() {
        assertNotEquals(game.hashCode(), game3.hashCode());
    }
}