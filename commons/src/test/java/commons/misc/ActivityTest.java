package commons.misc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {
    final private Activity activity1 = new Activity("id", "title", "source", "image", 100L);
    final private Activity activity2 = new Activity("id", "title", "source", "image", 100L);
    final private Activity activity3 = new Activity("newId", "newTitle", "newSource", "newImage", 110L);

    @Test
    void getSetKey() {
        Long result = 1L;
        activity1.setKey(result);
        assertEquals(result, activity1.getKey());
    }

    @Test
    void getId() {
        assertEquals("id", activity1.getId());
    }

    @Test
    void setId() {
        activity1.setId("newId");
        assertEquals("newId", activity1.getId());
    }

    @Test
    void getTitle() {
        assertEquals("title", activity1.getTitle());
    }

    @Test
    void setTitle() {
        activity1.setTitle("newTitle");
        assertEquals("newTitle", activity1.getTitle());
    }

    @Test
    void getSource() {
        assertEquals("source", activity1.getSource());
    }

    @Test
    void setSource() {
        activity1.setSource("newSource");
        assertEquals("newSource", activity1.getSource());

    }

    @Test
    void getImage() {
        assertEquals("image", activity1.getImage());
    }

    @Test
    void setImage() {
        activity1.setImage("newImage");
        assertEquals("newImage", activity1.getImage());

    }

    @Test
    void getConsumption() {
        assertEquals(100L, activity1.getConsumption());
    }

    @Test
    void setConsumption() {
        activity1.setConsumption(101L);
        assertEquals(101L, activity1.getConsumption());
    }

    @Test
    void testEquals() {
        assertEquals(activity1, activity2);
    }

    @Test
    void testEqualsDifferent() {
        assertNotEquals(activity1, activity3);
    }

    @Test
    void testEqualsDifferentId() {
        Activity activity4 = new Activity("new", "title", "source", "image", 100L);
        assertNotEquals(activity1, activity4);
    }

    @Test
    void testEqualsDifferentTitle() {
        Activity activity4 = new Activity("id", "new", "source", "image", 100L);
        assertNotEquals(activity1, activity4);
    }

    @Test
    void testEqualsDifferentSource() {
        Activity activity4 = new Activity("id", "title", "new", "image", 100L);
        assertNotEquals(activity1, activity4);
    }

    @Test
    void testEqualsDifferentImage() {
        Activity activity4 = new Activity("id", "title", "source", "new", 100L);
        assertNotEquals(activity1, activity4);
    }

    @Test
    void testEqualsDifferentConsumption() {
        Activity activity4 = new Activity("id", "title", "source", "image", 1L);
        assertNotEquals(activity1, activity4);
    }
}