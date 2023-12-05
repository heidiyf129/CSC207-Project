package view;

import entity.AirQuality;
import interface_adapter.APIAdapter;
import interface_adapter.AirQualityController;
import use_case.AnalyzeAirQuality;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainView {
    private JButton signUpButton;
    private JFrame mainFrame;
    private static JFrame frame;
    private JPanel panel;
    private static JPanel suggestionPanel;
    private JTextField countryTextField, stateTextField, cityTextField;
    private final java.util.List<String> locations = new ArrayList<>();
    private final List<Integer> aqis = new ArrayList<>();
    private String currentUsername; // To store the current user's username
    private final AnalyzeAirQuality analyzer = new AnalyzeAirQuality();

    public MainView() {
        mainFrame = new JFrame();
        signUpButton = new JButton("Sign Up");
        mainFrame = new JFrame("Air Quality Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 100);


        countryTextField = new JTextField(15);
        stateTextField = new JTextField(15);
        cityTextField = new JTextField(15);
    }
    public JFrame getFrame() {
        return mainFrame;
    }

    public void displayMainView() {

        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(new FlowLayout());

        JButton signUpButton = new JButton("Sign Up");
        JButton loginButton = new JButton("Login");

        signUpButton.addActionListener(e -> displaySignUpView());
        loginButton.addActionListener(e -> displayLoginView());

        // Create a slider for adjusting button size
        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 20, 15);
        sizeSlider.setMajorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(false);

        // Add change listener to the slider
        sizeSlider.addChangeListener(e -> {
            int sliderValue = sizeSlider.getValue();
            signUpButton.setFont(new Font("SansSerif", Font.PLAIN, sliderValue));
            loginButton.setFont(new Font("SansSerif", Font.PLAIN, sliderValue));
        });

        mainFrame.add(signUpButton);
        mainFrame.add(loginButton);
        mainFrame.add(sizeSlider); // Add the slider to the frame

        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setLocationRelativeTo(null); // Center the main window
        mainFrame.setVisible(true);
    }

    public void displaySignUpView() {
        SignUpView signUpView = new SignUpView(this);
        signUpView.display();
    }

    public void displayLoginView() {
        LoginView loginView = new LoginView(this);
        loginView.display();
    }
    public void displayAQIView(String city, String state, String country) {
        String response = fetchAQI(city, state, country);
        // Parse the response and handle errors appropriately
        int aqiValue;
        try {
            aqiValue = Integer.parseInt(response);
            AQIView aqiView = new AQIView(this); // Pass 'this' to reference MainView
            aqiView.display(city, state, country, aqiValue);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error fetching AQI: " + e.getMessage(), "AQI Error", JOptionPane.ERROR_MESSAGE);}
    }



    public void storeUserInfo(String username, String city, String state, String country) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + city + "," + state + "," + country);
            writer.newLine();
        } catch (IOException ex) {JOptionPane.showMessageDialog(mainFrame, "Error storing user info: " + ex.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String[] getUserLocation(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return Arrays.copyOfRange(parts, 1, parts.length); // city, state, country
                }
            }
        } catch (IOException ex) {JOptionPane.showMessageDialog(mainFrame, "Error reading user info: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void showSuggestionBasedOnAQI(int aqi) {
        AirQuality airQuality = new AirQuality(aqi);
        String analysis = analyzer.analyze(airQuality);
        JOptionPane.showMessageDialog(mainFrame, analysis, "Suggestion", JOptionPane.INFORMATION_MESSAGE);
        // Assuming AQIView has a method to bring its frame to focus
    }


    public static String fetchAQI(String city, String state, String country) {
        // Your implementation of fetching the AQI from the API
        // For example:
        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
        String response = airQualityController.fetchAirQuality(city, state, country);

        try {
            Integer.parseInt(response);
            return response;
        } catch (NumberFormatException e) {
            return "Error: " + e.getMessage();
        }
    }

    public void displayGUI() {
        frame = new JFrame("Other Countries AQI Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.WHITE);

        setupButtons();
        setupResultArea();

        frame.add(panel);
        frame.setVisible(true);

        String[] userLocation = getUserLocation(currentUsername);
        if (userLocation != null && userLocation.length == 3) {
            // Create text fields and set their text with user's location
            JTextField cityTextField = new JTextField(userLocation[0], 10);
            JTextField stateTextField = new JTextField(userLocation[1], 10);
            JTextField countryTextField = new JTextField(userLocation[2], 10);

            // Simulate the action of adding a location
            handleAddLocationAction(cityTextField, stateTextField, countryTextField);
        }
    }

    private void handleAddLocationAction(JTextField cityField, JTextField stateField, JTextField countryField) {
        // Use the passed text fields to add location
        int aqiValue = Integer.parseInt(fetchAQI(cityField.getText(), stateField.getText(), countryField.getText()));
        locations.add(String.format("%s, %s, %s", cityField.getText(), stateField.getText(), countryField.getText()));
        aqis.add(aqiValue);
        updateSuggestionArea();
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
        button.setForeground(Color.BLACK);
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

    private void openLocationInputDialog() {JDialog inputDialog = new JDialog(frame, "Enter Location", true);inputDialog.setLayout(new BoxLayout(inputDialog.getContentPane(), BoxLayout.Y_AXIS));JTextField cityTextField = new JTextField(10);JTextField stateTextField = new JTextField(10);JTextField countryTextField = new JTextField(10);JPanel countryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));countryPanel.add(new JLabel("Country:"));countryPanel.add(countryTextField);inputDialog.add(countryPanel);JPanel statePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));statePanel.add(new JLabel("    State:"));statePanel.add(stateTextField);inputDialog.add(statePanel);JPanel cityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));cityPanel.add(new JLabel("     City:"));cityPanel.add(cityTextField);inputDialog.add(cityPanel);JButton submitButton = new JButton("Submit");styleButton(submitButton);
        submitButton.addActionListener(e -> {
            String city = cityTextField.getText().trim();
            String state = stateTextField.getText().trim();
            String country = countryTextField.getText().trim();

            if (!city.isEmpty() && !state.isEmpty() && !country.isEmpty()) {int aqiValue = Integer.parseInt(fetchAQI(city, state, country));
                locations.add(String.format("%s, %s, %s", city, state, country));
                aqis.add(aqiValue);
                updateSuggestionArea();
            } else {JOptionPane.showMessageDialog(inputDialog, "Please fill in all the details.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }inputDialog.dispose();
        });

        inputDialog.add(submitButton);
        inputDialog.pack(); // Resize dialog to fit the components
        inputDialog.setLocationRelativeTo(frame); // Center dialog relative to the main frame
        inputDialog.setVisible(true);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));buttonPanel.add(submitButton);inputDialog.add(buttonPanel);

        submitButton.addActionListener(e -> {String city = cityTextField.getText().trim();String state = stateTextField.getText().trim();String country = countryTextField.getText().trim();

            if (!city.isEmpty() && !state.isEmpty() && !country.isEmpty()) {int aqiValue = Integer.parseInt(fetchAQI(city, state, country));this.locations.add(String.format("%s, %s, %s", city, state, country));aqis.add(aqiValue);updateSuggestionArea();storeUserInfo(currentUsername, city, state, country);
            } else {JOptionPane.showMessageDialog(inputDialog, "Please fill in all the details.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }inputDialog.dispose();
        });
    }

    private void updateSuggestionArea() {suggestionPanel.removeAll();

        for (int i = 0; i < locations.size(); i++) {String location = locations.get(i);
            int aqi = aqis.get(i);JLabel locationLabel = new JLabel(location + " - AQI: " + aqi);JButton suggestionButton = new JButton("Suggestion");styleButton(suggestionButton);int index = i;suggestionButton.addActionListener(e -> showSuggestion(index));JPanel locationPanel = new JPanel();locationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));locationPanel.setBackground(new Color(230, 230, 250));locationPanel.add(locationLabel);locationPanel.add(suggestionButton);suggestionPanel.add(locationPanel);
        }suggestionPanel.revalidate();suggestionPanel.repaint();
    }

    private void showSuggestion(int index) {
        if (index >= 0 && index < locations.size()) {int aqi = aqis.get(index);AirQuality airQuality = new AirQuality(aqi);String analysis = analyzer.analyze(airQuality);JOptionPane.showMessageDialog(frame, analysis, "Suggestion for " + locations.get(index), JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
