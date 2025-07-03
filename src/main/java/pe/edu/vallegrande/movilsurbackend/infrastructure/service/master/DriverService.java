package pe.edu.vallegrande.movilsurbackend.infrastructure.service.master;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.movilsurbackend.domain.enums.Status;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Driver;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.User;
import pe.edu.vallegrande.movilsurbackend.infrastructure.complement.DriverComplement;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Driver.Driver_User_Dto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master.DriverRepository;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final DriverComplement driverComplement;

    public List<Driver_User_Dto> listAll() {
        log.info("Listando todos los conductores");
        return driverRepository.findAll().stream()
                .map(driverComplement::toDto)
                .collect(Collectors.toList());
    }

    public Driver_User_Dto getById(UUID id) {
        log.info("Buscando un conductor por su id {}", id);
        return driverRepository.findById(id)
                .map(driverComplement::toDto)
                .orElseThrow(() -> new RuntimeException("No se encontro el conductor con el id: " + id));
    }

    public List<Driver_User_Dto> filterGeneral(String filtro) {
        Objects.requireNonNull(filtro, "El filtro no puede ser nulo");
        log.info("Filtrando conductores por: {}", filtro);

        return driverRepository.filterGeneral(filtro.trim().toLowerCase())
                .stream()
                .map(driverComplement::toDto)
                .collect(Collectors.toList());
    }

    public List<Driver_User_Dto> filterByLicense(String type, String category) {
        Objects.requireNonNull(type, "El tipo de licencia no puede ser nulo");
        Objects.requireNonNull(category, "La categoria de licencia no puede ser nula");
        log.info("Filtrando conductores por licencia tipo: {} y categoría: {}", type, category);
        return driverRepository.findByLicenseTypeAndLicenseCategory(type, category).stream()
                .map(driverComplement::toDto)
                .collect(Collectors.toList());
    }

    public List<Driver_User_Dto> filterByStatus(String status) {
        Objects.requireNonNull(status, "El estado no puede ser nulo");
        log.info("Filtrando conductores por estado: {}", status);
        return driverRepository.findByStatus(status).stream()
                .map(driverComplement::toDto)
                .collect(Collectors.toList());
    }

    public List<Driver_User_Dto> filterByAvailability(String availability) {
        Objects.requireNonNull(availability, "La disponibilidad no puede ser nula");
        log.info("Filtrando conductores por disponibilidad: {}", availability);
        return driverRepository.findByAvailable(availability).stream()
                .map(driverComplement::toDto)
                .collect(Collectors.toList());
    }



    @Transactional
    public Driver_User_Dto create(Driver_User_Dto dto) {
        log.info("Creando un conductor con datos: {}", dto);

        User user = driverComplement.buildDUserFormDto(dto);
        user = userRepository.save(user);

        Driver driver = driverComplement.buildDriverFormDto(dto, user);
        driver = driverRepository.save(driver);
        return driverComplement.toDto(driver);
    }

    // Actualiza los datos necesarios para un conductor
    @Transactional
    public Driver_User_Dto update(Driver_User_Dto dto, UUID id) {
        log.info("Actualizando un conductor con id: {} y datos: {}", id, dto);
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el conductor con el id: " + id));
        driverComplement.updateDriverFormDto(driver, dto);
        return driverComplement.toDto(driverRepository.save(driver));
    }

    //Remplaza todos los datos de un conductor
    @Transactional
    public Driver_User_Dto replace(Driver_User_Dto dto, UUID id) {
        log.info("Actualizando un conductor con id: {} y datos: {}", id, dto);

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el conductor con el id: " + id));

        User user = driverComplement.updateUserFormDto(dto, driver.getUser());
        user = userRepository.save(user);

        driverComplement.updateDriverFormDto(driver, dto);
        driver.setUser(user);
        driver = driverRepository.save(driver);
        return driverComplement.toDto(driver);
    }

    @Transactional
    public Driver_User_Dto softDelete(UUID id) {
        log.info("Eliminando un conductor con id: {}", id);
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el conductor con el id: " + id));

        User user = driver.getUser();
        user.setStatus(Status.INACTIVE.name());
        userRepository.save(user);

        driver.setAvailable(Status.N.name());
        driver.setStatus(Status.INACTIVE.name());
        return driverComplement.toDto(driverRepository.save(driver));
    }

    @Transactional
    public Driver_User_Dto softRestore(UUID id) {
        log.info("Restaurando un conductor con id: {}", id);
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el conductor con el id: " + id));
        User user = driver.getUser();
        user.setStatus(Status.ACTIVE.name());
        userRepository.save(user);
        driver.setAvailable(Status.Y.name());

        driver.setStatus(Status.ACTIVE.name());
        return driverComplement.toDto(driverRepository.save(driver));
    }



}
