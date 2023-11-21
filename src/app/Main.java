package app;

import entity.AirQuality;
import interface_adapter.AirQualityController;
import interface_adapter.APIAdapter;
import use_case.AnalyzeAirQuality;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    private JFrame mainFrame;
    private JFrame frame;
    private JPanel panel, suggestionPanel;
    private JTextField countryTextField;
    private JTextField stateTextField;
    private JTextField cityTextField;
    private JButton checkAqiButton;
    private final AnalyzeAirQuality analyzer;
    private final List<String> locations;
    private final List<Integer> aqis;



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
        analyzer = new AnalyzeAirQuality();
        locations = new ArrayList<>();
        aqis = new ArrayList<>();

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
        signInFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);

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
                signInFrame.dispose();
                displayLoginView();
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

        signInFrame.setLocationRelativeTo(null);
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
            if (location != null && location.length == 3) {
                loginFrame.dispose();

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

        loginFrame.setLocationRelativeTo(null);
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

        if (countryTextField == null) {
            countryTextField = new JTextField(15);
        }
        if (stateTextField == null) {
            stateTextField = new JTextField(15);
        }
        if (cityTextField == null) {
            cityTextField = new JTextField(15);
        }

        countryTextField.setText(country);
        stateTextField.setText(state);
        cityTextField.setText(city);


        panel.add(new JLabel("Country:"));
        panel.add(countryTextField);

        panel.add(new JLabel("State:"));
        panel.add(stateTextField);

        panel.add(new JLabel("City:"));
        panel.add(cityTextField);


        if (checkAqiButton == null) {
            checkAqiButton = new JButton("Check AQI");
        }


        JLabel resultLabel = new JLabel("AQI: ");
        panel.add(resultLabel);


        JTextArea analysisArea = new JTextArea(5, 50);
        analysisArea.setEditable(false);
        panel.add(new JScrollPane(analysisArea));

        for (ActionListener al : checkAqiButton.getActionListeners()) {
            checkAqiButton.removeActionListener(al);
        }


        panel.add(checkAqiButton);
        checkAqiButton.addActionListener(e -> {

            try {
                String aqiStr = fetchAQI(cityTextField.getText().trim(), stateTextField.getText().trim(), countryTextField.getText().trim());
                int aqiValue = Integer.parseInt(aqiStr);
                resultLabel.setText("AQI for " + cityTextField.getText().trim() + ": " + aqiValue);

                AirQuality airQuality = new AirQuality(aqiValue);
                AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
                String analysis = analyzer.analyze(airQuality);
                analysisArea.setText(analysis);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Error: Invalid AQI value received.");
                analysisArea.setText("");
            } catch (Exception ex) {
                resultLabel.setText("An error occurred while fetching AQI.");
                analysisArea.setText("");
                ex.printStackTrace();
            }
        });

        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setSize(600, 400);

        mainFrame.pack(); // Adjust the window size based on the components
        mainFrame.setLocationRelativeTo(null); // Center the window
        mainFrame.setVisible(true);

        SwingUtilities.invokeLater(() -> checkAqiButton.doClick());
        mainFrame.add(panel, BorderLayout.CENTER);


        JButton backButton = new JButton("Back to Main");
        backButton.addActionListener(e -> {

            mainFrame.getContentPane().removeAll();
            mainFrame.setLayout(new FlowLayout());

            mainFrame.setSize(300, 100);

            displayMainView();

            mainFrame.revalidate();
            mainFrame.repaint();


            mainFrame.setLocationRelativeTo(null);
        });

        mainFrame.add(backButton, BorderLayout.PAGE_END);

        JButton addLocationButton = new JButton("Add Location");
        styleButton(addLocationButton);
        addLocationButton.addActionListener(e -> displayGUI());

        // Assuming mainFrame is the JFrame of your AQI view
        mainFrame.add(addLocationButton, BorderLayout.PAGE_END);

    }

    public void displayGUI() {
        frame = new JFrame("哈哈:)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.WHITE);

        setupButtons();
        setupResultArea();

        frame.add(panel);
        frame.setVisible(true);
    }

    private void setupButtons() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton addLocationButton = new JButton("Add Location");
        styleButton(addLocationButton);
        addLocationButton.addActionListener(this::handleAddLocationAction);
        centerPanel.add(addLocationButton, gbc);
        centerPanel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.add(centerPanel, BorderLayout.NORTH);
    }


    private void styleButton(JButton button) {
        button.setBackground(new Color(163, 128, 170));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        button.setBorder(new EmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupResultArea() {
        suggestionPanel = new JPanel();
        suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
        suggestionPanel.setBackground(new Color(230, 230, 250)); // Light Lavender background

        JScrollPane scrollPane = new JScrollPane(suggestionPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(scrollPane);
    }

    private void handleAddLocationAction(ActionEvent e) {
        openLocationInputDialog();
    }

    private void openLocationInputDialog() {
        JDialog inputDialog = new JDialog(frame, "Enter Location", true);
        inputDialog.setLayout(new BoxLayout(inputDialog.getContentPane(), BoxLayout.Y_AXIS));

        JTextField cityTextField = new JTextField(10);
        JTextField stateTextField = new JTextField(10);
        JTextField countryTextField = new JTextField(10);

        // Add the Country label and text field
        JPanel countryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        countryPanel.add(new JLabel("Country:"));
        countryPanel.add(countryTextField);
        inputDialog.add(countryPanel);

        // Add the State label and text field
        JPanel statePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statePanel.add(new JLabel("    State:"));
        statePanel.add(stateTextField);
        inputDialog.add(statePanel);

        // Add the City label and text field
        JPanel cityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cityPanel.add(new JLabel("     City:"));
        cityPanel.add(cityTextField);
        inputDialog.add(cityPanel);

        JButton submitButton = new JButton("Submit");
        styleButton(submitButton);
        submitButton.addActionListener(e -> {
            String city = cityTextField.getText().trim();
            String state = stateTextField.getText().trim();
            String country = countryTextField.getText().trim();

            if (!city.isEmpty() && !state.isEmpty() && !country.isEmpty()) {
                int aqiValue = Integer.parseInt(fetchAQI(city, state, country));
                locations.add(String.format("%s, %s, %s", city, state, country));
                aqis.add(aqiValue);
                updateSuggestionArea();
            } else {
                JOptionPane.showMessageDialog(inputDialog, "Please fill in all the details.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
            inputDialog.dispose();
        });

        inputDialog.add(submitButton);
        inputDialog.pack(); // Resize dialog to fit the components
        inputDialog.setLocationRelativeTo(frame); // Center dialog relative to the main frame
        inputDialog.setVisible(true);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        inputDialog.add(buttonPanel);
    }


    private void updateSuggestionArea() {
        suggestionPanel.removeAll();

        for (int i = 0; i < locations.size(); i++) {
            String location = locations.get(i);
            int aqi = aqis.get(i);

            JLabel locationLabel = new JLabel(location + " - AQI: " + aqi);
            JButton suggestionButton = new JButton("Suggestion");
            styleButton(suggestionButton);
            int index = i;
            suggestionButton.addActionListener(e -> showSuggestion(index));

            JPanel locationPanel = new JPanel();
            locationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            locationPanel.setBackground(new Color(230, 230, 250));
            locationPanel.add(locationLabel);
            locationPanel.add(suggestionButton);

            suggestionPanel.add(locationPanel);
        }

        suggestionPanel.revalidate();
        suggestionPanel.repaint();
    }

    private void showSuggestion(int index) {
        if (index >= 0 && index < locations.size()) {
            int aqi = aqis.get(index);
            AirQuality airQuality = new AirQuality(aqi);
            String analysis = analyzer.analyze(airQuality);
            JOptionPane.showMessageDialog(frame, analysis, "Suggestion for " + locations.get(index), JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private String fetchAQI(String city, String state, String country) {
        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
        return airQualityController.fetchAirQuality(city, state, country);
    }
}