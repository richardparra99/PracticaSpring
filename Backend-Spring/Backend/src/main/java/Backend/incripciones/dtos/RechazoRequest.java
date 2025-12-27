package Backend.incripciones.dtos;

import jakarta.validation.constraints.NotBlank;

public class RechazoRequest {
    @NotBlank
    private String motivo;

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
