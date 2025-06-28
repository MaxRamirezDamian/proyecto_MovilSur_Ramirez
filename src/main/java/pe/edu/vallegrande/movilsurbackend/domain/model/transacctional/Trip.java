package pe.edu.vallegrande.movilsurbackend.domain.model.transacctional;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TRIP")
public class Trip {

    @Id
    @Column(name = "TRIP_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID tripId;

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Column(name = "DRIVER_ID")
    private UUID driverId;

    @Column(name = "VEHICLE_ID", nullable = false)
    private UUID vehicleId;

    @Column(name = "ORIGIN_ADDRESS", nullable = false)
    private String originAddress;

    @Column(name = "DESTINATION_ADDRESS", nullable = false)
    private String destinationAddress;

    @Column(name = "STARTED_AT")
    private LocalDateTime startedAt;

    @Column(name = "ENDED_AT")
    private LocalDateTime endedAt;

    @Column(name = "SEATS_USED", nullable = false)
    private Integer seatsUsed;

    @Column(name = "PAYMENT_METHOD_ID", nullable = false)
    private UUID paymentMethodId;

    @Column(name = "TOTAL_COST")
    private Double totalCost;

    @Column(name = "TRIP_STATUS")
    private String tripStatus;

    @Column(name = "CREATED_AT", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TripDetail> details = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addDetail(TripDetail detail) {
        details.add(detail);
        detail.setTrip(this);
    }


}

