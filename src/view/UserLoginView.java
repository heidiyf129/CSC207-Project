package view;

import entity.User;
import interface_adapter.UserController;
import java.util.Scanner;

public class UserLoginView {

    private UserController userController;
    private Scanner scanner;

    public UserLoginView(UserController userController) {
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void showLogin() {
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();

        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        try {
            // Call to the UserController to handle the login logic with username and password
            User user = userController.login(username, password);

            // If login is successful, user information (like the AQI) can be displayed.
            System.out.println("Login successful. Welcome " + user.getUsername() + "!");
            System.out.println("Your saved location AQI is: " + user.getAqiInfo());
        } catch (Exception e) {
            // Handle login failure.
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    // Additional view logic like displaying registration form, error messages, etc., could go here.
}
