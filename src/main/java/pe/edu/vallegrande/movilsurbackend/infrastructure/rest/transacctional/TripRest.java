package pe.edu.vallegrande.movilsurbackend.infrastructure.rest.transacctional;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.Trip;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional.TripDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.service.transactional.TripService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/movil-sur/trips")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TripRest {

    private final TripService tripService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trip crearViaje(@RequestBody @Valid TripDto tripDto) {
        return tripService.crearViaje(tripDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Trip actualizarViaje(@PathVariable UUID id, @RequestBody @Valid TripDto tripDto) {
        return tripService.updateViaje(id, tripDto);
    }

    @GetMapping("/listar-viajes")
    @ResponseStatus(HttpStatus.OK)
    public List<Trip> listarViajes() {
        return tripService.listarViajes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Trip obtenerViajePorId(@PathVariable UUID id) {
        return tripService.obtenerViajePorId(id);
    }

    @GetMapping("/estado/{estado}")
    @ResponseStatus(HttpStatus.OK)
    public List<Trip> listarPorEstado(@PathVariable String estado) {
        return tripService.listarViajesPorEstado(estado);
    }

    @PatchMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Trip finalizarViaje(@PathVariable UUID id) {
        return tripService.finalizarViaje(id);
    }
}
