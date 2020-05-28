package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.controller.InvoiceController;
import utn.dto.InvoiceDto;
import utn.exceptions.UserNotExistsException;
import utn.model.User;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
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

    private User getCurrentUser(String sessionToken) throws UserNotExistsException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistsException::new);
    }

}
