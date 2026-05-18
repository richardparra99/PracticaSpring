package Backend.admin.service;

import Backend.admin.dtos.UsuarioAdminResponse;
import Backend.usuario.model.Rol;
import Backend.usuario.model.Usuario;
import Backend.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UsuarioRepository usuarioRepository;

    public AdminService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioAdminResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioAdminResponse(u.getId(), u.getNombre(), u.getEmail(), u.getRol()))
                .toList();
    }

    public UsuarioAdminResponse cambiarRol(Long userId, Rol nuevoRol) {
        Usuario u = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        u.setRol(nuevoRol);
        u = usuarioRepository.save(u);

        return new UsuarioAdminResponse(u.getId(), u.getNombre(), u.getEmail(), u.getRol());
    }
}
