package server.utils;

import commons.misc.Activity;
import commons.misc.Response;
import commons.question.AbstractQuestion;
import commons.single.SinglePlayer;
import commons.single.SinglePlayerState;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import server.database.ActivityRepository;

import java.util.*;

/**
 * Utility class providing functionality for the single-player game mode.
 */
@Component
@ComponentScan(basePackageClasses = GenerateQuestionUtils.class)
public class SinglePlayerStateUtils {

    private final Map<Long, SinglePlayerState> games;
    private final GenerateQuestionUtils generateQuestionUtils;

    public SinglePlayerStateUtils(GenerateQuestionUtils generateQuestionUtils) {
        this.generateQuestionUtils = generateQuestionUtils;
        games = new HashMap<>();
    }

    /**
     * Get particular game state instance by its key in the games map.
     * Update its state if required.
     *
     * @param id Key value to search for.
     * @return SinglePlayerState instance in case it exists, null otherwise.
     */
    public SinglePlayerState getGameStateById(long id) {
        if (games.containsKey(id)) {
            SinglePlayerState game = games.get(id);
            updateState(game);
            return game;
        } else {
            return null;
        }
    }

    /**
     * Posts an answer in the current game - particular instance found by gameId stored in the
     * Response object sent.
     *
     * @param response Response sent from client.
     * @return Response instance in case the gameId is valid, or null otherwise.
     */
    public Response postAnswer(Response response) {
        long gameId = response.getGameId();
        if (!games.containsKey(gameId)) {
            return null;
        }
        games.get(gameId).getSubmittedAnswers().add(response);
        return response;
    }

    /**
     * Updates the state of the single-player game state based on the time of the
     * next phase.
     *
     * @param game SinglePlayer game state to update
     */
    public void updateState(SinglePlayerState game) {
        long time = new Date().getTime();

        // Check if we should be changing the state of the game
        if (time >= game.getNextPhase()) {
            if (game.getState().equals(SinglePlayerState.QUESTION_STATE)) {
                game.setState(SinglePlayerState.TRANSITION_STATE);
                game.setNextPhase(game.getNextPhase() + 3000);
                updateScore(game);
            } else if (game.getState().equals(SinglePlayerState.TRANSITION_STATE)) {
                /*
                Should be 19, as that would mean that the 19th round is just over.
                 */
                if (game.getRoundNumber() >= 19) {
                    game.setState(SinglePlayerState.GAME_OVER_STATE);
                } else {
                    game.setState(SinglePlayerState.QUESTION_STATE);
                    game.setNextPhase(game.getNextPhase() + 8000);
                    game.setRoundNumber(game.getRoundNumber() + 1);
                }
            }
        }
    }

    /**
     * Update the score of the player and clear responses.
     * <p>
     * This method runs only in the transition state.
     * <p>
     * All responses from the player are aggregated (to determine the true response)
     * and then cleared.
     * <p>
     * Finally, the player's score is updated based on their response (the timing,
     * whether it is correct etc)
     *
     * @param game Singleplayer game state (in the transition state)
     */
    private void updateScore(SinglePlayerState game) {
        if (game.getState().equals(SinglePlayerState.TRANSITION_STATE)) {
            Response playerResponse = computeFinalAnswer(game);
            /*
            Saves the latest response of the player in the list of answers submitted as final.
             */
            game.getFinalAnswers().add(playerResponse);

            /*
            Clear the game from any previous answers.
             */
            game.getSubmittedAnswers().clear();

            /*
            Use shared comparing functionality implemented in the class
            SinglePlayerState.
             */
            if (game.compareAnswer()) {
                /*
                If the answer submitted is the same, then the score is updated accordingly.
                 */
                SinglePlayer player = game.getPlayer();
                player.setScore(player.getScore() + computeScore(playerResponse));
            }
        }
    }

    /**
     * Compute the score of a response.
     * <p>
     * TODO: this method is just a mock method, and returns 100.
     *
     * @param response Response of the player with a correct answer. TODO: The time submitted to be used for computations.
     * @return Number of points to add to the player's score
     */
    private int computeScore(Response response) {
        return 100;
    }


    /**
     * Compute the final answer that the player chose.
     * <p>
     * The set of responses is iterated over, with the last answer choice being
     * returned. If the last answer choice is selected multiple times, the first
     * instance in the suffix of the answer choices is returned.
     *
     * @param game Singleplayer game for which the answer is computed.
     * @return The true response of the player.
     */
    public Response computeFinalAnswer(SinglePlayerState game) {
        // Dummy response, if the player did not choose anything
        Response playerResponse = new Response(
                game.getId(),
                Long.MAX_VALUE,
                game.getRoundNumber(),
                game.getPlayer().getUsername(),
                "wrong answer"
        );
        // Responses are sorted by the submission time.
        Comparator<Response> comp =
                (a, b) -> (int) (a.getTimeSubmitted() - b.getTimeSubmitted());
        game.getSubmittedAnswers().sort(comp);
        for (Response response : game.getSubmittedAnswers()) {
            // Only update the response if it differs
            // This is done to avoid punishing the player from clicking
            // the same response multiple times
            if (!playerResponse.getAnswerChoice().equals(response.getAnswerChoice())) {
                playerResponse = response;
            }
        }
        return playerResponse;
    }

    /**
     * Constructs a new single-player game state.
     *
     * @param player Player who is playing in the game
     * @param repo   ActivityRepository instance.
     * @return The newly constructed SinglePlayer game state
     */
    public SinglePlayerState createSingleGame(SinglePlayer player,
                                              ActivityRepository repo) {
        Set<Long> keys = games.keySet();
        long maxKey;
        if (keys.isEmpty()) {
            maxKey = -1;
        } else {
            maxKey = Collections.max(keys);
        }
        long id = maxKey + 1;
        long nextTransition = new Date().getTime() + 8000;
        int roundNumber = 0;
        List<AbstractQuestion> questionList = generateQuestionUtils.generate20Questions(repo);
        List<Response> submittedAnswers = new ArrayList<>();
        List<Response> finalAnswers = new ArrayList<>();
        List<Activity> activityList = new ArrayList<>();
        String state = SinglePlayerState.QUESTION_STATE;

        SinglePlayerState newGame = new SinglePlayerState(
                id,
                nextTransition,
                roundNumber,
                questionList,
                finalAnswers,
                submittedAnswers,
                activityList,
                state,
                player
        );
        games.put(newGame.getId(), newGame);
        return newGame;
    }
}
