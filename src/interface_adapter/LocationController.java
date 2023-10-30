package interface_adapter;

import data_access.LocationDao;

public class LocationController {
    private LocationDao locationDao;

    public LocationController(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    // methods to interact with LocationDao
}