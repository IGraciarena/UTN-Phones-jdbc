package utn.dao;

import utn.model.Rate;

public interface RateDao extends AbstractDao<Rate> {
    Rate getById(Integer id);
}
