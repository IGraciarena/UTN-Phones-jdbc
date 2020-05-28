package utn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ReturnedPhoneCallDto {
    private Integer id;
    private String lineNumberFrom;
    private String lineNumberTo;
    private String cityFrom;
    private String cityTo;
    private Integer duration;
    private Date callDate;
    private float totalPrice;
}
