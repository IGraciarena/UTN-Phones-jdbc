package utn.controller;

import org.junit.Before;
import org.junit.Test;
import utn.exceptions.UserNotExistsException;
import utn.exceptions.ValidationException;
import utn.model.User;
import utn.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    UserController userController;
    UserService userService;

    @Before
    public void setUp(){
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void testLoginOk() throws UserNotExistsException, ValidationException {

        User loggedUser = new User(1,"carlos","lolo",38888765,null,"username","password","email",null,null,null);
        //cuando el mock llama a login con estos parametros devuelve loggedUser
        when(userService.login("user","pwd")).thenReturn(loggedUser);
        User returnedUser = userController.login("user","pwd");
        //verifica que los campos coincidan
        assertEquals(loggedUser.getId(),returnedUser.getId());
        assertEquals(loggedUser.getFirstname(),returnedUser.getFirstname());
        assertEquals(loggedUser.getSurname(),returnedUser.getSurname());
        assertEquals(loggedUser.getDni(),returnedUser.getDni());
        assertEquals(loggedUser.getBirthdate(),returnedUser.getBirthdate());
        assertEquals(loggedUser.getUsername(),returnedUser.getUsername());
        assertEquals(loggedUser.getPwd(),returnedUser.getPwd());
        assertEquals(loggedUser.getEmail(),returnedUser.getEmail());
        assertEquals(loggedUser.getUserType(),returnedUser.getUserType());
        assertEquals(loggedUser.getUserStatus(),returnedUser.getUserStatus());
        assertEquals(loggedUser.getCity(),returnedUser.getCity());
        //verifica que el metodo login ha sido llamado una vez
        verify(userService,times(1)).login("user","pwd");

    }

    @Test(expected=UserNotExistsException.class)
    public void testLoginUserNotFound()throws UserNotExistsException,ValidationException{
        when(userService.login("user","pwd")).thenThrow(new UserNotExistsException());
        userController.login("user","pwd");
    }

    @Test(expected=ValidationException.class)
    public void testLoginIvalidadData()throws UserNotExistsException,ValidationException{
        userController.login(null,null);
    }
}
