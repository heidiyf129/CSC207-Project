package use_case;

import data_access.UserDao;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// Assuming AuthenticationException is in the use_case package
// import use_case.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

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
        User testUser = new User("testuser", null);  // Update as per your User constructor
        userDaoMock.addUser(testUser);  // Add the user to the mock DAO

        User result = loginUser.execute("testuser", "anyPassword");  // Password check is not performed here

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test(expected = AuthenticationException.class)
    public void testLoginWithInvalidUsername() throws AuthenticationException {
        loginUser.execute("nonexistentuser", "password");
    }
}
