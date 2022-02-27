package server.api;

import commons.AbstractQuestion;
import commons.BasicMultipleChoiceQuestion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.QuestionRepository;

import java.util.Random;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionRepository repo;

    public QuestionController(Random random, QuestionRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AbstractQuestion> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(repo.getById(id));
        }
    }
}
