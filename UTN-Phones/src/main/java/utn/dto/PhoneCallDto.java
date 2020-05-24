package utn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PhoneCallDto {
    private String lineNumberFrom;
    private String lineNumberTo;
    private Integer duration;
}
