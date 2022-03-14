package server.database;

import commons.SoloGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoloGameAnswerRepository extends JpaRepository<SoloGame, Long> {
}