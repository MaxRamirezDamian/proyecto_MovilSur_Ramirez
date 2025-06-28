package pe.edu.vallegrande.movilsurbackend.domain.model.master;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VEHICLES")
public class Vehicle {
    @Id
    @GeneratedValue
    @Column(name = "VEHICLE_ID")
    private UUID vehicleId;

    @ManyToOne
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;

    @Column(name = "LICENSE_PLATE")
    private String licensePlate;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "VEHICLE_PHOTO")
    private String vehiclePhoto;

    @Column(name = "SEAT_COUNT")
    @Min(4)
    @Max(7)
    private Integer seatCount;

    @Column(name = "VEHICLE_STATUS")
    @Builder.Default
    private String vehicleStatus = "DISPONIBLE";

    @Column(name = "REGISTRATION_DATE")
    private LocalDateTime registrationDate;

    @Column(name = "STATUS")
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    @Column(name = "FUEL_TYPE")
    private String fuelType;
}
