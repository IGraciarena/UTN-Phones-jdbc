package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.RateDao;
import utn.dto.RateDto;
import utn.exceptions.AlreadyExistsException;
import utn.model.Rate;

import java.util.List;

@Service
public class RateService {
    RateDao dao;

    @Autowired
    public RateService(RateDao rateDao) {
        this.dao = rateDao;
    }

    public void add(Rate rate) throws AlreadyExistsException {
        dao.add(rate);
    }

    public RateDto getById(Integer id) {
        return dao.getById(id);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(Rate rate) {
        dao.update(rate);
    }

    public List<RateDto> getAll() {
        return dao.getAll();
    }
}
