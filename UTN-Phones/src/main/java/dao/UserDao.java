package dao;


import model.City;
import model.User;

import java.util.List;

public interface UserDao extends AbstractDao<User> {

    User getByUsername(String username, String password);

    List<User> getByCity(City city);

    User add(User u);

    Integer update(User u);
}
