package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.UserLineDao;
import utn.dto.UserLineDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.UserLine;

import java.util.List;
import java.util.Optional;

@Service
public class UserLineService {
    UserLineDao dao;

    @Autowired
    public UserLineService(UserLineDao userLineDao) {
        this.dao = userLineDao;
    }

    public void add(UserLine userLine) throws AlreadyExistsException {
        if (dao.getUserLineByNumber(userLine.getLineNumber())) {
            throw new AlreadyExistsException();
        }
        dao.add(userLine);
    }

    public void remove(Integer id) throws NoExistsException {
        UserLineDto userLineDto = dao.getById(id);
        Optional.ofNullable(userLineDto).orElseThrow(NoExistsException::new);
        dao.remove(id);
    }

    public void update(UserLine userLine) throws NoExistsException {
        UserLineDto userLineDto = dao.getById(userLine.getId());
        Optional.ofNullable(userLineDto).orElseThrow(NoExistsException::new);
        dao.update(userLine);
    }

    public UserLineDto getById(Integer id) throws NoExistsException {
        UserLineDto userLineDto = dao.getById(id);
        Optional.ofNullable(userLineDto).orElseThrow(NoExistsException::new);
        return dao.getById(id);
    }

    public List<UserLineDto> getAll() {
        return dao.getAll();
    }
}
