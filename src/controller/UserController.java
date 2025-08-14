package controller;

import model.User;
import service.UserService;

import java.util.List;
import java.util.UUID;

public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public List<User> searchUserByName(String name) {
        return userService.searchUserByName(name);
    }

    public User getUserById(UUID id) {
        return userService.getUserById(id);
    }


    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public boolean addUser(User user) {
        return userService.addUser(user);
    }

    public boolean updateUser(User user) {
        return userService.updateUser(user);
    }
}
