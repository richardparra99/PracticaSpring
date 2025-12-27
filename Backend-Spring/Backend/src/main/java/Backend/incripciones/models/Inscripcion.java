package Backend.incripciones.models;

import Backend.eventos.models.Eventos;
import Backend.usuario.model.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_evento_participante", columnNames = {"evento_id", "participante_id"}),
                @UniqueConstraint(name = "uk_qr_token", columnNames = {"qr_token"})
        })
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Eventos evento;

    @ManyToOne
    @JoinColumn(name = "participante_id")
    private Usuario participante;

    @Column(name = "qr_token", nullable = false, length = 120, unique = true)
    private String qrToken;

    @Column(name = "ingresado", nullable = false)
    private Boolean ingresado = false;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "estado_pago", nullable = false, length = 20)
    private String estadoPago = "NO_REQUIERE";
    // NO_REQUIERE | PENDIENTE | APROBADO | RECHAZADO

    @Column(name = "comprobante_path", length = 500)
    private String comprobantePath;

    @Column(name = "comprobante_original", length = 255)
    private String comprobanteOriginal;

    @Column(name = "fecha_comprobante")
    private LocalDateTime fechaComprobante;

    @Column(name = "fecha_revision")
    private LocalDateTime fechaRevision;

    @Column(name = "motivo_rechazo", length = 500)
    private String motivoRechazo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public Boolean getIngresado() {
        return ingresado;
    }

    public void setIngresado(Boolean ingresado) {
        this.ingresado = ingresado;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getParticipante() {
        return participante;
    }

    public void setParticipante(Usuario participante) {
        this.participante = participante;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getComprobantePath() {
        return comprobantePath;
    }

    public void setComprobantePath(String comprobantePath) {
        this.comprobantePath = comprobantePath;
    }

    public String getComprobanteOriginal() {
        return comprobanteOriginal;
    }

    public void setComprobanteOriginal(String comprobanteOriginal) {
        this.comprobanteOriginal = comprobanteOriginal;
    }

    public LocalDateTime getFechaComprobante() {
        return fechaComprobante;
    }

    public void setFechaComprobante(LocalDateTime fechaComprobante) {
        this.fechaComprobante = fechaComprobante;
    }

    public LocalDateTime getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(LocalDateTime fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
}
