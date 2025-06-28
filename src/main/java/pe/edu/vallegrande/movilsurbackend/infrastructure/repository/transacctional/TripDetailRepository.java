package pe.edu.vallegrande.movilsurbackend.infrastructure.repository.transacctional;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.TripDetail;

@Repository
public interface TripDetailRepository extends JpaRepository<TripDetail, UUID> {
}