package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.controller.UserLineController;
import utn.dto.UserLineDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.User;
import utn.model.UserLine;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.util.List;

@RestController
@RequestMapping("/api/userline")
public class UserLineWebController {
    UserLineController userLineController;
    SessionManager sessionManager;

    @Autowired
    public UserLineWebController(UserLineController userLineController, SessionManager sessionManager) {
        this.userLineController = userLineController;
        this.sessionManager = sessionManager;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody UserLine userLine, @RequestHeader("Authorization") String token) throws AlreadyExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userLineController.add(userLine);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity update(@RequestBody UserLine userLine, @RequestHeader("Authorization") String token) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userLineController.update(userLine);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{userLineId}")
    public ResponseEntity getById(@RequestHeader("Authorization") String token, @PathVariable Integer userLineId) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.status(HttpStatus.OK).body(userLineController.getById(userLineId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserLineDto>> getAll(@RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            List<UserLineDto> userLineDtos = userLineController.getAll();
            return (userLineDtos.size() > 0) ?
                    ResponseEntity.ok(userLineDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{userLineId")
    public ResponseEntity remove(@RequestHeader("Authorization") String token, @PathVariable Integer userLineId) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userLineController.remove(userLineId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
