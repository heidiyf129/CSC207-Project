package interface_adapter;

import data_access.LocationDao;
import entity.Location;
import java.util.List;

public class LocationController {
    private LocationDao locationDao;

    public LocationController(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public void addLocation(Location location) {
        locationDao.addLocation(location);
        // Additional logic can be added here if needed
    }

    public Location getLocation(String city) {
        return locationDao.getLocation(city);
        // Additional logic can be added here if needed
    }

    public void updateLocation(Location location, String oldCity, String oldState, String oldCountry) {
        locationDao.updateLocation(location, oldCity, oldState, oldCountry);
        // Additional logic can be added here if needed
    }

    public void deleteLocation(String city, String state, String country) {
        locationDao.deleteLocation(city, state, country);
        // Additional logic can be added here if needed
    }

    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
        // Additional logic can be added here if needed
    }

    // Additional methods can be implemented as needed
}

