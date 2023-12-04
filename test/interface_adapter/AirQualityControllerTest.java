package interface_adapter;
import data_access.AirQualityDao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AirQualityControllerTest {

    private AirQualityController airQualityController;
    private AirQualityDao airQualityDao;

    @Before
    public void setUp() {
        // Initialize AirQualityDao with a stub implementation
        airQualityDao = new AirQualityDao() {
            @Override
            public Integer getAqi(String city, String state, String country) {
                return 50; // Stubbed response
            }

            @Override
            public String fetchAirQualityData(String city, String state, String country) {
                return "Good"; // Stubbed response
            }
        };

        airQualityController = new AirQualityController(airQualityDao);
    }

    @Test
    public void testFetchAirQuality_ValidInput_ReturnsAirQuality() {
        // Assuming fetchAirQualityData returns "Good" for these parameters
        String result = airQualityController.fetchAirQuality("TestCity", "TestState", "TestCountry");
        assertNotNull("Result should not be null for valid inputs", result);
        assertEquals("Expected air quality data does not match", "Good", result);
    }
}
