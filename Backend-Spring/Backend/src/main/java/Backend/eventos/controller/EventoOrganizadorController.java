package Backend.eventos.controller;

import Backend.eventos.dtos.EventoCreateRequest;
import Backend.eventos.dtos.EventoOrganizadorResponse;
import Backend.eventos.dtos.EventoUpdateRequest;
import Backend.eventos.service.EventoOrganizadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos/organizador")
public class EventoOrganizadorController {
    private final EventoOrganizadorService eventoOrganizadorService;

    public EventoOrganizadorController(EventoOrganizadorService eventoOrganizadorService) {
        this.eventoOrganizadorService = eventoOrganizadorService;
    }

    @PostMapping
    public ResponseEntity<EventoOrganizadorResponse> crear(@Valid @RequestBody EventoCreateRequest req) {
        return ResponseEntity.ok(eventoOrganizadorService.crear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoOrganizadorResponse> actualizar(@PathVariable Long id, @Valid @RequestBody EventoUpdateRequest req) {
        return ResponseEntity.ok(eventoOrganizadorService.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventoOrganizadorResponse> eliminar(@PathVariable Long id) {
        eventoOrganizadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EventoOrganizadorResponse>> listarMisEventos() {
        return ResponseEntity.ok(eventoOrganizadorService.listarMisEventos());
    }
}
