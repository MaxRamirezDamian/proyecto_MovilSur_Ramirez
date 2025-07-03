package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDriverUpdateDto {
    private UUID driverId;
}