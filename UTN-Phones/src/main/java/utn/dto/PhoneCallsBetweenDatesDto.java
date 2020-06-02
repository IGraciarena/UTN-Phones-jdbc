package utn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneCallsBetweenDatesDto {
    Integer idUser;
    String dateFrom;
    String dateTo;
}
