package dao.mysql;

import dao.UserDao;
import model.City;
import model.Province;
import model.User;
import model.enumerated.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.mysql.MySQLUtils.*;


public class UserMySQLDao implements UserDao {

    Connection connection;

    public UserMySQLDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getByUserName(String username, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_USERNAME_USER_QUERY);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = createUser(rs);
            }
            rs.close();
            ps.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener datos de usuario", e);
        }
    }

    private User createUser(ResultSet rs) throws SQLException {
        UserType userType = UserType.valueOf(rs.getString("user_type"));
        User u = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("surname"),
                    rs.getInt("dni"), rs.getDate("birthdate"),rs.getString("username"),rs.getString("pwd"),rs.getString("email"),
                        new City(rs.getInt("id"), rs.getString("city_name"),rs.getInt("prefix"),
                          new Province(rs.getInt("id"), rs.getString("province_name"))),userType);
        return u;
    }

    @Override
    public List<User> getByCity(City city) {
        return null;
    }

    @Override
    public User add(User value) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, value.getName());
            ps.setString(2, value.getSurname());
            ps.setInt(3, value.getDni());
            ps.setDate(4,new Date(value.getBirthdate().getTime()));
            ps.setString(5, value.getUsername());
            ps.setString(6, value.getPassword());
            ps.setString(7, value.getEmail());
            ps.setString(8, String.valueOf(value.getUserType()));
            ps.setInt(9, value.getCity().getId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                value.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public Integer update(User value) {
        try {
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_QUERY);
            ps.setString(1, value.getName());
            ps.setString(2, value.getSurname());
            ps.setInt(3, value.getCity().getId());
            ps.setString(4, value.getPassword());
            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (SQLException sqlException) {
            throw new RuntimeException("Error al modificar usuario", sqlException);
        }

    }

    @Override
    public Integer remove(Integer id) {
        try {
            PreparedStatement ps = connection.prepareStatement(REMOVE_USER_QUERY);
            ps.setInt(1, id);
            Integer rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

    @Override
    public Integer remove(User value) {
        return remove(value.getId());
    }

    @Override
    public User getById(Integer id) {
        User user = null;

        try {
            PreparedStatement ps = connection.prepareStatement(GET_BY_ID_USER_QUERY);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = createUser(rs);
            }
            rs.close();
            ps.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener datos de usuario", e);
        }

    }

    @Override
    public List<User> getAll() {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(BASE_USER_QUERY);
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                userList.add(createUser(rs));
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la lista de usuarios", e);
        }
    }
}
