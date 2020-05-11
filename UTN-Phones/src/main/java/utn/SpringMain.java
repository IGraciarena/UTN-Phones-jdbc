package utn;

import utn.controller.UserController;
import utn.execptions.UserNotexistException;
import utn.execptions.ValidationException;
import utn.model.City;
import utn.model.Province;
import utn.model.User;
import utn.model.enumerated.UserType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.Date;

public class SpringMain {

    public static void main(String[] args)throws SQLException {
        try {
            UserType userType = UserType.CLIENT;
            Date udt = new Date();
            User newUser = new User(null,"carlitos","bala",343434344,udt,"carlox","asd132","asd@asd",new City(1,"Mar del Plata",0223,new Province(1,"Buenos Aires")),userType);
            ApplicationContext context = new ClassPathXmlApplicationContext("annotationContext.xml");
            UserController userController = context.getBean("userController",UserController.class);
//            u = userController.createUser(u);
            User u2 = userController.login("villordin", "villoria");
            User u3 = userController.getById(1);

        } catch (UserNotexistException userNotExist) {
            userNotExist.printStackTrace();
        } catch (ValidationException validationException) {
            validationException.printStackTrace();
/*        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }*/
            System.out.println("AAA");
        }
    }

}
