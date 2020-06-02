package utn.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dto.InvoiceDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.UserNotExistsException;
import utn.exceptions.ValidationException;
import utn.service.InvoiceService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class InvoiceControllerTest {

    InvoiceController invoiceController;
    @Mock
    InvoiceService invoiceService;

    @Before
    public void setUp(){
        initMocks(this);
        invoiceController = new InvoiceController(invoiceService);
    }

    @Test
    public void testGetInvoicesByDateOk() throws ValidationException {
        List<InvoiceDto> invoiceDtoList = new ArrayList<>();
        InvoiceDto r1 = new InvoiceDto(3,"2234567843",null,null,10);
        InvoiceDto r2 = new InvoiceDto(3,"2234567843",null,null,10);
        invoiceDtoList.add(r1);
        invoiceDtoList.add(r2);
        when(invoiceService.getInvoicesByDate(any())).thenReturn(invoiceDtoList);
        List<InvoiceDto> invoiceDtoList2 = invoiceController.getInvoicesByDate(any());
        verify(invoiceService,times(1)).getInvoicesByDate(any());
        assertEquals(invoiceDtoList.size(),invoiceDtoList2.size());
    }

    @Test(expected = ValidationException.class)
    public void testGetInvoicesByDateIvalidadData() throws ValidationException {
        invoiceController.getInvoicesByDate( null);
    }
}
