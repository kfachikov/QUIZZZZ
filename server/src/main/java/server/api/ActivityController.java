package server.api;

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

    @PostMapping("")
    public ResponseEntity<Activity> add(@RequestBody Activity activity) {
        if (activity == null ||
                isNullOrEmpty(activity.getId()) ||
                isNullOrEmpty(activity.getTitle()) ||
                isNullOrEmpty(activity.getSource()) ||
                isNullOrEmpty(activity.getImage()) ||
                isNullOrEmpty(activity.getConsumption())) {
            return ResponseEntity.badRequest().build();
        }
        Activity saved = repo.save(activity);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
