package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    private UUID vehicleId;
    
    private UUID driverId;

    private String licensePlate;

    private String brand;

    private String model;

    private Integer year;

    private String color;

    private String vehiclePhoto;

    private Integer seatCount;
    
    private String vehicleStatus;
  
    private LocalDateTime registrationDate;

    private String status ;
    
    private String vehicleType;

    private String fuelType;



}