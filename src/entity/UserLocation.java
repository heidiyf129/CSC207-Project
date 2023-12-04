package entity;

public class UserLocation {
    private String locationName;
    private double latitude;
    private double longitude;
    private String aqiInfo; // Added attribute for AQI information

    public UserLocation(String locationName, double latitude, double longitude) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.aqiInfo = aqiInfo;
    }

    // Getter and setter for locationName
    public String getAqiInfo() {
        return aqiInfo;
    }

    public void setAqiInfo(String aqiInfo) {
        this.aqiInfo = aqiInfo;
    }
    public String getLocationName() {
        return locationName;
    }

    // You might also want to override toString(), equals(), and hashCode() methods.
}