package view;

import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.awt.event.ActionEvent;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class LoginViewTest {

    private LoginView loginView;
    private MainView mainView; // Mock or real instance depending on the test context

    @Before
    public void setUp() throws Exception {
        mainView = new MainView(); // Initialize MainView here
        loginView = new LoginView(mainView);
    }

    @Test
    public void testAdjustComponentSizes() throws Exception {
        JSlider sizeSlider = (JSlider) getPrivateField(loginView, "sizeSlider");
        JTextField usernameField = (JTextField) getPrivateField(loginView, "usernameField");

        // Manually set the slider value
        sizeSlider.setValue(18);

        // Manually invoke the change listener
        // Assuming 'adjustComponentSizes' is the method attached to the slider's change listener
        loginView.adjustComponentSizes();

        // Check if the font size of the username field is updated
        assertEquals("Font size should be updated", 18, usernameField.getFont().getSize());
    }


    @Test
    public void testLoginActionWithValidUsername() throws Exception {
        JTextField usernameField = (JTextField) getPrivateField(loginView, "usernameField");
        JButton loginButton = (JButton) getPrivateField(loginView, "loginButton");

        // Set a valid username
        usernameField.setText("validUsername");

        // Simulate login button click
        simulateButtonClick(loginButton);

        // Add assertions to check the expected behavior
    }

    @Test
    public void testLoginActionWithInvalidUsername() throws Exception {
        JTextField usernameField = (JTextField) getPrivateField(loginView, "usernameField");
        JButton loginButton = (JButton) getPrivateField(loginView, "loginButton");

        // Set an invalid username
        usernameField.setText("");

        // Simulate login button click
        simulateButtonClick(loginButton);

        // Add assertions to check for error dialog
    }


    private void simulateButtonClick(JButton button) {
        ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, button.getActionCommand());
        for (ActionListener listener : button.getActionListeners()) {
            listener.actionPerformed(event);
        }
    }

    private Object getPrivateField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    private JButton findButton(Container container, String text) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button.getText().equals(text)) {
                    return button;
                }
            } else if (comp instanceof Container) {
                JButton button = findButton((Container) comp, text);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }
}
