package data_access;

public interface AirQualityDao {
    String fetchAirQualityData(String city, String state, String country);
}