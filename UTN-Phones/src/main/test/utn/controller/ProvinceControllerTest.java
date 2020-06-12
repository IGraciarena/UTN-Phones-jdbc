package utn.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.Province;
import utn.service.ProvinceService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProvinceControllerTest {

    @Mock
    ProvinceService provinceService;
    ProvinceController provinceController;

    @Before
    public void setUp() {
        initMocks(this);
        provinceController = new ProvinceController(provinceService);
    }

    @Test
    public void testAddOk() throws AlreadyExistsException {
        Province province = new Province(1, "calamuchita");
        doNothing().when(provinceService).add(province);
        provinceController.add(province);
    }

    @Test(expected = AlreadyExistsException.class)
    public void testAddAlreadyExistsException() throws AlreadyExistsException {
        Province province = new Province(1, "calamuchita");
        doThrow(new AlreadyExistsException()).when(provinceService).add(province);
        provinceController.add(province);
        verify(provinceService, times(1)).add(province);
    }

    @Test
    public void testRemoveOk() throws NoExistsException {
        doNothing().when(provinceService).remove(1);
        provinceController.remove(1);
        verify(provinceService, times(1)).remove(1);
    }

    @Test(expected = NoExistsException.class)
    public void testRemoveNoExistsException() throws NoExistsException {
        doThrow(new NoExistsException()).when(provinceService).remove(1);
        provinceController.remove(1);
    }

    @Test
    public void testUpdateOk() throws NoExistsException {
        Province province = new Province(1, "calamuchita");
        doNothing().when(provinceService).update(province);
        provinceController.update(province);
        verify(provinceService, times(1)).update(province);
    }

    @Test(expected = NoExistsException.class)
    public void testUpdateNoExistsException() throws NoExistsException {
        Province province = new Province(1, "calamuchita");
        doThrow(new NoExistsException()).when(provinceService).update(province);
        provinceController.update(province);
    }

    @Test
    public void testGetById() throws NoExistsException {
        Province province = new Province(1, "calamuchita");
        when(provinceService.getById(1)).thenReturn(province);
        Province byId = provinceController.getById(1);
        assertEquals(byId.getId(),province.getId());
        assertEquals(byId.getProvinceName(),province.getProvinceName());
        verify(provinceService,times(1)).getById(1);
    }

}
