package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.InvoiceDao;
import utn.dto.InvoiceDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.Invoice;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    InvoiceDao dao;

    @Autowired
    public InvoiceService(InvoiceDao invoiceDao) {
        this.dao = invoiceDao;
    }

    public InvoiceDto getById(Integer id) throws NoExistsException {
        InvoiceDto invoice = dao.getById(id);
        Optional.ofNullable(invoice).orElseThrow(NoExistsException::new);
        return invoice;
    }

    public void add(Invoice invoice) throws AlreadyExistsException {
        dao.add(invoice);
    }

    public void remove(Integer id) throws NoExistsException {
        InvoiceDto invoice = dao.getById(id);
        Optional.ofNullable(invoice).orElseThrow(NoExistsException::new);
        dao.remove(id);
    }

    public void update(Invoice value) throws NoExistsException {
        InvoiceDto invoice = dao.getById(value.getId());
        Optional.ofNullable(invoice).orElseThrow(NoExistsException::new);
        dao.update(value);
    }

    public List<InvoiceDto> getAll() {
        return dao.getAll();
    }
}
