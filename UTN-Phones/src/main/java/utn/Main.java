package utn;

import utn.controller.UserController;
import utn.dao.UserDao;
import utn.dao.mysql.UserMySQLDao;
import utn.execptions.UserAlreadyExistsException;
import utn.execptions.UserNotexistException;
import utn.model.City;
import utn.model.Province;
import utn.model.User;
import utn.model.enumerated.UserType;
import utn.service.UserService;

import utn.execptions.ValidationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args)throws SQLException {
        //1)Connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //se genera la conexion
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/utnphones","root","");


        UserDao userDao = new UserMySQLDao(con);
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);
        try {
            System.out.println("-----------------Login----------------");
             User u =userController.login("villordin","villoria");
            System.out.println(u);
            System.out.println("-----------------ById----------------");
            u = userController.getById(1);
            System.out.println(u);
            System.out.println("-----------------Insert----------------");
            UserType userType = UserType.CLIENT;
            Date udt = new Date();
            User newUser = new User(null,"carlitos","bala",343434344,udt,"carlox","asd132","asd@asd",new City(1,"Mar del Plata",0223,new Province(1,"Buenos Aires")),userType);
            u = userController.add(newUser);
            System.out.println(u);
        } catch (UserNotexistException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }
}
