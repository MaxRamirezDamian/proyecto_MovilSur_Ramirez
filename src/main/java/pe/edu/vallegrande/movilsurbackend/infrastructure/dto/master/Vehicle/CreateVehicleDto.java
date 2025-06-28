package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle;

import java.util.UUID;

import com.google.auto.value.AutoValue.Builder;
import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateVehicleDto {
    @NotNull
    private UUID driverId;

    @NotBlank @Size(max = 10)
    private String licensePlate;

    @NotBlank @Size(max = 50)
    private String brand;

    @NotBlank @Size(max = 50)
    private String model;

    @NotNull @Min(1900) @Max(2100)
    private Integer year;

    @Size(max = 30)
    private String color;

    @NotBlank @URL
    private String vehiclePhoto;

    @NotNull @Min(4) @Max(7)
    private Integer seatCount;

    @NotBlank
    private String vehicleStatus;   // ej. "DISPONIBLE"

    @NotBlank
    private String vehicleType;     // ej. "AUTO"

    @NotBlank
    private String fuelType;        // ej. "GASOLINA"
}
