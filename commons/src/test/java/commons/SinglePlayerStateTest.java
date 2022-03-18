package commons;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerStateTest {

    @Test
    public void testConstructor() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        assertNotNull(game);
    }

    @Test
    public void testGetTimeLeft() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        assertEquals(8, game.getTimeLeft());
    }

    @Test
    public void testGetRoundNumber() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        assertEquals(2, game.getRoundNumber());
    }

    @Test
    public void testGetQuestionList() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        assertEquals(questions, game.getQuestionList());
    }

    @Test
    public void testGetSubmittedAnswers() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        assertEquals(answers, game.getSubmittedAnswers());
    }

    @Test
    public void testGetActivityList() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        assertEquals(activities, game.getActivityList());
    }

    @Test
    public void testGetState() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        assertEquals("question", game.getState());
    }

    @Test
    public void testGetPlayer() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        SinglePlayerState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        assertEquals(new SinglePlayer("Kate", 10), game.getPlayer());
    }

    @Test
    public void testSetTimeLeft() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));
        game.setTimeLeft(9);

        assertEquals(9, game.getTimeLeft());
    }

    @Test
    public void testSetRoundNumber() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));
        game.setRoundNumber(3);

        assertEquals(3, game.getRoundNumber());
    }

    @Test
    public void testSetQuestionList() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        List<AbstractQuestion> newQuestions = Arrays.asList(new GuessQuestion(new Activity("3", "taking a shower", "source3", "imag3", 270L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        game.setQuestionList(newQuestions);

        assertEquals(newQuestions, game.getQuestionList());
    }

    @Test
    public void testSetSubmittedAnswers() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        List<String> newAnswers = Arrays.asList("a", "b");
        game.setSubmittedAnswers(newAnswers);

        assertEquals(newAnswers, game.getSubmittedAnswers());
    }

    @Test
    public void testSetState() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));

        game.setState("game over");

        assertEquals("game over", game.getState());
    }

    @Test
    public void testSetPlayer() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        SinglePlayerState game = new SinglePlayerState(8, 2, questions, answers, activities, "question", new SinglePlayer("Kate", 10));
        game.setPlayer(new SinglePlayer("Mike", 10));

        assertEquals(new SinglePlayer("Mike", 10), game.getPlayer());
    }

    @Test
    public void testEquals() {
        List<AbstractQuestion> questions1 = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers1 = Arrays.asList("p", "q");
        List<Activity> activities1 = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game1 = new SinglePlayerState(8, 2, questions1, answers1, activities1, "question", new SinglePlayer("Kate", 10));
        GameState game2 = new SinglePlayerState(8, 2, questions1, answers1, activities1, "question", new SinglePlayer("Kate", 10));

        assertEquals(game1, game2);
    }

    @Test
    public void testNotEquals() {
        List<AbstractQuestion> questions1 = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers1 = Arrays.asList("p", "q");
        List<Activity> activities1 = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game1 = new SinglePlayerState(8, 2, questions1, answers1, activities1, "question", new SinglePlayer("Kate", 10));
        GameState game2 = new SinglePlayerState(8, 2, questions1, answers1, activities1, "question", new SinglePlayer("Mike", 10));

        assertNotEquals(game1, game2);
    }

    @Test
    public void testHashCode1() {
        List<AbstractQuestion> questions1 = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers1 = Arrays.asList("p", "q");
        List<Activity> activities1 = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game1 = new SinglePlayerState(8, 2, questions1, answers1, activities1, "question", new SinglePlayer("Kate", 10));
        GameState game2 = new SinglePlayerState(8, 2, questions1, answers1, activities1, "question", new SinglePlayer("Kate", 10));

        assertEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    public void testHashCode2() {
        List<AbstractQuestion> questions1 = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers1 = Arrays.asList("p", "q");
        List<Activity> activities1 = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        GameState game1 = new SinglePlayerState(8, 2, questions1, answers1, activities1, "question", new SinglePlayer("Kate", 10));
        GameState game2 = new SinglePlayerState(8, 2, questions1, answers1, activities1, "question", new SinglePlayer("Mike", 10));

        assertNotEquals(game1.hashCode(), game2.hashCode());
    }


}