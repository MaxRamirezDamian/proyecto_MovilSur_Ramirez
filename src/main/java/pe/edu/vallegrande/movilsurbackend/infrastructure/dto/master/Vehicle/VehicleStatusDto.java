package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehicleStatusDto {
    @NotBlank(message = "El estado del vehículo no puede estar vacío")
    private String vehicleStatus;
}