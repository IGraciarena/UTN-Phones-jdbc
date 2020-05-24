package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.dto.InvoiceDto;
import utn.exceptions.AlreadyExistsException;
import utn.model.Invoice;
import utn.service.InvoiceService;

import java.util.List;

@Controller
public class InvoiceController {
    InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void add(Invoice invoice) throws AlreadyExistsException {
        invoiceService.add(invoice);
    }

    public void remove(Integer id) {
        invoiceService.remove(id);
    }

    public void update(Invoice invoice) {
        invoiceService.update(invoice);
    }

    public InvoiceDto getById(Integer id) {
        return invoiceService.getById(id);
    }

    public List<InvoiceDto> getAll() {
        return invoiceService.getAll();
    }
}
