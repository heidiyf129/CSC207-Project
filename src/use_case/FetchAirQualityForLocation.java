package use_case;

import data_access.AirQualityDao;

public class FetchAirQualityForLocation {

    private final AirQualityDao airQualityDao;

    public FetchAirQualityForLocation(AirQualityDao airQualityDao) {
        this.airQualityDao = airQualityDao;
    }

    public String execute(String city, String state, String country) {
        return airQualityDao.fetchAirQualityData(city, state, country);
    }
}