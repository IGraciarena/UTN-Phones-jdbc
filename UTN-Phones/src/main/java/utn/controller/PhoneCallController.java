package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.exceptions.AlreadyExistsException;
import utn.model.PhoneCall;
import utn.service.PhoneCallService;

@Controller
public class PhoneCallController {
    PhoneCallService phoneCallService;

    @Autowired
    public PhoneCallController(PhoneCallService phoneCallService){
        this.phoneCallService = phoneCallService;
    }

    public PhoneCall add(PhoneCall phoneCall) throws AlreadyExistsException {
        return phoneCallService.add(phoneCall);
    }
    public void remove(Integer id){
        phoneCallService.remove(id);
    }
    public void update(PhoneCall phoneCall){
        phoneCallService.update(phoneCall);
    }
    public PhoneCall getById(Integer id){
        return phoneCallService.getById(id);
    }
}
