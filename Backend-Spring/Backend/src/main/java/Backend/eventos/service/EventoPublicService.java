package Backend.eventos.service;


import Backend.eventos.dtos.EventoCardResponse;
import Backend.eventos.dtos.EventoDetalleResponse;
import Backend.eventos.models.Eventos;
import Backend.eventos.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoPublicService {
    private final EventoRepository eventoRepository;

    public EventoPublicService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<EventoCardResponse> listarProximos() {
        LocalDateTime now = LocalDateTime.now();
        return eventoRepository
                .findByActivoTrueAndFechaHoraGreaterThanEqualOrderByFechaHoraAsc(now)
                .stream()
                .map(this::toCard)
                .toList();
    }

    public EventoDetalleResponse obtenerDetalle(Long id) {
        Eventos e = eventoRepository.findById(id)
                .filter(ev -> Boolean.TRUE.equals(ev.getActivo()))
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        return new EventoDetalleResponse(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getFechaHora(),
                e.getUbicacion(),
                e.getLatitud(),
                e.getLongitud(),
                e.getCapacidad(),
                e.getPrecio(),
                e.getAficheUrl()
        );
    }

    private EventoCardResponse toCard(Eventos e) {
        String desc = e.getDescripcion() == null ? "" : e.getDescripcion().trim();
        String corta = desc.length() <= 140 ? desc : desc.substring(0, 140) + "...";

        return new EventoCardResponse(
                e.getId(),
                e.getNombre(),
                corta,
                e.getFechaHora(),
                e.getUbicacion(),
                e.getPrecio(),
                e.getAficheUrl()
        );
    }
}
