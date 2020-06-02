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
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.PhoneCall;
import utn.model.User;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phonecall")
public class PhoneCallWebController {
    PhoneCallController phoneCallController;
    SessionManager sessionManager;

    @Autowired
    public PhoneCallWebController(PhoneCallController phoneCallController, SessionManager sessionManager) {
        this.phoneCallController = phoneCallController;
        this.sessionManager = sessionManager;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody PhoneCallDto phoneCallDto, @RequestHeader("Authorization") String token) throws UserNotExistsException, AlreadyExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.created(getLocation(phoneCallController.add(phoneCallDto))).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<ReturnedPhoneCallDto>> getAll(@RequestHeader("Authorization") String token) throws UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            List<ReturnedPhoneCallDto> phoneCalls = phoneCallController.getAll();
            return (phoneCalls.size() > 0) ? ResponseEntity.ok(phoneCalls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{idPhoneCall}")
    public ResponseEntity getById(@PathVariable Integer idPhoneCall, @RequestHeader("Authorization") String token) throws NoExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.status(HttpStatus.OK).body(phoneCallController.getById(idPhoneCall));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @DeleteMapping("/{idPhoneCall}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable Integer idPhoneCall) throws NoExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            phoneCallController.remove(idPhoneCall);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PutMapping
    public ResponseEntity update(@RequestBody PhoneCall phoneCall, @RequestHeader("Authorization") String token) throws NoExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            phoneCallController.update(phoneCall);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /*
    Consulta todas las llamadas a partir de un ID de usuario
     */
    @GetMapping("/employee/{userId}")
    public ResponseEntity<List<ReturnedPhoneCallDto>> getAllPhoneCallsFromUserId(@RequestHeader("Authorization") String token, @PathVariable Integer userId) throws UserNotExistsException, NoExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            List<ReturnedPhoneCallDto> returnedPhoneCallDtoList = phoneCallController.getAllPhoneCallsFromUserId(userId);
            return (returnedPhoneCallDtoList.size() > 0) ?
                    ResponseEntity.ok(returnedPhoneCallDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /*
    2) Consulta de llamadas del usuario logueado por rango de fechas.
    .*/
    @GetMapping("/client")
    public ResponseEntity<List<ReturnedPhoneCallDto>> getPhoneCallsFromUserIdBetweenDates(@RequestHeader("Authorization") String token, @RequestBody PhoneCallsBetweenDatesDto phonecallDto) throws UserNotExistsException, NoExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.CLIENT)) {
            List<ReturnedPhoneCallDto> returnedPhoneCallDtoList = phoneCallController.getPhoneCallsFromUserIdBetweenDates(phonecallDto);
            return (returnedPhoneCallDtoList.size() > 0) ?
                    ResponseEntity.ok(returnedPhoneCallDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /*
     Consulta de destinos más llamados por el usuario
     */
    @GetMapping("/client/{userID}")
    public ResponseEntity<List<String>> getMostCalledDestinsByUserId(@RequestHeader("Authorization") String token, @PathVariable Integer userID) throws UserNotExistsException, NoExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.CLIENT)) {
            List<String> returnedPhoneCallDtoList = phoneCallController.getMostCalledDestinsByUserId(userID);
            return (returnedPhoneCallDtoList.size() > 0) ?
                    ResponseEntity.ok(returnedPhoneCallDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public User getCurrentUser(String sessionToken) throws UserNotExistsException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistsException::new);
    }

    private URI getLocation(Integer idPhoneCall) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(idPhoneCall)
                .toUri();
    }
}
