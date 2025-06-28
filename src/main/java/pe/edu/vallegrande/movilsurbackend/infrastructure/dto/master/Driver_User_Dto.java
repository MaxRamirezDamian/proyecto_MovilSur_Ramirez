package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver_User_Dto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String firstName;

    private String lastName;

    private String documentType;

    private String documentNumber;

    private String email;

    private String phone;

    private String address;

    private String userPhoto;

    private String username;

    private String password;

    private String licenseType;

    private String licenseCategory;

    private String licenceNumber;

    private LocalDate licenceExpiry;

    private Integer experienceYears;

    private String licenceClass;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String available;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String estado;
}
