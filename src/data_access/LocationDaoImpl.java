package data_access;

import entity.AirQuality;
import entity.Location;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class LocationDaoImpl implements LocationDao {
    private Connection connection;

    // Constructor
    public LocationDaoImpl() {
        // Example connection setup
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabase", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLocation(Location location) {
        String sql = "INSERT INTO locations (city, state, country, aqi) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, location.getCity());
            statement.setString(2, location.getState());
            statement.setString(3, location.getCountry());
            statement.executeUpdate();
            System.out.println("Location added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add location");
        }
    }

    @Override
    public Location getLocation(String city) {
        String sql = "SELECT * FROM locations WHERE city = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, city);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Location(
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("country")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateLocation(Location location, String oldCity, String oldState, String oldCountry) {
        String updateSQL = "UPDATE locations SET city = ?, state = ?, country = ? WHERE city = ? AND state = ? AND country = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, location.getCity());
            statement.setString(2, location.getState());
            statement.setString(3, location.getCountry());
            statement.setString(4, oldCity);
            statement.setString(5, oldState);
            statement.setString(6, oldCountry);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLocation(String city, String state, String country) {
        String deleteSQL = "DELETE FROM locations WHERE city = ? AND state = ? AND country = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setString(1, city);
            statement.setString(2, state);
            statement.setString(3, country);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        String selectSQL = "SELECT city, state, country FROM locations";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                Location location = new Location(
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("country")
                );

                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
