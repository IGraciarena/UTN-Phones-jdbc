package utn.service;

import utn.dao.UserDao;
import utn.dto.UserMostCalledNumberDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.UserAlreadyExistsException;
import utn.exceptions.UserNotExistsException;
import utn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {

    private UserDao dao;
    @Autowired
    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public User login(String userName, String password) throws UserNotExistsException {
        User user = dao.getByUserName(userName, password);
        return Optional.ofNullable(user).orElseThrow(() -> new UserNotExistsException());

    }

    public User add(User value) throws AlreadyExistsException {
        return dao.add(value);
    }

    public UserMostCalledNumberDto getMostCalledNumber(String lineNumber){
        return dao.getMostCalledNumber(lineNumber);
    }

    public void updateUser(User user) throws UserNotExistsException {
        User byId = dao.getById(user.getId());
        Optional.ofNullable(byId).orElseThrow(UserNotExistsException::new);
        dao.update(user);
    }

    public void removeUser(Integer idUser) throws UserNotExistsException {
        User user = dao.getById(idUser);
        Optional.ofNullable(user).orElseThrow(UserNotExistsException::new);
        dao.remove(idUser);
    }

    public User getById(Integer id){
        return dao.getById(id);
    }

}
