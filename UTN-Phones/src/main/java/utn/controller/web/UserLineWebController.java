package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.controller.UserLineController;
import utn.dto.UserLineDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.User;
import utn.model.UserLine;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userlines")
public class UserLineWebController {
    UserLineController userLineController;
    SessionManager sessionManager;

    @Autowired
    public UserLineWebController(UserLineController userLineController, SessionManager sessionManager) {
        this.userLineController = userLineController;
        this.sessionManager = sessionManager;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody UserLine userLine, @RequestHeader("Authorization") String token) throws AlreadyExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.created(getLocation(userLineController.add(userLine))).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity update(@RequestBody UserLine userLine, @RequestHeader("Authorization") String token) throws NoExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            userLineController.update(userLine);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{userLineId}")
    public ResponseEntity getById(@RequestHeader("Authorization") String token, @PathVariable Integer userLineId) throws NoExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.status(HttpStatus.OK).body(userLineController.getById(userLineId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserLineDto>> getAll(@RequestHeader("Authorization") String token) throws UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            List<UserLineDto> userLineDtos = userLineController.getAll();
            return (userLineDtos.size() > 0) ?
                    ResponseEntity.ok(userLineDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{userLineId}")
    public ResponseEntity remove(@RequestHeader("Authorization") String token, @PathVariable Integer userLineId) throws NoExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            userLineController.remove(userLineId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private User getCurrentUser(String sessionToken) throws UserNotExistsException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistsException::new);
    }

    private URI getLocation(UserLine userLine) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userLine.getId())
                .toUri();
    }

}
