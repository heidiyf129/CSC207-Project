package view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MainViewTest {
    private MainView mainView;
    private JButton signUpButton;

    @Before
    public void setUp() throws Exception {
        mainView = new MainView();
    }

    @Test
    public void testgetFrame() {
        JFrame frame = mainView.getFrame();
        assertNotNull("Frame should not be null", frame);
        assertEquals("Frame title should be 'Air Quality Application'", "Air Quality Application", frame.getTitle());
    }


    @Test
    public void testdisplayMainView() {
        mainView.displayMainView();
        // Check if the necessary components are added
        // e.g., check for the presence of buttons, slider, etc.
    }

    @Test
    public void testDisplaySignUpView() throws Exception {
        // This test would check if the SignUpView is displayed correctly
        mainView.displaySignUpView();
        // Add assertions here
    }

    @Test
    public void testDisplayLoginView() throws Exception {
        // This test would check if the LoginView is displayed correctly
        mainView.displayLoginView();
        // Add assertions here
    }

    @Test
    public void testDisplayAQIView() throws Exception {
        // This test would simulate entering a city, state, country and then
        // check if the AQIView is displayed correctly
        mainView.displayAQIView("City", "State", "Country");
        // Add assertions here
    }

    @Test
    public void teststoreUserInfo() {
        String username = "testUser";
        String city = "testCity";
        String state = "testState";
        String country = "testCountry";
        mainView.storeUserInfo(username, city, state, country);

        // Read the file to verify the content
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String lastLine = "";
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            assertEquals("Stored user information should match",
                    username + "," + city + "," + state + "," + country,
                    lastLine.trim());
        } catch (IOException ex) {
            fail("Failed to read from users.txt");
        }
    }


    @Test
    public void testCurrentUsername() {
        String expectedUsername = "testUser";
        mainView.setCurrentUsername(expectedUsername);
        assertEquals("Username should be set correctly", expectedUsername, mainView.getCurrentUsername());
    }


    @Test
    public void getUserLocation() {
        String[] location = mainView.getUserLocation("testUser");
        // Verify that the returned location matches the expected values
    }

    @Test
    public void showSuggestionBasedOnAQI() {
        mainView.showSuggestionBasedOnAQI(50); // Use an example AQI value
        // Test for the expected behavior, like showing a dialog with the suggestion
    }

    @Test
    public void fetchAQI() {
        String aqi = MainView.fetchAQI("City", "State", "Country");
        // Verify that the returned AQI is valid or matches expected values
        // This test might need to be adjusted based on how fetchAQI is implemented
    }

    @Test
    public void displayGUI() {
        mainView.displayGUI();
        // Check for the presence of panels, buttons, text fields, etc.
    }

    private Object getPrivateField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    private void simulateButtonClick(JButton button) {
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, button.getActionCommand());
        for (ActionListener listener : button.getActionListeners()) {
            listener.actionPerformed(event);
        }
    }
    @Test
    public void testButtonClick() throws Exception {
        mainView.displayMainView();
        JButton signUpButton = (JButton) getPrivateField(mainView, "signUpButton");
        simulateButtonClick(signUpButton);

        // Assertions to check the expected behavior after clicking the button
    }
}