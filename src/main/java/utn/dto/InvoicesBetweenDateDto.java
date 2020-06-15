package utn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoicesBetweenDateDto {
    Integer userID;
    String dateFrom;
    String dateTo;
}
