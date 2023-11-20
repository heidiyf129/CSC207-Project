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

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    // Getter and setter for latitude
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Getter and setter for longitude
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // You might also want to override toString(), equals(), and hashCode() methods.
}