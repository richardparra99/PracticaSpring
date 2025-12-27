package Backend.incripciones.dtos;

import java.time.LocalDateTime;

public class InscripcionResponse {
    private Long id;
    private Long eventoId;
    private String eventoNombre;
    private LocalDateTime fechaHoraEvento;
    private String qrToken;
    private Boolean ingresado;

    public InscripcionResponse(Long id, Long eventoId, String eventoNombre, LocalDateTime fechaHoraEvento, String qrToken, Boolean ingresado) {
        this.id = id;
        this.eventoId = eventoId;
        this.eventoNombre = eventoNombre;
        this.fechaHoraEvento = fechaHoraEvento;
        this.qrToken = qrToken;
        this.ingresado = ingresado;
    }

    public Long getId() {
        return id;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public String getEventoNombre() {
        return eventoNombre;
    }

    public LocalDateTime getFechaHoraEvento() {
        return fechaHoraEvento;
    }

    public String getQrToken() {
        return qrToken;
    }

    public Boolean getIngresado() {
        return ingresado;
    }
}
