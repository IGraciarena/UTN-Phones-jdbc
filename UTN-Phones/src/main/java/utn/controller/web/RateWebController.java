package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.controller.RateController;
import utn.dto.RateDto;
import utn.model.Rate;
import utn.model.User;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.util.List;

@RestController
@RequestMapping("/api/rate")
public class RateWebController {
    RateController rateController;
    SessionManager sessionManager;

    @Autowired
    public RateWebController(RateController rateController, SessionManager sessionManager) {
        this.rateController = rateController;
        this.sessionManager = sessionManager;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Rate rate, @RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            rateController.add(rate);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("{rateId}")
    public ResponseEntity remove(@RequestHeader("Authorization") String token, @PathVariable Integer rateId) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            rateController.remove(rateId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<RateDto>> getAll(@RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            List<RateDto> rateDtos = rateController.getAll();
            return (rateDtos.size() > 0) ?
                    ResponseEntity.ok(rateDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{rateId}")
    public ResponseEntity<RateDto> getById(@RequestHeader("Authorization") String token, @PathVariable Integer rateId) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.status(HttpStatus.OK).body(rateController.getById(rateId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Rate rate, @RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            rateController.update(rate);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
