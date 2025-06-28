package pe.edu.vallegrande.movilsurbackend.infrastructure.repository.transacctional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.Trip;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {

    @Query("SELECT t FROM Trip t LEFT JOIN FETCH t.details WHERE t.tripId = :id")
    Optional<Trip> findByIdWithDetails(@Param("id") UUID id);

    List<Trip> findByTripStatusOrderByCreatedAtDesc(String tripStatus);

    List<Trip> findByTripStatusOrderByUpdatedAtDesc(String tripStatus);

    List<Trip> findByUserId(UUID userId);

    List<Trip> findByVehicleId(UUID vehicleId);

    @Query("SELECT t FROM Trip t WHERE t.tripStatus = :status AND t.userId = :userId ORDER BY t.createdAt DESC")
    List<Trip> findByStatusAndUser(@Param("status") String status, @Param("userId") UUID userId);
}
