package controller;

import execptions.UserAlreadyExistsExecption;
import execptions.UserNotexistException;
import execptions.ValidationExecption;
import model.User;
import service.UserService;

import java.util.List;

public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User login(String username, String password) throws UserNotexistException, ValidationExecption {
        if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new ValidationExecption();
        }
    }

    public User add(User user) throws UserAlreadyExistsExecption {
        return userService.add(user);
    }

    public void removeUser(User user) throws UserNotexistException {
        userService.removeUser(user);
    }

    public void removeUsers(List<User> userList) throws UserNotexistException {
        for (User u : userList) {
            userService.removeUser(u);
        }
    }

    public void updateUser(User user) throws UserNotexistException {
        userService.updateUser(user);
    }
}
