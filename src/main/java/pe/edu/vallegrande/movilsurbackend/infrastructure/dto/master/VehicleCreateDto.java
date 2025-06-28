package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateDto {
    private String licensePlate;
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private String vehiclePhoto;
    private Integer seatCount;
    private String vehicleType;
    private String fuelType;
}
