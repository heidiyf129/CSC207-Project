package use_case;

import data_access.UserDao;
import entity.User;
import entity.UserLocation;

public class RegisterUser {
    private UserDao userDao;

    public RegisterUser(UserDao userDao) {
        this.userDao = userDao;
    }

    public RegisterUser() {

    }

    public boolean execute(String username, String locationName, double latitude, double longitude) throws UsernameNotUniqueException {
        // Check if the username is unique
        if (userDao.isUsernameUnique(username)) {
            // Create a new user and add it to the UserDao
            UserLocation userLocation = new UserLocation(locationName, latitude, longitude);
            User newUser = new User(username, userLocation);
            userDao.addUser(newUser);
            return true; // Registration is successful
        }
        return false; // Username is not unique
    }
}