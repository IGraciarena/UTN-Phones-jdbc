package utn.dao.mysql;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dto.ReturnedPhoneCallDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static utn.dao.mysql.MySQLUtils.GETBYID_PHONECALLS_QUERY;
import static utn.dao.mysql.MySQLUtils.GETBYID_USERPHONECALLS_QUERY;

public class PhoneCallMySQLDaoTest {

    PhoneCallMySQLDao phoneCallMySQLDao;
    @Mock
    Connection connection;
    @Mock
    CityMySQLDao cityMySQLDao;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;

    @Before
    public void setUp() {
        initMocks(this);
        phoneCallMySQLDao = new PhoneCallMySQLDao(connection, cityMySQLDao);
    }


    @Test
    public void testGetAllPhoneCallsFromUserIdOk() throws SQLException {
        when(connection.prepareStatement(GETBYID_USERPHONECALLS_QUERY)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1, 1);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);


        ReturnedPhoneCallDto returnedPhoneCallDto = new ReturnedPhoneCallDto("12", "22", "mdp", "bsas", 3, null, 10);

//        doReturn(returnedPhoneCallDto).when(phoneCallMySQLDao.createPhoneCall(resultSet));
        when(phoneCallMySQLDao.createPhoneCall(resultSet)).thenReturn(returnedPhoneCallDto);

        when(resultSet.getString("line_number_from")).thenReturn("1");
        when(resultSet.getString("line_number_to")).thenReturn("2");
        when(resultSet.getInt("id_city_from_fk")).thenReturn(1);
        when(cityMySQLDao.getCityName(1)).thenReturn("mdp");
        when(resultSet.getInt("id_city_to_fk")).thenReturn(2);
        when(cityMySQLDao.getCityName(2)).thenReturn("bsas");
        when(resultSet.getInt("duration")).thenReturn(3);
        when(resultSet.getInt("total_price")).thenReturn(10);

        doNothing().when(resultSet).close();
        doNothing().when(preparedStatement).close();

        List<ReturnedPhoneCallDto> allPhoneCallsFromUserId = phoneCallMySQLDao.getAllPhoneCallsFromUserId(1);

        assertEquals("1", allPhoneCallsFromUserId.get(1).getLineNumberFrom());
        assertEquals("2", allPhoneCallsFromUserId.get(1).getLineNumberTo());
        assertEquals("mdp", allPhoneCallsFromUserId.get(1).getCityFrom());
        assertEquals("bsas", allPhoneCallsFromUserId.get(1).getCityTo());
        assertEquals(Integer.valueOf(3), allPhoneCallsFromUserId.get(1).getDuration());
        assertEquals(Integer.valueOf(10), allPhoneCallsFromUserId.get(1).getTotalPrice());
    }

    @Test
    public void testGetByIdOk() throws SQLException {
        when(connection.prepareStatement(GETBYID_PHONECALLS_QUERY)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1, 1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getString("line_number_from")).thenReturn("1");
        when(resultSet.getString("line_number_to")).thenReturn("2");
        when(resultSet.getInt("id_city_from_fk")).thenReturn(1);
        when(cityMySQLDao.getCityName(1)).thenReturn("mdp");
        when(resultSet.getInt("id_city_to_fk")).thenReturn(2);
        when(cityMySQLDao.getCityName(2)).thenReturn("bsas");
        when(resultSet.getInt("duration")).thenReturn(3);
        when(resultSet.getInt("total_price")).thenReturn(10);

        doNothing().when(resultSet).close();
        doNothing().when(preparedStatement).close();

        ReturnedPhoneCallDto byId = phoneCallMySQLDao.getById(1);


        assertEquals("1", byId.getLineNumberFrom());
        assertEquals("2", byId.getLineNumberTo());
        assertEquals("mdp", byId.getCityFrom());
        assertEquals("bsas", byId.getCityTo());
        assertEquals(Integer.valueOf(3), byId.getDuration());
        assertEquals(Integer.valueOf(10), byId.getTotalPrice());

    }

    @Test(expected = SQLException.class)
    public void getByIdSQLException() throws SQLException {
        when(connection.prepareStatement(GETBYID_PHONECALLS_QUERY)).thenThrow(new SQLException());
        phoneCallMySQLDao.getById(1);
    }

}
