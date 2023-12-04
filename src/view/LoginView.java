package view;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginView {
    private JFrame frame;
    private JTextField usernameField;
    private JSlider sizeSlider;
    private MainView mainView; // Reference to MainView

    public LoginView(MainView mainView) {
        this.mainView = mainView;
        initializeComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Login");
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        usernameField = new JTextField(15);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> loginAction());



        // Slider for resizing text and button
        sizeSlider = new JSlider(JSlider.HORIZONTAL, 15, 20, 17);
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(false);
        sizeSlider.addChangeListener(e -> adjustComponentSizes());

        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(loginButton);
        frame.add(sizeSlider);
    }

    private void adjustComponentSizes() {
        int size = sizeSlider.getValue();
        Font font = new Font("SansSerif", Font.PLAIN, size);
        usernameField.setFont(font);
        // Adjust other components if needed
    }

    private void loginAction() {
        String username = usernameField.getText().trim();
        System.out.println("Attempting to log in with username: " + username); // Debugging statement

        // Check if username is not empty
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a username.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] location = mainView.getUserLocation(username);
        if (location != null && location.length == 3) {
            System.out.println("User location found: " + Arrays.toString(location)); // Debugging statement
            mainView.setCurrentUsername(username);
            mainView.displayAQIView(location[0], location[1], location[2]);
            frame.dispose(); // Close login window
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void display() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}