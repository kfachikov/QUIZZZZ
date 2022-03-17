package server.api;

import commons.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.utils.GenerateQuestionUtils;

import java.util.*;

@RestController
@RequestMapping("/api/solo")
public class SingleplayerStateController {

    private final Map<Long, SinglePlayerState> games;

    public SingleplayerStateController() {
        this.games = new HashMap<>();
    }

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

    @PostMapping("/start")
    public ResponseEntity<SinglePlayerState> startSingleGame(@RequestBody SinglePlayer player) {
        SinglePlayerState newGame = createSingleGame(player);
        games.put(newGame.getId(), newGame);
        return ResponseEntity.ok(newGame);
    }

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

    private Response computeFinalAnswer(SinglePlayerState game) {
        Response playerResponse = new Response(
                game.getId(),
                Long.MAX_VALUE,
                game.getRoundNumber(),
                game.getPlayer().getUsername(),
                "wrong answer"
        );
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

    private void updateScore(SinglePlayerState game) {
        if (game.getState().equals(SinglePlayerState.TRANSITION_STATE)) {
            Response playerResponse = computeFinalAnswer(game);
            // Clear the game from any previous answers
            game.getSubmittedAnswers().clear();

            AbstractQuestion currentQuestion = getCurrentQuestion(game);
            if (checkResponse(playerResponse, currentQuestion)) {
                SinglePlayer player = game.getPlayer();
                player.setScore(player.getScore() + computeScore());
            }
        }
    }

    private long computeScore() {
        return 100;
    }

    private boolean checkResponse(Response response, AbstractQuestion question) {
        String submittedAnswer = response.getAnswerChoice();
        String correctAnswer = getCorrectAnswer(question);
        return submittedAnswer.equals(correctAnswer);
    }

    private String getCorrectAnswer(AbstractQuestion question) {
        return "sample answer";
    }

    private AbstractQuestion getCurrentQuestion(SinglePlayerState game) {
        return game.getQuestionList().get(game.getRoundNumber());
    }

    private List<AbstractQuestion> generateQuestions() {
        List<AbstractQuestion> questions = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ActivityConsumptionQuestionType acqt = new ActivityConsumptionQuestionType("1 12 121", "mockup", 12);
            acqt.answerChoices = List.of("1", "12", "121");
            questions.add(acqt);
        }
        return questions;
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
                if (game.getRoundNumber() >= 20) {
                    game.setState(SinglePlayerState.GAME_OVER_STATE);
                } else {
                    game.setState(SinglePlayerState.TRANSITION_STATE);
                    game.setNextPhase(game.getNextPhase() + 3000);
                    return true;
                }
            }
        } else if (game.getState().equals(SinglePlayerState.TRANSITION_STATE)) {
            game.setState(SinglePlayerState.QUESTION_STATE);
            game.setNextPhase(game.getNextPhase() + 8000);
            game.setRoundNumber(game.getRoundNumber() + 1);
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
        List<Activity> activityList = new ArrayList<>();
        String state = SinglePlayerState.QUESTION_STATE;
        return new SinglePlayerState(
                id,
                nextTransition,
                roundNumber,
                questionList,
                submittedAnswers,
                activityList,
                state,
                player
        );
    }
}
