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
import utn.model.UserLine;
import utn.session.SessionManager;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class UserLineWebController {
    UserLineController userLineController;
    SessionManager sessionManager;

    @Autowired
    public UserLineWebController(UserLineController userLineController, SessionManager sessionManager) {
        this.userLineController = userLineController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/backoffice/userlines")
    public ResponseEntity add(@RequestBody UserLine userLine, @RequestHeader("Authorization") String token) throws AlreadyExistsException, UserNotExistsException {
        return ResponseEntity.created(getLocation(userLineController.add(userLine))).build();
    }

    @PutMapping("/backoffice/userlines")
    public ResponseEntity update(@RequestBody UserLine userLine, @RequestHeader("Authorization") String token) throws NoExistsException {
        userLineController.update(userLine);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/backoffice/userlines/{userLineId}")
    public ResponseEntity remove(@RequestHeader("Authorization") String token, @PathVariable Integer userLineId) throws NoExistsException {
        userLineController.remove(userLineId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/backoffice/userlines/{userLineId}")
    public ResponseEntity getById(@RequestHeader("Authorization") String token, @PathVariable Integer userLineId) throws NoExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(userLineController.getById(userLineId));
    }

    //todo getByUserInSession del lado del cliente ("/api/userlines")

    @GetMapping("/backoffice/userlines")
    public ResponseEntity<List<UserLineDto>> getAll(@RequestHeader("Authorization") String token) throws UserNotExistsException {
        List<UserLineDto> userLineDtos = userLineController.getAll();
        return (userLineDtos.size() > 0) ?
                ResponseEntity.ok(userLineDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private URI getLocation(UserLine userLine) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userLine.getId())
                .toUri();
    }

}
