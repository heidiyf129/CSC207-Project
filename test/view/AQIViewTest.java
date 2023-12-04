package view;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.awt.event.ActionEvent;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AQIViewTest {

    private AQIView aqiView;
    private MainView mainView; // assuming MainView is a part of your project

    @Before
    public void setUp() throws Exception {
        mainView = new MainView(); // Create an instance of MainView
        aqiView = new AQIView(mainView); // Create an instance of AQIView
    }

    @Test
    public void testShowSuggestionWithValidAQI() throws Exception {
        JLabel resultLabel = (JLabel) getPrivateField(aqiView, "resultLabel");
        JButton suggestionButton = (JButton) getPrivateField(aqiView, "suggestionButton");

        // Set a valid AQI value
        resultLabel.setText("AQI for City: 250");

        // Start the dialog handler for the suggestion
        handleDialog("Suggestion Dialog Title");

        // Trigger the action that opens the suggestion dialog
        simulateButtonClick(suggestionButton);

        // Add assertions to verify the expected state after the dialog interaction
    }

    @Test
    public void testShowSuggestionWithInvalidAQI() throws Exception {
        JLabel resultLabel = (JLabel) getPrivateField(aqiView, "resultLabel");
        JButton suggestionButton = (JButton) getPrivateField(aqiView, "suggestionButton");

        // Set an invalid AQI value
        resultLabel.setText("Invalid AQI");

        // Start the dialog handler for the error
        handleDialog("Error Dialog Title");

        // Trigger the action that opens the error dialog
        simulateButtonClick(suggestionButton);

        // Add assertions to verify the expected state after the dialog interaction
    }

    @Test
    public void testGoBackToMainView() throws Exception {
        // Assuming MainView is a part of your project
        MainView mainView = new MainView();
        AQIView aqiView = new AQIView(mainView);

        // Access the private 'frame' field using reflection
        JFrame frame = (JFrame) getPrivateField(aqiView, "frame");

        // Simulate the back button click
        JButton backButton = (JButton) getPrivateField(aqiView, "backButton");
        simulateButtonClick(backButton);

        // Assertions
        assertFalse("Frame should be closed", frame.isShowing());
        // Add additional assertions to check if the main view is displayed
    }

    @Test
    public void testShowAQICompareView() throws Exception {
        JButton addLocationButton = (JButton) getPrivateField(aqiView, "addLocationButton");
        simulateButtonClick(addLocationButton);
        // Assertions to check if the comparison view is shown
    }

    @Test
    public void testAdjustFontSizes() throws Exception {
        JSlider fontSizeSlider = (JSlider) getPrivateField(aqiView, "fontSizeSlider");
        // Change the slider value and assert the font sizes are updated
    }

    @Test
    public void testDisplay() throws Exception {
        // Call the display method directly with test parameters
        aqiView.display("Test City", "Test State", "Test Country", 100);
        // Assert that the text fields and label are set correctly
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

    private void handleDialog(String dialogTitle) {
        new Thread(() -> {
            while (true) {
                try {
                    // Checking every 100ms
                    sleep(100);
                    JDialog dialog = findDialog(dialogTitle);
                    if (dialog != null) {
                        SwingUtilities.invokeLater(dialog::dispose);
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private JDialog findDialog(String title) {
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                if (dialog.getTitle().equals(title) && dialog.isVisible()) {
                    return dialog;
                }
            }
        }
        return null;
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