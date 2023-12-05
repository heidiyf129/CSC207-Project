package data_access;
public interface AirQualityDao { Integer getAqi(String city, String state, String country);String fetchAirQualityData(String city, String state, String country);}
