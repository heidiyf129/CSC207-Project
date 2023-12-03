package use_case;


import data_access.UserDao;
import entity.User;
import entity.UserLocation;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegisterUserTest {
    private UserDaoMock userDaoMock;
    private RegisterUser registerUser;

    @Before
    public void setUp() {
        userDaoMock = new UserDaoMock();
        registerUser = new RegisterUser(userDaoMock);
    }

    @Test
    public void testSuccessfulRegistration() throws UsernameNotUniqueException {
        String username = "newuser";
        boolean result = registerUser.execute(username, "TestLocation", 10.0, 20.0);

        assertTrue(result); // Expecting registration to be successful

        User registeredUser = userDaoMock.findByUsername(username);
        assertNotNull(registeredUser);
        assertEquals("TestLocation", registeredUser.getLocation().getLocationName());
    }


    @Test
    public void testRegistrationWithNonUniqueUsername() throws UsernameNotUniqueException {
        String username = "existinguser";
        User existingUser = new User(username, new UserLocation("ExistingLocation", 10.0, 20.0));
        userDaoMock.addUser(existingUser);

        boolean result = registerUser.execute(username, "NewLocation", 15.0, 25.0);

        assertFalse(result);
    }
}
