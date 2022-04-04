package commons.multi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import commons.misc.GameResponse;
import commons.misc.GameState;
import commons.question.AbstractQuestion;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * Child class extending GameState. Handles the state of every single multiplayer game started.
 */
@JsonTypeName(value = "multi")
public class MultiPlayerState extends GameState {

    public static final String NOT_STARTED_STATE = "NOT_STARTED";
    public static final String STARTING_STATE = "STARTING";
    public static final String QUESTION_STATE = "QUESTION";
    public static final String GAME_OVER_STATE = "GAME_OVER";
    public static final String TRANSITION_STATE = "TRANSITION";
    public static final String LEADERBOARD_STATE = "LEADERBOARD";

    private List<MultiPlayer> players;
    private List<ChatMessage> chatMessageList;
    private MultiPlayer playerUsingTimeJoker;

    /*
    Field to be used to count how many count attack have been used
    in each separate round.
     */
    private int timeAttacksUsed;

    /**
     * Default constructor to be used for the JSON parsing.
     */
    public MultiPlayerState() {

    }


    /**
     * Constructor for the state of the multiplayer game.
     *
     * @param id               the id of the game.
     * @param nextPhase        the time of the next phase.
     * @param roundNumber      the round number of the game.
     * @param questionList     the list of question for a game.
     * @param submittedAnswers the answers submitted by players during game in a single round.
     * @param state            the status of the game.
     * @param players          the list of players currently in the game.
     * @param chatMessageList     the list of messages sent in a game.
     */
    public MultiPlayerState(long id, long nextPhase, int roundNumber,
                            List<AbstractQuestion> questionList,
                            List<GameResponse> submittedAnswers,
                            String state,
                            List<MultiPlayer> players,
                            List<ChatMessage> chatMessageList) {
        super(id, nextPhase, roundNumber, questionList, submittedAnswers, state);
        this.chatMessageList = chatMessageList;
        this.players = players;
        this.playerUsingTimeJoker = null;
    }

    /**
     * Getter for the players currently in the game.
     *
     * @return a list of MultiPlayer representing the players currently in the game.
     */
    public List<MultiPlayer> getPlayers() {
        return players;
    }

    /**
     * Getter for the messages.
     *
     * @return a ChatMessage representing the messages sent used in a game.
     */
    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }

    /**
     * Setter for the players currently in the game.
     *
     * @param players the list of players currently in the game.
     */
    public void setPlayers(List<MultiPlayer> players) {
        this.players = players;
    }

    /**
     * Getter for a particular multiplayer using his unique identifier in that particular
     * multiplayer game instance - his username.
     *
     * @param username  Player's username to search for.
     * @return          MultiPlayer instance of the player with a particular username.
     */
    public MultiPlayer getPlayerByUsername(String username) {
        for (MultiPlayer player: players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Comparing answer function making use of the abstract functionality declared
     * in the parent abstract class of the different question types.
     *
     * @param response the response of the player.
     * @return Boolean value corresponding to the correctness of the answer.
     */
    public boolean compareAnswer(GameResponse response) {
        if (response == null) {
            return false;
        }
        String chosenAnswer = response.getAnswerChoice();
        String rightAnswer = getQuestionList().get(getRoundNumber()).getCorrectAnswer();
        return chosenAnswer.equals(rightAnswer);
    }

    /**
     * Comparing answer function making use of the abstract functionality declared
     * in the parent abstract class of the different question types.
     *
     * Used to compare the last answer a player clicked (marked as "final"), and the
     * actual answer, so that the client-side can react accordingly to the correctness of
     * the answer.
     *
     * @param lastSubmittedAnswer   The last answer a player had submitted before the moment
     *                              of a transition screen being shown.
     * @return Boolean value corresponding to the correctness of the answer.
     */
    public boolean compareAnswerClient(String lastSubmittedAnswer) {
        if (lastSubmittedAnswer == null) {
            return false;
        }
        String rightAnswer = getQuestionList().get(getRoundNumber()).getCorrectAnswer();
        return lastSubmittedAnswer.equals(rightAnswer);
    }

    /**
     * Setter for the messages.
     *
     * @param newChatMessageList the messages sent in a game.
     */
    public void setChatMessageList(List<ChatMessage> newChatMessageList) {
        this.chatMessageList = newChatMessageList;
    }

    /**
     * Getter for the player who used the time joker.
     *
     * @return the player who used the joker.
     */
    public MultiPlayer getPlayerUsingTimeJoker() {
        return playerUsingTimeJoker;
    }

    /**
     * Setter for the player who used the time joker.
     *
     * @param playerUsingTimeJoker the player who used the joker.
     */
    public void setPlayerUsingTimeJoker(MultiPlayer playerUsingTimeJoker) {
        this.playerUsingTimeJoker = playerUsingTimeJoker;
    }

    /**
     * Getter for the number of "Time Attack" jokers used in a single round.
     *
     * @return  Integer of the number of such jokers being used.
     */
    public int getTimeAttacksUsed() {
        return timeAttacksUsed;
    }

    /**
     *  Setter for the number of "Time Attack" jokers being used.
     *  To be used at the beginning of each round to set the number to 0.
     *
     * @param timeAttacksUsed   Value to be set.
     */
    public void setTimeAttacksUsed(int timeAttacksUsed) {
        this.timeAttacksUsed = timeAttacksUsed;
    }

    /**
     * Increments the number of "Time Attacks" jokers being used in the current moment.
     */
    public void incrementTimeAttacksUsed() {
        this.timeAttacksUsed ++;
    }

    /**
     * Compares two entities.
     *
     * @param o the instance that is compared to.
     * @return true if the two entities are equal, otherwise it will be returned false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MultiPlayerState that = (MultiPlayerState) o;
        return Objects.equals(players, that.players) && Objects.equals(chatMessageList, that.chatMessageList);
    }

    /**
     * Computes the object's hash code.
     *
     * @return the object's hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), players, chatMessageList);
    }

    /**
     * Multiline string representation of the multiplayer game state.
     * <p>
     * Automatically generated using ToStringBuilder.
     *
     * @return Multiline string of the multiplayer game state.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
