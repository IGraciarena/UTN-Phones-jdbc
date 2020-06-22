package utn.controller.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.controller.PhoneCallController;
import utn.dto.PhoneCallDto;
import utn.dto.PhoneCallsBetweenDatesDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.NoExistsException;
import utn.exceptions.ValidationException;
import utn.model.*;
import utn.model.enumerated.LineStatus;
import utn.model.enumerated.TypeLine;
import utn.model.enumerated.UserStatus;
import utn.model.enumerated.UserType;
import utn.session.SessionManager;
import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneCallWebControllerTest {

    PhoneCallWebController phoneCallWebController;
    @Mock
    PhoneCallController phoneCallController;
    @Mock
    SessionManager sessionManager;
    @Mock
    RestUtils restUtils;

    @Before
    public void setUp() {
        initMocks(this);
        //PowerMockito.mockStatic(RestUtils.class);
        phoneCallWebController = new PhoneCallWebController(phoneCallController, sessionManager);
    }

    //******************************************************getAllPhonecallFromUserId******************************************************************************************
    @Test
    public void testGetAllPhoneCallsFromUserIdOk() throws NoExistsException {
        List<ReturnedPhoneCallDto> phoneCallDtoList = new ArrayList<>();
        ReturnedPhoneCallDto r1 = new ReturnedPhoneCallDto("123", "456", "1", "2", 1, null, 10);
        ReturnedPhoneCallDto r2 = new ReturnedPhoneCallDto("456", "789", "3", "4", 5, null, 20);
        phoneCallDtoList.add(r1);
        phoneCallDtoList.add(r2);
        when(phoneCallController.getAllPhoneCallsFromUserId(1)).thenReturn(phoneCallDtoList);
        ResponseEntity responseRta = ResponseEntity.ok(phoneCallDtoList);
        ResponseEntity<List<ReturnedPhoneCallDto>> response = phoneCallWebController.getAllPhoneCallsFromUserId("123", 1);

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getAllPhoneCallsFromUserId(1);
    }

    @Test
    public void testGetAllPhoneCallsFromUserIdNoContent() throws NoExistsException {
        List<ReturnedPhoneCallDto> phoneCallDtoList = new ArrayList<>();
        when(phoneCallController.getAllPhoneCallsFromUserId(1)).thenReturn(phoneCallDtoList);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ResponseEntity<List<ReturnedPhoneCallDto>> response = phoneCallWebController.getAllPhoneCallsFromUserId("123", 1);

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getAllPhoneCallsFromUserId(1);
    }

    @Test(expected = NoExistsException.class)
    public void testGetAllPhoneCallsFromUserIdSQLException() throws NoExistsException {
        when(phoneCallController.getAllPhoneCallsFromUserId(1)).thenThrow(new NoExistsException());
        phoneCallWebController.getAllPhoneCallsFromUserId("123", 1);
    }

    //***********************************************************GetTop5DestinationsByUserId******************************************************************************
    @Test
    public void testGetTop5DestinationsByUserIdOk() throws NoExistsException {
        User u = new User(1,
                "jor",
                "vill",
                123,
                new Date(2005, 05, 05),
                "user",
                "pwd",
                "email",
                UserType.valueOf("CLIENT"),
                UserStatus.valueOf("ACTIVE"),
                new City(2, "mdp", 223, new Province(3, "bsas")));
        when(sessionManager.getCurrentUser("123")).thenReturn(u);
        List<String> phoneCallList = new ArrayList<>();
        phoneCallList.add("asd");
        phoneCallList.add("das");
        when(phoneCallController.getMostCalledDestinsByUserId(1)).thenReturn(phoneCallList);
        ResponseEntity responseRta = ResponseEntity.ok(phoneCallList);

        ResponseEntity<List<String>> response = phoneCallWebController.getTop5DestinationsByUserId("123");

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getMostCalledDestinsByUserId(1);
    }

    @Test
    public void testGetTop5DestinationsByUserIdNoContent() throws NoExistsException {
        User u = new User(1,
                "jor",
                "vill",
                123,
                new Date(2005, 05, 05),
                "user",
                "pwd",
                "email",
                UserType.valueOf("CLIENT"),
                UserStatus.valueOf("ACTIVE"),
                new City(2, "mdp", 223, new Province(3, "bsas")));
        when(sessionManager.getCurrentUser("123")).thenReturn(u);
        List<String> phoneCallList = new ArrayList<>();
        when(phoneCallController.getMostCalledDestinsByUserId(1)).thenReturn(phoneCallList);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        ResponseEntity<List<String>> response = phoneCallWebController.getTop5DestinationsByUserId("123");

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getMostCalledDestinsByUserId(1);
    }

    @Test(expected = NoExistsException.class)
    public void testGetTop5DestinationsByUserIdSQLException() throws NoExistsException {
        User u = new User(1,
                "jor",
                "vill",
                123,
                new Date(2005, 05, 05),
                "user",
                "pwd",
                "email",
                UserType.valueOf("CLIENT"),
                UserStatus.valueOf("ACTIVE"),
                new City(2, "mdp", 223, new Province(3, "bsas")));
        when(sessionManager.getCurrentUser("123")).thenReturn(u);
        when(phoneCallController.getMostCalledDestinsByUserId(1)).thenThrow(new NoExistsException());
        phoneCallWebController.getTop5DestinationsByUserId("123");
    }

    //*****************************************************getPhoneCallsFromUserIdBetweenDates*************************************************************************************
    @Test
    public void testGetPhoneCallsFromUserIdBetweenDatesOk() throws NoExistsException {
        User u = new User(1,
                "jor",
                "vill",
                123,
                new Date(2005, 05, 05),
                "user",
                "pwd",
                "email",
                UserType.valueOf("CLIENT"),
                UserStatus.valueOf("ACTIVE"),
                new City(2, "mdp", 223, new Province(3, "bsas")));
        when(sessionManager.getCurrentUser("123")).thenReturn(u);
        List<ReturnedPhoneCallDto> phoneCallDtoList = new ArrayList<>();
        ReturnedPhoneCallDto r1 = new ReturnedPhoneCallDto("123", "456", "1", "2", 1, null, 10);
        ReturnedPhoneCallDto r2 = new ReturnedPhoneCallDto("456", "789", "3", "4", 5, null, 20);
        phoneCallDtoList.add(r1);
        phoneCallDtoList.add(r2);
        PhoneCallsBetweenDatesDto phoneCallDto = new PhoneCallsBetweenDatesDto("1233", "1234");
        when(phoneCallController.getPhoneCallsFromUserIdBetweenDates(phoneCallDto, 1)).thenReturn(phoneCallDtoList);
        ResponseEntity responseRta = ResponseEntity.ok(phoneCallDtoList);
        ResponseEntity<List<ReturnedPhoneCallDto>> response = phoneCallWebController.getPhoneCallsFromUserIdBetweenDates("123", phoneCallDto);

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getPhoneCallsFromUserIdBetweenDates(phoneCallDto, 1);
    }

    @Test
    public void testGetPhoneCallsFromUserIdBetweenDatesNoContent() throws NoExistsException {
        User u = new User(1,
                "jor",
                "vill",
                123,
                new Date(2005, 05, 05),
                "user",
                "pwd",
                "email",
                UserType.valueOf("CLIENT"),
                UserStatus.valueOf("ACTIVE"),
                new City(2, "mdp", 223, new Province(3, "bsas")));
        when(sessionManager.getCurrentUser("123")).thenReturn(u);
        List<ReturnedPhoneCallDto> phoneCallDtoList = new ArrayList<>();
        PhoneCallsBetweenDatesDto phoneCallDto = new PhoneCallsBetweenDatesDto("1233", "1234");
        when(phoneCallController.getPhoneCallsFromUserIdBetweenDates(phoneCallDto, 1)).thenReturn(phoneCallDtoList);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ResponseEntity<List<ReturnedPhoneCallDto>> response = phoneCallWebController.getPhoneCallsFromUserIdBetweenDates("123", phoneCallDto);

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getPhoneCallsFromUserIdBetweenDates(phoneCallDto, 1);
    }

    @Test(expected = NoExistsException.class)
    public void testGetPhoneCallsFromUserIdBetweenDatesSQLException() throws NoExistsException {
        User u = new User(1,
                "jor",
                "vill",
                123,
                new Date(2005, 05, 05),
                "user",
                "pwd",
                "email",
                UserType.valueOf("CLIENT"),
                UserStatus.valueOf("ACTIVE"),
                new City(2, "mdp", 223, new Province(3, "bsas")));
        PhoneCallsBetweenDatesDto phoneCallDto = new PhoneCallsBetweenDatesDto("1233", "1234");
        when(sessionManager.getCurrentUser("123")).thenReturn(u);
        when(phoneCallController.getPhoneCallsFromUserIdBetweenDates(phoneCallDto, 1)).thenThrow(new NoExistsException());
        phoneCallWebController.getPhoneCallsFromUserIdBetweenDates("123", phoneCallDto);
    }

    //******************************************************getByIdEmployee************************************************************************************
    @Test
    public void testGetByIdEmployeeOk() throws NoExistsException {
        ReturnedPhoneCallDto r1 = new ReturnedPhoneCallDto("123", "456", "1", "2", 1, null, 10);

        when(phoneCallController.getById(1)).thenReturn(r1);
        ResponseEntity responseRta = ResponseEntity.ok(r1);
        ResponseEntity response = phoneCallWebController.getByIdEmployee(1, "123");

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getById(1);
    }

    @Test(expected = NoExistsException.class)
    public void testGetByIdEmployeeSQLException() throws NoExistsException {
        when(phoneCallController.getById(1)).thenThrow(new NoExistsException());
        ResponseEntity responseRta = ResponseEntity.badRequest().build();
        ResponseEntity response = phoneCallWebController.getByIdEmployee(1, "123");
        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getById(1);
    }
//*********************************************************getByIdClient********************************************************************************
    @Test
    public void testGetByIClientCOk() throws NoExistsException {
        ReturnedPhoneCallDto r1 = new ReturnedPhoneCallDto("123", "456", "1", "2", 1, null, 10);

        when(phoneCallController.getById(1)).thenReturn(r1);
        ResponseEntity responseRta = ResponseEntity.ok(r1);
        ResponseEntity response = phoneCallWebController.getByIdClient(1, "123");

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getById(1);
    }

    @Test(expected = NoExistsException.class)
    public void testGetByIdClientSQLException() throws NoExistsException {
        when(phoneCallController.getById(1)).thenThrow(new NoExistsException());
        ResponseEntity responseRta = ResponseEntity.badRequest().build();
        ResponseEntity response = phoneCallWebController.getByIdClient(1, "123");
        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getById(1);
    }
//********************************************************getAll*************************************************************************************
    @Test
    public void testGetAllCOk() throws NoExistsException {
        List<ReturnedPhoneCallDto> phoneCallDtoList = new ArrayList<>();
        ReturnedPhoneCallDto r1 = new ReturnedPhoneCallDto("123", "456", "1", "2", 1, null, 10);
        ReturnedPhoneCallDto r2 = new ReturnedPhoneCallDto("456", "789", "3", "4", 5, null, 20);
        phoneCallDtoList.add(r1);
        phoneCallDtoList.add(r2);
        when(phoneCallController.getAll()).thenReturn(phoneCallDtoList);
        ResponseEntity responseRta = ResponseEntity.ok(phoneCallDtoList);
        ResponseEntity<List<ReturnedPhoneCallDto>> response = phoneCallWebController.getAll("234");

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getAll();
    }
    @Test
    public void testGetAllNoContent() throws NoExistsException {
        List<ReturnedPhoneCallDto> phoneCallDtoList = new ArrayList<>();

        when(phoneCallController.getAll()).thenReturn(phoneCallDtoList);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ResponseEntity<List<ReturnedPhoneCallDto>> response = phoneCallWebController.getAll("234");

        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).getAll();
    }
