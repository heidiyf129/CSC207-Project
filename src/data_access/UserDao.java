package data_access;

import entity.User;
import java.util.HashMap;
import java.util.Map;

public class UserDao {
    // This is a simple in-memory store. Replace with real database access logic.
    private Map<String, User> users = new HashMap<>();


    public boolean isUsernameUnique(String username) {
        return !users.containsKey(username);
    }

    public void save(User user) {
        // Ideally, here you would use a database to save the user info
        users.put(user.getUsername(), user);
    }

    public User findByUsername(String username) {
        // Replace this with a database query in a real application
        return users.get(username);
    }
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // Additional methods for updating, deleting, etc. could be added here.
}
