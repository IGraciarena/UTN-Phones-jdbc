package utn.dao;


import utn.exceptions.UserAlreadyExistsException;
import utn.model.City;
import utn.model.User;

import java.util.List;
//todavia no voy a definir las operaciones, esta clase va a decir que operaciones voy a poder realizar sobre un usuario
//la vamos a codificar en la clase concreta EJ: UserMySqlDao,UserMemoryDao

public interface UserDao extends AbstractDao<User> {

    User getByUserName(String username, String password);

    List<User> getByCity(City city);

    void update(User u);

    User add(User u) throws UserAlreadyExistsException;
}
