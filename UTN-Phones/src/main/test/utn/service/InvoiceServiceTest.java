package utn.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dao.InvoiceDao;
import utn.dao.mysql.UserMySQLDao;
import utn.dto.InvoiceDto;
import utn.dto.InvoicesBetweenDateDto;
import utn.dto.UserDto;
import utn.exceptions.NoExistsException;

import java.util.ArrayList;
import java.util.Date;
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
    public void testGetInvoicesByDateOk() {
        List<InvoiceDto> invoiceDtoList = new ArrayList<>();
        InvoiceDto r1 = new InvoiceDto(3, "2234567843", new Date(), new Date(), 10);
        InvoiceDto r2 = new InvoiceDto(3, "2234567843", new Date(), new Date(), 10);
        invoiceDtoList.add(r1);
        invoiceDtoList.add(r2);
        when(invoiceDao.getInvoicesByDate(any())).thenReturn(invoiceDtoList);
        List<InvoiceDto> invoicesByDate = invoiceService.getInvoicesByDate(any());
        assertEquals(invoicesByDate.size(), invoiceDtoList.size());
        verify(invoiceDao, times(1)).getInvoicesByDate(any());
    }

    @Test
    public void testGetByIdOk() throws NoExistsException {
        InvoiceDto invoiceDto = new InvoiceDto(2, "123", new Date(), new Date(), 10f);
        when(invoiceDao.getById(1)).thenReturn(invoiceDto);
        InvoiceDto byId = invoiceService.getById(1);
        assertEquals(byId.getLineNumber(), invoiceDto.getLineNumber());
        assertEquals(byId.getCallCount(), invoiceDto.getCallCount());
        verify(invoiceDao, times(1)).getById(1);
    }

    @Test(expected = NoExistsException.class)
    public void testGetByIdNoExistsException() throws NoExistsException {
        InvoiceDto invoiceDto = new InvoiceDto(2, "123", new Date(), new Date(), 10f);
        when(invoiceDao.getById(1)).thenReturn(null);
        invoiceService.getById(1);
    }


    @Test
    public void testRemoveOk() throws NoExistsException {
        InvoiceDto invoiceDto = new InvoiceDto(2, "123", new Date(), new Date(), 10f);
        when(invoiceDao.getById(1)).thenReturn(invoiceDto);
        doNothing().when(invoiceDao).delete(1);
        invoiceService.delete(1);
        assertEquals("123", invoiceDto.getLineNumber());
        assertEquals(Integer.valueOf(2), invoiceDto.getCallCount());
        verify(invoiceDao, times(1)).delete(1);
    }

    @Test(expected = NoExistsException.class)
    public void testRemoveNoExistsException() throws NoExistsException {
        when(invoiceDao.getById(1)).thenReturn(null);
        invoiceService.delete(1);
    }

    @Test
    public void testGetAllOk() {
        List<InvoiceDto> dtoList = new ArrayList<>();
        InvoiceDto invoiceDto = new InvoiceDto(2, "123", new Date(), new Date(), 10f);
        InvoiceDto r2 = new InvoiceDto(3, "2234567843", new Date(), new Date(), 10);
        dtoList.add(invoiceDto);
        dtoList.add(r2);
        when(invoiceDao.getAll()).thenReturn(dtoList);
        List<InvoiceDto> all = invoiceService.getAll();
        assertEquals(all.size(), dtoList.size());
        verify(invoiceDao, times(1)).getAll();
    }

    @Test
    public void testGetInvoicesBetweenDatesFromUserIdOk() throws NoExistsException {
        List<InvoiceDto> list = new ArrayList<>();
        InvoiceDto invoice = new InvoiceDto(2, "123", new Date(), new Date(), 10f);
        InvoiceDto r2 = new InvoiceDto(3, "2234567843", new Date(), new Date(), 10);
        list.add(invoice);
        list.add(r2);
        InvoicesBetweenDateDto invoicedto = new InvoicesBetweenDateDto(any(), "11-11-1911", "11-12-1992");
        UserDto user = new UserDto("ivan", "graciarena", 38877444, "ivanmdq22", "ivan@ivan.com", "mdq");
        when(userDao.getById(invoicedto.getUserID())).thenReturn(user);
        when(invoiceDao.getInvoicesBetweenDatesFromUserId(invoicedto)).thenReturn(list);
        List<InvoiceDto> invoicesBetweenDatesFromUserId = invoiceService.getInvoicesBetweenDatesFromUserId(invoicedto);
        assertEquals(invoicesBetweenDatesFromUserId.size(), list.size());
        verify(invoiceDao, times(1)).getInvoicesBetweenDatesFromUserId(invoicedto);
    }

    @Test(expected = NoExistsException.class)
    public void testGetInvoicesBetweenDatesFromUserIdNoExistsException() throws NoExistsException {
        InvoicesBetweenDateDto invoicedto = new InvoicesBetweenDateDto(any(), "11-11-1911", "11-12-1992");
        when(invoiceDao.getById(1)).thenReturn(null);
        invoiceService.getInvoicesBetweenDatesFromUserId(invoicedto);
    }

}