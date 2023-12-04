package view;

import javax.swing.*;
import java.awt.*;

public class SignUpView {
    private JFrame frame;
    private JTextField usernameField, cityField, stateField, countryField;
    private JSlider scaleSlider; // Slider for font scaling
    private MainView mainView; // Reference to MainView

    public SignUpView(MainView mainView) {
        this.mainView = mainView;
        initializeComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Sign Up");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(7, 2, 5, 5)); // Adjust for slider

        usernameField = new JTextField(15);
        cityField = new JTextField(15);
        stateField = new JTextField(15);
        countryField = new JTextField(15);

        // Adding components directly to the frame
        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("City:"));
        frame.add(cityField);
        frame.add(new JLabel("State:"));
        frame.add(stateField);
        frame.add(new JLabel("Country:"));
        frame.add(countryField);

        // Slider for font scaling
        scaleSlider = new JSlider(JSlider.HORIZONTAL, 10, 20, 15);
        scaleSlider.setMajorTickSpacing(1);
        scaleSlider.setPaintTicks(true);
        scaleSlider.setPaintLabels(false);
        scaleSlider.addChangeListener(e -> adjustComponentSizes());
        frame.add(new JLabel("Adjust Size:"));
        frame.add(scaleSlider);

        // Submit button
        JButton submitButton = new JButton("Submit");
        frame.add(submitButton);
        submitButton.addActionListener(e -> submitAction());
    }

    private void adjustComponentSizes() {
        int scaleValue = scaleSlider.getValue();
        Font newFont = new Font("SansSerif", Font.PLAIN, scaleValue);
        usernameField.setFont(newFont);
        cityField.setFont(newFont);
        stateField.setFont(newFont);
        countryField.setFont(newFont);
    }

    private void submitAction() {
        String username = usernameField.getText().trim();
        String city = cityField.getText().trim();
        String state = stateField.getText().trim();
        String country = countryField.getText().trim();

        mainView.storeUserInfo(username, city, state, country);
        frame.dispose();
    }

    public void display() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


