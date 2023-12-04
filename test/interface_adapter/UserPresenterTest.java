package interface_adapter;

import entity.User;
import entity.UserLocation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserPresenterTest {

    private UserPresenter userPresenter;
    private User testUser;

    @Before
    public void setUp() {
        userPresenter = new UserPresenter();
        UserLocation testUserLocation = new UserLocation("Central Park, New York", 40.785091, -73.968285);
        testUser = new User("JohnDoe", testUserLocation);
    }

    @Test
    public void testPresentRegistrationSuccess() {
        String expectedMessage = "User JohnDoe successfully registered with location Central Park, New York.";
        String actualMessage = userPresenter.presentRegistrationSuccess(testUser);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testPresentRegistrationFailure() {
        String expectedMessage = "Registration failed: Username JohnDoe is already taken.";
        String actualMessage = userPresenter.presentRegistrationFailure("JohnDoe");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testPresentLoginSuccess() {
        String expectedMessage = "Welcome back, JohnDoe! Your saved location is Central Park, New York.";
        String actualMessage = userPresenter.presentLoginSuccess(testUser);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testPresentLoginFailure() {
        String expectedMessage = "Login failed: Incorrect username or password.";
        String actualMessage = userPresenter.presentLoginFailure("JohnDoe");
        assertEquals(expectedMessage, actualMessage);
    }
}
