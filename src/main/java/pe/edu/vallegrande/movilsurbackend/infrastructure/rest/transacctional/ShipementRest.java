package pe.edu.vallegrande.movilsurbackend.infrastructure.rest.transacctional;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.Shipment;
import pe.edu.vallegrande.movilsurbackend.domain.model.transacctional.ShippmentDetail;
import pe.edu.vallegrande.movilsurbackend.infrastructure.dto.transactional.ShipmentDto;
import pe.edu.vallegrande.movilsurbackend.infrastructure.service.transactional.ShipmentService;

import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = {"*", })
@RequiredArgsConstructor
@RequestMapping("/api/v1/movil-sur")

public class ShipementRest {
    private final ShipmentService shipmentService;

    @PostMapping("/shipment/crear-envio")
    @ResponseStatus(HttpStatus.CREATED)
    public Shipment crearEnvio(@RequestBody @Valid ShipmentDto shipmentDto) {
        return shipmentService.crearEnvio(shipmentDto);
    }

    @PatchMapping("/shipment/update-envio/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Shipment updateEnvio(@PathVariable UUID id, @RequestBody @Valid ShipmentDto shipmentDto) {
        return shipmentService.updateEnvio(id, shipmentDto);
    }

    @GetMapping("/shipment/listar-envio")
    @ResponseStatus(HttpStatus.OK)
    public List<Shipment> listarEnvio() {
        return shipmentService.listarEnvio();
    }

    @GetMapping("/shipment/listar-envio/{estado}")
    @ResponseStatus(HttpStatus.OK)
    public List<Shipment> listarEnvioEstado(@PathVariable String estado) {
        return shipmentService.listarEnvioEstado(estado);
    }

    @GetMapping("/shipment/listar-envio-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Shipment listarEnvioID(@PathVariable UUID id) {
        return shipmentService.listarEnvioId(id);
    }

    @PatchMapping("/shipment/entregar-paquete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ShippmentDetail entregarPaquetes(@PathVariable UUID id) {
        return shipmentService.paqueteEnviado(id);
    }


    @PatchMapping("/shipment/entregar-todo-envio/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Shipment enviarTodosPaquetes(@PathVariable UUID id) {
        return shipmentService.enviarTodo(id);
    }


}
