package gg.renz.placainfo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Plate {
    private Long id;
    private String plateNumber;
    private String make;
    private String model;
    private String color;
    private Integer manufactureYear;
    private String ownerId;
    private String state;
    private LocalDateTime registrationDate;
    private String observations;
}
