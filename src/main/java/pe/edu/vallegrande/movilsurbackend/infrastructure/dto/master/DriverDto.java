package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDto {

    private String licenseType;

    private String licenseCategory;

    private String licenceNumber;

    private LocalDate licenceExpiry;

    private Integer experienceYears;

    private String licenceClass;
}
