package server.database;

import commons.single.SinglePlayerLeaderboardScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardRepository extends JpaRepository<SinglePlayerLeaderboardScore, Long> {
}
