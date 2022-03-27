package server.api;

import commons.misc.Activity;
import commons.misc.ActivityImageMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.ActivityImageRepository;
import server.database.ActivityRepository;
import server.utils.GenerateQuestionUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

class ActivityControllerTest {

    private ActivityImageRepository activityImageRepository;
    private ActivityRepository repo;
    private ActivityController ctrl;
    private Activity initialActivity;
    private Random random;

    @BeforeEach
    public void setup() {
        repo = new TestActivityRepository();
        activityImageRepository = new TestActivityImageRepository();
        ctrl = new ActivityController(repo, activityImageRepository);
        random = new Random();
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
        assertEquals(activity2, result.getBody().get(0));
    }

    @Test
    public void testAddActivities() {
        Activity activity1 = new Activity("newId", "newTitle", "newSource", "newImage", 200L);
        Activity activity2 = new Activity("newId2", "newTitle2", "newSource2", "newImage2", 201L);
        List<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);
        List<Activity> result = ctrl.addActivities(activities).getBody();

        assertEquals(activities, result);
    }

    @Test
    public void testGenerateQuestions() {
        Activity activity1 = new Activity("newId", "newTitle", "newSource", "newImage", 200L);
        Activity activity2 = new Activity("newId2", "newTitle2", "newSource2", "newImage2", 201L);
        Activity activity3 = new Activity("newId3", "newTitle3", "newSource3", "newImage3", 202L);
        Activity activity4 = new Activity("newId4", "newTitle4", "newSource4", "newImage4", 203L);

        List<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);
        activities.add(activity4);
        repo.saveAll(activities);
        ctrl.addActivities(activities);
        GenerateQuestionUtils generateQuestionUtils = new GenerateQuestionUtils(repo, random);
        assertNotNull(generateQuestionUtils.generate20Questions());
    }

    @Test
    public void testGetAddActivityImage() {

        Activity activity1 = new Activity("newId1", "newTitle1", "newSource1", "newImage1", 200L);

        ctrl.addActivity(activity1);
        ActivityImageMessage activityImageMessage = new ActivityImageMessage(Base64.getEncoder()
                .encodeToString(activity1
                        .getImage()
                        .getBytes(StandardCharsets.UTF_8)), activity1.getKey());

        ctrl.addActivityImage(activity1.getKey(), activityImageMessage);
        assertNotNull(repo.findAll());
    }

}