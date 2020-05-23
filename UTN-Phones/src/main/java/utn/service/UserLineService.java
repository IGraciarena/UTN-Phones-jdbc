package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.UserLineDao;
import utn.dao.mysql.UserLineMySQLDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.UserLine;

@Service
public class UserLineService {
    UserLineDao dao;

    @Autowired
    public UserLineService(UserLineDao userLineDao){
        this.dao = userLineDao;
    }

    public UserLine add(UserLine userLine) throws AlreadyExistsException {
       return dao.add(userLine);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(UserLine userLine) {
        dao.update(userLine);
    }

    public UserLine getById(Integer id) {
        return dao.getById(id);
    }

}
