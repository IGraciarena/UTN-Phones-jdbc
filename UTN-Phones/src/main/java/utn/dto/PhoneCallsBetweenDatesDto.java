package utn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class PhoneCallsBetweenDatesDto {
    Integer idUser;
    String dateFrom;
    String dateTo;
}
