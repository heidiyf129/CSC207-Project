package interface_adapter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class APIAdapterTest {

    private APIAdapter apiAdapter;

    @Before
    public void setUp() {
        apiAdapter = new APIAdapter();
    }

    @Test
    public void testGetAqiWithValidLocation() {
        // Use a known city, state, and country that return a successful API response with a valid AQI
        String city = "Los Angeles";
        String state = "California";
        String country = "USA";

        Integer aqi = apiAdapter.getAqi(city, state, country);
        assertNotNull("AQI should not be null", aqi);
        // The following is a very basic check; you'd likely need to verify the AQI against expected values
        assertTrue("AQI should be a non-negative integer", aqi >= 0);
    }

    @Test
    public void testGetAqiWithInvalidLocation() {
        // Use an invalid city, state, and country that return a failed API response
        String city = "InvalidCity";
        String state = "InvalidState";
        String country = "InvalidCountry";

        Integer aqi = apiAdapter.getAqi(city, state, country);
        assertNull("AQI should be null for an invalid location", aqi);
    }

    @Test
    public void testFetchAirQualityDataSuccess() {
        // Provide a known city, state, and country that return a successful API response
        String city = "Los Angeles";
        String state = "California";
        String country = "USA";

        String result = apiAdapter.fetchAirQualityData(city, state, country);
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be an error message", result.contains("Error fetching data"));
        assertFalse("Result should not be an API status code", result.contains("API responded with status"));
        assertTrue("Result should contain AQI data", result.matches("\\d+")); // Checks if result is a number
    }

    @Test
    public void testFetchAirQualityDataApiResponseFailure() {
        // Use a valid city, state, and country but assume the API service will return an error
        String city = "ValidCity";
        String state = "ValidState";
        String country = "ValidCountry";

        // Make sure to choose parameters that will cause the API to respond with an error
        String result = apiAdapter.fetchAirQualityData(city, state, country);

        // Check if the result contains an error message
        assertTrue("Result should indicate an error", result.startsWith("API responded with status"));
    }


    // Additional tests can be created to handle other edge cases, such as rate limits, timeouts, etc.
}
