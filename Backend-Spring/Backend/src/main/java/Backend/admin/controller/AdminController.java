package Backend.admin.controller;

import Backend.admin.dtos.ChangeRoleRequest;
import Backend.admin.dtos.UsuarioAdminResponse;
import Backend.admin.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioAdminResponse> listarUsuarios() {
        return adminService.listarUsuarios();
    }

    @PutMapping("/usuarios/{id}/rol")
    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioAdminResponse cambiarRol(@PathVariable Long id, @Valid @RequestBody ChangeRoleRequest req) {
        return adminService.cambiarRol(id, req.getRol());
    }
}
