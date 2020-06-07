package utn.controller.web;

import org.junit.Before;
import org.mockito.Mock;
import utn.controller.PhoneCallController;
import utn.session.SessionManager;

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