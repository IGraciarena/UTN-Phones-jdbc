package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.dto.RateDto;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.Rate;
import utn.service.RateService;

import java.util.List;

@Controller
public class RateController {
    RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    public Rate add(Rate rate) throws AlreadyExistsException {
        return rateService.add(rate);
    }

    public void remove(Integer id) throws NoExistsException {
        rateService.remove(id);
    }

    public void update(Rate rate) throws NoExistsException {
        rateService.update(rate);
    }

    public RateDto getById(Integer id) throws NoExistsException {
        return rateService.getById(id);
    }

    public List<RateDto> getAll() {
        return rateService.getAll();
    }
}
