package data_access;

import data_access.LocationDaoImpl;
import entity.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.*;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class LocationDaoImplTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    private Statement mockStatement;

    private LocationDaoImpl locationDao;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        locationDao = new LocationDaoImpl(mockConnection);
    }

    @Test
    public void addLocation_Successful() throws SQLException {
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
        Location location = new Location("City", "State", "Country");

        locationDao.addLocation(location);

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void getLocation_Found() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("city")).thenReturn("City");
        when(mockResultSet.getString("state")).thenReturn("State");
        when(mockResultSet.getString("country")).thenReturn("Country");

        Location result = locationDao.getLocation("City");

        assertNotNull(result);
        assertEquals("City", result.getCity());
        assertEquals("State", result.getState());
        assertEquals("Country", result.getCountry());
    }

    @Test
    public void updateLocation_Successful() throws SQLException {
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
        Location location = new Location("NewCity", "NewState", "NewCountry");

        locationDao.updateLocation(location, "City", "State", "Country");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void deleteLocation_Successful() throws SQLException {
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());

        locationDao.deleteLocation("City", "State", "Country");

        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void getAllLocations_Successful() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("city")).thenReturn("City");
        when(mockResultSet.getString("state")).thenReturn("State");
        when(mockResultSet.getString("country")).thenReturn("Country");

        List<Location> result = locationDao.getAllLocations();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("City", result.get(0).getCity());
        assertEquals("State", result.get(0).getState());
        assertEquals("Country", result.get(0).getCountry());
    }
}
