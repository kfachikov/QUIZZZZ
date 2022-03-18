package server.api;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        initialActivity = new Activity("id", "image", "source", "title", 100L);
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
        Activity activity = new Activity("newId", "newTitle", "newSource", "newImage", 200L);
        ctrl.addActivity(activity);
        assertEquals(new ResponseEntity<>(Arrays.asList(initialActivity, activity), OK), ctrl.getAllActivities());
    }

    @Test
    public void testUpdateActivityId() {
        ctrl.addActivity(initialActivity);
        Activity activity = new Activity("newId", "title", "source", "image", 100L);

        assertEquals(activity.getId(), ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getId());
    }

    @Test
    public void testUpdateActivityTitle() {
        ctrl.addActivity(initialActivity);
        Activity activity = new Activity("id", "newTitle", "source", "image", 100L);

        assertEquals(activity.getTitle(), ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getTitle());
    }

    @Test
    public void testUpdateActivitySource() {
        ctrl.addActivity(initialActivity);
        Activity activity = new Activity("id", "title", "newSource", "image", 100L);

        assertEquals(
                activity.getSource(),
                ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getSource()
        );
    }

    @Test
    public void testUpdateActivityImage() {
        ctrl.addActivity(initialActivity);
        Activity activity = new Activity("id", "title", "source", "image", 100L);

        assertEquals(activity.getImage(), ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getImage());
    }

    @Test
    public void testUpdateActivityConsumption() {
        ctrl.addActivity(initialActivity);
        Activity activity = new Activity("id", "title", "source", "image", 101L);

        assertEquals(
                activity.getConsumption(),
                ctrl.updateActivity(initialActivity.getKey(), activity).getBody().getConsumption()
        );
    }

    @Test
    public void testUpdateActivityNotPresent() {
        Activity activity = new Activity("newId", "newTitle", "newSource", "newImage", 200L);
        var result = ctrl.updateActivity(initialActivity.getKey(), activity);

        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testAddActivitiesNullAttribute() {
        Activity activity1 = new Activity(null, "newTitle", "newSource", "newImage", 200L);
        Activity activity2 = new Activity("newId", "newTitle", "newSource", "newImage", 200L);
        List<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);

        var result = ctrl.addActivities(activities);
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testAddActivities() {
        Activity activity1 = new Activity("newId", "newTitle", "newSource", "newImage", 200L);
        Activity activity2 = new Activity("newId2", "newTitle2", "newSource2", "newImage2", 201L);
        List<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);

        ctrl.addActivities(activities);
        assertEquals(2, repo.count());
    }
}