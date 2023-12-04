package entity;

import static org.junit.Assert.*;
import org.junit.Test;

public class LocationTest {

    @Test
    public void testDefaultConstructor() {
        Location location = new Location();
        assertNull(location.getCity());
        assertNull(location.getState());
        assertNull(location.getCountry());
    }

    @Test
    public void testParameterizedConstructor() {
        Location location = new Location("New York", "NY", "USA");
        assertEquals("New York", location.getCity());
        assertEquals("NY", location.getState());
        assertEquals("USA", location.getCountry());
    }

    @Test
    public void testSettersAndGetters() {
        Location location = new Location();
        location.setCity("Los Angeles");
        location.setState("CA");
        location.setCountry("USA");

        assertEquals("Los Angeles", location.getCity());
        assertEquals("CA", location.getState());
        assertEquals("USA", location.getCountry());
    }

    @Test
    public void testToString() {
        Location location = new Location("Paris", "Ile-de-France", "France");
        String expectedString = "Location{city='Paris', state='Ile-de-France', country='France'}";
        assertEquals(expectedString, location.toString());
    }

    // Additional tests can be added for edge cases and null values.
}