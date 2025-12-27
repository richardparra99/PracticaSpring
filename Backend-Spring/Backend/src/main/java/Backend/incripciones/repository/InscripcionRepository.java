package Backend.incripciones.repository;

import Backend.incripciones.models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    Optional<Inscripcion> findByEvento_IdAndParticipante_Id(Long eventoId, Long participanteId);

    List<Inscripcion> findByParticipante_IdOrderByFechaCreacionDesc(Long participanteId);

    long countByEvento_Id(Long eventoId);
}
