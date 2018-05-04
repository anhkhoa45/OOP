package model;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    public static Map<String, User> users = new HashMap<>();

    public static User getUserByUsername(String username) {
        return users.get(username);
    }

    public static void addUser(User user) {
        users.put(user.getName(), user);
    }
}
