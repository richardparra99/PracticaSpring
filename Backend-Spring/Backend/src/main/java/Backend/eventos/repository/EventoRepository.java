package Backend.eventos.repository;

import Backend.eventos.models.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Eventos, Long> {

    List<Eventos> findByActivoTrueAndFechaHoraGreaterThanEqualOrderByFechaHoraAsc(LocalDateTime now);
    // Organizador
    List<Eventos> findByOrganizador_IdAndActivoTrueOrderByFechaHoraDesc(Long organizadorId);
    Optional<Eventos> findByIdAndOrganizador_Id(Long id, Long organizadorId);
}
