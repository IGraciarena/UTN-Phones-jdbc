package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.model.Invoice;
import utn.service.InvoiceService;

@Controller
public class InvoiceController {
    InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    public Invoice add(Invoice invoice){
        return invoiceService.add(invoice);
    }
    public void remove(Integer id){
        invoiceService.remove(id);
    }
    public void update(Invoice invoice){
        invoiceService.update(invoice);
    }
    public Invoice getById(Integer id){
        return invoiceService.getById(id);
    }
}
