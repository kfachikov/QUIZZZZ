package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.ActivityRepository;
import server.database.entities.Activity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

class ActivityControllerTest {

    private ActivityRepository repo;
    private ActivityController ctrl;
    private Activity initialActivity;

    @BeforeEach
    public void setup() {
        repo = new TestActivityRepository();
        ctrl = new ActivityController(repo);

        initialActivity = createActivity("id", "title", "source", "image", 100L);
    }

    private Activity createActivity(String id, String title, String source, String image, Long consumption) {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setTitle(title);
        activity.setSource(source);
        activity.setImage(image);
        activity.setConsumption(consumption);

        return activity;
    }

    @Test
    public void testAddActivityNull() {
        var result = ctrl.addActivity(null);
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testAddActivityNullId() {
        initialActivity.setId(null);
        var result = ctrl.addActivity(initialActivity);
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testAddActivityNullTitle() {
        initialActivity.setTitle(null);
        var result = ctrl.addActivity(initialActivity);
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testAddActivityNullSource() {
        initialActivity.setSource(null);
        var result = ctrl.addActivity(initialActivity);
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testAddActivityNullImage() {
        initialActivity.setImage(null);
        var result = ctrl.addActivity(initialActivity);
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testAddActivityNegativeConsumption() {
        initialActivity.setConsumption(-1L);
        var result = ctrl.addActivity(initialActivity);
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testAddActivity() {
        var result = ctrl.addActivity(initialActivity);
        assertEquals(OK, result.getStatusCode());
    }

    @Test
    public void testAddActivityKey() {
        ctrl.addActivity(initialActivity);
        assertEquals(initialActivity, repo.getById(0L));
    }

    @Test
    public void testRemoveActivityNotPresent() {
        ctrl.addActivity(initialActivity);
        var result = ctrl.removeActivity(1L);
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testRemoveActivityPresent() {
        ctrl.addActivity(initialActivity);
        assertEquals(initialActivity, ctrl.removeActivity(0L).getBody());
    }

    @Test
    public void testGetAllActivities() {
        ctrl.addActivity(initialActivity);
        Activity activity = createActivity("newId", "newTitle", "newSource", "newImage", 200L);
        ctrl.addActivity(activity);
        assertEquals(new ResponseEntity<>(Arrays.asList(initialActivity, activity), OK), ctrl.getAllActivities());
    }

    @Test
    public void testUpdateActivityPresent() {
        ctrl.addActivity(initialActivity);
        Activity activity = createActivity("newId", "newTitle", "newSource", "newImage", 200L);

        assertEquals(activity.getId(), ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getId());
        assertEquals(activity.getTitle(), ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getTitle());
        assertEquals(activity.getSource(), ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getSource());
        assertEquals(activity.getImage(), ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getImage());
        assertEquals(activity.getConsumption(), ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getConsumption());
    }

    @Test
    public void testUpdateActivityNotPresent() {
        Activity activity = createActivity("newId", "newTitle", "newSource", "newImage", 200L);
        var result = ctrl.updateActivity(initialActivity.getKey(), activity);

        assertEquals(BAD_REQUEST, result.getStatusCode());
        }
}