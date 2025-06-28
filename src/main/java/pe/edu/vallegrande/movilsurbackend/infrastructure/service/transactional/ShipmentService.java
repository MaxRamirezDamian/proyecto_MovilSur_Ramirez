package pe.edu.vallegrande.movilsurbackend.infrastructure.service.transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.vallegrande.movilsurbackend.domain.enums.Status;
import pe.edu.vallegrande.movilsurbackend.domain.model.master.Vehicle;
import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.Shipment;
import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.ShippmentDetail;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional.ShipmentDetailDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional.ShipmentDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.master.VehicleRepository;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.transacctional.ShipmentDetailRepository;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.transacctional.ShipmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j

public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentDetailRepository detalleRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public Shipment crearEnvio(ShipmentDto shipmentDto) {
        if (shipmentDto.getUserId() == null) {
            throw new IllegalArgumentException("User ID es obligatorio");
        }
        if (shipmentDto.getDetails() == null || shipmentDto.getDetails().isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un detalle de envío");
        }

        UUID driverId = shipmentDto.getDriverId();
        if (driverId == null && shipmentDto.getVehicleId() != null) {
            Vehicle vehicle = vehicleRepository.findById(shipmentDto.getVehicleId())
                    .orElseThrow(
                            () -> new RuntimeException("Vehículo no encontrado con ID: " + shipmentDto.getVehicleId()));
            driverId = vehicle.getDriver().getDriverId();
        }

        Shipment shipment = Shipment.builder()
                .userId(shipmentDto.getUserId())
                .driverId(driverId) // Usar el driverId correcto
                .vehicleId(shipmentDto.getVehicleId())
                .pickupAddress(shipmentDto.getPickupAddress())
                .descriptionAddress(shipmentDto.getDescriptionAddress())
                .deliveryDate(shipmentDto.getDeliveryDate())
                .hourShipments(shipmentDto.getHourShipments())
                .status(Status.PENDIENTE.name())
                .createdAt(LocalDateTime.now())
                .build();

        for (ShipmentDetailDto detallesDto : shipmentDto.getDetails()) {
            ShippmentDetail detalle = ShippmentDetail.builder()
                    .descripcion(detallesDto.getDescripcion())
                    .destino(detallesDto.getDestino())
                    .dimensiones(detallesDto.getDimensiones())
                    .receptorNombre(detallesDto.getReceptorNombre())
                    .receptorTelefono(detallesDto.getReceptorTelefono())
                    .receptorDocumento(detallesDto.getReceptorDocumento())
                    .cantidad(detallesDto.getCantidad())
                    .estado(Status.PENDIENTE.name())
                    .costo(detallesDto.getCosto())
                    .build();

            shipment.addDetail(detalle);
        }
        // shipment.sumatotal(); // Si tienes este método y lo necesitas
        return shipmentRepository.save(shipment);
    }

    @Transactional
    public Shipment updateEnvio(UUID shipmentId, ShipmentDto shipmentDto) {
        Shipment shipmentExistente = shipmentRepository.findByIdWithDetails(shipmentId)
                .orElseThrow(() -> new EntityNotFoundException("Envío no encontrado con ID: " + shipmentId));

        if (shipmentDto.getUserId() == null) {
            throw new IllegalArgumentException("User ID es obligatorio");
        }

        // Obtener driverId si es necesario
        UUID driverId = shipmentDto.getDriverId();
        if (driverId == null && shipmentDto.getVehicleId() != null) {
            Vehicle vehicle = vehicleRepository.findById(shipmentDto.getVehicleId())
                    .orElseThrow(
                            () -> new RuntimeException("Vehículo no encontrado con ID: " + shipmentDto.getVehicleId()));
            driverId = vehicle.getDriver().getDriverId();
        }

        shipmentExistente.setUserId(shipmentDto.getUserId());
        shipmentExistente.setDriverId(driverId);
        shipmentExistente.setVehicleId(shipmentDto.getVehicleId());
        shipmentExistente.setPickupAddress(shipmentDto.getPickupAddress());
        shipmentExistente.setDescriptionAddress(shipmentDto.getDescriptionAddress());
        shipmentExistente.setDeliveryDate(shipmentDto.getDeliveryDate());
        shipmentExistente.setHourShipments(shipmentDto.getHourShipments());

        if (shipmentDto.getDetails() != null) {
            shipmentExistente.getDetails().clear();

            for (ShipmentDetailDto detalleDto : shipmentDto.getDetails()) {
                ShippmentDetail detalle = ShippmentDetail.builder()
                        .descripcion(detalleDto.getDescripcion())
                        .destino(detalleDto.getDestino())
                        .dimensiones(detalleDto.getDimensiones())
                        .receptorNombre(detalleDto.getReceptorNombre())
                        .receptorTelefono(detalleDto.getReceptorTelefono())
                        .receptorDocumento(detalleDto.getReceptorDocumento())
                        .cantidad(detalleDto.getCantidad())
                        .estado(Status.PENDIENTE.name())
                        .costo(detalleDto.getCosto())
                        .build();
                shipmentExistente.addDetail(detalle);
            }
        }
        shipmentExistente.sumatotal();
        return shipmentRepository.save(shipmentExistente);
    }

    public List<Shipment> listarEnvio() {
        return shipmentRepository.findAll();
    }

    public Shipment listarEnvioId(UUID shipmentId) {
        Objects.requireNonNull(shipmentId, "El id no pode ser nulo.");
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("No se encontro el Id del envio: " + shipmentId));
    }

    public List<Shipment> listarEnvioEstado(String estado) {
        Objects.requireNonNull(estado);
        List<String> estadosValidos = List.of("PENDIENTE", "COMPLETADO", "CANCELADO", "ENTREGA_PARCIAL");

        if (!estadosValidos.contains(estado)) {
            throw new EntityNotFoundException("Estado no válido: " + estado);
        }

        // Lógica de ordenamiento según el estado
        if (estado.equals("PENDIENTE")) {
            return shipmentRepository.findByEstadoOrderByCreatedAt(estado);
        } else {
            return shipmentRepository.findByEstadoOrderByUpdatedAt(estado);
        }
    }

    @Transactional
    public ShippmentDetail paqueteEnviado(UUID id) {
        // Obtener el detalle del paquete
        ShippmentDetail detail = detalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Marcar el paquete como entregado
        detail.setEstado(Status.ENTREGADO.name());
        ShippmentDetail actualizado = detalleRepository.save(detail);

        // Obtener el envío asociado
        Shipment envio = detail.getShipment();

        // Contar paquetes entregados vs totales
        long totalPaquetes = envio.getDetails().size();
        long entregados = envio.getDetails().stream()
                .filter(d -> Status.ENTREGADO.name().equals(d.getEstado()))
                .count();

        // Actualizar estado del envío según las condiciones
        if (entregados == totalPaquetes) {
            // Todos los paquetes entregados
            envio.setStatus(Status.COMPLETADO.name());
        } else if (entregados > 0) {
            // Al menos un paquete entregado pero no todos
            envio.setStatus(Status.ENTREGA_PARCIAL.name());
        }

        // Actualizar fecha de modificación
        envio.setUpdatedAt(LocalDateTime.now());
        shipmentRepository.save(envio);
        return actualizado;
    }

    @Transactional
    public Shipment enviarTodo(UUID id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envio No encontado"));

        for (ShippmentDetail detalle : shipment.getDetails()) {
            detalle.setEstado(Status.ENTREGADO.name());
        }
        shipment.setStatus(Status.COMPLETADO.name());
        shipment.setUpdatedAt(LocalDateTime.now());
        return shipmentRepository.save(shipment);
    }

}
