package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDetailDto {

    private String description;

    private Integer quantity;

    private Double cost;

    private String isFragile;
}

