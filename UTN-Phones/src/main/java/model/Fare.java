package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Fare {

    private Integer id;

    private float pricePerMinute;

    private float costPerMin;

    private City cityFrom;

    private City cityTo;

}
