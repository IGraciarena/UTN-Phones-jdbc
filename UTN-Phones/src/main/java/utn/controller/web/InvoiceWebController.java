package utn.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.controller.InvoiceController;
import utn.dto.DateDto;
import utn.dto.InvoiceDto;
import utn.dto.InvoicesBetweenDateDto;
import utn.exceptions.NoExistsException;
import utn.exceptions.ValidationException;
import utn.session.SessionManager;

import java.util.List;

@RestController
@RequestMapping
public class InvoiceWebController {

    SessionManager sessionManager;
    InvoiceController invoiceController;

    @Autowired
    public InvoiceWebController(SessionManager sessionManager, InvoiceController invoiceController) {
        this.invoiceController = invoiceController;
        this.sessionManager = sessionManager;
    }

    // en este no hace falta trae todo
    @GetMapping("/backoffice/invoices/")
    public ResponseEntity<List<InvoiceDto>> getAllInvoicesEmployee(@RequestHeader("Authorization") String token) {
        List<InvoiceDto> invoiceDtos = invoiceController.getAll();
        return (invoiceDtos.size() > 0) ?
                ResponseEntity.ok(invoiceDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    //todo deberiamos tomar el id del usuario logueado de la session
//    @GetMapping("/api/invoices/")
//    public ResponseEntity<List<InvoiceDto>> getAllInvoicesClient(@RequestHeader("Authorization") String token) {
//        List<InvoiceDto> invoiceDtos = invoiceController.getAll();
//        return (invoiceDtos.size() > 0) ?
//                ResponseEntity.ok(invoiceDtos) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

    @GetMapping("/backoffice/invoices/{invoiceId}")
    public ResponseEntity getByIdEmployee(@RequestHeader("Authorization") String token, @PathVariable Integer invoiceId) throws NoExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceController.getById(invoiceId));
    }

    // casi seguro que esta mal esto
//    @GetMapping("/api/invoices/{invoiceId}")
//    public ResponseEntity getByIdClient(@RequestHeader("Authorization") String token, @PathVariable Integer invoiceId) throws NoExistsException {
//        return ResponseEntity.status(HttpStatus.OK).body(invoiceController.getById(invoiceId));
//    }

    /*
    ENDPOINT PARCIAL.
    Hago que reciba un dto con la fecha por que no se si puede pasar la fecha por la url, como no me levanta el proyecto no lo puedo probar.
    */
    @GetMapping("/api/invoices/date")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByDate(@RequestHeader("Authorization") String token, @RequestBody DateDto dateDto) throws ValidationException {
        List<InvoiceDto> returnedInvoicesDtoList = invoiceController.getInvoicesByDate(dateDto);
        return (returnedInvoicesDtoList.size() > 0) ?
                ResponseEntity.ok(returnedInvoicesDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
    Consulta de facturas del usuario logueado por rango de fechas.
    */
    @GetMapping("/api/invoices/dates/")
    public ResponseEntity<List<InvoiceDto>> getInvoicesBetweenDatesFromUserId(@RequestHeader("Authorization") String token, @RequestBody InvoicesBetweenDateDto invoiceDto) throws NoExistsException {
        List<InvoiceDto> returnedInvoicesDtoList = invoiceController.getInvoicesBetweenDatesFromUserId(invoiceDto);
        return (returnedInvoicesDtoList.size() > 0) ?
                ResponseEntity.ok(returnedInvoicesDtoList) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
