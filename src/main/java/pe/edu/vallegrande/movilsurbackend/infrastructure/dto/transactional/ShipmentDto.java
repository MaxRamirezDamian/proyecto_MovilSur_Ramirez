package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentDto {

    private UUID userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID driverId;

    private UUID vehicleId;

    @NotBlank
    private String pickupAddress;

    @NotBlank
    private String descriptionAddress;

    @NotBlank
    private LocalDate deliveryDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime hourShipments;

    private List<ShipmentDetailDto> details ;
}