//******************************************************delete*************************************************************************************

    @Test
    public void testDeleteOk() throws NoExistsException {
        doNothing().when(phoneCallController).delete(1);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.OK).build();
        ResponseEntity response = phoneCallWebController.delete("asd",1);
        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).delete(1);
    }

    @Test(expected = NoExistsException.class)
    public void testDeleteNoExistsException() throws NoExistsException {
        doThrow(new NoExistsException()).when(phoneCallController).delete(1);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        ResponseEntity response = phoneCallWebController.delete("asd",1);
        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).delete(1);

    }
//*******************************************************************************************************************************************
    @Test
    public void testUpdateOk() throws NoExistsException {
        PhoneCall phoneCall = new PhoneCall(1, "1", "2",
                new UserLine(),
                new UserLine(),
                new City(),
                new City(), 20,
                new java.util.Date(), 1, 1, 1, 1,
                new Invoice());
        doNothing().when(phoneCallController).update(phoneCall);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.OK).build();
        ResponseEntity response = phoneCallWebController.update(phoneCall,"asd");
        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).update(phoneCall);
    }
    @Test(expected = NoExistsException.class)
    public void testUpdateNotExists() throws NoExistsException {
        PhoneCall phoneCall = new PhoneCall(1, "1", "2",
                new UserLine(),
                new UserLine(),
                new City(),
                new City(), 20,
                new java.util.Date(), 1, 1, 1, 1,
                new Invoice());
        doThrow(new NoExistsException()).when(phoneCallController).update(phoneCall);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        ResponseEntity response = phoneCallWebController.update(phoneCall,"asd");
        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).update(phoneCall);
    }
