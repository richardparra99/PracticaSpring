package Backend.incripciones.controller;

import Backend.incripciones.dtos.InscripcionResponse;
import Backend.incripciones.service.InscripcionComprobanteService;
import Backend.incripciones.service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participante")
public class InscripcionController {
    private final InscripcionService inscripcionService;
    private final InscripcionComprobanteService comprobanteService;

    public InscripcionController(InscripcionService inscripcionService, InscripcionComprobanteService comprobanteService) {
        this.inscripcionService = inscripcionService;
        this.comprobanteService = comprobanteService;
    }

    @PostMapping("/eventos/{eventoId}/inscribirse")
    public ResponseEntity<InscripcionResponse> inscribirse(@PathVariable Long eventoId) {
        return ResponseEntity.ok(inscripcionService.inscribirse(eventoId));
    }

    @GetMapping("/inscripciones")
    public ResponseEntity<List<InscripcionResponse>> misInscripciones() {
        return ResponseEntity.ok(inscripcionService.misInscripciones());
    }

    @DeleteMapping("/inscripciones/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        inscripcionService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/inscripciones/{id}/comprobante", consumes = "multipart/form-data")
    public ResponseEntity<Void> subirComprobante(
            @PathVariable Long id,
            @RequestPart("file") org.springframework.web.multipart.MultipartFile file
    ) {
        comprobanteService.subirComprobante(id, file);
        return ResponseEntity.noContent().build();
    }
}
