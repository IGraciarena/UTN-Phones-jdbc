package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.UserLineDao;
import utn.dto.UserLineDto;
import utn.exceptions.AlreadyExistsException;
import utn.model.UserLine;

import java.util.List;

@Service
public class UserLineService {
    UserLineDao dao;

    @Autowired
    public UserLineService(UserLineDao userLineDao) {
        this.dao = userLineDao;
    }

    public void add(UserLine userLine) throws AlreadyExistsException {
        dao.add(userLine);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(UserLine userLine) {
        dao.update(userLine);
    }

    public UserLineDto getById(Integer id) {
        return dao.getById(id);
    }

    public List<UserLineDto> getAll() {
        return dao.getAll();
    }
}
