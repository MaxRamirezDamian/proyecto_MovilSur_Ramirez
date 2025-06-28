package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDto {

    @NotNull
    private UUID userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID driverId;

    @NotNull
    private UUID vehicleId;

    @NotBlank
    private String originAddress;

    @NotBlank
    private String destinationAddress;

    @NotNull
    private Integer seatsUsed;

    @NotNull
    private UUID paymentMethodId;

    private List<TripDetailDto> details;
}