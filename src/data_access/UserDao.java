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

    public User findByUsername(String username) {
        // Replace this with a database query in a real application
        return users.get(username);
    }
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }
}
