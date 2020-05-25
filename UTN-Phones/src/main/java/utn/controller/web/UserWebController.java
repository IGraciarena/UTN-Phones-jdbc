package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.controller.UserController;
import utn.dto.UserDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.User;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.util.List;

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
    public ResponseEntity add(@RequestBody User user, @RequestHeader("Authorization") String token) throws AlreadyExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userController.add(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity remove(@PathVariable Integer idUser, @RequestHeader("Authorization") String token) throws UserNotExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userController.removeUser(idUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody User user, @RequestHeader("Authorization") String token) throws UserNotExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userController.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/mostCalled/{lineNumber}")
    public ResponseEntity getMostCalledNumber(@RequestHeader("Authorization") String token, @PathVariable String lineNumber) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.CLIENT)) {
            return ResponseEntity.status(HttpStatus.OK).body(userController.getMostCalledNumber(lineNumber));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            List<UserDto> userList = userController.getAll();
            return (userList.size() > 0) ?
                    ResponseEntity.ok(userList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
