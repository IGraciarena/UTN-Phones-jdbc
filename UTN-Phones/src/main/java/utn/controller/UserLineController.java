package utn.controller;

import org.springframework.stereotype.Controller;
import utn.dto.UserLineDto;
import utn.exceptions.AlreadyExistsException;
import utn.model.UserLine;
import utn.service.UserLineService;

import java.util.List;

@Controller
public class UserLineController {

    UserLineService userLineService;

    public UserLineController(UserLineService userLineService) {
        this.userLineService = userLineService;
    }

    public void add(UserLine userLine) throws AlreadyExistsException {
        userLineService.add(userLine);
    }

    public void remove(Integer id) {
        userLineService.remove(id);
    }

    public void update(UserLine userLine) {
        userLineService.update(userLine);
    }

    public UserLineDto getById(Integer id) {
        return userLineService.getById(id);
    }

    public List<UserLineDto> getAll() {
        return userLineService.getAll();
    }
}
