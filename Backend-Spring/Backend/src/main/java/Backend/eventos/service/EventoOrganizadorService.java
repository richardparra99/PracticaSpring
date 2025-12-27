package Backend.eventos.service;

import Backend.eventos.dtos.EventoCreateRequest;
import Backend.eventos.dtos.EventoOrganizadorResponse;
import Backend.eventos.dtos.EventoUpdateRequest;
import Backend.eventos.models.Eventos;
import Backend.eventos.repository.EventoRepository;
import Backend.security.CustomUserDetails;
import Backend.usuario.model.Usuario;
import Backend.usuario.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoOrganizadorService {
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public EventoOrganizadorService(EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public EventoOrganizadorResponse crear(EventoCreateRequest req) {
        Usuario organizador = getUsuarioLogueado();

        Eventos e = new Eventos();
        e.setNombre(req.getTitulo());
        e.setDescripcion(req.getDescripcion());
        e.setFechaHora(req.getFechaHora());
        e.setUbicacion(req.getUbicacion());
        e.setLatitud(req.getLatitud());
        e.setLongitud(req.getLongitud());
        e.setCapacidad(req.getCapacidadMax());
        e.setPrecio(req.getPrecio());
        e.setAficheUrl(req.getAficheUrl());
        e.setActivo(true);
        e.setOrganizador(organizador);

        e = eventoRepository.save(e);
        return toResponse(e);
    }

    public EventoOrganizadorResponse actualizar(Long id, EventoUpdateRequest req) {
        Usuario organizador = getUsuarioLogueado();

        Eventos e = eventoRepository.findByIdAndOrganizador_Id(id, organizador.getId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado o no te pertenece"));

        e.setNombre(req.getTitulo());
        e.setDescripcion(req.getDescripcion());
        e.setFechaHora(req.getFechaHora());
        e.setUbicacion(req.getUbicacion());
        e.setLatitud(req.getLatitud());
        e.setLongitud(req.getLongitud());
        e.setCapacidad(req.getCapacidadMax());
        e.setPrecio(req.getPrecio());
        e.setAficheUrl(req.getAficheUrl());

        e = eventoRepository.save(e);
        return toResponse(e);
    }

    public void eliminar(Long id) {
        Usuario organizador = getUsuarioLogueado();

        Eventos e = eventoRepository.findByIdAndOrganizador_Id(id, organizador.getId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado o no te pertenece"));

        e.setActivo(false);
        eventoRepository.save(e);
    }

    public List<EventoOrganizadorResponse> listarMisEventos() {
        Usuario organizador = getUsuarioLogueado();

        return eventoRepository.findByOrganizador_IdAndActivoTrueOrderByFechaHoraDesc(organizador.getId())
                .stream()
                .map(this::toResponse)
                .toList();
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

    private EventoOrganizadorResponse toResponse(Eventos e) {
        return new EventoOrganizadorResponse(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getFechaHora(),
                e.getUbicacion(),
                e.getLatitud(),
                e.getLongitud(),
                e.getCapacidad(),
                e.getPrecio(),
                e.getAficheUrl(),
                e.getActivo()
        );
    }
}
