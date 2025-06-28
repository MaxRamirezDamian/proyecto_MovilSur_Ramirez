package pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentDetailDto {

    private String descripcion;

    private String destino;

    private String dimensiones;

    private String receptorNombre;

    private String receptorTelefono;

    private String receptorDocumento;

    private Integer cantidad;

    private Double costo;
}
