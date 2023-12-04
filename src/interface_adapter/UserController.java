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

    // Dependency injection of use cases and UserDao
    public UserController(RegisterUser registerUser, LoginUser loginUser, UserDao userDao) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
        this.userDao = userDao; // Initialize userDao here
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
}
