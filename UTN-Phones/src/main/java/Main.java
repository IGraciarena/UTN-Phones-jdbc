import controller.UserController;
import dao.UserDao;
import dao.mysql.UserMySQLDao;
import execptions.UserNotexistException;
import model.User;
import service.UserService;

import execptions.ValidationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            User u =userController.login("villordin","villoria");
            System.out.println(u);
            //u.mostrar();
        } catch (UserNotexistException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }finally {
            con.close();
        }
    }
}
