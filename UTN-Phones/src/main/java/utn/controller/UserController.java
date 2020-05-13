package utn.controller;

import utn.exceptions.UserAlreadyExistsException;
import utn.exceptions.UserNotexistException;
import utn.exceptions.ValidationException;
import utn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.service.UserService;

import java.util.List;
@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User login(String username, String password) throws UserNotexistException, ValidationException {
        if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new ValidationException();
        }
    }

    public User add(User user) throws UserAlreadyExistsException {
        return userService.add(user);
    }

    public void removeUser(Integer idUser) throws UserNotexistException {
        userService.removeUser(idUser);
    }


    public void updateUser(User user) throws UserNotexistException {
        userService.updateUser(user);
    }
    public User getById(Integer id){
        return userService.getById(id);
    }
}
