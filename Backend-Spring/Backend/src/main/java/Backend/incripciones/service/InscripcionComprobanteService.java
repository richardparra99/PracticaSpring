package Backend.incripciones.service;

import Backend.incripciones.models.Inscripcion;
import Backend.incripciones.repository.InscripcionRepository;
import Backend.security.CustomUserDetails;
import Backend.usuario.model.Usuario;
import Backend.usuario.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class InscripcionComprobanteService {
    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final FileStorageService fileStorageService;

    public InscripcionComprobanteService(InscripcionRepository inscripcionRepository, UsuarioRepository usuarioRepository, FileStorageService fileStorageService) {
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.fileStorageService = fileStorageService;
    }

    public void subirComprobante(Long inscripcionId, MultipartFile file) {
        Usuario participante = getUsuarioLogueado();

        Inscripcion ins = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        if (!ins.getParticipante().getId().equals(participante.getId())) {
            throw new RuntimeException("No te pertenece esta inscripción");
        }

        if (!"PENDIENTE".equals(ins.getEstadoPago())) {
            throw new RuntimeException("Esta inscripción no requiere comprobante o ya fue revisada");
        }

        var saved = fileStorageService.saveComprobante(file, inscripcionId);

        ins.setComprobantePath(saved.absolutePath());
        ins.setComprobanteOriginal(saved.originalName());
        ins.setFechaComprobante(LocalDateTime.now());

        // queda pendiente hasta que el organizador revise
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
