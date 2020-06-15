package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.controller.RateController;
import utn.dto.RateDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.Rate;
import utn.session.SessionManager;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class RateWebController {
    RateController rateController;
    SessionManager sessionManager;

    @Autowired
    public RateWebController(RateController rateController, SessionManager sessionManager) {
        this.rateController = rateController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/backoffice/rates/")
    public ResponseEntity add(@RequestBody Rate rate, @RequestHeader("Authorization") String token) throws UserNotExistsException, AlreadyExistsException {
        return ResponseEntity.created(getLocation(rateController.add(rate))).build();
    }

    @PutMapping("/backoffice/rates/")
    public ResponseEntity update(@RequestBody Rate rate, @RequestHeader("Authorization") String token) throws NoExistsException {
        rateController.update(rate);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/backoffice/rates/{rateId}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Integer rateId) throws NoExistsException {
        rateController.delete(rateId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/backoffice/rates/")
    public ResponseEntity<List<RateDto>> getAll(@RequestHeader("Authorization") String token) {
        List<RateDto> rateDtos = rateController.getAll();
        return (rateDtos.size() > 0) ?
                ResponseEntity.ok(rateDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/backoffice/rates/{rateId}")
    public ResponseEntity<RateDto> getById(@RequestHeader("Authorization") String token, @PathVariable Integer rateId) throws NoExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(rateController.getById(rateId));
    }

    private URI getLocation(Rate rate) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rate.getId())
                .toUri();
    }

}
