package entity;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;
    private UserLocation location;

    @Before
    public void setUp() {
        location = new UserLocation("Sample Location", 40.7128, -74.0060);
        location.setAqiInfo("AQI: 50");
        user = new User("testUser", location);
    }

    @Test
    public void testConstructor() {
        assertEquals("testUser", user.getUsername());
        assertEquals(location, user.getLocation());
    }

    @Test
    public void testGetAndSetUsername() {
        user.setUsername("newUser");
        assertEquals("newUser", user.getUsername());
    }

    @Test
    public void testGetAndSetLocation() {
        UserLocation newLocation = new UserLocation("New Location", 34.0522, -118.2437);
        newLocation.setAqiInfo("AQI: 70");
        user.setLocation(newLocation);
        assertEquals(newLocation, user.getLocation());
    }

    @Test
    public void testGetAqiInfo() {
        assertEquals("AQI: 50", user.getAqiInfo());
    }

    // Additional tests for toString(), equals(), and hashCode() if implemented
}