//***************************************************Add****************************************************************************************
    /*@Test
    public void testAddOk() throws ValidationException, NoExistsException {
        PhoneCallDto phoneCallDto = new PhoneCallDto("123","1233",12,null);
        when(phoneCallController.addPhoneCall(phoneCallDto)).thenReturn(1);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(1)
                .toUri();
        when(restUtils.getLocationPhoneCall(1)).thenReturn(uri);
        ResponseEntity responseEntity = ResponseEntity.created(uri).build();
        ResponseEntity response = phoneCallWebController.add(phoneCallDto,"asd");
        //List<String> headers = response.getHeaders().get("location");
        //assertEquals(headers.get(0),"myUri");
        assertEquals(responseEntity,response);
        verify(phoneCallController, times(1)).addPhoneCall(phoneCallDto);
    }*/
    @Test(expected = NoExistsException.class)
    public void testAddNotExists() throws ValidationException, NoExistsException {
        PhoneCallDto phoneCallDto = new PhoneCallDto("123","1233",12,null);
        doThrow(new NoExistsException()).when(phoneCallController).addPhoneCall(phoneCallDto);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        ResponseEntity response = phoneCallWebController.add(phoneCallDto,"asd");
        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).addPhoneCall(phoneCallDto);
    }
    @Test(expected = ValidationException.class)
    public void testAddValidation() throws ValidationException, NoExistsException {
        PhoneCallDto phoneCallDto = new PhoneCallDto("123","1233",12,null);
        doThrow(new ValidationException()).when(phoneCallController).addPhoneCall(phoneCallDto);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        ResponseEntity response = phoneCallWebController.add(phoneCallDto,"asd");
        assertEquals(responseRta, response);
        verify(phoneCallController, times(1)).addPhoneCall(phoneCallDto);
    }
//*******************************************************************************************************************************************






}