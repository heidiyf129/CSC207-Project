package use_case;

import entity.AirQuality;
import org.junit.Test;
import static org.junit.Assert.*;

public class AnalyzeAirQualityTest {
    @Test
    public void testAnalyzeWithGoodAQI() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
        AirQuality goodAirQuality = new AirQuality(50); // Example of good AQI

        String analysis = analyzer.analyze(goodAirQuality);
        assertNotNull(analysis);
        assertTrue(analysis.startsWith("Good\n\nSuggestions: "));
        // Additional assertions to check if the suggestions are formatted correctly
    }
    @Test
    public void testAnalyzeWithModerateAQI() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
        AirQuality airQuality = new AirQuality(75); // Moderate AQI

        String analysis = analyzer.analyze(airQuality);
        assertTrue(analysis.startsWith("Moderate\n\nSuggestions: "));
        // Additional assertions to check if the suggestions are formatted correctly
    }

    @Test
    public void testAnalyzeWithUnhealthyForSensitiveGroupsAQI() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
        AirQuality airQuality = new AirQuality(125); // Unhealthy for sensitive groups AQI

        String analysis = analyzer.analyze(airQuality);
        assertTrue(analysis.startsWith("Unhealthy for sensitive groups\n\nSuggestions: "));
        // Additional assertions
    }
    @Test
    public void testAnalyzeWithUnhealthyAQI() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
        AirQuality airQuality = new AirQuality(175); // Unhealthy AQI

        String analysis = analyzer.analyze(airQuality);
        assertTrue(analysis.startsWith("Unhealthy\n\nSuggestions: "));
        // Additional assertions
    }
    @Test
    public void testAnalyzeWithVeryUnhealthyAQI() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
        AirQuality airQuality = new AirQuality(250); // Very unhealthy AQI

        String analysis = analyzer.analyze(airQuality);
        assertTrue(analysis.startsWith("Very unhealthy\n\nSuggestions: "));
        // Additional assertions
    }
    @Test
    public void testAnalyzeWithHazardousAQI() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
        AirQuality airQuality = new AirQuality(350); // Hazardous AQI

        String analysis = analyzer.analyze(airQuality);
        assertTrue(analysis.startsWith("Hazardous\n\nSuggestions: "));
        // Additional assertions
    }

    @Test
    public void testAnalyzeWithLongTips() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality() {
            @Override
            public String getAirQuality(int aqi) {
                // Return a long string to test the formatting
                return "This is a very long suggestion string that should be wrapped correctly by the formatTipsForDisplay method to ensure that it is displayed properly in the user interface without exceeding the maximum line width.";
            }
        };
        AirQuality airQuality = new AirQuality(200); // Sample AQI value

        String analysis = analyzer.analyze(airQuality);
        assertNotNull(analysis);
        // Assertions to check if the line wrapping occurs correctly
    }
    @Test
    public void testAnalyzeWithAPIError() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality() {
            @Override
            public String getAirQuality(int aqi) {
                // Simulate an API error
                return "Unable to retrieve tips: API error";
            }
        };
        AirQuality airQuality = new AirQuality(300); // Sample AQI value

        String analysis = analyzer.analyze(airQuality);
        assertNotNull(analysis);
        assertTrue(analysis.contains("Unable to retrieve tips: API error"));
    }
    @Test
    public void testAnalyzeWithExtremeAQI() {
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
        AirQuality airQuality = new AirQuality(500); // Extreme AQI value

        String analysis = analyzer.analyze(airQuality);
        assertNotNull(analysis);
        assertTrue(analysis.startsWith("Hazardous\n\nSuggestions: "));
        // Additional assertions
    }



}

