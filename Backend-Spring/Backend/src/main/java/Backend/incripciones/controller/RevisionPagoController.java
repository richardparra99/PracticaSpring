package Backend.incripciones.controller;

import Backend.incripciones.dtos.RechazoRequest;
import Backend.incripciones.service.RevisionPagoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizador/pagos")
public class RevisionPagoController {
    private final RevisionPagoService revisionPagoService;

    public RevisionPagoController(RevisionPagoService revisionPagoService) {
        this.revisionPagoService = revisionPagoService;
    }

    @PostMapping("/inscripciones/{id}/aprobar")
    public ResponseEntity<Void> aprobar(@PathVariable Long id) {
        revisionPagoService.aprobar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/inscripciones/{id}/rechazar")
    public ResponseEntity<Void> rechazar(@PathVariable Long id, @Valid @RequestBody RechazoRequest req) {
        revisionPagoService.rechazar(id, req);
        return ResponseEntity.noContent().build();
    }
}
