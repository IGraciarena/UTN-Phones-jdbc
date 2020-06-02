package utn.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dao.PhoneCallDao;
import utn.dao.UserDao;
import utn.dto.ReturnedPhoneCallDto;
import utn.dto.UserDto;
import utn.exceptions.NoExistsException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneCallServiceTest {

    PhoneCallService phoneCallService;
    @Mock
    PhoneCallDao phoneCallDao;
    @Mock
    UserDao userDao;

    @Before
    public void setUp() {
        initMocks(this);
        phoneCallService = new PhoneCallService(phoneCallDao, userDao);
    }

    @Test
    public void testGetAllPhoneCallsFromUserIdOk() throws NoExistsException {
        UserDto userDto = new UserDto("ivan", "graciarena", 38705059, "ivanmdq22", "ivan@ivan.com", "alabama");

        when(userDao.getById(1)).thenReturn(userDto);
        phoneCallService.getAllPhoneCallsFromUserId(1);

        List<ReturnedPhoneCallDto> phoneCallDtoList = new ArrayList<>();
        ReturnedPhoneCallDto r1 = new ReturnedPhoneCallDto("123", "456", "1", "2", 1, null, 10);
        ReturnedPhoneCallDto r2 = new ReturnedPhoneCallDto("456", "789", "3", "4", 5, null, 20);
        phoneCallDtoList.add(r1);
        phoneCallDtoList.add(r2);


        when(phoneCallDao.getAllPhoneCallsFromUserId(1)).thenReturn(phoneCallDtoList);
        assertEquals(2, phoneCallDtoList.size());
        verify(userDao, times(1)).getById(1);
        verify(phoneCallDao, times(1)).getAllPhoneCallsFromUserId(1);
    }

    @Test
    public void testGetByIdOk(){

    }

    @Test(expected = NoExistsException.class)
    public void testGetAllPhoneCallsFromUserIdNoExistsException() throws NoExistsException{
        when(userDao.getById(1)).thenReturn(null);
        phoneCallService.getAllPhoneCallsFromUserId(1);
    }
}
