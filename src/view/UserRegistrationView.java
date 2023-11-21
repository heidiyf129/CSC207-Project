package view;

import interface_adapter.UserController;
import java.util.Scanner;

public class UserRegistrationView {

    private UserController userController;
    private Scanner scanner;

    public UserRegistrationView(UserController userController) {
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void showRegistrationForm() {
        System.out.println("Please enter the desired username:");
        String username = scanner.nextLine();

        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        System.out.println("Please enter your location name:");
        String locationName = scanner.nextLine();

        System.out.println("Please enter the latitude of your location:");
        double latitude = scanner.nextDouble();

        System.out.println("Please enter the longitude of your location:");
        double longitude = scanner.nextDouble();

        // Consume the remaining newline
        scanner.nextLine();

        // Attempt to register the user with the collected information
        boolean isRegistered = userController.register(username, password, locationName, latitude, longitude);

        if (isRegistered) {
            System.out.println("Registration successful for user: " + username);
        } else {
            System.out.println("Registration failed. Username might already be taken.");
        }

        // Additional logic for handling other aspects of the registration process
        // and interacting with the user can be added here.
    }
}
