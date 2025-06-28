package pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import java.util.Optional;

import pe.edu.vallegrande.movilsurbackend.domain.model.master.Driver;

import java.util.List;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

    List<Driver> findByLicenseTypeAndLicenseCategory(String type, String category);
    Optional<Driver> findById(UUID driverId);

    @Query("SELECT d FROM Driver d JOIN d.user u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(concat('%', :filtro, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(concat('%', :filtro, '%')) OR " +
            "LOWER(u.documentNumber) LIKE LOWER(concat('%', :filtro, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(concat('%', :filtro, '%')) OR " +
            "LOWER(d.licenseNumber) LIKE LOWER(concat('%', :filtro, '%')) OR " +
            "LOWER(d.licenseClass) LIKE LOWER(concat('%', :filtro, '%'))")
    List<Driver> filterGeneral(@Param("filtro") String filtro);


    @Query("SELECT d FROM Driver d JOIN d.user u WHERE d.status = :status ORDER BY u.createdAt DESC")
    List<Driver> findByStatus(@Param("status") String status);

    List<Driver> findByAvailable(String available);

}
