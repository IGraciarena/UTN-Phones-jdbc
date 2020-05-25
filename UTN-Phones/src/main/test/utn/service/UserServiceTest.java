package utn.service;

import org.junit.Before;
import org.junit.Test;
import utn.dao.UserDao;
import utn.exceptions.UserNotExistsException;
import utn.model.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    UserService userService;
    UserDao userDao;

    @Before
    public void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    public void testLoginOk() throws UserNotExistsException {
        User userAux = new User(1, "carlos", "lolo", 38888765, null, "username", "password", "email", null, null, null);
        when(userDao.getByUserName("user", "pwd")).thenReturn(userAux);
        User returnedUser = userService.login("user", "pwd");
        assertEquals(userAux.getId(), returnedUser.getId());
        assertEquals(userAux.getFirstname(), returnedUser.getFirstname());
        assertEquals(userAux.getSurname(), returnedUser.getSurname());
        assertEquals(userAux.getDni(), returnedUser.getDni());
        assertEquals(userAux.getBirthdate(), returnedUser.getBirthdate());
        assertEquals(userAux.getUsername(), returnedUser.getUsername());
        assertEquals(userAux.getPwd(), returnedUser.getPwd());
        assertEquals(userAux.getEmail(), returnedUser.getEmail());
        assertEquals(userAux.getUserType(), returnedUser.getUserType());
        assertEquals(userAux.getUserStatus(), returnedUser.getUserStatus());
        assertEquals(userAux.getCity(), returnedUser.getCity());
        verify(userDao, times(1)).getByUserName("user", "pwd");
    }

    @Test(expected = UserNotExistsException.class)
    public void testLoginUserNotFound() throws UserNotExistsException {
        when(userDao.getByUserName(any(), any())).thenReturn(null);
        userService.login("asd", "asd");
    }
}
