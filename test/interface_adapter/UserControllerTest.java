package interface_adapter;

import data_access.UserDao;
import org.junit.Before;
import org.junit.Test;
import use_case.LoginUser;
import use_case.RegisterUser;

import static org.junit.Assert.assertTrue;

public class UserControllerTest {

    private UserController userController;
    private UserDao userDao; // This would be an in-memory database or a stubbed DAO
    private RegisterUser registerUser; // Stubbed use case
    private LoginUser loginUser; // Stubbed use case

    @Before
    public void setUp() {
        // Initialize with stubbed or actual implementations
        userDao = new UserDao() {
            // Stub the methods of UserDao as required for testing
        };
        registerUser = new RegisterUser(userDao) {
            // Override the methods of RegisterUser for testing
        };
        loginUser = new LoginUser(userDao) { // Pass userDao to the constructor
            // Override the methods of LoginUser for testing, if necessary
        };
        userController = new UserController(registerUser, loginUser, userDao);
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        String username = "newUser";
        String password = "password123";
        String locationName = "LocationName";
        double latitude = 10.0;
        double longitude = 20.0;

        // Act
        boolean isRegistered = userController.register(username, locationName, latitude, longitude);

        // Assert
        assertTrue(isRegistered);
    }

    @Test
    public void testIsUsernameUnique() {
        // Arrange
        String uniqueUsername = "uniqueUser";

        // Act
        boolean isUnique = userController.isUsernameUnique(uniqueUsername);

        // Assert
        assertTrue(isUnique);
    }

    @Test
    public void testLoginUser() throws Exception {
        // Arrange
        String username = "existingUser";
        String password = "password123";
    }
}
