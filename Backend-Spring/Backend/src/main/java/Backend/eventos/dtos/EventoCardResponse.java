package Backend.eventos.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventoCardResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String ubicacion;
    private BigDecimal precio;
    private String aficheUrl;

    public EventoCardResponse(Long id, String titulo, String descripcion, LocalDateTime fechaHora, String ubicacion, BigDecimal precio, String aficheUrl) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.aficheUrl = aficheUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getAficheUrl() {
        return aficheUrl;
    }
}
