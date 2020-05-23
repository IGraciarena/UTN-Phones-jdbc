package utn.dao;

import utn.model.Invoice;

public interface InvoiceDao extends AbstractDao<Invoice> {
    Invoice getById(Integer id);


}
