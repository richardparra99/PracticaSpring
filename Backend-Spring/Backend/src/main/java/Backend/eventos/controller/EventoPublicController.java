package Backend.eventos.controller;

import Backend.eventos.dtos.EventoCardResponse;
import Backend.eventos.dtos.EventoDetalleResponse;
import Backend.eventos.service.EventoPublicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/eventos")
public class EventoPublicController {
    private final EventoPublicService eventoPublicService;

    public EventoPublicController(EventoPublicService eventoPublicService) {
        this.eventoPublicService = eventoPublicService;
    }

    @GetMapping
    public ResponseEntity<List<EventoCardResponse>> listar() {
        return ResponseEntity.ok(eventoPublicService.listarProximos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDetalleResponse> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(eventoPublicService.obtenerDetalle(id));
    }
}
