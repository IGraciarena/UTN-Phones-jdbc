package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.InvoiceDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.Invoice;

@Service
public class InvoiceService {
    InvoiceDao dao;

    @Autowired
    public InvoiceService(InvoiceDao invoiceDao){
        this.dao = invoiceDao;
    }

    public Invoice getById(Integer id) {
        return dao.getById(id);
    }

    public void add(Invoice invoice) throws AlreadyExistsException {
        dao.add(invoice);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(Invoice invoice) {
        dao.update(invoice);
    }
}
