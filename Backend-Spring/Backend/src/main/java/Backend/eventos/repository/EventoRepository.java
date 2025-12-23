package Backend.eventos.repository;

import Backend.eventos.models.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Eventos, Long> {

    List<Eventos> findByActivoTrueAndFechaHoraGreaterThanEqualOrderByFechaHoraAsc(LocalDateTime now);
}
