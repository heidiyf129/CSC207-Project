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
    private final AnalyzeAirQuality analyzer;
    private final List<String> locations;
    private final List<Integer> aqis;

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
                int aqiValue = fetchAQI(city, state, country);
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

    private int fetchAQI(String city, String state, String country) {
        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
        return Integer.parseInt(airQualityController.fetchAirQuality(city, state, country));
    }
}
