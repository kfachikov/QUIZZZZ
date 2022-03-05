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

        initialActivity = new Activity("id", "title", "source", "image", 100L);
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
    public void testAddActivityStored() {
        ctrl.addActivity(initialActivity);
        assertEquals(new ResponseEntity<>(Arrays.asList(initialActivity), OK), ctrl.getAllActivities());
    }
}