package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.model.Rate;
import utn.service.RateService;

@Controller
public class RateController {
    RateService rateService;

    @Autowired
    public RateController(RateService rateService){
        this.rateService = rateService;
    }
    public void add(Rate rate){

    }
    public void remove(Integer id){
        rateService.remove(id);
    }
    public void update(Rate rate){
        rateService.update(rate);
    }
    public Rate getById(Integer id){
        return rateService.getById(id);
    }
}
