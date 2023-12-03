package app;
import view.MainView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().displayMainView());
    }
}