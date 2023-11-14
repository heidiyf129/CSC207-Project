package data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AirQualityDaoImpl implements AirQualityDao {
    private Connection connection;

    public AirQualityDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer getAqi(String city, String state, String country) {
        String sql = "SELECT aqi FROM AirQuality WHERE city = ? AND state = ? AND country = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, city);
            statement.setString(2, state);
            statement.setString(3, country);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("aqi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String fetchAirQualityData(String city, String state, String country) {
        // This method's implementation depends on what exactly 'fetchAirQualityData' is supposed to do.
        // If it's meant to return a detailed string about the air quality, you might want to query more data
        // and format it into a readable string. For example:
        String sql = "SELECT * FROM AirQuality WHERE city = ? AND state = ? AND country = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, city);
            statement.setString(2, state);
            statement.setString(3, country);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Assuming there are more columns like 'pollutantLevel', 'advice', etc.
                return "AQI: " + resultSet.getInt("aqi") + ", Pollutant Level: " + resultSet.getString("pollutantLevel") + ", Advice: " + resultSet.getString("advice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Air quality data not found.";
    }
}
