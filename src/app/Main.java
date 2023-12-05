package app;
import view.MainView;
import javax.swing.*;

public class Main { public static void main(String[] args) {SwingUtilities.invokeLater(() -> new MainView().displayMainView());}
}

//public class Main {public class Main {
//    private JFrame mainFrame, frame;
//    private JPanel panel, suggestionPanel;
//    private JTextField countryTextField, stateTextField, cityTextField;
//    private final List<String> locations = new ArrayList<>();
//    private final List<Integer> aqis = new ArrayList<>();
//    private String currentUsername; // To store the current user's username
//    private final AnalyzeAirQuality analyzer = new AnalyzeAirQuality();
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new Main().displayMainView());
//    }
//
//    public Main() {
//        mainFrame = new JFrame("Air Quality Application");
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setSize(300, 100);
//
//        countryTextField = new JTextField(15);
//        stateTextField = new JTextField(15);
//        cityTextField = new JTextField(15);
//    }
//
//    public void displayMainView() {
//        mainFrame.setLayout(new FlowLayout());
//
//        JButton signInButton = new JButton("Sign In");
//        JButton loginButton = new JButton("Login");
//
//        signInButton.addActionListener(e -> displaySignInView());
//        loginButton.addActionListener(e -> displayLoginView());
//
//        // Create a slider for adjusting button size
//        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 20, 15);
//        sizeSlider.setMajorTickSpacing(1);
//        sizeSlider.setPaintTicks(true);
//        sizeSlider.setPaintLabels(false);
//
//        // Add change listener to the slider
//        sizeSlider.addChangeListener(e -> {
//            int sliderValue = sizeSlider.getValue();
//            // Adjust the size of the buttons based on the slider value
//            signInButton.setFont(new Font("SansSerif", Font.PLAIN, sliderValue));
//            loginButton.setFont(new Font("SansSerif", Font.PLAIN, sliderValue));
//        });
//
//        mainFrame.add(signInButton);
//        mainFrame.add(loginButton);
//        mainFrame.add(sizeSlider); // Add the slider to the frame
//
//        mainFrame.setLocationRelativeTo(null); // Center the main window
//        mainFrame.setVisible(true);
//    }
//
//    private void displaySignInView() {
//        JFrame signInFrame = new JFrame("Sign In");
//        signInFrame.setSize(300, 200); // Keeping size consistent with main view
//        signInFrame.setLayout(new GridLayout(6, 2, 5, 5)); // Adjusted for additional slider row
//
//        // Components
//        JLabel usernameLabel = new JLabel("Username:");
//        JTextField usernameField = new JTextField(15);
//        JLabel cityLabel = new JLabel("City:");
//        JTextField cityField = new JTextField(15);
//        JLabel stateLabel = new JLabel("State:");
//        JTextField stateField = new JTextField(15);
//        JLabel countryLabel = new JLabel("Country:");
//        JTextField countryField = new JTextField(15);
//        JButton submitButton = new JButton("Submit");
//        JLabel sizeAdjustLabel = new JLabel("Adjust Size:"); // Label for slider
//
//        // Slider for scaling
//        JSlider scaleSlider = new JSlider(JSlider.HORIZONTAL, 10, 20, 15);
//        scaleSlider.setMajorTickSpacing(1);
//        scaleSlider.setPaintTicks(true);
//        scaleSlider.setPaintLabels(false);
//
//        // Add components to frame
//        signInFrame.add(usernameLabel);
//        signInFrame.add(usernameField);
//        signInFrame.add(cityLabel);
//        signInFrame.add(cityField);
//        signInFrame.add(stateLabel);
//        signInFrame.add(stateField);
//        signInFrame.add(countryLabel);
//        signInFrame.add(countryField);
//        signInFrame.add(sizeAdjustLabel); // Added label for slider
//        signInFrame.add(scaleSlider);
//        signInFrame.add(submitButton);
//
//        // Slider Change Listener
//        scaleSlider.addChangeListener(e -> {
//            int scaleValue = scaleSlider.getValue();
//            Font newFont = new Font("SansSerif", Font.PLAIN, scaleValue);
//
//            // Update component sizes and font
//            usernameLabel.setFont(newFont);
//            usernameField.setFont(newFont);
//            cityLabel.setFont(newFont);
//            cityField.setFont(newFont);
//            stateLabel.setFont(newFont);
//            stateField.setFont(newFont);
//            countryLabel.setFont(newFont);
//            countryField.setFont(newFont);
//            submitButton.setFont(newFont);
//            sizeAdjustLabel.setFont(newFont);
//
//        });
//
//        submitButton.addActionListener(e -> {
//            String username = usernameField.getText().trim();
//            String city = cityField.getText().trim();
//            String state = stateField.getText().trim();
//            String country = countryField.getText().trim();
//            storeUserInfo(username, city, state, country);
//            signInFrame.dispose();
//        });
//
//        signInFrame.setLocationRelativeTo(null);
//        signInFrame.setVisible(true);
//    }
//
//    private void displayLoginView() {
//        JFrame loginFrame = new JFrame("Login");
//        loginFrame.setSize(300, 150); // Adjusted size to accommodate the slider
//        loginFrame.setLayout(new FlowLayout());
//
//        // Components
//        JLabel usernameLabel = new JLabel("Username:");
//        JTextField usernameField = new JTextField(15);
//        JButton loginButton = new JButton("Login");
//
//        // Slider for resizing text and button
//        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 15, 20, 17); // Min, Max, Initial Value
//        sizeSlider.setMajorTickSpacing(10);
//        sizeSlider.setMinorTickSpacing(1);
//        sizeSlider.setPaintTicks(true);
//        sizeSlider.setPaintLabels(false);
//
//        // Listener for the slider
//        sizeSlider.addChangeListener(e -> {
//            int size = sizeSlider.getValue();
//            Font font = new Font("SansSerif", Font.PLAIN, size);
//
//            // Adjusting the size of text and components
//            usernameField.setFont(font);
//            loginButton.setFont(font);
//            usernameLabel.setFont(font);
//        });
//
//        loginButton.addActionListener(e -> {
//            String username = usernameField.getText().trim();
//            String[] location = getUserLocation(username);
//            if (location != null && location.length == 3) {
//                loginFrame.dispose();
//                currentUsername = username;
//
//                countryTextField.setText(location[2]);
//                stateTextField.setText(location[1]);
//                cityTextField.setText(location[0]);
//
//                displayAQIView(location[0], location[1], location[2]);
//            } else {
//                JOptionPane.showMessageDialog(loginFrame, "Invalid username.",
//                        "Login Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        // Adding components to the frame
//        loginFrame.add(usernameLabel);
//        loginFrame.add(usernameField);
//        loginFrame.add(loginButton);
//        loginFrame.add(sizeSlider);  // Add the slider to the frame
//
//        loginFrame.setLocationRelativeTo(null);
//        loginFrame.setVisible(true);
//    }
//
//    private void adjustTextSize(boolean increase) {
//        // Adjust text size of components
//        // Example: increase or decrease the size of countryTextField
//        Font currentFont = countryTextField.getFont();
//        float newSize = currentFont.getSize() + (increase ? 2.0f : -2.0f);
//        countryTextField.setFont(currentFont.deriveFont(newSize));
//        // Repeat for other text components
//    }
//
//    private void storeUserInfo(String username, String city, String state, String country) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
//            writer.write(username + "," + city + "," + state + "," + country);
//            writer.newLine();
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(mainFrame, "Error storing user info: " + ex.getMessage(),
//                    "File Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private String[] getUserLocation(String username) {
//        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts[0].equals(username)) {
//                    return Arrays.copyOfRange(parts, 1, parts.length); // city, state, country
//                }
//            }
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(mainFrame, "Error reading user info: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
//        }
//        return null;
//    }
//
//
//    public void displayAQIView(String city, String state, String country) {
//        mainFrame.getContentPane().removeAll();
//        mainFrame.setLayout(new BorderLayout());
//
//        JPanel panel = new JPanel(new GridLayout(0, 2));
//            countryTextField.setText(country);
//            stateTextField.setText(state);
//            cityTextField.setText(city);
//
//            JLabel countryLabel = new JLabel("Country:");
//            panel.add(countryLabel);
//            panel.add(countryTextField);
//
//            JLabel stateLabel = new JLabel("State:");
//            panel.add(stateLabel);
//            panel.add(stateTextField);
//
//            JLabel cityLabel = new JLabel("City:");
//            panel.add(cityLabel);
//            panel.add(cityTextField);
//
//            String aqiStr = fetchAQI(city, state, country);
//            int aqiValue = Integer.parseInt(aqiStr);
//            JLabel resultLabel = new JLabel("AQI for " + city + ": " + aqiValue);
//            panel.add(resultLabel);
//
//            JButton suggestionButton = new JButton("Get Suggestion");
//            suggestionButton.addActionListener(e -> showSuggestionBasedOnAQI(aqiValue));
//            panel.add(suggestionButton);
//
//            mainFrame.add(panel, BorderLayout.CENTER);
//
//            // Slider for font size
//            JSlider fontSizeSlider = new JSlider(JSlider.HORIZONTAL, 8, 16, 12);
//        // Set the preferred size of the slider
//        fontSizeSlider.setPreferredSize(new Dimension(65, 50)); // Adjust width and height as needed
//            fontSizeSlider.setMajorTickSpacing(4);
//            fontSizeSlider.setPaintTicks(true);
//            fontSizeSlider.setPaintLabels(false);
//            JButton backButton = new JButton("Back to Main");
//            JButton addLocationButton = new JButton("Other locations");
//
//            fontSizeSlider.addChangeListener(e -> {
//                int fontSize = fontSizeSlider.getValue();
//                Font newFont = new Font("SansSerif", Font.PLAIN, fontSize);
//
//                countryLabel.setFont(newFont);
//                stateLabel.setFont(newFont);
//                cityLabel.setFont(newFont);
//                resultLabel.setFont(newFont);
//                countryTextField.setFont(newFont);
//                stateTextField.setFont(newFont);
//                cityTextField.setFont(newFont);
//                suggestionButton.setFont(newFont);
//                backButton.setFont(newFont);
//                addLocationButton.setFont(newFont);
//            });
//
//            JPanel bottomPanel = new JPanel(new FlowLayout());
//            bottomPanel.add(fontSizeSlider);
//
//            backButton.addActionListener(e -> {
//                mainFrame.getContentPane().removeAll();
//                mainFrame.setLayout(new FlowLayout());
//                mainFrame.setSize(300, 100);
//                displayMainView();
//                mainFrame.revalidate();
//                mainFrame.repaint();
//                mainFrame.setLocationRelativeTo(null);
//            });
//            bottomPanel.add(backButton);
//
//            styleButton(addLocationButton);
//            addLocationButton.addActionListener(e -> displayGUI());
//            bottomPanel.add(addLocationButton);
//
//            mainFrame.add(bottomPanel, BorderLayout.PAGE_END);
//
//            mainFrame.pack();
//            mainFrame.setLocationRelativeTo(null);
//            mainFrame.setVisible(true);
//        }
//
//    private void showSuggestionBasedOnAQI(int aqi) {
//        AirQuality airQuality = new AirQuality(aqi);
//        String analysis = analyzer.analyze(airQuality);
//        JOptionPane.showMessageDialog(mainFrame, analysis, "Suggestion", JOptionPane.INFORMATION_MESSAGE);
//    }
//
//    private String fetchAQI(String city, String state, String country) {
//        AirQualityController airQualityController = new AirQualityController(new APIAdapter());
//        return airQualityController.fetchAirQuality(city, state, country);
//    }
//
//    public void displayGUI() {
//        frame = new JFrame("哈哈：）");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 500);
//
//        panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//        panel.setBackground(Color.WHITE);
//
//        setupButtons();
//        setupResultArea();
//
//        frame.add(panel);
//        frame.setVisible(true);
//    }
//
//    private void setupButtons() {
//        JPanel centerPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//
//        gbc.gridx = 0; // Column
//        gbc.gridy = 0; // Row
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.anchor = GridBagConstraints.CENTER;
//
//        JButton addLocationButton = new JButton("Add Location");
//        styleButton(addLocationButton);
//        addLocationButton.addActionListener(this::handleAddLocationAction);
//        centerPanel.add(addLocationButton, gbc);
//        centerPanel.setBackground(Color.WHITE);
//        panel.setLayout(new BorderLayout());
//        panel.add(centerPanel, BorderLayout.NORTH);
//    }
//
//    private void styleButton(JButton button) {
//        button.setBackground(new Color(163, 128, 170));
//        button.setForeground(Color.BLACK);
//        button.setFocusPainted(false);
//        button.setFont(new Font("Times New Roman", Font.BOLD, 15));
//        button.setBorder(new EmptyBorder(10, 15, 10, 15));
//        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//    }
//
//    private void setupResultArea() {
//        suggestionPanel = new JPanel();
//        suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
//        suggestionPanel.setBackground(new Color(230, 230, 250)); // Light Lavender background
//
//        JScrollPane scrollPane = new JScrollPane(suggestionPanel,
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        panel.add(scrollPane);
//    }
//
//    private void handleAddLocationAction(ActionEvent e) {
//        openLocationInputDialog();
//    }
//
//    private void openLocationInputDialog() {
//        JDialog inputDialog = new JDialog(frame, "Enter Location", true);
//        inputDialog.setLayout(new BoxLayout(inputDialog.getContentPane(), BoxLayout.Y_AXIS));
//
//        JTextField cityTextField = new JTextField(10);
//        JTextField stateTextField = new JTextField(10);
//        JTextField countryTextField = new JTextField(10);
//
//        // Add the Country label and text field
//        JPanel countryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        countryPanel.add(new JLabel("Country:"));
//        countryPanel.add(countryTextField);
//        inputDialog.add(countryPanel);
//
//        // Add the State label and text field
//        JPanel statePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        statePanel.add(new JLabel("    State:"));
//        statePanel.add(stateTextField);
//        inputDialog.add(statePanel);
//
//        // Add the City label and text field
//        JPanel cityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        cityPanel.add(new JLabel("     City:"));
//        cityPanel.add(cityTextField);
//        inputDialog.add(cityPanel);
//
//        JButton submitButton = new JButton("Submit");
//        styleButton(submitButton);
//        submitButton.addActionListener(e -> {
//            String city = cityTextField.getText().trim();
//            String state = stateTextField.getText().trim();
//            String country = countryTextField.getText().trim();
//
//            if (!city.isEmpty() && !state.isEmpty() && !country.isEmpty()) {
//                int aqiValue = Integer.parseInt(fetchAQI(city, state, country));
//                locations.add(String.format("%s, %s, %s", city, state, country));
//                aqis.add(aqiValue);
//                updateSuggestionArea();
//            } else {
//                JOptionPane.showMessageDialog(inputDialog, "Please fill in all the details.", "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//            inputDialog.dispose();
//        });
//
//        inputDialog.add(submitButton);
//        inputDialog.pack(); // Resize dialog to fit the components
//        inputDialog.setLocationRelativeTo(frame); // Center dialog relative to the main frame
//        inputDialog.setVisible(true);
//
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        buttonPanel.add(submitButton);
//        inputDialog.add(buttonPanel);
//
//        submitButton.addActionListener(e -> {
//            String city = cityTextField.getText().trim();
//            String state = stateTextField.getText().trim();
//            String country = countryTextField.getText().trim();
//
//            if (!city.isEmpty() && !state.isEmpty() && !country.isEmpty()) {
//                int aqiValue = Integer.parseInt(fetchAQI(city, state, country));
//                this.locations.add(String.format("%s, %s, %s", city, state, country));
//                aqis.add(aqiValue);
//                updateSuggestionArea();
//
//                // Store the new location in the user's account
//                storeUserInfo(currentUsername, city, state, country);
//            } else {
//                JOptionPane.showMessageDialog(inputDialog, "Please fill in all the details.", "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//            inputDialog.dispose();
//        });
//    }
//
//    private void updateSuggestionArea() {
//        suggestionPanel.removeAll();
//
//        for (int i = 0; i < locations.size(); i++) {
//            String location = locations.get(i);
//            int aqi = aqis.get(i);
//
//            JLabel locationLabel = new JLabel(location + " - AQI: " + aqi);
//            JButton suggestionButton = new JButton("Suggestion");
//            styleButton(suggestionButton);
//            int index = i;
//            suggestionButton.addActionListener(e -> showSuggestion(index));
//
//            JPanel locationPanel = new JPanel();
//            locationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//            locationPanel.setBackground(new Color(230, 230, 250));
//            locationPanel.add(locationLabel);
//            locationPanel.add(suggestionButton);
//
//            suggestionPanel.add(locationPanel);
//        }
//
//        suggestionPanel.revalidate();
//        suggestionPanel.repaint();
//    }
//
//    private void showSuggestion(int index) {
//        if (index >= 0 && index < locations.size()) {
//            int aqi = aqis.get(index);
//            AirQuality airQuality = new AirQuality(aqi);
//            String analysis = analyzer.analyze(airQuality);
//            JOptionPane.showMessageDialog(frame, analysis, "Suggestion for " + locations.get(index), JOptionPane.INFORMATION_MESSAGE);
//        }
//    }
//}