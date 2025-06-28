package pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Vehicle;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository <Vehicle, UUID>{
}
