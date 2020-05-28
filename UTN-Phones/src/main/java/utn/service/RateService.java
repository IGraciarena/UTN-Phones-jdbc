package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.RateDao;
import utn.dto.RateDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.Rate;

import java.util.List;
import java.util.Optional;

@Service
public class RateService {
    RateDao dao;

    @Autowired
    public RateService(RateDao rateDao) {
        this.dao = rateDao;
    }

    public Rate add(Rate rate) throws AlreadyExistsException {
        return dao.add(rate);
    }

    public RateDto getById(Integer id) throws NoExistsException {
        RateDto rateDto = dao.getById(id);
        Optional.ofNullable(rateDto).orElseThrow(NoExistsException::new);
        return dao.getById(id);
    }

    public void remove(Integer id) throws NoExistsException {
        RateDto rateDto = dao.getById(id);
        Optional.ofNullable(rateDto).orElseThrow(NoExistsException::new);
        dao.remove(id);
    }

    public void update(Rate rate) throws NoExistsException {
        RateDto rateDto = dao.getById(rate.getId());
        Optional.ofNullable(rateDto).orElseThrow(NoExistsException::new);
        dao.update(rate);
    }

    public List<RateDto> getAll() {
        return dao.getAll();
    }
}
