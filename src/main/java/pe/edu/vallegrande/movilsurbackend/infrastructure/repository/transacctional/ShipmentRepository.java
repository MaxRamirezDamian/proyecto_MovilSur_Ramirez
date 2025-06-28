package pe.edu.vallegrande.movilsurbackend.infrastructure.repository.transacctional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.Shipment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {

    @Query("SELECT s FROM Shipment s LEFT JOIN FETCH s.details WHERE s.shipmentId = :id")
    Optional<Shipment> findByIdWithDetails(@Param("id") UUID id);


    @Query("SELECT s FROM Shipment s WHERE s.status = :estado ORDER BY s.createdAt DESC")
    List<Shipment> findByEstadoOrderByCreatedAt(@Param("estado") String estado);

    // Ordenar por fecha de actualización (para los demás)
    @Query("SELECT s FROM Shipment s WHERE s.status = :estado ORDER BY s.updatedAt DESC")
    List<Shipment> findByEstadoOrderByUpdatedAt(@Param("estado") String estado);
}
