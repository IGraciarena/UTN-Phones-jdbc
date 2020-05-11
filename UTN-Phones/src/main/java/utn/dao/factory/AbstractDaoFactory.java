package utn.dao.factory;

import utn.dao.UserDao;

import java.util.Properties;

public abstract class AbstractDaoFactory {

    Properties config;

    public AbstractDaoFactory(Properties config) {
        this.config = config;
    }

    public abstract UserDao getUserDao();

}
