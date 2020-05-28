package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.PhoneCallDao;
import utn.dto.PhoneCallDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.AlreadyExistsException;
import utn.model.PhoneCall;
import utn.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utn.dao.mysql.MySQLUtils.*;

@Repository
public class PhoneCallMySQLDao implements PhoneCallDao {
    Connection con;
    CityMySQLDao cityMySQLDao;

    @Autowired
    public PhoneCallMySQLDao(Connection con, CityMySQLDao cityMySQLDao) {
        this.cityMySQLDao = cityMySQLDao;
        this.con = con;
    }

    @Override
    public Object add(PhoneCall value) throws AlreadyExistsException {
        return null;
    }

    @Override
    public void update(PhoneCall value) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_PHONECALLS_QUERY);
            ps.setString(1, value.getLineNumberFrom());
            ps.setString(2, value.getLineNumberTo());
            ps.setInt(3, value.getIdLineNumberFrom().getId());
            ps.setInt(4, value.getIdLineNumberTo().getId());
            ps.setInt(5, value.getIdCityFrom().getId());
            ps.setInt(6, value.getIdCityTo().getId());
            ps.setInt(7, value.getDuration());
            ps.setDate(8, new Date(value.getCallDate().getTime()));
            ps.setFloat(9, value.getCostPerMin());
            ps.setFloat(10, value.getPricePerMin());
            ps.setFloat(11, value.getTotalPrice());
            ps.setFloat(12, value.getTotalCost());
            ps.executeQuery();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer id) {
        try {
            PreparedStatement ps = con.prepareStatement(REMOVE_PHONECALLS_QUERY);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la llamada", e);
        }
    }

    @Override
    public ReturnedPhoneCallDto getById(Integer id) {
        ReturnedPhoneCallDto returnedPhoneCallDto = null;
        try {
            PreparedStatement ps = con.prepareStatement(GETBYID_PHONECALLS_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                returnedPhoneCallDto = createPhoneCall(rs);
            }
            rs.close();
            ps.close();
            return returnedPhoneCallDto;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener datos de la llamada", e);
        }
    }

    @Override
    public PhoneCallDto add(PhoneCallDto value) {
        return null;
    }


    @Override
    public ReturnedPhoneCallDto addPhoneCall(PhoneCallDto value) {
        ReturnedPhoneCallDto returnedPhoneCallDto =null;
        return returnedPhoneCallDto;
    }

    private ReturnedPhoneCallDto createPhoneCall(ResultSet rs) throws SQLException {
        ReturnedPhoneCallDto returnedPhoneCallDto = new ReturnedPhoneCallDto(rs.getInt("id_phonecall"),
                rs.getString("line_number_from"),
                rs.getString("line_number_to"),
                cityMySQLDao.getCityName(rs.getInt("id_city_from_fk")),
                cityMySQLDao.getCityName(rs.getInt("id_city_to_fk")),
                rs.getInt("duration"),
                rs.getDate("call_date"),
                rs.getFloat("total_price"));
        return returnedPhoneCallDto;
    }


    @Override
    public List<ReturnedPhoneCallDto> getAll() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(BASE_PHONECALLS_QUERY);
            List<ReturnedPhoneCallDto> phoneCallDtos = new ArrayList<>();
            while (rs.next()) {
                phoneCallDtos.add(createPhoneCall(rs));
            }
            rs.close();
            st.close();
            return phoneCallDtos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la lista de llamadas", e);
        }
    }

    @Override
    public List<ReturnedPhoneCallDto> getAllPhoneCallsFromUserId(Integer userId) {
        try {
            PreparedStatement ps = con.prepareStatement(GETBYID_USERPHONECALLS_QUERY);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            List<ReturnedPhoneCallDto> returnedPhoneCallDtoList = new ArrayList<>();
            while (rs.next()) {
                returnedPhoneCallDtoList.add(createPhoneCall(rs));
            }
            rs.close();
            ps.close();
            return returnedPhoneCallDtoList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la lista de llamadas por usuario", e);
        }
    }
}
