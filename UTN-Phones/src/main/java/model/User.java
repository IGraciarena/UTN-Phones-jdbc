package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import model.enumerated.UserType;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class User {

    private Integer id;
    private String name;
    private String surname;
    private int DNI;
    private Date birthdate;
    private String username;
    private String password;
    private String email;
    private City city;
    private UserType userType;

    public User(int id, String name, String surname, int dni, java.sql.Date birthdate, String username, String password, String email, City city) {
    }
}