package pe.edu.vallegrande.movilsurbackend.infrastructure.repository.transacctional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.ShippmentDetail;

import java.util.UUID;

@Repository
public interface ShipmentDetailRepository extends JpaRepository<ShippmentDetail, UUID> {
}
