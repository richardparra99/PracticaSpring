package Backend.incripciones.service;

import Backend.incripciones.dtos.RechazoRequest;
import Backend.incripciones.models.Inscripcion;
import Backend.incripciones.repository.InscripcionRepository;
import Backend.security.CustomUserDetails;
import Backend.usuario.model.Usuario;
import Backend.usuario.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RevisionPagoService {
    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;

    public RevisionPagoService(InscripcionRepository inscripcionRepository, UsuarioRepository usuarioRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void aprobar(Long inscripcionId) {
        Usuario organizador = getUsuarioLogueado();

        Inscripcion ins = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        // seguridad: que el evento sea del organizador
        if (!ins.getEvento().getOrganizador().getId().equals(organizador.getId())) {
            throw new RuntimeException("No puedes revisar pagos de eventos que no son tuyos");
        }

        if (!"PENDIENTE".equals(ins.getEstadoPago())) {
            throw new RuntimeException("La inscripción no está pendiente");
        }

        if (ins.getComprobantePath() == null) {
            throw new RuntimeException("No hay comprobante subido");
        }

        ins.setEstadoPago("APROBADO");
        ins.setFechaRevision(LocalDateTime.now());
        ins.setMotivoRechazo(null);

        inscripcionRepository.save(ins);
    }

    public void rechazar(Long inscripcionId, RechazoRequest req) {
        Usuario organizador = getUsuarioLogueado();

        Inscripcion ins = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        if (!ins.getEvento().getOrganizador().getId().equals(organizador.getId())) {
            throw new RuntimeException("No puedes revisar pagos de eventos que no son tuyos");
        }

        if (!"PENDIENTE".equals(ins.getEstadoPago())) {
            throw new RuntimeException("La inscripción no está pendiente");
        }

        ins.setEstadoPago("RECHAZADO");
        ins.setFechaRevision(LocalDateTime.now());
        ins.setMotivoRechazo(req.getMotivo());

        inscripcionRepository.save(ins);
    }

    private Usuario getUsuarioLogueado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails cud) {
            Long id = cud.getUsuario().getId();
            return usuarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));
        }
        throw new RuntimeException("No autenticado");
    }
}
