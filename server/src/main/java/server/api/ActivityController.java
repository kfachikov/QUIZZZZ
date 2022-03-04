package server.api;

import commons.MultiUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;
import server.database.entities.Activity;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityRepository repo;

    public ActivityController(ActivityRepository repo) {
        this.repo = repo;
    }

    @GetMapping("")
    public ResponseEntity<List<Activity>> getAllActivities() {
        return new ResponseEntity<List<Activity>>(repo.findAll(), HttpStatus.OK);
    }
}
