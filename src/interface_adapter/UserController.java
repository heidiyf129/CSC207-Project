package interface_adapter;

import data_access.UserDao;
import entity.User;
import entity.UserLocation;
import use_case.RegisterUser;
import use_case.LoginUser;
import use_case.UsernameNotUniqueException;

public class UserController {

    private final RegisterUser registerUser;
    private final LoginUser loginUser;
    private final UserDao userDao;
    private final UserPresenter userPresenter; // Add UserPresenter

    // Constructor with UserPresenter
    public UserController(RegisterUser registerUser, LoginUser loginUser, UserDao userDao, UserPresenter userPresenter) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
        this.userDao = userDao;
        this.userPresenter = userPresenter; // Initialize UserPresenter
    }

    // Method to handle user registration
    public String register(String username, String locationName, double latitude, double longitude) {
        try {
            registerUser.execute(username, locationName, latitude, longitude);
            return userPresenter.presentRegistrationSuccess(new User(username, new UserLocation(locationName, latitude, longitude)));
        } catch (UsernameNotUniqueException e) {
            return userPresenter.presentRegistrationFailure(username);
        }
    }

    public boolean isUsernameUnique(String username) {
        return userDao.isUsernameUnique(username);
    }

    // Method to handle user login
    public String login(String username, String password) {
        try {
            User user = loginUser.execute(username, password);
            return userPresenter.presentLoginSuccess(user);
        } catch (Exception e) {
            return userPresenter.presentLoginFailure(username);
        }
    }

    // Method to display AQI information after login
    public String displayAQIForUser(String username, String password) {
        try {
            User user = loginUser.execute(username, password);
            if (user != null) {
                return user.getAqiInfo();
            } else {
                return "User not found or AQI information is unavailable.";
            }
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }
}
