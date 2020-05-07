package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class City {

    private Integer id;
    private String name;
    private String prefix;
    private Province provinceId;

}