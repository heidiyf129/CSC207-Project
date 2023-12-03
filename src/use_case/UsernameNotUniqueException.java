package use_case;

public class UsernameNotUniqueException extends Exception {
    public UsernameNotUniqueException(String message) {
        super(message);
    }
}
