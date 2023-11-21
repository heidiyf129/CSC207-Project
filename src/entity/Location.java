package entity;

public class Location {
    private String city;
    private String state;
    private String country;

    // Default constructor
    public Location() {
    }

    // Parameterized constructor
    public Location(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    // Getters
    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    // Setters
    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    // Optional: Override toString method for easy printing
    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
