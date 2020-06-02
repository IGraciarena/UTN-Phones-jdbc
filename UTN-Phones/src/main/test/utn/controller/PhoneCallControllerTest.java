package utn.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.NoExistsException;
import utn.service.PhoneCallService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneCallControllerTest {


    PhoneCallController phoneCallController;
    @Mock
    PhoneCallService phoneCallService;

    @Before
    public void setUp(){
        initMocks(this);
        phoneCallController = new PhoneCallController(phoneCallService);
    }

    @Test
    public void testGetAllPhoneCallsFromUserIdOk() throws NoExistsException {
        List<ReturnedPhoneCallDto> phoneCallDtoList = new ArrayList<>();
        ReturnedPhoneCallDto r1 = new ReturnedPhoneCallDto("123","456","1","2",1,null,10);
        ReturnedPhoneCallDto r2 = new ReturnedPhoneCallDto("456","789","3","4",5,null,20);
        phoneCallDtoList.add(r1);
        phoneCallDtoList.add(r2);
        when(phoneCallService.getAllPhoneCallsFromUserId(1)).thenReturn(phoneCallDtoList);
        List<ReturnedPhoneCallDto>returnedPhoneCallDtoList = phoneCallController.getAllPhoneCallsFromUserId(1);
        verify(phoneCallService,times(1)).getAllPhoneCallsFromUserId(1);
        assertEquals(returnedPhoneCallDtoList.size(),phoneCallDtoList.size());
    }

    @Test(expected = NoExistsException.class)
    public void testGetAllPhoneCallsFromUserIdNoExistsException()throws NoExistsException{
        when(phoneCallService.getAllPhoneCallsFromUserId(1)).thenThrow(new NoExistsException());
        phoneCallController.getAllPhoneCallsFromUserId(1);
    }




}
