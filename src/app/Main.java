package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import data_access.LocationDaoImpl;
import entity.AirQuality;
import interface_adapter.AirQualityController;
import interface_adapter.APIAdapter;
import interface_adapter.LocationController;
import use_case.AnalyzeAirQuality;

public class Main {

    private JFrame frame;
    private JPanel panel, suggestionPanel;
    private LocationController locationController;
    private AnalyzeAirQuality analyzer;
    private List<String> locations;
    private List<Integer> aqis;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Main().displayGUI());
    }

    public Main() {
        locationController = new LocationController(new LocationDaoImpl());
        analyzer = new AnalyzeAirQuality();
        locations = new ArrayList<>();
        aqis = new ArrayList<>();
    }

    public void displayGUI() {
        frame = new JFrame("哈哈");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.WHITE); // Light Pink background

        setupButtons();
        setupResultArea();

        frame.add(panel);
        frame.setVisible(true);
    }

    private void setupButtons() {
        JButton addLocationButton = new JButton("Add Location");
        styleButton(addLocationButton);
        addLocationButton.addActionListener(this::handleAddLocationAction);
        panel.add(addLocationButton);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(163, 128, 170));
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
        button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        button.setBorder(new EmptyBorder(10, 15, 10, 15)); // Padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor on hover
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
        inputDialog.setLayout(new FlowLayout());
        inputDialog.setSize(300, 200);

        JTextField cityTextField = new JTextField(10);
        JTextField stateTextField = new JTextField(10);
        JTextField countryTextField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        styleButton(submitButton);

        inputDialog.add(new JLabel("City:"));
        inputDialog.add(cityTextField);
        inputDialog.add(new JLabel("State:"));
        inputDialog.add(stateTextField);
        inputDialog.add(new JLabel("Country:"));
        inputDialog.add(countryTextField);
        inputDialog.add(submitButton);

        submitButton.addActionListener(e -> {
            String city = cityTextField.getText().trim();
            String state = stateTextField.getText().trim();
            String country = countryTextField.getText().trim();

            if (!city.isEmpty() && !state.isEmpty() && !country.isEmpty()) {
                int aqiValue = fetchAQI(city, state, country);
                locations.add(String.format("%s, %s, %s", city, state, country));
                aqis.add(aqiValue);
                updateSuggestionArea();
            } else {
                JOptionPane.showMessageDialog(inputDialog, "Please fill in all the details.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
            inputDialog.dispose();
        });

        inputDialog.setVisible(true);
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
            locationPanel.setBackground(new Color(230, 230, 250)); // Light Pink background
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

    private int fetchAQI(String city, String state, String country) {
        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
        return Integer.parseInt(airQualityController.fetchAirQuality(city, state, country));
    }
}
