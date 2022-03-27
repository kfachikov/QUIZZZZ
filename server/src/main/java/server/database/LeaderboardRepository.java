package server.database;

import commons.single.SinglePlayerLeaderboardScore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Leaderboard Repository for the leaderboard entries.
 */
public interface LeaderboardRepository extends JpaRepository<SinglePlayerLeaderboardScore, Long> {
}
