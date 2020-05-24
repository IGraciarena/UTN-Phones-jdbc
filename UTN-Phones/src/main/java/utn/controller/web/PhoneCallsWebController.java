package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.controller.PhoneCallController;
import utn.controller.UserController;
import utn.dto.PhoneCallDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.PhoneCall;
import utn.model.User;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.util.List;

@RestController
@RequestMapping("/api/phonecall")
public class PhoneCallsWebController {
    PhoneCallController phoneCallController;
    SessionManager sessionManager;

    @Autowired
    public PhoneCallsWebController(PhoneCallController phoneCallController,SessionManager sessionManager){
        this.phoneCallController = phoneCallController;
        this.sessionManager = sessionManager;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody PhoneCallDto phoneCallDto, @RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            phoneCallController.addPhoneCall(phoneCallDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<ReturnedPhoneCallDto>> getAll(@RequestHeader("Authorization") String token){
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)){
            List<ReturnedPhoneCallDto> phoneCalls = phoneCallController.getAll();
            return (phoneCalls.size() > 0) ? ResponseEntity.ok(phoneCalls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/{idPhoneCall}")
    public ResponseEntity getById(@PathVariable Integer idPhoneCall,@RequestHeader("Authorization") String token) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)){
            return ResponseEntity.status(HttpStatus.OK).body(phoneCallController.getById(idPhoneCall));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @DeleteMapping("/{idPhoneCall}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token,@PathVariable Integer idPhoneCall) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)){
            phoneCallController.remove(idPhoneCall);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PutMapping
    public ResponseEntity update(@RequestBody PhoneCall phoneCall, @RequestHeader("Authorization")String token) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)){
            phoneCallController.update(phoneCall);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
