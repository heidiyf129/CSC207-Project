package entity;

public class User {
    private String username;
    private String hashedPassword; // Store the hashed password, not the plain text
    private UserLocation location;

    public User(String username, UserLocation location) {
        this.username = username;
        this.location = location;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }

    public String getAqiInfo() {
        // Return the AQI info from the UserLocation object
        return location.getAqiInfo();
    }


    // Additional methods such as toString(), equals(), and hashCode() could be implemented here as well
}
