package Backend.eventos.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventoDetalleResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String ubicacion;
    private Double latitud;
    private Double longitud;
    private Integer capacidad;
    private BigDecimal precio;
    private String aficheUrl;

    public EventoDetalleResponse(Long id, String titulo, String descripcion, LocalDateTime fechaHora, String ubicacion, Double latitud, Double longitud, Integer capacidad, BigDecimal precio, String aficheUrl) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.capacidad = capacidad;
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

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getAficheUrl() {
        return aficheUrl;
    }
}
