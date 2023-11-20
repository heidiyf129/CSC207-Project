package use_case;

import data_access.UserDao;
import entity.User;
import entity.UserLocation;

public class RegisterUser {

    private UserDao userDao;

    public RegisterUser(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean execute(String username, String locationName, String name, double latitude, double longitude) {
        // Check if the username is unique
        if (userDao.isUsernameUnique(username)) {
            // Create a new UserLocation instance
            UserLocation location = new UserLocation(locationName, latitude, longitude);

            // Create a new User instance
            User user = new User(username, location);

            // Persist the new user
            userDao.save(user);

            return true; // Registration successful
        } else {
            return false; // Username not unique, registration failed
        }
    }
}
