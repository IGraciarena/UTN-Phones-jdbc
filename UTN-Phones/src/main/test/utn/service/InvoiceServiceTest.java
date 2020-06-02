package utn.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dao.InvoiceDao;
import utn.dao.mysql.UserMySQLDao;
import utn.dto.InvoiceDto;
import utn.model.PhoneCall;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class InvoiceServiceTest {
    InvoiceService invoiceService;
    @Mock
    InvoiceDao invoiceDao;
    @Mock
    UserMySQLDao userDao;

    @Before
    public void setUp() {
        initMocks(this);
        invoiceService = new InvoiceService(invoiceDao, userDao);
    }

    @Test
    public void testGetInvoicesByDateOk(){
        List<InvoiceDto> invoiceDtoList = new ArrayList<>();
        InvoiceDto r1 = new InvoiceDto(3,"2234567843",null,null,10);
        InvoiceDto r2 = new InvoiceDto(3,"2234567843",null,null,10);
        invoiceDtoList.add(r1);
        invoiceDtoList.add(r2);
        when(invoiceDao.getInvoicesByDate(any())).thenReturn(invoiceDtoList);
        assertEquals(2,invoiceDtoList.size());
        verify(invoiceDao,times(1)).getInvoicesByDate(any());
    }

}
