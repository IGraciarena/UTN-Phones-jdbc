package utn.dao;

import utn.model.Province;

public interface ProvinceDao extends AbstractDao<Province> {
    Province getById(Integer id);
}
