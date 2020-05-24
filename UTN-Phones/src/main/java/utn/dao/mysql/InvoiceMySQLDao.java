package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.InvoiceDao;
import utn.dto.InvoiceDto;
import utn.exceptions.AlreadyExistsException;
import utn.model.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static utn.dao.mysql.MySQLUtils.BASE_INVOICES_QUERY;
import static utn.dao.mysql.MySQLUtils.GETBYID_INVOICES_QUERY;

@Repository
public class InvoiceMySQLDao implements InvoiceDao {
    Connection con;
    UserLineMySQLDao userLineMySQLDao;

    @Autowired
    public InvoiceMySQLDao(Connection con) {
        this.con = con;
    }

    @Override
    public void add(Invoice value) throws AlreadyExistsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Invoice value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InvoiceDto getById(Integer id) {
        InvoiceDto invoiceDto = null;
        try {
            PreparedStatement ps = con.prepareStatement(GETBYID_INVOICES_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                invoiceDto = createInvoice(rs);
            }
            ps.close();
            rs.close();
            return invoiceDto;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener datos de la factura", e);
        }
    }

    private InvoiceDto createInvoice(ResultSet rs) throws SQLException {
        InvoiceDto invoiceDto = new InvoiceDto(
                rs.getInt("call_count"),
                userLineMySQLDao.getLineNumber(rs.getInt("line_number")),
                new Date(rs.getDate("date_emission").getTime()),
                new Date(rs.getDate("date_expiration").getTime()),
                rs.getFloat("price_total")
        );
        return invoiceDto;
    }

    @Override
    public List<InvoiceDto> getAll() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(BASE_INVOICES_QUERY);
            List<InvoiceDto> invoiceDtos=new ArrayList<>();
            while (rs.next()){
                invoiceDtos.add(createInvoice(rs));
            }
            rs.close();
            st.close();
            return invoiceDtos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la lista de facturas",e);
        }
    }
}
