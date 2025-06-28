package pe.edu.vallegrande.movilsurbackend.domain.model.transacctional;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "SHIPMENTS")
public class Shipment {

    @Id
    @Column(name = "SHIPMENT_ID")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID shipmentId;

    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "DRIVER_ID")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID driverId;

    @Column(name = "VEHICLE_ID")
    private UUID vehicleId;

    @Column(name = "PICKUP_ADDRESS")
    private String pickupAddress;

    @Column(name = "DESCRIPTION_ADDRESS")
    private String descriptionAddress;

    @Column(name = "DELIVERY_DATE")
    private LocalDate deliveryDate;

    @Column(name = "HOUR_SHIPMENTS")
    private LocalTime hourShipments;

    @Column(name = "TOTAL_COST")
    private Double totalCost;

    @Column(name = "STATUS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;

    @Column(name = "CREATED_AT")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShippmentDetail> details = new ArrayList<>();

    public void addDetail(ShippmentDetail detail) {
        details.add(detail);
        detail.setShipment(this);
        sumatotal();
    }

    public void sumatotal() {
        this.totalCost = details.stream()
                .mapToDouble(d -> d.getCantidad() * d.getCosto())
                .sum();
    }

}
