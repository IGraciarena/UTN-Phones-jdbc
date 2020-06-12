package utn.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.City;
import utn.model.Province;
import utn.service.CityService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityControllerTest {

    @Mock
    CityService cityService;
    CityController cityController;

    @Before
    public void setUp() {
        initMocks(this);
        cityController = new CityController(cityService);
    }

    @Test
    public void testAddOk() throws AlreadyExistsException {
        City city = new City(1, "mardelplata", 223, new Province());
        doNothing().when(cityService).add(city);
        cityController.add(city);
        verify(cityService, times(1)).add(city);
    }

    @Test(expected = AlreadyExistsException.class)
    public void testAddAlreadyExistsException() throws AlreadyExistsException {
        City city = new City(1, "mardelplata", 223, new Province());
        doThrow(new AlreadyExistsException()).when(cityService).add(city);
        cityController.add(city);
    }

    @Test
    public void testRemoveOk() throws NoExistsException {
        doNothing().when(cityService).remove(1);
        cityController.remove(1);
        verify(cityService, times(1)).remove(1);
    }

    @Test(expected = NoExistsException.class)
    public void testRemoveNoExistsException() throws NoExistsException {
        doThrow(new NoExistsException()).when(cityService).remove(1);
        cityController.remove(1);
    }

    @Test
    public void testUpdateOk() throws NoExistsException {
        City city = new City(1, "mardelplata", 223, new Province());
        doNothing().when(cityService).update(city);
        cityController.update(city);
        verify(cityService, times(1)).update(city);
    }

    @Test(expected = NoExistsException.class)
    public void testUpdateNoExistsException() throws NoExistsException {
        City city = new City(1, "mardelplata", 223, new Province());
        doThrow(new NoExistsException()).when(cityService).update(city);
        cityController.update(city);
    }

    @Test
    public void testGetByIdOk() throws NoExistsException {
        City city = new City(1, "mardelplata", 223, new Province());
        when(cityService.getById(1)).thenReturn(city);
        City byId = cityController.getById(1);
        assertEquals(city.getId(),byId.getId());
        assertEquals(city.getCityName(),byId.getCityName());
        assertEquals(city.getPrefix(),byId.getPrefix());
        verify(cityService,times(1)).getById(1);
    }

    @Test(expected = NoExistsException.class)
    public void testGetByIdNoExistsException () throws NoExistsException{
        City city = new City(1, "mardelplata", 223, new Province());
        when(cityService.getById(1)).thenThrow(new NoExistsException());
        cityController.getById(1);
    }


}
