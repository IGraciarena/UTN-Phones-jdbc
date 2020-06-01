package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.controller.InvoiceController;
import utn.dto.InvoiceDto;
import utn.dto.InvoicesBetweenDateDto;
import utn.dto.PhoneCallsBetweenDatesDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.User;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceWebController {

    SessionManager sessionManager;
    InvoiceController invoiceController;


    @Autowired
    public InvoiceWebController(SessionManager sessionManager, InvoiceController invoiceController) {
        this.invoiceController = invoiceController;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices(@RequestHeader("Authorization") String token) throws UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            List<InvoiceDto> invoiceDtos = invoiceController.getAll();
            return (invoiceDtos.size() > 0) ?
                    ResponseEntity.ok(invoiceDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity getById(@RequestHeader("Authorization") String token,@PathVariable Integer invoiceId) throws NoExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.EMPLOYEE)) {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceController.getById(invoiceId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /*
    Consulta de facturas del usuario logueado por rango de fechas.
    */
    @GetMapping("/client")
    public ResponseEntity<List<InvoiceDto>> getInvoicesBetweenDatesFromUserId(@RequestHeader("Authorization") String token, @RequestBody InvoicesBetweenDateDto invoiceDto) throws NoExistsException, UserNotExistsException {
        if (getCurrentUser(token).getUserType().equals(UserType.CLIENT)) {
            List<InvoiceDto> returnedInvoicesDtoList = invoiceController.getInvoicesBetweenDatesFromUserId(invoiceDto);
            return (returnedInvoicesDtoList.size() > 0) ?
                    ResponseEntity.ok(returnedInvoicesDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    private User getCurrentUser(String sessionToken) throws UserNotExistsException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistsException::new);
    }

}
