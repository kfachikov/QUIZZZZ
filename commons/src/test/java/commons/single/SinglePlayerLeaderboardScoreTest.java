package commons.single;

import commons.single.SinglePlayerLeaderboardScore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerLeaderboardScoreTest {

    @Test
    void getUsername1() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);

        assertEquals("user", singlePlayerLeaderboardScore.getUsername());

    }

    @Test
    void getUsername2() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user2", 300);

        assertEquals("user2", singlePlayerLeaderboardScore.getUsername());

    }

    @Test
    void getUsername3() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user3", 300);

        assertEquals("user3", singlePlayerLeaderboardScore.getUsername());

    }

    @Test
    void getUsername4() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user4", 300);

        assertEquals("user4", singlePlayerLeaderboardScore.getUsername());

    }



    @Test
    void getScore1() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);

        assertEquals(300, singlePlayerLeaderboardScore.getScore());

    }

    @Test
    void getScore2() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 400);

        assertEquals(400, singlePlayerLeaderboardScore.getScore());

    }

    @Test
    void getScore3() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 500);

        assertEquals(500, singlePlayerLeaderboardScore.getScore());

    }

    @Test
    void getScore4() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 600);

        assertEquals(600, singlePlayerLeaderboardScore.getScore());

    }


    @Test
    void testHashCode() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore1 = new SinglePlayerLeaderboardScore("user", 300);

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore2 = new SinglePlayerLeaderboardScore("user", 300);
        assertEquals(singlePlayerLeaderboardScore1.hashCode(), singlePlayerLeaderboardScore2.hashCode());

    }

    @Test
    void testToString() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore1 = new SinglePlayerLeaderboardScore("user", 300);

        assertEquals("SinglePlayerLeaderboardScore{" +
                "id=" + singlePlayerLeaderboardScore1.getId() +
                ", username='" + singlePlayerLeaderboardScore1.getUsername() + '\'' +
                ", score=" + singlePlayerLeaderboardScore1.getScore() +
                '}', singlePlayerLeaderboardScore1.toString());

    }
}

