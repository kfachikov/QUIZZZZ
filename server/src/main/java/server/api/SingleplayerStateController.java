package server.api;

import commons.misc.Response;
import commons.single.SinglePlayerState;
import commons.misc.Activity;
import commons.question.AbstractQuestion;
import commons.single.SinglePlayer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;
import server.utils.GenerateQuestionUtils;

import java.util.*;

@RestController
@RequestMapping("/api/solo")
public class SingleplayerStateController {

    private final Map<Long, SinglePlayerState> games;
    private final ActivityRepository repo;
    private Random random;

    /**
     * Constructor for singleplayer state controller.
     * <p>
     * Initializes the list of games inside of the controller.
     */
    public SingleplayerStateController(ActivityRepository repo) {
        this.repo = repo;
        this.games = new HashMap<>();
        random = new Random();
    }

    /**
     * GET mapping for the singleplayer game state.
     * <p>
     * Internally, the state of the game will be updated. That means, it might
     * switch to another state (e. g. from QUESTION_STATE to TRANSITION_STATE),
     * and it might increase the players' scores.
     * <p>
     * There is expectation that this endpoint will be called every about 500 ms.
     *
     * @param id Id for the singleplayer game
     * @return SinglePlayerState after updating internal state.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SinglePlayerState> getGameState(@PathVariable("id") long id) {
        if (games.containsKey(id)) {
            SinglePlayerState game = games.get(id);
            if (updateState(game)) {
                updateScore(game);
            }
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST mapping for starting a new singleplayer game.
     * <p>
     * Constructs a new game object and inserts it in the list of games.
     *
     * @param player SinglePlayer that is starting the game.
     * @return Newly started singleplayer game state
     */
    @PostMapping("/start")
    public ResponseEntity<SinglePlayerState> startSingleGame(@RequestBody SinglePlayer player) {
        SinglePlayerState newGame = createSingleGame(player);
        games.put(newGame.getId(), newGame);
        return ResponseEntity.ok(newGame);
    }

    /**
     * POST mapping for a response from a player.
     * <p>
     * Inserts the new response in the appropriate game for that player.
     *
     * @param response Response that the player selected.
     * @return Response that the player just chose.
     */
    @PostMapping("/answer")
    public ResponseEntity<Response> postResponse(@RequestBody Response response) {
        long gameId = response.getGameId();
        if (!games.containsKey(gameId)) {
            return ResponseEntity.notFound().build();
        }
        SinglePlayerState game = games.get(gameId);
        game.getSubmittedAnswers().add(response);
        return ResponseEntity.ok(response);
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
    private Response computeFinalAnswer(SinglePlayerState game) {
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

//            // Clear the game from any previous answers
            game.getSubmittedAnswers().clear();

            if (game.compareAnswer()) {
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
     * @param response Response of the player with a correct answer.
     * @return Number of points to add to the player's score
     */
    private int computeScore(Response response) {
        return 100;
    }

    /**
     * Checks if the response matches the correct answer of the question.
     *
     * @param response Response to be checked.
     * @param question Question to be compared against.
     * @return Whether the response is correct for the question.
     */
    private boolean checkResponse(Response response, AbstractQuestion question) {
        String submittedAnswer = response.getAnswerChoice();
        String correctAnswer = getCorrectAnswer(question);
        return submittedAnswer.equals(correctAnswer);
    }

    /**
     * Gets the correct answer of a question.
     * <p>
     * TODO: this method is just a mock method and returns "sample answer"
     *
     * @param question Question to get the answer from.
     * @return Correct answer in String form.
     */
    private String getCorrectAnswer(AbstractQuestion question) {
        return "sample answer";
    }

    /**
     * Get the current question of the game.
     * <p>
     * Calculates and returns the current question, based on the current round.
     *
     * @param game Singleplayer game state.
     * @return Current question of the game.
     */
    private AbstractQuestion getCurrentQuestion(SinglePlayerState game) {
        return game.getQuestionList().get(game.getRoundNumber());
    }

    /**
     * Generates the questions for the singleplayer game.
     * <p>
     * TODO: this method is a mock method and returns 20 identical mock questions.
     *
     * @return List of 20 newly generated questions
     */
    private List<AbstractQuestion> generateQuestions() {
        GenerateQuestionUtils generateQuestionUtils = new GenerateQuestionUtils();

        List<AbstractQuestion> questionList = generateQuestionUtils.generate20Questions(random, repo);
        return questionList;
    }

    /**
     * Updates the state of the singleplayer game state based on the time of the
     * next phase.
     *
     * @param game Singleplayer game state to update
     * @return Whether the scores should be updated
     */
    private boolean updateState(SinglePlayerState game) {
        long time = new Date().getTime();

        // Check if we should be changing the state of the game
        if (time >= game.getNextPhase()) {
            if (game.getState().equals(SinglePlayerState.QUESTION_STATE)) {
                game.setState(SinglePlayerState.TRANSITION_STATE);
                game.setNextPhase(game.getNextPhase() + 3000);
                return true;
            } else if (game.getState().equals(SinglePlayerState.TRANSITION_STATE)) {
                if (game.getRoundNumber() >= 20) {
                    game.setState(SinglePlayerState.GAME_OVER_STATE);
                } else {
                    game.setState(SinglePlayerState.QUESTION_STATE);
                    game.setNextPhase(game.getNextPhase() + 8000);
                    game.setRoundNumber(game.getRoundNumber() + 1);
                }
            }
        }
        return false;
    }

    /**
     * Constructs a new singleplayer game state.
     *
     * @param player Player who is playing in the game
     * @return The newly constructed singleplayer game state
     */
    private SinglePlayerState createSingleGame(SinglePlayer player) {
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
        List<AbstractQuestion> questionList = generateQuestions();
        List<Response> submittedAnswers = new ArrayList<>();
        List<Response> finalAnswers = new ArrayList<>();
        List<Activity> activityList = new ArrayList<>();
        String state = SinglePlayerState.QUESTION_STATE;
        return new SinglePlayerState(
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
    }
}
