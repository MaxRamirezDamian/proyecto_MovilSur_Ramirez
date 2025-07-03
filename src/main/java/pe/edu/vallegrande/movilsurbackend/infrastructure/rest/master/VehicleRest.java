package pe.edu.vallegrande.movilsurbackend.infrastructure.rest.master;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Vehicle;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle.VehicleCreateDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle.VehicleDriverUpdateDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Vehicle.VehicleStatusDto;
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
    public List<Vehicle> getAll() {
        return vehicleService.getAll();
    }

    
    @GetMapping("/vehicles/getVehicleById/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") UUID id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/vehicles/listByDriver/{id}")
    public ResponseEntity<List<Vehicle>> listVehiclesByDriver(@PathVariable("id") UUID id) {
        List<Vehicle> vehicles = vehicleService.listVehiclesByDriver(id);
        if (vehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/vehicles/listByStatus")
    public ResponseEntity<List<Vehicle>> listVehiclesByStatus(@RequestParam String status) {
        List<Vehicle> vehicles = vehicleService.listVehiclesByStatus(status);
        if (vehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehicles);
    }

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

    @PutMapping("/vehicles/update/{id}")
public ResponseEntity<Vehicle> updateVehicle(
        @PathVariable("id") UUID vehicleId,
        @RequestBody @Valid VehicleCreateDto dto) {

    Vehicle updated = vehicleService.updateVehicle(vehicleId, dto);
    return ResponseEntity.ok(updated);
}


@PutMapping("/vehicles/{id}/update-driver")
public ResponseEntity<Vehicle> updateVehicleDriver(
        @PathVariable("id") UUID vehicleId,
        @RequestBody VehicleDriverUpdateDto dto) {

    Vehicle updated = vehicleService.updateVehicleDriver(vehicleId, dto);
    return ResponseEntity.ok(updated);
}







    /* Actualizar estado*/
    @PatchMapping("/vehicles/update-status/{id}")
public ResponseEntity<Vehicle> updateVehicleStatus(
        @PathVariable UUID id,
        @RequestBody @Valid VehicleStatusDto dto) {
    
    Vehicle updated = vehicleService.updateVehicleStatus(id, dto.getVehicleStatus());
    return ResponseEntity.ok(updated);
}

    /* Desactivar Vehiculo*/
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateVehicle(@PathVariable("id") String id) {
        vehicleService.deactivateVehicle(id);
        return ResponseEntity.ok("Vehículo desactivado correctamente.");
    }

    /* Activar Vehiculo*/
    @PatchMapping("/activate/{id}")
    public ResponseEntity<String> activateVehicle(@PathVariable("id") String id) {
        vehicleService.activateVehicle(id);
        return ResponseEntity.ok("Vehículo activado correctamente.");
    }


}
