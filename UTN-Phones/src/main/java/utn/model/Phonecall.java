package utn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Phonecall {

    private Integer id;
    private UserLine lineFrom;
    private UserLine lineTo;
    private Integer duration;
    private Date callDate;
    private float pricePerMinute;
    private float totalPrice;
    private Invoice invoiceId;
    private City cityFrom;
    private City cityTo;

}
