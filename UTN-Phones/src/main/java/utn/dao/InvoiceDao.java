package utn.dao;

import utn.dto.InvoiceDto;
import utn.model.Invoice;

import java.util.List;

public interface InvoiceDao extends AbstractDao<Invoice> {
    InvoiceDto getById(Integer id);

    List<InvoiceDto> getAll();



}
