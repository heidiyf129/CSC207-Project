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
        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
        AirQuality airQuality = new AirQuality(200); // Assume this will produce a long tips string
        String analysis = analyzer.analyze(airQuality);
        // Assertions to check if the line wrapping occurs
    }
}

