package server.database;

import commons.AbstractQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<AbstractQuestion, Long> {}
