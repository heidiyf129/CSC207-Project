package use_case;

import data_access.UserDao;
import entity.User;


public class LoginUser {

    private UserDao userDao;

    public LoginUser() {
        this.userDao = userDao;
    }

    public User execute(String username, String providedPassword) throws AuthenticationException {
        // Retrieve the user with the given username
        User user = userDao.findByUsername(username);

        // Check if the user exists and the password matches
        if (user != null) {
            // Return the found user
            return user;
        } else {
            // Throw a custom exception to indicate authentication failure
            throw new AuthenticationException("Invalid username or password.");
        }
    }

}
