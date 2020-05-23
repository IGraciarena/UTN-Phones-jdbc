package utn.controller;

import org.springframework.stereotype.Controller;
import utn.exceptions.AlreadyExistsException;
import utn.model.UserLine;
import utn.service.UserLineService;

@Controller
public class UserLineController {

    UserLineService userLineService;

    public UserLineController (UserLineService userLineService){
        this.userLineService = userLineService;
    }

    public UserLine add(UserLine userLine) throws AlreadyExistsException {
       return userLineService.add(userLine);
    }
    public void remove(Integer id){
        userLineService.remove(id);
    }

    public void update(UserLine userLine){
        userLineService.update(userLine);
    }

    public UserLine getById(Integer id){
        return userLineService.getById(id);
    }
}
