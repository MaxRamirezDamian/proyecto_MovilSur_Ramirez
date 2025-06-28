package pe.edu.vallegrande.movilsurbackend.domain.model.transacctional;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.movilsurbackend.domain.enums.Status;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "SHIPPMENT_DETAIL")
public class ShippmentDetail {

    @Id
    @Column(name = "SHIP_DETAIL_ID")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID shipmentDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "SHIPMENT_ID")
    private Shipment shipment;

    @Column(name = "PACKAGE_DESCRIPTION")
    private String descripcion;

    @Column(name = "DESTINATION_ADDRESS")
    private String destino;

    @Column(name = "DIMENSIONS")
    private String dimensiones;

    @Column(name = "RECIPIENT_NAME")
    private String receptorNombre;

    @Column(name = "RECIPIENT_PHONE")
    private String receptorTelefono;

    @Column(name = "RECIPIENT_DOCUMENT_NUMBER")
    private String receptorDocumento;

    @Column(name = "QUANTITY")
    private Integer cantidad;

    @Column(name = "DELIVERY_STATUS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String estado;

    @Column(name = "COST_PACKAGE")
    private Double costo;


}
