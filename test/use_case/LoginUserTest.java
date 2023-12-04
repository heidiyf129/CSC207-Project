package use_case;

import entity.User;
import entity.UserLocation;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginUserTest {

    private UserDaoMock userDaoMock;
    private LoginUser loginUser;

    @Before
    public void setUp() {
        userDaoMock = new UserDaoMock();
        loginUser = new LoginUser(userDaoMock);
    }

    @Test
    public void testSuccessfulLogin() throws AuthenticationException {
        // Create a UserLocation object with the required parameters
        String locationName = "SomeCity";  // Example city name
        double latitude = 40.7128;  // Example latitude
        double longitude = -74.0060;  // Example longitude
        UserLocation location = new UserLocation(locationName, latitude, longitude);

        // Create a User object with the username and UserLocation
        User testUser = new User("testuser", location);
        userDaoMock.addUser(testUser);

        // Execute the login method
        User result = loginUser.execute("testuser", "password");

        // Assertions
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test(expected = AuthenticationException.class)
    public void testLoginWithInvalidUsername() throws AuthenticationException {
        loginUser.execute("nonexistentuser", "password");
    }
}
