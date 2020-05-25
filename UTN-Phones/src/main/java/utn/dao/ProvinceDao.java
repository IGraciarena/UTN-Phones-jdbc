package utn.dao;

import utn.model.Province;

import java.util.List;

public interface ProvinceDao extends AbstractDao<Province> {
    Province getById(Integer id);

    public List<Province> getAll();
}
