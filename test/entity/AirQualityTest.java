package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AirQualityTest {
    private AirQuality airQuality;

    @BeforeEach
    public void setUp() {
        airQuality = new AirQuality(50); // Example initial value
    }

    @Test
    public void testConstructor() {
        assertEquals(50, airQuality.getAqi(), "Constructor should initialize aqi value correctly");
    }

    @Test
    public void testGetAqi() {
        assertEquals(50, airQuality.getAqi(), "getAqi() should return the correct aqi value");
    }

    @Test
    public void testSetAqi() {
        airQuality.setAqi(100); // Changing the aqi value
        assertEquals(100, airQuality.getAqi(), "setAqi() should correctly update the aqi value");
    }
}