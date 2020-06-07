package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.controller.UserController;
import utn.dto.UserDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.User;
import utn.session.SessionManager;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class UserWebController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public UserWebController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/backoffice/users")
    public ResponseEntity add(@RequestBody User user, @RequestHeader("Authorization") String token) throws AlreadyExistsException, UserNotExistsException {
        return ResponseEntity.created(getLocation(userController.add(user))).build();
    }

    @DeleteMapping("backoffice/users/{idUser}")
    public ResponseEntity remove(@PathVariable Integer idUser, @RequestHeader("Authorization") String token) throws UserNotExistsException {
        userController.removeUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/backoffice/users")
    public ResponseEntity<Object> update(@RequestBody User user, @RequestHeader("Authorization") String token) throws UserNotExistsException {
        userController.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/backoffice/users/most-called/{lineNumber}")
    public ResponseEntity getMostCalledNumber(@RequestHeader("Authorization") String token, @PathVariable String lineNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(userController.getMostCalledNumber(lineNumber));
    }

    @GetMapping("/backoffice/users")
    public ResponseEntity<List<UserDto>> getAll(@RequestHeader("Authorization") String token) {
        List<UserDto> userList = userController.getAll();
        return (userList.size() > 0) ?
                ResponseEntity.ok(userList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/backoffice/users/{userId}")
    public ResponseEntity getById(@RequestHeader("Authorization") String token,@PathVariable Integer userId){
        return ResponseEntity.status(HttpStatus.OK).body(userController.getById(userId));
    }
    //todo getById

    private URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }
}
