package server.database;

import commons.MultiPlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiPlayerGameAnswerRepository extends JpaRepository<MultiPlayerGame, Long> {
}