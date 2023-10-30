package app;

import entity.AirQuality;
import interface_adapter.AirQualityController;
import interface_adapter.APIAdapter;
import use_case.AnalyzeAirQuality;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().displayGUI();
            }
        });
    }
    public void displayGUI() {
        JFrame frame = new JFrame("Innovative Air Quality Map ;)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 250);  // Increased size for the added TextArea

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Country input
        JLabel countryLabel = new JLabel("Enter country:");
        JTextField countryTextField = new JTextField(15);
        panel.add(countryLabel);
        panel.add(countryTextField);

        // State input
        JLabel stateLabel = new JLabel("Enter state:");
        JTextField stateTextField = new JTextField(15);
        panel.add(stateLabel);
        panel.add(stateTextField);

        // City input
        JLabel cityLabel = new JLabel("Enter city name:");
        JTextField cityTextField = new JTextField(15);
        panel.add(cityLabel);
        panel.add(cityTextField);

        JButton button = new JButton("Check AQI");
        panel.add(button);

        JLabel resultLabel = new JLabel("AQI: ");
        panel.add(resultLabel);

        JTextArea analysisArea = new JTextArea(5, 50); // New TextArea for detailed analysis
        analysisArea.setEditable(false);
        panel.add(new JScrollPane(analysisArea)); // Added scrolling in case of overflow

        AnalyzeAirQuality analyzer = new AnalyzeAirQuality(); // The analyzer instance

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityTextField.getText().trim();
                String state = stateTextField.getText().trim();
                String country = countryTextField.getText().trim();

                if (city.isEmpty() || state.isEmpty() || country.isEmpty()) {
                    resultLabel.setText("Please fill in all the details.");
                    analysisArea.setText(""); // Clearing analysis if any
                    return; // Exit the method to prevent further processing
                }

                try {
                    String aqiStr = fetchAQI(city, state, country);
                    int aqiValue = Integer.parseInt(aqiStr); // Parse AQI value from string
                    resultLabel.setText("AQI for " + city + ": " + aqiValue);

                    AirQuality airQuality = new AirQuality(aqiValue);
                    String analysis = analyzer.analyze(airQuality);
                    analysisArea.setText(analysis); // Set analyzed result to the TextArea
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Error: Invalid AQI value received.");
                    analysisArea.setText("");
                } catch (Exception ex) {
                    // Handle unexpected errors
                    ex.printStackTrace(); // This will print the error details to the console
                    resultLabel.setText("An error occurred while fetching AQI.");
                    analysisArea.setText("");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
    private String fetchAQI(String city, String state, String country) {
        // Create an instance of your AirQualityController
        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
        return airQualityController.fetchAirQuality(city, state, country);
    }


}