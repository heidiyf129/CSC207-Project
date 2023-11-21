package data_access;

import entity.Location;
import java.util.List;

public interface LocationDao {
    // Add a new location
    void addLocation(Location location);

    // Retrieve a location by city
    Location getLocation(String city);

    // Update an existing location based on old city, state, and country
    void updateLocation(Location location, String oldCity, String oldState, String oldCountry);

    // Delete a location by city, state, and country
    void deleteLocation(String city, String state, String country);

    // Retrieve all locations
    List<Location> getAllLocations();

    // Additional methods can be defined here depending on requirements.
}

