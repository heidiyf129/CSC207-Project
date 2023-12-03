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
}