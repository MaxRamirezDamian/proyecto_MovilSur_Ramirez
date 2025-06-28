package pe.edu.vallegrande.movilsurbackend.infrastructure.complement;


import pe.edu.vallegrande.movilsurbackend.domain.model.master.Vehicle;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Driver;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.master.VehicleDto;

public class VehicleMapperManual {
    public static VehicleDto toDto(Vehicle v) {
        return VehicleDto.builder()
            .vehicleId(v.getVehicleId())
            .driverId(v.getDriver().getDriverId())
            .licensePlate(v.getLicensePlate())
            .brand(v.getBrand())
            .model(v.getModel())
            .year(v.getYear())
            .color(v.getColor())
            .vehiclePhoto(v.getVehiclePhoto())
            .seatCount(v.getSeatCount())
            .vehicleStatus(v.getVehicleStatus())
            .registrationDate(v.getRegistrationDate())
            .status(v.getStatus())
            .vehicleType(v.getVehicleType())
            .fuelType(v.getFuelType())
            .build();
    }

    public static Vehicle toEntity(VehicleDto dto, Driver driver) {
        return Vehicle.builder()
            .driver(driver)
            .licensePlate(dto.getLicensePlate())
            .brand(dto.getBrand())
            .model(dto.getModel())
            .year(dto.getYear())
            .color(dto.getColor())
            .vehiclePhoto(dto.getVehiclePhoto())
            .seatCount(dto.getSeatCount())
            .vehicleStatus(dto.getVehicleStatus())
            .registrationDate(dto.getRegistrationDate())
            .status(dto.getStatus())
            .vehicleType(dto.getVehicleType())
            .fuelType(dto.getFuelType())
            .build();
    }
}

