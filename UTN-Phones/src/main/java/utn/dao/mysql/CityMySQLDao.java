package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.CityDao;
import utn.model.City;
import utn.model.Province;
import utn.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utn.dao.mysql.MySQLUtils.*;

@Repository
public class CityMySQLDao implements CityDao {
    Connection con;

    @Autowired
    public CityMySQLDao(Connection con) {
        this.con = con;
    }

    @Override
    public City getById(Integer id) {
        City c = null;
        try {
            PreparedStatement ps = con.prepareStatement(GETBYID_CITY_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c = createCity(rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    private City createCity(ResultSet rs) throws SQLException {
        City c = new City(rs.getInt("id_city"), rs.getString("city_name"), rs.getInt("prefix"),
                new Province(rs.getInt("id_province"), rs.getString("province_name")));
        return c;
    }


    public String getCityName(Integer id) {
        String aux = "";
        try {
            PreparedStatement ps = con.prepareStatement("select city_name from cities where id_city=?");
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                aux = resultSet.getString("city_name");
            }
            resultSet.close();
            ps.close();
            return aux;
        } catch (SQLException e) {
            throw new RuntimeException("Error en traer el nombre de la ciudad", e);
        }
    }
}
