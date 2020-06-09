package utn.dao.mysql;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import utn.dto.ReturnedPhoneCallDto;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
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
    @Mock
    CallableStatement cs;

    @Before
    public void setUp() {
        initMocks(this);
        phoneCallMySQLDao = new PhoneCallMySQLDao(connection, cityMySQLDao);
    }
//****************************************************************************************************************************************************

//***************************************************************getAll*************************************************************************************
    @Test
    public void testGetAllOk(){
        
    }
//*************************************************************getMostCalledDestinsByUserId***************************************************************************************
    @Test
    public void testGetMostCalledDestinsByUserIdOk() throws SQLException {
        when(connection.prepareCall("call sp_user_top10(?)")).thenReturn(cs);
        doNothing().when(cs).setInt(1, 1);
        when(cs.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("line_number_to")).thenReturn("223");

        doNothing().when(resultSet).close();
        doNothing().when(preparedStatement).close();

        List<String> destins = phoneCallMySQLDao.getMostCalledDestinsByUserId(1);

        assertEquals("223", destins.get(0));
        verify(connection,times(1)).prepareCall("call sp_user_top10(?)");
        verify(cs,times(1)).setInt(1,1);
        verify(cs,times(1)).executeQuery();

    }
    @Test
    public void testGetMostCalledDestinsByUserIdNoContent() throws SQLException {
        when(connection.prepareCall("call sp_user_top10(?)")).thenReturn(cs);
        doNothing().when(cs).setInt(1, 1);
        when(cs.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        doNothing().when(resultSet).close();
        doNothing().when(preparedStatement).close();

        List<String> destins = phoneCallMySQLDao.getMostCalledDestinsByUserId(1);

        assertEquals(0, destins.size());
        verify(connection,times(1)).prepareCall("call sp_user_top10(?)");
        verify(cs,times(1)).setInt(1,1);
        verify(cs,times(1)).executeQuery();
    }
//***********************************************************GetAllPhoneCallsFromUserId*****************************************************************************************

    @Test
    public void testGetAllPhoneCallsFromUserIdOk() throws SQLException {
        when(connection.prepareStatement(GETBYID_USERPHONECALLS_QUERY)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1, 1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
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

        assertEquals("1", allPhoneCallsFromUserId.get(0).getLineNumberFrom());
        assertEquals("2", allPhoneCallsFromUserId.get(0).getLineNumberTo());
        assertEquals("mdp", allPhoneCallsFromUserId.get(0).getCityFrom());
        assertEquals("bsas", allPhoneCallsFromUserId.get(0).getCityTo());
        assertEquals(Integer.valueOf(3), allPhoneCallsFromUserId.get(0).getDuration());
        assertEquals(Integer.valueOf(10), allPhoneCallsFromUserId.get(0).getTotalPrice());

        verify(resultSet,times(1)).getString("line_number_from");
        verify(resultSet,times(1)).getString("line_number_to");
        verify(connection,times(1)).prepareStatement(GETBYID_USERPHONECALLS_QUERY);
        verify(preparedStatement,times(1)).setInt(1,1);
        verify(preparedStatement,times(1)).executeQuery();
    }
    @Test
    public void testGetAllPhoneCallsFromUserIdNoContent() throws SQLException {
        when(connection.prepareStatement(GETBYID_USERPHONECALLS_QUERY)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1, 1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        doNothing().when(resultSet).close();
        doNothing().when(preparedStatement).close();

        List<ReturnedPhoneCallDto> allPhoneCallsFromUserId = phoneCallMySQLDao.getAllPhoneCallsFromUserId(1);

        assertEquals(0, allPhoneCallsFromUserId.size());
        verify(connection,times(1)).prepareStatement(GETBYID_USERPHONECALLS_QUERY);
        verify(preparedStatement,times(1)).setInt(1,1);
        verify(preparedStatement,times(1)).executeQuery();
    }

    @Test(expected = SQLException.class)
    public void testGetAllPhoneCallsFromUserIdOSQLException() throws SQLException {

    }
//**************************************************************GetById**************************************************************************************
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
        verify(resultSet,times(1)).getString("line_number_from");
        verify(resultSet,times(1)).getString("line_number_to");
        verify(connection,times(1)).prepareStatement(GETBYID_PHONECALLS_QUERY);
        verify(preparedStatement,times(1)).setInt(1,1);
        verify(preparedStatement,times(1)).executeQuery();

    }

    @Test
    public void testGetByIdNoContent() throws SQLException {
        when(connection.prepareStatement(GETBYID_PHONECALLS_QUERY)).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(1, 1);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        doNothing().when(resultSet).close();
        doNothing().when(preparedStatement).close();

        ReturnedPhoneCallDto byId = phoneCallMySQLDao.getById(1);

        assertEquals(null, byId);
        verify(connection,times(1)).prepareStatement(GETBYID_PHONECALLS_QUERY);
        verify(preparedStatement,times(1)).setInt(1,1);
        verify(preparedStatement,times(1)).executeQuery();

    }
//este no anda
    @Test(expected = SQLException.class)
    public void getByIdSQLException() throws SQLException {
        when(connection.prepareStatement(GETBYID_PHONECALLS_QUERY)).thenThrow(new SQLException());
        phoneCallMySQLDao.getById(1);
    }
//****************************************************************************************************************************************************
}
