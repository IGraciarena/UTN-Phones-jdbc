package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLine {

    private Integer id;

    private Integer lineNumber;

    private User userId;

}
