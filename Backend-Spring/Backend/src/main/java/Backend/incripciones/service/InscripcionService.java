package Backend.incripciones.service;

import Backend.eventos.models.Eventos;
import Backend.eventos.repository.EventoRepository;
import Backend.incripciones.dtos.InscripcionResponse;
import Backend.incripciones.models.Inscripcion;
import Backend.incripciones.repository.InscripcionRepository;
import Backend.security.CustomUserDetails;
import Backend.usuario.model.Usuario;
import Backend.usuario.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InscripcionService {
    private final InscripcionRepository inscripcionRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository, EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public InscripcionResponse inscribirse(Long eventoId) {
        Usuario participante = getUsuarioLogueado();

        Eventos evento = eventoRepository.findById(eventoId)
                .filter(e -> Boolean.TRUE.equals(e.getActivo()))
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        if(evento.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede inscribir a un evento pasado");
        }

        // Evitar duplicado
        boolean yaInscrito = inscripcionRepository
                .findByEvento_IdAndParticipante_Id(eventoId, participante.getId())
                .isPresent();
        if (yaInscrito) {
            throw new RuntimeException("Ya estás inscrito en este evento");
        }

        // Validar cupo (por ahora: inscritos contados <= capacidad)
        long inscritos = inscripcionRepository.countByEvento_Id(eventoId);
        if (inscritos >= evento.getCapacidad()) {
            throw new RuntimeException("Cupo lleno");
        }

        Inscripcion ins = new Inscripcion();
        ins.setEvento(evento);
        ins.setParticipante(participante);
        ins.setQrToken(generarQrToken());
        ins.setFechaCreacion(LocalDateTime.now());
        ins.setIngresado(false);

        if (evento.getPrecio() != null && evento.getPrecio().compareTo(java.math.BigDecimal.ZERO) > 0) {
            ins.setEstadoPago("PENDIENTE");
        } else {
            ins.setEstadoPago("NO_REQUIERE");
        }

        ins = inscripcionRepository.save(ins);

        return toResponse(ins);
    }

    public List<InscripcionResponse> misInscripciones() {
        Usuario participante = getUsuarioLogueado();

        return inscripcionRepository.findByParticipante_IdOrderByFechaCreacionDesc(participante.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void cancelar(Long inscripcionId) {
        Usuario participante = getUsuarioLogueado();

        Inscripcion ins = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        if (!ins.getParticipante().getId().equals(participante.getId())) {
            throw new RuntimeException("No te pertenece esta inscripción");
        }

        // No cancelar si evento ya pasó
        if (ins.getEvento().getFechaHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No puedes cancelar una inscripción de un evento pasado");
        }

        // No cancelar si ya ingresó
        if (Boolean.TRUE.equals(ins.getIngresado())) {
            throw new RuntimeException("No puedes cancelar, ya registraste ingreso");
        }

        inscripcionRepository.delete(ins);
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

    private InscripcionResponse toResponse(Inscripcion ins) {
        Eventos e = ins.getEvento();
        return new InscripcionResponse(
                ins.getId(),
                e.getId(),
                e.getNombre(),
                e.getFechaHora(),
                ins.getQrToken(),
                ins.getIngresado()
        );
    }

    private String generarQrToken() {
        // token simple y único (luego podemos hacerlo más “seguro” si quieres)
        return UUID.randomUUID().toString().replace("-", "");
    }
}
