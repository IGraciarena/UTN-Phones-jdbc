package utn.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dao.mysql.UserMySQLDao;
import utn.dao.mysql.MySQLUtils.*;
import utn.model.User;
import utn.model.enumerated.UserStatus;
import utn.model.enumerated.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static utn.dao.mysql.MySQLUtils.GET_BY_USERNAME_USER_QUERY;

public class UserMySQLDaoTest {

    UserMySQLDao dao;
    @Mock
    Connection con;
    @Mock
    PreparedStatement ps;
    @Mock
    ResultSet rs;

    @Before
    public void setUp(){
        initMocks(this);
        dao = new UserMySQLDao(con);
    }
    @Test
    public void testGetByUserNameOk() throws SQLException {
        when(con.prepareStatement(GET_BY_USERNAME_USER_QUERY)).thenReturn(ps);
        doNothing().when(ps).setString(1,"user");
        doNothing().when(ps).setString(1,"pwd");

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getInt("id")).thenReturn(2);
        when(rs.getInt("id")).thenReturn(3);

        when(rs.getString("user_type")).thenReturn("CLIENT");
        when(rs.getString("user_status")).thenReturn("ACTIVE");
        when(rs.getString("username")).thenReturn("username");
        when(rs.getString("pwd")).thenReturn("pwd");
        UserType userType = UserType.CLIENT;
        UserStatus userStatus = UserStatus.ACTIVE;

        doNothing().when(ps).close();
        doNothing().when(rs).close();

        User u = dao.getByUserName("user","pwd");

        assertEquals("username",u.getUsername());
        assertEquals("pwd",u.getPwd());
        assertEquals(Integer.valueOf(1),u.getId());
        assertEquals(Integer.valueOf(2),u.getCity().getId());
        assertEquals(Integer.valueOf(3),u.getCity().getProvince().getId());
        assertEquals(userType,u.getUserType());
        assertEquals(userStatus,u.getUserStatus());


        verify(ps,times(2)).setString(anyInt(),anyString());

    }
}
