package pe.edu.vallegrande.movilsurbackend.infrastructure.service.master;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Driver;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Vehicle;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle.VehicleCreateDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle.VehicleDriverUpdateDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.exception.CustomException;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master.DriverRepository;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master.VehicleRepository;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }


/* Buscar vehiculo por su id*/
    public Vehicle getVehicleById(UUID vehicleId) {
    try {
        log.info("Buscando vehículo por vehicleId={}", vehicleId);
        return vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new CustomException(404,
                "Vehículo no encontrado",
                "No existe un vehículo con id " + vehicleId));
    } catch (CustomException ce) {
        // ya es un 404, sólo relanzamos
        throw ce;
    } catch (Exception e) {
        log.warn("Error al buscar vehículo por id: {}", e.getMessage());
        throw new CustomException(500,
            "Error interno al buscar vehículo",
            e.getMessage());
    }
}






/* Buscar vehiculo por el id del conductor*/

@PersistenceContext 
    private EntityManager entityManager;

public List<Vehicle> listVehiclesByDriver(UUID driverId) {
    try {
        log.info("Buscando vehículos por driverId={}", driverId);

        String jpql = "SELECT v FROM Vehicle v WHERE v.driver.driverId = :driverId";
        TypedQuery<Vehicle> query = entityManager.createQuery(jpql, Vehicle.class);
        query.setParameter("driverId", driverId);

        return query.getResultList();
    } catch (Exception e) {
        log.warn("Error al listar vehículos por conductor: {}", e.getMessage());
        throw new CustomException(500, "No se pueden listar por conductor", e.getMessage());
    }
}

/* Listar Vehiculos por su estado emocional digo que?*/
public List<Vehicle> listVehiclesByStatus(String status) {
    try {
        log.info("Buscando vehículos con estado: {}", status);
        
        String jpql = "SELECT v FROM Vehicle v WHERE v.status = :status";
        TypedQuery<Vehicle> query = entityManager.createQuery(jpql, Vehicle.class);
        query.setParameter("status", status);
        
        return query.getResultList();
    } catch (Exception e) {
        log.warn("Error al listarlos por su estado: {}", e.getMessage());
        throw new CustomException(500, "No se pueden listar ", e.getMessage());
    }
}

/* Listar Vehiculos por su estado de operacion*/
public List<Vehicle> listByVehicleStatus(String vehicleStatus) {
    try {
        log.info("Buscando vehículos por estado de operación: { }", vehicleStatus);
        
        String jpql = "SELECT v FROM Vehicle v WHERE v.vehicleStatus = :vehicleStatus";
        TypedQuery<Vehicle> query = entityManager.createQuery(jpql, Vehicle.class);
        query.setParameter("vehicleStatus", vehicleStatus);

        return query.getResultList();
    } catch (Exception e) {
        log.warn("Error al listar vehículos activos: {}", e.getMessage());
        throw new CustomException(500, "No se pueden listar ", e.getMessage());
    }
}



/* crear Vehiculos*/
@Transactional
public Vehicle createVehicle(VehicleCreateDto dto) {
    log.info("Creando vehículo sin conductor: {}", dto);
    Driver driver = driverRepository.findById(dto.getDriverId())
    .orElseThrow(() -> new CustomException(404, "Conductor no encontrado",
            "No existe un conductor con ID " + dto.getDriverId()));

    Vehicle vehicle = Vehicle.builder()
            .driver(driver)
            .licensePlate(dto.getLicensePlate())
            .brand(dto.getBrand())
            .model(dto.getModel())
            .year(dto.getYear())
            .color(dto.getColor())
            .vehiclePhoto(dto.getVehiclePhoto())
            .seatCount(dto.getSeatCount())
            .vehicleType(dto.getVehicleType())
            .fuelType(dto.getFuelType())
            .vehicleStatus("DISPONIBLE")
            .status("ACTIVE")
            .registrationDate(java.time.LocalDateTime.now())
            .build();

    return vehicleRepository.save(vehicle);
}


@Transactional
public Vehicle updateVehicle(UUID vehicleId, VehicleCreateDto dto) {
    log.info("Actualizando vehículo con ID: {}", vehicleId);

    Vehicle existing = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new CustomException(404, "Vehículo no encontrado",
                    "No existe un vehículo con ID " + vehicleId));

    // Actualizar campos
    existing.setLicensePlate(dto.getLicensePlate());
    existing.setBrand(dto.getBrand());
    existing.setModel(dto.getModel());
    existing.setYear(dto.getYear());
    existing.setColor(dto.getColor());
    existing.setVehiclePhoto(dto.getVehiclePhoto());
    existing.setSeatCount(dto.getSeatCount());
    existing.setVehicleType(dto.getVehicleType());
    existing.setFuelType(dto.getFuelType());

    return vehicleRepository.save(existing);
}




//////////////////ESTADOS//////////////

@Transactional
public Vehicle updateVehicleDriver(UUID vehicleId, VehicleDriverUpdateDto dto) {
    log.info("Actualizando conductor del vehículo {}", vehicleId);

    // Buscar vehículo
    Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new CustomException(404, "Vehículo no encontrado",
                    "No existe un vehículo con ID " + vehicleId));

    // Buscar conductor
    Driver newDriver = driverRepository.findById(dto.getDriverId())
            .orElseThrow(() -> new CustomException(404, "Conductor no encontrado",
                    "No existe un conductor con ID " + dto.getDriverId()));

    // Actualizar el conductor
    vehicle.setDriver(newDriver);

    return vehicleRepository.save(vehicle);
}







 /* Actualizar estado de vehiculo*/
@Transactional
public Vehicle updateVehicleStatus(UUID vehicleId, String vehicleStatus) {
    Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new CustomException(404, "Vehículo no encontrado",
                    "No se encontró un vehículo con ID: " + vehicleId));

    vehicle.setVehicleStatus(vehicleStatus);
    return vehicleRepository.save(vehicle);
}


        /* borrado logico de Vehiculo*/
   
   @Transactional
    public void deactivateVehicle(String vehicleId) {
        UUID uuid = UUID.fromString(vehicleId);
        Vehicle vehicle = vehicleRepository.findById(uuid)
            .orElseThrow(() ->
                new CustomException(404,
                    "Vehículo no encontrado",
                    "No existe vehículo con ID: " + vehicleId)
            );

        // Actualiza la columna STATUS
        vehicle.setStatus("INACTIVE");
        // No es necesario vehicleRepository.save(...) gracias a @Transactional

        log.info("Vehículo con ID {} ha sido desactivado.", vehicleId);
    }


        /* Restauracion de Vehiculo*/
    @Transactional
    public void activateVehicle(String vehicleId) {
        UUID uuid = UUID.fromString(vehicleId);
        Vehicle vehicle = vehicleRepository.findById(uuid)
            .orElseThrow(() ->
                new CustomException(404,
                    "Vehículo no encontrado",
                    "No existe vehículo con ID: " + vehicleId)
            );

        // Actualiza la columna STATUS a "ACTIVE"
        vehicle.setStatus("ACTIVE");

        log.info("Vehículo con ID {} ha sido activado.", vehicleId);
    }



}
