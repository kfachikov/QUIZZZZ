package commons;

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
    void setUsername1() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);
        singlePlayerLeaderboardScore.setUsername("new1");

        assertEquals("new1", singlePlayerLeaderboardScore.getUsername());

    }

    @Test
    void setUsername2() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);
        singlePlayerLeaderboardScore.setUsername("new2");

        assertEquals("new2", singlePlayerLeaderboardScore.getUsername());

    }

    @Test
    void setUsername3() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);
        singlePlayerLeaderboardScore.setUsername("new3");

        assertEquals("new3", singlePlayerLeaderboardScore.getUsername());

    }

    @Test
    void setUsername4() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);
        singlePlayerLeaderboardScore.setUsername("new4");

        assertEquals("new4", singlePlayerLeaderboardScore.getUsername());

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
    void setScore1() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);
        singlePlayerLeaderboardScore.setScore(600);

        assertEquals(600, singlePlayerLeaderboardScore.getScore());

    }

    @Test
    void setScore2() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);
        singlePlayerLeaderboardScore.setScore(1400);

        assertEquals(1400, singlePlayerLeaderboardScore.getScore());

    }

    @Test
    void setScore3() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);
        singlePlayerLeaderboardScore.setScore(6400);

        assertEquals(6400, singlePlayerLeaderboardScore.getScore());

    }

    @Test
    void setScore4() {

        SinglePlayerLeaderboardScore singlePlayerLeaderboardScore = new SinglePlayerLeaderboardScore("user", 300);
        singlePlayerLeaderboardScore.setScore(407);

        assertEquals(407, singlePlayerLeaderboardScore.getScore());

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

