package view;

import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class SignUpViewTest {

    private SignUpView signUpView;
    private MainView mainView; // Mock or real instance depending on the test context

    @Before
    public void setUp() throws Exception {
        mainView = new MainView(); // Initialize MainView here
        signUpView = new SignUpView(mainView);
    }

    @Test
    public void testAdjustComponentSizes() throws Exception {
        JSlider scaleSlider = (JSlider) getPrivateField(signUpView, "scaleSlider");
        JTextField usernameField = (JTextField) getPrivateField(signUpView, "usernameField");

        scaleSlider.setValue(18);

        // Invoke the private method adjustComponentSizes
        Method method = SignUpView.class.getDeclaredMethod("adjustComponentSizes");
        method.setAccessible(true);
        method.invoke(signUpView);

        assertEquals("Font size should be updated", 18, usernameField.getFont().getSize());
    }

    // ... Other test methods ...

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
        // Iterate through all components in the container
        for (Component comp : container.getComponents()) {
            // Check if the component is a JButton
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                // Check if the button's text matches the specified text
                if (text.equals(button.getText())) {
                    return button;
                }
            } else if (comp instanceof Container) {
                // If the component is a container, recursively search its children
                JButton button = findButton((Container) comp, text);
                if (button != null) {
                    return button;
                }
            }
        }
        // Return null if no matching button is found
        return null;
    }

}
