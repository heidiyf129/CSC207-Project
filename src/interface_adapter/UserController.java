package interface_adapter;

import entity.User;
import use_case.RegisterUser;
import use_case.LoginUser;

public class UserController {

    private final RegisterUser registerUser;
    private final LoginUser loginUser;

    // Dependency injection of use cases
    public UserController(RegisterUser registerUser, LoginUser loginUser) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
    }

    // Method to handle user registration with all necessary parameters
    public boolean register(String username, String password, String locationName, double latitude, double longitude) {
        // Pass all the parameters to the RegisterUser.execute method
        return registerUser.execute(username, password, locationName, latitude, longitude);
    }

    // Method to handle user login
    public User login(String username, String password) throws Exception {
        // Ensure that the execute method in LoginUser accepts these parameters
        return loginUser.execute(username, password);
    }

    // Method to display AQI information after login (could be part of the login process)
    public String displayAQIForUser(String username, String password) throws Exception {
        // The password is now included and passed to the execute method
        User user = loginUser.execute(username, password);
        if (user != null) {
            return user.getAqiInfo();
        } else {
            // Handle the case where the user is not found or AQI information is not available
            return "User not found or AQI information is unavailable.";
        }
    }
}
