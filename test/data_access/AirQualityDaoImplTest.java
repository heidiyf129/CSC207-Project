package data_access;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AirQualityDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private AirQualityDaoImpl dao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        dao = new AirQualityDaoImpl(connection);
    }

    @Test
    void testGetAqi() throws SQLException {
        // Set up mock behavior
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("aqi")).thenReturn(50);

        // Call the method and assert the result
        Integer aqi = dao.getAqi("City", "State", "Country");
        assertNotNull(aqi);
        assertEquals(50, aqi);

        // Add more test cases as needed
    }

    @Test
    void testFetchAirQualityData() throws SQLException {
        // Set up mock behavior
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("aqi")).thenReturn(50);
        when(resultSet.getString("pollutantLevel")).thenReturn("High");
        when(resultSet.getString("advice")).thenReturn("Stay indoors");

        // Call the method and assert the result
        String result = dao.fetchAirQualityData("City", "State", "Country");
        assertEquals("AQI: 50, Pollutant Level: High, Advice: Stay indoors", result);

        // Add more test cases as needed
    }

    // Additional tests can be written for SQLException scenarios and cases where data is not found
}
