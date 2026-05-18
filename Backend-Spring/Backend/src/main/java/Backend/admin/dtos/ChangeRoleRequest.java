package Backend.admin.dtos;

import Backend.usuario.model.Rol;
import jakarta.validation.constraints.NotNull;

public class ChangeRoleRequest {

    @NotNull
    private Rol rol;

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
