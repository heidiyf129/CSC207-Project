package app;
import app.Main;
import org.junit.Test;

public class MainTest {

    @Test
    public void testMain() {
        // The test just invokes the main method to ensure no exceptions are thrown
        String[] args = {};
        Main.main(args);
        // No assertions needed as we're just checking for exceptions
    }
}