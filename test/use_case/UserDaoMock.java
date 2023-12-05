package use_case;

import data_access.UserDao;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserDaoMock extends UserDao {
    private final Map<String, User> mockUsers = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        return mockUsers.get(username);
    }

    public void addUser(User user) {
        mockUsers.put(user.getUsername(), user);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        return !mockUsers.containsKey(username);
    }
}
