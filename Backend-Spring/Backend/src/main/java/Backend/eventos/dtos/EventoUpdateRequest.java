package Backend.eventos.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventoUpdateRequest {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotNull
    @Future
    private LocalDateTime fechaHora;

    @NotBlank
    private String ubicacion;

    private Double latitud;
    private Double longitud;

    @NotNull
    @Min(1)
    private Integer capacidadMax;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal precio;

    public Integer getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(Integer capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    private String aficheUrl;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getAficheUrl() {
        return aficheUrl;
    }

    public void setAficheUrl(String aficheUrl) {
        this.aficheUrl = aficheUrl;
    }
}
