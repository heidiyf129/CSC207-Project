package data_access;
import entity.User;
import use_case.AnalyzeAirQuality;

import javax.swing.*;
import java.io.*;
import java.util.*;

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
}
