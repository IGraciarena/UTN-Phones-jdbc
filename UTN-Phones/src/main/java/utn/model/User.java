package utn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.model.enumerated.UserType;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private Integer id;
    private String name;
    private String surname;
    private Integer dni;
    private Date birthdate;
    private String username;
    private String password;
    private String email;
    private City city;
    private UserType userType;

}