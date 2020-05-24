package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.dto.PhoneCallDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.exceptions.NoExistsException;
import utn.model.PhoneCall;
import utn.service.PhoneCallService;

import java.util.List;

@Controller
public class PhoneCallController {
    PhoneCallService phoneCallService;

    @Autowired
    public PhoneCallController(PhoneCallService phoneCallService) {
        this.phoneCallService = phoneCallService;
    }

    public void addPhoneCall(PhoneCallDto phoneCall) {
        phoneCallService.addPhoneCall(phoneCall);
    }

    public void remove(Integer id) throws NoExistsException {
        phoneCallService.remove(id);
    }

    public void update(PhoneCall phoneCall) throws NoExistsException {
        phoneCallService.update(phoneCall);
    }

    public ReturnedPhoneCallDto getById(Integer id) throws NoExistsException {
        return phoneCallService.getById(id);
    }

    public List<ReturnedPhoneCallDto> getAll() {
        return phoneCallService.getAll();
    }

    public List<ReturnedPhoneCallDto> getAllPhoneCallsFromUserId(Integer userId){
        return phoneCallService.getAllPhoneCallsFromUserId(userId);
    }
}
