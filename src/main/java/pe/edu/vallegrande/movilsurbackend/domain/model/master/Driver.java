package pe.edu.vallegrande.movilsurbackend.domain.model.master;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DRIVERS")
public class Driver {

    @Id
    @GeneratedValue
    @Column(name = "DRIVER_ID")
    private UUID driverId;

    @Column(name = "LICENSE_TYPE")
    private String licenseType;

    @Column(name = "LICENSE_CATEGORY")
    private String licenseCategory;

    @Column(name = "LICENSE_NUMBER")
    private String licenseNumber;

    @Column(name = "LICENSE_EXPIRATION")
    private LocalDate licenseExpiration;

    @Column(name = "EXPERIENCE_YEARS")
    private Integer experienceYears;

    @Column(name = "AVAILABLE")
    private String available;

    @Column(name = "LICENSE_CLASS")
    private String licenseClass;

    @Column(name = "STATUS")
    private String status;

    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;
}
