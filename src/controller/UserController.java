package controller;

import model.User;
import service.UserService;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public boolean addUser(User user) {
        return userService.addUser(user);
    }


}
