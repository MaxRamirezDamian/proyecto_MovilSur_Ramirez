package pe.edu.vallegrande.movilsurbackend.infrastructure.service.transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.Trip;
import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.TripDetail;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional.TripDetailDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional.TripDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.transacctional.TripDetailRepository;
import pe.edu.vallegrande.movilsurbackend.infrastructure.repository.transacctional.TripRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripService {

    private final TripRepository tripRepository;
    private final TripDetailRepository tripDetailRepository;

    @Transactional
    public Trip crearViaje(TripDto tripDto) {
        if (tripDto.getUserId() == null || tripDto.getVehicleId() == null) {
            throw new IllegalArgumentException("User ID y Vehicle ID son obligatorios");
        }

        Trip trip = Trip.builder()
                .userId(tripDto.getUserId())
                .driverId(tripDto.getDriverId())
                .vehicleId(tripDto.getVehicleId())
                .originAddress(tripDto.getOriginAddress())
                .destinationAddress(tripDto.getDestinationAddress())
                .seatsUsed(tripDto.getSeatsUsed())
                .paymentMethodId(tripDto.getPaymentMethodId())
                .tripStatus("EN_CURSO")
                .createdAt(LocalDateTime.now())
                .build();

        for (TripDetailDto detailDto : tripDto.getDetails()) {
            TripDetail detail = TripDetail.builder()
                    .description(detailDto.getDescription())
                    .quantity(detailDto.getQuantity())
                    .isFragile(detailDto.getIsFragile())
                    .build();
        }

        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateViaje(UUID id, TripDto tripDto) {
        Trip trip = tripRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Viaje no encontrado con ID: " + id));

        trip.setUserId(tripDto.getUserId());
        trip.setDriverId(tripDto.getDriverId());
        trip.setVehicleId(tripDto.getVehicleId());
        trip.setOriginAddress(tripDto.getOriginAddress());
        trip.setDestinationAddress(tripDto.getDestinationAddress());
        trip.setSeatsUsed(tripDto.getSeatsUsed());
        trip.setPaymentMethodId(tripDto.getPaymentMethodId());
        trip.setUpdatedAt(LocalDateTime.now());

        if (tripDto.getDetails() != null) {
            trip.getDetails().clear();
            for (TripDetailDto detailDto : tripDto.getDetails()) {
                TripDetail detail = TripDetail.builder()
                        .description(detailDto.getDescription())
                        .quantity(detailDto.getQuantity())
                        .isFragile(detailDto.getIsFragile())
                        .build();
            }
        }

        return tripRepository.save(trip);
    }

    public List<Trip> listarViajes() {
        return tripRepository.findAll();
    }

    public Trip obtenerViajePorId(UUID id) {
        Objects.requireNonNull(id);
        return tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viaje no encontrado con ID: " + id));
    }

    public List<Trip> listarViajesPorEstado(String estado) {
        if (estado.equals("EN_CURSO")) {
            return tripRepository.findByTripStatusOrderByCreatedAtDesc(estado);
        } else {
            return tripRepository.findByTripStatusOrderByUpdatedAtDesc(estado);
        }
    }

    @Transactional
    public Trip finalizarViaje(UUID id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viaje no encontrado"));

        trip.setTripStatus("COMPLETADO");
        trip.setEndedAt(LocalDateTime.now());
        trip.setUpdatedAt(LocalDateTime.now());

        return tripRepository.save(trip);
    }
}
