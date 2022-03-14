package server.database;

import commons.MultiPlayerGameRound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiPlayerGameAnswerRepository extends JpaRepository<MultiPlayerGameRound, Long> {
}