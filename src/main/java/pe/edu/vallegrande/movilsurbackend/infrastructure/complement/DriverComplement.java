package pe.edu.vallegrande.movilsurbackend.infrastructure.complement;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.movilsurbackend.domain.enums.RolUser;
import pe.edu.vallegrande.movilsurbackend.domain.enums.Status;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Driver;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.User;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Driver_User_Dto;

import java.time.LocalDateTime;


@Service
public class DriverComplement {

    public Driver_User_Dto toDto(Driver driver){
        return Driver_User_Dto.builder()
                .id(driver.getDriverId())
                .firstName(driver.getUser().getFirstName())
                .lastName(driver.getUser().getLastName())
                .documentType(driver.getUser().getDocumentType())
                .documentNumber(driver.getUser().getDocumentNumber())
                .email(driver.getUser().getEmail())
                .phone(driver.getUser().getPhone())
                .address(driver.getUser().getAddress())
                .userPhoto(driver.getUser().getUserPhoto())
                .username(driver.getUser().getUsername())
                .password(driver.getUser().getPassword())
                .licenseType(driver.getLicenseType())
                .licenseCategory(driver.getLicenseCategory())
                .licenceNumber(driver.getLicenseNumber())
                .licenceExpiry(driver.getLicenseExpiration())
                .experienceYears(driver.getExperienceYears())
                .licenceClass(driver.getLicenseClass())
                .available(driver.getAvailable())
                .estado(driver.getStatus())
                .build();
    }


    /* Metodos auxiliares para crear un conductor */

    public User buildDUserFormDto(Driver_User_Dto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .documentType(dto.getDocumentType())
                .documentNumber(dto.getDocumentNumber())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .userPhoto(dto.getUserPhoto())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .createdAt(LocalDateTime.now())
                .userType(RolUser.DRIVER.name())
                .status(Status.ACTIVE.name())
                .build();
    }

    public Driver buildDriverFormDto(Driver_User_Dto dto, User user) {
        return Driver.builder()
                .licenseType(dto.getLicenseType())
                .licenseCategory(dto.getLicenseCategory())
                .licenseNumber(dto.getLicenceNumber())
                .licenseExpiration(dto.getLicenceExpiry())
                .available(Status.Y.name())
                .experienceYears(dto.getExperienceYears())
                .licenseClass(dto.getLicenceClass())
                .status(Status.ACTIVE.name())
                .user(user)
                .build();
    }

    /* Metodos auxiliares para actualizar un conductor */

    public Driver updateDriverFormDto(Driver driver, Driver_User_Dto dto) {
        driver.setLicenseType(dto.getLicenseType());
        driver.setLicenseCategory(dto.getLicenseCategory());
        driver.setLicenseNumber(dto.getLicenceNumber());
        driver.setLicenseExpiration(dto.getLicenceExpiry());
        driver.setAvailable(dto.getAvailable());
        driver.setExperienceYears(dto.getExperienceYears());
        driver.setLicenseClass(dto.getLicenceClass());
        driver.setStatus(Status.ACTIVE.name());
        return driver;
    }

    public User updateUserFormDto(Driver_User_Dto dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDocumentType(dto.getDocumentType());
        user.setDocumentNumber(dto.getDocumentNumber());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setUserPhoto(dto.getUserPhoto());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(RolUser.DRIVER.name());
        user.setStatus(Status.ACTIVE.name());
        return user;
    }
}
