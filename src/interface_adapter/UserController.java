package interface_adapter;

import data_access.UserDao;
import entity.User;
import use_case.RegisterUser;
import use_case.LoginUser;
import use_case.UsernameNotUniqueException;

public class UserController {

    private final RegisterUser registerUser;
    private final LoginUser loginUser;
    private final UserDao userDao; // Add this line

    // Dependency injection of use cases and UserDao
    public UserController(RegisterUser registerUser, LoginUser loginUser, UserDao userDao) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
        this.userDao = userDao; // Initialize userDao here
    }

    // Method to handle user registration with all necessary parameters
    public boolean register(String username, String locationName, double latitude, double longitude) {
        try {
            registerUser.execute(username,locationName, latitude, longitude);
            return true;
        } catch (UsernameNotUniqueException e) {
            // Handle the exception, e.g., log it, notify the user, etc.
            System.out.println("Registration failed: " + e.getMessage());
            return false;
        }
    }
    public boolean isUsernameUnique(String username) {
        return userDao.isUsernameUnique(username);
    }

    // Method to handle user login
    public User login(String username, String password) throws Exception {
        // Ensure that the execute method in LoginUser accepts these parameters
        return loginUser.execute(username, password);
    }
}