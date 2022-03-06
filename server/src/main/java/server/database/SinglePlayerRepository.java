package server.database;

import commons.SinglePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SinglePlayerRepository extends JpaRepository<SinglePlayer, Long> {
}