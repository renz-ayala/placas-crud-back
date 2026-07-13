package gg.renz.placainfo.infraestructure.shared.dtos.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLACA", schema = "EXTRANET")
public class PlateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_placa")
    private Long id;

    @Column(name = "numero_placa", nullable = false, unique = true, length = 10)
    private String plateNumber;

    @Column(name = "marca", length = 50)
    private String make;

    @Column(name = "modelo", length = 50)
    private String model;

    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "anio_fabricacion")
    private Integer manufactureYear;

    @Column(name = "propietario_dni", length = 15)
    private String ownerId;

    @Column(name = "estado", length = 20)
    private String state = "ACTIVO";

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime registrationDate;

    @Column(name = "observaciones", length = 500)
    private String observations;

    @PrePersist
    protected void onCreate() {
        if (this.registrationDate == null) {
            this.registrationDate = LocalDateTime.now();
        }
    }
}
