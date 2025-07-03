package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DriverUpdatedto {
    //dto usuario
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

//dto driver
private String linceseType;
private String licenseCategory;
private String licenseNumber;
private LocalDate licenseExpery;
private Integer experienceYears;
private String licenseClass;
private String status;

//vehiculos agregar
private List <VehicleCreateDto> vehicles;

}
