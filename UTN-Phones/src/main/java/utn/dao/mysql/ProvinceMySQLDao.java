package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.ProvinceDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.Province;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utn.dao.mysql.MySQLUtils.*;

@Repository
public class ProvinceMySQLDao implements ProvinceDao {
    Connection con;

    @Autowired
    public ProvinceMySQLDao(Connection con) {
        this.con = con;
    }


    @Override
    public void add(Province value) throws AlreadyExistsException {
        try {
            PreparedStatement ps = con.prepareStatement(INSERT_PROVINCE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, value.getProvinceName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                value.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar provincia", e);
        }
    }

    @Override
    public void update(Province value) {
        try {
            PreparedStatement ps = con.prepareStatement(UPDATE_PROVINCE_QUERY);
            ps.setString(1, value.getProvinceName());
            ps.setInt(2, value.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar provincia", e);
        }
    }

    @Override
    public void remove(Integer id) {
        try {
            PreparedStatement ps = con.prepareStatement(REMOVE_PROVINCE_QUERY);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar provincia", e);
        }

    }

    @Override
    public Province getById(Integer id) {
        Province province = null;
        try {
            PreparedStatement ps = con.prepareStatement(GETBYID_PROVINCE_QUERY);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                province = createProvince(resultSet);
            }
            ps.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return province;
    }

    private Province createProvince(ResultSet resultSet) throws SQLException {
        Province province = new Province(resultSet.getInt("id_province"), resultSet.getString("province_name"));
        return province;
    }

    @Override
    public List<Province> getAll() {
        try {
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery(GETALL_PROVINCE_QUERY);
            List<Province> provinceList = new ArrayList<>();
            while (resultSet.next()) {
                provinceList.add(createProvince(resultSet));
            }
            st.close();
            resultSet.close();
            return provinceList;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la lista de provincias", e);
        }
    }
}
