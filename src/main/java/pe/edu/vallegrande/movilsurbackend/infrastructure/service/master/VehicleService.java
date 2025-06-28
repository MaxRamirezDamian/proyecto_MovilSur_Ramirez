package pe.edu.vallegrande.movilsurbackend.infrastructure.service.master;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Vehicle;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.VehicleCreateDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.exception.CustomException;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master.VehicleRepository;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    

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




@Transactional
public Vehicle createVehicle(VehicleCreateDto dto) {
    log.info("Creando vehículo sin conductor: {}", dto);

    Vehicle vehicle = Vehicle.builder()
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






}
