package utn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import utn.model.User;

import java.util.Date;

@SpringBootApplication
@EnableAsync//
@EnableScheduling//
public class SpringBootMain {
    public static void main(String[] args){
        SpringApplication.run(SpringBootMain.class,args);
    }
}
