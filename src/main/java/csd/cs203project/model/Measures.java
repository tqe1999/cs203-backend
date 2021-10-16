package csd.cs203project.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Measures {
    //admin keys this
    private @Id @GeneratedValue Long id;
    @NotNull
    private String typeOfShop;
    private int dineInSize;
    private int maxGrpSizeVacc;
    private int maxGrpSizeNonVacc;
    private int socialDistance;
    private String closingTime;
    private String phase;

}
