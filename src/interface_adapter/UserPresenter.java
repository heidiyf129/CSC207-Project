package interface_adapter;

import entity.User;

public class UserPresenter {

    // Method to format the user data for registration success
    public String presentRegistrationSuccess(User user) {
        // Format the successful registration message
        return String.format("User %s successfully registered with location %s.",
                user.getUsername(), user.getLocation().getLocationName());
    }

    // Method to format the user data for registration failure
    public String presentRegistrationFailure(String username) {
        // Format the registration failure message
        return String.format("Registration failed: Username %s is already taken.", username);
    }

    // Method to present login success
    public String presentLoginSuccess(User user) {
        // Format the successful login message, possibly including additional user details
        return String.format("Welcome back, %s! Your saved location is %s.",
                user.getUsername(), user.getLocation().getLocationName());
    }

    // Method to present login failure
    public String presentLoginFailure(String username) {
        // Format the login failure message
        return "Login failed: Incorrect username or password.";
    }

    // Additional methods to present different aspects of the user data can be added as needed
}
