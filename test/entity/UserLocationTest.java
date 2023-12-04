package entity;
import static org.junit.Assert.*;
import org.junit.Test;

public class UserLocationTest {

    @Test
    public void testConstructor() {
        UserLocation location = new UserLocation("Sample Location", 40.7128, -74.0060);

        assertEquals("Sample Location", location.getLocationName());
        // Check if the default value of aqiInfo is as expected (possibly null or an empty string)
        assertNull("AQI info should be null initially", location.getAqiInfo());
    }

    @Test
    public void testGetAndSetAqiInfo() {
        UserLocation location = new UserLocation("Sample Location", 40.7128, -74.0060);
        location.setAqiInfo("AQI: 50");
        assertEquals("AQI: 50", location.getAqiInfo());
    }

    @Test
    public void testGetLocationName() {
        UserLocation location = new UserLocation("Sample Location", 40.7128, -74.0060);
        assertEquals("Sample Location", location.getLocationName());
    }

    // You can also add tests for latitude and longitude if getter methods are added later
}