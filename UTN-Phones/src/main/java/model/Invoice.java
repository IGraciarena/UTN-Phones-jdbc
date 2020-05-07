package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enumerated.Status;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invoice {

    private Integer id;
    private Integer callCount;
    private float priceCost;
    private float priceTotal;
    private Date dateEmission;
    private Date dateExpiration;
    private Status status;
    private User userId;
    private UserLine userLine;


}
