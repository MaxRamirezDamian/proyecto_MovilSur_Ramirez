package pe.edu.vallegrande.movilsurbackend.infrastructure.rest.master;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Vehicle;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.VehicleCreateDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.service.master.VehicleService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/movil-sur")
public class VehicleRest {
    private final VehicleService vehicleService;

    @GetMapping("/vehicles/listar-vehiculos")
    public List<Vehicle> getall() {
        return vehicleService.getAll();
    }

/* Buscar vehiculo por su id*/
    @GetMapping("/vehicles/getVehicleById/{id}")
public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") UUID id) {
    Vehicle vehicle = vehicleService.getVehicleById(id);
    return ResponseEntity.ok(vehicle);
}


    
/* Buscar vehiculo por el id del conductor*/
@GetMapping("/vehicles/listByDriver/{id}")
    public ResponseEntity<List<Vehicle>> listVehiclesByDriver(@PathVariable("id") UUID id) {
        List<Vehicle> vehicles = vehicleService.listVehiclesByDriver(id);
        if (vehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehicles);
    }

/* Listar Vehiculos por su estado emocional digo que?*/
@GetMapping("/vehicles/listByStatus")
public ResponseEntity<List<Vehicle>> listVehiclesByStatus(@RequestParam String status) {
    List<Vehicle> vehicles = vehicleService.listVehiclesByStatus(status);
    if (vehicles.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(vehicles);
}

/* Listar Vehiculos por su estado de operacion*/
@GetMapping("/vehicles/listByVehicleStatus")
public ResponseEntity<List<Vehicle>> listByVehicleStatus(@RequestParam String vehicleStatus) {
    List<Vehicle> vehicles = vehicleService.listByVehicleStatus(vehicleStatus);
    
    if (vehicles.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    
    return ResponseEntity.ok(vehicles);
}


@PostMapping("/vehicles/create")
public ResponseEntity<Vehicle> create(@RequestBody VehicleCreateDto dto) {
    Vehicle created = vehicleService.createVehicle(dto);
    return ResponseEntity.ok(created);
}


}
