package server.database;

import commons.SoloGameRound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoloGameAnswerRepository extends JpaRepository<SoloGameRound, Long> {
}