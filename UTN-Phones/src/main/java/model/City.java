package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class City {

    private Integer cityId;
    private String name;
    private String prefix;
    private Province province;

    public City(int id, String name, String prefix, Province province) {
    }
}