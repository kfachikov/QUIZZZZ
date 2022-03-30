package server.utils;

import commons.misc.GameResponse;
import commons.misc.GameState;
import commons.multi.MultiPlayerState;
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
     * @param response  GameResponse of the player with a correct answer.
     * @return Number of points to add to the player's score
     */
    public int computeScore(GameState game, GameResponse response) {
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
                } else if (correctAnswerNumber < submittedAnswerNumber
                        && submittedAnswerNumber - correctAnswerNumber <= 500) {
                    points = (int) (100 + timeRemaining * 50.0
                            - 0.1 * (submittedAnswerNumber - correctAnswerNumber));
                } else if (correctAnswerNumber > submittedAnswerNumber
                        && correctAnswerNumber - submittedAnswerNumber <= 500) {
                    points = (int) (100 + timeRemaining * 50.0
                            - 0.1 * (correctAnswerNumber - submittedAnswerNumber));
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
        return points;
    }
}
