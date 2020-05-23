package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.PhoneCallDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.PhoneCall;

@Service
public class PhoneCallService {
    PhoneCallDao dao;

    @Autowired
    public PhoneCallService(PhoneCallDao dao){
        this.dao = dao;
    }

    public PhoneCall getById(Integer id) {
       return dao.getById(id);
    }

    public PhoneCall add(PhoneCall phoneCall) throws AlreadyExistsException {
        return dao.add(phoneCall);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(PhoneCall phoneCall) {
        dao.update(phoneCall);
    }
}
