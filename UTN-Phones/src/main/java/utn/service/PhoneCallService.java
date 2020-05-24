package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.PhoneCallDao;
import utn.dto.PhoneCallDto;
import utn.exceptions.AlreadyExistsException;
import utn.model.PhoneCall;

@Service
public class PhoneCallService {
    PhoneCallDao dao;

    @Autowired
    public PhoneCallService(PhoneCallDao dao){
        this.dao = dao;
    }

    public PhoneCallDto getById(Integer id) {
       return dao.getById(id);
    }

    public void add(PhoneCall phoneCall) throws AlreadyExistsException {
         dao.add(phoneCall);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(PhoneCall phoneCall) {
        dao.update(phoneCall);
    }
}
