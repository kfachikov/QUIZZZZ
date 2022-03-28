package commons.single;

import commons.misc.Activity;
import commons.misc.GameResponse;
import commons.question.AbstractQuestion;
import commons.question.GuessQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerStateTest {

    private List<AbstractQuestion> questions;

    private GameResponse gameResponse1;
    private GameResponse gameResponse2;
    private GameResponse gameResponse3;
    private GameResponse gameResponse4;

    private List<GameResponse> finalAnswers;
    private List<GameResponse> finalAnswers2;

    private List<GameResponse> answers;
    private List<GameResponse> answers2;

    private List<Activity> activities;

    private SinglePlayer player1;
    private SinglePlayer player2;

    private SinglePlayerState game;
    private SinglePlayerState game2;

    private SinglePlayerState game3;

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

        gameResponse1 = new GameResponse(37, 5287, 2, "Kate", "200Wh");
        gameResponse2 = new GameResponse(37, 5300, 2, "Lu", "500Wh");
        gameResponse3 = new GameResponse(37, 5386, 2, "Kate", "500Wh");
        gameResponse4 = new GameResponse(37, 5360, 2, "Lu", "200Wh");

        answers = Arrays.asList(gameResponse1, gameResponse3);
        answers2 = Arrays.asList(gameResponse2, gameResponse4);

        finalAnswers = Arrays.asList(gameResponse3);
        finalAnswers2 = Arrays.asList(gameResponse4);

        activities = Arrays.asList(
                new Activity("1", "title1", "source1", "image1", 250L),
                new Activity("2", "title2", "source2", "image2", 260L)
        );

        player1 = new SinglePlayer("Kate", 10);

        player2 = new SinglePlayer("Lu", 10);

        game = new SinglePlayerState(37, 5500, 2, questions, finalAnswers, answers, "question", player1);
        game2 = new SinglePlayerState(37, 5500, 2, questions, finalAnswers, answers, "question", player1);
        game3 = new SinglePlayerState(37, 5500, 2, questions, finalAnswers2, answers2, "question", player2);
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
    public void testGetPlayer() {
        assertEquals(new SinglePlayer("Kate", 10), game.getPlayer());
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
        List<AbstractQuestion> newQuestions =
                Arrays.asList(
                        new GuessQuestion(
                                new Activity("3", "taking a shower", "source3", "imag3", 270L)),
                        new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        game.setQuestionList(newQuestions);

        assertEquals(newQuestions, game.getQuestionList());
    }

    @Test
    public void testSetSubmittedAnswers() {
        List<GameResponse> newAnswers = Arrays.asList(gameResponse3);
        game.setSubmittedAnswers(newAnswers);

        assertEquals(newAnswers, game.getSubmittedAnswers());
    }

    @Test
    public void testSetState() {
        game.setState("game over");

        assertEquals("game over", game.getState());
    }

    @Test
    public void testSetPlayer() {
        game.setPlayer(new SinglePlayer("Mike", 10));

        assertEquals(new SinglePlayer("Mike", 10), game.getPlayer());
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