package interface_adapter;

import data_access.AirQualityDao;

public class AirQualityController {
    private AirQualityDao airQualityDao;

    public AirQualityController(AirQualityDao airQualityDao) {
        this.airQualityDao = airQualityDao;
    }

    // Existing method to fetch air quality data as a String
    public String fetchAirQuality(String city, String state, String country) {
        return airQualityDao.fetchAirQualityData(city, state, country);
    }

    // New method to fetch the AQI and return it as an integer
    public int getAqiForLocation(String city, String state, String country) throws Exception {
        // Assuming airQualityDao.fetchAirQualityData returns a JSON string
        // with the AQI data, you would parse that JSON here and return the AQI as an int.
        String jsonResponse = airQualityDao.fetchAirQualityData(city, state, country);

        // Parse the JSON response to extract the AQI value
        // You'll need to use a JSON library like Jackson or Gson for this.
        // The following line is just an example and should be replaced with actual parsing logic.
        int aqiValue = parseAqiFromResponse(jsonResponse);

        return aqiValue;
    }

    // Example parsing method that you'll need to implement based on your JSON structure
    private int parseAqiFromResponse(String jsonResponse) throws Exception {
        // Use a JSON parsing library to extract the AQI value from the JSON response
        // For example, using org.json library (make sure to add the dependency to your project):
        // JSONObject jsonObject = new JSONObject(jsonResponse);
        // return jsonObject.getInt("aqi");

        // Placeholder for the parsing logic
        return 0; // Replace with actual parsing logic
    }
}
