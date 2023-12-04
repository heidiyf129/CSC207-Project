package view;

import javax.swing.*;
import java.awt.*;

public class AQIView {
    private JFrame frame;
    private JTextField countryTextField, stateTextField, cityTextField;
    private JLabel resultLabel, countryLabel, stateLabel, cityLabel;
    private JButton suggestionButton, backButton, addLocationButton;
    private JSlider fontSizeSlider;
    private MainView mainView; // Add this as a class member



    public AQIView(MainView mainView) {
        this.mainView = mainView; // Store the reference to MainView
        initializeComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("AQI Information");
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2));
        countryTextField = new JTextField(15);
        stateTextField = new JTextField(15);
        cityTextField = new JTextField(15);

        countryLabel = new JLabel("Country:");
        stateLabel = new JLabel("State:");
        cityLabel = new JLabel("City:");
        resultLabel = new JLabel();
        suggestionButton = new JButton("Get Suggestion");
        suggestionButton.addActionListener(e -> showSuggestion());

        backButton = new JButton("Back to Main");
        backButton.addActionListener(e -> goBackToMainView());
        addLocationButton = new JButton("Other locations");
        addLocationButton.addActionListener(e -> showAQICompareView());


        panel.add(countryLabel);
        panel.add(countryTextField);
        panel.add(stateLabel);
        panel.add(stateTextField);
        panel.add(cityLabel);
        panel.add(cityTextField);
        panel.add(resultLabel);
        panel.add(suggestionButton);

        // Slider for font size
        fontSizeSlider = new JSlider(JSlider.HORIZONTAL, 8, 16, 12);
        fontSizeSlider.setPreferredSize(new Dimension(65, 50));
        fontSizeSlider.setMajorTickSpacing(4);
        fontSizeSlider.setPaintTicks(true);
        fontSizeSlider.setPaintLabels(false);
        fontSizeSlider.addChangeListener(e -> adjustFontSizes());


        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(fontSizeSlider);
        bottomPanel.add(backButton);
        bottomPanel.add(addLocationButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.PAGE_END);
    }
    private void showSuggestion() {
        try {
            int aqiValue = Integer.parseInt(resultLabel.getText().split(": ")[1].trim());
            mainView.showSuggestionBasedOnAQI(aqiValue);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(frame, "Invalid AQI value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void goBackToMainView() {
        frame.dispose(); // Close the AQI view
        mainView.displayMainView(); // Display the main view of the application
    }
    private void showAQICompareView() {
        mainView.displayGUI();
    }

    private void adjustFontSizes() {
        int fontSize = fontSizeSlider.getValue();
        Font newFont = new Font("SansSerif", Font.PLAIN, fontSize);

        countryLabel.setFont(newFont);
        stateLabel.setFont(newFont);
        cityLabel.setFont(newFont);
        resultLabel.setFont(newFont);
        countryTextField.setFont(newFont);
        stateTextField.setFont(newFont);
        cityTextField.setFont(newFont);
        suggestionButton.setFont(newFont);
        backButton.setFont(newFont);
        addLocationButton.setFont(newFont);
    }

    public void display(String city, String state, String country, int aqiValue) {
        countryTextField.setText(country);
        stateTextField.setText(state);
        cityTextField.setText(city);
        resultLabel.setText("AQI for " + city + ": " + aqiValue);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Additional methods...
}