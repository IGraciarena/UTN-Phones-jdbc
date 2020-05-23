package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.InvoiceDao;
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
    public Invoice add(Invoice value) throws AlreadyExistsException {
        return null;
    }

    @Override
    public void update(Invoice value) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public Invoice getById(Integer id) {
        return null;
    }

    @Override
    public List<Invoice> getAll() {
        return null;
    }
}
