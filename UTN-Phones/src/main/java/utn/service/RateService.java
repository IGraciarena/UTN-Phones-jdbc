package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.RateDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.Rate;

@Service
public class RateService {
    RateDao dao;

    @Autowired
    public RateService(RateDao rateDao){
        this.dao = rateDao;
    }
    public Rate add(Rate rate) throws AlreadyExistsException {
        return dao.add(rate);
    }
    public Rate getById(Integer id) {
        return dao.getById(id);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(Rate rate) {
        dao.update(rate);
    }
}
