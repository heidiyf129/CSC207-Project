package use_case;

import data_access.UserDao;
import entity.User;
import java.util.Objects;


// Custom exception for authentication failures
class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}

public class LoginUser {

    private UserDao userDao;

    public LoginUser(UserDao userDao) {
        this.userDao = userDao;
    }

    public User execute(String username, String providedPassword) throws AuthenticationException {
        // Retrieve the user with the given username
        User user = userDao.findByUsername(username);

        // Check if the user exists and the password matches
        if (user != null && checkPassword(user, providedPassword)) {
            // Return the found user
            return user;
        } else {
            // Throw a custom exception to indicate authentication failure
            throw new AuthenticationException("Invalid username or password.");
        }
    }

    // Simulate password checking - in a real application, use hash comparison
    private boolean checkPassword(User user, String providedPassword) {
        // Simulate hashing of the provided password and comparison with the stored hash
        String hashedProvidedPassword = hashPassword(providedPassword);
        return Objects.equals(user.getHashedPassword(), hashedProvidedPassword);
    }

    // Simulate hashing a password - replace with a real hashing implementation
    private String hashPassword(String password) {
        // This is just a placeholder. Use a real hashing function here.
        return Integer.toString(password.hashCode()); // Do NOT use this in production!
    }

    // Additional logic related to login, such as session management or token generation, would go here.
}
