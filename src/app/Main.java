package app;

import entity.AirQuality;
import interface_adapter.AirQualityController;
import interface_adapter.APIAdapter;
import use_case.AnalyzeAirQuality;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private JFrame mainFrame;
    private JTextField countryTextField;
    private JTextField stateTextField;
    private JTextField cityTextField;
    private JButton checkAqiButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().displayMainView());
    }

    public Main() {
        mainFrame = new JFrame("Air Quality Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 100);

        // Initialize these here, so they are not null when referenced later.
        countryTextField = new JTextField(15);
        stateTextField = new JTextField(15);
        cityTextField = new JTextField(15);
        checkAqiButton = new JButton("Check AQI");
    }

    public void displayMainView() {
        mainFrame.setLayout(new FlowLayout());

        JButton signInButton = new JButton("Sign In");
        JButton loginButton = new JButton("Login");

        signInButton.addActionListener(e -> displaySignInView());
        loginButton.addActionListener(e -> displayLoginView());

        mainFrame.add(signInButton);
        mainFrame.add(loginButton);

        mainFrame.setLocationRelativeTo(null);  // Center the main window
        mainFrame.setVisible(true);
    }


    private void displaySignInView() {
        JFrame signInFrame = new JFrame("Sign In");
        signInFrame.setSize(400, 400);
        signInFrame.setLayout(new GridBagLayout()); // Set layout to GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4); // Provide some spacing

        JTextField usernameField = new JTextField(15);
        JTextField cityField = new JTextField(15);
        JTextField stateField = new JTextField(15);
        JTextField countryField = new JTextField(15);
        JButton submitButton = new JButton("Submit");


        submitButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String city = cityField.getText().trim();
            String state = stateField.getText().trim();
            String country = countryField.getText().trim();

            if (!username.isEmpty() && !city.isEmpty() && !state.isEmpty() && !country.isEmpty()) {
                storeUserInfo(username, city, state, country);
                signInFrame.dispose(); // Close the sign-in window
                displayLoginView(); // Open the login window
            } else {
                JOptionPane.showMessageDialog(signInFrame, "Please fill in all fields.",
                        "Incomplete Form", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.weightx = 0.5;
        signInFrame.add(new JLabel("Username:"), gbc);
        signInFrame.add(usernameField, gbc);
        signInFrame.add(new JLabel("City:"), gbc);
        signInFrame.add(cityField, gbc);
        signInFrame.add(new JLabel("State:"), gbc);
        signInFrame.add(stateField, gbc);
        signInFrame.add(new JLabel("Country:"), gbc);
        signInFrame.add(countryField, gbc);
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        signInFrame.add(submitButton, gbc);

        signInFrame.setLocationRelativeTo(null);  // Center the sign-in window
        signInFrame.setVisible(true);
    }

    private void storeUserInfo(String username, String city, String state, String country) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + city + "," + state + "," + country);
            writer.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error storing user info: " + ex.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void displayLoginView() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 100);
        loginFrame.setLayout(new FlowLayout());

        JTextField usernameField = new JTextField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String[] location = getUserLocation(username);
            if (location != null && location.length == 3) { // Ensure location has 3 elements
                loginFrame.dispose(); // Close the login window

                // Set the text fields with the user's location
                countryTextField.setText(location[2]);
                stateTextField.setText(location[1]);
                cityTextField.setText(location[0]);

                // Display the AQI view with pre-filled location data
                displayAQIView(location[0], location[1], location[2]);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username.",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);
        loginFrame.add(loginButton);

        loginFrame.setLocationRelativeTo(null);  // Center the login window
        loginFrame.setVisible(true);
    }


    private String[] getUserLocation(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return Arrays.copyOfRange(parts, 1, parts.length); // city, state, country
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error reading user info: " + ex.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    public void displayAQIView(String city, String state, String country) {
        // Clear the main frame and prepare for adding AQI components
        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2)); // Use GridLayout for an ordered layout



        // Initialize the text fields if they haven't been already
        if (countryTextField == null) {
            countryTextField = new JTextField(15);
        }
        if (stateTextField == null) {
            stateTextField = new JTextField(15);
        }
        if (cityTextField == null) {
            cityTextField = new JTextField(15);
        }

        // Set the text fields with the user's location
        countryTextField.setText(country);
        stateTextField.setText(state);
        cityTextField.setText(city);

        // Add components to the panel
        panel.add(new JLabel("Country:"));
        panel.add(countryTextField);

        panel.add(new JLabel("State:"));
        panel.add(stateTextField);

        panel.add(new JLabel("City:"));
        panel.add(cityTextField);

        // Initialize the button if it hasn't been already
        if (checkAqiButton == null) {
            checkAqiButton = new JButton("Check AQI");
        }

        // Result label to display the AQI
        JLabel resultLabel = new JLabel("AQI: ");
        panel.add(resultLabel);

        // Text area for detailed analysis
        JTextArea analysisArea = new JTextArea(5, 50);
        analysisArea.setEditable(false);
        panel.add(new JScrollPane(analysisArea));

        for (ActionListener al : checkAqiButton.getActionListeners()) {
            checkAqiButton.removeActionListener(al);
        }

        // Add the button to the panel and define its action
        panel.add(checkAqiButton);
        checkAqiButton.addActionListener(e -> {
            // The action to perform when the Check AQI button is clicked
            try {
                String aqiStr = fetchAQI(cityTextField.getText().trim(), stateTextField.getText().trim(), countryTextField.getText().trim());
                int aqiValue = Integer.parseInt(aqiStr); // Parse AQI value from string
                resultLabel.setText("AQI for " + cityTextField.getText().trim() + ": " + aqiValue);

                AirQuality airQuality = new AirQuality(aqiValue);
                AnalyzeAirQuality analyzer = new AnalyzeAirQuality(); // The analyzer instance
                String analysis = analyzer.analyze(airQuality);
                analysisArea.setText(analysis); // Set analyzed result to the TextArea
            } catch (NumberFormatException ex) {
                resultLabel.setText("Error: Invalid AQI value received.");
                analysisArea.setText("");
            } catch (Exception ex) {
                // Handle unexpected errors
                resultLabel.setText("An error occurred while fetching AQI.");
                analysisArea.setText("");
                ex.printStackTrace(); // This will print the error details to the console
            }
        });

        // Add the panel to the main frame
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setSize(600, 400);

        mainFrame.pack(); // Adjust the window size based on the components
        mainFrame.setLocationRelativeTo(null); // Center the window
        mainFrame.setVisible(true);

        // Automatically click the Check AQI button to fetch the data
        SwingUtilities.invokeLater(() -> checkAqiButton.doClick());
        // Add the panel to the main frame
        mainFrame.add(panel, BorderLayout.CENTER);

        // Add a Back button at the bottom
        JButton backButton = new JButton("Back to Main");
        backButton.addActionListener(e -> {
            // Clear the content of the main frame
            mainFrame.getContentPane().removeAll();
            mainFrame.setLayout(new FlowLayout());

            // Resize the main frame before redisplaying the main view
            mainFrame.setSize(300, 100); // Resize the main frame to 300x100

            // Redisplay the main view
            displayMainView();

            // Revalidate and repaint the main frame
            mainFrame.revalidate();
            mainFrame.repaint();

            // Ensure the main frame is centered
            mainFrame.setLocationRelativeTo(null);
        });

// Add the Back button to the main frame, at the bottom
        mainFrame.add(backButton, BorderLayout.PAGE_END);

    }

    private String fetchAQI(String city, String state, String country) {
        // Create an instance of your AirQualityController
        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
        return airQualityController.fetchAirQuality(city, state, country);
    }
}

