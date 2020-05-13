package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.controller.UserController;
import utn.exceptions.UserAlreadyExistsException;
import utn.exceptions.UserNotAuthorizedException;
import utn.exceptions.UserNotexistException;
import utn.model.User;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

@RestController
@RequestMapping("/api/user")
public class UserWebController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public UserWebController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody User user,@RequestHeader("Authorization") String token) throws UserAlreadyExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)){
            return ResponseEntity.status(HttpStatus.CREATED).body(userController.add(user));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity removeUser(@PathVariable Integer idUser,@RequestHeader("Authorization") String token) throws UserNotexistException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)){
            userController.removeUser(idUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody User user, @RequestHeader("Authorization")String token) throws UserNotexistException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)){
            userController.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

//    public void checkUserType(String token) throws UserNotAuthorizedException {
//        User currentUser = sessionManager.getCurrentUser(token);
//        if (currentUser.getUserType().equals(UserType.EMPLOYEE)){
//            throw new UserNotAuthorizedException();
//        }
//    }
}
