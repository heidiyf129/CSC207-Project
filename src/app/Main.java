package app;

import data_access.LocationDaoImpl;
import entity.AirQuality;
import entity.Location;
import interface_adapter.AirQualityController;
import interface_adapter.APIAdapter;
import interface_adapter.LocationController;
import use_case.AnalyzeAirQuality;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {

    private JFrame frame;
    private JPanel panel;
    private JTextField countryTextField, stateTextField, cityTextField;
    private JLabel resultLabel;
    private JTextArea analysisArea;
    private LocationController locationController;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().displayGUI());
    }

    public Main() {
        locationController = new LocationController(new LocationDaoImpl());
    }

    public void displayGUI() {
        frame = new JFrame("Innovative Air Quality Map ;)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);

        panel = new JPanel(new FlowLayout());

        setupInputFields();
        setupButtons();
        setupResultArea();

        frame.add(panel);
        frame.setVisible(true);
    }

    private void setupInputFields() {
        countryTextField = new JTextField(15);
        stateTextField = new JTextField(15);
        cityTextField = new JTextField(15);

        panel.add(new JLabel("Enter country:"));
        panel.add(countryTextField);
        panel.add(new JLabel("Enter state:"));
        panel.add(stateTextField);
        panel.add(new JLabel("Enter city name:"));
        panel.add(cityTextField);
    }

    private void handleAddLocationAction(ActionEvent e) {
        // Your logic to handle adding a location goes here
        // For example, you might want to collect data from input fields and use it to create a new Location object
        String city = cityTextField.getText().trim();
        String state = stateTextField.getText().trim();
        String country = countryTextField.getText().trim();

        if (!city.isEmpty() && !state.isEmpty() && !country.isEmpty()) {
            Location newLocation = new Location(city, state, country);
            locationController.addLocation(newLocation);
            JOptionPane.showMessageDialog(frame, "Location added successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill in all the fields.");
        }
    }

    private void handleCheckAqiAction(ActionEvent e) {
        String city = cityTextField.getText().trim();
        String state = stateTextField.getText().trim();
        String country = countryTextField.getText().trim();

        if (city.isEmpty() || state.isEmpty() || country.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the details.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Assume you have a method in AirQualityController that takes city, state, country and returns the AQI.
            AirQualityController aqController = new AirQualityController(new APIAdapter());
            try {
                String aqiStr = fetchAQI(city, state, country);
                int aqiValue = Integer.parseInt(aqiStr); // Parse AQI value from string
                resultLabel.setText("AQI for " + city + ": " + aqiValue);
                AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
                AirQuality airQuality = new AirQuality(aqiValue);
                String analysis = analyzer.analyze(airQuality);
                analysisArea.setText(analysis);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Failed to fetch AQI data: " + ex.getMessage(), "API Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setupButtons() {
        JButton checkAqiButton = new JButton("Check AQI");
        JButton addLocationButton = new JButton("Add Location");
        JButton compareAqiButton = new JButton("Compare AQI");

        checkAqiButton.addActionListener(this::handleCheckAqiAction);
        addLocationButton.addActionListener(this::handleAddLocationAction);
        compareAqiButton.addActionListener(e -> openAqiComparisonDialog());

        panel.add(checkAqiButton);
        panel.add(addLocationButton);
        panel.add(compareAqiButton);
    }

    private void setupResultArea() {
        resultLabel = new JLabel("AQI: ");
        analysisArea = new JTextArea(5, 50);
        analysisArea.setEditable(false);

        panel.add(resultLabel);
        panel.add(new JScrollPane(analysisArea));
    }


    private void openAqiComparisonDialog() {
        JDialog comparisonDialog = new JDialog(frame, "Compare AQI", true);
        comparisonDialog.setLayout(new FlowLayout());
        comparisonDialog.setSize(500, 200);

        // Fields for the first city
        JTextField city1TextField = new JTextField(7);
        JTextField state1TextField = new JTextField(7);
        JTextField country1TextField = new JTextField(7);

        // Fields for the second city
        JTextField city2TextField = new JTextField(7);
        JTextField state2TextField = new JTextField(7);
        JTextField country2TextField = new JTextField(7);

        JButton compareButton = new JButton("Compare");
        JLabel resultLabel = new JLabel(" ");

        comparisonDialog.add(new JLabel("City 1:"));
        comparisonDialog.add(city1TextField);
        comparisonDialog.add(new JLabel("State 1:"));
        comparisonDialog.add(state1TextField);
        comparisonDialog.add(new JLabel("Country 1:"));
        comparisonDialog.add(country1TextField);

        comparisonDialog.add(new JLabel("City 2:"));
        comparisonDialog.add(city2TextField);
        comparisonDialog.add(new JLabel("State 2:"));
        comparisonDialog.add(state2TextField);
        comparisonDialog.add(new JLabel("Country 2:"));
        comparisonDialog.add(country2TextField);

        comparisonDialog.add(compareButton);
        comparisonDialog.add(resultLabel);

        compareButton.addActionListener(e -> {
            String aqi1 = fetchAQI(city1TextField.getText().trim(), state1TextField.getText().trim(), country1TextField.getText().trim());
            String aqi2 = fetchAQI(city2TextField.getText().trim(), state2TextField.getText().trim(), country2TextField.getText().trim());
            resultLabel.setText("<html>City 1 AQI: " + aqi1 + "<br/>City 2 AQI: " + aqi2 + "</html>");
        });

        comparisonDialog.setVisible(true);
    }

    private String fetchAQI(String city, String state, String country) {
        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
        return airQualityController.fetchAirQuality(city, state, country);
    }
}