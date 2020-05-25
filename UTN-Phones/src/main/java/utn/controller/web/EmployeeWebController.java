package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.controller.*;
import utn.dto.InvoiceDto;
import utn.dto.RateDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.dto.UserDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.User;
import utn.model.UserLine;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeWebController {
    UserController userController; //todo manejo de clientes
    UserLineController userLineController;//todo manejo de alta baja y suspencion de lineas listo<<---
    RateController rateController;
    PhoneCallController phoneCallController;//todo consulta de llamadas por usuario
    InvoiceController invoiceController; //todo consulta de facturacion (getById) listo<<<<---
    SessionManager sessionManager;

    @Autowired
    public EmployeeWebController(UserController userController, UserLineController userLineController, RateController rateController, PhoneCallController phoneCallController, InvoiceController invoiceController, SessionManager sessionManager) {
        this.invoiceController = invoiceController;
        this.phoneCallController = phoneCallController;
        this.rateController = rateController;
        this.userController = userController;
        this.userLineController = userLineController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody User user, @RequestHeader("Authorization") String token) throws AlreadyExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userController.add(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity removeUser(@PathVariable Integer idUser, @RequestHeader("Authorization") String token) throws UserNotExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userController.removeUser(idUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody User user, @RequestHeader("Authorization") String token) throws UserNotExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userController.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            List<UserDto> userList = userController.getAll();
            return (userList.size() > 0) ?
                    ResponseEntity.ok(userList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    ////////////////////////////////////////////RATE///////////////////////////////////////////////////////
    @GetMapping("/rate/")
    public ResponseEntity<List<RateDto>> getAllRates(@RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            List<RateDto> rateDtos = rateController.getAll();
            return (rateDtos.size() > 0) ?
                    ResponseEntity.ok(rateDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/rate/{rateId}")
    public ResponseEntity<RateDto> getByIdRate(@RequestHeader("Authorization") String token, @PathVariable Integer rateId) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.status(HttpStatus.OK).body(rateController.getById(rateId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    ////////////////////////////////////////////USER LINES///////////////////////////////////////////////////////
    @PostMapping("/userline")
    public ResponseEntity addUserLine(@RequestBody UserLine userLine, @RequestHeader("Authorization") String token) throws AlreadyExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userLineController.add(userLine);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/userline")
    public ResponseEntity updateUserLine(@RequestBody UserLine userLine, @RequestHeader("Authorization") String token) throws UserNotExistsException, NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userLineController.update(userLine);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/userLine/{userLineId")
    public ResponseEntity removeUserLine(@RequestHeader("Authorization") String token, @PathVariable Integer userLineId) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            userLineController.remove(userLineId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    ////////////////////////////////////////////INVOICE////////////////////////////////////////////////////////
    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity getByIdInvoice(@RequestHeader("Authorization") String token, @PathVariable Integer invoiceId) throws NoExistsException {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceController.getById(invoiceId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/invoice/")
    public ResponseEntity<List<InvoiceDto>> getAllInvoices(@RequestHeader("Authorization") String token) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            List<InvoiceDto> invoiceDtos = invoiceController.getAll();
            return (invoiceDtos.size() > 0) ?
                    ResponseEntity.ok(invoiceDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    ////////////////////////////////////////////PHONECALLS////////////////////////////////////////////////////////
    @GetMapping("/phonecalls/{userId}")
    public ResponseEntity<List<ReturnedPhoneCallDto>> getAllPhoneCallsFromUserId(@RequestHeader("Authorization") String token, @PathVariable Integer userId) {
        User currentUser = sessionManager.getCurrentUser(token);
        if (currentUser.getUserType().equals(UserType.EMPLOYEE)) {
            List<ReturnedPhoneCallDto> returnedPhoneCallDtoList = phoneCallController.getAllPhoneCallsFromUserId(userId);
            return (returnedPhoneCallDtoList.size() > 0) ?
                    ResponseEntity.ok(returnedPhoneCallDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
