package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.InvoiceDao;
import utn.dto.InvoiceDto;
import utn.exceptions.AlreadyExistsException;
import utn.model.Invoice;

import java.sql.Connection;
import java.util.List;

@Repository
public class InvoiceMySQLDao implements InvoiceDao {
    Connection con;

    @Autowired
    public InvoiceMySQLDao(Connection con){
        this.con = con;
    }

    @Override
    public void add(Invoice value) throws AlreadyExistsException {

    }

    @Override
    public void update(Invoice value) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public InvoiceDto getById(Integer id) {
        return null;
    }

    @Override
    public List<InvoiceDto> getAll() {
        return null;
    }
}
