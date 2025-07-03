package pe.edu.vallegrande.movilsurbackend.infrastructure.rest.master;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.Driver.Driver_User_Dto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.service.master.DriverService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/movil-sur")
public class DriveRest {

    private final DriverService driverService;

    @GetMapping("/drivers/listAll")
    public List<Driver_User_Dto> listAll() {
        return driverService.listAll();
    }

    @GetMapping("/drivers/filterByStatus/{status}")
    public List<Driver_User_Dto> filterByStatus(@PathVariable String status) {
        return driverService.filterByStatus(status.toUpperCase());
    }

    @GetMapping("/drivers/filterGeneral/{filter}")
    public List<Driver_User_Dto> filterGeneral(@PathVariable String filter) {
        return driverService.filterGeneral(filter.toUpperCase());
    }

    /* Este es un metodo para crear */
    @PostMapping("/drivers/create")
    public Driver_User_Dto create(@RequestBody Driver_User_Dto dto) {
        return driverService.create(dto);
    }

    @PutMapping("/drivers/update/{id}")
    public Driver_User_Dto update(@RequestBody Driver_User_Dto dto, @PathVariable UUID id) {
        return driverService.update(dto, id);
    }

    @PatchMapping("/drivers/replace/{id}")
    public Driver_User_Dto replace(@RequestBody Driver_User_Dto dto, @PathVariable UUID id) {
        return driverService.replace(dto, id);
    }

    @DeleteMapping("/drivers/softDelete/{id}")
    public Driver_User_Dto softDelete(@PathVariable UUID id) {
        return driverService.softDelete(id);
    }

    @PatchMapping("/drivers/restore/{id}")
    public Driver_User_Dto restore(@PathVariable UUID id) {
        return driverService.softRestore(id);
    }

    @GetMapping("/drivers/getById/{id}")
    public Driver_User_Dto getById(@PathVariable UUID id) {
        return driverService.getById(id);
    }

    @GetMapping("/drivers/filterByLicense/{type}/{category}")
    public List<Driver_User_Dto> filterByLicense(@PathVariable String type, @PathVariable String category) {
        return driverService.filterByLicense(type.toUpperCase(), category.toUpperCase());
    }

    @GetMapping("/drivers/filterByAvailability/{availability}")
    public List<Driver_User_Dto> filterByAvailability(@PathVariable String availability) {
        return driverService.filterByAvailability(availability.toUpperCase());
    }




}
