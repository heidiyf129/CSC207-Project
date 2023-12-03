package view;

import interface_adapter.UserController;
import javax.swing.*;
import java.util.Scanner;

public class UserRegistrationView {

    private UserController userController;
    private Scanner scanner;

    public UserRegistrationView(UserController userController) {
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void showRegistrationForm() {
        boolean isRegistered = false;

        // Variables for user input
        String locationName;
        double latitude;
        double longitude;

        while (!isRegistered) {
            System.out.println("Please enter the desired username:");
            String username = scanner.nextLine();

            if (!userController.isUsernameUnique(username)) {
                // Show warning if username is not unique
                JOptionPane.showMessageDialog(null, "Username already taken. Please choose a different username.", "Username Taken", JOptionPane.WARNING_MESSAGE);
                continue; // Skip the rest of the loop and prompt for the username again
            }

            // Collect additional user details
            System.out.println("Please enter your location name:");
            locationName = scanner.nextLine();

            System.out.println("Please enter the latitude of your location:");
            latitude = Double.parseDouble(scanner.nextLine());

            System.out.println("Please enter the longitude of your location:");
            longitude = Double.parseDouble(scanner.nextLine());

            // Attempt to register the user
            isRegistered = userController.register(username, locationName, latitude, longitude);
            if (isRegistered) {
                System.out.println("Registration successful for user: " + username);
            } else {
                System.out.println("Registration failed. Please try again.");
            }
        }
    }
}
