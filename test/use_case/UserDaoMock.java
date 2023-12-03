package use_case;

import data_access.UserDao;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserDaoMock extends UserDao {
    private Map<String, User> mockusers = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        return mockusers.get(username);
    }

    @Override
    public void save(User user) {
        mockusers.put(user.getUsername(), user); // Corrected here
    }

    @Override
    public void addUser(User user) {
        mockusers.put(user.getUsername(), user);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        return !mockusers.containsKey(username);
    }
}
