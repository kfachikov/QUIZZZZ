package commons.multi;

import commons.misc.Activity;
import commons.misc.GameState;
import commons.question.AbstractQuestion;
import commons.question.GuessQuestion;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerStateTest {


    @Test
    public void testConstructor() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertNotNull(game);
    }

    @Test
    public void testGetTimeLeft() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(8, game.getTimeLeft());
    }

    @Test
    public void testGetRoundNumber() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(2, game.getRoundNumber());
    }

    @Test
    public void testGetQuestionList() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(questions, game.getQuestionList());
    }

    @Test
    public void testGetSubmittedAnswers() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(answers, game.getSubmittedAnswers());
    }

    @Test
    public void testGetActivityList() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(activities, game.getActivityList());
    }

    @Test
    public void testGetState() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals("question", game.getState());
    }

    @Test
    public void testGetPlayers() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        MultiPlayerState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(players, game.getPlayers());
    }



    @Test
    public void testGetReaction() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        MultiPlayerState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(reaction, game.getReaction());
    }

    @Test
    public void testSetTimeLeft() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);
        game.setTimeLeft(9);

        assertEquals(9, game.getTimeLeft());
    }

    @Test
    public void testSetRoundNumber() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);
        game.setRoundNumber(3);

        assertEquals(3, game.getRoundNumber());
    }

    @Test
    public void testSetQuestionList() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        List<AbstractQuestion> newQuestions = Arrays.asList(new GuessQuestion(new Activity("3", "taking a shower", "source3", "imag3", 270L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        game.setQuestionList(newQuestions);

        assertEquals(newQuestions, game.getQuestionList());
    }

    @Test
    public void testSetSubmittedAnswers() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        List<String> newAnswers = Arrays.asList("a", "b");
        game.setSubmittedAnswers(newAnswers);

        assertEquals(newAnswers, game.getSubmittedAnswers());
    }

    @Test
    public void testSetState() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        game.setState("game over");

        assertEquals("game over", game.getState());
    }

    @Test
    public void testEquals() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game1 = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);
        GameState game2 = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(game1, game2);
    }

    @Test
    public void testNotEquals() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers1 = Arrays.asList("p", "q");
        List<String> answers2 = Arrays.asList("a", "b");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game1 = new MultiPlayerState(8, 2, questions, answers1, activities, "question", players, reaction);
        GameState game2 = new MultiPlayerState(8, 2, questions, answers2, activities, "question", players, reaction);


        assertNotEquals(game1, game2);
    }

    @Test
    public void testHashCode1() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers = Arrays.asList("p", "q");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game1 = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);
        GameState game2 = new MultiPlayerState(8, 2, questions, answers, activities, "question", players, reaction);

        assertEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    public void testHashCode2() {
        List<AbstractQuestion> questions = Arrays.asList(new GuessQuestion(new Activity("1", "taking a shower", "source1", "image1", 250L)), new GuessQuestion(new Activity("2", "taking a shower", "source2", "imag2", 260L)));
        List<String> answers1 = Arrays.asList("p", "q");
        List<String> answers2 = Arrays.asList("a", "b");
        List<Activity> activities = Arrays.asList(new Activity("1", "title1", "source1", "image1", 250L), new Activity("2", "title2", "source2", "image2", 260L));
        MultiPlayer player1 = new MultiPlayer("Kate", 10, true, true, true);
        MultiPlayer player2 = new MultiPlayer("Lu", 10, true, true, true);
        List<MultiPlayer> players = Arrays.asList(player1, player2);
        Reaction reaction = new Reaction(Arrays.asList("sad", "tired"));
        GameState game1 = new MultiPlayerState(8, 2, questions, answers1, activities, "question", players, reaction);
        GameState game2 = new MultiPlayerState(8, 2, questions, answers2, activities, "question", players, reaction);

        assertNotEquals(game1.hashCode(), game2.hashCode());
    }


}