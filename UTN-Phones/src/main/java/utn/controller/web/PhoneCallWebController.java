package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.controller.PhoneCallController;
import utn.dto.PhoneCallDto;
import utn.dto.PhoneCallsBetweenDatesDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.exceptions.ValidationException;
import utn.model.PhoneCall;
import utn.session.SessionManager;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class PhoneCallWebController {
    PhoneCallController phoneCallController;
    SessionManager sessionManager;

    @Autowired
    public PhoneCallWebController(PhoneCallController phoneCallController, SessionManager sessionManager) {
        this.phoneCallController = phoneCallController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/backoffice/phonecalls/")
    public ResponseEntity add(@RequestBody PhoneCallDto phoneCallDto, @RequestHeader("Authorization") String token) throws UserNotExistsException, ValidationException, NoExistsException {
        return ResponseEntity.created(getLocation(phoneCallController.addPhoneCall(phoneCallDto))).build();
    }

    @PutMapping("/backoffice/phonecalls/")
    public ResponseEntity update(@RequestBody PhoneCall phoneCall, @RequestHeader("Authorization") String token) throws NoExistsException {
        phoneCallController.update(phoneCall);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/backoffice/phonecalls/{idPhoneCall}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Integer idPhoneCall) throws NoExistsException {
        phoneCallController.delete(idPhoneCall);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/backoffice/phonecalls/")
    public ResponseEntity<List<ReturnedPhoneCallDto>> getAll(@RequestHeader("Authorization") String token) {
        List<ReturnedPhoneCallDto> phoneCalls = phoneCallController.getAll();
        return (phoneCalls.size() > 0) ? ResponseEntity.ok(phoneCalls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/api/phonecalls/{idPhoneCall}")
    public ResponseEntity getByIdClient(@PathVariable Integer idPhoneCall, @RequestHeader("Authorization") String token) throws NoExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(phoneCallController.getById(idPhoneCall));
    }

    @GetMapping("/backoffice/phonecalls/{idPhoneCall}")
    public ResponseEntity getByIdEmployee(@PathVariable Integer idPhoneCall, @RequestHeader("Authorization") String token) throws NoExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(phoneCallController.getById(idPhoneCall));
    }

    /*
    Consulta todas las llamadas a partir de un ID de usuario
     */
    @GetMapping("/backoffice/phonecalls/users/{userId}")
    public ResponseEntity<List<ReturnedPhoneCallDto>> getAllPhoneCallsFromUserId(@RequestHeader("Authorization") String token, @PathVariable Integer userId) throws NoExistsException {
        List<ReturnedPhoneCallDto> returnedPhoneCallDtoList = phoneCallController.getAllPhoneCallsFromUserId(userId);
        return (returnedPhoneCallDtoList.size() > 0) ?
                ResponseEntity.ok(returnedPhoneCallDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
    2) Consulta de llamadas del usuario logueado por rango de fechas.
    .*/
    @GetMapping("/api/phonecalls/dates/")
    public ResponseEntity<List<ReturnedPhoneCallDto>> getPhoneCallsFromUserIdBetweenDates(@RequestHeader("Authorization") String token, @RequestBody PhoneCallsBetweenDatesDto phonecallDto) throws NoExistsException {
        List<ReturnedPhoneCallDto> returnedPhoneCallDtoList = phoneCallController.getPhoneCallsFromUserIdBetweenDates(phonecallDto, sessionManager.getCurrentUser(token).getId());
        return (returnedPhoneCallDtoList.size() > 0) ?
                ResponseEntity.ok(returnedPhoneCallDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
     Consulta de destinos más llamados por el usuario
     */
    @GetMapping("/api/phonecalls/top5/")
    public ResponseEntity<List<String>> getTop5DestinationsByUserId(@RequestHeader("Authorization") String token) throws NoExistsException {
        List<String> returnedPhoneCallDtoList = phoneCallController.getMostCalledDestinsByUserId(sessionManager.getCurrentUser(token).getId());
        return (returnedPhoneCallDtoList.size() > 0) ?
                ResponseEntity.ok(returnedPhoneCallDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private URI getLocation(Integer idPhoneCall) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(idPhoneCall)
                .toUri();
    }
}
