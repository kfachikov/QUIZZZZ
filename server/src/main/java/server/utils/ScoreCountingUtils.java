package server.utils;

import commons.misc.GameResponse;
import commons.misc.GameState;
import commons.misc.Player;
import commons.multi.MultiPlayer;
import commons.question.AbstractQuestion;
import commons.question.GuessQuestion;

/**
 * Score counting utility class. Would be used by both game classes, as they share functionality,
 * when it comes to score allocating algorithm.
 */
public class ScoreCountingUtils {

    /**
     * Default constructor for an instance of our score counting utility class.
     */
    public ScoreCountingUtils() {
    }

    /**
     * Compute the score of a response.
     * If question's type is GuessQuestion then the score is computed based on how fast the answer was submitted and how close the player was to the actual answer.
     * If question's type is not GuessQuestion then the score is computed based only on how fast the answer was submitted
     * <p>
     *
     * @param game      Multiplayer game instance to get the questions from.
     * @param player    A Player instance to be checked for "Double Points" joker usage.
     * @param response  GameResponse of the player with a correct answer.
     * @return Number of points to add to the player's score
     */
    public int computeScore(GameState game, Player player, GameResponse response) {
        int points = 0;
        AbstractQuestion currentQuestion = game.getQuestionList()
                .get(game.getRoundNumber());
        /*
        Next variable would calculate the number of seconds remaining when the player
        submitted his response.
         */
        double timeRemaining = (game.getNextPhase() - response.getTimeSubmitted()) / 1000;

        /*
        The two answers to be compared.
         */
        String correctAnswer = currentQuestion.getCorrectAnswer();
        String submittedAnswer = response.getAnswerChoice();

        if (currentQuestion instanceof GuessQuestion) {
            boolean validResponse = true;
            double correctAnswerNumber = Double.parseDouble(correctAnswer);
            double submittedAnswerNumber = -1;
            try {
                submittedAnswerNumber = Double.parseDouble(submittedAnswer);
            } catch (NumberFormatException e) {
                validResponse = false;
            }
            if (validResponse) {
                if (correctAnswerNumber == submittedAnswerNumber) {
                    points = (int) (100 + timeRemaining * 50.0);
                } else if (Math.abs(submittedAnswerNumber - correctAnswerNumber) <= 500) {
                    points = (int) (100 + timeRemaining * 50.0
                            - 0.1 * Math.abs(correctAnswerNumber - submittedAnswerNumber));
                }
            }
        } else if (correctAnswer.equals(submittedAnswer)) {
            /*
            Check in `else if` statement is made, as the method would be called even when answer is
            not the same as the correct one. Only this way, points from guess questions can be allocated
            reasonably.
            */
            points = (int) (100 + timeRemaining * 50.0);
        }

        /*
        Checks whether the player passed as argument is an instance of MultiPlayer.
        In case it is, the scoring utility should make sure that whether the player
        is currently using "Double Points" joker is considered.
         */
        if (player instanceof MultiPlayer) {
            MultiPlayer currentPlayer = (MultiPlayer) player;
            if (currentPlayer.isCurrentlyUsingDoublePoints()) {
                points = points * 2;
                currentPlayer.setCurrentlyUsingDoublePoints(false);

            }
        }

        return points;
    }
}
