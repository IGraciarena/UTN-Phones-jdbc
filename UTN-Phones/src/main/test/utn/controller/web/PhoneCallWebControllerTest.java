package utn.controller.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import utn.controller.PhoneCallController;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.NoExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneCallWebControllerTest {

    PhoneCallWebController phoneCallWebController;
    @Mock
    PhoneCallController phoneCallController;
    @Mock
    SessionManager sessionManager;


    @Before
    public void setUp(){
        initMocks(this);
        phoneCallWebController = new PhoneCallWebController(phoneCallController,sessionManager);
    }
//
//    @Test
//    public void getAllPhoneCallsFromUserIdOk()throws UserNotExistsException, NoExistsException {
//        when(phoneCallWebController.getCurrentUser("token").getUserType().equals(UserType.EMPLOYEE)).thenReturn(true);
//        List<ReturnedPhoneCallDto>phoneCallDtoList = new ArrayList<>();
//        ReturnedPhoneCallDto r1 = new ReturnedPhoneCallDto("123","456","1","2",1,null,10);
//        ReturnedPhoneCallDto r2 = new ReturnedPhoneCallDto("456","789","3","4",5,null,20);
//        phoneCallDtoList.add(r1);
//        phoneCallDtoList.add(r2);
//        when(phoneCallController.getAllPhoneCallsFromUserId(2)).thenReturn(phoneCallDtoList);
//        when(phoneCallDtoList.size()>0).thenReturn(true);
//    }
}
