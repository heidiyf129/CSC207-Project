package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import data_access.LocationDaoImpl;
import entity.AirQuality;
import entity.Location;
import interface_adapter.AirQualityController;
import interface_adapter.APIAdapter;
import interface_adapter.LocationController;
import use_case.AnalyzeAirQuality;

public class Main {

    private JFrame frame;
    private JPanel panel;
    private JTextField countryTextField, stateTextField, cityTextField;
    private JLabel aqiLabel1, aqiLabel2;
    private JTextArea suggestionArea1, suggestionArea2;
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

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        setupInputFields();
        setupButtons();
        setupResultArea();

        frame.add(new JScrollPane(panel));
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

    private void setupButtons() {
        JButton checkAqiButton = new JButton("Check AQI");
        JButton addLocationButton = new JButton("Add Location");

        checkAqiButton.addActionListener(this::handleCheckAqiAction);
        addLocationButton.addActionListener(e -> openSecondLocationDialog());

        panel.add(checkAqiButton);
        panel.add(addLocationButton);
    }

    private void setupResultArea() {
        aqiLabel1 = new JLabel("First Location AQI: ");
        suggestionArea1 = new JTextArea(2, 20);
        suggestionArea1.setEditable(false);

        aqiLabel2 = new JLabel("Second Location AQI: ");
        suggestionArea2 = new JTextArea(2, 20);
        suggestionArea2.setEditable(false);

        panel.add(aqiLabel1);
        panel.add(suggestionArea1);
        panel.add(aqiLabel2);
        panel.add(suggestionArea2);
    }

    private void handleCheckAqiAction(ActionEvent e) {
        String city = cityTextField.getText().trim();
        String state = stateTextField.getText().trim();
        String country = countryTextField.getText().trim();

        if (city.isEmpty() || state.isEmpty() || country.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the details.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            AirQualityController aqController = new AirQualityController(new APIAdapter());
            try {
                int aqiValue = fetchAQI(city, state, country); // Assuming fetchAQI returns an int directly
                aqiLabel1.setText("AQI for " + city + ": " + aqiValue); // Change resultLabel to aqiLabel1
                AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
                AirQuality airQuality = new AirQuality(aqiValue);
                String analysis = analyzer.analyze(airQuality);
                suggestionArea1.setText(analysis); // Assuming you want to display the analysis in suggestionArea1
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Failed to fetch AQI data: " + ex.getMessage(), "API Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void openSecondLocationDialog() {
        JDialog secondLocationDialog = new JDialog(frame, "Add Second Location", true);
        secondLocationDialog.setLayout(new FlowLayout());
        secondLocationDialog.setSize(300, 200);

        JTextField city2TextField = new JTextField(10);
        JTextField state2TextField = new JTextField(10);
        JTextField country2TextField = new JTextField(10);
        JButton compareButton = new JButton("Compare");

        secondLocationDialog.add(new JLabel("City:"));
        secondLocationDialog.add(city2TextField);
        secondLocationDialog.add(new JLabel("State:"));
        secondLocationDialog.add(state2TextField);
        secondLocationDialog.add(new JLabel("Country:"));
        secondLocationDialog.add(country2TextField);
        secondLocationDialog.add(compareButton);

        compareButton.addActionListener(e -> {
            compareLocations(cityTextField.getText(), stateTextField.getText(), countryTextField.getText(),
                    city2TextField.getText(), state2TextField.getText(), country2TextField.getText());
            secondLocationDialog.dispose();
        });

        secondLocationDialog.setVisible(true);
    }

    private void compareLocations(String city1, String state1, String country1, String city2, String state2, String country2) {
        // Assuming fetchAQI returns an integer AQI value or -1 in case of error
        int aqi1 = fetchAQI(city1, state1, country1);
        int aqi2 = fetchAQI(city2, state2, country2);

        AnalyzeAirQuality analyzer = new AnalyzeAirQuality();

        // Update the labels and text areas with the AQI values and suggestions
        SwingUtilities.invokeLater(() -> {
            aqiLabel1.setText("First Location AQI: " + (aqi1 >= 0 ? aqi1 : "Error fetching AQI"));
            suggestionArea1.setText(aqi1 >= 0 ? analyzer.analyze(new AirQuality(aqi1)) : "Error fetching AQI");

            aqiLabel2.setText("Second Location AQI: " + (aqi2 >= 0 ? aqi2 : "Error fetching AQI"));
            suggestionArea2.setText(aqi2 >= 0 ? analyzer.analyze(new AirQuality(aqi2)) : "Error fetching AQI");
        });
    }

    private void handleAddLocationAction(ActionEvent e) {
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

    private void openAqiComparisonDialog() {
        JDialog comparisonDialog = new JDialog(frame, "Compare AQI", true);
        comparisonDialog.setLayout(new FlowLayout());
        comparisonDialog.setSize(300, 200);

        JTextField city2TextField = new JTextField(10);
        JTextField state2TextField = new JTextField(10);
        JTextField country2TextField = new JTextField(10);
        JButton compareButton = new JButton("Compare");

        comparisonDialog.add(new JLabel("City 2:"));
        comparisonDialog.add(city2TextField);
        comparisonDialog.add(new JLabel("State 2:"));
        comparisonDialog.add(state2TextField);
        comparisonDialog.add(new JLabel("Country 2:"));
        comparisonDialog.add(country2TextField);
        comparisonDialog.add(compareButton);

        compareButton.addActionListener(e -> {
            // Fetch AQI for both locations
            int aqi1 = fetchAQI(cityTextField.getText(), stateTextField.getText(), countryTextField.getText());
            int aqi2 = fetchAQI(city2TextField.getText(), state2TextField.getText(), country2TextField.getText());
            aqiLabel1.setText("First Location AQI: " + (aqi1 >= 0 ? aqi1 : "Error fetching AQI"));
            aqiLabel2.setText("Second Location AQI: " + (aqi2 >= 0 ? aqi2 : "Error fetching AQI"));
            comparisonDialog.dispose();
        });

        comparisonDialog.setVisible(true);
    }

    private int fetchAQI(String city, String state, String country) {
        // Placeholder method to fetch AQI for given location
        // Replace with actual logic to fetch AQI using AirQualityController
        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
        try {
            return Integer.parseInt(airQualityController.fetchAirQuality(city, state, country));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error parsing AQI value for " + city, "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error fetching AQI for " + city, "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
}

